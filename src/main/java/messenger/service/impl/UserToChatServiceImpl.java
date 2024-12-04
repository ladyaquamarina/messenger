package messenger.service.impl;

import lombok.RequiredArgsConstructor;
import messenger.entity.UserToChatEntity;
import messenger.repository.UserToChatRepository;
import messenger.service.UserToChatService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserToChatServiceImpl implements UserToChatService {

    private final UserToChatRepository userToChatRepository;

    @Override
    public Mono<UserToChatEntity> getByUserId(String userId) {
        return userToChatRepository.findByUserId(userId);
    }

    @Override
    public Mono<Boolean> checkMatchUserToChat(String userId, String chatId) {
        return userToChatRepository.findByUserIdAndChatId(userId, chatId)
                .map(entity -> true)
                .switchIfEmpty(Mono.just(false));
    }
}
