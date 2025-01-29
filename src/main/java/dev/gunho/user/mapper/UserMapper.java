package dev.gunho.user.mapper;

import dev.gunho.user.domain.User;
import dev.gunho.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "idx", ignore = true)           // idx 필드 무시
    @Mapping(target = "status", ignore = true)       // status 필드 무시 (필요 시 수정 가능)
    @Mapping(target = "uuid", ignore = true)         // uuid 필드 무시 (필요 시 수정 가능)
    @Mapping(target = "auth", ignore = true)         // auth 필드 무시 (필요 시 수정 가능)
    User toEntity(UserDto dto);

    @Mapping(target = "_csrf", ignore = true)        // _csrf 필드 무시 (필요 시 수정 가능)
    @Mapping(target = "emailAuth", ignore = true)    // emailAuth 필드 무시 (필요 시 수정 가능)
    UserDto toDto(User entity);

}
