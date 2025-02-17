package dev.gunho.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -2122825677L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final dev.gunho.global.entity.QBaseTimeEntity _super = new dev.gunho.global.entity.QBaseTimeEntity(this);

    public final QAuth auth;

    public final ListPath<dev.gunho.chat.entity.ChatRoom, dev.gunho.chat.entity.QChatRoom> chatRooms = this.<dev.gunho.chat.entity.ChatRoom, dev.gunho.chat.entity.QChatRoom>createList("chatRooms", dev.gunho.chat.entity.ChatRoom.class, dev.gunho.chat.entity.QChatRoom.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath nick = createString("nick");

    public final StringPath password = createString("password");

    public final ListPath<Invite, QInvite> receivedInvites = this.<Invite, QInvite>createList("receivedInvites", Invite.class, QInvite.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final EnumPath<dev.gunho.user.constant.UserRole> role = createEnum("role", dev.gunho.user.constant.UserRole.class);

    public final ListPath<dev.gunho.rule.entity.Rule, dev.gunho.rule.entity.QRule> rules = this.<dev.gunho.rule.entity.Rule, dev.gunho.rule.entity.QRule>createList("rules", dev.gunho.rule.entity.Rule.class, dev.gunho.rule.entity.QRule.class, PathInits.DIRECT2);

    public final ListPath<Invite, QInvite> sentInvites = this.<Invite, QInvite>createList("sentInvites", Invite.class, QInvite.class, PathInits.DIRECT2);

    public final StringPath status = createString("status");

    public final ListPath<dev.gunho.stock.entity.Stock, dev.gunho.stock.entity.QStock> stocks = this.<dev.gunho.stock.entity.Stock, dev.gunho.stock.entity.QStock>createList("stocks", dev.gunho.stock.entity.Stock.class, dev.gunho.stock.entity.QStock.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> udtDate = _super.udtDate;

    public final StringPath userId = createString("userId");

    public final StringPath uuid = createString("uuid");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auth = inits.isInitialized("auth") ? new QAuth(forProperty("auth"), inits.get("auth")) : null;
    }

}

