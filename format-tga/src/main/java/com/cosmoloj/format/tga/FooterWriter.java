package com.cosmoloj.format.tga;

import java.io.Closeable;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public class FooterWriter implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final WritableByteChannel channel;

    public FooterWriter(final WritableByteChannel channel) {
        this.channel = channel;
    }

    public void write(final Footer footer) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(Footer.LENGTH);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(footer.extensionAreaOffset());
        buffer.putInt(footer.developerDirectoryOffset());
        buffer.put(footer.signature().getBytes(StandardCharsets.US_ASCII));
        buffer.put(footer.reservedChar());
        buffer.put(footer.zero());
        buffer.flip();
        channel.write(buffer);
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }
}
