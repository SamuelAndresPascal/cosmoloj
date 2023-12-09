package com.cosmoloj.language.common.impl.parsing;

import com.cosmoloj.language.api.parsing.Lexer;
import com.cosmoloj.language.api.parsing.Parser;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <div class="fr">
 * <p>Convention :
 * <ol>
 * <li>La règle générale est de rentrer aussitôt que possible dans une méthode ayant pour objet de produire un jeton.
 * Dans la circonstance où il est impossible de déterminer le jeton à construire ou la méthode à appeler sans construire
 * auparavant des jetons qui en feront partie, ceux-ci devront être fournis en paramètre de la méthode finalement
 * appelée pour construire le jeton résultat. <b>Tout jeton déjà lu ou construit faisant partie du jeton produit par une
 * méthode, doit être fourni en paramètre.</b>
 * <li>De même, la règle générale consiste à sortir d'une méthode de construction de jeton aussitôt construits les
 * jetons qui lui sont strictement nécessaires pour sa construction. Dans la circonstance où il est impossible de
 * déterminer le jeton à construire sans en constuire d'autres dont on ne sais pas s'ils en feront partie, les jetons
 * lus au-delà de la limite du jeton à construire doivent être retournés dans un tableau de jetons fourni en paramètre
 * et dont la taille doit être par avance adaptée. <b>Tout jeton lu ou construit par une méthode ne faisant pas partie
 * du jeton qu'elle produit doit être retourné dans un tableau de jeton de sorti fourni en paramètre.</b>
 * </ol>
 * </div>
 *
 * @author Samuel Andrés
 * @param <L>
 */
public abstract class AbstractParser<L extends Lexer> implements Parser<L> {

    private final L lexer;

    public AbstractParser(final L lexer) {
        this.lexer = lexer;
    }

    @Override
    public L getLexer() {
        return this.lexer;
    }

    /*##################################################################################################################
    STRUCTURE API -- STRUCTURE API -- STRUCTURE API -- STRUCTURE API -- STRUCTURE API -- STRUCTURE API -- STRUCTURE API
    ##################################################################################################################*/

    // STRUCTURE API ===================================================================================================

    // result := a ((p) f)*
    public static <S> S oneAndAppendWithPredicate(final Supplier<S> a, final Predicate<Void> p,
            final Function<S, S> f) {
        S result = a.get();
        while (p.test(null)) {
            result = f.apply(result);
        }
        return result;
    }

    // result := conditionalAction1 | conditionalAction2 | conditionalAction3 | ... defaultAction
    public static <S> S conditionalSupplier(final Supplier<S> defaultAction,
            final List<Map.Entry<Predicate<Void>, Supplier<S>>> conditionalActions) {
        for (final Map.Entry<Predicate<Void>, Supplier<S>> conditionalAction : conditionalActions) {
            if (conditionalAction.getKey().test(null)) {
                return conditionalAction.getValue().get();
            }
        }
        return defaultAction.get();
    }
}
