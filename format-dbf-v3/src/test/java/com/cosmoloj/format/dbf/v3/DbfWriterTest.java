package com.cosmoloj.format.dbf.v3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class DbfWriterTest {


    @Test
    public void gadm36_FRA_1_Test() throws IOException, URISyntaxException {

        try (FileChannel fileChannel = new FileInputStream(
                new File(DbfReaderTest.class.getResource("gadm36_FRA_1.dbf").toURI())).getChannel()) {
            final DbfReader reader1 = new DbfReader(fileChannel, false, false, StandardCharsets.UTF_8);

            // lecture du fichier
            final DbfRecord[] records1 = reader1.read();

            // fichier à écrire
            final File fileW = File.createTempFile("dbfWriterTest", ".dbf");
            fileW.deleteOnExit();

            try (FileChannel channelW = new FileOutputStream(fileW).getChannel()) {
                final DbfWriter writer = new DbfWriter(channelW, StandardCharsets.UTF_8, reader1.getHeader());

                // écriture
                writer.write(records1);

                // contrôle en lecture du fichier écrit
                DbfTestData.gadm_36_FRA_1_test(fileW);
            } finally {
                if (fileW.exists()) {
                    fileW.delete();
                }
            }
        }
    }


    @Test
    public void types_Test() throws IOException, URISyntaxException {


        try (FileChannel fileChannel = new FileInputStream(
                new File(DbfReaderTest.class.getResource("types.dbf").toURI())).getChannel()) {
            final DbfReader reader1 = new DbfReader(fileChannel, false, false, StandardCharsets.UTF_8);

            // lecture du fichier
            final DbfRecord[] records1 = reader1.read();

            // fichier à écrire
            final File fileW = File.createTempFile("dbfWriterTest", ".dbf");
            fileW.deleteOnExit();

            try (FileChannel channelW = new FileOutputStream(fileW).getChannel()) {
                final DbfWriter writer = new DbfWriter(channelW, StandardCharsets.UTF_8, reader1.getHeader());

                // écriture
                writer.write(records1);

                // contrôle en lecture du fichier écrit
                DbfTestData.types_test(fileW);
            } finally {
                if (fileW.exists()) {
                    fileW.delete();
                }
            }
        }
    }
}
