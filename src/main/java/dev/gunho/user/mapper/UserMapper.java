package dev.gunho.user.mapper;

import dev.gunho.user.dto.UserDto;
import dev.gunho.user.dto.UserPayload;
import dev.gunho.user.entity.User;
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

    @Mapping(target = "_csrf", ignore = true)
    @Mapping(target = "_token", ignore = true)
    @Mapping(target = "emailAuth", ignore = true)
    UserDto toDto(User entity);

    UserPayload toPayload(User entity);
}
