package com.cosmoloj.format.csv;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 *
 * @author Samuel Andrés
 */
public class CsvStreamReader extends CsvSource implements Closeable {

    private final Reader reader;

    private boolean firstLine;
    private int nbCols;
    private boolean potentialEnd;

    public CsvStreamReader(final InputStream input, final boolean header, final Charset charset, final int separator,
            final int leftDelimiter, final int rightDelimiter, final int escape, final boolean strictEscape) {
        super(header, separator, leftDelimiter, rightDelimiter, escape, strictEscape);

        this.reader = new BufferedReader(new InputStreamReader(input, charset));
        firstLine = true;
        nbCols = -1;
        potentialEnd = false;
    }

    public String[] readLine() throws IOException {

        // pour la première ligne, on ne connaît pas encore le nombre de colonnes
        if (firstLine) {
            firstLine = false;
            final String[] line0 = readFirstLine(reader);
            nbCols = line0.length;
            return line0;
        } else {
            // on lit la ligne
            final String[] line = readLine(reader, nbCols);

            // si le résultat est null cela peut être dû aux fins de lignes windows, on tente donc une autre fois
            if (line == null) {
                if (potentialEnd) {
                    // deuxième ligne null consécutive : fin véritable
                    return null;
                } else {
                    // nouvelle tentative
                    potentialEnd = true;
                    return readLine();
                }
            } else {
                potentialEnd = false;
                return line;
            }

        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
