package com.cosmoloj.format.shp;

import com.cosmoloj.util.bin.BinaryUtil;
import com.cosmoloj.util.io.EntryStreamWriter;
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
public class ShpWriter implements EntryStreamWriter<ShpRecord<?>> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ShpHeader header;
    private final WritableByteChannel channel;
    private int position;

    public ShpWriter(final WritableByteChannel channel, final ShpHeader header) throws IOException {
        this.channel = channel;
        this.header = header;
        new ShpHeaderWriter(channel).write(header);
        position = ShpHeader.HEADER_LENGTH;
    }

    @Override
    public void write(final ShpRecord<?>... records) throws IOException {

        LOG.debug("écriture des enregistrements");

        EntryStreamWriter.super.write(records);

        if (position != header.length() * 2) {
            throw new IllegalStateException(String.format("incoherent header file length : %s instead of %s expected",
                    position, header.length() * 2));
        }
    }

    @Override
    public void write(final Iterable<ShpRecord<?>> records) throws IOException {

        LOG.debug("écriture des enregistrements");

        EntryStreamWriter.super.write(records);

        if (position != header.length() * 2) {
            throw new IllegalStateException(String.format("incoherent header file length : %s instead of %s expected",
                    position, header.length() * 2));
        }
    }

    @Override
    public void writeEntry(final ShpRecord<?> rec) throws IOException {
        writeRecordHeaderField(channel, rec.getRecordNumber());

        final int recordLength = rec.getRecordLength();
        writeRecordHeaderField(channel, rec.getRecordLength());

        /*
        on met à jour la position du prochain enregistrement avec la taille de l'en-tête de l'enregistrement
        (2 simples = 2x4 = 8) plus la taille des données qu'on vient de lire, convertie de nombre de mots
        16 bits à nombre de mots à 8 bits.
        */
        position += Integer.BYTES * 2 + recordLength * 2;

        writeRecordData(channel, rec.getShapeType(), rec.getGeometry(), rec.getBbox(),
                rec.getZRange(), rec.getZValues(), rec.getMeasureRange(), rec.getMeasureValues(),
                rec.getPartTypes());
    }

    //==================================================================================================================
    // En-tête d'enregistrement
    //==================================================================================================================

    private static void writeRecordHeaderField(final WritableByteChannel channel, final int value) throws IOException {

        final ByteBuffer buffer = BinaryUtil.allocate(Integer.BYTES, ByteOrder.BIG_ENDIAN)
                .putInt(value)
                .flip();
        channel.write(buffer);
    }

    //==================================================================================================================
    // Enregistrement
    //==================================================================================================================

    private static void writeRecordData(final WritableByteChannel channel, final int shapeType, final Object data,
            final double[] bbox, final double[] zRange, final double[] zValues, final double[] measureRange,
            final double[] measureValues, final int[] partTypes) throws IOException {

        // type de forme
        final ByteBuffer buffer = BinaryUtil.allocate(Integer.BYTES, ByteOrder.LITTLE_ENDIAN)
                .putInt(shapeType)
                .flip();
        channel.write(buffer);

        writeBbox(channel, shapeType, bbox);

        writeGeometry(channel, shapeType, data, partTypes);

        if (zRange != null) {
            writeArray(channel, zRange);
        }
        if (zValues != null) {
            writeArray(channel, zValues);
        }
        if (measureRange != null) {
            writeArray(channel, measureRange);
        }
        if (measureValues != null) {
            writeArray(channel, measureValues);
        }

    }

    //==================================================================================================================
    // BBox d'enregistrement
    //==================================================================================================================

    private static void writeBbox(final WritableByteChannel channel, final int shapeType, final double[] bbox)
            throws IOException {
        switch (shapeType) {
            case ShapeType.NULL_SHAPE: return;
            case ShapeType.POINT: return;
            case ShapeType.POLY_LINE:
            case ShapeType.POLYGON:
            case ShapeType.MULTI_POINT: writeArray(channel, bbox); return;

            case ShapeType.POINT_Z: return;
            case ShapeType.POLY_LINE_Z:
            case ShapeType.POLYGON_Z:
            case ShapeType.MULTI_POINT_Z: writeArray(channel, bbox); return;

            case ShapeType.POINT_M: return;
            case ShapeType.POLY_LINE_M:
            case ShapeType.POLYGON_M:
            case ShapeType.MULTI_POINT_M: writeArray(channel, bbox); return;

            case ShapeType.MULTI_PATCH:
            default: throw new IllegalArgumentException(String.format("unknown shape type %s", shapeType));
        }
    }

    //==================================================================================================================
    // Géométrie d'enregistrement
    //==================================================================================================================


    private static void writeGeometry(final WritableByteChannel channel, final int shapeType, final Object data,
            final int[] partTypes) throws IOException {
        switch (shapeType) {
            case ShapeType.NULL_SHAPE: return;
            case ShapeType.POINT:
            case ShapeType.POINT_M:
            case ShapeType.POINT_Z: writePoint(channel, (double[]) data); return;
            case ShapeType.POLY_LINE:
            case ShapeType.POLY_LINE_M:
            case ShapeType.POLY_LINE_Z: writePolyLine(channel, (double[][][]) data); return;
            case ShapeType.POLYGON:
            case ShapeType.POLYGON_M:
            case ShapeType.POLYGON_Z: writePolygon(channel, (double[][][]) data); return;
            case ShapeType.MULTI_POINT:
            case ShapeType.MULTI_POINT_M:
            case ShapeType.MULTI_POINT_Z: writeMultiPoint(channel, (double[][]) data); return;
            case ShapeType.MULTI_PATCH: writeMultiPatch(channel, (double[][][]) data, partTypes); return;
            default: throw new IllegalArgumentException(String.format("unknown shape type %s", shapeType));
        }
    }

    /**
     * <div class="fr">Écriture d'un polygone. La structure des enregistrements est identique à celle des polylignes.
     * On considère seulement que les anneaux extérieurs sont donnés dans le sens des aiguilles d'une montre et les
     * anneaux intérieurs dans le sens contraire. De même, les suites de point définissant les anneaux doivent
     * explicitement finir par le même vertex que leur vertex de départ.</div>
     *
     * @param channel
     * @param polygon
     * @throws IOException
     */
    private static void writePolygon(final WritableByteChannel channel, final double[][][] polygon) throws IOException {
        writePolyLine(channel, polygon);
    }

    /**
     * <div class="fr">Écriture d'une polyligne.</div>
     *
     * @param channel
     * @param polyLine
     * @throws IOException
     */
    private static void writePolyLine(final WritableByteChannel channel,
            final double[][][] polyLine) throws IOException {

        // Détermination des indices de départ et du nombre de points

        // on prépare un tableau pour enregistrer les indices de départ
        final int[] parts = new int[polyLine.length];

        // on prépare le décompte du nombre de points
        int nbPoints = 0;

        // on parcourt les lignes de la polyligne
        for (int i = 0; i < polyLine.length; i++) {
            // indice de début de la ligne dans les points de la polyligne
            parts[i] = nbPoints;
            nbPoints += polyLine[i].length; // incrémentation du nombre de points
        }

        final ByteBuffer buffer = BinaryUtil.allocate(Integer.BYTES * (2 + parts.length), ByteOrder.LITTLE_ENDIAN)

            // nombre de lignes dans la polyligne
            .putInt(polyLine.length)

            // nombre de points
            .putInt(nbPoints);

        // écriture des indices de départ
        for (int i = 0; i < parts.length; i++) {
            buffer.putInt(parts[i]);
        }

        buffer.flip();
        channel.write(buffer);

        // points des lignes de la polyligne
        for (final double[][] polyLinei : polyLine) {
            for (final double[] polyLineij : polyLinei) {
                writeArray(channel, polyLineij);
            }
        }
    }

    /**
     * <div class="fr">Écriture d'un MultiPatch.</div>
     *
     * @param channel
     * @param multipatch
     * @param partTypes <span class="fr">types des parties d'un mutipatch</span>
     * @throws IOException
     */
    private static void writeMultiPatch(final WritableByteChannel channel, final double[][][] multipatch,
            final int[] partTypes) throws IOException {

        // Détermination des indices de départ et du nombre de points

        // on prépare un tableau pour enregistrer les indices de départ
        final int[] parts = new int[multipatch.length];

        // on prépare le décompte du nombre de points
        int nbPoints = 0;

        // on parcourt les lignes de la polyligne
        for (int i = 0; i < multipatch.length; i++) {
            // indice de début de la ligne dans les points de la polyligne
            parts[i] = nbPoints;
            nbPoints += multipatch[i].length; // incrémentation du nombre de points
        }

        final ByteBuffer buffer = BinaryUtil.allocate(Integer.BYTES * (2 + parts.length * 2), ByteOrder.LITTLE_ENDIAN)

            // nombre de lignes dans la polyligne
            .putInt(multipatch.length)

            // nombre de points
            .putInt(nbPoints);

        // écriture des indices de départ
        for (final int part : parts) {
            buffer.putInt(part);
        }

        // écriture des types de parties
        for (final int partType : partTypes) {
            buffer.putInt(partType);
        }

        buffer.flip();
        channel.write(buffer);

        // points des lignes de la polyligne
        for (final double[][] multipatchi : multipatch) {
            for (final double[] multipatchij : multipatchi) {
                writeArray(channel, multipatchij);
            }
        }
    }

    /**
     * <div class="fr">Écriture d'un MultiPoint.</div>
     *
     * @param channel
     * @param multiPoint
     * @throws IOException
     */
    private static void writeMultiPoint(final WritableByteChannel channel, final double[][] multiPoint)
            throws IOException {

        // nombre de points
        final ByteBuffer buffer = BinaryUtil.allocate(Integer.BYTES, ByteOrder.LITTLE_ENDIAN)
                .putInt(multiPoint.length)
                .flip();
        channel.write(buffer);

        // points du multipoint
        for (final double[] point : multiPoint) {
            writeArray(channel, point);
        }
    }

    /**
     * <div class="fr">Écriture d'un point.</div>
     *
     * @param channel
     * @param point
     */
    private static void writePoint(final WritableByteChannel channel, final double[] point) throws IOException {
        writeArray(channel, point);
    }

    /**
     * <div class="fr">Écriture d'un tableau de doubles.</div>
     *
     * @param channel
     * @param values <span class="fr">tableau à écrire</span>
     * @throws IOException
     */
    private static void writeArray(final WritableByteChannel channel, final double[] values) throws IOException {

        final ByteBuffer buffer = BinaryUtil.allocate(Double.BYTES * values.length, ByteOrder.LITTLE_ENDIAN);

        for (final double value : values) {
            buffer.putDouble(value);
        }

        buffer.flip();
        channel.write(buffer);
    }
}
