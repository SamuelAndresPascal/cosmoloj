package com.cosmoloj.format.tga.test;

import com.cosmoloj.format.tga.ColorMapSpecification;
import com.cosmoloj.format.tga.ColorMapType;
import com.cosmoloj.format.tga.ImageSpecification;
import com.cosmoloj.format.tga.ImageType;
import com.cosmoloj.format.tga.Header;
import com.cosmoloj.format.tga.HeaderReader;
import com.cosmoloj.format.tga.HeaderWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.WritableByteChannel;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Samuel Andrés
 */
public class HeaderTest {

    static Stream<Arguments> tgaImageProvider() {
        return Stream.of(
            Arguments.arguments("sampleBW.tga", BW),
            Arguments.arguments("sampleColorMap.tga", COLOR_MAP),
            Arguments.arguments("sampleCompressed.tga", COMPRESSED),
            Arguments.arguments("sampleUncompressedBL.tga", UNCOMPRESSED_BL),
            Arguments.arguments("sampleUncompressedBLLarge.tga", UNCOMPRESSED_BL_LARGE),
            Arguments.arguments("sampleUncompressedBW.tga", UNCOMPRESSED_BW),
            Arguments.arguments("sampleUncompressedColorMap.tga", UNCOMPRESSED_COLOR_MAP),
            Arguments.arguments("sampleUncompressedTL.tga", UNCOMPRESSED_TL),
            Arguments.arguments("cbw8.tga", CBW8),
            Arguments.arguments("ccm8.tga", CCM8),
            Arguments.arguments("ctc24.tga", CTC24),
            Arguments.arguments("ubw8.tga", UBW8),
            Arguments.arguments("ucm8.tga", UCM8),
            Arguments.arguments("utc16.tga", UTC16),
            Arguments.arguments("utc24.tga", UTC24),
            Arguments.arguments("utc32.tga", UTC32)
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("tgaImageProvider")
    void readerTest(final String fileName, final Consumer<Header> headerTest)
            throws IOException, URISyntaxException {
        readerGenericTest(new File(HeaderTest.class.getResource(fileName).toURI()), headerTest);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("tgaImageProvider")
    void writerTest(final String fileName, final Consumer<Header> headerTest)
            throws IOException, URISyntaxException {

        // fichier à écrire
        final File fileW = File.createTempFile("tgaWriterTest", ".tga");
        fileW.deleteOnExit();

        try (HeaderReader headerReader = new HeaderReader(
                new FileInputStream(new File(HeaderTest.class.getResource(fileName).toURI())).getChannel())) {

            final Header header = headerReader.read();

            try (WritableByteChannel wChannel = new FileOutputStream(fileW, false).getChannel();
                    HeaderWriter headerWriter = new HeaderWriter(wChannel)) {
                headerWriter.write(header);
            }

            readerGenericTest(fileW, headerTest);
        } finally {
            if (fileW.exists()) {
                fileW.delete();
            }
        }
    }

    private void readerGenericTest(final File file, final Consumer<Header> headerTestConsumer) throws IOException {
        try (var headerReader = new HeaderReader(new FileInputStream(file).getChannel())) {
            headerTestConsumer.accept(headerReader.read());
        }
    }

    private static final Consumer<Header> BW = header -> {

        Assertions.assertEquals(0, header.idLength());
        Assertions.assertEquals(ColorMapType.NO_COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.RLE_ENCODED_BLACK_AND_WHITE_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(0, colorMap.colorMapLength());
        Assertions.assertEquals(0, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(2, image.imageWidth());
        Assertions.assertEquals(2, image.imageHeight());
        Assertions.assertEquals(8, image.pixelDepth());
        Assertions.assertEquals(0, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> COLOR_MAP = header -> {

        Assertions.assertEquals(0, header.idLength());
        Assertions.assertEquals(ColorMapType.COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.RLE_ENCODED_COLOR_MAPPED_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(4, colorMap.colorMapLength());
        Assertions.assertEquals(24, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(2, image.imageWidth());
        Assertions.assertEquals(2, image.imageHeight());
        Assertions.assertEquals(8, image.pixelDepth());
        Assertions.assertEquals(0, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> COMPRESSED = header -> {

        Assertions.assertEquals(0, header.idLength());
        Assertions.assertEquals(ColorMapType.NO_COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.RLE_ENCODED_TRUE_COLOR_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(0, colorMap.colorMapLength());
        Assertions.assertEquals(0, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(2, image.yOrigin());
        Assertions.assertEquals(2, image.imageWidth());
        Assertions.assertEquals(2, image.imageHeight());
        Assertions.assertEquals(24, image.pixelDepth());
        Assertions.assertEquals(0x20, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorTop());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> UNCOMPRESSED_BL = header -> {

        Assertions.assertEquals(0, header.idLength());
        Assertions.assertEquals(ColorMapType.NO_COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.UNCOMPRESSED_TRUE_COLOR_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(0, colorMap.colorMapLength());
        Assertions.assertEquals(0, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(2, image.imageWidth());
        Assertions.assertEquals(2, image.imageHeight());
        Assertions.assertEquals(24, image.pixelDepth());
        Assertions.assertEquals(0, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> UNCOMPRESSED_BL_LARGE = header -> {

        Assertions.assertEquals(0, header.idLength());
        Assertions.assertEquals(ColorMapType.NO_COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.UNCOMPRESSED_TRUE_COLOR_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(0, colorMap.colorMapLength());
        Assertions.assertEquals(0, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(4, image.imageWidth());
        Assertions.assertEquals(2, image.imageHeight());
        Assertions.assertEquals(24, image.pixelDepth());
        Assertions.assertEquals(0, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> UNCOMPRESSED_BW = header -> {

        Assertions.assertEquals(0, header.idLength());
        Assertions.assertEquals(ColorMapType.NO_COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.UNCOMPRESSED_BLACK_AND_WHITE_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(0, colorMap.colorMapLength());
        Assertions.assertEquals(0, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(2, image.imageWidth());
        Assertions.assertEquals(2, image.imageHeight());
        Assertions.assertEquals(8, image.pixelDepth());
        Assertions.assertEquals(0, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> UNCOMPRESSED_COLOR_MAP = header -> {

        Assertions.assertEquals(0, header.idLength());
        Assertions.assertEquals(ColorMapType.COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.UNCOMPRESSED_COLOR_MAPPED_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(4, colorMap.colorMapLength());
        Assertions.assertEquals(24, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(2, image.imageWidth());
        Assertions.assertEquals(2, image.imageHeight());
        Assertions.assertEquals(8, image.pixelDepth());
        Assertions.assertEquals(0, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> UNCOMPRESSED_TL = header -> {

        Assertions.assertEquals(0, header.idLength());
        Assertions.assertEquals(ColorMapType.NO_COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.UNCOMPRESSED_TRUE_COLOR_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(0, colorMap.colorMapLength());
        Assertions.assertEquals(0, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(2, image.yOrigin());
        Assertions.assertEquals(2, image.imageWidth());
        Assertions.assertEquals(2, image.imageHeight());
        Assertions.assertEquals(24, image.pixelDepth());
        Assertions.assertEquals(32, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorTop());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> CBW8 = header -> {

        Assertions.assertEquals(26, header.idLength());
        Assertions.assertEquals(ColorMapType.NO_COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.RLE_ENCODED_BLACK_AND_WHITE_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(0, colorMap.colorMapLength());
        Assertions.assertEquals(0, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(128, image.imageWidth());
        Assertions.assertEquals(128, image.imageHeight());
        Assertions.assertEquals(8, image.pixelDepth());
        Assertions.assertEquals(0, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> CCM8 = header -> {

        Assertions.assertEquals(26, header.idLength());
        Assertions.assertEquals(ColorMapType.COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.RLE_ENCODED_COLOR_MAPPED_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(256, colorMap.colorMapLength());
        Assertions.assertEquals(16, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(128, image.imageWidth());
        Assertions.assertEquals(128, image.imageHeight());
        Assertions.assertEquals(8, image.pixelDepth());
        Assertions.assertEquals(0, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> CTC24 = header -> {

        Assertions.assertEquals(26, header.idLength());
        Assertions.assertEquals(ColorMapType.NO_COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.RLE_ENCODED_TRUE_COLOR_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(0, colorMap.colorMapLength());
        Assertions.assertEquals(0, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(128, image.imageWidth());
        Assertions.assertEquals(128, image.imageHeight());
        Assertions.assertEquals(24, image.pixelDepth());
        Assertions.assertEquals(0, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> UBW8 = header -> {

        Assertions.assertEquals(26, header.idLength());
        Assertions.assertEquals(ColorMapType.NO_COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.UNCOMPRESSED_BLACK_AND_WHITE_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(0, colorMap.colorMapLength());
        Assertions.assertEquals(0, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(128, image.imageWidth());
        Assertions.assertEquals(128, image.imageHeight());
        Assertions.assertEquals(8, image.pixelDepth());
        Assertions.assertEquals(0, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> UCM8 = header -> {

        Assertions.assertEquals(26, header.idLength());
        Assertions.assertEquals(ColorMapType.COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.UNCOMPRESSED_COLOR_MAPPED_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(256, colorMap.colorMapLength());
        Assertions.assertEquals(16, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(128, image.imageWidth());
        Assertions.assertEquals(128, image.imageHeight());
        Assertions.assertEquals(8, image.pixelDepth());
        Assertions.assertEquals(0, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> UTC16 = header -> {

        Assertions.assertEquals(26, header.idLength());
        Assertions.assertEquals(ColorMapType.NO_COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.UNCOMPRESSED_TRUE_COLOR_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(0, colorMap.colorMapLength());
        Assertions.assertEquals(0, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(128, image.imageWidth());
        Assertions.assertEquals(128, image.imageHeight());
        Assertions.assertEquals(16, image.pixelDepth());
        Assertions.assertEquals(1, image.imageDescriptor());
        Assertions.assertEquals(1, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> UTC24 = header -> {

        Assertions.assertEquals(26, header.idLength());
        Assertions.assertEquals(ColorMapType.NO_COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.UNCOMPRESSED_TRUE_COLOR_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(0, colorMap.colorMapLength());
        Assertions.assertEquals(0, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(128, image.imageWidth());
        Assertions.assertEquals(128, image.imageHeight());
        Assertions.assertEquals(24, image.pixelDepth());
        Assertions.assertEquals(0, image.imageDescriptor());
        Assertions.assertEquals(0, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };

    private static final Consumer<Header> UTC32 = header -> {

        Assertions.assertEquals(26, header.idLength());
        Assertions.assertEquals(ColorMapType.NO_COLOR_MAP, header.colorMapType());
        Assertions.assertEquals(ImageType.UNCOMPRESSED_TRUE_COLOR_IMAGE, header.imageType());

        final ColorMapSpecification colorMap = header.colorMapSpecification();

        Assertions.assertEquals(0, colorMap.firstEntryIndex());
        Assertions.assertEquals(0, colorMap.colorMapLength());
        Assertions.assertEquals(0, colorMap.colorMapEntrySize());

        final ImageSpecification image = header.imageSpecification();

        Assertions.assertEquals(0, image.xOrigin());
        Assertions.assertEquals(0, image.yOrigin());
        Assertions.assertEquals(128, image.imageWidth());
        Assertions.assertEquals(128, image.imageHeight());
        Assertions.assertEquals(32, image.pixelDepth());
        Assertions.assertEquals(8, image.imageDescriptor());
        Assertions.assertEquals(8, image.getImageDescriptorAttributeBitsPerPixel());
        Assertions.assertTrue(image.isImageDescriptorBottom());
        Assertions.assertTrue(image.isImageDescriptorLeft());
    };
}
