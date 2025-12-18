package com.space.munova.coupon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCouponDetail is a Querydsl query type for CouponDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCouponDetail extends EntityPathBase<CouponDetail> {

    private static final long serialVersionUID = 1228304543L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCouponDetail couponDetail = new QCouponDetail("couponDetail");

    public final com.space.munova.core.entity.QBaseEntity _super = new com.space.munova.core.entity.QBaseEntity(this);

    public final StringPath couponName = createString("couponName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.space.munova.coupon.dto.QDiscountPolicy discountPolicy;

    public final DateTimePath<java.time.LocalDateTime> expiredAt = createDateTime("expiredAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> publishedAt = createDateTime("publishedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> publisherId = createNumber("publisherId", Long.class);

    public final NumberPath<Long> quantity = createNumber("quantity", Long.class);

    public final NumberPath<Long> remainQuantity = createNumber("remainQuantity", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCouponDetail(String variable) {
        this(CouponDetail.class, forVariable(variable), INITS);
    }

    public QCouponDetail(Path<? extends CouponDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCouponDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCouponDetail(PathMetadata metadata, PathInits inits) {
        this(CouponDetail.class, metadata, inits);
    }

    public QCouponDetail(Class<? extends CouponDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.discountPolicy = inits.isInitialized("discountPolicy") ? new com.space.munova.coupon.dto.QDiscountPolicy(forProperty("discountPolicy")) : null;
    }

}

