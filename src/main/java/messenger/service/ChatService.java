package messenger.service;

import messenger.entity.ChatEntity;
import messenger.entity.MessageEntity;
import reactor.core.publisher.Mono;

public interface ChatService {
    Mono<ChatEntity> getById(String id);
    Mono<ChatEntity> getByIdAndUserId(String id, String userId);
    Mono<ChatEntity> addSupportToChat(MessageEntity message, String supportId);
}
