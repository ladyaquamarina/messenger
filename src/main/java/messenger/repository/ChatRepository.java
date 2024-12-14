package messenger.repository;

import messenger.entity.ChatEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ChatRepository extends R2dbcRepository<ChatEntity, String> {
    Mono<ChatEntity> findByIdAndUserId(String id, String userId);
}
