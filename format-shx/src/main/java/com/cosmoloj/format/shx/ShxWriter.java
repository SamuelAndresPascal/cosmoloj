package com.cosmoloj.format.shx;

import com.cosmoloj.util.io.EntryStreamWriter;
import com.cosmoloj.format.shp.ShpHeader;
import com.cosmoloj.format.shp.ShpHeaderWriter;
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
public class ShxWriter implements EntryStreamWriter<ShxRecord> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ShpHeader header;
    private final FileChannel channel;
    private int position;

    public ShxWriter(final FileChannel channel, final ShpHeader header) throws IOException {
        this.channel = channel;
        this.header = header;
        new ShpHeaderWriter(channel).write(header);
        position = ShpHeader.HEADER_LENGTH;
    }

    @Override
    public void write(final ShxRecord... records) throws IOException {

        LOG.debug("écriture des enregistrements");

        EntryStreamWriter.super.write(records);

        if (position != header.length() * 2) {
            throw new IllegalStateException(String.format("incoherent header file length : %s instead of %s expected",
                    position, header.length() * 2));
        }
    }

    @Override
    public void write(final Iterable<ShxRecord> records) throws IOException {

        LOG.debug("écriture des enregistrements");

        EntryStreamWriter.super.write(records);

        if (position != header.length() * 2) {
            throw new IllegalStateException(String.format("incoherent header file length : %s instead of %s expected",
                    position, header.length() * 2));
        }
    }

    @Override
    public void writeEntry(final ShxRecord shx) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 2);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(shx.offset());
        buffer.putInt(shx.contentLength());
        buffer.flip();
        channel.write(buffer);

        position += Integer.BYTES * 2;
    }
}
