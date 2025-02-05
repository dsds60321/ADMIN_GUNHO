package dev.gunho.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInvite is a Querydsl query type for Invite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInvite extends EntityPathBase<Invite> {

    private static final long serialVersionUID = -273675919L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInvite invite = new QInvite("invite");

    public final dev.gunho.global.entity.QBaseTimeEntity _super = new dev.gunho.global.entity.QBaseTimeEntity(this);

    public final StringPath email = createString("email");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final DateTimePath<java.time.LocalDateTime> invitationTime = createDateTime("invitationTime", java.time.LocalDateTime.class);

    public final QUser invitee;

    public final QUser inviter;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final EnumPath<dev.gunho.user.constant.InviteStatus> status = createEnum("status", dev.gunho.user.constant.InviteStatus.class);

    public final StringPath token = createString("token");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> udtDate = _super.udtDate;

    public QInvite(String variable) {
        this(Invite.class, forVariable(variable), INITS);
    }

    public QInvite(Path<? extends Invite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInvite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInvite(PathMetadata metadata, PathInits inits) {
        this(Invite.class, metadata, inits);
    }

    public QInvite(Class<? extends Invite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.invitee = inits.isInitialized("invitee") ? new QUser(forProperty("invitee"), inits.get("invitee")) : null;
        this.inviter = inits.isInitialized("inviter") ? new QUser(forProperty("inviter"), inits.get("inviter")) : null;
    }

}

