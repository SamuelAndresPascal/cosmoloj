package com.cosmoloj.format.shx;

import com.cosmoloj.util.io.EntryStreamReader;
import com.cosmoloj.format.shp.ShpHeader;
import com.cosmoloj.format.shp.ShpHeaderReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public class ShxReader implements EntryStreamReader<ShxRecord> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ShpHeader header;
    private final FileChannel channel;
    private int position;
    private final int length;

    public ShxReader(final FileChannel channel) throws IOException {
        this.channel = channel;
        this.header = new ShpHeaderReader(channel).read();
        this.length = header.length() * 2;
        this.position = ShpHeader.HEADER_LENGTH;
        this.channel.position(position);
    }

    public ShpHeader getHeader() {
        return header;
    }

    @Override
    public boolean hasNext() {
        return position < length;
    }

    @Override
    public ShxRecord readEntry() throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 2);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.clear();
        channel.read(buffer);
        buffer.flip();

        final int offset = buffer.getInt();
        final int contentLength = buffer.getInt();
        LOG.debug("lecture de l'index (position {}) : {} (offset) ; {} (longueur)", position, offset, contentLength);

        position += Integer.BYTES * 2;

        return new ShxRecord(offset, contentLength);
    }
}
