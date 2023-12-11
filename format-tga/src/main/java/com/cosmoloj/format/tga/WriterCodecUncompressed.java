package com.cosmoloj.format.tga;

import com.cosmoloj.util.io.CodecWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.WritableByteChannel;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 *
 * @author Samuel Andrés
 */
public class WriterCodecUncompressed implements CodecWriter {

    private final WritableByteChannel channel;
    private final ByteOrder byteOrder;
    private final int pixelAllocation;
    private final UnaryOperator<byte[]> pixelToData;
    private final int imageWidth;
    private final int imageHeight;

    public WriterCodecUncompressed(final WritableByteChannel channel, final ByteOrder byteOrder,
            final int pixelAllocation, final UnaryOperator<byte[]> pixelToData, final int imageWidth,
            final int imageHeight) {
        this.channel = channel;
        this.byteOrder = byteOrder;
        this.pixelAllocation = pixelAllocation;
        this.pixelToData = pixelToData;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    @Override
    public void write(final Function<int[], byte[]> dataSupplier) throws IOException {

        final ByteBuffer buffer = ByteBuffer.allocate(pixelAllocation * imageWidth * imageHeight);
        buffer.order(byteOrder);
        buffer.clear();

        final int[] coordinates = new int[2];
        for (int i = 0; i < imageHeight; i++) {
            coordinates[0] = i;
            for (int j = 0; j < imageWidth; j++) {
                coordinates[1] = j;
                buffer.put(pixelToData.apply(dataSupplier.apply(coordinates)));
            }
        }
        buffer.flip();
        channel.write(buffer);
    }
}
