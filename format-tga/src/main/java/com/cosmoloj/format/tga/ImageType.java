package com.cosmoloj.format.tga;

import com.cosmoloj.math.tabular.core.ByteTabulars;
import com.cosmoloj.util.java2d.image.BufferedImageParameters;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.nio.ByteOrder;

/**
 *
 * @author Samuel Andrés
 */
public enum ImageType {

    NO_IMAGE_DATA_INCLUDED((byte) 0, (byte) 0) {
        @Override
        public BufferedImage toBufferedImage(final byte[][] data, final Header header, final byte[][] imageMap) {
            return null;
        }
    },
    UNCOMPRESSED_COLOR_MAPPED_IMAGE((byte) 1, (byte) 1) {
        @Override
        public BufferedImage toBufferedImage(final byte[][] data, final Header header, final byte[][] imageMap) {
            return createColorImage(data,
                    header.imageSpecification().imageHeight(),
                    header.imageSpecification().imageWidth(),
                    header.getPixelAllocation(),
                    ColorSpace.CS_sRGB,
                    header.colorMapSpecification().colorMapEntrySize() / 3, imageMap);
        }
    },
    UNCOMPRESSED_TRUE_COLOR_IMAGE((byte) 2, (byte) 3) {
        @Override
        public BufferedImage toBufferedImage(final byte[][] data, final Header header, final byte[][] imageMap) {
            return createColorImage(data,
                    header.imageSpecification().imageHeight(),
                    header.imageSpecification().imageWidth(),
                    header.getPixelAllocation(),
                    ColorSpace.CS_sRGB, header.getColorDepth(), imageMap);
        }
    },
    UNCOMPRESSED_BLACK_AND_WHITE_IMAGE((byte) 3, (byte) 1) {
        @Override
        public BufferedImage toBufferedImage(final byte[][] data, final Header header, final byte[][] imageMap) {
            return createColorImage(data,
                    header.imageSpecification().imageHeight(),
                    header.imageSpecification().imageWidth(),
                    header.getPixelAllocation(),
                    ColorSpace.CS_GRAY, header.getColorDepth(), imageMap);
        }
    },
    RLE_ENCODED_COLOR_MAPPED_IMAGE((byte) 9, (byte) 1) {
        @Override
        public BufferedImage toBufferedImage(final byte[][] data, final Header header, final byte[][] imageMap) {
            return createColorImage(data,
                    header.imageSpecification().imageHeight(),
                    header.imageSpecification().imageWidth(),
                    header.getPixelAllocation(),
                    ColorSpace.CS_sRGB,
                    header.colorMapSpecification().colorMapEntrySize() / 3, imageMap);
        }
    },
    RLE_ENCODED_TRUE_COLOR_IMAGE((byte) 10, (byte) 3) {
        @Override
        public BufferedImage toBufferedImage(final byte[][] data, final Header header, final byte[][] imageMap) {
            return createColorImage(data,
                    header.imageSpecification().imageHeight(),
                    header.imageSpecification().imageWidth(),
                    header.getPixelAllocation(),
                    ColorSpace.CS_sRGB, header.getColorDepth(), imageMap);
        }
    },
    RLE_ENCODED_BLACK_AND_WHITE_IMAGE((byte) 11, (byte) 1) {
        @Override
        public BufferedImage toBufferedImage(final byte[][] data, final Header header, final byte[][] imageMap) {
            return createColorImage(data,
                    header.imageSpecification().imageHeight(),
                    header.imageSpecification().imageWidth(),
                    header.getPixelAllocation(),
                    ColorSpace.CS_GRAY, header.getColorDepth(), imageMap);
        }
    };

    private final byte value;
    private final byte bandNumber;

    ImageType(final byte value, final byte bandNumber) {
        this.value = value;
        this.bandNumber = bandNumber;
    }

    public byte getValue() {
        return value;
    }

    public byte getColorBandNumber() {
        return bandNumber;
    }

    public static ImageType forByte(final byte b) {
        for (final ImageType it : values()) {
            if (it.value == b) {
                return it;
            }
        }
        throw new IllegalArgumentException();
    }

    public abstract BufferedImage toBufferedImage(byte[][] data, Header header, byte[][] imageMap);

    // https://stackoverflow.com/questions/42615441/convert-2d-pixel-array-into-bufferedimage
    private static BufferedImage createColorImage(final byte[][] input, final int height, final int width,
            final int allocation, final int colorSpace, final int pixelDepth, final byte[][] imageMap) {

        final BufferedImageParameters p;
        final ColorModel colorModel;
        final SampleModel sm;

        if (imageMap.length == 0) {

            p = switch (colorSpace) {
                case ColorSpace.CS_GRAY -> BufferedImageParameters.grayByte(
                        input, height, width, allocation, pixelDepth, false);
                case ColorSpace.CS_sRGB -> BufferedImageParameters.rgbInt(
                        input, ByteOrder.BIG_ENDIAN, height, width, allocation, pixelDepth);
                default -> throw new UnsupportedOperationException();
            };

            final int dataBufferType = p.dataBufferType();

            colorModel = new ComponentColorModel(
                    p.colorModelParameters().colorSpace(),
                    p.colorModelParameters().pixelBits(),
                    false,
                    false,
                    Transparency.OPAQUE,
                    dataBufferType);

            sm = new PixelInterleavedSampleModel(
                    dataBufferType,
                    p.sampleModelParameters().width(),
                    p.sampleModelParameters().height(),
                    p.sampleModelParameters().depth(),
                    p.sampleModelParameters().width() * p.sampleModelParameters().depth(),
                    switch (colorSpace) {
                        case ColorSpace.CS_GRAY -> p.sampleModelParameters().bandOffsets();
                        case ColorSpace.CS_sRGB -> new int[]{2, 1, 0};
                        default -> throw new UnsupportedOperationException();
                    });
        } else  {

            // on utilise byte gray qui fonctionne sur 8 bits
            // peu importe son colorspace car il n'est pas utilisé (indexed color model utilise de base RGB)
            p = BufferedImageParameters.grayByte(input, height, width, allocation, Byte.SIZE, false);

            final int[] bandOffsets = p.sampleModelParameters().bandOffsets();

            final int dataBufferType = p.dataBufferType();

            // indexed color model ne fonctionne qu'avec 8 bits de couleur significatifs
            // il faut donc ajuster les couleurs pour occuper 8 bits si elles occupent moins de 8 bits
            final byte[][] bgr = ByteTabulars.transpose(resizeMap(imageMap, pixelDepth));

            colorModel = new IndexColorModel(Byte.SIZE, imageMap.length, bgr[2], bgr[1], bgr[0]);

            sm = new PixelInterleavedSampleModel(
                    dataBufferType,
                    p.sampleModelParameters().width(),
                    p.sampleModelParameters().height(),
                    p.sampleModelParameters().depth(),
                    p.sampleModelParameters().width() * p.sampleModelParameters().depth(),
                    bandOffsets);
        }

        final WritableRaster raster = Raster.createWritableRaster(sm, p.dataBuffer(), null);
        return new BufferedImage(colorModel, raster, false, null);
    }

    private static byte[][] resizeMap(final byte[][] imageMap, final int pixelDepth) {
        final byte[][] rgb = new byte[imageMap.length][imageMap[0].length];

        for (int entry = 0; entry < imageMap.length; entry++) {
            for (int band = 0; band < imageMap[entry].length; band++) {
                rgb[entry][band] = (byte) (imageMap[entry][band] * (int) Math.pow(2., (double) Byte.SIZE - pixelDepth));
            }
        }
        return rgb;
    }
}
