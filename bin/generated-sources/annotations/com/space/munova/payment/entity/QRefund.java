package com.space.munova.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRefund is a Querydsl query type for Refund
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRefund extends EntityPathBase<Refund> {

    private static final long serialVersionUID = -116572938L;

    public static final QRefund refund = new QRefund("refund");

    public final com.space.munova.core.entity.QBaseEntity _super = new com.space.munova.core.entity.QBaseEntity(this);

    public final NumberPath<Long> cancelAmount = createNumber("cancelAmount", Long.class);

    public final DateTimePath<java.time.Instant> canceledAt = createDateTime("canceledAt", java.time.Instant.class);

    public final StringPath cancelReason = createString("cancelReason");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> orderItemId = createNumber("orderItemId", Long.class);

    public final NumberPath<Long> paymentId = createNumber("paymentId", Long.class);

    public final StringPath paymentKey = createString("paymentKey");

    public final StringPath transactionKey = createString("transactionKey");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QRefund(String variable) {
        super(Refund.class, forVariable(variable));
    }

    public QRefund(Path<? extends Refund> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRefund(PathMetadata metadata) {
        super(Refund.class, metadata);
    }

}

