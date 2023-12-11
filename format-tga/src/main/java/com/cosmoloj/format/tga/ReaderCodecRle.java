package com.cosmoloj.format.tga;

import com.cosmoloj.util.io.CodecReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;

/**
 *
 * @author Samuel Andrés
 */
public class ReaderCodecRle implements CodecReader {

    private final ReadableByteChannel channel;
    private final ByteOrder byteOrder;
    private final int pixelAllocation;
    private final UnaryOperator<byte[]> dataToPixel;
    private final int imageWidth;
    private final int imageHeight;

    public ReaderCodecRle(final ReadableByteChannel channel, final ByteOrder byteOrder, final int pixelAllocation,
            final UnaryOperator<byte[]> dataToPixel, final int imageWidth, final int imageHeight) {
        this.channel = channel;
        this.byteOrder = byteOrder;
        this.pixelAllocation = pixelAllocation;
        this.dataToPixel = dataToPixel;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    @Override
    public void read(final BiConsumer<byte[], int[]> pixelConsumer) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(Byte.BYTES);
        buffer.order(byteOrder);
        buffer.clear();
        channel.read(buffer);
        buffer.flip();

        /*
        The first field (1 byte) of each packet is called the Repetition Count field.  The second field is called
        the Pixel Value field.  For Run-length Packets, the Pixel Value field contains a single pixel value.
        For Raw Packets, the field is a variable number of pixel values.
        */
        byte repetitionCount = buffer.get();

        /*
        The highest order bit of the Repetition Count indicates whether the packet is a Raw Packet or a Run-
        length Packet.  If bit 7 of the Repetition Count is set to 1, then the packet is a Run-length Packet.  If
        bit 7 is set to zero, then the packet is a Raw Packet.
        */
        boolean rlePacket = TgaUtil.isRunLengthPacket(repetitionCount);

        /*
        The lower 7 bits of the Repetition Count specify how many pixel values are represented by the
        packet.  In the case of a Run-length packet, this count indicates how many successive pixels have the
        pixel value specified by the Pixel Value field.  For Raw Packets, the Repetition Count specifies how
        many pixel values are actually contained in the next field.  This 7 bit value is actually encoded as 1
        less than the number of pixels in the packet (a value of 0 implies 1 pixel while a value of 0x7F
        implies 128 pixels).
        */
        int count = TgaUtil.getCount(repetitionCount);

        /*
        S'il s'agit d'un "Run-length packet", on alloue la lecture du seul byte suivant.
        S'il s'agit d'un "Raw packet", on peut directement allouer la lecture des bytes correspondant au nombre de
        pixels indiqué.
        Ne pas oublier que chaque pixel contient les trois bandes…
        pour chaque pixel, on alloue un byte par couleur, plus le nombre de bytes nécessaire aux attributs.
        */
        buffer = ByteBuffer.allocate(pixelAllocation * (rlePacket ? Byte.BYTES : Byte.BYTES * count));
        buffer.order(byteOrder);
        buffer.clear();
        channel.read(buffer);
        buffer.flip();

        final byte[] data = new byte[pixelAllocation];
        buffer.get(data);

        int n = 0;
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {

                pixelConsumer.accept(dataToPixel.apply(data), new int[]{i, j});
                n++;

                /*
                Lorqu'on a enregistré le nombre de pixels déduit du décompte, il faut lire la suite, sauf si on arrive
                en fin d'image
                */
                if (n == count && !(j == imageWidth - 1 && i == imageHeight - 1)) {
                    n = 0;

                    buffer = ByteBuffer.allocate(Byte.BYTES);
                    buffer.order(byteOrder);
                    buffer.clear();
                    channel.read(buffer);
                    buffer.flip();

                    repetitionCount = buffer.get();
                    rlePacket = TgaUtil.isRunLengthPacket(repetitionCount);
                    count = TgaUtil.getCount(repetitionCount);

                    /*
                    Pour chaque pixel, on alloue un byte par couleur, plus le nombre de bytes nécessaire aux attributs.
                    */
                    buffer = ByteBuffer.allocate(pixelAllocation * (rlePacket ? Byte.BYTES : Byte.BYTES * count));
                    buffer.order(byteOrder);
                    buffer.clear();
                    channel.read(buffer);
                    buffer.flip();

                    buffer.get(data);

                } else if (!rlePacket && !(j == imageWidth - 1 && i == imageHeight - 1)) {
                    buffer.get(data);
                }
            }
        }
    }
}
