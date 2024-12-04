package messenger.mapper;

import messenger.dto.MessageDto;
import messenger.entity.MessageEntity;
import messenger.enums.MessageStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "getStringFromStatus")
    MessageEntity toEntity(MessageDto dto);

    @Mapping(target = "status", source = "status", qualifiedByName = "getStatusFromString")
    MessageDto toDto(MessageEntity entity);

    @Named("getStringFromStatus")
    private String getStringFromStatus(MessageStatus status) {
        if (status == null) return MessageStatus.ACTIVE.getValue();
        return status.getValue();
    }

    @Named("getStatusFromString")
    private MessageStatus getStatusFromString(String status) {
        if (status == null || status.isEmpty()) return MessageStatus.ACTIVE;
        return MessageStatus.valueOf(status);
    }
}
