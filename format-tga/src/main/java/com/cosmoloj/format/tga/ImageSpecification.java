package com.cosmoloj.format.tga;

import com.cosmoloj.bibliography.astroloj.Astroloj;
import com.cosmoloj.util.bib.Reference;
import com.cosmoloj.util.bin.BinaryUtil;

/**
 * <div class="en">This field and its sub-fields describe the image screen location, size and pixel depth.  These bytes
 * are always written to the file.</div>
 *
 * @author Samuel Andrés
 */
@Reference(Astroloj.TGA)
public record ImageSpecification(
    /**
     * <div class="en">Field 5.1 (2 bytes) - X-origin of Image:</div>
     *
     * <div class="en">These bytes specify the absolute horizontal coordinate for the lower left corner of the image as
     * it is positioned on a display device having an origin at the lower left of the screen (e.g., the TARGA series).
     * </div>
     * @return
     */
    short xOrigin,

    /**
     * <div class="en">Field 5.2 (2 bytes) - Y-origin of Image:</div>
     *
     * <div class="en">These bytes specify the absolute vertical coordinate for the lower left corner of the image as it
     * is positioned on a display device having an origin at the lower left of the screen (e.g., the TARGA series).
     * </div>
     * @return
     */
    short yOrigin,

    /**
     * <div class="en">Field 5.3 (2 bytes) - Image Width:</div>
     *
     * <div class="en">This field specifies the width of the image in pixels.</div>
     * @return
     */
    short imageWidth,

    /**
     * <div class="en">Field 5.4 (2 bytes) - Image Height:</div>
     *
     * <div class="en">This field specifies the height of the image in pixels.</div>
     * @return
     */
    short imageHeight,

    /**
     * <div class="en">Field 5.5 (1 byte) - Pixel Depth:</div>
     *
     * <div class="en">This field indicates the number of bits per pixel. This number includes the Attribute or Alpha
     * channel bits.  Common values are 8, 16, 24 and 32 but other pixel depths could be used.</div>
     * @return
     */
    byte pixelDepth,

    /**
     * <div class="en">Field 5.6 (1 byte) - Image Descriptor:</div>
     *
     * <div class="en"><p>Image Descriptor:
     *
     * <p> Bits 3-0: These bits specify the number of attribute bits per pixel.  In the case of the TrueVista, these
     * bits indicate the number of bits per pixel which are designated as Alpha Channel bits.  For the ICB and TARGA
     * products, these bits indicate the number of overlay bits available per pixel.  See field 24 (Attributes Type) for
     * more information.
     *
     * <p> Bits 5 & 4:These bits are used to indicate the order in which pixel data is transferred from the file to the
     * screen. Bit 4 is for left-to-right ordering and bit 5 is for top-to-bottom ordering as shown below.
     *
     * <table>
     * <tr><th rowspan="2">Screen destination of first pixel<th colspan="2">Image Origin
     * <tr><th>bit 5<th>bit 4
     * <tr><td>bottom left<td>0<td>0
     * <tr><td>bottom right<td>0<td>1
     * <tr><td>top left<td>1<td>0
     * <tr><td>top right<td>1<td>1
     * </table>
     *
     * <p>Bits 7 & 6:Must be zero to insure future compatibility.</div>
     * @return
     * @see ImageDescriptor
     */
    @Reference(Astroloj.TGA)
    byte imageDescriptor) {

    /**
     * <div class="fr">Extrait le nombre de bits attributs par pixel à partir du champ "Image Descriptor".</div>
     * @return
     */
    public byte getImageDescriptorAttributeBitsPerPixel() {
        return (byte) (imageDescriptor & 0xf);
    }

    public boolean isImageDescriptorTop() {
        return ((imageDescriptor & 0x20) >>> 5) == 1;
    }

    public boolean isImageDescriptorBottom() {
        return ((imageDescriptor & 0x20) >>> 5) == 0;
    }

    public boolean isImageDescriptorRight() {
        return ((imageDescriptor & 0x10) >>> 4) == 1;
    }

    public boolean isImageDescriptorLeft() {
        return ((imageDescriptor & 0x10) >>> 4) == 0;
    }

    public int attributeBytes() {
        return BinaryUtil.bytesFor(getImageDescriptorAttributeBitsPerPixel());
    }
}
