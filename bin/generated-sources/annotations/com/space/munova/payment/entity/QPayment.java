package com.space.munova.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment> {

    private static final long serialVersionUID = -1191017400L;

    public static final QPayment payment = new QPayment("payment");

    public final com.space.munova.core.entity.QBaseEntity _super = new com.space.munova.core.entity.QBaseEntity(this);

    public final DateTimePath<java.time.Instant> approvedAt = createDateTime("approvedAt", java.time.Instant.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastTransactionKey = createString("lastTransactionKey");

    public final EnumPath<PaymentMethod> method = createEnum("method", PaymentMethod.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath receipt = createString("receipt");

    public final DateTimePath<java.time.Instant> requestedAt = createDateTime("requestedAt", java.time.Instant.class);

    public final EnumPath<PaymentStatus> status = createEnum("status", PaymentStatus.class);

    public final StringPath tossPaymentKey = createString("tossPaymentKey");

    public final NumberPath<Long> totalAmount = createNumber("totalAmount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPayment(String variable) {
        super(Payment.class, forVariable(variable));
    }

    public QPayment(Path<? extends Payment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPayment(PathMetadata metadata) {
        super(Payment.class, metadata);
    }

}

