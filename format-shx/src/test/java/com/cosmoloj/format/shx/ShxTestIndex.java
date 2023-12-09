package com.cosmoloj.format.shx;

import com.cosmoloj.format.shp.ShapeType;
import com.cosmoloj.format.shp.ShpHeader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author Samuel Andrés
 */
public final class ShxTestIndex {

    private ShxTestIndex() {
    }

    private static final int[][] POINTS = new int[][]{
            {50, 10},
            {64, 10},
            {78, 10}};

    public static void pointHeaderTest(final ShpHeader header) {

        Assertions.assertEquals((100 + 3 * 4 * 2) / 2, header.length());
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

    public static void pointIndexTest(final File file) throws IOException {

        // par iterateur
        try (FileChannel channel = new FileInputStream(file).getChannel()) {
            final var it = new ShxReader(channel);

            int i = 0;
            while (it.hasNext()) {
                final ShxRecord record = it.readEntry();
                pointIndexTest_0(record, i);
                i++;
            }
            Assertions.assertEquals(3, i);
        }
    }

    private static void pointIndexTest_0(final ShxRecord shx, final int i) {
        Assertions.assertArrayEquals(POINTS[i], new int[]{shx.offset(), shx.contentLength()});
    }

    private static final int[][] POLYLINE = new int[][]{
            {50, 88},
            {142, 80}};

    public static void polyLineHeaderTest(final ShpHeader header) {

        Assertions.assertEquals((100 + 2 * 4 * 2) / 2, header.length());
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

    public static void polyLineIndexTest(final File file) throws IOException {

        // par iterateur
        try (FileChannel channel = new FileInputStream(file).getChannel()) {
            final var it = new ShxReader(channel);

            int i = 0;
            while (it.hasNext()) {
                final ShxRecord record = it.readEntry();
                polyLineIndexTest_0(record, i);
                i++;
            }
            Assertions.assertEquals(2, i);
        }
    }

    private static void polyLineIndexTest_0(final ShxRecord shx, final int i) {
        Assertions.assertArrayEquals(POLYLINE[i], new int[]{shx.offset(), shx.contentLength()});
    }

    private static final int[][] POLYGON = new int[][]{
            {50, 80},
            {134, 64}};

    public static void polygonHeaderTest(final ShpHeader header) {

        Assertions.assertEquals((100 + 2 * 4 * 2) / 2, header.length());
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

    public static void polygonIndexTest(final File file) throws IOException {

        // par iterateur
        try (FileChannel channel = new FileInputStream(file).getChannel()) {
            final var it = new ShxReader(channel);

            int i = 0;
            while (it.hasNext()) {
                final ShxRecord record = it.readEntry();
                polygonIndexTest_0(record, i);
                i++;
            }
            Assertions.assertEquals(2, i);
        }
    }

    private static void polygonIndexTest_0(final ShxRecord shx, final int i) {
        Assertions.assertArrayEquals(POLYGON[i], new int[]{shx.offset(), shx.contentLength()});
    }

    private static final int[][] POLYGON2 = new int[][]{
            {50, 114},
            {168, 64}};

    public static void polygon2HeaderTest(final ShpHeader header) {

        Assertions.assertEquals((100 + 2 * 4 * 2) / 2, header.length());
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

    public static void polygon2IndexTest(final File file) throws IOException {

        // par iterateur
        try (FileChannel channel = new FileInputStream(file).getChannel()) {
            final var it = new ShxReader(channel);

            int i = 0;
            while (it.hasNext()) {
                final ShxRecord record = it.readEntry();
                polygon2IndexTest_0(record, i);
                i++;
            }
            Assertions.assertEquals(2, i);
        }
    }

    private static void polygon2IndexTest_0(final ShxRecord shx, int i) {
        Assertions.assertArrayEquals(POLYGON2[i++], new int[]{shx.offset(), shx.contentLength()});
    }

    private static final int[][] GADM36_FRA_1 = new int[][]{{50, 19602},
            {19656, 10320},
            {29980, 636600},
            {666584, 8032},
            {674620, 216676},
            {891300, 35176},
            {926480, 51682},
            {978166, 4728},
            {982898, 168624},
            {1151526, 220456},
            {1371986, 107346},
            {1479336, 130646},
            {1609986, 194566}};

    public static void gadm36_fra_1_IndexTest(final File file) throws IOException {

        // par iterateur
        try (FileChannel channel = new FileInputStream(file).getChannel()) {
            final var it = new ShxReader(channel);

            int i = 0;
            while (it.hasNext()) {
                final ShxRecord record = it.readEntry();
                gadm36_fra_1_IndexTest_0(record, i);
                i++;
            }
            Assertions.assertEquals(13, i);
        }
    }

    private static void gadm36_fra_1_IndexTest_0(final ShxRecord shx, int i) {
        Assertions.assertArrayEquals(GADM36_FRA_1[i++], new int[]{shx.offset(), shx.contentLength()});
    }
}
