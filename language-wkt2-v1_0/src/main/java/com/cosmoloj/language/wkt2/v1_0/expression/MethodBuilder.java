package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <M>
 */
public abstract class MethodBuilder<M extends Method> extends CheckTokenBuilder<Token, M>
        implements PredicateListTokenBuilder<Token>, ConstraintLastPredicateTokenBuilder<Token> {

    private final Predicate<? super Token> labels;

    protected MethodBuilder(final WktKeyword... labels) {
        Predicate<? super Token> l = labels[0];
        for (int i = 1; i < labels.length; i++) {
            l = l.or(labels[i]);
        }
        this.labels = l;
    }

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(labels,
                LeftDelimiter.class::isInstance,
                QuotedLatinText.QUOTED_LATIN_TEXT,
                RightDelimiter.INSTANCE_OF.or(SpecialSymbol.comma),
                Identifier.INSTANCE_OF,
                RightDelimiter.INSTANCE_OF);
    }

    @Override
    public Predicate<? super Token> constraintLast(final int currentIndex) {
        return currentIndex == 4 ? SpecialSymbol.comma : t -> true;
    }

    public static class MapProjectionMethodBuilder extends MethodBuilder<Method.MapProjectionMethod> {

        public MapProjectionMethodBuilder() {
            super(WktKeyword.METHOD, WktKeyword.PROJECTION);
        }

        @Override
        public Method.MapProjectionMethod build() {
            return new Method.MapProjectionMethod(first(), last(), index(), token(2), tokens(Identifier.INSTANCE_OF));
        }
    }

    public static class OperationMethodBuilder extends MethodBuilder<Method.OperationMethod> {

        public OperationMethodBuilder() {
            super(WktKeyword.METHOD);
        }

        @Override
        public Method.OperationMethod build() {
            return new Method.OperationMethod(first(), last(), index(), token(2), tokens(Identifier.INSTANCE_OF));
        }
    }
}
