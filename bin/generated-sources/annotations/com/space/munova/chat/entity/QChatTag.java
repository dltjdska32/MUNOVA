package com.space.munova.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatTag is a Querydsl query type for ChatTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatTag extends EntityPathBase<ChatTag> {

    private static final long serialVersionUID = -1836184344L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatTag chatTag = new QChatTag("chatTag");

    public final EnumPath<com.space.munova.product.domain.enums.ProductCategory> categoryType = createEnum("categoryType", com.space.munova.product.domain.enums.ProductCategory.class);

    public final QChat chat;

    public final NumberPath<Long> chatTagId = createNumber("chatTagId", Long.class);

    public final com.space.munova.product.domain.QCategory productCategoryId;

    public QChatTag(String variable) {
        this(ChatTag.class, forVariable(variable), INITS);
    }

    public QChatTag(Path<? extends ChatTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatTag(PathMetadata metadata, PathInits inits) {
        this(ChatTag.class, metadata, inits);
    }

    public QChatTag(Class<? extends ChatTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chat = inits.isInitialized("chat") ? new QChat(forProperty("chat"), inits.get("chat")) : null;
        this.productCategoryId = inits.isInitialized("productCategoryId") ? new com.space.munova.product.domain.QCategory(forProperty("productCategoryId"), inits.get("productCategoryId")) : null;
    }

}

