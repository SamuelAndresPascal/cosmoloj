package com.cosmoloj.format.tga;

import com.cosmoloj.util.bin.BinaryUtil;
import com.cosmoloj.util.io.CodecWriter;
import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


/**
 *
 * @author Samuel Andrés
 */
public class DataWriter implements Closeable {

    private final WritableByteChannel channel;
    private final Header header;

    private final ImageSpecification imageSpecification;
    private final int colorDepth;
    private final int attributeBytes;
    private final int pixelAllocation;
    private final int[] oldSizes;

    private final boolean fromTop;
    private final boolean fromLeft;
    private final short imageWidth;
    private final short imageHeight;

    public DataWriter(final WritableByteChannel channel, final Header header) throws IOException {
        this.channel = channel;
        this.header = header;
        this.imageSpecification = header.imageSpecification();
        colorDepth = header.getColorDepth();
        attributeBytes = imageSpecification.attributeBytes();

        oldSizes = oldSizes(header.imageType().getColorBandNumber(), colorDepth, attributeBytes);

        // Spécification du "Field 8" : "Each pixel is stored as an integral number of bytes."
        pixelAllocation = BinaryUtil.bytesFor(imageSpecification.pixelDepth());

        fromTop = imageSpecification.isImageDescriptorTop();
        fromLeft = imageSpecification.isImageDescriptorLeft();
        imageWidth = imageSpecification.imageWidth();
        imageHeight = imageSpecification.imageHeight();
    }

    public void write(final String imageId, final byte[][] imageMap, final byte[][] image) throws IOException {

        if (imageId != null) {
            final byte[] imageIdBytes = imageId.getBytes(StandardCharsets.US_ASCII);
            final ByteBuffer buffer = ByteBuffer.allocate(imageIdBytes.length);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.put(imageIdBytes);
            buffer.flip();
            channel.write(buffer);
        }

        if (header.colorMapType() != ColorMapType.NO_COLOR_MAP) {

            final ColorMapSpecification colorMapSpecification = header.colorMapSpecification();
            // Field 4.2 specifies the number of color map entries in this field
            final short entryNb = colorMapSpecification.colorMapLength();
            // Field 4.3 specifies the width in bits of each color map entry
            final byte entrySize = colorMapSpecification.colorMapEntrySize();

            final ByteBuffer buffer = ByteBuffer.allocate(entryNb * BinaryUtil.bytesFor(entrySize));
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.clear();

            // Pour chaque entrée, on calcule la taille en bits de l'espace réservé à chaque couleurs
            // cette taille ne peut pas excéder 8 bits par couleur
            final int colorLength = Math.min(entrySize / 3, 8);

            // on détecte le nombre de bits à ignorer
            final int remainingBytes = entrySize - (colorLength * 3);

            // on sait qu'on a 3 bandes et que les bits supplémentaires peuvent être ignorés
            final int[] oldSizesMap = oldSizes(3, colorLength, remainingBytes);

            // parcours des entrées de la map
            for (short i = 0; i < imageMap.length; i++) {
                buffer.put(pixelToData(imageMap[i], remainingBytes, colorLength, oldSizesMap));
            }
            buffer.flip();
            channel.write(buffer);
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

        final CodecWriter dataWriter;
        if (header.imageType().getValue() < ImageType.RLE_ENCODED_COLOR_MAPPED_IMAGE.getValue()) {
            dataWriter = new WriterCodecUncompressed(channel, ByteOrder.LITTLE_ENDIAN, pixelAllocation,
                    this::pixelToData, imageWidth, imageHeight);
        } else if (header.imageType() != ImageType.NO_IMAGE_DATA_INCLUDED) {
            dataWriter = new WriterCodecRle(channel, ByteOrder.LITTLE_ENDIAN, pixelAllocation, this::pixelToData,
                    imageWidth, imageHeight);
        } else {
            return;
        }
        dataWriter.write(c -> {
            final int line = c[0];
            final int column = c[1];
            final int h = fromTop ? line : imageHeight - 1 - line;
            final int w = fromLeft ? column : imageWidth - 1 - column;
            return image[h * imageWidth + w];
        });
    }

    private static int[] oldSizes(final int colorBands, final int colorDepth, final int attributeBytes) {
        final int[] oldSizes = new int[colorBands + (attributeBytes == 0 ? 0 : 1)];
        Arrays.fill(oldSizes, colorDepth);
        if (attributeBytes != 0) {
            oldSizes[oldSizes.length - 1] = attributeBytes;
        }
        return oldSizes;
    }

    private byte[] pixelToData(final byte[] pixelValue) {
        return pixelToData(pixelValue, attributeBytes, colorDepth, oldSizes);
    }

    private static byte[] pixelToData(final byte[] pixelValue, final int attributeBytes, final int colorDepth,
            final int[] oldSizes) {
        if (attributeBytes == 0 && colorDepth == Byte.SIZE) {
            return pixelValue;
        } else if (attributeBytes == 0 || attributeBytes == colorDepth) {
            return BinaryUtil.compand(pixelValue, colorDepth);
        } else {
            return BinaryUtil.compand(pixelValue, oldSizes);
        }
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }
}
