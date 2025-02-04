package dev.gunho.rule.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRule is a Querydsl query type for Rule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRule extends EntityPathBase<Rule> {

    private static final long serialVersionUID = -1595845355L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRule rule = new QRule("rule");

    public final dev.gunho.global.entity.QBaseTimeEntity _super = new dev.gunho.global.entity.QBaseTimeEntity(this);

    public final NumberPath<java.math.BigDecimal> buyPercentage = createNumber("buyPercentage", java.math.BigDecimal.class);

    public final NumberPath<Integer> buyPrice = createNumber("buyPrice", Integer.class);

    public final StringPath id = createString("id");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final NumberPath<java.math.BigDecimal> sellPercentage = createNumber("sellPercentage", java.math.BigDecimal.class);

    public final NumberPath<Integer> sellPrice = createNumber("sellPrice", Integer.class);

    public final dev.gunho.stock.entity.QStock stock;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> udtDate = _super.udtDate;

    public final dev.gunho.user.entity.QUser user;

    public QRule(String variable) {
        this(Rule.class, forVariable(variable), INITS);
    }

    public QRule(Path<? extends Rule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRule(PathMetadata metadata, PathInits inits) {
        this(Rule.class, metadata, inits);
    }

    public QRule(Class<? extends Rule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.stock = inits.isInitialized("stock") ? new dev.gunho.stock.entity.QStock(forProperty("stock"), inits.get("stock")) : null;
        this.user = inits.isInitialized("user") ? new dev.gunho.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

