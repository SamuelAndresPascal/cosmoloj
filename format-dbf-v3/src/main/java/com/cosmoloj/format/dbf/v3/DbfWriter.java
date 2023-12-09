package com.cosmoloj.format.dbf.v3;

import com.cosmoloj.util.io.EntryStreamWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 *
 * @author Samuel Andrés
 */
public class DbfWriter implements EntryStreamWriter<DbfRecord> {

    private final FileChannel channel;
    private final DbfHeader header;
    private final Charset headerNameCharset;

    public DbfWriter(final FileChannel channel, final Charset headerNameCharset, final DbfHeader header)
            throws IOException {
        this.channel = channel;
        this.header = header;
        this.headerNameCharset = headerNameCharset;
        writeHeader();
    }

    public DbfHeader getHeader() {
        return header;
    }

    @Override
    public void writeEntry(final DbfRecord dbf) throws IOException {

        // byte indiquant si l'enregistrement est supprimé ou non
        final var buffer = ByteBuffer.allocateDirect(header.recordByteNb());
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(dbf.deleted() ? DbfUtil.DELETED : DbfUtil.NOT_DELETED);

        final byte[][] result = dbf.tuple();
        for (final byte[] r : result) {
            buffer.put(r);
        }
        buffer.flip();
        channel.write(buffer);
    }

    /**
     * <div class="fr">Écriture de l'en-tête.</div>
     *
     * @param channel
     * @return
     * @throws IOException
     */
    private void writeHeader() throws IOException {

        ByteBuffer buffer;

        // 1- lecture du code
        //==============================================================================================================

        buffer = ByteBuffer.allocateDirect(1 + 3 + 4 + 2 + 2 + 3 + 13 + 4); // read 32 bytes
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(header.valid());

        final var date = header.lastUpdate();

        buffer.put((byte) (date.getYear() - DbfUtil.BASE_YEAR));
        buffer.put((byte) date.getMonthValue());
        buffer.put((byte) date.getDayOfMonth());

        buffer.putInt(header.recordNb());
        buffer.putShort(header.headerByteNb());
        buffer.putShort(header.recordByteNb());

        buffer.put(new byte[3 + 13 + 4]);
        buffer.flip();
        channel.write(buffer);

        // description du schéma
        final var columnHeaders = header.columnHeaders();
        for (final DbfColumnHeader columnHeader : columnHeaders) {
            writeColumnHeader(columnHeader);
        }

        // dernier byte du header
        buffer = ByteBuffer.allocateDirect(1);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte) 0);
        buffer.flip();
        channel.write(buffer);
    }

    private void writeColumnHeader(final DbfColumnHeader columnHeader) throws IOException {

        final ByteBuffer buffer = ByteBuffer.allocateDirect(11 + 1 + 4 + 1 + 1 + 14);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(columnHeader.getName().getBytes(headerNameCharset));
        // type sur 1 byte
        buffer.put(columnHeader.getType());
        // address mémoire ? entier sur 4 bytes
        buffer.putInt(columnHeader.getMemory());
        // taille : entier sur 1 byte
        buffer.put(columnHeader.getSize());
        // nombre de décimales : entier sur 1 byte
        buffer.put(columnHeader.getDecimals());


        // réservés : restent 14 bytes non utilisés ici
        buffer.put(new byte[14]);

        buffer.flip();
        channel.write(buffer);
    }
}
