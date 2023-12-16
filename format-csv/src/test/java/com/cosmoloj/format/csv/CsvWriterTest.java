package com.cosmoloj.format.csv;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class CsvWriterTest {

    private static void test(final String resource, final int separator, final int leftLimiter, final int rightLimiter,
            final int escape, final String eol, final List<String[]> expected, final boolean strictEscape)
            throws IOException, URISyntaxException {

        // fichier à écrire
        final File fileW = File.createTempFile("csvWriterTest", ".csv");
        fileW.deleteOnExit();
        try {
            test(resource, separator, leftLimiter, rightLimiter, escape, eol, expected, fileW, strictEscape);
        } finally {
            if (fileW.exists()) {
                fileW.delete();
            }
        }
    }

    private static void test(final String resource, final int separator, final int leftLimiter, final int rightLimiter,
            final int escape, final String eol, final List<String[]> expected, final File out,
            final boolean strictEscape) throws IOException, URISyntaxException {

        final File file = new File(CsvReaderTest.class.getResource(resource).toURI());

        // lecture du fichier
        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, separator, leftLimiter, rightLimiter, escape, strictEscape);
        final List<String[]> records = reader.read();

        final CsvWriter writer
                = new CsvWriter(out, false, StandardCharsets.UTF_8, separator, leftLimiter, rightLimiter, escape, eol);


        // écriture
        writer.write(records);

        // contrôle en lecture du fichier écrit
        final CsvReader reader2 = new CsvReader(out,
            false, StandardCharsets.UTF_8, separator, leftLimiter, rightLimiter, escape, true);

        CsvTestData.test(reader2, expected);
    }

    @Test
    public void example1_utf8_unix() throws IOException, URISyntaxException {
        test("example1_utf8_unix.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR, 0, 0, 0, "\n", CsvTestData.EXAMPLE1, true);
    }

    @Test
    public void example1_utf8_mac() throws IOException, URISyntaxException {
        test("example1_utf8_mac.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR, 0, 0, 0, "\r", CsvTestData.EXAMPLE1, true);
    }

    @Test
    public void example1_utf8_win() throws IOException, URISyntaxException {
        test("example1_utf8_win.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR, 0, 0, 0, "\r\n", CsvTestData.EXAMPLE1, true);
    }


    @Test
    public void example2_utf8_unix() throws IOException, URISyntaxException {
        test("example2_utf8_unix.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\n",
                CsvTestData.EXAMPLE2, true);
    }

    @Test
    public void example2_utf8_mac() throws IOException, URISyntaxException {
        test("example2_utf8_mac.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\r",
                CsvTestData.EXAMPLE2, true);
    }

    @Test
    public void example2_utf8_win() throws IOException, URISyntaxException {
        test("example2_utf8_win.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\r\n",
                CsvTestData.EXAMPLE2, true);
    }


    @Test
    public void example3_utf8_unix() throws IOException, URISyntaxException {
        test("example3_utf8_unix.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\n",
                CsvTestData.EXAMPLE3, true);
    }

    @Test
    public void example3_utf8_mac() throws IOException, URISyntaxException {
        test("example3_utf8_mac.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\r",
                CsvTestData.EXAMPLE3, true);
    }

    @Test
    public void example3_utf8_win() throws IOException, URISyntaxException {
        test("example3_utf8_win.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\r\n",
                CsvTestData.EXAMPLE3, true);
    }


    @Test
    public void example5_utf8_unix() throws IOException, URISyntaxException {
        test("example5_utf8_unix.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\n",
                CsvTestData.EXAMPLE5, true);
    }

    @Test
    public void example5_utf8_mac() throws IOException, URISyntaxException {
        test("example5_utf8_mac.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\r",
                CsvTestData.EXAMPLE5, true);
    }

    @Test
    public void example5_utf8_win() throws IOException, URISyntaxException {
        test("example5_utf8_win.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\r\n",
                CsvTestData.EXAMPLE5, true);
    }


    @Test
    public void example6_utf8_unix() throws IOException, URISyntaxException {
        test("example6_utf8_unix.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\n",
                CsvTestData.EXAMPLE6_UNIX, true);
    }

    @Test
    public void example6_utf8_mac() throws IOException, URISyntaxException {
        test("example6_utf8_mac.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\r",
                CsvTestData.EXAMPLE6_MAC, true);
    }

    @Test
    public void example6_utf8_win() throws IOException, URISyntaxException {
        test("example6_utf8_win.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\r\n",
                CsvTestData.EXAMPLE6_WIN, true);
    }


    @Test
    public void example7_utf8_unix() throws IOException, URISyntaxException {
        test("example7_utf8_unix.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\n",
                CsvTestData.EXAMPLE7, true);
    }

    @Test
    public void example7_utf8_mac() throws IOException, URISyntaxException {
        test("example7_utf8_mac.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\r",
                CsvTestData.EXAMPLE7, true);
    }

    @Test
    public void example7_utf8_win() throws IOException, URISyntaxException {
        test("example7_utf8_win.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\r\n",
                CsvTestData.EXAMPLE7, true);
    }


    @Test
    public void example8_utf8_unix() throws IOException, URISyntaxException {
        test("example8_utf8_unix.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\n",
                CsvTestData.EXAMPLE8, true);
    }

    @Test // exemple 8, mais en changeant les délimiteurs
    public void example8_utf8_unix_extra() throws IOException, URISyntaxException {
        test("example8_utf8_unix_extra.csv", ';',
                '<', '>', '>', "\n", CsvTestData.EXAMPLE8, true);
    }

    @Test
    public void example8_utf8_mac() throws IOException, URISyntaxException {
        test("example8_utf8_mac.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\r",
                CsvTestData.EXAMPLE8, true);
    }

    @Test
    public void example8_utf8_win() throws IOException, URISyntaxException {
        test("example8_utf8_win.csv", CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, "\r\n",
                CsvTestData.EXAMPLE8, true);
    }


    @Test
    public void example9_utf8_unix() throws IOException, URISyntaxException {
        test("example9_utf8_unix.csv", ';', '<', '>', '>', "\n", CsvTestData.EXAMPLE9, true);
    }

    @Test
    public void example9_utf8_mac() throws IOException, URISyntaxException {
        test("example9_utf8_mac.csv", ';', '<', '>', '>', "\r", CsvTestData.EXAMPLE9, true);
    }

    @Test
    public void example9_utf8_win() throws IOException, URISyntaxException {
        test("example9_utf8_win.csv", ';', '<', '>', '>', "\r\n", CsvTestData.EXAMPLE9, true);
    }


    @Test
    public void example10_utf8_unix() throws IOException, URISyntaxException {
        test("example10_utf8_unix.csv", ';', '<', '>', '\\', "\n", CsvTestData.EXAMPLE10, true);
    }

    @Test
    public void example10_utf8_mac() throws IOException, URISyntaxException {
        test("example10_utf8_mac.csv", ';', '<', '>', '\\', "\r", CsvTestData.EXAMPLE10, true);
    }

    @Test
    public void example10_utf8_win() throws IOException, URISyntaxException {
        test("example10_utf8_win.csv", ';', '<', '>', '\\', "\r\n", CsvTestData.EXAMPLE10, true);
    }


    @Test
    public void example11_utf8_unix() throws IOException, URISyntaxException {
        test("example11_utf8_unix.csv", ';', '<', '>', '\\', "\n", CsvTestData.EXAMPLE11, false);
    }

    @Test
    public void example11_utf8_mac() throws IOException, URISyntaxException {
        test("example11_utf8_mac.csv", ';', '<', '>', '\\', "\r", CsvTestData.EXAMPLE11, false);
    }

    @Test
    public void example11_utf8_win() throws IOException, URISyntaxException {
        test("example11_utf8_win.csv", ';', '<', '>', '\\', "\r\n", CsvTestData.EXAMPLE11, false);
    }


    @Test
    public void example11b_utf8_unix() throws IOException, URISyntaxException {
        final IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class, () -> {
            test("example11_utf8_unix.csv", ';', '<', '>', '\\', "\n", CsvTestData.EXAMPLE11, true);
        });
        Assertions.assertEquals("""
                                the escape character (92) must only escape the right delimiter (62) or himself \
                                (encountered : 120)""", ex.getMessage());
    }

    @Test
    public void example11b_utf8_mac() throws IOException, URISyntaxException {
        final IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class, () -> {
            test("example11_utf8_mac.csv", ';', '<', '>', '\\', "\r", CsvTestData.EXAMPLE11, true);
        });
        Assertions.assertEquals("""
                                the escape character (92) must only escape the right delimiter (62) or himself \
                                (encountered : 120)""", ex.getMessage());
    }

    @Test
    public void example11b_utf8_win() throws IOException, URISyntaxException {
        final IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class, () -> {
            test("example11_utf8_win.csv", ';', '<', '>', '\\', "\r\n", CsvTestData.EXAMPLE11, true);
        });
        Assertions.assertEquals("""
                                the escape character (92) must only escape the right delimiter (62) or himself \
                                (encountered : 120)""", ex.getMessage());
    }
}
