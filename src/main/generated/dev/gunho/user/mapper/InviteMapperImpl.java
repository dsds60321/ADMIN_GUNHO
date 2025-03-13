package dev.gunho.user.mapper;

import dev.gunho.user.dto.InviteDto;
import dev.gunho.user.entity.Invite;
import dev.gunho.user.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-25T23:16:58+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class InviteMapperImpl implements InviteMapper {

    @Override
    public InviteDto toDto(Invite invite) {
        if ( invite == null ) {
            return null;
        }

        String inviterId = null;
        String inviteeId = null;
        long inviterIdx = 0L;
        long inviteeIdx = 0L;
        String email = null;

        inviterId = inviteInviterUserId( invite );
        String userId1 = inviteInviteeUserId( invite );
        if ( userId1 != null ) {
            inviteeId = userId1;
        }
        else {
            inviteeId = "";
        }
        inviterIdx = inviteInviterIdx( invite );
        inviteeIdx = inviteInviteeIdx( invite );
        email = invite.getEmail();

        InviteDto inviteDto = new InviteDto( email, inviteeId, inviteeIdx, inviterId, inviterIdx );

        return inviteDto;
    }

    private String inviteInviterUserId(Invite invite) {
        if ( invite == null ) {
            return null;
        }
        User inviter = invite.getInviter();
        if ( inviter == null ) {
            return null;
        }
        String userId = inviter.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }

    private String inviteInviteeUserId(Invite invite) {
        if ( invite == null ) {
            return null;
        }
        User invitee = invite.getInvitee();
        if ( invitee == null ) {
            return null;
        }
        String userId = invitee.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }

    private long inviteInviterIdx(Invite invite) {
        if ( invite == null ) {
            return 0L;
        }
        User inviter = invite.getInviter();
        if ( inviter == null ) {
            return 0L;
        }
        long idx = inviter.getIdx();
        return idx;
    }

    private long inviteInviteeIdx(Invite invite) {
        if ( invite == null ) {
            return 0L;
        }
        User invitee = invite.getInvitee();
        if ( invitee == null ) {
            return 0L;
        }
        long idx = invitee.getIdx();
        return idx;
    }
}
