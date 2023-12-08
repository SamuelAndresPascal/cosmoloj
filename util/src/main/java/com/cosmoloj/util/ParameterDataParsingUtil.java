package com.cosmoloj.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public final class ParameterDataParsingUtil {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String COMMENT_PREFIX = "#";

    private ParameterDataParsingUtil() {
    }

    /**
     *
     * @param inputStream <span class="fr">flux d'entrée</span>
     * @param separatorRegexp <span class="fr">séparateur des champs dans un enregistrement</span>
     * @return
     * @throws IOException <span class="fr">en cas de problème de lecture</span>
     */
    public static Map<Double, Double> fillMap(final InputStream inputStream, final String separatorRegexp)
            throws IOException {

        final var tab = new HashMap<Double, Double>();

        try (var buff = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = buff.readLine()) != null) {
                if (!line.isEmpty() && !line.startsWith(COMMENT_PREFIX)) {
                    LOG.trace(line);
                    final String[] numbers = line.split(separatorRegexp);
                    tab.put(Double.valueOf(numbers[0]), Double.valueOf(numbers[1]));
                }
            }
        }
        return tab;
    }
}
