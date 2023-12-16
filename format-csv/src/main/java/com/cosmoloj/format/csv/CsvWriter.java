package com.cosmoloj.format.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 *
 * @author Samuel Andrés
 */
public class CsvWriter {

    private final File file;
    private final Charset charset;
    private final boolean header;
    private final int separator;
    private final int leftDelimiter;
    private final int rightDelimiter;
    private final int escape;
    private final String eol;

    public CsvWriter(final File file, final boolean header, final Charset charset, final int separator,
            final int leftDelimiter, final int rightDelimiter, final int escape, final String eol) {
        this.file = file;
        this.charset = charset;
        this.header = header;
        this.separator = separator;
        this.leftDelimiter = leftDelimiter;
        this.rightDelimiter = rightDelimiter;
        this.escape = escape;
        this.eol = eol;
    }

    public CsvWriter(final File file, final boolean header, final String eol) {
        this(file, header, CsvUtil.DEFAULT_CHARSET, CsvUtil.DEFAULT_VALUE_SEPARATOR, CsvUtil.DEFAULT_VALUE_LEFT_LIMIT,
                CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, eol);
    }

    public Charset getCharset() {
        return charset;
    }

    public boolean isHeader() {
        return header;
    }

    public int getSeparator() {
        return separator;
    }

    public int getLeftDelimiter() {
        return leftDelimiter;
    }

    public int getRightDelimiter() {
        return rightDelimiter;
    }

    public int getEscape() {
        return escape;
    }

    public void write(final Iterable<String[]> lines) throws IOException {

        try (var writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset))) {

            for (final String[] line : lines) {
                writeLine(writer, line);
            }
        }
    }

    private void writeLine(final Writer writer, final String[] line) throws IOException {

        for (int i = 0; i < line.length - 1; i++) {
            writeField(writer, line[i]);
            writer.write(separator);
        }
        writeField(writer, line[line.length - 1]);
        writer.write(eol);
    }

    private void writeField(final Writer writer, final String field) throws IOException {


        if (leftDelimiter > 0) {
            writer.write(leftDelimiter);
        }

        field.codePoints().forEachOrdered((value) -> {
            try {
                // on échappe le délimiteur de fin et le caractère d'échappement lui-même
                if (value == rightDelimiter || value == escape) {
                    writer.write(escape);
                }
                writer.write(value);

            } catch (final IOException ex) {
                throw new UncheckedIOException(ex);
            }
        });

        if (rightDelimiter > 0) {
            writer.write(rightDelimiter);
        }
    }
}
