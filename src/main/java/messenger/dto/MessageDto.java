package messenger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.enums.MessageStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private String id;
    private String chatId;
    private String authorId;
    private String message;
    private Integer numberInChat;
    LocalDateTime createdAt;
    String status;
}
