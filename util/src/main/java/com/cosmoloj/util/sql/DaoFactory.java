package com.cosmoloj.util.sql;

import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author Samuel Andrés
 */
public interface DaoFactory {

    String type(Class<?> type);

    List<Object[]> project(Class<?> type, boolean withHeader, String... others) throws DaoFactoryException;

    <R> List<R> where(Class<R> type, Predicate... clauses) throws DaoFactoryException;

    default <R> List<R> where(final Class<R> type, final String field, final Object value) throws DaoFactoryException {
        return where(type, new Predicate(field, Operator.EQ, value));
    }

    static List<String> getIdFields(final Class<?> type) {
        return Stream.of(type.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Col.class) && field.getAnnotation(Col.class).pkMember())
                .map(f -> f.getAnnotation(Col.class).value().isEmpty()
                        ? f.getName() : f.getAnnotation(Col.class).value())
                .toList();
    }

    static List<String> getFields(final Class<?> type) {
        return Stream.of(type.getDeclaredFields())
                .map(f -> f.isAnnotationPresent(Col.class) && f.getAnnotation(Col.class).value().isEmpty()
                            ? f.getName() : f.getAnnotation(Col.class).value())
                .toList();
    }
}
