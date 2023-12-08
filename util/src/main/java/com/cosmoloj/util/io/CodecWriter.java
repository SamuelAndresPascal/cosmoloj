package com.cosmoloj.util.io;

import java.io.IOException;
import java.util.function.Function;

/**
 *
 * @author Samuel Andrés
 */
public interface CodecWriter {

    void write(Function<int[], byte[]> function) throws IOException;
}
