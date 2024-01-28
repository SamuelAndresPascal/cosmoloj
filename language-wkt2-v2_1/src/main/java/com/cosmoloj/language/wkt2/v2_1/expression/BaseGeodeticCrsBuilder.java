package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import com.cosmoloj.util.function.Predicates;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class BaseGeodeticCrsBuilder extends CheckTokenBuilder<Token, BaseGeodeticCrs>
        implements PredicateListTokenBuilder<Token>, ConstraintLastPredicateTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(pb(WktKeyword.BASEGEODCRS, WktKeyword.GEOGCS, WktKeyword.GEOCCS),
                LeftDelimiter.class::isInstance,
                QuotedLatinText.class::isInstance,
                SpecialSymbol.COMMA,
                GeodeticDatum.class::isInstance,
                pb(RightDelimiter.class).or(SpecialSymbol.COMMA),
                Unit.Angle.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    @Override
    public Predicate<? super Token> constraintLast(final int index) {
        return index == 6 ? SpecialSymbol.COMMA : Predicates.yes();
    }

    @Override
    public BaseGeodeticCrs build() {
        return new BaseGeodeticCrs(first(), last(), index(), token(2), token(4),
                firstToken(Unit.Angle.class::isInstance));
    }
}
