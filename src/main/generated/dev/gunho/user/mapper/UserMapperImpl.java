package dev.gunho.user.mapper;

import dev.gunho.user.constant.UserRole;
import dev.gunho.user.dto.UserDto;
import dev.gunho.user.dto.UserPayload;
import dev.gunho.user.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-31T00:24:07+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userId( dto.userId() );
        user.password( dto.password() );
        user.email( dto.email() );
        user.nick( dto.nick() );
        user.role( dto.role() );

        return user.build();
    }

    @Override
    public UserDto toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        String userId = null;
        String password = null;
        String email = null;
        String nick = null;
        UserRole role = null;

        userId = entity.getUserId();
        password = entity.getPassword();
        email = entity.getEmail();
        nick = entity.getNick();
        role = entity.getRole();

        String csrf = null;
        String emailAuth = null;

        UserDto userDto = new UserDto( userId, password, email, nick, csrf, emailAuth, role );

        return userDto;
    }

    @Override
    public UserPayload toPayload(User entity) {
        if ( entity == null ) {
            return null;
        }

        String userId = null;
        String email = null;
        String nick = null;
        String role = null;

        userId = entity.getUserId();
        email = entity.getEmail();
        nick = entity.getNick();
        if ( entity.getRole() != null ) {
            role = entity.getRole().name();
        }

        UserPayload userPayload = new UserPayload( userId, email, nick, role );

        return userPayload;
    }
}
