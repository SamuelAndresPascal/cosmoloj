package com.cosmoloj.format.dbf.v3;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class DbfReaderTest {


    @Test
    public void gadm_36_FRA_1_test() throws URISyntaxException, IOException {

        DbfTestData.gadm_36_FRA_1_test(new File(DbfReaderTest.class.getResource("gadm36_FRA_1.dbf").toURI()));
    }

    @Test
    public void types_test() throws URISyntaxException, IOException {

        DbfTestData.types_test(new File(DbfReaderTest.class.getResource("types.dbf").toURI()));
    }
}
