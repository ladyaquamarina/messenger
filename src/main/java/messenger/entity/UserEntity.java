package messenger.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.enums.UserStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity implements Persistable<String> {

    @Id
    @Column("id")
    private String id;

    @Column("first_name")
    private String firstName;

    @Column("second_name")
    private String lastName;

    @Column("sur_name")
    private String surName;

    @Column("status")
    private UserStatus status;

    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
