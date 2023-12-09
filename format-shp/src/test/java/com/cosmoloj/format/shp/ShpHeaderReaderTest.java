package com.cosmoloj.format.shp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class ShpHeaderReaderTest {

    // POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT -- POINT

    @Test
    public void pointHeaderTest() throws IOException, URISyntaxException {

        try (FileChannel channel = new FileInputStream(
                new File(ShpHeaderReaderTest.class.getResource("point.shp").toURI())).getChannel()) {
            final ShpHeaderReader reader = new ShpHeaderReader(channel);
            ShpTestGeometries.pointHeaderTest(reader.read());
        }
    }

    // MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT -- MULTIPOINT


    // TODO


    // POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON

    @Test
    public void polyLineHeaderTest() throws IOException, URISyntaxException {

        try (FileChannel channel = new FileInputStream(
                new File(ShpHeaderReaderTest.class.getResource("polyLine.shp").toURI())).getChannel()) {
            final ShpHeaderReader reader = new ShpHeaderReader(channel);
            ShpTestGeometries.polyLineHeaderTest(reader.read());
        }
    }


    // POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON

    @Test
    public void polygonHeaderTest() throws IOException, URISyntaxException {

        try (FileChannel channel = new FileInputStream(
                new File(ShpHeaderReaderTest.class.getResource("polygon.shp").toURI())).getChannel()) {
            final ShpHeaderReader reader = new ShpHeaderReader(channel);
            ShpTestGeometries.polygonHeaderTest(reader.read());
        }
    }

    @Test
    public void polygon2HeaderTest() throws IOException, URISyntaxException {

        try (FileChannel channel = new FileInputStream(
                new File(ShpHeaderReaderTest.class.getResource("polygon2.shp").toURI())).getChannel()) {
            final ShpHeaderReader reader = new ShpHeaderReader(channel);
            ShpTestGeometries.polygon2HeaderTest(reader.read());
        }
    }

    @Test
    public void coco() throws IOException, URISyntaxException {

        try (FileChannel channel = new FileInputStream(
                new File(ShpHeaderReaderTest.class.getResource("gadm36_FRA_1.shp").toURI())).getChannel()) {
            final ShpHeaderReader reader = new ShpHeaderReader(channel);
            reader.read();
        }
    }
}
