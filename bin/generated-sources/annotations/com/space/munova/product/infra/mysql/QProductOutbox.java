package com.space.munova.product.infra.mysql;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductOutbox is a Querydsl query type for ProductOutbox
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductOutbox extends EntityPathBase<ProductOutbox> {

    private static final long serialVersionUID = 1533857310L;

    public static final QProductOutbox productOutbox = new QProductOutbox("productOutbox");

    public final com.space.munova.core.entity.QBaseEntity _super = new com.space.munova.core.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.space.munova.product.domain.enums.EventType> eventType = createEnum("eventType", com.space.munova.product.domain.enums.EventType.class);

    public final StringPath eventValue = createString("eventValue");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.space.munova.product.domain.enums.OutboxStatus> status = createEnum("status", com.space.munova.product.domain.enums.OutboxStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProductOutbox(String variable) {
        super(ProductOutbox.class, forVariable(variable));
    }

    public QProductOutbox(Path<? extends ProductOutbox> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductOutbox(PathMetadata metadata) {
        super(ProductOutbox.class, metadata);
    }

}

