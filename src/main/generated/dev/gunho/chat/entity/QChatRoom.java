package dev.gunho.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = 1664477768L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final dev.gunho.user.entity.QUser host;

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final EnumPath<dev.gunho.chat.constant.ChatStatus> status = createEnum("status", dev.gunho.chat.constant.ChatStatus.class);

    public final StringPath title = createString("title");

    public final ListPath<dev.gunho.user.entity.User, dev.gunho.user.entity.QUser> users = this.<dev.gunho.user.entity.User, dev.gunho.user.entity.QUser>createList("users", dev.gunho.user.entity.User.class, dev.gunho.user.entity.QUser.class, PathInits.DIRECT2);

    public QChatRoom(String variable) {
        this(ChatRoom.class, forVariable(variable), INITS);
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoom(PathMetadata metadata, PathInits inits) {
        this(ChatRoom.class, metadata, inits);
    }

    public QChatRoom(Class<? extends ChatRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.host = inits.isInitialized("host") ? new dev.gunho.user.entity.QUser(forProperty("host"), inits.get("host")) : null;
    }

}

