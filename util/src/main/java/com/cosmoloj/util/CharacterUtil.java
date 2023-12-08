package com.cosmoloj.util;

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
public final class CharacterUtil {

    private CharacterUtil() {
    }

    public static List<Integer> toCodePoints(final File file, final Charset charset) throws IOException {

        try (var isr = new InputStreamReader(new FileInputStream(file), charset)) {

            final var result = new ArrayList<Integer>();

            int cp = 0;
            while ((cp = isr.read()) != -1) {
                result.add(cp);
            }
            return result;
        }
    }
}
