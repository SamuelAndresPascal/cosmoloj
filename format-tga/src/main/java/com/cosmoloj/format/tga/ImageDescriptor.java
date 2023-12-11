package com.cosmoloj.format.tga;


/**
 * <div class="en"><p>Image Descriptor:</p>
 *
 * <p> Bits 3-0: These bits specify the number of attribute bits per pixel.  In the case of the TrueVista, these
 * bits indicate the number of bits per pixel which are designated as Alpha Channel bits.  For the ICB and TARGA
 * products, these bits indicate the number of overlay bits available per pixel.  See field 24 (Attributes Type) for
 * more information.</p>
 *
 * <p> Bits 5 & 4:These bits are used to indicate the order in which pixel data is transferred from the file to the
 * screen. Bit 4 is for left-to-right ordering and bit 5 is for top-to-bottom ordering as shown below.</p>
 *
 * <table>
 * <tr><th rowspan="2">Screen destination of first pixel</th><th colspan="2">Image Origin</th></tr>
 * <tr><th>bit 5</th><th>bit 4</th></tr>
 * <tr><td>bottom left</td><td>0</td><td>0</td></tr>
 * <tr><td>bottom right</td><td>0</td><td>1</td></tr>
 * <tr><td>top left</td><td>1</td><td>0</td></tr>
 * <tr><td>top right</td><td>1</td><td>1</td></tr>
 * </table>
 *
 * <p>Bits 7 & 6:Must be zero to insure future compatibility.</p></div>
 *
 * @author Samuel Andrés
 * @see References#TGA_SPECIFICATION
 */
public enum ImageDescriptor {

    BOTTOM_LEFT((byte) 0x00),
    BOTTOM_RIGHT((byte) 0x10),
    TOP_LEFT((byte) 0x20),
    TOP_RIGHT((byte) 0x30);

    private final byte value;

    ImageDescriptor(final byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
