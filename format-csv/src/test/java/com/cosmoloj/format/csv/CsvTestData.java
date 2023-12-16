package com.cosmoloj.format.csv;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author Samuel Andrés
 */
public final class CsvTestData {

    private CsvTestData() {
    }

    // points de code attendus pour les différents fichiers de test

    public static final List<Integer> FILE1_UNIX = List.of(
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 10,
            10);

    public static final List<Integer> FILE1_MAC = List.of(
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 13,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13,
            13);

    public static final List<Integer> FILE1_WIN = List.of(
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 13, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13, 10,
            13, 10);


    public static final List<Integer> FILE2_UNIX = List.of(
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 10);

    public static final List<Integer> FILE2_MAC = List.of(
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 13,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13);

    public static final List<Integer> FILE2_WIN = List.of(
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 13, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13, 10);

    public static final List<Integer> FILE3_UNIX = List.of(
            102, 105, 101, 108, 100, 95, 110, 97, 109, 101, 44, 102, 105, 101, 108, 100, 95, 110, 97, 109, 101, 44, 102,
            105, 101, 108, 100, 95, 110, 97, 109, 101, 10,
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 10,
            10);

    public static final List<Integer> FILE3_MAC = List.of(
            102, 105, 101, 108, 100, 95, 110, 97, 109, 101, 44, 102, 105, 101, 108, 100, 95, 110, 97, 109, 101, 44, 102,
            105, 101, 108, 100, 95, 110, 97, 109, 101, 13,
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 13,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13,
            13);

    public static final List<Integer> FILE3_WIN = List.of(
            102, 105, 101, 108, 100, 95, 110, 97, 109, 101, 44, 102, 105, 101, 108, 100, 95, 110, 97, 109, 101, 44, 102,
            105, 101, 108, 100, 95, 110, 97, 109, 101, 13, 10,
            97, 97, 97, 44, 98, 98, 98, 44, 99, 99, 99, 13, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13, 10,
            13, 10);

    public static final List<Integer> FILE5_UNIX = List.of(
            34, 97, 97, 97, 34, 44, 34, 98, 98, 98, 34, 44, 34, 99, 99, 99, 34, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 10
    );

    public static final List<Integer> FILE5_MAC = List.of(
            34, 97, 97, 97, 34, 44, 34, 98, 98, 98, 34, 44, 34, 99, 99, 99, 34, 13,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13
    );

    public static final List<Integer> FILE5_WIN = List.of(
            34, 97, 97, 97, 34, 44, 34, 98, 98, 98, 34, 44, 34, 99, 99, 99, 34, 13, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13, 10
    );

    public static final List<Integer> FILE6_UNIX = List.of(
            34, 97, 97, 97, 34, 44, 34, 98, 10, 98, 98, 34, 44, 34, 99, 99, 99, 34, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 10
    );

    public static final List<Integer> FILE6_MAC = List.of(
            34, 97, 97, 97, 34, 44, 34, 98, 13, 98, 98, 34, 44, 34, 99, 99, 99, 34, 13,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13
    );

    public static final List<Integer> FILE6_WIN = List.of(
            34, 97, 97, 97, 34, 44, 34, 98, 13, 10, 98, 98, 34, 44, 34, 99, 99, 99, 34, 13, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13, 10
    );


    public static final List<Integer> FILE7_UNIX = List.of(
            34, 97, 97, 97, 34, 44, 34, 98, 34, 34, 98, 98, 34, 44, 34, 99, 99, 99, 34, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 10
    );

    public static final List<Integer> FILE7_MAC = List.of(
            34, 97, 97, 97, 34, 44, 34, 98, 34, 34, 98, 98, 34, 44, 34, 99, 99, 99, 34, 13,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13
    );

    public static final List<Integer> FILE7_WIN = List.of(
            34, 97, 97, 97, 34, 44, 34, 98, 34, 34, 98, 98, 34, 44, 34, 99, 99, 99, 34, 13, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13, 10
    );


    public static final List<Integer> FILE8_UNIX = List.of(
            34, 97, 97, 97, 34, 44, 98, 34, 98, 98, 44, 34, 99, 99, 99, 34, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 10
    );

    public static final List<Integer> FILE8_MAC = List.of(
            34, 97, 97, 97, 34, 44, 98, 34, 98, 98, 44, 34, 99, 99, 99, 34, 13,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13
    );

    public static final List<Integer> FILE8_WIN = List.of(
            34, 97, 97, 97, 34, 44, 98, 34, 98, 98, 44, 34, 99, 99, 99, 34, 13, 10,
            122, 122, 122, 44, 121, 121, 121, 44, 120, 120, 120, 13, 10
    );


    public static final List<Integer> FILE9_UNIX = List.of(
            60, 97, 97, 97, 62, 59, 60, 98, 34, 98, 62, 62, 98, 62, 59, 60, 99, 99, 99, 62, 10,
            122, 122, 122, 59, 121, 121, 121, 59, 60, 120, 120, 120, 62, 10
    );

    public static final List<Integer> FILE9_MAC = List.of(
            60, 97, 97, 97, 62, 59, 60, 98, 34, 98, 62, 62, 98, 62, 59, 60, 99, 99, 99, 62, 13,
            122, 122, 122, 59, 121, 121, 121, 59, 60, 120, 120, 120, 62, 13
    );

    public static final List<Integer> FILE9_WIN = List.of(
            60, 97, 97, 97, 62, 59, 60, 98, 34, 98, 62, 62, 98, 62, 59, 60, 99, 99, 99, 62, 13, 10,
            122, 122, 122, 59, 121, 121, 121, 59, 60, 120, 120, 120, 62, 13, 10
    );


    public static final List<Integer> FILE10_UNIX = List.of(
            60, 97, 97, 97, 62, 59, 60, 98, 34, 98, 92, 62, 98, 62, 59, 60, 99, 92, 92, 99, 99, 62, 10,
            122, 122, 122, 59, 121, 121, 121, 59, 60, 120, 120, 120, 62, 10
    );

    public static final List<Integer> FILE10_MAC = List.of(
            60, 97, 97, 97, 62, 59, 60, 98, 34, 98, 92, 62, 98, 62, 59, 60, 99, 92, 92, 99, 99, 62, 13,
            122, 122, 122, 59, 121, 121, 121, 59, 60, 120, 120, 120, 62, 13
    );

    public static final List<Integer> FILE10_WIN = List.of(
            60, 97, 97, 97, 62, 59, 60, 98, 34, 98, 92, 62, 98, 62, 59, 60, 99, 92, 92, 99, 99, 62, 13, 10,
            122, 122, 122, 59, 121, 121, 121, 59, 60, 120, 120, 120, 62, 13, 10
    );


    public static final List<Integer> FILE11_UNIX = List.of(
            60, 97, 97, 97, 62, 59, 60, 98, 34, 98, 92, 62, 98, 62, 59, 60, 99, 92, 92, 99, 99, 62, 10,
            122, 122, 122, 59, 121, 92, 121, 121, 59, 60, 120, 92, 120, 120, 62, 10
    );

    public static final List<Integer> FILE11_MAC = List.of(
            60, 97, 97, 97, 62, 59, 60, 98, 34, 98, 92, 62, 98, 62, 59, 60, 99, 92, 92, 99, 99, 62, 13,
            122, 122, 122, 59, 121, 92, 121, 121, 59, 60, 120, 92, 120, 120, 62, 13
    );

    public static final List<Integer> FILE11_WIN = List.of(
            60, 97, 97, 97, 62, 59, 60, 98, 34, 98, 92, 62, 98, 62, 59, 60, 99, 92, 92, 99, 99, 62, 13, 10,
            122, 122, 122, 59, 121, 92, 121, 121, 59, 60, 120, 92, 120, 120, 62, 13, 10
    );


    public static final List<Integer> FILE12_UNIX = List.of(
            60, 97, 97, 8364, 97, 62, 59, 60, 98, 34, 98, 92, 62, 98, 62, 59, 60, 99, 92, 92, 99, 99, 62, 10,
            122, 122, 122, 59, 121, 92, 121, 121, 59, 60, 120, 92, 120, 120, 62, 10
    );

    public static final List<Integer> FILE12_MAC = List.of(
            60, 97, 97, 8364, 97, 62, 59, 60, 98, 34, 98, 92, 62, 98, 62, 59, 60, 99, 92, 92, 99, 99, 62, 13,
            122, 122, 122, 59, 121, 92, 121, 121, 59, 60, 120, 92, 120, 120, 62, 13
    );

    public static final List<Integer> FILE12_WIN = List.of(
            60, 97, 97, 8364, 97, 62, 59, 60, 98, 34, 98, 92, 62, 98, 62, 59, 60, 99, 92, 92, 99, 99, 62, 13, 10,
            122, 122, 122, 59, 121, 92, 121, 121, 59, 60, 120, 92, 120, 120, 62, 13, 10
    );


    public static final List<Integer> FILE13_UNIX = List.of(
            34, 97, 97, 97, 34, 8364, 98, 34, 98, 98, 8364, 34, 99, 99, 99, 34, 10,
            122, 122, 122, 8364, 121, 121, 121, 8364, 120, 120, 120, 10
    );

    public static final List<Integer> FILE13_MAC = List.of(
            34, 97, 97, 97, 34, 8364, 98, 34, 98, 98, 8364, 34, 99, 99, 99, 34, 13,
            122, 122, 122, 8364, 121, 121, 121, 8364, 120, 120, 120, 13
    );

    public static final List<Integer> FILE13_WIN = List.of(
            34, 97, 97, 97, 34, 8364, 98, 34, 98, 98, 8364, 34, 99, 99, 99, 34, 13, 10,
            122, 122, 122, 8364, 121, 121, 121, 8364, 120, 120, 120, 13, 10
    );

    // contenu textuel attendu pour les différents fichiers de test

    public static final List<String[]> EXAMPLE1 = List.of(
            new String[]{"aaa", "bbb", "ccc"},
            new String[]{"zzz", "yyy", "xxx"});

    public static final List<String[]> EXAMPLE2 = EXAMPLE1;

    public static final List<String[]> EXAMPLE3 = List.of(
            new String[]{"field_name", "field_name", "field_name"},
            new String[]{"aaa", "bbb", "ccc"},
            new String[]{"zzz", "yyy", "xxx"});

    public static final List<String[]> EXAMPLE5 = EXAMPLE2;

    public static final List<String[]> EXAMPLE6_UNIX = List.of(
            new String[]{"aaa", "b\nbb", "ccc"},
            new String[]{"zzz", "yyy", "xxx"});

    public static final List<String[]> EXAMPLE6_MAC = List.of(
            new String[]{"aaa", "b\rbb", "ccc"},
            new String[]{"zzz", "yyy", "xxx"});

    public static final List<String[]> EXAMPLE6_WIN = List.of(
            new String[]{"aaa", "b\r\nbb", "ccc"},
            new String[]{"zzz", "yyy", "xxx"});

    public static final List<String[]> EXAMPLE7 = List.of(
            new String[]{"aaa", "b\"bb", "ccc"},
            new String[]{"zzz", "yyy", "xxx"});

    public static final List<String[]> EXAMPLE8 = EXAMPLE7;

    public static final List<String[]> EXAMPLE9 = List.of(
            new String[]{"aaa", "b\"b>b", "ccc"},
            new String[]{"zzz", "yyy", "xxx"});

    public static final List<String[]> EXAMPLE10 = List.of(
            new String[]{"aaa", "b\"b>b", "c\\cc"},
            new String[]{"zzz", "yyy", "xxx"});

    public static final List<String[]> EXAMPLE11 = List.of(
            new String[]{"aaa", "b\"b>b", "c\\cc"},
            new String[]{"zzz", "y\\yy", "xxx"});

    public static final List<String[]> EXAMPLE12 = List.of(
            new String[]{"aa€a", "b\"b>b", "c\\cc"},
            new String[]{"zzz", "y\\yy", "xxx"});

    public static final List<String[]> EXAMPLE13 = EXAMPLE8;

    // méthode de test

    public static void test(final CsvReader reader, final List<String[]> expected) throws IOException {

        // avec lecture de masse
        final List<String[]> lines = reader.read();

        Assertions.assertEquals(expected.size(), lines.size());

        for (int i = 0; i < lines.size(); i++) {
            Assertions.assertArrayEquals(expected.get(i), lines.get(i));
        }
    }

    public static void testStreamReader(final CsvStreamReader reader, final List<String[]> expected)
            throws IOException {

        // avec lecture de masse
        String[] line;
        int i = 0;
        while ((line = reader.readLine()) != null) {
            Assertions.assertArrayEquals(expected.get(i++), line);
        }

        Assertions.assertEquals(expected.size(), i);
    }
}
