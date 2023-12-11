package com.cosmoloj.format.tga;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Samuel Andrés
 */
public class FooterReader implements Closeable {

    private final ReadableByteChannel channel;

    public FooterReader(final ReadableByteChannel channel) {
        this.channel = channel;
    }

    public Footer read() throws IOException {

        final ByteBuffer buffer = ByteBuffer.allocate(Footer.LENGTH);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.clear();
        channel.read(buffer);
        buffer.flip();

        final int extensionAreaOffset = buffer.getInt();
        final int developerDirectoryOffset = buffer.getInt();

        final byte[] dest = new byte[16];
        buffer.get(dest);
        final String signature = new String(dest, StandardCharsets.US_ASCII);

        final byte reservedChar = buffer.get();
        final byte zero = buffer.get();

        return new Footer(extensionAreaOffset, developerDirectoryOffset, signature, reservedChar, zero);
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }
}
