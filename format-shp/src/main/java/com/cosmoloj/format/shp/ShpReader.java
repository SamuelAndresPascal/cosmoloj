package com.cosmoloj.format.shp;

import com.cosmoloj.util.bin.BinaryUtil;
import com.cosmoloj.util.io.EntryStreamReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 * @param <R> <span class="fr">type d'enregistrement produit</span>
 */
public class ShpReader<R extends ShpRecord<?>> implements EntryStreamReader<R> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ReadableByteChannel channel;
    private int position;
    private final ShpHeader header;
    private final int length;
    private final RecordFactory<R> recordFactory;

    public ShpReader(final SeekableByteChannel channel, final RecordFactory<R> factory)
            throws IOException {
        this.channel = channel;
        this.header = new ShpHeaderReader(channel).read();
        this.length = header.length() * 2;
        this.recordFactory = factory;
        this.position = ShpHeader.HEADER_LENGTH;
    }

    public ShpHeader getHeader() {
        return header;
    }

    @Override
    public boolean hasNext() {
        return position < length;
    }

    @Override
    public R readEntry() throws IOException {
        final int recordNumber = readRecordHeaderField(channel);
        final int recordLength = readRecordHeaderField(channel);
        LOG.debug("lecture de l'en-tête d'enregistrement : {} (numéro) ; {} (longueur)", recordNumber, recordLength);

        /*
        on met à jour la position du prochain enregistrement avec la taille de l'en-tête de l'enregistrement
        (2 simples = 2x4 = 8 ) plus la taille des données que l'on va lire, convertie de nombre de mots
        16 bits à nombre de mots à 8 bits.
        */
        position += (Integer.BYTES + recordLength) * 2;

        return readRecordData(channel, recordNumber, recordLength, recordFactory);
    }

    //==================================================================================================================
    // En-tête d'enregistrement
    //==================================================================================================================

    private static int readRecordHeaderField(final ReadableByteChannel channel) throws IOException {

        return BinaryUtil.read(channel, Integer.BYTES, ByteOrder.BIG_ENDIAN).getInt();
    }

    //==================================================================================================================
    // Enregistrement
    //==================================================================================================================

    private static <SR extends ShpRecord<?>> SR readRecordData(final ReadableByteChannel channel,
            final int recordNumber, final int recordLength, final RecordFactory<SR> factory) throws IOException {

        // lecture du type de forme
        final int shapeType = BinaryUtil.read(channel, Integer.BYTES, ByteOrder.LITTLE_ENDIAN).getInt();

        // lecture de la bbox
        final double[] bbox = readBbox(channel, shapeType);

        final int[] parameters = recomputeNbPoints(channel, shapeType);

        final Object geometry = readGeometry(channel, shapeType, parameters);

        final double[] zRange = shapeType >= ShapeType.POINT_Z ? readZRange(channel, shapeType) : new double[0];
        final double[] z = shapeType >= ShapeType.POINT_Z ? readZ(channel, shapeType, parameters[1]) : new double[0];

        final double[] measureRange = shapeType >= ShapeType.POINT_M
                ? readMeasureRange(channel, shapeType) : new double[0];
        final double[] measure = shapeType >= ShapeType.POINT_M
                ? readMeasure(channel, shapeType, parameters[1]) : new double[0];

        final int[] partTypes = shapeType == ShapeType.MULTI_PATCH
                ? Arrays.copyOfRange(parameters, ((parameters.length - 2) / 2) + 2, parameters.length) : new int[0];

        // lecture de la géométrie et création de l'enregistrement
        return factory.get(recordNumber, recordLength, shapeType, bbox, geometry, measureRange, measure, zRange, z,
                partTypes);
    }

    //==================================================================================================================
    // calcul du nombre de points
    //==================================================================================================================

    private static int[] recomputeNbPoints(final ReadableByteChannel channel, final int shapeType) throws IOException {
        return switch (shapeType) {
            case ShapeType.NULL_SHAPE, ShapeType.POINT -> new int[0];
            case ShapeType.POLY_LINE, ShapeType.POLYGON -> readPolyLineParameters(channel);
            case ShapeType.MULTI_POINT -> readMultiPointParamter(channel);

            case ShapeType.POINT_Z -> new int[0];
            case ShapeType.POLY_LINE_Z, ShapeType.POLYGON_Z -> readPolyLineParameters(channel);
            case ShapeType.MULTI_POINT_Z -> readMultiPointParamter(channel);

            case ShapeType.POINT_M -> new int[0];
            case ShapeType.POLY_LINE_M, ShapeType.POLYGON_M -> readPolyLineParameters(channel);
            case ShapeType.MULTI_POINT_M -> readMultiPointParamter(channel);

            case ShapeType.MULTI_PATCH -> readMultiPatchParameters(channel);
            default -> throw new IllegalArgumentException("unknown shape type");
        };
    }

    //==================================================================================================================
    // intervalle des valeurs de mesure
    //==================================================================================================================

    private static double[] readMeasureRange(final ReadableByteChannel channel, final int shapeType)
            throws IOException {
        return switch (shapeType) {
            case ShapeType.NULL_SHAPE,
                    ShapeType.POINT,
                    ShapeType.POLY_LINE,
                    ShapeType.POLYGON,
                    ShapeType.MULTI_POINT,
                    ShapeType.POINT_Z -> new double[0];
            case ShapeType.POLY_LINE_Z,
                    ShapeType.POLYGON_Z,
                    ShapeType.MULTI_POINT_Z -> readArray(channel, 2);
            case ShapeType.POINT_M -> new double[0];
            case ShapeType.POLY_LINE_M,
                    ShapeType.POLYGON_M,
                    ShapeType.MULTI_POINT_M,
                    ShapeType.MULTI_PATCH -> readArray(channel, 2);
            default -> throw new IllegalArgumentException("unknown shape type");
        };
    }

    //==================================================================================================================
    // valeurs de mesure
    //==================================================================================================================

    private static double[] readMeasure(final ReadableByteChannel channel, final int shapeType, final int nbPoints)
            throws IOException {
        return switch (shapeType) {
            case ShapeType.NULL_SHAPE,
                    ShapeType.POINT,
                    ShapeType.POLY_LINE,
                    ShapeType.POLYGON,
                    ShapeType.MULTI_POINT,
                    ShapeType.POINT_Z -> new double[0];
            case ShapeType.POLY_LINE_Z,
                    ShapeType.POLYGON_Z,
                    ShapeType.MULTI_POINT_Z -> readArray(channel, nbPoints);
            case ShapeType.POINT_M -> new double[0];
            case ShapeType.POLY_LINE_M,
                    ShapeType.POLYGON_M,
                    ShapeType.MULTI_POINT_M,
                    ShapeType.MULTI_PATCH -> readArray(channel, nbPoints);
            default -> throw new IllegalArgumentException("unknown shape type");
        };
    }

    //==================================================================================================================
    // intervalle des valeurs de la composante en Z
    //==================================================================================================================

    private static double[] readZRange(final ReadableByteChannel channel, final int shapeType) throws IOException {
        return switch (shapeType) {
            case ShapeType.NULL_SHAPE,
                    ShapeType.POINT,
                    ShapeType.POLY_LINE,
                    ShapeType.POLYGON,
                    ShapeType.MULTI_POINT,
                    ShapeType.POINT_Z -> new double[0];
            case ShapeType.POLY_LINE_Z,
                    ShapeType.POLYGON_Z,
                    ShapeType.MULTI_POINT_Z -> readArray(channel, 2);
            case ShapeType.POINT_M,
                    ShapeType.POLY_LINE_M,
                    ShapeType.POLYGON_M,
                    ShapeType.MULTI_POINT_M -> new double[0];
            case ShapeType.MULTI_PATCH -> readArray(channel, 2);
            default -> throw new IllegalArgumentException("unknown shape type");
        };
    }

    //==================================================================================================================
    // valeurs de la composante en z
    //==================================================================================================================

    private static double[] readZ(final ReadableByteChannel channel, final int shapeType, final int nbPoints)
            throws IOException {
        return switch (shapeType) {
            case ShapeType.NULL_SHAPE,
                    ShapeType.POINT,
                    ShapeType.POLY_LINE,
                    ShapeType.POLYGON,
                    ShapeType.MULTI_POINT,
                    ShapeType.POINT_Z -> new double[0];
            case ShapeType.POLY_LINE_Z,
                    ShapeType.POLYGON_Z,
                    ShapeType.MULTI_POINT_Z -> readArray(channel, nbPoints);
            case ShapeType.POINT_M,
                    ShapeType.POLY_LINE_M,
                    ShapeType.POLYGON_M,
                    ShapeType.MULTI_POINT_M -> new double[0];
            case ShapeType.MULTI_PATCH -> readArray(channel, nbPoints);
            default -> throw new IllegalArgumentException("unknown shape type");
        };
    }

    //==================================================================================================================
    // BBox d'enregistrement
    //==================================================================================================================

    private static double[] readBbox(final ReadableByteChannel channel, final int shapeType) throws IOException {
        return switch (shapeType) {
            case ShapeType.NULL_SHAPE -> new double[0];
            case ShapeType.POINT -> new double[0];
            case ShapeType.POLY_LINE,
                    ShapeType.POLYGON,
                    ShapeType.MULTI_POINT -> readArray(channel, 4);
            case ShapeType.POINT_Z -> new double[0];
            case ShapeType.POLY_LINE_Z,
                    ShapeType.POLYGON_Z,
                    ShapeType.MULTI_POINT_Z -> readArray(channel, 4);
            case ShapeType.POINT_M -> new double[0];
            case ShapeType.POLY_LINE_M,
                    ShapeType.POLYGON_M,
                    ShapeType.MULTI_POINT_M -> readArray(channel, 4);
            case ShapeType.MULTI_PATCH -> readArray(channel, 4);
            default -> throw new IllegalArgumentException("unknown shape type");
        };
    }

    //==================================================================================================================
    // Géométrie d'enregistrement
    //==================================================================================================================

    private static Object readGeometry(final ReadableByteChannel channel, final int shapeType, final int[] parameters)
            throws IOException {
        return switch (shapeType) {
            case ShapeType.NULL_SHAPE -> null;
            case ShapeType.POINT, ShapeType.POINT_M, ShapeType.POINT_Z -> readPoint(channel);
            case ShapeType.POLY_LINE, ShapeType.POLY_LINE_M, ShapeType.POLY_LINE_Z -> readPolyLine(channel, parameters);
            case ShapeType.POLYGON, ShapeType.POLYGON_M, ShapeType.POLYGON_Z -> readPolygon(channel, parameters);
            case ShapeType.MULTI_POINT, ShapeType.MULTI_POINT_M, ShapeType.MULTI_POINT_Z ->
                readMultiPoint(channel, parameters[0]);
            case ShapeType.MULTI_PATCH -> readMultiPatch(channel, parameters);
            default -> throw new IllegalArgumentException("unknown shape type");
        };
    }

    /**
     * <div class="fr">Lecture d'un polygone 2D. La structure des enregistrements est identique à celle des polylignes.
     * On considère seulement que les anneaux extérieurs sont donnés dans le sens des aiguilles d'une montre et les
     * anneaux intérieurs dans le sens contraire. De même, les suites de point définissant les anneaux doivent
     * explicitement finir par le même vertex que leur vertex de départ.</div>
     *
     * @param channel
     * @param parameters
     * @return <span class="fr">tableau de trois dimensions contentant, dans l'ordre des indices, 1) les anneaux du
     * polygone 2) les points de chaque anneau 3) les coordonnées x et y de chaque point</span>
     * @throws IOException
     */
    private static double[][][] readPolygon(final ReadableByteChannel channel, final int[] parameters)
            throws IOException {
        return readPolyLine(channel, parameters);
    }

    /**
     * <div class="fr">Lecture des paramètres des polylignes et des polygones. Les paramètres sont :
     * <ol>
     * <li>le nombre de parties ;</li>
     * <li>le nombre total de points ;</li>
     * <li>une liste de longueur égale au nombre de parties contenant les indices de départ de chaque partie.</li>
     * </ol>
     * </div>
     *
     * @param channel
     * @return
     * @throws IOException
     */
    private static int[] readPolyLineParameters(final ReadableByteChannel channel) throws IOException {

        ByteBuffer buffer = BinaryUtil.read(channel, Integer.BYTES * 2, ByteOrder.LITTLE_ENDIAN);

        final int nbParts = buffer.getInt();
        final int nbPoints = buffer.getInt();

        final int[] parameters = new int[nbParts + 2];
        parameters[0] = nbParts;
        parameters[1] = nbPoints;

        // lecture du tableau des index de premier point de chaque ligne
        buffer =  BinaryUtil.read(channel, Integer.BYTES * nbParts, ByteOrder.LITTLE_ENDIAN);

        for (int i = 2; i < nbParts + 2; i++) {
            parameters[i] = buffer.getInt();
        }
        return parameters;
    }

    /**
     * <div class="fr">Lecture d'une polyLigne.</div>
     *
     * @param channel
     * @param parameters
     * @return <span class="fr">tableau de trois dimensions contentant, dans l'ordre des indices, 1) les lignes de la
     * polyligne 2) les points de chaque ligne 3) les coordonnées x et y de chaque point</span>
     * @throws IOException
     */
    private static double[][][] readPolyLine(final ReadableByteChannel channel, final int[] parameters)
            throws IOException {

        final int nbParts = parameters[0];
        final int nbPoints = parameters[1];

        // lecture des points de chaque ligne
        final double[][][] result = new double[nbParts][][];
        for (int i = 0; i < nbParts - 1; i++) {

            // le nombre de point de la ligne est donné par l'index du premier point de la ligne suivante
            // dont on retranche l'index du premier point de la ligne courante
            result[i] = readPoints(channel, parameters[2 + i + 1] - parameters[2 + i]);
        }

        // le nombre de point de la dernière ligne doit être calculé à l'aide du nombre de points dont on retranche
        // l'index du premier point de la dernière ligne.
        result[result.length - 1] = readPoints(channel, nbPoints - parameters[2 + nbParts - 1]);
        return result;
    }

    /**
     * <div class="fr">Lecture des paramètres des Multipatchs. Les paramètres sont :
     * <ol>
     * <li>le nombre de parties ;</li>
     * <li>le nombre total de points ;</li>
     * <li>une liste de longueur égale au nombre de parties contenant les indices de départ de chaque partie ;</li>
     * <li>une liste de longueur égale au nombre de parties contenant le type correspondant à chaque partie.</li>
     * </ol>
     * </div>
     *
     * @param channel
     * @return
     * @throws IOException
     */
    private static int[] readMultiPatchParameters(final ReadableByteChannel channel) throws IOException {
        ByteBuffer buffer = BinaryUtil.read(channel, Integer.BYTES * 2, ByteOrder.LITTLE_ENDIAN);

        final int nbParts = buffer.getInt();
        final int nbPoints = buffer.getInt();

        final int[] parameters = new int[nbParts * 2 + 2];
        parameters[0] = nbParts;
        parameters[1] = nbPoints;

        // lecture du tableau des index de premier point de chaque ligne et du tableau des types de parties
        buffer = BinaryUtil.read(channel, Integer.BYTES * nbParts * 2, ByteOrder.LITTLE_ENDIAN);

        for (int i = 2; i < 2 + nbParts * 2; i++) {
            parameters[i] = buffer.getInt();
        }

        return parameters;
    }

    /**
     * <div class="fr">Lecture d'un multipatch.</div>
     *
     * @param channel
     * @param parameters
     * @return <span class="fr">tableau de trois dimensions contentant, dans l'ordre des indices, 1) les parties du
     * multipatch 2) les points de chaque partie 3) les coordonnées x et y de chaque point</span>
     * @throws IOException
     */
    private static double[][][] readMultiPatch(final ReadableByteChannel channel, final int[] parameters)
            throws IOException {

        final int nbParts = parameters[0];
        final int nbPoints = parameters[1];


        // lecture des points de chaque ligne
        final double[][][] result = new double[nbParts][][];
        for (int i = 2; i < nbParts + 2 - 1; i++) {

            // le nombre de point de la ligne est donné par l'index du premier point de la ligne suivante
            // dont on retranche l'index du premier point de la ligne courante
            result[i] = readPoints(channel, parameters[i + 1] - parameters[i]);
        }

        // le nombre de point de la dernière ligne doit être calculé à l'aide du nombre de points dont on retranche
        // l'index du premier point de la dernière ligne.
        result[result.length - 1] = readPoints(channel, nbPoints - parameters[2 + nbParts - 1]);
        return result;
    }

    /**
     * <div class="fr">Lecture de l'unique paramètre des multipoints : le nombre de points.</div>
     *
     * @param channel
     * @return
     * @throws IOException
     */
    private static int[] readMultiPointParamter(final ReadableByteChannel channel) throws IOException {

        // on commence par lire le nombre de points
        return new int[]{BinaryUtil.read(channel, Integer.BYTES, ByteOrder.LITTLE_ENDIAN).getInt()};
    }

    /**
     * <div class="fr">Lecture d'un multipoint.</div>
     *
     * @param channel
     * @param nbPoints
     * @return <span class="fr">tableau de deux dimensions contentant, dans l'ordre des indices,
     * 1) les points du multipoint 2) les coordonnées x et y de chaque point</span>
     * @throws IOException
     */
    private static double[][] readMultiPoint(final ReadableByteChannel channel, final int nbPoints) throws IOException {
        return readPoints(channel, nbPoints);
    }

    /**
     * <div class="fr">Lecture des coordonnées d'une série de points.</div>
     *
     * @param channel
     * @param nbPoints <span class="fr">nombre de points à lire</div>
     * @param dim <span class="fr">dimension des points</div>
     * @return <span class="fr">coordonnées d'une série de point dans un tableau à deux indices dont le premier
     * est le nombre de points de la série et le second la dimension des points</div>
     * @throws IOException
     */
    private static double[][] readPoints(final ReadableByteChannel channel, final int nbPoints) throws IOException {

        final double[][] result = new double[nbPoints][];
        for (int i = 0; i < nbPoints; i++) {
            result[i] = readArray(channel, 2);
        }
        return result;
    }

    /**
     * <div class="fr">Lecture d'un point.</div>
     *
     * @param channel
     * @return <span class="fr">tableau d'une dimension de longueur 2 contenant les coordonnées x et y du point</span>
     * @throws IOException
     */
    private static double[] readPoint(final ReadableByteChannel channel) throws IOException {
        return readArray(channel, 2);
    }

    /**
     * <div class="fr">Lecture d'un tableau de doubles.</div>
     *
     * @param channel
     * @param length <span class="fr">longueur du tableau à lire</span>
     * @return
     * @throws IOException
     */
    private static double[] readArray(final ReadableByteChannel channel, final int length) throws IOException {

        final ByteBuffer buffer = BinaryUtil.read(channel, Double.BYTES * length, ByteOrder.LITTLE_ENDIAN);

        final double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            result[i] = buffer.getDouble();
        }
        return result;
    }
}
