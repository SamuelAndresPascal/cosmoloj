package com.cosmoloj.util.java2d.image;

import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferDouble;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferUShort;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.function.ToIntFunction;

/**
 *
 * @author Samuel Andrés
 */
public interface BufferedImageParameters {

    int dataBufferType();

    DataBuffer dataBuffer();

    ColorModelParameters colorModelParameters();

    SampleModelParameters sampleModelParameters();

    default byte[][] colorMapParameters() {
        return new byte[0][];
    }

    static BufferedImageParameters grayByte(
            final byte[][] input,
            final int height,
            final int width,
            final int depth,
            final int pixelDepth,
            final boolean minIsWhite) {
        return new DefaultBufferedImageParameters(DataBuffer.TYPE_BYTE) {
            private final byte[] output = new byte[height * width * depth];

            @Override
            public SampleModelParameters sampleModelParameters() {
                return new SampleModelParameters(height, width, depth, new int[]{0}, new int[]{0});
            }

            @Override
            public ColorModelParameters colorModelParameters() {
                return ColorModelParameters.of1(pixelDepth, ColorSpace.CS_GRAY);
            }

            @Override
            public DataBuffer dataBuffer() {
                int out = 0;
                for (final byte[] strip : input) {
                    int in = 0;
                    final int len = strip.length;
                    while (in < len) {
                        for (int i = 0; i < depth; i++) {
                            output[out++] = map(strip[in++]);
                        }
                    }
                }
                return new DataBufferByte(output, output.length, 0);
            }

            private byte map(final byte b) {
                return switch (pixelDepth) {
                    case 1 -> {
                        if (minIsWhite) {
                            yield (byte) switch (b) {
                                case 0 -> 1;
                                case 1 -> 0;
                                default -> throw new IllegalStateException("unexpected value " + b);
                            };
                        } else {
                            yield b;
                        }
                    }
                    case Byte.SIZE -> {
                        if (minIsWhite) {
                            yield (byte) (255 - b);
                        } else {
                            yield b;
                        }
                    }
                    default -> {
                        if (pixelDepth < Byte.SIZE) {
                            if (minIsWhite) {
                                yield (byte) (255 - b);
                            } else {
                                yield b;
                            }
                        } else {
                            throw new UnsupportedOperationException("unsupported pixel depth " + pixelDepth);
                        }
                    }
                };
            }
        };
    }

    static BufferedImageParameters paletteByte(
            final byte[][] input,
            final int height,
            final int width,
            final int depth,
            final int pixelDepth,
            final byte[][] colorMap) {
        return new DefaultBufferedImageParameters(DataBuffer.TYPE_BYTE) {
            private final byte[] output = new byte[height * width * depth];

            @Override
            public SampleModelParameters sampleModelParameters() {
                return new SampleModelParameters(height, width, depth, new int[]{0}, new int[]{0});
            }

            @Override
            public ColorModelParameters colorModelParameters() {
                return ColorModelParameters.of1(pixelDepth, null);
            }

            @Override
            public DataBuffer dataBuffer() {
                int out = 0;
                for (final byte[] strip : input) {
                    int in = 0;
                    final int len = strip.length;
                    while (in < len) {
                        for (int i = 0; i < depth; i++) {
                            output[out++] = strip[in++];
                        }
                    }
                }
                return new DataBufferByte(output, output.length, 0);
            }

            @Override
            public byte[][] colorMapParameters() {
                return colorMap;
            }
        };
    }

    static BufferedImageParameters grayUshort(
            final byte[][] input,
            final int height,
            final int width,
            final int depth,
            final int pixelDepth) {

        return new DefaultBufferedImageParameters(DataBuffer.TYPE_USHORT) {

            private final short[] output = new short[height * width * depth];
            private final int pas = Short.BYTES * depth;

            @Override
            public SampleModelParameters sampleModelParameters() {
                return new SampleModelParameters(height, width, depth, new int[]{0}, new int[]{0});
            }

            @Override
            public ColorModelParameters colorModelParameters() {
                return ColorModelParameters.of1(pixelDepth, ColorSpace.CS_GRAY);
            }

            @Override
            public DataBuffer dataBuffer() {

                int out = 0;
                for (final byte[] strip : input) {
                    int in = 0;
                    final int len = strip.length;
                    final ByteBuffer buffer = ByteBuffer.wrap(strip);
                    while (in < len) {
                        for (int i = 0; i < depth; i++) {
                            output[out++] = buffer.getShort();
                        }
                        in += pas;
                    }
                }
                return new DataBufferUShort(output, output.length, 0);
            }
        };
    }

    static BufferedImageParameters rgbInt(
            final byte[][] input,
            final ByteOrder order,
            final int height,
            final int width,
            final int depth,
            final int pixelDepth) {

        return new DefaultBufferedImageParameters(DataBuffer.TYPE_INT) {

            private final int[] output = new int[height * width * depth];

            private final int pas = depth * switch (pixelDepth) {
                case Byte.SIZE -> Byte.BYTES;
                case Short.SIZE -> Short.BYTES;
                case Integer.SIZE -> Integer.BYTES;
                default -> {
                    if (pixelDepth < Byte.SIZE) {
                        yield Byte.BYTES;
                    } else if (pixelDepth < Short.SIZE) {
                        yield Short.BYTES;
                    } else if (pixelDepth < Integer.SIZE) {
                        yield Integer.BYTES;
                    }
                    throw new UnsupportedOperationException("unsupported pixel depth " + pixelDepth);
                }
            };
            private final ToIntFunction<ByteBuffer> toRgb = switch (pixelDepth) {
                case Byte.SIZE -> buffer -> Byte.toUnsignedInt(buffer.get());
                case Short.SIZE -> buffer -> Short.toUnsignedInt(buffer.getShort());
                case Integer.SIZE -> ByteBuffer::getInt;
                default -> {
                    if (pixelDepth < Byte.SIZE) {
                        yield buffer -> Byte.toUnsignedInt(buffer.get());
                    } else if (pixelDepth < Short.SIZE) {
                        yield buffer -> Short.toUnsignedInt(buffer.getShort());
                    } else if (pixelDepth < Integer.SIZE) {
                        yield ByteBuffer::getInt;
                    }
                    throw new UnsupportedOperationException("unsupported pixel depth " + pixelDepth);
                }
            };

            @Override
            public SampleModelParameters sampleModelParameters() {
                return new SampleModelParameters(height, width, depth, new int[]{0, 1, 2}, new int[]{0});
            }

            @Override
            public ColorModelParameters colorModelParameters() {
                return ColorModelParameters.of3(pixelDepth, ColorSpace.CS_sRGB);
            }

            @Override
            public DataBuffer dataBuffer() {

                int out = 0;
                for (final byte[] strip : input) {
                    int in = 0;
                    final int len = strip.length;
                    final ByteBuffer buffer = ByteBuffer.wrap(strip).order(order);
                    while (in < len) {
                        for (int i = 0; i < depth; i++) {
                            output[out++] = toRgb.applyAsInt(buffer);
                        }
                        in += pas;
                    }
                }
                return new DataBufferInt(output, output.length, 0);
            }
        };
    }

    static BufferedImageParameters rgbDouble(final byte[][] input, final ByteOrder order, final int height,
            final int width, final int depth, final int pixelDepth, final int r, final int g, final int b) {
        return new BufferedImageParameters() {

            private final int pas = Double.BYTES * depth;
            private final double[] output = new double[height * width * depth];
            private final double[] min = new double[depth];
            private final double[] max = new double[depth];

            @Override
            public int dataBufferType() {
                return DataBuffer.TYPE_DOUBLE;
            }

            @Override
            public SampleModelParameters sampleModelParameters() {
                return new SampleModelParameters(height, width, depth, new int[]{r, g, b}, new int[]{0});
            }

            @Override
            public ColorModelParameters colorModelParameters() {
                return ColorModelParameters.of3(pixelDepth,
                        new ICC_ColorSpace(ICC_Profile.getInstance(ColorSpace.CS_sRGB)) {

                            @Override
                            public float getMinValue(final int component) {
                                return (float) min[component];
                            }

                            @Override
                            public float getMaxValue(final int component) {
                                return (float) max[component];
                            }
                        });
            }

            @Override
            public DataBuffer dataBuffer() {
                int out = 0;
                for (final byte[] strip : input) {
                    int in = 0;
                    final int len = strip.length;
                    final ByteBuffer buffer = ByteBuffer.wrap(strip).order(order);
                    while (in < len) {
                        for (int i = 0; i < depth; i++) {
                            final double d = buffer.getDouble();
                            if (d < min[i]) {
                                min[i] = d;
                            } else if (d > max[i]) {
                                max[i] = d;
                            }
                            output[out++] = d;
                        }
                        in += pas;
                    }
                }
                return new DataBufferDouble(output, output.length, 0);
            }
        };
    }

    static BufferedImageParameters bandedRgbInt(
            final byte[][] input,
            final ByteOrder order,
            final int height,
            final int width,
            final int depth,
            final int pixelDepth,
            final int r,
            final int g,
            final int b) {

        return new DefaultBufferedImageParameters(DataBuffer.TYPE_INT) {

            private final int[][] output = new int[depth][height * width];

            @Override
            public SampleModelParameters sampleModelParameters() {
                return new SampleModelParameters(height, width, depth,
                        new int[]{0, 0, 0},
                        new int[]{r, g, b});
            }

            @Override
            public ColorModelParameters colorModelParameters() {
                return ColorModelParameters.of3(pixelDepth, ColorSpace.CS_sRGB);
            }

            @Override
            public DataBuffer dataBuffer() {
                final int[] out = new int[depth];
                for (final byte[] strip : input) {
                    final int len = strip.length;
                    final ByteBuffer buffer = ByteBuffer.wrap(strip).order(order);

                    int in = 0;
                    while (in < len) {
                        final int b = (depth * in) / len;
                        output[b][out[b]++] = Byte.toUnsignedInt(buffer.get());
                        in++;
                    }
                }

                final int[] offsets = new int[depth];
                Arrays.fill(offsets, 0);
                return new DataBufferInt(output, output.length, offsets);
            }
        };
    }
}
