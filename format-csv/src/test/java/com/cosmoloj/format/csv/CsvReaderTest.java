package com.cosmoloj.format.csv;

import com.cosmoloj.util.CharacterUtil;
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
public class CsvReaderTest {


    @Test
    public void example1_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example1_utf8_unix.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE1_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR, 0, 0, 0, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE1);
    }

    @Test
    public void example1_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example1_utf8_mac.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE1_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR, 0, 0, 0, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE1);
    }

    @Test
    public void example1_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example1_utf8_win.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE1_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR, 0, 0, 0, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE1);
    }

    @Test
    public void example2_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example2_utf8_unix.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE2_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE2);
    }

    @Test
    public void example2_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example2_utf8_mac.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE2_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE2);
    }

    @Test
    public void example2_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example2_utf8_win.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE2_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE2);
    }

    @Test
    public void example3_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example3_utf8_unix.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE3_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE3);
    }

    @Test
    public void example3_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example3_utf8_mac.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE3_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE3);
    }

    @Test
    public void example3_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example3_utf8_win.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE3_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE3);
    }

    @Test
    public void example5_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example5_utf8_unix.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE5_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE5);
    }

    @Test
    public void example5_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example5_utf8_mac.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE5_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE5);
    }

    @Test
    public void example5_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example5_utf8_win.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE5_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE5);
    }

    @Test
    public void example6_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example6_utf8_unix.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE6_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE6_UNIX);
    }

    @Test
    public void example6_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example6_utf8_mac.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE6_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE6_MAC);
    }

    @Test
    public void example6_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example6_utf8_win.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE6_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE6_WIN);
    }

    @Test
    public void example7_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example7_utf8_unix.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE7_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE7);
    }

    @Test
    public void example7_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example7_utf8_mac.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE7_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE7);
    }

    @Test
    public void example7_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example7_utf8_win.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE7_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE7);
    }

    @Test
    public void example8_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example8_utf8_unix.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE8_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE8);
    }

    @Test
    public void example8_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example8_utf8_mac.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE8_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE8);
    }

    @Test
    public void example8_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example8_utf8_win.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE8_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, CsvUtil.DEFAULT_VALUE_SEPARATOR,
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE8);
    }

    @Test
    public void example9_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example9_utf8_unix.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE9_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, ';', '<', '>', '>', true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE9);
    }

    @Test
    public void example9_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example9_utf8_mac.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE9_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, ';', '<', '>', '>', true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE9);
    }

    @Test
    public void example9_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example9_utf8_win.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE9_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, ';', '<', '>', '>', true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE9);
    }

    @Test
    public void example10_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example10_utf8_unix.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE10_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, ';', '<', '>', '\\', true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE10);
    }

    @Test
    public void example10_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example10_utf8_mac.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE10_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, ';', '<', '>', '\\', true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE10);
    }

    @Test
    public void example10_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example10_utf8_win.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE10_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, ';', '<', '>', '\\', true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE10);
    }

    @Test
    public void example11a_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example11_utf8_unix.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE11_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, ';', '<', '>', '\\', false);

        CsvTestData.test(reader, CsvTestData.EXAMPLE11);
    }

    @Test
    public void example11a_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example11_utf8_mac.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE11_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, ';', '<', '>', '\\', false);

        CsvTestData.test(reader, CsvTestData.EXAMPLE11);
    }

    @Test
    public void example11a_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example11_utf8_win.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE11_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, ';', '<', '>', '\\', false);

        CsvTestData.test(reader, CsvTestData.EXAMPLE11);
    }

    @Test
    public void example11b_utf8_unix() throws URISyntaxException, IOException {

        final IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class, () -> {
            final File file = new File(CsvReaderTest.class.getResource("example11_utf8_unix.csv").toURI());

            Assertions.assertEquals(CsvTestData.FILE11_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

            final CsvReader reader = new CsvReader(file,
                    false, StandardCharsets.UTF_8, ';', '<', '>', '\\', true);

            CsvTestData.test(reader, CsvTestData.EXAMPLE11);
        });
        Assertions.assertEquals("""
                                the escape character (92) must only escape the right delimiter (62) or himself \
                                (encountered : 120)""", ex.getMessage());
    }

    @Test
    public void example11b_utf8_mac() throws URISyntaxException, IOException {

        final IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class, () -> {
            final File file = new File(CsvReaderTest.class.getResource("example11_utf8_mac.csv").toURI());

            Assertions.assertEquals(CsvTestData.FILE11_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

            final CsvReader reader = new CsvReader(file,
                    false, StandardCharsets.UTF_8, ';', '<', '>', '\\', true);

            CsvTestData.test(reader, CsvTestData.EXAMPLE11);
        });
        Assertions.assertEquals("""
                                the escape character (92) must only escape the right delimiter (62) or himself \
                                (encountered : 120)""", ex.getMessage());
    }

    @Test
    public void example11b_utf8_win() throws URISyntaxException, IOException {

        final IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class, () -> {
            final File file = new File(CsvReaderTest.class.getResource("example11_utf8_win.csv").toURI());

            Assertions.assertEquals(CsvTestData.FILE11_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

            final CsvReader reader = new CsvReader(file,
                    false, StandardCharsets.UTF_8, ';', '<', '>', '\\', true);

            CsvTestData.test(reader, CsvTestData.EXAMPLE11);
        });
        Assertions.assertEquals("""
                                the escape character (92) must only escape the right delimiter (62) or himself \
                                (encountered : 120)""", ex.getMessage());
    }

    @Test
    public void example12_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example12_utf8_unix.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE12_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, ';', '<', '>', '\\', false);

        CsvTestData.test(reader, CsvTestData.EXAMPLE12);
    }

    @Test
    public void example12_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example12_utf8_mac.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE12_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, ';', '<', '>', '\\', false);

        CsvTestData.test(reader, CsvTestData.EXAMPLE12);
    }

    @Test
    public void example12_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example12_utf8_win.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE12_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, ';', '<', '>', '\\', false);

        CsvTestData.test(reader, CsvTestData.EXAMPLE12);
    }

    @Test
    public void example13_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example13_utf8_unix.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE13_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, '€',
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE13);
    }

    @Test
    public void example13_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example13_utf8_mac.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE13_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, '€',
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE13);
    }

    @Test
    public void example13_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CsvReaderTest.class.getResource("example13_utf8_win.csv").toURI());

        Assertions.assertEquals(CsvTestData.FILE13_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));

        final CsvReader reader = new CsvReader(file,
                false, StandardCharsets.UTF_8, '€',
                CsvUtil.DEFAULT_VALUE_LEFT_LIMIT, CsvUtil.DEFAULT_VALUE_RIGHT_LIMIT, CsvUtil.DEFAULT_ESCAPE, true);

        CsvTestData.test(reader, CsvTestData.EXAMPLE13);
    }
}
