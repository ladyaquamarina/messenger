package messenger.service;

import messenger.entity.MessageEntity;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import reactor.core.publisher.Mono;

public interface KafkaService<K, V> {
    void sendMessage(String messageId);
    Mono<String> getMessageId(ConsumerRecord<K, V> consumerRecord);
}
