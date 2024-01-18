package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Expression;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <E>
 */
public abstract class TaggedLatinTextBuilder<E extends Expression> extends CheckTokenBuilder<Token, E>
        implements PredicateListTokenBuilder<Token> {

    private final WktKeyword tag;

    protected TaggedLatinTextBuilder(final WktKeyword tag) {
        this.tag = tag;
    }

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(this.tag,
                LeftDelimiter.class::isInstance,
                QuotedLatinText.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    public static TaggedLatinTextBuilder<Area> area() {
        return new TaggedLatinTextBuilder<Area>(WktKeyword.AREA) {

            @Override
            public Area build() {
                return new Area(first(), last(), index(), (QuotedLatinText) token(2));
            }
        };
    }

    public static TaggedLatinTextBuilder<Citation> citation() {
        return new TaggedLatinTextBuilder<Citation>(WktKeyword.CITATION) {

            @Override
            public Citation build() {
                return new Citation(first(), last(), index(), (QuotedLatinText) token(2));
            }
        };
    }

    public static TaggedLatinTextBuilder<Uri> uri() {
        return new TaggedLatinTextBuilder<Uri>(WktKeyword.URI) {

            @Override
            public Uri build() {
                return new Uri(first(), last(), index(), (QuotedLatinText) token(2));
            }
        };
    }

    public static TaggedLatinTextBuilder<Anchor> anchor() {
        return new TaggedLatinTextBuilder<Anchor>(WktKeyword.ANCHOR) {

            @Override
            public Anchor build() {
                return new Anchor(first(), last(), index(), (QuotedLatinText) token(2));
            }
        };
    }

    public static TaggedLatinTextBuilder<Scope> scope() {
        return new TaggedLatinTextBuilder<Scope>(WktKeyword.SCOPE) {

            @Override
            public Scope build() {
                return new Scope(first(), last(), index(), (QuotedLatinText) token(2));
            }
        };
    }
}
