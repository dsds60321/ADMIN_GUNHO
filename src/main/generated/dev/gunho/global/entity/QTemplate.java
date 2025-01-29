package dev.gunho.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTemplate is a Querydsl query type for Template
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTemplate extends EntityPathBase<Template> {

    private static final long serialVersionUID = 118711482L;

    public static final QTemplate template = new QTemplate("template");

    public final StringPath bcc = createString("bcc");

    public final StringPath content = createString("content");

    public final StringPath from = createString("from");

    public final StringPath id = createString("id");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath subject = createString("subject");

    public final StringPath to = createString("to");

    public QTemplate(String variable) {
        super(Template.class, forVariable(variable));
    }

    public QTemplate(Path<? extends Template> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTemplate(PathMetadata metadata) {
        super(Template.class, metadata);
    }

}

