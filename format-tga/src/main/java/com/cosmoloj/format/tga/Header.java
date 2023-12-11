package com.cosmoloj.format.tga;

/**
 *
 * @author Samuel Andrés
 */
public record Header(

    /**
     * <div class="en">ID Length - Field 1 (1 byte):</div>
     *
     * <div class="en">This field identifies the number of bytes contained in Field 6, the Image ID Field.  The maximum
     * number of characters is 255.  A value of zero indicates that no Image ID field is included with the image.</div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    byte idLength,

    /**
     * <div class="en">Color Map Type - Field 2 (1 byte):</div>
     *
     * <div class="en"><p>This field indicates the type of color map (if any) included with the image.  There are
     * currently 2 defined values for this field:</p>
     *
     * <ul>
     * <li>0 - indicates that no color-map data is included with this image.</li>
     * <li>1 - indicates that a color-map is included with this image.</li>
     * </ul>
     *
     * <p>
     * The first 128 Color Map Type codes (Field 2) are reserved for use by Truevision, while the second set of 128
     * Color Map Type codes (128 to 255) may be used for developer applications.
     * </p>
     *
     * <p>
     * True-Color images do not normally make use of the color map field, but some current applications store palette
     * information or developer-defined information in this field.  It is best to check Field 3, Image Type, to make
     * sure you have a file which can use the data stored in the Color Map Field. Otherwise ignore the information.
     * </p>
     *
     * <p>
     * When saving or creating files for True-Color images do not use this field and set it to Zero to ensure
     * compatibility.  Please refer to the Developer Area specification for methods of storing developer defined
     * information.</p></div>
     * @return
     * @see References#TGA_SPECIFICATION
     * @see ColorMapType
     */
    ColorMapType colorMapType,

    /**
     * <div class="en">Image Type - Field 3 (1 byte):</div>
     *
     * <div class="en"><p>The TGA File Format can be used to store Pseudo-Color, True-Color and Direct-Color images of
     * various pixel depths.  Truevision has currently defined seven image types:</p>
     *
     * <table>
     * <caption>Table 1 - Image Types</caption>
     * <tr><th>Image Type</th><th>Description</th></tr>
     * <tr><td>0</td><td>No Image Data Included</td></tr>
     * <tr><td>1</td><td>Uncompressed, Color-mapped Image</td></tr>
     * <tr><td>2</td><td>Uncompressed, True-color Image</td></tr>
     * <tr><td>3</td><td>Uncompressed, Black-and-white Image</td></tr>
     * <tr><td>9</td><td>Run-length encoded, Color-mapped Image</td></tr>
     * <tr><td>10</td><td>Run-length encoded, True-color Image</td></tr>
     * <tr><td>11</td><td>Run-length encoded, Black-and-white Image</td></tr>
     * </table>
     *
     * <p>Image Data Type codes 0 to 127 are reserved for use by Truevision for general applications.  Image Data Type
     * codes 128 to 255 may be used for developer applications. For a complete description of these image-data types,
     * see the IMAGE TYPES section later in this manual</p></div>
     * @return
     * @see ImageType
     * @see References#TGA_SPECIFICATION
     */
    ImageType imageType,

    /**
     * <div class="en">Color Map Specification - Field 4 (5 bytes):</div>
     *
     * <div class="en">This field and its sub-fields describe the color map (if any) used for the image.  If the Color
     * Map Type field is set to zero, indicating that no color map exists, then these 5 bytes should be set to zero.
     * These bytes always must be written to the file</div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    ColorMapSpecification colorMapSpecification,

    /**
     * <div class="en">Image Specification - Field 5 (10 bytes):</div>
     *
     * <div class="en">This field and its sub-fields describe the image screen location, size and pixel depth.  These
     * bytes are always written to the file.</div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    ImageSpecification imageSpecification) {

    public static final int LENGTH = 18;

    /**
     * <div class="fr">Construit l'information du nombre de bit utilisé pour encoder une bande.</div>
     * @return
     */
    public int getColorDepth() {
        return (imageSpecification().pixelDepth()
                - imageSpecification().getImageDescriptorAttributeBitsPerPixel())
                / imageType().getColorBandNumber();
    }

    public int getPixelAllocation() {
        return imageType.getColorBandNumber() + imageSpecification.attributeBytes();
    }
}
