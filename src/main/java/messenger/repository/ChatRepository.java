package messenger.repository;

import messenger.entity.ChatEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends R2dbcRepository<ChatEntity, String> {
}
