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
public class ReaderCodecUncompressed implements CodecReader {

    private final ReadableByteChannel channel;
    private final ByteOrder byteOrder;
    private final int pixelAllocation;
    private final UnaryOperator<byte[]> dataToPixel;
    private final int imageWidth;
    private final int imageHeight;

    public ReaderCodecUncompressed(final ReadableByteChannel channel, final ByteOrder byteOrder,
            final int pixelAllocation, final UnaryOperator<byte[]> dataToPixel, final int imageWidth,
            final int imageHeight) {
        this.channel = channel;
        this.byteOrder = byteOrder;
        this.pixelAllocation = pixelAllocation;
        this.dataToPixel = dataToPixel;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    @Override
    public void read(final BiConsumer<byte[], int[]> pixelConsumer) throws IOException {

        final ByteBuffer buffer = ByteBuffer.allocate(pixelAllocation * imageWidth * imageHeight);
        buffer.order(byteOrder);
        buffer.clear();
        channel.read(buffer);
        buffer.flip();

        final byte[] data = new byte[pixelAllocation];
        final int[] coordinates = new int[2];

        for (int i = 0; i < imageHeight; i++) {
            coordinates[0] = i;
            for (int j = 0; j < imageWidth; j++) {
                coordinates[1] = j;
                buffer.get(data);
                pixelConsumer.accept(dataToPixel.apply(data), coordinates);
            }
        }
    }
}
