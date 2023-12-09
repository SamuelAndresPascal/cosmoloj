package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.api.semantic.Token;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <I>
 */
public interface StateCheckTokenBuilder<I extends Token> {

    /**
     *
     * @return <span class="fr">validation du jeton courant</span>
     */
    boolean check();

    /**
     *
     * @return <span class="fr">liste des jetons déjà acceptés</span>
     */
    List<I> tokens();

    /**
     *
     * @return <span class="fr">jeton en cours de validation</span>
     */
    I current();

    default int size() {
        return tokens().size();
    }

    enum Parity {
        ODD, EVEN
    }

    default Parity parity() {
        return even() ? Parity.EVEN : Parity.ODD;
    }

    default boolean even() {
        return size() % 2 == 0;
    }

    default boolean odd() {
        return !even();
    }

    default boolean beyond(final int i) {
        return size() > i;
    }

    default boolean below(final int i) {
        return size() < i;
    }

    default int before(final int historyIndex) {
        return size() - historyIndex;
    }

    default boolean isEmpty() {
        return tokens().isEmpty();
    }

    default int first() {
        return tokens().get(0).first();
    }

    default int last() {
        return tokens().get(size() - 1).last();
    }

    default int index() {
        return tokens().get(size() - 1).order() + 1;
    }

    default <T extends I> T token(final int i) {
        return size() > i ? (T) tokens().get(i) : null;
    }

    default <T extends I> List<T> tokens(final Predicate<? super I> p) {
        return (List<T>) tokens().stream().filter(p).toList();
    }

    default <T extends I> T firstToken(final Predicate<? super I> p) {
        return (T) tokens().stream().filter(p).findFirst().orElse(null);
    }

    default boolean testToken(final int position, Predicate<? super I> predicate) {
        return predicate.test(tokens().get(position));
    }

    default boolean testLast(final Predicate<? super I> predicate) {
        return testPrevious(1, predicate);
    }

    default boolean testPrevious(final int positionBefore, final Predicate<? super I> predicate) {
        return predicate.test(tokens().get(size() - positionBefore));
    }

    default boolean current(final Predicate<? super I> p) {
        return p.test(current());
    }

    default boolean i(final int i, final Predicate<? super I> p) {
        return p.test(tokens().get(i));
    }

    default boolean previous(final int before, final Predicate<? super I> p) {
        return i(size() - before, p);
    }
}
