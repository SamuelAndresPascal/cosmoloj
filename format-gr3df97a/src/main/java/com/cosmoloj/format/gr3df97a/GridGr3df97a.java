package com.cosmoloj.format.gr3df97a;

import com.cosmoloj.format.gr3df97a.ntg88.v1_0.Header;
import com.cosmoloj.format.gr3df97a.ntg88.v1_0.Line;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author Samuel Andrés
 */
public class GridGr3df97a implements Consumer<String>, Function<double[], double[]> {

    private final Header header;
    private final List<Line> lines;
    private final Function<String, Line> constructor;
    private final int rows;
    private final int cols;

    public GridGr3df97a(final Header header, final Function<String, Line> constructor) {
        this.header = header;
        this.lines = new ArrayList<>();
        this.constructor = constructor;
        this.rows = (int) Math.round((header.getLatitudeMax() - header.getLatitudeMin()) / header.getDeltaLat()) + 1;
        this.cols = (int) Math.round((header.getLongitudeMax() - header.getLongitudeMin()) / header.getDeltaLon()) + 1;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    @Override
    public void accept(final String line) {
        this.lines.add(constructor.apply(line));
    }

    public Header getHeader() {
        return header;
    }

    @Override
    public double[] apply(final double[] input) {

        final double lon = input[0];
        final double lat = input[1];

        // détermination des noeuds de la grille pour interpolation
        final int r = (int) Math.floor((lat - header.getLatitudeMin()) / header.getDeltaLat());
        final int c = (int) Math.floor((lon - header.getLongitudeMin()) / header.getDeltaLon());

        final Line l1 = lines.get(r + c * rows);
        final Line l2 = lines.get(r + 1 + c * rows);
        final Line l3 = lines.get(r + (c + 1) * rows);
        final Line l4 = lines.get(r + 1 + (c + 1) * rows);

        final double[][] t = {l1.getT(), l2.getT(), l3.getT(), l4.getT()};

        final double x = (lon - l1.getLongitude()) / (l3.getLongitude() - l1.getLongitude());
        final double y = (lat - l1.getLatitude()) / (l2.getLatitude() - l1.getLatitude());

        final double[] xy = new double[]{(1. - x) * (1. - y), (1. - x) * y, x * (1. - y), x * y};

        return DoubleTabulars.mult(xy, t);
    }

    public static GridGr3df97a read(final Path path) throws IOException {
        try (var reader = new Gr3df97aReader(path)) {
            final GridGr3df97a consumer = new GridGr3df97a(reader.getHeader(), FileLine::new);
            reader.read(consumer);
            return consumer;
        }
    }

    public static GridGr3df97a read() throws IOException {
        return read(Paths.get(GridGr3df97a.class.getResource("gr3df97a.txt").getPath()));
    }
}
