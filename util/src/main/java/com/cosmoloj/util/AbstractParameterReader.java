package com.cosmoloj.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <div class="fr">Permet replir un tableau de parmètres à l'aide d'un fichier interprétant le type</div>
 *
 * @author Samuel Andrés
 * @param <T>
 */
public abstract class AbstractParameterReader<T> implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final BufferedReader buff;
    private final String separatorRegexp;
    private final String commentPrefix;

    protected AbstractParameterReader(final InputStream is) {
        this(is, ",");
    }

    protected AbstractParameterReader(final InputStream is, final String separatorRegexp) {
        this(is, separatorRegexp, "#");
    }

    protected AbstractParameterReader(final InputStream is, final String separatorRegexp, final String commentPrefix) {
        this.buff = new BufferedReader(new InputStreamReader(is));
        this.separatorRegexp = separatorRegexp;
        this.commentPrefix = commentPrefix;
    }

    public void fill(final T tab) throws IOException {

        int cpt = 0;
        String line;
        while ((line = buff.readLine()) != null) {
            if (!line.isEmpty() && !line.startsWith(this.commentPrefix)) {
                LOG.trace(line);
                cpt = handleLine(line.split(separatorRegexp), tab, cpt);
            }
        }
    }

    protected abstract int handleLine(String[] numbers, T tab, int firstIdx);

    @Override
    public void close() throws IOException {
        this.buff.close();
    }
}
