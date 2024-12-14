package messenger.service;

import messenger.entity.MessageEntity;
import reactor.core.publisher.Mono;

public interface KafkaService<K, V> {
    Mono<MessageEntity> sendMessage(MessageEntity message);
    Mono<String> getMessageId();
}
