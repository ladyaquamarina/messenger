package messenger.service.impl;

import lombok.RequiredArgsConstructor;
import messenger.entity.MessageEntity;
import messenger.enums.MessageStatus;
import messenger.repository.MessageRepository;
import messenger.service.ChatService;
import messenger.service.KafkaService;
import messenger.service.MessageService;
import messenger.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static messenger.util.Util.SUCCESS;
import static messenger.util.Util.UNAUTHORIZED_ACCESS_ATTEMPT_EXCEPTION;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    private final KafkaService kafkaService;
    private final UserService userService;
    private final ChatService chatService;

    @Override
    public Mono<MessageEntity> sendMessage(String chatId, String authorId, String message) {
        MessageEntity messageEntity = createNewMessage(authorId, chatId, message);
        return checkUserMatchChat(authorId, chatId)
                .map(b -> messageEntity)
                .flatMap(entity -> messageRepository.countByChatId(chatId)
                        .map(count -> {
                            entity.setNumberInChat(count == null ? 1 : ++count);
                            return entity;
                        }))
                .flatMap(messageRepository::save)
                .flatMap(entity -> checkIdFromNotUserIdList(authorId)
                        .filter(checkNotUser -> !checkNotUser)
                        .map(check -> entity)
                )
                .flatMap(kafkaService::sendMessage)
                .flatMap(entity -> chatService.addSupportToChat((MessageEntity) entity, authorId))
                .map(chat -> messageEntity);
    }

    @Override
    public Mono<MessageEntity> getMessage(String chatId, String userId, String messageId) {
        return checkUserMatchChat(userId, chatId)
                .flatMap(a -> messageRepository.findById(messageId));
    }

    @Override
    public Mono<MessageEntity> getMessage(String userId) {
        return checkIdFromNotUserIdList(userId)
                .filter(check -> check)
                .switchIfEmpty(Mono.error(UNAUTHORIZED_ACCESS_ATTEMPT_EXCEPTION))
                .flatMap(check -> (Mono<String>) kafkaService.getMessageId())
                .flatMap(messageRepository::findById);
    }

    @Override
    public Flux<MessageEntity> getMessages(String chatId, String userId) {
        return checkUserMatchChat(userId, chatId)
                .flatMapMany(a -> messageRepository.findAllByChatId(chatId));
    }

    @Override
    public Mono<String> deleteMessage(String messageId, String userId, String chatId) {
        return checkUserMatchChat(userId, chatId)
                .flatMap(a -> messageRepository.deleteById(messageId))
                .then(Mono.just(SUCCESS));
    }

    private Mono<Boolean> checkUserMatchChat(String userId, String chatId) {
        return userService.getAllNotUserIds()
                .any(id -> Objects.equals(id, userId))
                .flatMap(checkSupportId -> chatService.getByIdAndUserId(chatId, userId)
                        .map(checkMatchChat -> !checkSupportId || checkMatchChat != null)
                        .switchIfEmpty(checkSupportId ? Mono.just(true) : Mono.empty()))
                .switchIfEmpty(Mono.error(UNAUTHORIZED_ACCESS_ATTEMPT_EXCEPTION))
                .map(chat -> true);
    }

    private Mono<Boolean> checkIdFromNotUserIdList(String userId) {
        return userService.getAllNotUserIds()
                .any(id -> Objects.equals(id, userId));
    }

    private MessageEntity createNewMessage(String authorId, String chatId, String message) {
        MessageEntity entity = new MessageEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setAuthorId(authorId);
        entity.setChatId(chatId);
        entity.setMessage(message);
        entity.setStatus(MessageStatus.ACTIVE);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setNew(true);
        return entity;
    }
}
