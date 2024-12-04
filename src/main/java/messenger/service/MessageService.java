package messenger.service;

import messenger.entity.MessageEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageService {
    Mono<MessageEntity> sendMessage(String chatId, String authorId, String message);
    Mono<MessageEntity> getMessage(String chatId, String userId, String messageId);
    Mono<MessageEntity> getMessage(String userId);
    Flux<MessageEntity> getMessages(String chatId, String userId);
    Mono<String> deleteMessage(String messageId, String userId, String chatId);
}
