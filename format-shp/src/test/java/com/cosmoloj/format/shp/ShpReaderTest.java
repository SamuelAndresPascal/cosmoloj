package com.cosmoloj.format.shp;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class ShpReaderTest {

    // POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT

    @Test
    public void pointDataTest() throws IOException, URISyntaxException {
        ShpTestGeometries.pointDataTest(new File(ShpReaderTest.class.getResource("point.shp").toURI()));
    }


    // MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT


    // TODO


    // POLYLINE -- POLYLINE -- POLYLINE -- POLYLINE -- POLYLINE -- POLYLINE -- POLYLINE -- POLYLINE -- POLYLINE

    @Test
    public void polyLineDataTest() throws IOException, URISyntaxException {

        ShpTestGeometries.polyLineDataTest(new File(ShpReaderTest.class.getResource("polyLine.shp").toURI()));
    }


    // POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON

    @Test
    public void polygonDataTest() throws IOException, URISyntaxException {

        ShpTestGeometries.polygonDataTest(new File(ShpReaderTest.class.getResource("polygon.shp").toURI()));
    }

    @Test
    public void polygon2DataTest() throws IOException, URISyntaxException {

        ShpTestGeometries.polygon2DataTest(new File(ShpReaderTest.class.getResource("polygon2.shp").toURI()));
    }
}
