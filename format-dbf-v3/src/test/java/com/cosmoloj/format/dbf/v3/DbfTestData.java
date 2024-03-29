package com.cosmoloj.format.dbf.v3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author Samuel Andrés
 */
public final class DbfTestData {

    private DbfTestData() {
    }

    private static final String[] GADM36_FRA_1_COLUMN_NAME = new String[]{
        "GID_0      ",
        "NAME_0     ",
        "GID_1      ",
        "NAME_1     ",
        "VARNAME_1  ",
        "NL_NAME_1  ",
        "TYPE_1     ",
        "ENGTYPE_1  ",
        "CC_1       ",
        "HASC_1     "};


    private static final String[][] GADM36_FRA_1_RESULT = new String[][]{{
        "FRA                                                                             ",
        "France                                                                          ",
        "FRA.1_1                                                                         ",
        "Auvergne-Rhône-Alpes                                                           ",
        "                                                                                ",
        "                                                                                ",
        "Région                                                                         ",
        "Region                                                                          ",
        "                                                                                ",
        "FR.AR                                                                           "},
    {
        "FRA                                                                             ",
        "France                                                                          ",
        "FRA.2_1                                                                         ",
        "Bourgogne-Franche-Comté                                                        ",
        "                                                                                ",
        "                                                                                ",
        "Région                                                                         ",
        "Region                                                                          ",
        "                                                                                ",
        "FR.BF                                                                           "},
    {
        "FRA                                                                             ",
        "France                                                                          ",
        "FRA.3_1                                                                         ",
        "Bretagne                                                                        ",
        "                                                                                ",
        "                                                                                ",
        "Région                                                                         ",
        "Region                                                                          ",
        "                                                                                ",
        "FR.BT                                                                           "},
    {
        "FRA                                                                             ",
        "France                                                                          ",
        "FRA.4_1                                                                         ",
        "Centre-Val de Loire                                                             ",
        "                                                                                ",
        "                                                                                ",
        "Région                                                                         ",
        "Region                                                                          ",
        "                                                                                ",
        "FR.CN                                                                           "},
    {
        "FRA                                                                             ",
        "France                                                                          ",
        "FRA.5_1                                                                         ",
        "Corse                                                                           ",
        "Corsica                                                                         ",
        "                                                                                ",
        "Région                                                                         ",
        "Region                                                                          ",
        "                                                                                ",
        "FR.CE                                                                           "},
    {
        "FRA                                                                             ",
        "France                                                                          ",
        "FRA.6_1                                                                         ",
        "Grand Est                                                                       ",
        "                                                                                ",
        "                                                                                ",
        "Région                                                                         ",
        "Region                                                                          ",
        "                                                                                ",
        "FR.AO                                                                           "},
    {
        "FRA                                                                             ",
        "France                                                                          ",
        "FRA.7_1                                                                         ",
        "Hauts-de-France                                                                 ",
        "                                                                                ",
        "                                                                                ",
        "Région                                                                         ",
        "Region                                                                          ",
        "                                                                                ",
        "FR.NC                                                                           "},
    {
        "FRA                                                                             ",
        "France                                                                          ",
        "FRA.8_1                                                                         ",
        "Île-de-France                                                                  ",
        "                                                                                ",
        "                                                                                ",
        "Région                                                                         ",
        "Region                                                                          ",
        "                                                                                ",
        "FR.IF                                                                           "},
    {
        "FRA                                                                             ",
        "France                                                                          ",
        "FRA.9_1                                                                         ",
        "Normandie                                                                       ",
        "                                                                                ",
        "                                                                                ",
        "Région                                                                         ",
        "Region                                                                          ",
        "                                                                                ",
        "FR.ND                                                                           "},
    {
        "FRA                                                                             ",
        "France                                                                          ",
        "FRA.10_1                                                                        ",
        "Nouvelle-Aquitaine                                                              ",
        "                                                                                ",
        "                                                                                ",
        "Région                                                                         ",
        "Region                                                                          ",
        "                                                                                ",
        "FR.AC                                                                           "},
    {
        "FRA                                                                             ",
        "France                                                                          ",
        "FRA.11_1                                                                        ",
        "Occitanie                                                                       ",
        "                                                                                ",
        "                                                                                ",
        "Région                                                                         ",
        "Region                                                                          ",
        "                                                                                ",
        "FR.LP                                                                           "},
    {
        "FRA                                                                             ",
        "France                                                                          ",
        "FRA.12_1                                                                        ",
        "Pays de la Loire                                                                ",
        "                                                                                ",
        "                                                                                ",
        "Région                                                                         ",
        "Region                                                                          ",
        "                                                                                ",
        "FR.PL                                                                           "},
    {
        "FRA                                                                             ",
        "France                                                                          ",
        "FRA.13_1                                                                        ",
        "Provence-Alpes-Côte d'Azur                                                     ",
        "                                                                                ",
        "                                                                                ",
        "Région                                                                         ",
        "Region                                                                          ",
        "                                                                                ",
        "FR.PR                                                                           "}};




    public static void gadm_36_FRA_1_test(final File file) throws IOException {

        try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
            final DbfReader reader = new DbfReader(fileChannel, false, false, StandardCharsets.UTF_8);
            final DbfRecord[] records = reader.read();


            final DbfHeader header = reader.getHeader();

            Assertions.assertEquals(3, header.valid());
            Assertions.assertEquals(LocalDate.of(2018, Month.MAY, 6), header.lastUpdate());
            Assertions.assertEquals(13, header.recordNb());
            Assertions.assertEquals(353, header.headerByteNb());
            Assertions.assertEquals(801, header.recordByteNb());
            Assertions.assertEquals(header.recordNb(), records.length);

            final DbfColumnHeader[] columnHeaders = header.columnHeaders();
            Assertions.assertEquals(10, columnHeaders.length);

            for (int i = 0; i < columnHeaders.length; i++) {
                final DbfColumnHeader columnHeader = columnHeaders[i];
                Assertions.assertEquals(DbfType.CHARACTER, columnHeader.getType());
                Assertions.assertEquals(0, columnHeader.getMemory());
                Assertions.assertEquals(80, columnHeader.getSize());

                final String name = columnHeader.getName();
                Assertions.assertEquals(11, name.length());
                Assertions.assertEquals(GADM36_FRA_1_COLUMN_NAME[i], name);

                Assertions.assertEquals(0, columnHeader.getDecimals());
            }

            for (int i = 0; i < header.recordNb(); i++) {
                Assertions.assertFalse(records[i].deleted());
                for (int j = 0; j < columnHeaders.length; j++) {
                    Assertions.assertEquals(
                            GADM36_FRA_1_RESULT[i][j], columnHeaders[j].fromBytes(records[i].tuple()[j]));
                    Assertions.assertArrayEquals(
                            columnHeaders[j].toBytes(GADM36_FRA_1_RESULT[i][j]), records[i].tuple()[j]);
                }
            }
        }
    }




    private static final Object[][] TYPES_COLUMN_NAME = new Object[][]{
        {"id\0\0\0\0\0\0\0\0\0", DbfType.NUMERIC, 0, (short) 10, (short) 0},
        {"champ1\0\0\0\0\0", DbfType.CHARACTER, 0, (short) 80, (short) 0},
        {"champ2\0\0\0\0\0", DbfType.CHARACTER, 0, (short) 40, (short) 0},
        {"champ3\0\0\0\0\0", DbfType.NUMERIC, 0, (short) 19, (short) 0},
        {"champ4\0\0\0\0\0", DbfType.NUMERIC, 0, (short) 19, (short) 0},
        {"champ5\0\0\0\0\0", DbfType.NUMERIC, 0, (short) 11, (short) 3},
        {"champ6\0\0\0\0\0", DbfType.DATE, 0, (short) 8, (short) 0},
        {"champ7\0\0\0\0\0", DbfType.DATE, 0, (short) 8, (short) 0}};


    private static final Object[] TYPES_RESULT = new Object[]{
            2,
        "sdfs                                                                            ",
        "skldfj                                  ",
        65463,
        564625,
        3543212.435,
        LocalDate.of(1983, Month.MARCH, 24),
        LocalDate.of(2018, Month.JUNE, 22)
            };

    public static void types_test(final File file) throws IOException {

        try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
            final DbfReader reader = new DbfReader(fileChannel, false, false, StandardCharsets.UTF_8);
            final DbfRecord[] records = reader.read();

            final DbfHeader header = reader.getHeader();

            Assertions.assertEquals(3, header.valid());
            Assertions.assertEquals(LocalDate.of(2018, Month.JUNE, 22), header.lastUpdate());
            Assertions.assertEquals(1, header.recordNb());
            Assertions.assertEquals(289, header.headerByteNb());
            Assertions.assertEquals(196, header.recordByteNb());
            Assertions.assertEquals(header.recordNb(), records.length);

            final DbfColumnHeader[] columnHeaders = header.columnHeaders();
            Assertions.assertEquals(8, columnHeaders.length);

            for (int i = 0; i < columnHeaders.length; i++) {
                final DbfColumnHeader columnHeader = columnHeaders[i];
                final String name = columnHeader.getName();
                Assertions.assertEquals(11, name.length());
                Assertions.assertEquals(TYPES_COLUMN_NAME[i][0], name);
                Assertions.assertEquals((byte) TYPES_COLUMN_NAME[i][1], columnHeader.getType());
                Assertions.assertEquals((int) TYPES_COLUMN_NAME[i][2], columnHeader.getMemory());
                Assertions.assertEquals((short) TYPES_COLUMN_NAME[i][3], (short) columnHeader.getSize());
                Assertions.assertEquals((short) TYPES_COLUMN_NAME[i][4], (short) columnHeader.getDecimals());
            }

            Assertions.assertFalse(records[0].deleted());
            for (int j = 0; j < columnHeaders.length; j++) {
                Assertions.assertEquals(TYPES_RESULT[j], columnHeaders[j].fromBytes(records[0].tuple()[j]));
                Assertions.assertArrayEquals(columnHeaders[j].toBytes(TYPES_RESULT[j]), records[0].tuple()[j]);
            }
        }
    }
}
