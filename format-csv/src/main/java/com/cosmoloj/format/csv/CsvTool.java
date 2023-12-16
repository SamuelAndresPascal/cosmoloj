package com.cosmoloj.format.csv;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public final class CsvTool {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private CsvTool() {
    }

    public static void main(final String[] args) throws IOException {

        LOG.info("{}", Arrays.toString(args));

        final String path = args[0];
        final char separator = args[1].charAt(0);

        final long start = System.currentTimeMillis();
        try (var reader = new CsvStreamReader(
                Files.newInputStream(Paths.get(path)), true, StandardCharsets.UTF_8, separator, 0, 0, 0, true)) {

            int l = 0;
            String[] line;
            while ((line = reader.readLine()) != null) {
                System.out.println(Arrays.toString(line));
                l++;
            }
            System.out.println(l);
            System.out.println(System.currentTimeMillis() - start);
        }
    }
}
