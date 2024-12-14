package messenger.service.impl;

import lombok.RequiredArgsConstructor;
import messenger.entity.MessageEntity;
import messenger.service.KafkaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService<String, String> {

    private static final String TOPIC_MESSAGE = "message";

    private final KafkaTemplate<String, String> messageKafkaTemplate;

    private Long offset = 0L;

    @Override
    public Mono<MessageEntity> sendMessage(MessageEntity message) {
        messageKafkaTemplate.send(TOPIC_MESSAGE, message.getId());
        return Mono.just(message);
    }

    @Override
    @KafkaListener(topics = TOPIC_MESSAGE, containerFactory = "messageKafkaListenerContainerFactory")
    public Mono<String> getMessageId() {
        String messageId;
        synchronized (offset) {
            ConsumerRecord<String, String> kafkaMessage = messageKafkaTemplate.receive(TOPIC_MESSAGE, 0, offset);
            if (kafkaMessage != null) {
                offset++;
                messageId = kafkaMessage.value();
            } else {
                messageId = null;
            }
        }
        return Mono.just(messageId);
    }
}
