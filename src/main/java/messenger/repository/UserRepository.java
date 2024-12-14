package messenger.repository;

import messenger.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends R2dbcRepository<UserEntity, String> {
    @Query("""
            SELECT u.id
            FROM "user" u
            WHERE u.status <> 'USER'
            """)
    Flux<String> getAllNotUserId();
}
