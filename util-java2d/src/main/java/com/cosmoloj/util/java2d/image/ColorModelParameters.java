package com.cosmoloj.util.java2d.image;

import java.awt.color.ColorSpace;

/**
 *
 * @author Samuel Andrés
 */
public record ColorModelParameters(int[] pixelBits, ColorSpace colorSpace) {

    public static ColorModelParameters of3(final int pixelBits, final ColorSpace colorSpace) {
        return new ColorModelParameters(new int[]{pixelBits, pixelBits, pixelBits}, colorSpace);
    }

    public static ColorModelParameters of1(final int pixelBits, final ColorSpace colorSpace) {
        return new ColorModelParameters(new int[]{pixelBits}, colorSpace);
    }

    public static ColorModelParameters of(final int[] pixelBits, final int colorSpace) {
        return new ColorModelParameters(pixelBits, ColorSpace.getInstance(colorSpace));
    }

    public static ColorModelParameters of3(final int pixelBits, final int colorSpace) {
        return new ColorModelParameters(new int[]{pixelBits, pixelBits, pixelBits},
                ColorSpace.getInstance(colorSpace));
    }

    public static ColorModelParameters of1(final int pixelBits, final int colorSpace) {
        return new ColorModelParameters(new int[]{pixelBits}, ColorSpace.getInstance(colorSpace));
    }
}
