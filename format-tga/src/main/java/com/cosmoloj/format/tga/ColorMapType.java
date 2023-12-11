package com.cosmoloj.format.tga;

/**
 *
 * @author Samuel Andrés
 */
public enum ColorMapType {
    NO_COLOR_MAP,
    COLOR_MAP;

    public byte getValue() {
        return (byte) ordinal();
    }

    public static ColorMapType forByte(final byte b) {
        for (final ColorMapType it : values()) {
            if (it.getValue() == b) {
                return it;
            }
        }
        throw new IllegalArgumentException();
    }
}
