package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintBeforeIndexPredicateTokenBuilder;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import com.cosmoloj.util.function.Predicates;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class GeographicCsBuilder extends CheckTokenBuilder<Token, GeographicCs>
        implements ConstraintBeforeIndexPredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate() {
        return switch (size()) {
            case 0 -> WktName.GEOGCS;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedName.class::isInstance;
            case 3, 5, 7 -> SpecialSymbol.COMMA;
            case 4 -> Datum.class::isInstance;
            case 6 -> PrimeMeridian.class::isInstance;
            case 8 -> Unit.class::isInstance;
            case 9 -> SpecialSymbol.COMMA.or(RightDelimiter.class::isInstance); // inutile
            case 10 -> pb(Unit.class, // unité angulaire verticale pour la compatibilité GOGCS 3D
                    Axis.class, Authority.class);
            case 12, 14 -> pb(Axis.class, Authority.class);
            case 16 -> Authority.class::isInstance;
            default -> {
                final int position = size();
                yield (position > 8 && position % 2 == 1)
                    ? SpecialSymbol.COMMA.or(RightDelimiter.class::isInstance) : Predicates.no();
            }
        };
//        return checkFor0(WktName.GEOGCS)
//                || checkFor(1, LEFTDELIMITER)
//                || checkFor(2, QUOTEDNAME)
//                || checkFor(3, COMMA)
//                || checkFor(4, DATUM)
//                || checkFor(5, COMMA)
//                || checkFor(6, PRIMERIDIAN)
//                || checkFor(7, COMMA)
//                || checkFor(8, UNIT)
//                // un GEOGCS 3D doit prévoir l'unité linéaire verticale
//                || checkFor(9, COMMA_OR_RIGHTDELIMITER)
//                // on continue uniquement si on n'a pas fermé l'expression au lexème précédent
//                || checkFor(10, UNIT, COMMA)
//                || checkFor(ODD_BEYOND_8, COMMA_OR_RIGHTDELIMITER, AXIS_OR_UNIT)
//                || checkFor(ODD_BEYOND_8, RIGHTDELIMITER, AUTHORITY)
//                || checkBeyond(8, AUTHORITY_OR_AXIS, COMMA, UNIT)// vérifier le seuil
//                || checkBeyond(8, AXIS, COMMA, AXIS, COMMA, AXIS.negate())// vérifier le seuil
//                || checkBeyond(8, AUTHORITY, COMMA, UNIT)// vérifier le seuil
//                || checkBeyond(8, AUTHORITY, COMMA, AXIS, COMMA, AXIS); // vérifier le seuil
    }

    @Override
    public Predicate<? super Token> constraintBeforeIndex(final int before, final int index) {
        return switch (before) {
            case 1 -> switch (index) {
                case 10, 14, 16 -> SpecialSymbol.COMMA;
                default -> Predicates.yes();
            };
            case 2 -> index == 14 ? Axis.class::isInstance : Predicates.yes();
            default -> Predicates.yes();
        };
    }

    @Override
    public GeographicCs build() {
        final boolean hasLinearUnit = size() > 10 && token(10) instanceof Unit;

        final int axisIndex;
        if (size() > 10 && token(10) instanceof Axis) {
            axisIndex = 10;
        } else if (size() > 12 && token(12) instanceof Axis) {
            axisIndex = 12;
        } else {
            axisIndex = -1;
        }

        final int authorityIndex;
        if (size() > 10 && token(10) instanceof Authority) {
            authorityIndex = 10;
        } else if (size() > 12 && token(12) instanceof Authority) {
            authorityIndex = 12;
        } else if (size() > 14 && token(14) instanceof Authority) {
            authorityIndex = 14;
        } else {
            authorityIndex = -1;
        }

        return new GeographicCs(first(), last(), index(), token(2), token(4),
                token(6), token(8),
                hasLinearUnit ? token(10) : null,
                axisIndex != -1 ? token(axisIndex) : null,
                axisIndex != -1 ? token(axisIndex + 2) : null,
                authorityIndex != -1 ? token(authorityIndex) : null);
    }
}
