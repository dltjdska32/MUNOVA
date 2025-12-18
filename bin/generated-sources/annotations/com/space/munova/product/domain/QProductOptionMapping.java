package com.space.munova.product.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductOptionMapping is a Querydsl query type for ProductOptionMapping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductOptionMapping extends EntityPathBase<ProductOptionMapping> {

    private static final long serialVersionUID = -691381232L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductOptionMapping productOptionMapping = new QProductOptionMapping("productOptionMapping");

    public final com.space.munova.core.entity.QBaseEntity _super = new com.space.munova.core.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final QOption option;

    public final QProductDetail productDetail;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProductOptionMapping(String variable) {
        this(ProductOptionMapping.class, forVariable(variable), INITS);
    }

    public QProductOptionMapping(Path<? extends ProductOptionMapping> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductOptionMapping(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductOptionMapping(PathMetadata metadata, PathInits inits) {
        this(ProductOptionMapping.class, metadata, inits);
    }

    public QProductOptionMapping(Class<? extends ProductOptionMapping> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.option = inits.isInitialized("option") ? new QOption(forProperty("option")) : null;
        this.productDetail = inits.isInitialized("productDetail") ? new QProductDetail(forProperty("productDetail"), inits.get("productDetail")) : null;
    }

}

