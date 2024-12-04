package messenger.service;

import messenger.entity.ChatEntity;
import reactor.core.publisher.Mono;

public interface ChatService {
    public Mono<ChatEntity> getById(String id);
}
