package dev.gunho.user.mapper;

import dev.gunho.user.dto.UserPayload;
import dev.gunho.user.entity.User;
import dev.gunho.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(target = "idx", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "uuid", ignore = true),
            @Mapping(target = "auth", ignore = true),
            @Mapping(target = "stocks", ignore = true),
            @Mapping(target = "rules", ignore = true),
    })
    User toEntity(UserDto dto);

    @Mapping(target = "_csrf", ignore = true)        // _csrf 필드 무시 (필요 시 수정 가능)
    @Mapping(target = "emailAuth", ignore = true)    // emailAuth 필드 무시 (필요 시 수정 가능)
    UserDto toDto(User entity);

    UserPayload toPayload(User entity);

}
