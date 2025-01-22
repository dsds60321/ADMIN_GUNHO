package dev.gunho.toooy.user.mapper;

import dev.gunho.toooy.user.dto.UserDto;
import dev.gunho.toooy.user.domain.User;
import lombok.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "idx", ignore = true) // idx 필드 무시
    User toEntity(UserDto dto);

    UserDto toDto(User entity);

}
