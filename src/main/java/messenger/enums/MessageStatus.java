package messenger.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageStatus {
    ACTIVE("Активно"),
    DELETED("Удалено");

    private final String value;
}
