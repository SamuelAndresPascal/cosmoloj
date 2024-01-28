package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class ExtentBuilder extends CheckTokenBuilder<Token, Extent> implements PredicateListTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(pb(Area.class, BBox.class, VerticalExtent.class, TemporalExtent.class),
                SpecialSymbol.COMMA,
                pb(BBox.class, VerticalExtent.class, TemporalExtent.class),
                SpecialSymbol.COMMA,
                pb(VerticalExtent.class, TemporalExtent.class),
                SpecialSymbol.COMMA,
                pb(TemporalExtent.class));
    }

    @Override
    public Extent build() {
        if (size() == 1) {
            return token(0);
        } else {
            return new Extent.Coumpound(first(), last(), index(),
                    firstToken(Area.class::isInstance),
                    firstToken(BBox.class::isInstance),
                    firstToken(VerticalExtent.class::isInstance),
                    firstToken(TemporalExtent.class::isInstance));
        }
    }
}
