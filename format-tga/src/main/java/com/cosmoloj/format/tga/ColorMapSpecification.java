package com.cosmoloj.format.tga;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.util.bib.Reference;

/**
 * <div class="en">This field and its sub-fields describe the color map (if any) used for the image.  If the Color Map
 * Type field is set to zero, indicating that no color map exists, then these 5 bytes should be set to zero. These bytes
 * always must be written to the file.</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.TGA)
public record ColorMapSpecification(

    /**
     * <div class="en">Field 4.1 (2 bytes) - First Entry Index:</div>
     *
     * <div class="en"><p>Index of the first color map entry.  Index refers to the starting entry in loading the color
     * map.
     *
     * <p>Example:  If you would have 1024 entries in the entire color map but you only need to store 72 of those
     * entries, this field allows you to start in the middle of the color-map (e.g., position 342).</div>
     *
     * @return
     */
    short firstEntryIndex,

    /**
     * <div class="en">Field 4.2 (2 bytes) - Color map Length:</div>
     *
     * <div class="en">Total number of color map entries included.</div>
     *
     * @return
     */
    short colorMapLength,

    /**
     * <div class="en">Field 4.3 (1 byte) - Color map Entry Size:</div>
     *
     * <div class="en"><p>Establishes the number of bits per entry.  Typically 15, 16, 24 or 32-bit values are used.
     *
     * <p>When working with VDA or VDA/D cards it is preferred that you select 16 bits (5 bits per primary with 1 bit to
     * select interrupt control) and set the 16th bit to 0 so that the interrupt bit is disabled.  Even if this field is
     * set to 15 bits (5 bits per primary) you must still parse the color map data 16 bits at a time and ignore the 16th
     * bit.
     *
     * <p>When working with a TARGA M8 card you would select 24 bits (8 bits per primary) since the color map is defined
     * as 256 entries of 24 bit color values.
     *
     * <p>When working with a TrueVista card (ATVista or NuVista) you would select 24-bit (8 bits per primary) or 32-bit
     * (8 bits per primary including Alpha channel) depending on your application’s use of look-up tables. It is
     * suggested that when working with 16-bit and 32-bit color images, you store them as True-Color images and do not
     * use the color map field to store look-up tables. Please refer to the TGA Extensions for fields better suited to
     * storing look-up table information.</div>
     *
     * @return
     */
    @Reference(Cosmoloj.TGA)
    byte colorMapEntrySize) {
}
