package com.cosmoloj.format.shx;

import com.cosmoloj.format.shp.ShpHeaderReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class ShxWriterTest {

    // POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT


    @Test
    public void pointTest() throws IOException, URISyntaxException {
        final File file = new File(ShxWriterTest.class.getResource("point.shx").toURI());

        // lecture du fichier
        try (FileChannel rChannel = new FileInputStream(file).getChannel()) {
            final ShxReader reader1 = new ShxReader(rChannel);
            final List<ShxRecord> records1 = new ArrayList<>();
            while (reader1.hasNext()) {
                records1.add(reader1.readEntry());
            }

            // fichier à écrire
            final File fileW = File.createTempFile("shxWriterTest", ".shx");
            fileW.deleteOnExit();


            try (FileChannel wChannel = new FileOutputStream(fileW, true).getChannel()) {
                final ShxWriter writer = new ShxWriter(wChannel, reader1.getHeader());

                // écriture
                writer.write(records1);


                // contrôle en lecture du fichier écrit
                try (FileChannel rChannel2 = new FileInputStream(fileW).getChannel()) {
                    final ShpHeaderReader headerReader2 = new ShpHeaderReader(rChannel2);

                    ShxTestIndex.pointHeaderTest(headerReader2.read());
                }
                ShxTestIndex.pointIndexTest(fileW);
            } finally {
                if (fileW.exists()) {
                    fileW.delete();
                }
            }
        }
    }

    // MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT

    // TODO

    // POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON

    @Test
    public void polyLineTest() throws IOException, URISyntaxException {
        final File file = new File(ShxWriterTest.class.getResource("polyLine.shx").toURI());


        // lecture du fichier
        try (FileChannel rChannel = new FileInputStream(file).getChannel()) {
            final ShxReader reader1 = new ShxReader(rChannel);
            final List<ShxRecord> records1 = new ArrayList<>();
            while (reader1.hasNext()) {
                records1.add(reader1.readEntry());
            }


            // fichier à écrire
            final File fileW = File.createTempFile("shxWriterTest", ".shx");
            fileW.deleteOnExit();


            try (FileChannel wChannel = new FileOutputStream(fileW, true).getChannel()) {
                final ShxWriter writer = new ShxWriter(wChannel, reader1.getHeader());

                // écriture
                writer.write(records1);


                // contrôle en lecture du fichier écrit
                try (FileChannel rChannel2 = new FileInputStream(fileW).getChannel()) {
                    final ShpHeaderReader headerReader2 = new ShpHeaderReader(rChannel2);

                    ShxTestIndex.polyLineHeaderTest(headerReader2.read());
                }
                ShxTestIndex.polyLineIndexTest(fileW);
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
        final File file = new File(ShxWriterTest.class.getResource("polygon.shx").toURI());


        // lecture du fichier
        try (FileChannel rChannel = new FileInputStream(file).getChannel()) {
            final ShxReader reader1 = new ShxReader(rChannel);
            final List<ShxRecord> records1 = new ArrayList<>();
            while (reader1.hasNext()) {
                records1.add(reader1.readEntry());
            }


            // fichier à écrire
            final File fileW = File.createTempFile("shxWriterTest", ".shx");
            fileW.deleteOnExit();


            try (FileChannel wChannel = new FileOutputStream(fileW, true).getChannel()) {
                final ShxWriter writer = new ShxWriter(wChannel, reader1.getHeader());

                // écriture
                writer.write(records1);


                // contrôle en lecture du fichier écrit
                try (FileChannel rChannel2 = new FileInputStream(fileW).getChannel()) {
                    final ShpHeaderReader headerReader2 = new ShpHeaderReader(rChannel2);

                    ShxTestIndex.polygonHeaderTest(headerReader2.read());
                }
                ShxTestIndex.polygonIndexTest(fileW);
            } finally {
                if (fileW.exists()) {
                    fileW.delete();
                }
            }
        }
    }

    @Test
    public void polygon2Test() throws IOException, URISyntaxException {
        final File file = new File(ShxWriterTest.class.getResource("polygon2.shx").toURI());


        // lecture du fichier
        try (FileChannel rChannel = new FileInputStream(file).getChannel()) {
            final ShxReader reader1 = new ShxReader(rChannel);
            final List<ShxRecord> records1 = new ArrayList<>();
            while (reader1.hasNext()) {
                records1.add(reader1.readEntry());
            }


            // fichier à écrire
            final File fileW = File.createTempFile("shxWriterTest", ".shx");
            fileW.deleteOnExit();


            try (FileChannel wChannel = new FileOutputStream(fileW, true).getChannel()) {
                final ShxWriter writer = new ShxWriter(wChannel, reader1.getHeader());

                // écriture
                writer.write(records1);


                // contrôle en lecture du fichier écrit
                try (FileChannel rChannel2 = new FileInputStream(fileW).getChannel()) {
                    final ShpHeaderReader headerReader2 = new ShpHeaderReader(rChannel2);

                    ShxTestIndex.polygon2HeaderTest(headerReader2.read());
                }
                ShxTestIndex.polygon2IndexTest(fileW);
            } finally {
                if (fileW.exists()) {
                    fileW.delete();
                }
            }
        }
    }

    @Test
    public void gadm36_fra_1_IndexTest() throws IOException, URISyntaxException {
        final File file = new File(ShxWriterTest.class.getResource("gadm36_FRA_1.shx").toURI());


        // lecture du fichier
        try (FileChannel rChannel = new FileInputStream(file).getChannel()) {
            final ShxReader reader1 = new ShxReader(rChannel);
            final List<ShxRecord> records1 = new ArrayList<>();
            while (reader1.hasNext()) {
                records1.add(reader1.readEntry());
            }


            // fichier à écrire
            final File fileW = File.createTempFile("shxWriterTest", ".shx");
            fileW.deleteOnExit();


            try (FileChannel wChannel = new FileOutputStream(fileW, true).getChannel()) {
                final ShxWriter writer = new ShxWriter(wChannel, reader1.getHeader());

                // écriture
                writer.write(records1);


                // contrôle en lecture du fichier écrit
//                try (FileChannel rChannel2 = new FileInputStream(fileW).getChannel()) {
//                    final ShpHeaderReader headerReader2 = new ShpHeaderReader(rChannel2);
//
//                    ShxTestIndex.polygon2HeaderTest(headerReader2.read());
//                }
                ShxTestIndex.gadm36_fra_1_IndexTest(fileW);
            } finally {
                if (fileW.exists()) {
                    fileW.delete();
                }
            }
        }
    }
}
