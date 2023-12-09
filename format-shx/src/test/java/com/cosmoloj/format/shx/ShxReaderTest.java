package com.cosmoloj.format.shx;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class ShxReaderTest {

    @Test
    public void pointIndexTest() throws IOException, URISyntaxException {
        ShxTestIndex.pointIndexTest(new File(ShxReaderTest.class.getResource("point.shx").toURI()));
    }

    // MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT


    // TODO


    // POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON


    @Test
    public void polyLineIndexTest() throws IOException, URISyntaxException {

        ShxTestIndex.polyLineIndexTest(new File(ShxReaderTest.class.getResource("polyLine.shx").toURI()));
    }


    // POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON


    @Test
    public void polygonIndexTest() throws IOException, URISyntaxException {

        ShxTestIndex.polygonIndexTest(new File(ShxReaderTest.class.getResource("polygon.shx").toURI()));
    }


    @Test
    public void polygon2IndexTest() throws IOException, URISyntaxException {

        ShxTestIndex.polygon2IndexTest(new File(ShxReaderTest.class.getResource("polygon2.shx").toURI()));
    }


    @Test
    public void gadm36_fra_1_IndexTest() throws IOException, URISyntaxException {

        ShxTestIndex.gadm36_fra_1_IndexTest(new File(ShxReaderTest.class.getResource("gadm36_FRA_1.shx").toURI()));
    }
}
