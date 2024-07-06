package com.cosmoloj.format.gr3df97a;

import com.cosmoloj.format.gr3df97a.ntg88.v1_0.Header;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.function.Consumer;

/**
 *
 * @author Samuel Andrés
 */
public final class Gr3df97aReader implements Closeable {

    private final BufferedReader buf;
    private final Header header;

    public Gr3df97aReader(final Path file) throws FileNotFoundException, IOException {
        this.buf = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile())));
        final String[] field = new String[4];
        for (int i = 0; i < field.length; i++) {
            field[i] = buf.readLine();
        }
        this.header = new Header(field[0], field[1], field[2], field[3]);
    }

    public Header getHeader() {
        return header;
    }

    public void read(final Consumer<String> consumer) throws IOException {
        String line;
        while ((line = buf.readLine()) != null) {
            consumer.accept(line);
        }
    }

    @Override
    public void close() throws IOException {
        buf.close();
    }
}
