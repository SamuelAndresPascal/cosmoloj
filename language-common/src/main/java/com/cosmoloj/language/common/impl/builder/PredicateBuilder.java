package com.cosmoloj.language.common.impl.builder;

import com.cosmoloj.language.common.impl.semantic.SemanticEnum;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public final class PredicateBuilder implements Predicate<Object> {

    private Predicate<Object> predicate;

    PredicateBuilder(final Class<?> c) {
        this.predicate = c::isInstance;
    }

    PredicateBuilder(final SemanticEnum<?> e) {
        this.predicate = e;
    }

    public PredicateBuilder and(final Class<?> c) {
        this.predicate = this.predicate.and(c::isInstance);
        return this;
    }

    public PredicateBuilder and(final SemanticEnum<?> e) {
        this.predicate = this.predicate.and(e);
        return this;
    }

    public PredicateBuilder or(final Class<?> c) {
        this.predicate = this.predicate.or(c::isInstance);
        return this;
    }

    public PredicateBuilder or(final SemanticEnum<?> e) {
        this.predicate = this.predicate.or(e);
        return this;
    }

    public PredicateBuilder or(final Class<?>... c) {
        for (final var e : c) {
            this.or(e);
        }
        return this;
    }

    public PredicateBuilder or(final SemanticEnum<?>... e) {
        for (final var l : e) {
            this.or(l);
        }
        return this;
    }

    public Predicate<Object> build() {
        return this.predicate;
    }

    public static PredicateBuilder of(final Class<?> c) {
        return new PredicateBuilder(c);
    }

    public static PredicateBuilder of(final SemanticEnum<?> e) {
        return new PredicateBuilder(e);
    }

    @Override
    public boolean test(final Object t) {
        return this.predicate.test(t);
    }
}
