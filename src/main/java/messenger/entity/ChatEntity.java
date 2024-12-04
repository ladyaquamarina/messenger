package messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("chats")
public class ChatEntity implements Persistable<String> {
    @Id
    @Column("id")
    private String id;

    @Column("user_id")
    private String userId;

    @Column("support_ids")
    private List<String> supportIds;

    @Transient
    private boolean isNew = false;
}
