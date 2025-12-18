package com.space.munova.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductSearchLog is a Querydsl query type for ProductSearchLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductSearchLog extends EntityPathBase<ProductSearchLog> {

    private static final long serialVersionUID = 1802897779L;

    public static final QProductSearchLog productSearchLog = new QProductSearchLog("productSearchLog");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> searchCategoryId = createNumber("searchCategoryId", Long.class);

    public final StringPath searchDetail = createString("searchDetail");

    public QProductSearchLog(String variable) {
        super(ProductSearchLog.class, forVariable(variable));
    }

    public QProductSearchLog(Path<? extends ProductSearchLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductSearchLog(PathMetadata metadata) {
        super(ProductSearchLog.class, metadata);
    }

}

