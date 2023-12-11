package com.cosmoloj.format.tga.test;

import com.cosmoloj.format.tga.Footer;
import com.cosmoloj.format.tga.FooterReader;
import com.cosmoloj.format.tga.FooterWriter;
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
public class FooterTest {

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
    void readerTest(final String fileName, final Consumer<Footer> footerTest)
            throws IOException, URISyntaxException {
        readerGenericTest(new File(FooterTest.class.getResource(fileName).toURI()), footerTest);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("tgaImageProvider")
    void writerTest(final String fileName, final Consumer<Footer> footerTest)
            throws IOException, URISyntaxException {

        // fichier à écrire
        final File fileW = File.createTempFile("tgaWriterTest", ".tga");
        fileW.deleteOnExit();

        try (var channel = new FileInputStream(
                new File(FooterTest.class.getResource(fileName).toURI())).getChannel();
                var footerReader = new FooterReader(channel)) {

            final long footerPosition = Footer.footerPosition(channel.size());
            channel.position(footerPosition);
            final Footer footer = footerReader.read();

            try (var wChannel = new FileOutputStream(fileW, false).getChannel();
                    var footerWriter = new FooterWriter(wChannel)) {
                wChannel.position(footerPosition);
                footerWriter.write(footer);
            }

            readerGenericTest(fileW, footerTest);
        } finally {
            if (fileW.exists()) {
                fileW.delete();
            }
        }
    }

    private void readerGenericTest(final File file, final Consumer<Footer> footerTest) throws IOException {
        try (var channel = new FileInputStream(file).getChannel();
                var footerReader = new FooterReader(channel)) {

            channel.position(Footer.footerPosition(channel.size()));
            footerTest.accept(footerReader.read());
        }
    }

    private static final Consumer<Footer> BW = footer -> {
        Assertions.assertEquals(0, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> COLOR_MAP = footer -> {
        Assertions.assertEquals(0, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> COMPRESSED = footer -> {
        Assertions.assertEquals(0, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> UNCOMPRESSED_BL = footer -> {
        Assertions.assertEquals(0, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> UNCOMPRESSED_BL_LARGE = footer -> {
        Assertions.assertEquals(0, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> UNCOMPRESSED_BW = footer -> {
        Assertions.assertEquals(0, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> UNCOMPRESSED_COLOR_MAP = footer -> {
        Assertions.assertEquals(0, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> UNCOMPRESSED_TL = footer -> {
        Assertions.assertEquals(0, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> CBW8 = footer -> {
        Assertions.assertEquals(8238, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> CCM8 = footer -> {
        Assertions.assertEquals(8750, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> CTC24 = footer -> {
        Assertions.assertEquals(20526, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> UBW8 = footer -> {
        Assertions.assertEquals(20526, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> UCM8 = footer -> {
        Assertions.assertEquals(21038, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> UTC16 = footer -> {
        Assertions.assertEquals(41006, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> UTC24 = footer -> {
        Assertions.assertEquals(61486, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };

    private static final Consumer<Footer> UTC32 = footer -> {
        Assertions.assertEquals(81966, footer.extensionAreaOffset(), "unexpected footer extension area offset");
        Assertions.assertEquals(0, footer.developerDirectoryOffset(), "unexpected footer developer area offset");
        Assertions.assertEquals("TRUEVISION-XFILE", footer.signature(), "unexpected footer signature");
        Assertions.assertEquals('.', (char) footer.reservedChar(), "unexpected footer reserved char");
        Assertions.assertEquals(0, footer.zero(), "unexpected footer zero (last byte)");
    };
}
