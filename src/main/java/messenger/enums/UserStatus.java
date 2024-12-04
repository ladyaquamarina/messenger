package messenger.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ADMIN("Администратор"),
    SUPPORT("Сотрудник техподдержки"),
    USER("Пользователь");

    private final String value;
}
