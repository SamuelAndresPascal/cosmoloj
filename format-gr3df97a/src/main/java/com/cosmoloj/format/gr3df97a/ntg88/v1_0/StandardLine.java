package com.cosmoloj.format.gr3df97a.ntg88.v1_0;

/**
 *
 * @author Samuel Andrés
 */
public class StandardLine implements Line {

    private final double longitude;
    private final double latitude;
    private final double tx;
    private final double ty;
    private final double tz;
    private final String precisionCode;
    private final char f50;
    private final int n;

    public StandardLine(final double longitude, final double latitude, final double tx, final double ty,
            final double tz, final String precisionCode, final char f50, final int n) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
        this.precisionCode = precisionCode;
        this.f50 = f50;
        this.n = n;
    }

    public StandardLine(final String line) {
        final String[] entries = line.trim().split("\\s+");
        this.longitude = Double.parseDouble(entries[0]);
        this.latitude = Double.parseDouble(entries[1]);
        this.tx = Double.parseDouble(entries[2]);
        this.ty = Double.parseDouble(entries[3]);
        this.tz = Double.parseDouble(entries[4]);
        this.precisionCode = entries[5];

        switch (entries[6].charAt(0)) {
            case 'L', '-' -> {
                this.f50 = entries[6].charAt(0);
                this.n = Integer.parseInt(entries[6].substring(1));
            }
            default -> {
                this.f50 = ' ';
                this.n = Integer.parseInt(entries[6]);
            }
        }
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getTx() {
        return tx;
    }

    @Override
    public double getTy() {
        return ty;
    }

    @Override
    public double getTz() {
        return tz;
    }

    @Override
    public String getPrecisionCode() {
        return precisionCode;
    }

    @Override
    public char getF50() {
        return f50;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public String toString() {
        return "Line{" + "longitude=" + longitude + ", latitude=" + latitude + ", tx=" + tx + ", ty=" + ty
                + ", tz=" + tz + ", precisionCode=" + precisionCode + ", f50=" + f50 + ", n=" + n + '}';
    }
}
