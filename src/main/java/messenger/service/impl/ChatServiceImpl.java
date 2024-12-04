package messenger.service.impl;

import lombok.RequiredArgsConstructor;
import messenger.entity.ChatEntity;
import messenger.repository.ChatRepository;
import messenger.service.ChatService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Mono<ChatEntity> getById(String id) {
        return chatRepository.findById(id);
    }
}
