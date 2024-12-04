package messenger.service.impl;

import lombok.RequiredArgsConstructor;
import messenger.repository.UserRepository;
import messenger.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Flux<String> getAllNotUserIds() {
        return userRepository.getAllNotUserId();
    }
}
