package messenger.service;

import messenger.entity.UserToChatEntity;
import reactor.core.publisher.Mono;

public interface UserToChatService {
    Mono<UserToChatEntity> getByUserId(String userId);
    Mono<Boolean> checkMatchUserToChat(String userId, String chatId);
}
