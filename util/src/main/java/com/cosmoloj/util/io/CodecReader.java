package com.cosmoloj.util.io;

import java.io.IOException;
import java.util.function.BiConsumer;

/**
 *
 * @author Samuel Andrés
 */
public interface CodecReader {

    void read(BiConsumer<byte[], int[]> consumer) throws IOException;
}
