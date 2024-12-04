package messenger.service.impl;

import lombok.RequiredArgsConstructor;
import messenger.entity.MessageEntity;
import messenger.enums.MessageStatus;
import messenger.repository.MessageRepository;
import messenger.service.KafkaService;
import messenger.service.MessageService;
import messenger.service.UserService;
import messenger.service.UserToChatService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static messenger.util.Util.SUCCESS;
import static messenger.util.Util.checkAuthorizedAccess;
import static messenger.util.Util.throwException;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    private final KafkaService kafkaService;
    private final UserService userService;
    private final UserToChatService userToChatService;

    @Override
    public Mono<MessageEntity> sendMessage(String chatId, String authorId, String message) {
        Mono<Boolean> check = checkIdFromNotUserIdList(authorId);
        MessageEntity messageEntity = createNewMessage(authorId, chatId, message);
        return check
                .zipWith(userToChatService.checkMatchUserToChat(authorId, chatId), (checkNotUser, checkMatchChat) -> {
                    checkAuthorizedAccess(checkNotUser, checkMatchChat);
                    return messageEntity;
                })
                .zipWith(messageRepository.countByChatId(chatId), (entity, count) -> {
                    entity.setNumberInChat(count);
                    return entity;
                })
                .flatMap(messageRepository::save)
                .zipWith(check, (entity, checkNotUser) -> checkNotUser ? entity : null)
                .flatMap(entity -> kafkaService.sendMessage(entity.getId()))
                .switchIfEmpty(Mono.just(messageEntity));
    }

    @Override
    public Mono<MessageEntity> getMessage(String chatId, String userId, String messageId) {
        Mono<Boolean> check = checkIdFromNotUserIdList(userId);
        return check
                .zipWith(userToChatService.checkMatchUserToChat(userId, chatId), (checkNotUser, checkMatchChat) -> {
                    checkAuthorizedAccess(checkNotUser, checkMatchChat);
                    return checkNotUser;
                })
                .flatMap(a -> messageRepository.findById(messageId));
    }

    @Override
    public Mono<MessageEntity> getMessage(String userId) {
        return checkIdFromNotUserIdList(userId)
                .flatMap(check -> {
                    if (check) {
                        throwException("Unauthorized access attempts");
                    }
                    return kafkaService.getMessageId();
                })
                .flatMap(messageRepository::findById);
    }

    @Override
    public Flux<MessageEntity> getMessages(String chatId, String userId) {
        Mono<Boolean> check = checkIdFromNotUserIdList(userId);
        return check
                .zipWith(userToChatService.checkMatchUserToChat(userId, chatId), (checkNotUser, checkMatchChat) -> {
                    checkAuthorizedAccess(checkNotUser, checkMatchChat);
                    return checkNotUser;
                })
                .flatMapMany(a -> messageRepository.findAllByChatId(chatId));
    }

    @Override
    public Mono<String> deleteMessage(String messageId, String userId, String chatId) {
        Mono<Boolean> check = checkIdFromNotUserIdList(userId);
        return check
                .zipWith(userToChatService.checkMatchUserToChat(userId, chatId), (checkNotUser, checkMatchChat) -> {
                    checkAuthorizedAccess(checkNotUser, checkMatchChat);
                    return checkNotUser;
                })
                .flatMap(a -> messageRepository.deleteById(messageId))
                .then(Mono.just(SUCCESS));
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
