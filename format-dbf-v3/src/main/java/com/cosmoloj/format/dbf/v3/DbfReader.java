package com.cosmoloj.format.dbf.v3;

import com.cosmoloj.util.bin.BinaryUtil;
import com.cosmoloj.util.io.EntryStreamReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://www.dbf2002.com/dbf-file-format.html
 * http://www.dbase.com/Knowledgebase/INT/db7_file_fmt.htm
 * https://www.loc.gov/preservation/digital/formats/fdd/fdd000325.shtml
 * http://www.oocities.org/geoff_wass/dBASE/GaryWhite/dBASE/FAQ/qformt.htm
 * https://www.clicketyclick.dk/databases/xbase/format/index.html
 * @author Samuel Andrés
 */
public class DbfReader implements EntryStreamReader<DbfRecord> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final SeekableByteChannel fileChannel;

    // jeu de caractères utilisé pour le codage des données
    private final Charset characterCharset;

    // jeu de caractères utilisé pour le codage des noms d'en-têtes
    private final Charset headerNameCharset;
    private final DbfHeader header;
    private int entryCpt = 0;

    public DbfReader(final FileChannel fileChannel, final boolean trimHeaders, final boolean trimValues,
            final Charset characterCharset, final Charset headerNameCharset) throws IOException {
        this.fileChannel = fileChannel;
        this.characterCharset = characterCharset;
        this.headerNameCharset = headerNameCharset;
        this.header = readHeader(trimHeaders, trimValues);
    }

    public DbfReader(final FileChannel fileChannel, final boolean trimHeaders, final boolean trimValues,
            final Charset charset) throws IOException {
        this(fileChannel, trimHeaders, trimValues, charset, charset);
    }

    // Il semble que le jeu de caractères utilisé par dbf soit à la base ISO-8859-1 et que la possibilité d'utiliser un
    // autre jeu de caractère soit une extension de shapefile.
    public DbfReader(final FileChannel fileChannel, final boolean trimHeaders, final boolean trimValues)
            throws IOException {
        this(fileChannel, trimHeaders, trimValues, StandardCharsets.ISO_8859_1);
    }

    public DbfHeader getHeader() {
        return header;
    }

    public DbfRecord[] read() throws IOException {

        LOG.debug("lecture de l'en-tête");

        final DbfRecord[] records = new DbfRecord[header.recordNb()];
        while (hasNext()) {
            records[entryCpt] = readEntry();
        }
        return records;
    }

    @Override
    public boolean hasNext() {
        return entryCpt < header.recordNb();
    }

    @Override
    public DbfRecord readEntry() throws IOException {

        // byte indiquant si l'enregistrement est supprimé ou non
        final ByteBuffer buffer = BinaryUtil.read(fileChannel, header.recordByteNb(), ByteOrder.LITTLE_ENDIAN);

        final byte recorded = buffer.get();

        final byte[][] result = new byte[header.columnHeaders().length][];
        for (int i = 0, len = result.length; i < len; i++) {
            result[i] = new byte[header.columnHeaders()[i].getSize()];
            buffer.get(result[i]);
        }
        entryCpt++;
        return new DbfRecord(recorded == DbfUtil.DELETED, result);
    }

    /**
     * <div class="fr">Lecture de l'en-tête.</div>
     *
     * @param trimHeaders
     * @param trimValues
     * @return
     * @throws IOException
     */
    private DbfHeader readHeader(final boolean trimHeaders, final boolean trimValues) throws IOException {

        // 1- lecture du code
        //==========================================================================================================

        final ByteBuffer buffer = BinaryUtil.read(
                fileChannel, 1 + 3 + 4 + 2 + 2 + 3 + 13 + 4, ByteOrder.LITTLE_ENDIAN); // read 32 bytes

        // version ?
        final byte valid = buffer.get();

        // date de dernière mise à jour
        final short year = (short) buffer.get();
        final short month = (short) buffer.get();
        final short day = (short) buffer.get();

        // nombre d'enregistrements
        final int recordNb = buffer.getInt();

        // nombre de bytes de l'en-tête
        final short headerByteNb = buffer.getShort();

        // nombre de bytes par enregistrement
        final short recordByteNb = buffer.getShort();

        // bytes reservés : 3 + 13 + 4

        // description du schéma
        final DbfColumnHeader[] columnHeaders = new DbfColumnHeader[(headerByteNb - 1 - 32) / 32];
        int i = 0;
        while (fileChannel.position() < headerByteNb - 1) {
            columnHeaders[i++] = readColumnHeader(trimHeaders, trimValues);
        }

        // dernier byte du header
        BinaryUtil.read(fileChannel, 1, ByteOrder.LITTLE_ENDIAN);

        // Structuration de l'en-tête
        //==========================================================================================================

        return new DbfHeader(valid, LocalDate.of(year + DbfUtil.BASE_YEAR, month, day),
                recordNb, headerByteNb, recordByteNb, columnHeaders);
    }

    /**
     *
     * <table>
     * <caption> Table Field Descriptor Bytes</caption>
     * <thead>
     * <tr><th>Byte</th><th>Contents</th><th>Description</th><tr>
     * </thead>
     * <tbody>
     * <tr><td>0-10  </td><td>11 bytes  </td><td>Field name in ASCII (zero-filled).</td></tr>
     * <tr><td>11  </td><td>1 byte  </td><td>Field type in ASCII (C, D, L, M, or N).</td></tr>
     * <tr><td>12-15  </td><td>4 bytes  </td><td>Field data address (address is set in memory; not useful on disk).</td>
     * </tr>
     * <tr><td>16  </td><td>1 byte  </td><td>Field length in binary.</td></tr>
     * <tr><td>17  </td><td>1 byte  </td><td>Field decimal count in binary.</td></tr>
     * <tr><td>18-19  </td><td>2 bytes  </td><td>Reserved for dBASE III PLUS on a LAN.</td></tr>
     * <tr><td>20  </td><td>1 byte  </td><td>Work area ID.</td></tr>
     * <tr><td>21-22  </td><td>2 bytes  </td><td>Reserved for dBASE III PLUS on a LAN.</td></tr>
     * <tr><td>23  </td><td>1 byte  </td><td>SET FIELDS flag.</td></tr>
     * <tr><td>24-31  </td><td>1 byte  </td><td>Reserved bytes.</td></tr>
     * </tbody>
     * </pre>
     *
     * @param trimHeaders
     * @param trimValues
     * @return
     * @throws IOException
     */
    private DbfColumnHeader<?> readColumnHeader(final boolean trimHeaders, final boolean trimValues)
            throws IOException {

        final ByteBuffer buffer = BinaryUtil.read(fileChannel, 11 + 1 + 4 + 1 + 1 + 14, ByteOrder.LITTLE_ENDIAN);

        // nom sur 11 bytes
        final byte[] nameBuf = new byte[11];
        buffer.get(nameBuf);
        final String name = new String(nameBuf, headerNameCharset);

        // type sur 1 byte
        final byte type = buffer.get();

        // address mémoire ? entier sur 4 bytes
        final int memory = buffer.getInt();

        // taille : entier sur 1 byte
        final byte size = buffer.get();

        // nombre de décimales : entier sur 1 byte
        final byte decimals = buffer.get();

        // réservés : restent 14 bytes non utilisés ici

        return DbfColumnHeader.of(trimHeaders ? name.trim() : name, type, memory, size, decimals, characterCharset,
                trimValues);
    }
}
