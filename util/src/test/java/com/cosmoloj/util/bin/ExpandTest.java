package com.cosmoloj.util.bin;

import java.nio.ByteOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class ExpandTest {

    @Test
    public void expandFromLow1() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{
            1, 0, 0, 1, 1, 0, 0, 0,
            0, 1, 0, 1, 0, 0, 0, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(1);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow2() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{1, 2, 1, 0, 2, 2, 0, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(2);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow3() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{1, 3, 0, 5, 0, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(3);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow4() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{9, 1, 10, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(4);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow5() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{25, 16, 2, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(5);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow5_bis() {

        final byte[] in = new byte[]{0x0, 0x7c};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{0, 0, 31, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(5);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow5_1() {

        final byte[] in = new byte[]{0, 124};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{0, 0, 31, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(5);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow5_2() {

        final byte[] in = new byte[]{-32, 3};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{0, 31, 0, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(5);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow5_3() {

        final byte[] in = new byte[]{31, 0};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{31, 0, 0, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(5);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow6() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{25, 40, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(6);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow7() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{25, 20, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(7);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow8() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{0x19, 0x0a};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(8);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow5_2b() {

        final byte[] in = new byte[]{-32, 3};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{0, 31, 0, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(5);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow9() {

        final byte[] in = new byte[]{0x01, 0x01};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);
        expand.expand(7);
        expand.expand(1);
        expand.expand(2);
        expand.expand(3);
        expand.expand(3);

        final byte[] expected = new byte[]{1, 0, 1, 0, 0};

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromLow10() {

        final byte[] in = new byte[]{0x01, 0x01};

        final Expand expand = new Expand(in, FillOrder.FROM_LOW, ByteOrder.LITTLE_ENDIAN);
        expand.expand(1);
        expand.expand(7);
        expand.expand(1);
        expand.expand(7);

        final byte[] expected = new byte[]{1, 0, 1, 0};

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh1() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{
            0, 0, 0, 1, 1, 0, 0, 1,
            0, 0, 0, 0, 1, 0, 1, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(1);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh2() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{0, 1, 2, 1, 0, 0, 2, 2};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(2);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh3() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{0, 6, 2, 0, 5, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(3);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh4() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{1, 9, 0, 10};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(4);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh5() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{3, 4, 5, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(5);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh5_bis() {

        final byte[] in = new byte[]{0x0, 0x7c};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{0, 1, 30, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(5);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh5_1() {

        final byte[] in = new byte[]{0, 124};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{0, 1, 30, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(5);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh5_2() {

        final byte[] in = new byte[]{-32, 3};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{28, 0, 1, 16};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(5);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh5_3() {

        final byte[] in = new byte[]{31, 0};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{3, 28, 0, 0};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(5);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh6() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{6, 16, 40};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(6);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh7() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{12, 66, 64};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(7);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh8() {

        final byte[] in = new byte[]{0x19, 0x0a};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{0x19, 0x0a};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(8);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh5_2b() {

        final byte[] in = new byte[]{-32, 3};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);

        final byte[] expected = new byte[]{28, 0, 1, 16};

        for (int i = 0; i < expected.length; i++) {
            expand.expand(5);
        }

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh9() {

        final byte[] in = new byte[]{0x01, 0x01};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);
        expand.expand(7);
        expand.expand(1);
        expand.expand(2);
        expand.expand(3);
        expand.expand(3);

        final byte[] expected = new byte[]{0, 1, 0, 0, 1};

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }

    @Test
    public void expandFromHigh10() {

        final byte[] in = new byte[]{0x01, 0x01};

        final Expand expand = new Expand(in, FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);
        expand.expand(1);
        expand.expand(7);
        expand.expand(1);
        expand.expand(7);

        final byte[] expected = new byte[]{0, 1, 0, 1};

        Assertions.assertArrayEquals(expected, expand.getByteArray());
    }
}
