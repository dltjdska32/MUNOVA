package com.space.munova.recommend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductRecommendation is a Querydsl query type for ProductRecommendation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductRecommendation extends EntityPathBase<ProductRecommendation> {

    private static final long serialVersionUID = 1822485301L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductRecommendation productRecommendation = new QProductRecommendation("productRecommendation");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.space.munova.product.domain.QProduct sourceProduct;

    public final com.space.munova.product.domain.QProduct targetProduct;

    public QProductRecommendation(String variable) {
        this(ProductRecommendation.class, forVariable(variable), INITS);
    }

    public QProductRecommendation(Path<? extends ProductRecommendation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductRecommendation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductRecommendation(PathMetadata metadata, PathInits inits) {
        this(ProductRecommendation.class, metadata, inits);
    }

    public QProductRecommendation(Class<? extends ProductRecommendation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sourceProduct = inits.isInitialized("sourceProduct") ? new com.space.munova.product.domain.QProduct(forProperty("sourceProduct"), inits.get("sourceProduct")) : null;
        this.targetProduct = inits.isInitialized("targetProduct") ? new com.space.munova.product.domain.QProduct(forProperty("targetProduct"), inits.get("targetProduct")) : null;
    }

}

