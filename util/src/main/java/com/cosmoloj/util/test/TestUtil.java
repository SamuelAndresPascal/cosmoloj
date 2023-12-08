package com.cosmoloj.util.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Samuel Andrés
 */
public final class TestUtil {

    private TestUtil() {
    }

    public static String resourceContent(final InputStream is, final String lineSepartor) throws IOException {
        final StringBuilder content = new StringBuilder();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
            String acc;
            while ((acc = r.readLine()) != null) {
                content.append(acc).append(lineSepartor);
            }
        }
        return content.toString();
    }
}
