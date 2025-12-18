package com.space.munova.recommend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserRecommendation is a Querydsl query type for UserRecommendation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserRecommendation extends EntityPathBase<UserRecommendation> {

    private static final long serialVersionUID = 132488919L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserRecommendation userRecommendation = new QUserRecommendation("userRecommendation");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.space.munova.member.entity.QMember member;

    public final com.space.munova.product.domain.QProduct product;

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public QUserRecommendation(String variable) {
        this(UserRecommendation.class, forVariable(variable), INITS);
    }

    public QUserRecommendation(Path<? extends UserRecommendation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserRecommendation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserRecommendation(PathMetadata metadata, PathInits inits) {
        this(UserRecommendation.class, metadata, inits);
    }

    public QUserRecommendation(Class<? extends UserRecommendation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.space.munova.member.entity.QMember(forProperty("member")) : null;
        this.product = inits.isInitialized("product") ? new com.space.munova.product.domain.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

