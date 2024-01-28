package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;

/**
 *
 * @author Samuel Andrés
 * @param <I>
 * @param <V>
 */
public class Identifier<I extends Lexeme, V extends Lexeme> extends AbstractExpression {

    private final QuotedLatinText name;
    private final I id;
    private final V version;
    private final Citation citation;
    private final Uri uri;

    public Identifier(final int start, final int end, final int index, final QuotedLatinText name, final I id,
            final V version, final Citation citation, final Uri uri) {
        super(start, end, index);
        this.name = name;
        this.id = id;
        this.version = version;
        this.citation = citation;
        this.uri = uri;
    }

    public QuotedLatinText getName() {
        return this.name;
    }

    public I getId() {
        return this.id;
    }

    public V getVersion() {
        return this.version;
    }

    public Citation getCitation() {
        return this.citation;
    }

    public Uri getUri() {
        return this.uri;
    }
}
