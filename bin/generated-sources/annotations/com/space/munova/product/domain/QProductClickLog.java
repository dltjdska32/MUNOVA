package com.space.munova.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductClickLog is a Querydsl query type for ProductClickLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductClickLog extends EntityPathBase<ProductClickLog> {

    private static final long serialVersionUID = -1586620219L;

    public static final QProductClickLog productClickLog = new QProductClickLog("productClickLog");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> productClickLogId = createNumber("productClickLogId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public QProductClickLog(String variable) {
        super(ProductClickLog.class, forVariable(variable));
    }

    public QProductClickLog(Path<? extends ProductClickLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductClickLog(PathMetadata metadata) {
        super(ProductClickLog.class, metadata);
    }

}

