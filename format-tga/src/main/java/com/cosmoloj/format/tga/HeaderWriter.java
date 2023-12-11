package com.cosmoloj.format.tga;

import java.io.Closeable;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.WritableByteChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public class HeaderWriter implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final WritableByteChannel channel;

    public HeaderWriter(final WritableByteChannel channel) {
        this.channel = channel;
    }

    public void write(final Header header) throws IOException {
        LOG.debug("write header {}", header);
        final ByteBuffer buffer = ByteBuffer.allocate(Header.LENGTH);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(header.idLength());
        buffer.put(header.colorMapType().getValue());
        buffer.put(header.imageType().getValue());
        buffer.putShort(header.colorMapSpecification().firstEntryIndex());
        buffer.putShort(header.colorMapSpecification().colorMapLength());
        buffer.put(header.colorMapSpecification().colorMapEntrySize());
        buffer.putShort(header.imageSpecification().xOrigin());
        buffer.putShort(header.imageSpecification().yOrigin());
        buffer.putShort(header.imageSpecification().imageWidth());
        buffer.putShort(header.imageSpecification().imageHeight());
        buffer.put(header.imageSpecification().pixelDepth());
        buffer.put(header.imageSpecification().imageDescriptor());
        buffer.flip();
        channel.write(buffer);
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }
}
