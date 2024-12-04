package messenger.repository;

import messenger.entity.UserToChatEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserToChatRepository extends R2dbcRepository<UserToChatEntity, String> {
    Mono<UserToChatEntity> findByUserId(String userId);
    Mono<UserToChatEntity> findByUserIdAndChatId(String userId, String chatId);
}
