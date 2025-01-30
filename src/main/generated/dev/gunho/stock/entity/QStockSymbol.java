package dev.gunho.stock.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStockSymbol is a Querydsl query type for StockSymbol
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStockSymbol extends EntityPathBase<StockSymbol> {

    private static final long serialVersionUID = 766568251L;

    public static final QStockSymbol stockSymbol = new QStockSymbol("stockSymbol");

    public final StringPath country = createString("country");

    public final StringPath industry = createString("industry");

    public final NumberPath<java.math.BigDecimal> lastSale = createNumber("lastSale", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> marketCap = createNumber("marketCap", java.math.BigDecimal.class);

    public final StringPath name = createString("name");

    public final StringPath sector = createString("sector");

    public final StringPath symbol = createString("symbol");

    public QStockSymbol(String variable) {
        super(StockSymbol.class, forVariable(variable));
    }

    public QStockSymbol(Path<? extends StockSymbol> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStockSymbol(PathMetadata metadata) {
        super(StockSymbol.class, metadata);
    }

}

