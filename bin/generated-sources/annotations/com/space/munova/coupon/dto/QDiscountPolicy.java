package com.space.munova.coupon.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDiscountPolicy is a Querydsl query type for DiscountPolicy
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDiscountPolicy extends BeanPath<DiscountPolicy> {

    private static final long serialVersionUID = -863119867L;

    public static final QDiscountPolicy discountPolicy = new QDiscountPolicy("discountPolicy");

    public final EnumPath<CouponType> couponType = createEnum("couponType", CouponType.class);

    public final NumberPath<Long> discountAmount = createNumber("discountAmount", Long.class);

    public final NumberPath<Long> maxDiscountAmount = createNumber("maxDiscountAmount", Long.class);

    public final NumberPath<Long> minPaymentAmount = createNumber("minPaymentAmount", Long.class);

    public QDiscountPolicy(String variable) {
        super(DiscountPolicy.class, forVariable(variable));
    }

    public QDiscountPolicy(Path<? extends DiscountPolicy> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDiscountPolicy(PathMetadata metadata) {
        super(DiscountPolicy.class, metadata);
    }

}

