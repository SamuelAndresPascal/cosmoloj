package com.cosmoloj.util.io;

import java.io.IOException;

/**
 *
 * @author Samuel Andrés
 * @param <E>
 */
public interface EntryStreamReader<E> {

    boolean hasNext();

    E readEntry() throws IOException;

}
