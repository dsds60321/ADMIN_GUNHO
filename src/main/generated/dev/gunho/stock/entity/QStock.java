package dev.gunho.stock.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStock is a Querydsl query type for Stock
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStock extends EntityPathBase<Stock> {

    private static final long serialVersionUID = 2094638019L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStock stock = new QStock("stock");

    public final dev.gunho.global.entity.QBaseTimeEntity _super = new dev.gunho.global.entity.QBaseTimeEntity(this);

    public final NumberPath<Double> averagePrice = createNumber("averagePrice", Double.class);

    public final StringPath currency = createString("currency");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final dev.gunho.rule.entity.QRule rule;

    public final StringPath symbol = createString("symbol");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> udtDate = _super.udtDate;

    public final dev.gunho.user.entity.QUser user;

    public QStock(String variable) {
        this(Stock.class, forVariable(variable), INITS);
    }

    public QStock(Path<? extends Stock> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStock(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStock(PathMetadata metadata, PathInits inits) {
        this(Stock.class, metadata, inits);
    }

    public QStock(Class<? extends Stock> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rule = inits.isInitialized("rule") ? new dev.gunho.rule.entity.QRule(forProperty("rule"), inits.get("rule")) : null;
        this.user = inits.isInitialized("user") ? new dev.gunho.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

