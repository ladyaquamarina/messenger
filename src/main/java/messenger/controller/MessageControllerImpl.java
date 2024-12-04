package messenger.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messenger.dto.MessageDto;
import messenger.mapper.MessageMapper;
import messenger.service.MessageService;
import messenger.util.Util;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
@Slf4j
public class MessageControllerImpl {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @PostMapping
    public Mono<String> sendMessage(@RequestParam String chatId,
                                    @RequestParam String authorId,
                                    @RequestBody String message) {
        return messageService.sendMessage(chatId, authorId, message)
                .map(entity -> Util.SUCCESS);
    }

    // for users
    @GetMapping("/to_show")
    public Mono<MessageDto> getMessage(@RequestParam String chatId,
                                       @RequestParam String userId,
                                       @RequestParam String messageId) {
        return messageService.getMessage(chatId, userId, messageId)
                .map(messageMapper::toDto);
    }

    // for supports
    @GetMapping("/to_answer")
    public Mono<MessageDto> getMessage(@RequestParam String userId) {
        return messageService.getMessage(userId)
                .map(messageMapper::toDto);
    }

    @GetMapping("/all")
    public Flux<MessageDto> getMessages(@RequestParam String chatId,
                                        @RequestParam String userId) {
        return messageService.getMessages(chatId, userId)
                .map(messageMapper::toDto);
    }

    @DeleteMapping
    public Mono<String> deleteMessage(@RequestParam String messageId,
                                      @RequestParam String userId,
                                      @RequestParam String chatId){
        return messageService.deleteMessage(messageId, userId, chatId);
    }
}
