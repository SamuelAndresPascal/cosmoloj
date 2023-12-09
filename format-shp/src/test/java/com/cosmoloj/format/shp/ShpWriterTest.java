package com.cosmoloj.format.shp;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class ShpWriterTest {

    // POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT


    @Test
    public void pointTest() throws IOException, URISyntaxException {


        // lecture du fichier
        final File file1 = new File(ShpWriterTest.class.getResource("point.shp").toURI());

        final List<ShpRecord<?>> records1 = new ArrayList<>();

        try (FileChannel rChannel = new FileInputStream(file1).getChannel()) {
            final ShpReader reader = new ShpReader(rChannel, DefaultRecordFactory.INSTANCE);
            while (reader.hasNext()) {
                records1.add(reader.readEntry());
            }

            // fichier à écrire
            final File fileW = File.createTempFile("shpWriterTest", ".shp");
            fileW.deleteOnExit();

            try (FileChannel wChannel = new FileOutputStream(fileW, true).getChannel()) {
                final ShpWriter writer = new ShpWriter(wChannel, reader.getHeader());
                writer.write(records1);


                try (FileChannel rChannel2 = new FileInputStream(fileW).getChannel()) {
                    final ShpHeaderReader headerReader2 = new ShpHeaderReader(rChannel2);
                    // contrôle en lecture du fichier écrit
                    ShpTestGeometries.pointHeaderTest(headerReader2.read());
                }

                ShpTestGeometries.pointDataTest(fileW);
            } finally {
                if (fileW.exists()) {
                    fileW.delete();
                }
            }
        }
    }

    // MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT


    @Test
    public void multiPointTest() throws IOException, URISyntaxException {


        // lecture du fichier
        final List<ShpRecord<?>> records1 = new ArrayList<>();

        records1.add(new DefaultShpRecord(1, 88 / 2, ShapeType.MULTI_POINT, ShpTestGeometries.MULTIPOINTS_BBOXES[0],
                ShpTestGeometries.MULTIPOINTS[0], null, null, null, null, null));
        records1.add(new DefaultShpRecord(2, 88 / 2, ShapeType.MULTI_POINT, ShpTestGeometries.MULTIPOINTS_BBOXES[1],
                ShpTestGeometries.MULTIPOINTS[1], null, null, null, null, null));


        // fichier à écrire
        final File fileW = File.createTempFile("shpWriterTest", ".shp");
        fileW.deleteOnExit();

        final ShpHeader header = new ShpHeader((100 + 96 + 96) / 2, ShapeType.MULTI_POINT,
                2.319942485021687, 42.1236994094, 3.7894734278072297, 49.89021323552048, 0, 0, 0, 0);

        try (FileChannel channel = new FileOutputStream(fileW, true).getChannel()) {
            final ShpWriter writer = new ShpWriter(channel, header);
            writer.write(records1);


            try (FileChannel rChannel2 = new FileInputStream(fileW).getChannel()) {
                final ShpHeaderReader headerReader2 = new ShpHeaderReader(rChannel2);
                // contrôle en lecture du fichier écrit
                ShpTestGeometries.multiPointHeaderTest(headerReader2.read());
            }
            ShpTestGeometries.multiPointDataTest(fileW);
        } finally {
            if (fileW.exists()) {
                fileW.delete();
            }
        }
    }

    // POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON

    @Test
    public void polyLineTest() throws IOException, URISyntaxException {


        // lecture du fichier
        final File file1 = new File(ShpWriterTest.class.getResource("polyLine.shp").toURI());

        final List<ShpRecord<?>> records1 = new ArrayList<>();

        try (FileChannel rChannel = new FileInputStream(file1).getChannel()) {
            final ShpReader reader = new ShpReader(rChannel, DefaultRecordFactory.INSTANCE);
            while (reader.hasNext()) {
                records1.add(reader.readEntry());
            }


            // fichier à écrire
            final File fileW = File.createTempFile("shpWriterTest", ".shp");
            fileW.deleteOnExit();

            try (FileChannel wChannel = new FileOutputStream(fileW, true).getChannel()) {
                final ShpWriter writer = new ShpWriter(wChannel, reader.getHeader());
                writer.write(records1);


                try (FileChannel rChannel2 = new FileInputStream(fileW).getChannel()) {
                    final ShpHeaderReader headerReader2 = new ShpHeaderReader(rChannel2);
                    // contrôle en lecture du fichier écrit

                    ShpTestGeometries.polyLineHeaderTest(headerReader2.read());
                }
                ShpTestGeometries.polyLineDataTest(fileW);
            } finally {
                if (fileW.exists()) {
                    fileW.delete();
                }
            }
        }
    }

    // POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON

    @Test
    public void polygonTest() throws IOException, URISyntaxException {


        // lecture du fichier
        File file1 = new File(ShpWriterTest.class.getResource("polygon.shp").toURI());

        final List<ShpRecord<?>> records1 = new ArrayList<>();

        try (FileChannel rChannel1 = new FileInputStream(file1).getChannel()) {
            final ShpReader reader = new ShpReader(rChannel1, DefaultRecordFactory.INSTANCE);
            while (reader.hasNext()) {
                records1.add(reader.readEntry());
            }


            // fichier à écrire
            final File fileW = File.createTempFile("shpWriterTest", ".shp");
            fileW.deleteOnExit();

            try (FileChannel wChannel = new FileOutputStream(fileW, true).getChannel()) {
                final ShpWriter writer = new ShpWriter(wChannel, reader.getHeader());
                writer.write(records1);


                try (FileChannel rChannel2 = new FileInputStream(fileW).getChannel()) {
                    final ShpHeaderReader headerReader2 = new ShpHeaderReader(rChannel2);
                    // contrôle en lecture du fichier écrit

                    ShpTestGeometries.polygonHeaderTest(headerReader2.read());
                }

                ShpTestGeometries.polygonDataTest(fileW);
            } finally {
                if (fileW.exists()) {
                    fileW.delete();
                }
            }
        }
    }

    @Test
    public void polygon2Test() throws IOException, URISyntaxException {


        // lecture du fichier
        File file1 = new File(ShpWriterTest.class.getResource("polygon2.shp").toURI());

        final List<ShpRecord<?>> records1 = new ArrayList<>();

        try (FileChannel rChannel1 = new FileInputStream(file1).getChannel()) {
            final ShpReader reader1 = new ShpReader(rChannel1, DefaultRecordFactory.INSTANCE);
            while (reader1.hasNext()) {
                records1.add(reader1.readEntry());
            }

            // fichier à écrire
            final File fileW = File.createTempFile("shpWriterTest", ".shp");
            fileW.deleteOnExit();

            try (FileChannel wChannel = new FileOutputStream(fileW, true).getChannel()) {
                final ShpWriter writer = new ShpWriter(wChannel, reader1.getHeader());
                writer.write(records1);


                try (FileChannel rChannel2 = new FileInputStream(fileW).getChannel()) {
                    final ShpHeaderReader headerReader2 = new ShpHeaderReader(rChannel2);
                    // contrôle en lecture du fichier écrit
                    ShpTestGeometries.polygon2HeaderTest(headerReader2.read());
                }

                ShpTestGeometries.polygon2DataTest(fileW);
            } finally {
                if (fileW.exists()) {
                    fileW.delete();
                }
            }
        }
    }
}
