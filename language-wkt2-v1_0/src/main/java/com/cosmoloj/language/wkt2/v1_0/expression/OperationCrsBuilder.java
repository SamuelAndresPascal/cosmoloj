package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <E>
 */
public abstract class OperationCrsBuilder<E extends OperationCrs> extends CheckTokenBuilder<Token, E>
        implements PredicateListTokenBuilder<Token> {

    private final WktKeyword tag;

    public OperationCrsBuilder(final WktKeyword tag) {
        this.tag = tag;
    }

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(this.tag,
                LeftDelimiter.class::isInstance,
                Crs.CRS,
                RightDelimiter.class::isInstance);
    }

    public static OperationCrsBuilder<OperationCrs.SourceCrs> source() {
        return new OperationCrsBuilder<OperationCrs.SourceCrs>(WktKeyword.SOURCECRS) {

            @Override
            public OperationCrs.SourceCrs build() {
                return new OperationCrs.SourceCrs(first(), last(), index(), (Crs) token(2));
            }
        };
    }

    public static OperationCrsBuilder<OperationCrs.TargetCrs> target() {
        return new OperationCrsBuilder<OperationCrs.TargetCrs>(WktKeyword.TARGETCRS) {

            @Override
            public OperationCrs.TargetCrs build() {
                return new OperationCrs.TargetCrs(first(), last(), index(), (Crs) token(2));
            }
        };
    }

    public static OperationCrsBuilder<OperationCrs.InterpolationCrs> interpolation() {
        return new OperationCrsBuilder<OperationCrs.InterpolationCrs>(WktKeyword.INTERPOLATIONCRS) {

            @Override
            public OperationCrs.InterpolationCrs build() {
                return new OperationCrs.InterpolationCrs(first(), last(), index(), (Crs) token(2));
            }
        };
    }
}
