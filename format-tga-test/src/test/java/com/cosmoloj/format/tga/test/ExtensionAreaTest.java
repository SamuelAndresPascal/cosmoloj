package com.cosmoloj.format.tga.test;

import com.cosmoloj.format.tga.ExtensionArea;
import com.cosmoloj.format.tga.ExtensionAreaReader;
import com.cosmoloj.format.tga.ExtensionAreaWriter;
import com.cosmoloj.format.tga.Footer;
import com.cosmoloj.format.tga.FooterReader;
import com.cosmoloj.format.tga.FooterWriter;
import com.cosmoloj.format.tga.Header;
import com.cosmoloj.format.tga.HeaderReader;
import com.cosmoloj.format.tga.HeaderWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
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
public class ExtensionAreaTest {

    static Stream<Arguments> tgaImageProvider() {
        return Stream.of(
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
    void readerTest(final String fileName, final Consumer<ExtensionArea> extensionTest)
            throws IOException, URISyntaxException {
        readerGenericTest(new File(ExtensionAreaTest.class.getResource(fileName).toURI()), extensionTest);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("tgaImageProvider")
    void writerTest(final String fileName, final Consumer<ExtensionArea> extensionTest)
            throws IOException, URISyntaxException {

        // fichier à écrire
        final File fileW = File.createTempFile("tgaWriterTest", ".tga");
        fileW.deleteOnExit();

        try (var channel = new FileInputStream(
                new File(ExtensionAreaTest.class.getResource(fileName).toURI())).getChannel();
                var headerReader = new HeaderReader(channel)) {

            final Header header = headerReader.read();

            final long footerPosition = Footer.footerPosition(channel.size());
            channel.position(footerPosition);

            try (var footerReader = new FooterReader(channel);
                    var extensionReader = new ExtensionAreaReader(channel, header.imageSpecification())) {

                final Footer footer = footerReader.read();
                final int extensionAreaPosition = footer.extensionAreaOffset();
                channel.position(extensionAreaPosition);

                final ExtensionArea extensionArea = extensionReader.read();

                try (var wChannel = new FileOutputStream(fileW, false).getChannel();
                        var headerWriter = new HeaderWriter(wChannel);
                        var footerWriter = new FooterWriter(wChannel);
                        var extensionAreaWriter = new ExtensionAreaWriter(wChannel, header.imageSpecification())) {

                    headerWriter.write(header);

                    wChannel.position(footerPosition);
                    footerWriter.write(footer);

                    wChannel.position(extensionAreaPosition);
                    extensionAreaWriter.write(extensionArea);
                }

                readerGenericTest(fileW, extensionTest);

            }
        } finally {
            if (fileW.exists()) {
                fileW.delete();
            }
        }
    }

    protected void readerGenericTest(final File file, final Consumer<ExtensionArea> extensionAreaTestConsumer)
            throws IOException {

        try (var channel = new FileInputStream(file).getChannel();
                var headerReader = new HeaderReader(channel)) {

            final Header header = headerReader.read();
            channel.position(Footer.footerPosition(channel.size()));

            try (var footerReader = new FooterReader(channel);
                    var extensionReader = new ExtensionAreaReader(channel, header.imageSpecification())) {

                final Footer footer = footerReader.read();
                channel.position(footer.extensionAreaOffset());

                final ExtensionArea extensionArea = extensionReader.read();
                extensionAreaTestConsumer.accept(extensionArea);
            }
        }
    }

    private static String toZeros(final int i) {
        final StringBuilder sb = new StringBuilder();
        for (int a = 0; a < i; a++) {
            sb.appendCodePoint(0);
        }
        return sb.toString();
    }

    private static final Consumer<ExtensionArea> CBW8 = extensionArea -> {
        Assertions.assertEquals(495, extensionArea.extensionSize(), "unexpected footer extension area size");
        Assertions.assertEquals("Ricky True                               ", extensionArea.authorName(),
                "unexpected footer extension area author name");
        Assertions.assertEquals("Sample 8 bit run length compressed black and white image" + toZeros(268),
                extensionArea.authorComments(),
                "unexpected footer extension area author comments");
        Assertions.assertArrayEquals(new short[]{3, 24, 1990, 10, 0, 0}, extensionArea.dateTimeStamp(),
                "unexpected footer extension area date/time stamp");
        Assertions.assertEquals("TGA Utilities                            ", extensionArea.jobName(),
                "unexpected footer extension area job name");
        Assertions.assertArrayEquals(new short[]{0, 0, 0}, extensionArea.jobTime(),
                "unexpected footer extension area job time");
        Assertions.assertEquals("TGAEdit                                  ", extensionArea.softwareId(),
                "unexpected footer extension area software id");
        Assertions.assertArrayEquals(new byte[]{-56, 0, 32}, extensionArea.softwareVersion(),
                "unexpected footer extension area software version");
        Assertions.assertEquals(0, extensionArea.keyColor(), "unexpected footer extension area key color");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.pixelAspectRatio(),
                "unexpected footer extension area pixel aspect ratio");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.gammaValue(),
                "unexpected footer extension area gamma value");
        Assertions.assertEquals(0, extensionArea.colorCorrectionOffset(),
                "unexpected footer extension area color correction offset");
        Assertions.assertEquals(4_140, extensionArea.postageStampOffset(),
                "unexpected footer extension area postage stamp offset");
        Assertions.assertEquals(0, extensionArea.scanLineOffset(),
                "unexpected footer extension area scanline offset");
        Assertions.assertEquals(0, extensionArea.attributesType(),
                "unexpected footer extension area attributes type");
        Assertions.assertNull(extensionArea.scanLineTable(), "unexpected footer extension area scanline table");
        Assertions.assertNotNull(extensionArea.postageStampImage(),
                "unexpected footer extension area postage stamp image");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[0],
                "unexpected footer extension area postage stamp image width");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[1],
                "unexpected footer extension area postage stamp image height");
        Assertions.assertEquals(64 * 64 + 2, extensionArea.postageStampImage().length,
                "unexpected footer extension area postage stamp image length");
        Assertions.assertNull(extensionArea.colorCorrectionTable(),
                "unexpected footer extension area color correction table");
    };

    private static final Consumer<ExtensionArea> CCM8 = extensionArea -> {
        Assertions.assertEquals(495, extensionArea.extensionSize(), "unexpected footer extension area size");
        Assertions.assertEquals("Ricky True                               ", extensionArea.authorName(),
                "unexpected footer extension area author name");
        Assertions.assertEquals("Sample 8 bit run length compressed color mapped image" + toZeros(271),
                extensionArea.authorComments(),
                "unexpected footer extension area author comments");
        Assertions.assertArrayEquals(new short[]{3, 24, 1990, 10, 0, 0}, extensionArea.dateTimeStamp(),
                "unexpected footer extension area date/time stamp");
        Assertions.assertEquals("TGA Utilities                            ", extensionArea.jobName(),
                "unexpected footer extension area job name");
        Assertions.assertArrayEquals(new short[]{0, 0, 0}, extensionArea.jobTime(),
                "unexpected footer extension area job time");
        Assertions.assertEquals("TGAEdit                                  ", extensionArea.softwareId(),
                "unexpected footer extension area software id");
        Assertions.assertArrayEquals(new byte[]{-56, 0, 32}, extensionArea.softwareVersion(),
                "unexpected footer extension area software version");
        Assertions.assertEquals(0, extensionArea.keyColor(),
                "unexpected footer extension area key color");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.pixelAspectRatio(),
                "unexpected footer extension area pixel aspect ratio");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.gammaValue(),
                "unexpected footer extension area gamma value");
        Assertions.assertEquals(0, extensionArea.colorCorrectionOffset(),
                "unexpected footer extension area color correction offset");
        Assertions.assertEquals(4_652, extensionArea.postageStampOffset(),
                "unexpected footer extension area postage stamp offset");
        Assertions.assertEquals(0, extensionArea.scanLineOffset(),
                "unexpected footer extension area scanline offset");
        Assertions.assertEquals(0, extensionArea.attributesType(),
                "unexpected footer extension area attributes type");
        Assertions.assertNull(extensionArea.scanLineTable(), "unexpected footer extension area scanline table");
        Assertions.assertNotNull(extensionArea.postageStampImage(),
                "unexpected footer extension area postage stamp image");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[0],
                "unexpected footer extension area postage stamp image width");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[1],
                "unexpected footer extension area postage stamp image height");
        Assertions.assertEquals(64 * 64 + 2, extensionArea.postageStampImage().length,
                "unexpected footer extension area postage stamp image length");
        Assertions.assertNull(extensionArea.colorCorrectionTable(),
                "unexpected footer extension area color correction table");
    };

    private static final Consumer<ExtensionArea> CTC24 = extensionArea -> {
        Assertions.assertEquals(495, extensionArea.extensionSize(), "unexpected footer extension area size");
        Assertions.assertEquals("Ricky True                               ", extensionArea.authorName(),
                "unexpected footer extension area author name");
        Assertions.assertEquals("Sample 24 bit run length compressed true color image" + toZeros(272),
                extensionArea.authorComments(),
                "unexpected footer extension area author comments");
        Assertions.assertArrayEquals(new short[]{3, 24, 1990, 10, 0, 0}, extensionArea.dateTimeStamp(),
                "unexpected footer extension area date/time stamp");
        Assertions.assertEquals("TGA Utilities                            ", extensionArea.jobName(),
                "unexpected footer extension area job name");
        Assertions.assertArrayEquals(new short[]{0, 0, 0}, extensionArea.jobTime(),
                "unexpected footer extension area job time");
        Assertions.assertEquals("TGAEdit                                  ", extensionArea.softwareId(),
                "unexpected footer extension area software id");
        Assertions.assertArrayEquals(new byte[]{-56, 0, 32}, extensionArea.softwareVersion(),
                "unexpected footer extension area software version");
        Assertions.assertEquals(0, extensionArea.keyColor(), "unexpected footer extension area key color");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.pixelAspectRatio(),
                "unexpected footer extension area pixel aspect ratio");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.gammaValue(),
                "unexpected footer extension area gamma value");
        Assertions.assertEquals(0, extensionArea.colorCorrectionOffset(),
                "unexpected footer extension area color correction offset");
        Assertions.assertEquals(8_236, extensionArea.postageStampOffset(),
                "unexpected footer extension area postage stamp offset");
        Assertions.assertEquals(0, extensionArea.scanLineOffset(),
                "unexpected footer extension area scanline offset");
        Assertions.assertEquals(0, extensionArea.attributesType(),
                "unexpected footer extension area attributes type");
        Assertions.assertNull(extensionArea.scanLineTable(),
                "unexpected footer extension area scanline table");
        Assertions.assertNotNull(extensionArea.postageStampImage(),
                "unexpected footer extension area postage stamp image");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[0],
                "unexpected footer extension area postage stamp image width");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[1],
                "unexpected footer extension area postage stamp image height");
        Assertions.assertEquals(64 * 64 + 2, extensionArea.postageStampImage().length,
                "unexpected footer extension area postage stamp image length");
        Assertions.assertNull(extensionArea.colorCorrectionTable(),
                "unexpected footer extension area color correction table");
    };

    private static final Consumer<ExtensionArea> UBW8 = extensionArea -> {
        Assertions.assertEquals(495, extensionArea.extensionSize(), "unexpected footer extension area size");
        Assertions.assertEquals("Ricky True                               ", extensionArea.authorName(),
                "unexpected footer extension area author name");
        Assertions.assertEquals("Sample 8 bit uncompressed black and white image" + toZeros(277),
                extensionArea.authorComments(),
                "unexpected footer extension area author comments");
        Assertions.assertArrayEquals(new short[]{2, 23, 1990, 10, 0, 0}, extensionArea.dateTimeStamp(),
                "unexpected footer extension area date/time stamp");
        Assertions.assertEquals("TGA Utilities                            ", extensionArea.jobName(),
                "unexpected footer extension area job name");
        Assertions.assertArrayEquals(new short[]{0, 0, 0}, extensionArea.jobTime(),
                "unexpected footer extension area job time");
        Assertions.assertEquals("TGAEdit                                  ", extensionArea.softwareId(),
                "unexpected footer extension area software id");
        Assertions.assertArrayEquals(new byte[]{-126, 0, 32}, extensionArea.softwareVersion(),
                "unexpected footer extension area software version");
        Assertions.assertEquals(0, extensionArea.keyColor(), "unexpected footer extension area key color");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.pixelAspectRatio(),
                "unexpected footer extension area pixel aspect ratio");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.gammaValue(),
                "unexpected footer extension area gamma value");
        Assertions.assertEquals(0, extensionArea.colorCorrectionOffset(),
                "unexpected footer extension area color correction offset");
        Assertions.assertEquals(16_428, extensionArea.postageStampOffset(),
                "unexpected footer extension area postage stamp offset");
        Assertions.assertEquals(0, extensionArea.scanLineOffset(),
                "unexpected footer extension area scanline offset");
        Assertions.assertEquals(0, extensionArea.attributesType(),
                "unexpected footer extension area attributes type");
        Assertions.assertNull(extensionArea.scanLineTable(), "unexpected footer extension area scanline table");
        Assertions.assertNotNull(extensionArea.postageStampImage(),
                "unexpected footer extension area postage stamp image");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[0],
                "unexpected footer extension area postage stamp image width");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[1],
                "unexpected footer extension area postage stamp image height");
        Assertions.assertEquals(64 * 64 + 2, extensionArea.postageStampImage().length,
                "unexpected footer extension area postage stamp image length");
        Assertions.assertNull(extensionArea.colorCorrectionTable(),
                "unexpected footer extension area color correction table");
    };

    private static final Consumer<ExtensionArea> UCM8 = extensionArea -> {
        Assertions.assertEquals(495, extensionArea.extensionSize(), "unexpected footer extension area size");
        Assertions.assertEquals("Ricky True                               ", extensionArea.authorName(),
                "unexpected footer extension area author name");
        Assertions.assertEquals("Sample 8 bit uncompressed color mapped image" + toZeros(280),
                extensionArea.authorComments(),
                "unexpected footer extension area author comments");
        Assertions.assertArrayEquals(new short[]{2, 24, 1990, 10, 0, 0}, extensionArea.dateTimeStamp(),
                "unexpected footer extension area date/time stamp");
        Assertions.assertEquals("TGA Utilities                            ", extensionArea.jobName(),
                "unexpected footer extension area job name");
        Assertions.assertArrayEquals(new short[]{0, 0, 0}, extensionArea.jobTime(),
                "unexpected footer extension area job time");
        Assertions.assertEquals("TGAEdit                                  ", extensionArea.softwareId(),
                "unexpected footer extension area software id");
        Assertions.assertArrayEquals(new byte[]{-116, 0, 32}, extensionArea.softwareVersion(),
                "unexpected footer extension area software version");
        Assertions.assertEquals(0, extensionArea.keyColor(), "unexpected footer extension area key color");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.pixelAspectRatio(),
                "unexpected footer extension area pixel aspect ratio");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.gammaValue(),
                "unexpected footer extension area gamma value");
        Assertions.assertEquals(0, extensionArea.colorCorrectionOffset(),
                "unexpected footer extension area color correction offset");
        Assertions.assertEquals(16_940, extensionArea.postageStampOffset(),
                "unexpected footer extension area postage stamp offset");
        Assertions.assertEquals(0, extensionArea.scanLineOffset(),
                "unexpected footer extension area scanline offset");
        Assertions.assertEquals(0, extensionArea.attributesType(),
                "unexpected footer extension area attributes type");
        Assertions.assertNull(extensionArea.scanLineTable(), "unexpected footer extension area scanline table");
        Assertions.assertNotNull(extensionArea.postageStampImage(),
                "unexpected footer extension area postage stamp image");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[0],
                "unexpected footer extension area postage stamp image width");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[1],
                "unexpected footer extension area postage stamp image height");
        Assertions.assertEquals(64 * 64 + 2, extensionArea.postageStampImage().length,
                "unexpected footer extension area postage stamp image length");
        Assertions.assertNull(extensionArea.colorCorrectionTable(),
                "unexpected footer extension area color correction table");
    };

    private static final Consumer<ExtensionArea> UTC16 = extensionArea -> {
        Assertions.assertEquals(495, extensionArea.extensionSize(), "unexpected footer extension area size");
        Assertions.assertEquals("Ricky True                               ", extensionArea.authorName(),
                "unexpected footer extension area author name");
        Assertions.assertEquals("Sample 16 bit uncompressed true color image" + toZeros(281),
                extensionArea.authorComments(),
                "unexpected footer extension area author comments");
        Assertions.assertArrayEquals(new short[]{2, 23, 1990, 10, 0, 0}, extensionArea.dateTimeStamp(),
                "unexpected footer extension area date/time stamp");
        Assertions.assertEquals("TGA Utilities                            ", extensionArea.jobName(),
                "unexpected footer extension area job name");
        Assertions.assertArrayEquals(new short[]{0, 0, 0}, extensionArea.jobTime(),
                "unexpected footer extension area job time");
        Assertions.assertEquals("TGAEdit                                  ", extensionArea.softwareId(),
                "unexpected footer extension area software id");
        Assertions.assertArrayEquals(new byte[]{-126, 0, 32}, extensionArea.softwareVersion(),
                "unexpected footer extension area software version");
        Assertions.assertEquals(0, extensionArea.keyColor(), "unexpected footer extension area key color");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.pixelAspectRatio(),
                "unexpected footer extension area pixel aspect ratio");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.gammaValue(),
                "unexpected footer extension area gamma value");
        Assertions.assertEquals(0, extensionArea.colorCorrectionOffset(),
                "unexpected footer extension area color correction offset");
        Assertions.assertEquals(32_812, extensionArea.postageStampOffset(),
                "unexpected footer extension area postage stamp offset");
        Assertions.assertEquals(0, extensionArea.scanLineOffset(),
                "unexpected footer extension area scanline offset");
        Assertions.assertEquals(2, extensionArea.attributesType(),
                "unexpected footer extension area attributes type");
        Assertions.assertNull(extensionArea.scanLineTable(), "unexpected footer extension area scanline table");
        Assertions.assertNotNull(extensionArea.postageStampImage(),
                "unexpected footer extension area postage stamp image");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[0],
                "unexpected footer extension area postage stamp image width");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[1],
                "unexpected footer extension area postage stamp image height");
        Assertions.assertEquals(64 * 64 + 2, extensionArea.postageStampImage().length,
                "unexpected footer extension area postage stamp image length");
        Assertions.assertNull(extensionArea.colorCorrectionTable(),
                "unexpected footer extension area color correction table");
    };

    private static final Consumer<ExtensionArea> UTC24 = extensionArea -> {
        Assertions.assertEquals(495, extensionArea.extensionSize(), "unexpected footer extension area size");
        Assertions.assertEquals("Ricky True                               ", extensionArea.authorName(),
                "unexpected footer extension area author name");
        Assertions.assertEquals("Sample 24 bit uncompressed true color image" + toZeros(281),
                extensionArea.authorComments(),
                "unexpected footer extension area author comments");
        Assertions.assertArrayEquals(new short[]{2, 24, 1990, 10, 0, 0}, extensionArea.dateTimeStamp(),
                "unexpected footer extension area date/time stamp");
        Assertions.assertEquals("TGA Utilities                            ", extensionArea.jobName(),
                "unexpected footer extension area job name");
        Assertions.assertArrayEquals(new short[]{0, 0, 0}, extensionArea.jobTime(),
                "unexpected footer extension area job time");
        Assertions.assertEquals("TGAEdit                                  ", extensionArea.softwareId(),
                "unexpected footer extension area software id");
        Assertions.assertArrayEquals(new byte[]{-116, 0, 32}, extensionArea.softwareVersion(),
                "unexpected footer extension area software version");
        Assertions.assertEquals(0, extensionArea.keyColor(),
                "unexpected footer extension area key color");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.pixelAspectRatio(),
                "unexpected footer extension area pixel aspect ratio");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.gammaValue(),
                "unexpected footer extension area gamma value");
        Assertions.assertEquals(0, extensionArea.colorCorrectionOffset(),
                "unexpected footer extension area color correction offset");
        Assertions.assertEquals(49_196, extensionArea.postageStampOffset(),
                "unexpected footer extension area postage stamp offset");
        Assertions.assertEquals(0, extensionArea.scanLineOffset(),
                "unexpected footer extension area scanline offset");
        Assertions.assertEquals(0, extensionArea.attributesType(),
                "unexpected footer extension area attributes type");
        Assertions.assertNull(extensionArea.scanLineTable(), "unexpected footer extension area scanline table");
        Assertions.assertNotNull(extensionArea.postageStampImage(),
                "unexpected footer extension area postage stamp image");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[0],
                "unexpected footer extension area postage stamp image width");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[1],
                "unexpected footer extension area postage stamp image height");
        Assertions.assertEquals(64 * 64 + 2, extensionArea.postageStampImage().length,
                "unexpected footer extension area postage stamp image length");
        Assertions.assertNull(extensionArea.colorCorrectionTable(),
                "unexpected footer extension area color correction table");
    };

    private static final Consumer<ExtensionArea> UTC32 = extensionArea -> {
        Assertions.assertEquals(495, extensionArea.extensionSize(), "unexpected footer extension area size");
        Assertions.assertEquals("Ricky True                               ", extensionArea.authorName(),
                "unexpected footer extension area author name");
        Assertions.assertEquals("Sample 32 bit uncompressed true color image" + toZeros(281),
                extensionArea.authorComments(),
                "unexpected footer extension area author comments");
        Assertions.assertArrayEquals(new short[]{2, 24, 1990, 10, 0, 0}, extensionArea.dateTimeStamp(),
                "unexpected footer extension area date/time stamp");
        Assertions.assertEquals("TGA Utilities                            ", extensionArea.jobName(),
                "unexpected footer extension area job name");
        Assertions.assertArrayEquals(new short[]{0, 0, 0}, extensionArea.jobTime(),
                "unexpected footer extension area job time");
        Assertions.assertEquals("TGAEdit                                  ", extensionArea.softwareId(),
                "unexpected footer extension area software id");
        Assertions.assertArrayEquals(new byte[]{-116, 0, 32}, extensionArea.softwareVersion(),
                "unexpected footer extension area software version");
        Assertions.assertEquals(0, extensionArea.keyColor(),
                "unexpected footer extension area key color");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.pixelAspectRatio(),
                "unexpected footer extension area pixel aspect ratio");
        Assertions.assertArrayEquals(new short[]{0, 0}, extensionArea.gammaValue(),
                "unexpected footer extension area gamma value");
        Assertions.assertEquals(0, extensionArea.colorCorrectionOffset(),
                "unexpected footer extension area color correction offset");
        Assertions.assertEquals(65_580, extensionArea.postageStampOffset(),
                "unexpected footer extension area postage stamp offset");
        Assertions.assertEquals(0, extensionArea.scanLineOffset(),
                "unexpected footer extension area scanline offset");
        Assertions.assertEquals(2, extensionArea.attributesType(),
                "unexpected footer extension area attributes type");
        Assertions.assertNull(extensionArea.scanLineTable(), "unexpected footer extension area scanline table");
        Assertions.assertNotNull(extensionArea.postageStampImage(),
                "unexpected footer extension area postage stamp image");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[0],
                "unexpected footer extension area postage stamp image width");
        Assertions.assertEquals(64, extensionArea.postageStampImage()[1],
                "unexpected footer extension area postage stamp image height");
        Assertions.assertEquals(64 * 64 + 2, extensionArea.postageStampImage().length,
                "unexpected footer extension area postage stamp image length");
        Assertions.assertNull(extensionArea.colorCorrectionTable(),
                "unexpected footer extension area color correction table");
    };
}
