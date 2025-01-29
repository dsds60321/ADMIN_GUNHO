package dev.gunho.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuth is a Querydsl query type for Auth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuth extends EntityPathBase<Auth> {

    private static final long serialVersionUID = -2123419120L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuth auth = new QAuth("auth");

    public final dev.gunho.global.entity.QBaseTimeEntity _super = new dev.gunho.global.entity.QBaseTimeEntity(this);

    public final StringPath accessToken = createString("accessToken");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath refreshToken = createString("refreshToken");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> udtDate = _super.udtDate;

    public final QUser user;

    public QAuth(String variable) {
        this(Auth.class, forVariable(variable), INITS);
    }

    public QAuth(Path<? extends Auth> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuth(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuth(PathMetadata metadata, PathInits inits) {
        this(Auth.class, metadata, inits);
    }

    public QAuth(Class<? extends Auth> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

