package messenger.service.impl;

import lombok.RequiredArgsConstructor;
import messenger.entity.ChatEntity;
import messenger.entity.MessageEntity;
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

    @Override
    public Mono<ChatEntity> getByIdAndUserId(String id, String userId) {
        return chatRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public Mono<ChatEntity> addSupportToChat(MessageEntity message, String supportId) {
        return chatRepository.findById(message.getChatId())
                .map(chat -> {
                    chat.getSupportIds().add(supportId);
                    return chat;
                })
                .flatMap(chatRepository::save);
    }
}
