package messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.enums.MessageStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("message")
public class MessageEntity implements Persistable<String> {
    @Id
    @Column("id")
    private String id;

    @Column("chat_id")
    private String chatId;

    @Column("author_id")
    private String authorId;

    @Column("message")
    private String message;

    @Column("number_in_chat")
    private Integer numberInChat;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("status")
    private MessageStatus status;

    @Transient
    private boolean isNew = false;
}
