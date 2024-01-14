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
 */
public class BaseGeodeticCrsBuilder extends CheckTokenBuilder<Token, BaseGeodeticCrs>
        implements PredicateListTokenBuilder<Token>, ConstraintLastPredicateTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(WktKeyword.BASEGEODCRS.or(WktKeyword.GEOGCS).or(WktKeyword.GEOCCS),
                LeftDelimiter.class::isInstance,
                QuotedLatinText.QUOTED_LATIN_TEXT,
                SpecialSymbol.COMMA,
                GeodeticDatum.INSTANCE_OF,
                RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA),
                Unit.Angle.INSTANCE_OF_ANGLE,
                RightDelimiter.INSTANCE_OF);
    }

    @Override
    public Predicate<? super Token> constraintLast(final int index) {
        return index == 6 ? SpecialSymbol.COMMA : t -> true;
    }

    @Override
    public BaseGeodeticCrs build() {

        return new BaseGeodeticCrs(first(), last(), index(), token(2), token(4),
                firstToken(Unit.Angle.INSTANCE_OF_ANGLE));
    }
}