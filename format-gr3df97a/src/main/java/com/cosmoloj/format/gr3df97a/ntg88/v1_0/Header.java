package com.cosmoloj.format.gr3df97a.ntg88.v1_0;

/**
 *
 * @author Samuel Andrés
 */
public class Header {

    private final String source;
    private final String target;

    private final String crs;

    private final String coordinateType;
    private final String ellipsoid;
    private final String unit;
    private final String meridian;

    private final double longitudeMin;
    private final double longitudeMax;
    private final double latitudeMin;
    private final double latitudeMax;
    private final double deltaLon;
    private final double deltaLat;

    private final String interpolationMode;

    public Header(final String gr3d, final String gr3d1, final String gr3d2, final String gr3d3) {
        final String[] line0 = gr3d.trim().split("\\s+");

        this.source = line0[1].substring(0, 3);
        this.target = line0[1].substring(3);

        this.crs = line0[2];

        this.coordinateType = line0[3].substring(0, 1);
        this.ellipsoid = line0[3].substring(1, 4);
        this.unit = line0[3].substring(4, 6);
        this.meridian = line0[3].substring(6);

        final String[] line1 = gr3d1.trim().split("\\s+");

        this.longitudeMin = Double.parseDouble(line1[1]);
        this.longitudeMax = Double.parseDouble(line1[2]);
        this.latitudeMin = Double.parseDouble(line1[3]);
        this.latitudeMax = Double.parseDouble(line1[4]);
        this.deltaLon = Double.parseDouble(line1[5]);
        this.deltaLat = Double.parseDouble(line1[6]);

        final String gr3d2Trim = gr3d2.trim();
        this.interpolationMode = gr3d2Trim.substring(gr3d2Trim.indexOf(' ')).trim();
    }

    public String getInterpolationMode() {
        return interpolationMode;
    }

    public double getLongitudeMin() {
        return longitudeMin;
    }

    public double getLongitudeMax() {
        return longitudeMax;
    }

    public double getLatitudeMin() {
        return latitudeMin;
    }

    public double getLatitudeMax() {
        return latitudeMax;
    }

    public double getDeltaLon() {
        return deltaLon;
    }

    public double getDeltaLat() {
        return deltaLat;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getCrs() {
        return crs;
    }

    public String getCoordinateType() {
        return coordinateType;
    }

    public String getEllipsoid() {
        return ellipsoid;
    }

    public String getUnit() {
        return unit;
    }

    public String getMeridian() {
        return meridian;
    }

    public boolean handle(final double lon, final double lat) {
        return lon >= getLongitudeMin() && lon <= getLongitudeMax()
                && lat >= getLatitudeMin() && lat <= getLatitudeMax();
    }

    @Override
    public String toString() {
        return "Header{" + "source=" + source + ", target=" + target + ", crs=" + crs
                + ", coordinateType=" + coordinateType + ", ellipsoid=" + ellipsoid + ", unit=" + unit
                + ", meridian=" + meridian + ", longitudeMin=" + longitudeMin + ", longitudeMax=" + longitudeMax
                + ", latitudeMin=" + latitudeMin + ", latitudeMax=" + latitudeMax + ", deltaLon=" + deltaLon
                + ", deltaLat=" + deltaLat + ", interpolationMode=" + interpolationMode + '}';
    }
}
