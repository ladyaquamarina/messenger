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

    private final AtomicLong offset = new AtomicLong(0);

    @Override
    public Mono<MessageEntity> sendMessage(MessageEntity message) {
        messageKafkaTemplate.send(TOPIC_MESSAGE, message.getId());
        return Mono.just(message);
    }

    @Override
    @KafkaListener(topics = TOPIC_MESSAGE, containerFactory = "messageKafkaListenerContainerFactory")
    public Mono<String> getMessageId() {
        String messageId = messageKafkaTemplate.receive(TOPIC_MESSAGE, 0, offset.getAndIncrement()).value();
        return Mono.just(messageId);
    }
}
