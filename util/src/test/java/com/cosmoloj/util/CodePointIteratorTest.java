package com.cosmoloj.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class CodePointIteratorTest {

    private static final int[] FILE1_UNIX = new int[]{
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 10,
            10};

    private static final int[] FILE1_MAC = new int[]{
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 13,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13,
            13};

    private static final int[] FILE1_WIN = new int[]{
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 13, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13, 10,
            13, 10};

    @Test
    public void example1_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CharacterUtilTest.class.getResource("example1_utf8_unix.csv").toURI());

        try (CodePointIterator it = new CodePointIterator(file, StandardCharsets.UTF_8)) {

            int i = 0;
            while (it.hasNext()) {
                Assertions.assertEquals(FILE1_UNIX[i++], it.nextInt());
            }
            Assertions.assertEquals(FILE1_UNIX.length, i);
        }
    }

    @Test
    public void example1_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CharacterUtilTest.class.getResource("example1_utf8_mac.csv").toURI());

        try (CodePointIterator it = new CodePointIterator(file, StandardCharsets.UTF_8)) {

            int i = 0;
            while (it.hasNext()) {
                Assertions.assertEquals(FILE1_MAC[i++], it.nextInt());
            }
            Assertions.assertEquals(FILE1_MAC.length, i);
        }
    }

    @Test
    public void example1_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CharacterUtilTest.class.getResource("example1_utf8_win.csv").toURI());

        try (CodePointIterator it = new CodePointIterator(file, StandardCharsets.UTF_8)) {

            int i = 0;
            while (it.hasNext()) {
                Assertions.assertEquals(FILE1_WIN[i++], it.nextInt());
            }
            Assertions.assertEquals(FILE1_WIN.length, i);
        }
    }
}
