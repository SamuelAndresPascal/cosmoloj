package com.cosmoloj.util.io;

import java.io.IOException;

/**
 *
 * @author Samuel Andrés
 * @param <E>
 */
public interface EntryStreamWriter<E> {

    void writeEntry(E entry) throws IOException;

    default void write(final E... entries) throws IOException {
        for (final E e : entries) {
            writeEntry(e);
        }
    }

    default void write(final Iterable<E> entries) throws IOException {
        for (final E e : entries) {
            writeEntry(e);
        }
    }
}
