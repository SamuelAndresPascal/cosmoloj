package com.cosmoloj.format.tga;

import com.cosmoloj.util.bin.BinaryUtil;
import com.cosmoloj.util.bin.FillOrder;
import com.cosmoloj.util.io.CodecReader;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Voir :
 * ComponentSampleModel
 * ColorModel
 *
 * @author Samuel Andrés
 */
public class DataReader implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
    private static final FillOrder FILL_ORDER = FillOrder.FROM_LOW;

    private final ReadableByteChannel channel;
    private final Header header;

    private final ImageSpecification imageSpecification;
    private final int colorDepth;
    private final int attributeBytes;
    private final int pixelAllocation;

    private final boolean fromTop;
    private final boolean fromLeft;
    private final short imageWidth;
    private final short imageHeight;
    private final byte[][] imageMap;

    private String imageId;
    private byte[][] image;

    public DataReader(final ReadableByteChannel channel, final Header header) throws IOException {
        this.channel = channel;
        this.header = header;

        imageSpecification = header.imageSpecification();
        colorDepth = header.getColorDepth();
        attributeBytes = imageSpecification.attributeBytes();

        // Spécification du "Field 8" : "Each pixel is stored as an integral number of bytes."
        pixelAllocation = BinaryUtil.bytesFor(imageSpecification.pixelDepth());

        fromTop = imageSpecification.isImageDescriptorTop();
        fromLeft = imageSpecification.isImageDescriptorLeft();
        imageWidth = imageSpecification.imageWidth();
        imageHeight = imageSpecification.imageHeight();



        if (!ColorMapType.NO_COLOR_MAP.equals(header.colorMapType())) {

            final ColorMapSpecification specification = header.colorMapSpecification();
             // Field 4.2 specifies the number of color map entries in this field
            final short entryNb = specification.colorMapLength();

            imageMap = new byte[entryNb][];
        } else {
            imageMap = new byte[0][];
        }
    }

    public BufferedImage getBufferedImage() {
        return getHeader().imageType().toBufferedImage(getImage(), getHeader(), getImageMap());
    }

    public void read() throws IOException {

        if (header.idLength() != 0) {
            final ByteBuffer buffer = ByteBuffer.allocate(header.idLength());
            buffer.order(BYTE_ORDER);
            buffer.clear();
            channel.read(buffer);
            buffer.flip();

            final byte[] dst = new byte[header.idLength()];
            buffer.get(dst);
            imageId = new String(dst, StandardCharsets.US_ASCII);
        }

        if (!ColorMapType.NO_COLOR_MAP.equals(header.colorMapType())) {

            final ColorMapSpecification specification = header.colorMapSpecification();
             // Field 4.2 specifies the number of color map entries in this field
            final short entryNb = specification.colorMapLength();
             // Field 4.3 specifies the width in bits of each color map entry
            final byte entrySize = specification.colorMapEntrySize();

            // Pour chaque entrée, on calcule la taille en bits de l'espace réservé à chaque couleurs
            // cette taille ne peut pas excéder 8 bits par couleur
            final int colorLength = Math.min(entrySize / 3, 8);

            /*
            Dans la spécification du champ 4, il est écrit :

            "When working with VDA or VDA/D cards it is preferred that you
            select 16 bits (5 bits per primary with 1 bit to select interrupt control)
            and set the 16th bit to 0 so that the interrupt bit is disabled.  Even if this
            field is set to 15 bits (5 bits per primary) you must still parse the color
            map data 16 bits at a time and ignore the 16th bit."

            D'autre part, dans la spécification du champ 7, il est écrit :

            "Each color map entry is stored using an integral number of bytes."

            Il faut donc prévoir de lire un nombre entier de bytes pour chaque entrée
            */
            final int entryBytes = BinaryUtil.bytesFor(entrySize);

            final ByteBuffer buffer = ByteBuffer.allocate(entryNb * entryBytes);
            buffer.order(BYTE_ORDER);
            buffer.clear();
            channel.read(buffer);
            buffer.flip();

            // parcours des entrées de la map
            for (short i = 0; i < imageMap.length; i++) {

                final byte[] byteContent = new byte[entryBytes];
                // à lire bleu, vert, rouge
                buffer.get(byteContent);
                LOG.debug("map content : {}", byteContent);
                imageMap[i] = FILL_ORDER.expand(byteContent, colorLength, BYTE_ORDER);
                LOG.debug("map content parsed : {}", imageMap[i]);
            }
        }


        /*
        TGA File Format
        RUN-LENGTH ENCODING OF IMAGES
        Some of the Image Types described above are stored using a compression algorithm called Run-
        length Encoding.  This type of encoding is used with file types 9 (Run-length Encoded Color-
        mapped Images), 10 (Run-length Encoded True-Color Images) and 11 (Run-length Encoded Black-
        and-white Images).
        Run-length encoding takes advantage of the fact that many types of images have large sections in
        which the pixel values are all the same.  This situation occurs more often in graphic images as
        opposed to captured, video images.  In those images where large areas contain pixels of the same
        value, run-length encoding can greatly reduce the size of the stored image.
        Run-length encoded (RLE) images comprise two types of data elements: Run-length Packets and
        Raw Packets.
        The first field (1 byte) of each packet is called the Repetition Count field.  The second field is called
        the Pixel Value field.  For Run-length Packets, the Pixel Value field contains a single pixel value.
        For Raw Packets, the field is a variable number of pixel values.
        The highest order bit of the Repetition Count indicates whether the packet is a Raw Packet or a Run-
        length Packet.  If bit 7 of the Repetition Count is set to 1, then the packet is a Run-length Packet.  If
        bit 7 is set to zero, then the packet is a Raw Packet.
        The lower 7 bits of the Repetition Count specify how many pixel values are represented by the
        packet.  In the case of a Run-length packet, this count indicates how many successive pixels have the
        pixel value specified by the Pixel Value field.  For Raw Packets, the Repetition Count specifies how
        many pixel values are actually contained in the next field.  This 7 bit value is actually encoded as 1
        less than the number of pixels in the packet (a value of 0 implies 1 pixel while a value of 0x7F
        implies 128 pixels).
        Run-length Packets should never encode pixels from more than one scan line.  Even if the end of one
        scan line and the beginning of the next contain pixels of the same value, the two should be encoded
        as separate packets.  In other words, Run-length Packets should not wrap from one line to another.
        This scheme allows software to create and use a scan line table for rapid, random access of
        individual lines.  Scan line tables are discussed in further detail in the Extension Area section of this
        document.
        As an example of the difference between the two packet types, consider a section of a single scan
        line with 128, 24-bit (3 byte) pixels all with the same value (color).  The Raw Packet would require
        1 byte for the Repetition Count and 128 pixels values each being 3 bytes long (384 bytes).  The total
        number of bytes required to specify the chosen data using the Raw Packet is, therefore, 385 bytes.
        The Run-length Packet would require 1 byte for the Repetition Count and a single, 3-byte pixel
        value.  The total number of bytes required to specify the chosen data using the Run-length Packet is,
        therefore, just 4 bytes!
        */

        this.image = new byte[imageHeight * imageWidth][header.imageType().getColorBandNumber() + attributeBytes];

        final CodecReader dataReader;
        if (header.imageType().getValue() < ImageType.RLE_ENCODED_COLOR_MAPPED_IMAGE.getValue()) {
            dataReader = new ReaderCodecUncompressed(channel, BYTE_ORDER, pixelAllocation,
                    this::dataToPixel, imageWidth, imageHeight);
        } else if (header.imageType() != ImageType.NO_IMAGE_DATA_INCLUDED) {
            dataReader = new ReaderCodecRle(channel, BYTE_ORDER, pixelAllocation, this::dataToPixel,
                    imageWidth, imageHeight);
        } else {
            return;
        }
        dataReader.read(this::loadPixelValue);
    }

    private byte[] dataToPixel(final byte[] data) {
        return expandBytes(data, attributeBytes, colorDepth);
    }

    private void loadPixelValue(final byte[] pixelValue, final int[] coordinates) {
        final int line = coordinates[0];
        final int column = coordinates[1];
        final int h = fromTop ? line : imageHeight - 1 - line;
        final int w = fromLeft ? column : imageWidth - 1 - column;
        System.arraycopy(pixelValue, 0, this.image[h * imageWidth + w], 0, pixelValue.length);
    }

    private static byte[] expandBytes(final byte[] pixelValue, final int attributeBytes, final int colorDepth) {
        return attributeBytes == 0 && colorDepth == Byte.SIZE
                ? pixelValue : FILL_ORDER.expand(pixelValue, colorDepth, BYTE_ORDER);
    }

    public Header getHeader() {
        return header;
    }

    public String getImageId() {
        return imageId;
    }

    public byte[][] getImage() {
        return image;
    }

    public byte[][] getImageMap() {
        return imageMap;
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }
}
