package messenger.repository;

import messenger.entity.MessageEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MessageRepository extends R2dbcRepository<MessageEntity, String> {
    Mono<Integer> countByChatId(String chatId);
    Flux<MessageEntity> findAllByChatId(String chatId);
}
