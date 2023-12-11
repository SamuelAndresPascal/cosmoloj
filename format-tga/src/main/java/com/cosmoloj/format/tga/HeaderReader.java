package com.cosmoloj.format.tga;

import java.io.Closeable;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public class HeaderReader implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ReadableByteChannel channel;

    public HeaderReader(final ReadableByteChannel channel) {
        this.channel = channel;
    }

    public Header read() throws IOException {

        final ByteBuffer buffer = ByteBuffer.allocate(Header.LENGTH);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.clear();
        channel.read(buffer);
        buffer.flip();

        final byte idLenght = buffer.get();

        final ColorMapType colorMapType = ColorMapType.forByte(buffer.get());

        final ImageType imageType = ImageType.forByte(buffer.get());
        final short firstEntryIndex = buffer.getShort();
        final short colorMapLength = buffer.getShort();
        final byte colorMapEntrySize = buffer.get();

        final short xOrigin = buffer.getShort();
        final short yOrigin = buffer.getShort();
        final short imageWidth = buffer.getShort();
        final short imageHeight = buffer.getShort();
        final byte pixelDepth = buffer.get();
        final byte imageDescriptor = buffer.get();

        return new Header(idLenght, colorMapType, imageType,
                new ColorMapSpecification(firstEntryIndex, colorMapLength, colorMapEntrySize),
                new ImageSpecification(xOrigin, yOrigin, imageWidth, imageHeight, pixelDepth, imageDescriptor));
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }
}
