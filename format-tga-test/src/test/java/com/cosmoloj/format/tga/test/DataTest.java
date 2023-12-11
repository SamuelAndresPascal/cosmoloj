package com.cosmoloj.format.tga.test;

import com.cosmoloj.format.tga.DataReader;
import com.cosmoloj.format.tga.DataWriter;
import com.cosmoloj.format.tga.HeaderReader;
import com.cosmoloj.format.tga.HeaderWriter;
import com.cosmoloj.util.java2d.ImageViewer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
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
public class DataTest {

    public static void main(final String[] args) throws URISyntaxException, IOException {

        final List<String> inputs = List.of("utc16.tga", "utc24.tga", "utc32.tga", "ubw8.tga", "cbw8.tga", "ctc24.tga",
                "ucm8.tga", "ccm8.tga");

        ImageViewer.view(inputs.stream()
                .map(input -> {

                    try (var channel = new FileInputStream(new File(DataTest.class.getResource(input).toURI()))
                            .getChannel();
                            var headerReader = new HeaderReader(channel);
                            var reader = new DataReader(channel, headerReader.read())) {
                        reader.read();
                        return Map.entry(input, (Image) reader.getBufferedImage());
                    } catch (final URISyntaxException | IOException ex) {
                        return null;
                    }
                })
                .toList());
    }

    static Stream<Arguments> tgaImageProvider() {
        return Stream.of(
            Arguments.arguments("sampleBW.tga", BW, null, null),
            Arguments.arguments("sampleColorMap.tga", COLOR_MAP, COLOR_MAP_MAP, null),
            Arguments.arguments("sampleCompressed.tga", COMPRESSED, null, null),
            Arguments.arguments("sampleUncompressedBL.tga", UNCOMPRESSED_BL, null, null),
            Arguments.arguments("sampleUncompressedBLLarge.tga", UNCOMPRESSED_BL_LARGE, null, null),
            Arguments.arguments("sampleUncompressedBW.tga", UNCOMPRESSED_BW, null, null),
        Arguments.arguments("sampleUncompressedColorMap.tga", UNCOMPRESSED_COLOR_MAP, UNCOMPRESSED_COLOR_MAP_MAP, null),
            Arguments.arguments("sampleUncompressedTL.tga", UNCOMPRESSED_TL, null, null),
            Arguments.arguments("cbw8.tga", CBW8, null, TRUEVISION_ID),
            Arguments.arguments("ccm8.tga", CCM8, CCM8_MAP, TRUEVISION_ID),
            Arguments.arguments("ctc24.tga", CTC24, null, TRUEVISION_ID),
            Arguments.arguments("ubw8.tga", UBW8, null, TRUEVISION_ID),
            Arguments.arguments("ucm8.tga", UCM8, UCM8_MAP, TRUEVISION_ID),
            Arguments.arguments("utc16.tga", UTC16, null, TRUEVISION_ID),
            Arguments.arguments("utc24.tga", UTC24, null, TRUEVISION_ID),
            Arguments.arguments("utc32.tga", UTC32, null, TRUEVISION_ID)
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("tgaImageProvider")
    void readerTest(final String file, final Consumer<byte[][]> imageTest,
            final Consumer<byte[][]> imageMapTest, final Consumer<String> imageIdTest)
            throws IOException, URISyntaxException {
        readerGenericTest(new File(DataTest.class.getResource(file).toURI()), imageTest, imageMapTest, imageIdTest);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("tgaImageProvider")
    void writerTest(final String fileName, final Consumer<byte[][]> imageTest,
            final Consumer<byte[][]> imageMapTest, final Consumer<String> imageIdTest)
            throws IOException, URISyntaxException {

        // fichier à écrire
        final File fileW = File.createTempFile("tgaWriterTest", ".tga");
        fileW.deleteOnExit();

        try (var rChannel = new FileInputStream(new File(DataTest.class.getResource(fileName).toURI())).getChannel();
                var headerReader = new HeaderReader(rChannel);
                var reader = new DataReader(rChannel, headerReader.read())) {

            reader.read();

            try (var wChannel = new FileOutputStream(fileW, false).getChannel();
                    var headerWriter = new HeaderWriter(wChannel);
                    var writer = new DataWriter(wChannel, reader.getHeader())) {

                headerWriter.write(reader.getHeader());
                writer.write(reader.getImageId(), reader.getImageMap(), reader.getImage());
            }

            readerGenericTest(fileW, imageTest, imageMapTest, imageIdTest);
        } finally {
            if (fileW.exists()) {
                fileW.delete();
            }
        }
    }

    private BufferedImage readerGenericTest(final File file, final Consumer<byte[][]> imageTest,
            final Consumer<byte[][]> imageMapTest, final Consumer<String> imageIdTest) throws IOException {

        try (var channel = new FileInputStream(file).getChannel();
                var headerReader = new HeaderReader(channel);
                var dataReader = new DataReader(channel, headerReader.read())) {

            dataReader.read();

            if (imageIdTest == null) {
                Assertions.assertNull(dataReader.getImageId(), "image id should be null");
            } else {
                imageIdTest.accept(dataReader.getImageId());
            }

            if (imageMapTest == null) {
                Assertions.assertEquals(0, dataReader.getImageMap().length, "image map should be empty");
            } else {
                imageMapTest.accept(dataReader.getImageMap());
            }

            final byte[][] data = dataReader.getImage();
            imageTest.accept(data);
            return dataReader.getHeader().imageType()
                    .toBufferedImage(data, dataReader.getHeader(), dataReader.getImageMap());
        }
    }

    private static final Consumer<byte[][]> BW = image -> {

        final int h = 2;
        final int w = 2;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(1, image1.length, "");
        }

        // en byte signé
        Assertions.assertArrayEquals(new byte[]{-1}, image[index(0, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{100}, image[index(0, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{0}, image[index(1, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{-56}, image[index(1, w, 1)], "");

        // en int cast byte :
        Assertions.assertArrayEquals(new byte[]{(byte) 255}, image[index(0, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{100}, image[index(0, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{0}, image[index(1, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{(byte) 200}, image[index(1, w, 1)], "");
    };

    private static final Consumer<byte[][]> COLOR_MAP_MAP = imageMap -> {
        Assertions.assertEquals(4, imageMap.length, "");
        Assertions.assertArrayEquals(new byte[]{0, 0, 0}, imageMap[0], "");
        Assertions.assertArrayEquals(new byte[]{-1, 0, 0}, imageMap[1], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, imageMap[2], "");
        Assertions.assertArrayEquals(new byte[]{0, -1, 0}, imageMap[3], "");
    };

    private static final Consumer<byte[][]> COLOR_MAP = image -> {

        final int h = 2;
        final int w = 2;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(1, image1.length, "");
        }

        Assertions.assertArrayEquals(new byte[]{2}, image[index(0, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{3}, image[index(0, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{0}, image[index(1, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{1}, image[index(1, w, 1)], "");
    };

    private static final Consumer<byte[][]> COMPRESSED = image -> {

        final int h = 2;
        final int w = 2;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(3, image1.length, "");
        }

        Assertions.assertArrayEquals(new byte[]{-1, 0, 0}, image[index(0, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, 0}, image[index(0, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, image[index(1, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{0, -1, 0}, image[index(1, w, 1)], "");
    };

    private static final Consumer<byte[][]> UNCOMPRESSED_BL = image -> {

        final int h = 2;
        final int w = 2;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(3, image1.length, "");
        }

        Assertions.assertArrayEquals(new byte[]{-1, 0, 0}, image[index(0, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, 0}, image[index(0, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, image[index(1, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{0, -1, 0}, image[index(1, w, 1)], "");
    };

    private static final Consumer<byte[][]> UNCOMPRESSED_BL_LARGE = image -> {

        final int h = 2;
        final int w = 4;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(3, image1.length, "");
        }

        Assertions.assertArrayEquals(new byte[]{-1, 0, 0}, image[index(0, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, 0}, image[index(0, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{-1, -1, -1}, image[index(0, w, 2)], "");
        Assertions.assertArrayEquals(new byte[]{0, -1, -1}, image[index(0, w, 3)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, image[index(1, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{0, -1, 0}, image[index(1, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{-1, -1, 0}, image[index(1, w, 2)], "");
        Assertions.assertArrayEquals(new byte[]{100, 100, 100}, image[index(1, w, 3)], "");
    };

    private static final Consumer<byte[][]> UNCOMPRESSED_BW = image -> {

        final int h = 2;
        final int w = 2;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(1, image1.length, "");
        }

        // en byte signé
        Assertions.assertArrayEquals(new byte[]{-1}, image[index(0, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{100}, image[index(0, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{0}, image[index(1, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{-56}, image[index(1, w, 1)], "");

        // en int cast byte :
        Assertions.assertArrayEquals(new byte[]{(byte) 255}, image[index(0, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{100}, image[index(0, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{0}, image[index(1, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{(byte) 200}, image[index(1, w, 1)], "");
    };

    private static final Consumer<byte[][]> UNCOMPRESSED_COLOR_MAP_MAP = imageMap -> {
        Assertions.assertEquals(4, imageMap.length, "");
        Assertions.assertArrayEquals(new byte[]{0, 0, 0}, imageMap[0], "");
        Assertions.assertArrayEquals(new byte[]{-1, 0, 0}, imageMap[1], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, imageMap[2], "");
        Assertions.assertArrayEquals(new byte[]{0, -1, 0}, imageMap[3], "");
    };

    private static final Consumer<byte[][]> UNCOMPRESSED_COLOR_MAP = image -> {

        final int h = 2;
        final int w = 2;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(1, image1.length, "");
        }

        Assertions.assertArrayEquals(new byte[]{2}, image[index(0, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{3}, image[index(0, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{0}, image[index(1, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{1}, image[index(1, w, 1)], "");
    };

    private static final Consumer<byte[][]> UNCOMPRESSED_TL = image -> {

        final int h = 2;
        final int w = 2;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(3, image1.length, "");
        }

        Assertions.assertArrayEquals(new byte[]{-1, 0, 0}, image[index(0, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, 0}, image[index(0, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, image[index(1, w, 0)], "");
        Assertions.assertArrayEquals(new byte[]{0, -1, 0}, image[index(1, w, 1)], "");
    };

    private static final Consumer<String> TRUEVISION_ID
            = (id) -> Assertions.assertEquals("Truevision(R) Sample Image", id, "");

    private static final Consumer<byte[][]> CBW8 = image -> {

        final int h = 128;
        final int w = 128;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(1, image1.length, "");
        }

        final byte[] values = new byte[]{76, -107, -78, 0, 76, -107, -78, -2};

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Assertions.assertEquals(values[getValueIndex(j)], image[index(i, w, j)][0],
                        "error at index (" + i + "," + j + ")");
            }
        }
    };

    private static final Consumer<byte[][]> CCM8_MAP = imageMap -> {
        Assertions.assertEquals(256, imageMap.length, "");
        // sur 16 bits on a trois couleurs chacune sur 5 bits et un bit d'attribut
        for (final byte[] imageMap1 : imageMap) {
            Assertions.assertEquals(3 + 1, imageMap1.length, "");
        }
    };

    private static final Consumer<byte[][]> CCM8 = image -> {

        final int h = 128;
        final int w = 128;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(1, image1.length, "");
        }

        final byte[] values = new byte[]{64, -128, -64, 0, 64, -128, -64, -1};

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Assertions.assertEquals(values[getValueIndex(j)], image[index(i, w, j)][0],
                        "error at index (" + i + "," + j + ")");
            }
        }
    };

    private static final Consumer<byte[][]> CTC24 = image -> {

        final int h = 128;
        final int w = 128;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(3, image1.length, "");
        }

        final byte[][] values = new byte[][]{
            {0, 0, -1},
            {0, -1, 0},
            {-1, 0, 0},
            {0, 0, 0},
            {0, 0, -1},
            {0, -1, 0},
            {-1, 0, 0},
            {-1, -1, -1}};

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Assertions.assertArrayEquals(values[getValueIndex(j)], image[index(i, w, j)],
                        "error at index (" + i + "," + j + ")");
            }
        }
    };

    private static final Consumer<byte[][]> UBW8 = image -> {

        final int h = 128;
        final int w = 128;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(1, image1.length, "");
        }

        final byte[] values = new byte[]{76, -107, -78, 0, 76, -107, -78, -2};

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Assertions.assertEquals(values[getValueIndex(j)], image[index(i, w, j)][0],
                        "error at index (" + i + "," + j + ")");
            }
        }
    };

    private static final Consumer<byte[][]> UCM8_MAP = imageMap -> {
        Assertions.assertEquals(256, imageMap.length, "");
        // sur 16 bits on a trois couleurs chacune sur 5 bits et un bit d'attribut
        for (final byte[] imageMap1 : imageMap) {
            Assertions.assertEquals(3 + 1, imageMap1.length, "");
        }
    };

    private static final Consumer<byte[][]> UCM8 = image -> {

        final int h = 128;
        final int w = 128;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(1, image1.length, "");
        }

        final byte[] values = new byte[]{64, -128, -64, 0, 64, -128, -64, -1};

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Assertions.assertEquals(values[getValueIndex(j)], image[index(i, w, j)][0],
                        "error at index (" + i + "," + j + ")");
            }
        }
    };

    private static final Consumer<byte[][]> UTC16 = image -> {

        final int h = 128;
        final int w = 128;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(4, image1.length, "");
        }

        final byte[][] values = new byte[][]{
            {0, 0, 31, 0},
            {0, 31, 0, 0},
            {31, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 31, 0},
            {0, 31, 0, 0},
            {31, 0, 0, 0},
            {31, 31, 31, 0}};

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Assertions.assertArrayEquals(values[getValueIndex(j)], image[index(i, w, j)],
                        "error at index (" + i + "," + j + ")");
            }
        }

        Assertions.assertEquals(4, image[index(0, w, 0)].length, "");
        Assertions.assertArrayEquals(new byte[]{0, 0, 31, 0}, image[index(0, w, 0)], ""); // rouge
        Assertions.assertArrayEquals(new byte[]{0, 0, 31, 0}, image[index(0, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, 31, 0}, image[index(0, w, 2)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, 31, 0}, image[index(0, w, 3)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, 31, 0}, image[index(0, w, 4)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, 31, 0}, image[index(0, w, 5)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, 31, 0}, image[index(0, w, 6)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, 31, 0}, image[index(0, w, 7)], "");
        Assertions.assertArrayEquals(new byte[]{0, 31, 0, 0}, image[index(0, w, 8)], ""); // on passe au vert
    };

    private static final Consumer<byte[][]> UTC24 = image -> {

        final int h = 128;
        final int w = 128;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(3, image1.length, "");
        }

        final byte[][] values = new byte[][]{
            {0, 0, -1},
            {0, -1, 0},
            {-1, 0, 0},
            {0, 0, 0},
            {0, 0, -1},
            {0, -1, 0},
            {-1, 0, 0},
            {-1, -1, -1}};

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Assertions.assertArrayEquals(values[getValueIndex(j)], image[index(i, w, j)],
                        "error at index (" + i + "," + j + ")");
            }
        }

        Assertions.assertEquals(3, image[index(0, w, 0)].length, "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, image[index(0, w, 0)], ""); // rouge
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, image[index(0, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, image[index(0, w, 2)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, image[index(0, w, 3)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, image[index(0, w, 4)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, image[index(0, w, 5)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, image[index(0, w, 6)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1}, image[index(0, w, 7)], "");
        Assertions.assertArrayEquals(new byte[]{0, -1, 0}, image[index(0, w, 8)], ""); // on passe au vert
    };

    private static final Consumer<byte[][]> UTC32 = image -> {

        final int h = 128;
        final int w = 128;

        // dimensions
        Assertions.assertEquals(h * w, image.length, "");

        // profondeur des pixels
        for (final byte[] image1 : image) {
            Assertions.assertEquals(4, image1.length, "");
        }

        final byte[][] values = new byte[][]{
            {0, 0, -1, 0},
            {0, -1, 0, 0},
            {-1, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, -1, 0},
            {0, -1, 0, 0},
            {-1, 0, 0, 0},
            {-1, -1, -1, 0}};

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Assertions.assertArrayEquals(values[getValueIndex(j)], image[index(i, w, j)],
                        "error at index (" + i + "," + j + ")");
            }
        }

        Assertions.assertEquals(4, image[index(0, w, 0)].length, "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1, 0}, image[index(0, w, 0)], ""); // rouge
        Assertions.assertArrayEquals(new byte[]{0, 0, -1, 0}, image[index(0, w, 1)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1, 0}, image[index(0, w, 2)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1, 0}, image[index(0, w, 3)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1, 0}, image[index(0, w, 4)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1, 0}, image[index(0, w, 5)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1, 0}, image[index(0, w, 6)], "");
        Assertions.assertArrayEquals(new byte[]{0, 0, -1, 0}, image[index(0, w, 7)], "");
        Assertions.assertArrayEquals(new byte[]{0, -1, 0, 0}, image[index(0, w, 8)], ""); // on passe au vert
    };

    private static int getValueIndex(final int column) {
        return (column / 8) > 7 ? (column / 8) % 8 : column / 8;
    }

    private static int index(final int l, final int w, final int c) {
        return l * w + c;
    }
}
