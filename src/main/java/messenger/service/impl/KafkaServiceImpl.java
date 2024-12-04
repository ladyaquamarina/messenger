package messenger.service.impl;

import lombok.RequiredArgsConstructor;
import messenger.entity.MessageEntity;
import messenger.service.KafkaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService<String, String> {

    private static final String TOPIC_MESSAGE = "message";

    private final KafkaTemplate<String, String> messageKafkaTemplate;

    @Override
    public Mono<String> sendMessage(String messageId) {
        messageKafkaTemplate.send(TOPIC_MESSAGE, messageId);
        return Mono.just(messageId);
    }

    @Override
    @KafkaListener(topics = TOPIC_MESSAGE, containerFactory = "messageKafkaListenerContainerFactory")
    public Mono<String> getMessageId() {
        String messageId = messageKafkaTemplate.receive(TOPIC_MESSAGE, 1, 1).value();
        return Mono.just(messageId);
    }
}
