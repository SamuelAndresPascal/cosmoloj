package com.cosmoloj.util.bin;

import java.nio.ByteOrder;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 *
 * @author Samuel Andrés
 */
public class FillOrderTest {

    public static Stream<Arguments> join() {
        return Stream.of(
            Arguments.arguments(1486,
                    FillOrder.FROM_HIGH, new int[]{2, 231, 2}, 1, 1, ByteOrder.LITTLE_ENDIAN)
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource
    public void join(final int expected, final FillOrder fOrder, final int[] input, final int bitCursor,
            final int left, final ByteOrder bOrder) {
        Assertions.assertEquals(expected, fOrder.join(input, input.length, bitCursor, left, bOrder));
    }

    private static final int NONE = -1;
    public static Stream<Arguments> joinString() {
        return Stream.of(
            Arguments.arguments("00000000000000000000001111100111", FillOrder.FROM_LOW,
                    new String[]{"00000000000000000000000011100111",
                        "00000000000000000000000000000011"}, NONE, 8, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments("00000000000000001110011100000010", FillOrder.FROM_LOW,
                    new String[]{"00000000000000000000000000000010",
                        "00000000000000000000000011100111"}, NONE, 8, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments("00000000000000001110011110", FillOrder.FROM_LOW,
                    new String[]{"00000000000000000000000000000010",
                        "00000000000000000000000011100111"}, NONE, 2, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments("00000000000000000000011100111010", FillOrder.FROM_LOW,
                    new String[]{"00000000000000000000000000000010",
                        "00000000000000000000000011100111"}, NONE, 3, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments("00000000000000000010111001110010", FillOrder.FROM_LOW,
                    new String[]{"00000000000000000000000000000010",
                        "00000000000000000000000011100111",
                        "00000000000000000000000000000010"}, NONE, 4, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments("00000000000000000001111001110", FillOrder.FROM_LOW,
                    new String[]{"00000000000000000000000000000000",
                        "00000000000000000000000011100111",
                        "00000000000000000000000000000001"}, NONE, 1, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments("00000000000000000000101110011110", FillOrder.FROM_LOW,
                    new String[]{"00000000000000000000000000000010",
                        "00000000000000000000000011100111",
                        "00000000000000000000000000000010"}, NONE, 2, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments("00000000000000000000111001111", FillOrder.FROM_HIGH,
                    new String[]{"00000000000000000000000000000000",
                        "00000000000000000000000011100111",
                        "00000000000000000000000000000001"}, 1, NONE, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments("00000000000000000000101110011110", FillOrder.FROM_HIGH,
                    new String[]{"00000000000000000000000000000010",
                        "00000000000000000000000011100111",
                        "00000000000000000000000000000010"}, 2, NONE, ByteOrder.LITTLE_ENDIAN)
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource
    public void joinString(final String expected, final FillOrder fOrder, final String[] input, final int bitCursor,
            final int left, final ByteOrder bOrder) {
        final int[] inputBin = BinaryUtil.parseUnsignedIntArray2(input);
        final int result = fOrder.join(inputBin, input.length, bitCursor, left, bOrder);

        Assertions.assertEquals(Integer.parseUnsignedInt(expected, 2), result);
    }

    public static Stream<Arguments> extract() {
        return Stream.of(
            Arguments.arguments(3, FillOrder.FROM_LOW, (byte) 0x06, 1, 2),
            Arguments.arguments(0, FillOrder.FROM_LOW, (byte) 0x60, 1, 2),
            Arguments.arguments(3, FillOrder.FROM_LOW, (byte) 0x18, 3, 2),
            Arguments.arguments(0, FillOrder.FROM_LOW, (byte) 0xe1, 3, 2),
            Arguments.arguments(0, FillOrder.FROM_HIGH, (byte) 0x06, 1, 2),
            Arguments.arguments(3, FillOrder.FROM_HIGH, (byte) 0x60, 1, 2),
            Arguments.arguments(3, FillOrder.FROM_HIGH, (byte) 0x18, 3, 2),
            Arguments.arguments(0, FillOrder.FROM_HIGH, (byte) 0xe1, 3, 2)
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource
    public void extract(final int expected, final FillOrder order, final byte input, final int bitCursor,
            final int size) {
        Assertions.assertEquals(expected, order.extract(input, bitCursor, size, ByteOrder.LITTLE_ENDIAN));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @CsvFileSource(files = "src/test/resources/com/cosmoloj/util/bin/extract2.csv")
    public void extract2(final String expected, final FillOrder order, final String input, final int bitCursor,
            final int size) {
        Assertions.assertEquals(Integer.parseUnsignedInt(expected, 2),
                order.extract((byte) Integer.parseInt(input, 2), bitCursor, size, ByteOrder.LITTLE_ENDIAN));
    }

    public static Stream<Arguments> complete() {
        return Stream.of(
            // extraction de deux bits à l'offset 1 à partir du poids faible
            Arguments.arguments(3, FillOrder.FROM_LOW, (byte) 0x06, 1, 2),
            Arguments.arguments(0, FillOrder.FROM_LOW, (byte) 0x60, 1, 2),
            // extraction de deux bits à l'offset 3 à partir du poids faible
            Arguments.arguments(3, FillOrder.FROM_LOW, (byte) 0x18, 3, 2),
            Arguments.arguments(0, FillOrder.FROM_LOW, (byte) 0xe1, 3, 2),
            // extraction de deux bits à l'offset 1 à partir du poids fort
            Arguments.arguments(0, FillOrder.FROM_HIGH, (byte) 0x06, 1, 2),
            Arguments.arguments(3, FillOrder.FROM_HIGH, (byte) 0x60, 1, 2),
            // extraction de deux bits à l'offset 3 à partir du poids faible
            Arguments.arguments(3, FillOrder.FROM_HIGH, (byte) 0x18, 3, 2),
            Arguments.arguments(0, FillOrder.FROM_HIGH, (byte) 0xe1, 3, 2)
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource
    public void complete(final int expected, final FillOrder order, final byte input, final int bitCursor,
            final int size) {
        Assertions.assertEquals(expected,
                order.complete(input, bitCursor, Byte.SIZE - bitCursor, size, ByteOrder.LITTLE_ENDIAN));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @CsvFileSource(files = "src/test/resources/com/cosmoloj/util/bin/extract2.csv")
    public void complete2(final String expected, final FillOrder order, final String input, final int bitCursor,
            final int size) {
        Assertions.assertEquals(Integer.parseUnsignedInt(expected, 2),
                order.complete((byte) Integer.parseInt(input, 2), bitCursor, Byte.SIZE - bitCursor, size,
                        ByteOrder.LITTLE_ENDIAN),
                "expected " + expected + " for " + order + " " + input + " at " + bitCursor + " (" + size + ")");
    }

    public static Stream<Arguments> expand() {
        return Stream.of(
            Arguments.arguments(new byte[]{1, 0, 0, 1, 1, 0, 0, 0,
                                            0, 1, 0, 1, 0, 0, 0, 0},
                    FillOrder.FROM_LOW, new byte[]{0x19, 0x0a}, 1, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{1, 2, 1, 0, 2, 2, 0, 0},
                    FillOrder.FROM_LOW, new byte[]{0x19, 0x0a}, 2, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{1, 3, 0, 5, 0, 0},
                    FillOrder.FROM_LOW, new byte[]{0x19, 0x0a}, 3, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{9, 1, 10, 0},
                    FillOrder.FROM_LOW, new byte[]{0x19, 0x0a}, 4, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{25, 16, 2, 0},
                    FillOrder.FROM_LOW, new byte[]{0x19, 0x0a}, 5, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{0, 0, 31, 0},
                    FillOrder.FROM_LOW, new byte[]{0x0, 0x7c}, 5, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{0, 0, 31, 0},
                    FillOrder.FROM_LOW, new byte[]{0, 124}, 5, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{0, 31, 0, 0},
                    FillOrder.FROM_LOW, new byte[]{-32, 3}, 5, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{31, 0, 0, 0},
                    FillOrder.FROM_LOW, new byte[]{31, 0}, 5, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{25, 40, 0},
                    FillOrder.FROM_LOW, new byte[]{0x19, 0x0a}, 6, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{25, 20, 0},
                    FillOrder.FROM_LOW, new byte[]{0x19, 0x0a}, 7, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{0x19, 0x0a},
                    FillOrder.FROM_LOW, new byte[]{0x19, 0x0a}, 8, ByteOrder.LITTLE_ENDIAN),

            Arguments.arguments(new byte[]{0, 0, 0, 1, 1, 0, 0, 1,
                                            0, 0, 0, 0, 1, 0, 1, 0},
                    FillOrder.FROM_HIGH, new byte[]{0x19, 0x0a}, 1, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{0, 1, 2, 1, 0, 0, 2, 2},
                    FillOrder.FROM_HIGH, new byte[]{0x19, 0x0a}, 2, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{0, 6, 2, 0, 5, 0},
                    FillOrder.FROM_HIGH, new byte[]{0x19, 0x0a}, 3, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{1, 9, 0, 10},
                    FillOrder.FROM_HIGH, new byte[]{0x19, 0x0a}, 4, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{3, 4, 5, 0},
                    FillOrder.FROM_HIGH, new byte[]{0x19, 0x0a}, 5, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{0, 1, 30, 0},
                    FillOrder.FROM_HIGH, new byte[]{0x0, 0x7c}, 5, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{0, 1, 30, 0},
                    FillOrder.FROM_HIGH, new byte[]{0, 124}, 5, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{28, 0, 1, 16},
                    FillOrder.FROM_HIGH, new byte[]{-32, 3}, 5, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{3, 28, 0, 0},
                    FillOrder.FROM_HIGH, new byte[]{31, 0}, 5, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{6, 16, 40},
                    FillOrder.FROM_HIGH, new byte[]{0x19, 0x0a}, 6, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{12, 66, 64},
                    FillOrder.FROM_HIGH, new byte[]{0x19, 0x0a}, 7, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{0x19, 0x0a},
                    FillOrder.FROM_HIGH, new byte[]{0x19, 0x0a}, 8, ByteOrder.LITTLE_ENDIAN)
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource
    public void expand(final byte[] expected, final FillOrder order, final byte[] in, final int size,
            final ByteOrder bOrder) {

        Assertions.assertArrayEquals(expected, order.expand(in, size, bOrder));
    }

    public static Stream<Arguments> expandArray() {
        return Stream.of(
            Arguments.arguments(new byte[]{0, 31, 0, 0},
                    FillOrder.FROM_LOW, new byte[]{-32, 3}, new int[]{5, 5, 5, 5}, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{1, 0, 1, 0, 0},
                    FillOrder.FROM_LOW, new byte[]{0x01, 0x01}, new int[]{7, 1, 2, 3, 3}, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{1, 0, 1, 0},
                    FillOrder.FROM_LOW, new byte[]{0x01, 0x01}, new int[]{1, 7, 1, 7}, ByteOrder.LITTLE_ENDIAN),

            Arguments.arguments(new byte[]{28, 0, 1, 16},
                    FillOrder.FROM_HIGH, new byte[]{-32, 3}, new int[]{5, 5, 5, 5}, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{0, 1, 0, 0, 1},
                    FillOrder.FROM_HIGH, new byte[]{0x01, 0x01}, new int[]{7, 1, 2, 3, 3}, ByteOrder.LITTLE_ENDIAN),
            Arguments.arguments(new byte[]{0, 1, 0, 1},
                    FillOrder.FROM_HIGH, new byte[]{0x01, 0x01}, new int[]{1, 7, 1, 7}, ByteOrder.LITTLE_ENDIAN));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource
    public void expandArray(final byte[] expected, final FillOrder order, final byte[] in, final int[] sizes,
            final ByteOrder bOrder) {

        Assertions.assertArrayEquals(expected, order.expand(in, 0, in.length, sizes, bOrder));
    }
}
