package dev.gunho.user.mapper;

import dev.gunho.user.dto.InviteDto;
import dev.gunho.user.entity.Invite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InviteMapper {

    InviteMapper INSTANCE = Mappers.getMapper(InviteMapper.class);

    @Mappings({
            @Mapping(source = "inviter.userId", target = "inviterId"),
            @Mapping(source = "invitee.userId", target = "inviteeId", defaultValue = ""),
            @Mapping(source = "inviter.idx", target = "inviterIdx"),
            @Mapping(source = "invitee.idx", target = "inviteeIdx", defaultValue = "0"),
    })
    InviteDto toDto(Invite invite);
}
