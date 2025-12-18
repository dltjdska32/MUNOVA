package com.space.munova.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderProductLog is a Querydsl query type for OrderProductLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderProductLog extends EntityPathBase<OrderProductLog> {

    private static final long serialVersionUID = -1130044163L;

    public static final QOrderProductLog orderProductLog = new QOrderProductLog("orderProductLog");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> order_product_log_id = createNumber("order_product_log_id", Long.class);

    public final EnumPath<com.space.munova.order.dto.OrderStatus> orderStatus = createEnum("orderStatus", com.space.munova.order.dto.OrderStatus.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QOrderProductLog(String variable) {
        super(OrderProductLog.class, forVariable(variable));
    }

    public QOrderProductLog(Path<? extends OrderProductLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderProductLog(PathMetadata metadata) {
        super(OrderProductLog.class, metadata);
    }

}

