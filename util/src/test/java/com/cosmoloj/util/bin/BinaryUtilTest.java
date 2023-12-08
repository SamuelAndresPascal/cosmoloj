package com.cosmoloj.util.bin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class BinaryUtilTest {

    @Test
    public void bytesFor() {
        Assertions.assertEquals(0, BinaryUtil.bytesFor(0));

        Assertions.assertEquals(1, BinaryUtil.bytesFor(1));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(2));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(3));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(4));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(5));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(6));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(7));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(8));

        Assertions.assertEquals(2, BinaryUtil.bytesFor(9));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(10));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(11));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(12));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(13));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(14));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(15));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(16));

        Assertions.assertEquals(3, BinaryUtil.bytesFor(17));
        Assertions.assertEquals(3, BinaryUtil.bytesFor(18));
        Assertions.assertEquals(3, BinaryUtil.bytesFor(19));
    }

    @Test
    public void bytesForOffset() {
        Assertions.assertEquals(0, BinaryUtil.bytesFor(0, 0));

        Assertions.assertEquals(1, BinaryUtil.bytesFor(1, 0));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(2, 0));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(3, 0));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(4, 0));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(5, 0));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(6, 0));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(7, 0));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(8, 0));

        Assertions.assertEquals(2, BinaryUtil.bytesFor(9, 0));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(10, 0));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(11, 0));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(12, 0));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(13, 0));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(14, 0));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(15, 0));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(16, 0));

        Assertions.assertEquals(3, BinaryUtil.bytesFor(17, 0));
        Assertions.assertEquals(3, BinaryUtil.bytesFor(18, 0));
        Assertions.assertEquals(3, BinaryUtil.bytesFor(19, 0));

        Assertions.assertEquals(1, BinaryUtil.bytesFor(0, 1));

        Assertions.assertEquals(1, BinaryUtil.bytesFor(1, 1));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(2, 1));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(3, 1));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(4, 1));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(5, 1));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(6, 1));
        Assertions.assertEquals(1, BinaryUtil.bytesFor(7, 1));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(8, 1));

        Assertions.assertEquals(2, BinaryUtil.bytesFor(9, 1));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(10, 1));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(11, 1));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(12, 1));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(13, 1));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(14, 1));
        Assertions.assertEquals(2, BinaryUtil.bytesFor(15, 1));
        Assertions.assertEquals(3, BinaryUtil.bytesFor(16, 1));

        Assertions.assertEquals(3, BinaryUtil.bytesFor(17, 1));
        Assertions.assertEquals(3, BinaryUtil.bytesFor(18, 1));
        Assertions.assertEquals(3, BinaryUtil.bytesFor(19, 1));
    }

    @Test
    public void sizedUnitsForAmount() {

        // pour stocker 0 il faut toujours 0
        Assertions.assertEquals(0, BinaryUtil.sizedUnitsForAmount(0, 0));
        Assertions.assertEquals(0, BinaryUtil.sizedUnitsForAmount(0, 1));
        Assertions.assertEquals(0, BinaryUtil.sizedUnitsForAmount(0, 2));
        Assertions.assertEquals(0, BinaryUtil.sizedUnitsForAmount(0, 3));
        Assertions.assertEquals(0, BinaryUtil.sizedUnitsForAmount(0, 4));
        Assertions.assertEquals(0, BinaryUtil.sizedUnitsForAmount(0, 5));
        Assertions.assertEquals(0, BinaryUtil.sizedUnitsForAmount(0, 6));
        Assertions.assertEquals(0, BinaryUtil.sizedUnitsForAmount(0, 7));
        Assertions.assertEquals(0, BinaryUtil.sizedUnitsForAmount(0, 8));
        Assertions.assertEquals(0, BinaryUtil.sizedUnitsForAmount(0, 9));
        Assertions.assertEquals(0, BinaryUtil.sizedUnitsForAmount(0, 10));
        Assertions.assertEquals(0, BinaryUtil.sizedUnitsForAmount(0, 0));

        // une quantité non nulle ne peut être contenue dans des unités de taille nulle
        Assertions.assertEquals("/ by zero",
                Assertions.assertThrows(ArithmeticException.class, () -> BinaryUtil.sizedUnitsForAmount(1, 0))
                        .getMessage());

        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(1, 1));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(1, 2));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(1, 3));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(1, 4));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(1, 5));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(1, 6));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(1, 7));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(1, 8));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(1, 9));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(1, 10));

        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(2, 1));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(2, 2));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(2, 3));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(2, 4));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(2, 5));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(2, 6));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(2, 7));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(2, 8));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(2, 9));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(2, 10));

        Assertions.assertEquals(3, BinaryUtil.sizedUnitsForAmount(3, 1));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(3, 2));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(3, 3));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(3, 4));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(3, 5));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(3, 6));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(3, 7));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(3, 8));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(3, 9));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(3, 10));

        Assertions.assertEquals(4, BinaryUtil.sizedUnitsForAmount(4, 1));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(4, 2));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(4, 3));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(4, 4));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(4, 5));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(4, 6));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(4, 7));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(4, 8));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(4, 9));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(4, 10));

        Assertions.assertEquals(5, BinaryUtil.sizedUnitsForAmount(5, 1));
        Assertions.assertEquals(3, BinaryUtil.sizedUnitsForAmount(5, 2));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(5, 3));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(5, 4));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(5, 5));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(5, 6));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(5, 7));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(5, 8));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(5, 9));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(5, 10));

        Assertions.assertEquals(6, BinaryUtil.sizedUnitsForAmount(6, 1));
        Assertions.assertEquals(3, BinaryUtil.sizedUnitsForAmount(6, 2));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(6, 3));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(6, 4));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(6, 5));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(6, 6));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(6, 7));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(6, 8));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(6, 9));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(6, 10));

        Assertions.assertEquals(7, BinaryUtil.sizedUnitsForAmount(7, 1));
        Assertions.assertEquals(4, BinaryUtil.sizedUnitsForAmount(7, 2));
        Assertions.assertEquals(3, BinaryUtil.sizedUnitsForAmount(7, 3));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(7, 4));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(7, 5));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(7, 6));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(7, 7));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(7, 8));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(7, 9));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(7, 10));

        Assertions.assertEquals(8, BinaryUtil.sizedUnitsForAmount(8, 1));
        Assertions.assertEquals(4, BinaryUtil.sizedUnitsForAmount(8, 2));
        Assertions.assertEquals(3, BinaryUtil.sizedUnitsForAmount(8, 3));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(8, 4));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(8, 5));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(8, 6));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(8, 7));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(8, 8));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(8, 9));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(8, 10));

        Assertions.assertEquals(9, BinaryUtil.sizedUnitsForAmount(9, 1));
        Assertions.assertEquals(5, BinaryUtil.sizedUnitsForAmount(9, 2));
        Assertions.assertEquals(3, BinaryUtil.sizedUnitsForAmount(9, 3));
        Assertions.assertEquals(3, BinaryUtil.sizedUnitsForAmount(9, 4));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(9, 5));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(9, 6));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(9, 7));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(9, 8));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(9, 9));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(9, 10));

        Assertions.assertEquals(10, BinaryUtil.sizedUnitsForAmount(10, 1));
        Assertions.assertEquals(5, BinaryUtil.sizedUnitsForAmount(10, 2));
        Assertions.assertEquals(4, BinaryUtil.sizedUnitsForAmount(10, 3));
        Assertions.assertEquals(3, BinaryUtil.sizedUnitsForAmount(10, 4));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(10, 5));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(10, 6));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(10, 7));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(10, 8));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(10, 9));
        Assertions.assertEquals(1, BinaryUtil.sizedUnitsForAmount(10, 10));

        Assertions.assertEquals(11, BinaryUtil.sizedUnitsForAmount(11, 1));
        Assertions.assertEquals(6, BinaryUtil.sizedUnitsForAmount(11, 2));
        Assertions.assertEquals(4, BinaryUtil.sizedUnitsForAmount(11, 3));
        Assertions.assertEquals(3, BinaryUtil.sizedUnitsForAmount(11, 4));
        Assertions.assertEquals(3, BinaryUtil.sizedUnitsForAmount(11, 5));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(11, 6));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(11, 7));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(11, 8));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(11, 9));
        Assertions.assertEquals(2, BinaryUtil.sizedUnitsForAmount(11, 10));
    }

    @Test
    public void compress_test1() {

        final byte[] in = new byte[]{2, 2};

        final byte[] out = BinaryUtil.compand(in, 2);

        final byte[] expected = new byte[]{10};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void compress_test1_1() {

        final byte[] in = new byte[]{2, 3};

        final byte[] out = BinaryUtil.compand(in, 2);

        final byte[] expected = new byte[]{14};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void compress_test1_2() {

        final byte[] in = new byte[]{3, 2};

        final byte[] out = BinaryUtil.compand(in, 2);

        final byte[] expected = new byte[]{11};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void compress_test2() {

        final byte[] in = new byte[]{2, 2, 1};

        final byte[] out = BinaryUtil.compand(in, 2);

        final byte[] expected = new byte[]{26};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void compress_test3() {

        final byte[] in = new byte[]{2, 2, 1, 3};

        final byte[] out = BinaryUtil.compand(in, 2);

        final byte[] expected = new byte[]{-38};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void compress_test4() {

        final byte[] in = new byte[]{2, 2, 1, 3, 2, 2, 1};

        final byte[] out = BinaryUtil.compand(in, 2);

        final byte[] expected = new byte[]{-38, 26};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void compress_test5() {

        final byte[] in = new byte[]{7, 7, 7, 7};

        final byte[] out = BinaryUtil.compand(in, 3);

        final byte[] expected = new byte[]{-1, 15};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void compress_2_test1() {

        final byte[] in = new byte[]{2, 2};

        final int[] oldSizes = new int[2];
        Arrays.fill(oldSizes, 2);

        final byte[] out = BinaryUtil.compand(in, oldSizes);

        final byte[] expected = new byte[]{10};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void compress_2_test2() {

        final byte[] in = new byte[]{2, 2, 1};

        final int[] oldSizes = new int[3];
        Arrays.fill(oldSizes, 2);

        final byte[] out = BinaryUtil.compand(in, oldSizes);

        final byte[] expected = new byte[]{26};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void compress_2_test3() {

        final byte[] in = new byte[]{2, 2, 1, 3};

        final int[] oldSizes = new int[4];
        Arrays.fill(oldSizes, 2);

        final byte[] out = BinaryUtil.compand(in, oldSizes);

        final byte[] expected = new byte[]{-38};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void compress_2_test4() {

        final byte[] in = new byte[]{2, 2, 1, 3, 2, 2, 1};

        final int[] oldSizes = new int[7];
        Arrays.fill(oldSizes, 2);

        final byte[] out = BinaryUtil.compand(in, oldSizes);

        final byte[] expected = new byte[]{-38, 26};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void compress_2_test5() {

        final byte[] in = new byte[]{7, 7, 7, 7};

        final int[] oldSizes = new int[4];
        Arrays.fill(oldSizes, 3);

        final byte[] out = BinaryUtil.compand(in, oldSizes);

        final byte[] expected = new byte[]{-1, 15};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void compress_2_test6() {

        final byte[] in = new byte[]{31, 31, 31, 1};

        final byte[] out1 = BinaryUtil.compand(in, 5);

        final byte[] expected = new byte[]{-1, -1, 0};

        Assertions.assertArrayEquals(expected, out1);

        final int[] oldSizes = new int[4];
        Arrays.fill(oldSizes, 5);

        final byte[] out2 = BinaryUtil.compand(in, oldSizes);

        Assertions.assertArrayEquals(expected, out2);
    }

    @Test
    public void compress_2_test7() {

        final byte[] in = new byte[]{31, 31, 31, 1};

        final int[] oldSizes = new int[]{5, 5, 5, 1};

        final byte[] out = BinaryUtil.compand(in, oldSizes);

        final byte[] expected = new byte[]{-1, -1};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void compress_2_test8() {

        final byte[] in = new byte[]{0, 31, 0, 0};

        final int[] oldSizes = new int[]{5, 5, 5, 1};

        final byte[] out = BinaryUtil.compand(in, oldSizes);

        final byte[] expected = new byte[]{-32, 3};

        Assertions.assertArrayEquals(expected, out);
    }

    @Test
    public void integerBinaryString() {

        // application de la méthode standard
        Assertions.assertEquals("10000000", Integer.toBinaryString(128));
        Assertions.assertEquals("11111111111111111111111110000000", Integer.toBinaryString(-128));
        Assertions.assertEquals("0", Integer.toBinaryString(0));
        Assertions.assertEquals("1", Integer.toBinaryString(1));
        Assertions.assertEquals("10", Integer.toBinaryString(2));
        Assertions.assertEquals("11", Integer.toBinaryString(3));
        Assertions.assertEquals("100", Integer.toBinaryString(4));
        Assertions.assertEquals("11111111111111111111111111111111", Integer.toBinaryString(-1));
        Assertions.assertEquals("110", Integer.toBinaryString(6));
        Assertions.assertEquals("1110000", Integer.toBinaryString(112));
        Assertions.assertEquals("110001", Integer.toBinaryString(49));
        Assertions.assertEquals("11000", Integer.toBinaryString(24));
        Assertions.assertEquals("11111111111111111111111111100100", Integer.toBinaryString(-28));
        Assertions.assertEquals("10", Integer.toBinaryString(2));
        Assertions.assertEquals("11111111111111111111111111000000", Integer.toBinaryString(-64));
        Assertions.assertEquals("11111111111111111111111110000001", Integer.toBinaryString(-127));

        Assertions.assertEquals("11000000", Integer.toBinaryString(192));

        // application de la méthode à 32 caractères
        Assertions.assertEquals("00000000000000000000000010000000", BinaryUtil.toBinaryString(128));
        Assertions.assertEquals("11111111111111111111111110000000", BinaryUtil.toBinaryString(-128));
        Assertions.assertEquals("00000000000000000000000000000000", BinaryUtil.toBinaryString(0));
        Assertions.assertEquals("00000000000000000000000000000001", BinaryUtil.toBinaryString(1));
        Assertions.assertEquals("00000000000000000000000000000010", BinaryUtil.toBinaryString(2));
        Assertions.assertEquals("00000000000000000000000000000011", BinaryUtil.toBinaryString(3));
        Assertions.assertEquals("00000000000000000000000000000100", BinaryUtil.toBinaryString(4));
        Assertions.assertEquals("11111111111111111111111111111111", BinaryUtil.toBinaryString(-1));
        Assertions.assertEquals("00000000000000000000000000000110", BinaryUtil.toBinaryString(6));
        Assertions.assertEquals("00000000000000000000000001110000", BinaryUtil.toBinaryString(112));
        Assertions.assertEquals("00000000000000000000000000110001", BinaryUtil.toBinaryString(49));
        Assertions.assertEquals("00000000000000000000000000011000", BinaryUtil.toBinaryString(24));
        Assertions.assertEquals("11111111111111111111111111100100", BinaryUtil.toBinaryString(-28));
        Assertions.assertEquals("00000000000000000000000000000010", BinaryUtil.toBinaryString(2));
        Assertions.assertEquals("11111111111111111111111111000000", BinaryUtil.toBinaryString(-64));
        Assertions.assertEquals("11111111111111111111111110000001", BinaryUtil.toBinaryString(-127));

        Assertions.assertEquals("00000000000000000000000011000000", BinaryUtil.toBinaryString(192));
    }

    @Test
    public void byteBinaryString() {
        Assertions.assertEquals("10000000", BinaryUtil.toBinaryString((byte) 128));
        Assertions.assertEquals("10000000", BinaryUtil.toBinaryString((byte) -128));
        Assertions.assertEquals("00000000", BinaryUtil.toBinaryString((byte) 0));
        Assertions.assertEquals("00000001", BinaryUtil.toBinaryString((byte) 1));
        Assertions.assertEquals("00000010", BinaryUtil.toBinaryString((byte) 2));
        Assertions.assertEquals("00000011", BinaryUtil.toBinaryString((byte) 3));
        Assertions.assertEquals("00000100", BinaryUtil.toBinaryString((byte) 4));
        Assertions.assertEquals("11111111", BinaryUtil.toBinaryString((byte) -1));
        Assertions.assertEquals("00000110", BinaryUtil.toBinaryString((byte) 6));
        Assertions.assertEquals("01110000", BinaryUtil.toBinaryString((byte) 112));
        Assertions.assertEquals("00110001", BinaryUtil.toBinaryString((byte) 49));
        Assertions.assertEquals("00011000", BinaryUtil.toBinaryString((byte) 24));
        Assertions.assertEquals("11100100", BinaryUtil.toBinaryString((byte) -28));
        Assertions.assertEquals("00000010", BinaryUtil.toBinaryString((byte) 2));
        Assertions.assertEquals("11000000", BinaryUtil.toBinaryString((byte) -64));
        Assertions.assertEquals("10000001", BinaryUtil.toBinaryString((byte) -127));
    }

    @Test
    public void byteArrayBinaryString() {

        Assertions.assertEquals(
                "10000000,00000000,00000110,01110000,00110001,00011000,11100100,00000010,11000000,10000001",
                BinaryUtil.toBinaryString(new byte[]{-128, 0, 6, 112, 49, 24, -28, 2, -64, -127}, ','));
    }

    @Test
    public void parseByteArray2() {
        Assertions.assertArrayEquals(
                new byte[]{-128, 0, 6, 112, 49, 24, -28, 2, -64, -127},
                BinaryUtil.parseByteArray2("10000000", "00000000", "00000110", "01110000", "00110001", "00011000",
                        "11100100", "00000010", "11000000", "10000001"));
    }

    @Test
    public void intArrayBinaryString() {

        Assertions.assertEquals(
                """
                11111111111111111111111110000000,00000000000000000000000000000000,00000000000000000000000000000110,\
                00000000000000000000000001110000,00000000000000000000000000110001,00000000000000000000000000011000,\
                11111111111111111111111111100100,00000000000000000000000000000010,11111111111111111111111111000000,\
                11111111111111111111111110000001""",
                BinaryUtil.toBinaryString(new int[]{-128, 0, 6, 112, 49, 24, -28, 2, -64, -127}, ','));
    }

    @Test
    public void toIntArray2() {

        Assertions.assertArrayEquals(
                new int[]{-128, 0, 6, 112, 49, 24, -28, 2, -64, -127},
                BinaryUtil.parseUnsignedIntArray2(
                        "11111111111111111111111110000000", "00000000000000000000000000000000",
                        "00000000000000000000000000000110", "00000000000000000000000001110000",
                        "00000000000000000000000000110001", "00000000000000000000000000011000",
                        "11111111111111111111111111100100", "00000000000000000000000000000010",
                        "11111111111111111111111111000000", "11111111111111111111111110000001"));
    }

    @Test
    public void intArrayBinaryStringMax() {

        Assertions.assertEquals(
                "10000000,00000000,00000110,01110000,00110001,00011000,11100100,00000010,11000000,10000001",
                BinaryUtil.toBinaryString(new int[]{-128, 0, 6, 112, 49, 24, -28, 2, -64, -127}, Byte.SIZE, ','));

        Assertions.assertEquals(
                "110000000,000000000,000000110,001110000,000110001,000011000,111100100,000000010,111000000,110000001",
                BinaryUtil.toBinaryString(new int[]{-128, 0, 6, 112, 49, 24, -28, 2, -64, -127}, 9, ','));
    }

    @Test
    public void parseIntArray() throws IOException {

        try (var lzw = new BufferedReader(
                new InputStreamReader(BinaryUtilTest.class.getResourceAsStream("strip.lzw.txt")))) {

            final int[] stream = BinaryUtil.parseIntArray(lzw, 10);

            Assertions.assertEquals(66040, stream.length);
            Assertions.assertEquals(-128, stream[0]);
            Assertions.assertEquals(-128, stream[stream.length - 1]);
        }
    }
}
