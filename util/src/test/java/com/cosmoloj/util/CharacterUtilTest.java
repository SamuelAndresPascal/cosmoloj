package com.cosmoloj.util;

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
public class CharacterUtilTest {

    private static final List<Integer> FILE1_UNIX = List.of(
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 10,
            10);

    private static final List<Integer> FILE1_MAC = List.of(
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 13,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13,
            13);

    private static final List<Integer> FILE1_WIN = List.of(
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 13, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13, 10,
            13, 10);

    @Test
    public void example1_utf8_unix() throws URISyntaxException, IOException {
        final File file = new File(CharacterUtilTest.class.getResource("example1_utf8_unix.csv").toURI());

        Assertions.assertEquals(FILE1_UNIX, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));
    }

    @Test
    public void example1_utf8_mac() throws URISyntaxException, IOException {
        final File file = new File(CharacterUtilTest.class.getResource("example1_utf8_mac.csv").toURI());

        Assertions.assertEquals(FILE1_MAC, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));
    }

    @Test
    public void example1_utf8_win() throws URISyntaxException, IOException {
        final File file = new File(CharacterUtilTest.class.getResource("example1_utf8_win.csv").toURI());

        Assertions.assertEquals(FILE1_WIN, CharacterUtil.toCodePoints(file, StandardCharsets.UTF_8));
    }

    @Test
    public void codePoint() {
        final String a = "𝒳";
        Assertions.assertEquals(0x1d4b3, a.codePointAt(0));
        Assertions.assertEquals(0xdcb3, a.codePointAt(1));
        Assertions.assertEquals(2, a.length());
        Assertions.assertTrue(Character.isHighSurrogate(a.charAt(0)));
        Assertions.assertTrue(Character.isLowSurrogate(a.charAt(1)));

        final String b = "😂";
        Assertions.assertEquals(128514, b.codePointAt(0));
        Assertions.assertEquals(56834, b.codePointAt(1));
        Assertions.assertEquals(2, b.length());
        Assertions.assertTrue(Character.isHighSurrogate(b.charAt(0)));
        Assertions.assertTrue(Character.isLowSurrogate(b.charAt(1)));

        final String c = "𩷶";
        Assertions.assertEquals(171510, c.codePointAt(0));
        Assertions.assertEquals(56822, c.codePointAt(1));
        Assertions.assertEquals(2, c.length());
        Assertions.assertTrue(Character.isHighSurrogate(c.charAt(0)));
        Assertions.assertTrue(Character.isLowSurrogate(c.charAt(1)));

        final String d = "Ṩ";
        Assertions.assertEquals("S\u0307\u0323", d);
        Assertions.assertEquals('S', d.codePointAt(0));
    }
}
