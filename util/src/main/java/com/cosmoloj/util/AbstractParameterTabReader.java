package com.cosmoloj.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 * @param <T>
 */
public abstract class AbstractParameterTabReader<T> implements Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final BufferedReader buff;
    private final String separatorRegexp;
    private final String commentPrefix;
    private final IntFunction<T> tabSupplier;
    private int cpt = 0;

    protected AbstractParameterTabReader(final InputStream is, final IntFunction<T> tabSupplier) {
        this(is, tabSupplier, ",");
    }

    protected AbstractParameterTabReader(final InputStream is, final IntFunction<T> tabSupplier,
            final String separatorRegexp) {
        this(is, tabSupplier, separatorRegexp, "#");
    }

    protected AbstractParameterTabReader(final InputStream is, final IntFunction<T> tabSupplier,
            final String separatorRegexp, final String commentPrefix) {
        this.buff = new BufferedReader(new InputStreamReader(is));
        this.separatorRegexp = separatorRegexp;
        this.commentPrefix = commentPrefix;
        this.tabSupplier = tabSupplier;
    }

    /**
     *
     * @param tab <span class="fr">tableau de tableaux à remplir</span>
     * @param fillLineSupplier <span class="fr">interprétation des éléments</span>
     * @throws IOException <span class="fr">en cas de problème de lecture</span>
     */
    public void fill(final T[] tab, final Supplier<BiConsumer<String[], T>> fillLineSupplier) throws IOException {

        final var fillLine = fillLineSupplier.get();

        String line;
        while ((line = buff.readLine()) != null && cpt < tab.length) {
            if (!line.isEmpty() && !line.startsWith(this.commentPrefix)) {
                LOGGER.trace(line);
                final String[] numbers = line.split(separatorRegexp);
                tab[cpt] = tabSupplier.apply(numbers.length);
                fillLine.accept(numbers, tab[cpt]);
                cpt++;
            }
        }
        cpt = 0;
    }

    @Override
    public void close() throws IOException {
        this.buff.close();
    }
}
