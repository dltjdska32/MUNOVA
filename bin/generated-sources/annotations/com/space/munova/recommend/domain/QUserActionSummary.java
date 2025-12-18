package com.space.munova.recommend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserActionSummary is a Querydsl query type for UserActionSummary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserActionSummary extends EntityPathBase<UserActionSummary> {

    private static final long serialVersionUID = -986137166L;

    public static final QUserActionSummary userActionSummary = new QUserActionSummary("userActionSummary");

    public final NumberPath<Integer> clicked = createNumber("clicked", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> clickedAt = createDateTime("clickedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath inCart = createBoolean("inCart");

    public final DateTimePath<java.time.LocalDateTime> inCartAt = createDateTime("inCartAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUpdated = createDateTime("lastUpdated", java.time.LocalDateTime.class);

    public final BooleanPath liked = createBoolean("liked");

    public final DateTimePath<java.time.LocalDateTime> likedAt = createDateTime("likedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final BooleanPath purchased = createBoolean("purchased");

    public final DateTimePath<java.time.LocalDateTime> purchasedAt = createDateTime("purchasedAt", java.time.LocalDateTime.class);

    public QUserActionSummary(String variable) {
        super(UserActionSummary.class, forVariable(variable));
    }

    public QUserActionSummary(Path<? extends UserActionSummary> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserActionSummary(PathMetadata metadata) {
        super(UserActionSummary.class, metadata);
    }

}

