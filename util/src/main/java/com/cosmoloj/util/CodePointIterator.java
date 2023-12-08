package com.cosmoloj.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.util.PrimitiveIterator;

/**
 *
 * @author Samuel Andrés
 */
public class CodePointIterator implements PrimitiveIterator.OfInt, Closeable {

    private final InputStreamReader isr;

    public CodePointIterator(final File file, final Charset charset) throws FileNotFoundException {
        this.isr = new InputStreamReader(new FileInputStream(file), charset);
    }

    public CodePointIterator(final InputStream is, final Charset charset) {
        this.isr = new InputStreamReader(is, charset);
    }

    @Override
    public boolean hasNext() {
        try {
            return isr.ready();
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public int nextInt() {
        try {
            return isr.read();
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public void close() throws IOException {
        isr.close();
    }
}
