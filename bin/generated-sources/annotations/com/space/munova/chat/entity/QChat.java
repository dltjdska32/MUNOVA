package com.space.munova.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChat is a Querydsl query type for Chat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChat extends EntityPathBase<Chat> {

    private static final long serialVersionUID = 1205920082L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChat chat = new QChat("chat");

    public final com.space.munova.core.entity.QBaseEntity _super = new com.space.munova.core.entity.QBaseEntity(this);

    public final ListPath<ChatMember, QChatMember> chatMembers = this.<ChatMember, QChatMember>createList("chatMembers", ChatMember.class, QChatMember.class, PathInits.DIRECT2);

    public final ListPath<ChatTag, QChatTag> chatTags = this.<ChatTag, QChatTag>createList("chatTags", ChatTag.class, QChatTag.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> curParticipant = createNumber("curParticipant", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastMessageContent = createString("lastMessageContent");

    public final DateTimePath<java.time.LocalDateTime> lastMessageTime = createDateTime("lastMessageTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> maxParticipant = createNumber("maxParticipant", Integer.class);

    public final StringPath name = createString("name");

    public final com.space.munova.product.domain.QProduct productId;

    public final EnumPath<com.space.munova.chat.enums.ChatStatus> status = createEnum("status", com.space.munova.chat.enums.ChatStatus.class);

    public final EnumPath<com.space.munova.chat.enums.ChatType> type = createEnum("type", com.space.munova.chat.enums.ChatType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QChat(String variable) {
        this(Chat.class, forVariable(variable), INITS);
    }

    public QChat(Path<? extends Chat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChat(PathMetadata metadata, PathInits inits) {
        this(Chat.class, metadata, inits);
    }

    public QChat(Class<? extends Chat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productId = inits.isInitialized("productId") ? new com.space.munova.product.domain.QProduct(forProperty("productId"), inits.get("productId")) : null;
    }

}

