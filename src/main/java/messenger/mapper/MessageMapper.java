package messenger.mapper;

import messenger.dto.MessageDto;
import messenger.entity.MessageEntity;
import messenger.enums.MessageStatus;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @InheritInverseConfiguration
    MessageEntity toEntity(MessageDto dto);

    MessageDto toDto(MessageEntity entity);
}
