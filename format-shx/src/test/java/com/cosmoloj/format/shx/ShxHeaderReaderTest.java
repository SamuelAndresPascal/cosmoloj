package com.cosmoloj.format.shx;

import com.cosmoloj.format.shp.ShpHeaderReader;
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
public class ShxHeaderReaderTest {

    @Test
    public void pointHeaderTest() throws IOException, URISyntaxException {
        final File file = new File(ShxHeaderReaderTest.class.getResource("point.shx").toURI());

        try (FileChannel channel = new FileInputStream(file).getChannel()) {
            final ShpHeaderReader reader = new ShpHeaderReader(channel);

            ShxTestIndex.pointHeaderTest(reader.read());
        }
    }

    // TODO: MULTIPOINT


    @Test
    public void polyLineHeaderTest() throws IOException, URISyntaxException {

        final File file = new File(ShxHeaderReaderTest.class.getResource("polyLine.shx").toURI());

        try (FileChannel channel = new FileInputStream(file).getChannel()) {
            final ShpHeaderReader reader = new ShpHeaderReader(channel);
            ShxTestIndex.polyLineHeaderTest(reader.read());
        }
    }


    // POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON -- POLYGON

    @Test
    public void polygonHeaderTest() throws IOException, URISyntaxException {

        final File file = new File(ShxHeaderReaderTest.class.getResource("polygon.shx").toURI());

        try (FileChannel channel = new FileInputStream(file).getChannel()) {
            final ShpHeaderReader reader = new ShpHeaderReader(channel);

            ShxTestIndex.polygonHeaderTest(reader.read());
        }
    }

    @Test
    public void polygon2HeaderTest() throws IOException, URISyntaxException {

        final File file = new File(ShxHeaderReaderTest.class.getResource("polygon2.shx").toURI());

        try (FileChannel channel = new FileInputStream(file).getChannel()) {
            final ShpHeaderReader reader = new ShpHeaderReader(channel);

            ShxTestIndex.polygon2HeaderTest(reader.read());
        }
    }
    /*
    @Test
    public void coco() throws IOException, URISyntaxException {

        final ShpHeaderReader reader = new ShpHeaderReader(
    new File(ShxHeaderReaderTest.class.getResource("gadm36_FRA_1.shx").toURI()));
        reader.read();
    }*/
}
