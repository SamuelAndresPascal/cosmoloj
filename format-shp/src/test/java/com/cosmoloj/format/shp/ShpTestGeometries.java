package com.cosmoloj.format.shp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author Samuel Andrés
 */
public final class ShpTestGeometries {

    private ShpTestGeometries() {
    }

    private static final double[][] POINTS = new double[][] {
            {3.7894734278072297, 43.700350792012046},
            {2.7849839226120485, 43.1236994094},
            {2.319942485021687, 48.89021323552048}
    };

    public static void pointHeaderTest(final ShpHeader header) {

        Assertions.assertEquals((100 + 3 * 28) / 2, header.length());
        Assertions.assertEquals(ShapeType.POINT, header.shapeType());
        Assertions.assertEquals(2.319942485021687, header.xMin());
        Assertions.assertEquals(43.1236994094, header.yMin());
        Assertions.assertEquals(3.7894734278072297, header.xMax());
        Assertions.assertEquals(48.89021323552048, header.yMax());
        Assertions.assertEquals(0., header.zMin());
        Assertions.assertEquals(0., header.zMax());
        Assertions.assertEquals(0., header.mMin());
        Assertions.assertEquals(0., header.mMax());
    }

    public static void pointDataTest(final File file) throws IOException {
        try (FileChannel channel = new FileInputStream(file).getChannel()) {

            // par iterateur
            final ShpReader it = new ShpReader(channel, DefaultRecordFactory.INSTANCE);

            int i = 0;
            while (it.hasNext()) {
                final ShpRecord<?> record = it.readEntry();
                pointDataTest_0(record, i);
                i++;
            }
            Assertions.assertEquals(3, i);
        }
    }

    private static void pointDataTest_0(final ShpRecord record, final int i) {
        Assertions.assertEquals(i + 1, record.getRecordNumber());
        Assertions.assertEquals(20 / 2, record.getRecordLength());
        Assertions.assertEquals(ShapeType.POINT, record.getShapeType());

        // comparaison des bbox
        Assertions.assertArrayEquals(new double[0], record.getBbox());

        // Les données sont des points qui sont des ensembles de coordonnées
        Assertions.assertArrayEquals(POINTS[i], (double[]) record.getGeometry());
    }

    // multipoints attendus
    static final double[][][] MULTIPOINTS = new double[][][]{
            // premier ensemble de points
            {
            {3.7894734278072297, 43.700350792012046},
            {2.7849839226120485, 43.1236994094},
            {2.319942485021687, 49.89021323552048}
            },
            // second ensemble de points
            {
            {3.7894734278072297, 43.700350792012046},
            {2.7849839226120485, 42.1236994094},
            {2.319942485021687, 48.89021323552048}
            }};

    // bboxes de multipoints attendues
    static final double[][] MULTIPOINTS_BBOXES = new double[][]{
            {2.319942485021687, 43.1236994094, 3.7894734278072297, 49.89021323552048},
            {2.319942485021687, 42.1236994094, 3.7894734278072297, 48.89021323552048}
            };

    public static void multiPointHeaderTest(final ShpHeader header) {
            Assertions.assertEquals((100 + 96 + 96) / 2, header.length());
            Assertions.assertEquals(ShapeType.MULTI_POINT, header.shapeType());
            Assertions.assertEquals(2.319942485021687, header.xMin());
            Assertions.assertEquals(42.1236994094, header.yMin());
            Assertions.assertEquals(3.7894734278072297, header.xMax());
            Assertions.assertEquals(49.89021323552048, header.yMax());
            Assertions.assertEquals(0., header.zMin());
            Assertions.assertEquals(0., header.zMax());
            Assertions.assertEquals(0., header.mMin());
            Assertions.assertEquals(0., header.mMax());
    }

    public static void multiPointDataTest(final File file) throws IOException {
        try (FileChannel channel = new FileInputStream(file).getChannel()) {

            // par iterateur
            final ShpReader it = new ShpReader(channel, DefaultRecordFactory.INSTANCE);

            int i = 0;
            while (it.hasNext()) {
                final ShpRecord<?> record = it.readEntry();
                multiPointDataTest_0(record, i);
                i++;
            }
            Assertions.assertEquals(2, i);
        }
    }

    private static void multiPointDataTest_0(final ShpRecord<?> record, final int i) {

        Assertions.assertEquals(i + 1, record.getRecordNumber());
        Assertions.assertEquals(88 / 2, record.getRecordLength());
        Assertions.assertEquals(ShapeType.MULTI_POINT, record.getShapeType());

        // comparaison des bbox
        Assertions.assertArrayEquals(MULTIPOINTS_BBOXES[i], record.getBbox());

        final double[][] data = (double[][]) record.getGeometry();
        Assertions.assertEquals(MULTIPOINTS[i].length, data.length);

        for (int j = 0; j < MULTIPOINTS[i].length; j++) {
            Assertions.assertArrayEquals(MULTIPOINTS[i][j], data[j]);
        }
    }

    // Polylignes attendues
    private static final double[][][][] POLYLINES = new double[][][][]{

            // 1ère polyligne, constituée d'une seule ligne de huit points de dimension 2
            {{{-0.2098829354698788, 42.881877861853006},
                {1.7432911024096382, 44.81645024222891},
                {3.733668455296386, 44.68623863970361},
                {4.477734755440964, 43.99797731206988},
                {4.198709892886748, 43.60734250449397},
                {2.8965938676337357, 43.16090272440722},
                {2.9896021551518066, 42.54704802678795},
                {0.10634524209156648, 42.86327620434939}}},

            // de polyligne, constituée d'une seule ligne de sept points de dimension 2
            {{{1.2038430348048195, 48.62979003046988},
                {0.2923618171277109, 47.27186903270602},
                {1.4828678973590366, 46.509201075057824},
                {2.2641375125108434, 46.620811020079515},
                {2.933797182640963, 46.95564085514457},
                {2.5431623750650596, 48.03453699035421},
                {1.4084612673445784, 48.648391687973486}}}
        };

    private static final double[][] POLYLINES_BBOXES = new double[][]{
            {-0.2098829354698788, 42.54704802678795, 4.477734755440964, 44.81645024222891},
            {0.2923618171277109, 46.509201075057824, 2.933797182640963, 48.648391687973486}
            };

    // longueurs de données attendues dans les enregistrements
    private static final int[] POLYLINES_LENGTHS = new int[]{176 / 2, 160 / 2};

    // nombres de points attendus dans les enregistrements
    private static final int[] POLYLINES_NB_POINTS = new int[]{8, 7};

    public static void polyLineHeaderTest(final ShpHeader header) {

        /*
        pour le premier enregistrement :
        (en-têtes)
        4 pour le numéro d'enregistrement
        4 pour la longueur de l'enregistrement
        (données)
        4 pour le type de géométrie
        4*8 = 32 pour la bbox
        4 pour le nombre de segments
        4 pour le nombre de points
        1*4 = 4 pour l'indice de début du premier segment
        8*2*8 = 128 pour la géométrie
        total deuxième enregistrement = 176 + 8 pour l'en-tête

        pour le deuxième enregistrement :
        (en-têtes)
        4 pour le numéro d'enregistrement
        4 pour la longueur de l'enregistrement
        (données)
        4 pour le type de géométrie
        4*8 = 32 pour la bbox
        4 pour le nombre de segments
        4 pour le nombre de points
        1*4 = 4 pour l'indice de début du premier segment
        7*2*8 = 112 pour la géométrie
        total premier enregistrement = 160 + 8 pour l'en-tête

        total données = 352
        */
        Assertions.assertEquals((100 + 352) / 2, header.length());
        Assertions.assertEquals(ShapeType.POLY_LINE, header.shapeType());
        Assertions.assertEquals(-0.2098829354698788, header.xMin());
        Assertions.assertEquals(42.54704802678795, header.yMin());
        Assertions.assertEquals(4.477734755440964, header.xMax());
        Assertions.assertEquals(48.648391687973486, header.yMax());
        Assertions.assertEquals(0., header.zMin());
        Assertions.assertEquals(0., header.zMax());
        Assertions.assertEquals(0., header.mMin());
        Assertions.assertEquals(0., header.mMax());
    }

    public static void polyLineDataTest(final File file) throws IOException {
        try (FileChannel channel = new FileInputStream(file).getChannel()) {

            // par iterateur
            final ShpReader it = new ShpReader(channel, DefaultRecordFactory.INSTANCE);

            int i = 0;
            while (it.hasNext()) {
                final ShpRecord<?> record = it.readEntry();
                polyLineDataTest_0(record, i);
                i++;
            }
            Assertions.assertEquals(2, i);
        }
    }

    private static void polyLineDataTest_0(final ShpRecord record, final int i) {
        Assertions.assertEquals(i + 1, record.getRecordNumber());
        Assertions.assertEquals(POLYLINES_LENGTHS[i], record.getRecordLength());
        Assertions.assertEquals(ShapeType.POLY_LINE, record.getShapeType());

        // comparaison des bbox
        Assertions.assertArrayEquals(POLYLINES_BBOXES[i], record.getBbox());

        // Les données sont des ensembles de lignes
        // les lignes sont des ensembles de points
        // les points sont des ensembles de coordonnées
        final double[][][] data =  (double[][][]) record.getGeometry();

        // toutes les polylignes du fichiers ont composées d'une unique ligne
        Assertions.assertEquals(1, data.length);

        Assertions.assertEquals(POLYLINES_NB_POINTS[i], data[0].length);

        // parcours des points
        for (int j = 0; j < data[0].length; j++) {

            // les points doivent être de dimension 2
            Assertions.assertEquals(2, data[0][j].length);

            // comparaison des points lus avec les points attendus
            Assertions.assertArrayEquals(POLYLINES[i][0][j], data[0][j]);
        }
    }

    // Polylignes attendues
    public static final double[][][][] POLYGONS = new double[][][][]{

            // 1er polygone, constitué d'un seul anneau de 6+1 points de dimension 2
            {{{-4.934703941387951, 48.57398505795903},
                {2.2641375125108434, 51.19681876596867},
                {8.216667913667468, 48.94601820803132},
                {7.6214148735518075, 44.07238394208433},
                {2.9896021551518066, 42.491243054277106},
                {-1.5864055907373489, 43.458529244465055},
                {-4.934703941387951, 48.57398505795903}}},

            // deuxième polygone, constitué d'un seul anneau de 4+1 points de dimension 2
            {{{9.388572336395182, 43.086496094392764},
                {9.388572336395182, 41.375143604060234},
                {8.625904378746988, 41.80298172664337},
                {8.55149774873253, 42.64005631430602},
                {9.388572336395182, 43.086496094392764}}}
        };

    public static final double[][] POLYGONS_BBOXES = new double[][]{
            {-4.934703941387951, 42.491243054277106, 8.216667913667468, 51.19681876596867},
            {8.55149774873253, 41.375143604060234, 9.388572336395182, 43.086496094392764}
            };

    // longueurs de données attendues dans les enregistrements
    private static final int[] POLYGONS_LENGTHS = new int[]{160 / 2, 128 / 2};

    // nombres de points attendus dans les enregistrements
    private static final int[] POLYGONS_NB_POINTS = new int[]{7, 5};

    public static void polygonHeaderTest(final ShpHeader header) {

        /*
        pour le premier enregistrement :
        (en-têtes)
        4 pour le numéro d'enregistrement
        4 pour la longueur de l'enregistrement
        (données)
        4 pour le type de géométrie
        4*8 = 32 pour la bbox
        4 pour le nombre de segments
        4 pour le nombre de points
        1*4 = 4 pour l'indice de début du premier segment
        (6+1)*2*8 = 112 pour la géométrie (il faut compter le premier point qui se répète)
        total deuxième enregistrement = 160 + 8 pour l'en-tête

        pour le deuxième enregistrement :
        (en-têtes)
        4 pour le numéro d'enregistrement
        4 pour la longueur de l'enregistrement
        (données)
        4 pour le type de géométrie
        4*8 = 32 pour la bbox
        4 pour le nombre de segments
        4 pour le nombre de points
        1*4 = 4 pour l'indice de début du premier segment
        (4+1)*2*8 = 80 pour la géométrie (il faut compter le premier point qui se répète)
        total premier enregistrement = 128 + 8 pour l'en-tête

        total données = 304
        */
        Assertions.assertEquals((100 + 304) / 2, header.length());
        Assertions.assertEquals(ShapeType.POLYGON, header.shapeType());
        Assertions.assertEquals(-4.934703941387951, header.xMin());
        Assertions.assertEquals(41.375143604060234, header.yMin());
        Assertions.assertEquals(9.388572336395182, header.xMax());
        Assertions.assertEquals(51.19681876596867, header.yMax());
        Assertions.assertEquals(0., header.zMin());
        Assertions.assertEquals(0., header.zMax());
        Assertions.assertEquals(0., header.mMin());
        Assertions.assertEquals(0., header.mMax());
    }

    public static void polygonDataTest(final File file) throws IOException {
        try (FileChannel channel = new FileInputStream(file).getChannel()) {

            // par iterateur
            final ShpReader it = new ShpReader(channel, DefaultRecordFactory.INSTANCE);

            int i = 0;
            while (it.hasNext()) {
                final ShpRecord<?> record = it.readEntry();
                polygonDataTest_0(record, i);
                i++;
            }
            Assertions.assertEquals(2, i);
        }
    }

    private static void polygonDataTest_0(final ShpRecord record, final int i) {
        Assertions.assertEquals(i + 1, record.getRecordNumber());
        Assertions.assertEquals(POLYGONS_LENGTHS[i], record.getRecordLength());
        Assertions.assertEquals(ShapeType.POLYGON, record.getShapeType());

        // comparaison des bbox
        Assertions.assertArrayEquals(POLYGONS_BBOXES[i], record.getBbox());

        // Les données sont des ensembles d'anneaux
        // les anneaux sont des ensembles de points
        // les points sont des ensembles de coordonnées
        final double[][][] data =  (double[][][]) record.getGeometry();

        // toutes les polylignes du fichiers ont composées d'une unique ligne
        Assertions.assertEquals(1, data.length);

        Assertions.assertEquals(POLYGONS_NB_POINTS[i], data[0].length);

        // parcours des points
        for (int j = 0; j < data[0].length; j++) {

            // les points doivent être de dimension 2
            Assertions.assertEquals(2, data[0][j].length);

            // comparaison des points lus avec les points attendus
            Assertions.assertArrayEquals(POLYGONS[i][0][j], data[0][j]);
            //System.out.println("{"+data[0][j][0]+", "+data[0][j][1]+"}");
        }
    }

    // Polylignes attendues
    private static final double[][][][] POLYGONS2 = new double[][][][]{

            // 1er polygone, constitué d'un seul anneau de 6+1 points de dimension 2
            {{{-4.934703941387951, 48.57398505795903},
                {2.2641375125108434, 51.19681876596867},
                {8.216667913667468, 48.94601820803132},
                {7.6214148735518075, 44.07238394208433},
                {2.9896021551518066, 42.491243054277106},
                {-1.5864055907373489, 43.458529244465055},
                {-4.934703941387951, 48.57398505795903}},
                // anneau intérieur
                {{2.3066047051453165, 48.88226516071388},
                {-3.0232150750888795, 48.07296497716422},
                {0.5145828701424957, 47.11336618809819},
                {2.3066047051453165, 48.88226516071388}}},

            // deuxième polygone, constitué d'un seul anneau de 4+1 points de dimension 2
            {{{9.388572336395182, 43.086496094392764},
                {9.388572336395182, 41.375143604060234},
                {8.625904378746988, 41.80298172664337},
                {8.55149774873253, 42.64005631430602},
                {9.388572336395182, 43.086496094392764}}}
        };

    // longueurs de données attendues dans les enregistrements
    private static final int[] POLYGONS2_LENGTHS = new int[]{228 / 2, 128 / 2};

    // nombres de points attendus dans les enregistrements
    private static final int[] POLYGONS2_NB_POINTS = new int[]{7 + 4, 5};

    private static final int[] POLYGONS2_NB_RINGS = new int[]{2, 1};

    public static void polygon2HeaderTest(final ShpHeader header) {

        /*
        pour le premier enregistrement :
        (en-têtes)
        4 pour le numéro d'enregistrement
        4 pour la longueur de l'enregistrement
        (données)
        4 pour le type de géométrie
        4*8 = 32 pour la bbox
        4 pour le nombre de segments
        4 pour le nombre de points
        2*4 = 4 pour l'indice de début du premier segment (deux anneaux)
        (6+1)*2*8 = 112 pour la géométrie de l'anneau extérieur (il faut compter le premier point qui se répète)
        (3+1)*2*8 = 64 pour la géométrie de l'anneau intérieur (il faut compter le premier point qui se répète)
        total deuxième enregistrement = 228 + 8 pour l'en-tête

        pour le deuxième enregistrement :
        (en-têtes)
        4 pour le numéro d'enregistrement
        4 pour la longueur de l'enregistrement
        (données)
        4 pour le type de géométrie
        4*8 = 32 pour la bbox
        4 pour le nombre de segments
        4 pour le nombre de points
        1*4 = 4 pour l'indice de début du premier segment
        (4+1)*2*8 = 80 pour la géométrie
        total premier enregistrement = 128 + 8 pour l'en-tête

        total données = 372
        */
        Assertions.assertEquals((100 + 372) / 2, header.length());
        Assertions.assertEquals(ShapeType.POLYGON, header.shapeType());
        Assertions.assertEquals(-4.934703941387951, header.xMin());
        Assertions.assertEquals(41.375143604060234, header.yMin());
        Assertions.assertEquals(9.388572336395182, header.xMax());
        Assertions.assertEquals(51.19681876596867, header.yMax());
        Assertions.assertEquals(0., header.zMin());
        Assertions.assertEquals(0., header.zMax());
        Assertions.assertEquals(0., header.mMin());
        Assertions.assertEquals(0., header.mMax());
    }

    public static void polygon2DataTest(final File file) throws IOException {
        try (FileChannel channel = new FileInputStream(file).getChannel()) {

            // par iterateur
            final ShpReader it = new ShpReader(channel, DefaultRecordFactory.INSTANCE);

            int i = 0;
            while (it.hasNext()) {
                final ShpRecord<?> record = it.readEntry();
                polygon2DataTest_0(record, i);
                i++;
            }
            Assertions.assertEquals(2, i);
        }
    }

    private static void polygon2DataTest_0(final ShpRecord record, final int i) {

        Assertions.assertEquals(i + 1, record.getRecordNumber());
        Assertions.assertEquals(POLYGONS2_LENGTHS[i], record.getRecordLength());
        Assertions.assertEquals(ShapeType.POLYGON, record.getShapeType());

        // comparaison des bbox
        Assertions.assertArrayEquals(POLYGONS_BBOXES[i], record.getBbox());

        // Les données sont des ensembles d'anneaux
        // les anneaux sont des ensembles de points
        // les points sont des ensembles de coordonnées
        final double[][][] data =  (double[][][]) record.getGeometry();

        // nombre d'anneaux des polygones
        Assertions.assertEquals(POLYGONS2_NB_RINGS[i], data.length);

        int points = 0;
        for (int j = 0; j < data.length; j++) {
            points += data[j].length; // somme le nombre de points des anneaux

            // parcours des points
            for (int k = 0; k < data[j].length; k++) {

                // les points doivent être de dimension 2
                Assertions.assertEquals(2, data[j][k].length);

                // comparaison des points lus avec les points attendus
                Assertions.assertArrayEquals(POLYGONS2[i][j][k], data[j][k]);
                //System.out.println("{"+data[j][k][0]+", "+data[j][k][1]+"}");
            }
        }

        Assertions.assertEquals(POLYGONS2_NB_POINTS[i], points);
    }
}
