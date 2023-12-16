package com.cosmoloj.format.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class CsvReader extends CsvSource {

    // fichier csv
    private final File file;

    // encodage des caractères
    private final Charset charset;

    public CsvReader(final File file, final boolean header, final Charset charset, final int separator,
            final int leftDelimiter, final int rightDelimiter, final int escape, final boolean strictEscape) {
        super(header, separator, leftDelimiter, rightDelimiter, escape, strictEscape);
        this.file = file;
        this.charset = charset;
    }

    public CsvReader(final File file, final boolean header) {
        this(file, header, CsvUtil.DEFAULT_CHARSET, CsvUtil.DEFAULT_VALUE_SEPARATOR, CsvUtil.DEFAULT_VALUE_LEFT_LIMIT,
                CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);
    }

    public Charset getCharset() {
        return charset;
    }

    public List<String[]> read() throws IOException {

        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))) {

            final List<String[]> lines = new ArrayList<>();

            if (reader.ready()) {

                final String[] firstLine = readFirstLine(reader);
                lines.add(firstLine);
                final int nbCols = firstLine.length;

                while (reader.ready()) {
                    final String[] readLine = readLine(reader, nbCols);
                    if (readLine != null) {
                        lines.add(readLine);
                    }
                }

                return lines;
            } else {
                throw new IllegalStateException();
            }
        }
    }
}
