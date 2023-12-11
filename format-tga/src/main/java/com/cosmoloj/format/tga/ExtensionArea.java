package com.cosmoloj.format.tga;

import java.util.Arrays;
import java.util.Objects;

/**
 * <div class="en">
 * <p>The Extension Area was created to satisfy requests from developers for additional file information.</p>
 * <p>Every attempt has been made to keep the format of the Extension Area simple yet flexible enough to satisfy most
 * developers’ needs.</p>
 * <p>The Extension Area is pointed to by the Extension Area Offset in the TGA File Footer. If the Offset is ZERO
 * (binary zero) then no Extension Area exists. Otherwise, this value is the offset from the beginning of the file to
 * the beginning of the Extension Area. The combination of the Developer Area and the Extension Area should serve
 * Truevision and its developer community well into the future. However, should technology dictate additional changes to
 * the specification, they will be quite easy to implement. The first field of the Extension Area is the
 * “Extension Size” field. This field currently contains the value 495, indicating that there are 495 bytes in the
 * fixed-length portion of the Extension Area. If future development warrants additional fields, then this value may
 * change to indicate that new fields have been added. Any change in the number of bytes in the Extension Area will be
 * made by Truevision and will be relayed to the Developer community via a revised TGA File Format Specification. TGA
 * readers should only parse as much as they understand (i.e., 495 bytes for this first release). If they encounter
 * additional bytes (as specified by the “Extension Size”) they should not preserve them if rebuilding the file, since
 * they do not know the nature of the extra bytes.</p>
 * </div>
 * @see References#TGA_SPECIFICATION
 *
 * @author Samuel Andrés
 */
public record ExtensionArea(

    /**
     * <div class="en">
     * <p>Extension Size - Field 10 (2 Bytes): Bytes 0-1</p>
     * <p>This field is a SHORT field which specifies the number of BYTES in the fixed-length portion of the Extension
     * Area. For Version 2.0 of the TGA File Format, this number should be set to 495. If the number found in this field
     * is not 495, then the file will be assumed to be of a version other than 2.0. If it ever becomes necessary to
     * alter this number, the change will be controlled by Truevision, and will be accompanied by a revision to the TGA
     * File Format with an accompanying change in the version number.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    short extensionSize,

    /**
     * <div class="en">
     * <p>Author Name - Field 11 (41 Bytes): Bytes 2-42</p>
     * <p>This field is an ASCII field of 41 bytes where the last byte must be a null (binary zero). This gives a total
     * of 40 ASCII characters for the name. If the field is used, it should contain the name of the person who created
     * the image(author). If the field is not used, you may fill it with nulls or a series of blanks (spaces) terminated
     * by a null. The 41st byte must always be a null.
     * </p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    String authorName,

    /**
     * <div class="en">
     * <p>Author Comments - Field 12 (324 Bytes): Bytes 43-366</p>
     * <p>This is an ASCII field consisting of 324 bytes which are organized as four lines of 80 characters, each
     * followed by a null terminator. This field is provided, in addition to the original IMAGE ID field (in the
     * original TGA format), because it was determined that a few developers had used the IMAGE ID field for their own
     * purposes. This field gives the developer four lines of 80 characters each, to use as an Author Comment area. Each
     * line is fixed to 81 bytes which makes access to the four lines easy. Each line must be terminated by a null. If
     * you do not use all 80 available characters in the line, place the null after the last character and blank or null
     * fill the rest of the line. The 81st byte of each of the four lines must be null.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    String authorComments,

    /**
     * <div class="en">
     * <p>Date/Time Stamp - Field 13 (12 Bytes): Bytes 367-378</p>
     * <p>This field contains a series of 6 SHORT values which define the integer value for the date and time that the
     * image was saved. This data is formatted as follows:</p>
     * <table>
     * <tr><td>SHORT 0:</td><td>Month</td><td>(1 - 12)</td></tr>
     * <tr><td>SHORT 1:</td><td>Day</td><td>(1 - 31)</td></tr>
     * <tr><td>SHORT 2:</td><td>Year</td><td>(4 digit, ie. 1989)</td></tr>
     * <tr><td>SHORT 3:</td><td>Hour</td><td>(0 - 23)</td></tr>
     * <tr><td>SHORT 4:</td><td>Minute</td><td>(0 - 59)</td></tr>
     * <tr><td>SHORT 5:</td><td>Second</td><td>(0 - 59)</td></tr>
     * </table>
     * <p>Even though operating systems typically time- and date-stamp files, this feature is provided because the
     * operating system may change the time and date stamp if the file is copied. By using this area, you are guaranteed
     * an unmodified region for date and time recording. If the fields are not used, you should fill them with binary
     * zeros (0).
     * </p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    short[] dateTimeStamp,

    /**
     * <div class="en">
     * <p>Job Name/ID - Field 14 (41 Bytes): Bytes 379-419</p>
     * <p>This field is an ASCII field of 41 bytes where the last byte must be a binary zero.</p>
     * <p>This gives a total of 40 ASCII characters for the job name or the ID. If the field is used, it should contain
     * a name or id tag which refers to the job with which the image was associated. This allows production companies
     * (and others) to tie images with jobs by using this field as a job name (i.e., CITY BANK) or job id number (i.e.,
     * CITY023). If the field is not used, you may fill it with a null terminated series of blanks (spaces) or nulls. In
     * any case, the 41st byte must be a null.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    String jobName,

    /**
     *
     * <div class="en">
     * <p>Job Time - Field 15 (6 Bytes): Bytes 420-425</p>
     * <p>This field contains a series of 3 SHORT values which define the integer value for the job elapsed time when
     * the image was saved. This data is formatted as follows:</p>
     *
     * <table>
     * <tr><td>SHORT 0:</td><td>Hours</td><td>(0 - 65535)</td></tr>
     * <tr><td>SHORT 1:</td><td>Minutes</td><td>(0 - 59)</td></tr>
     * <tr><td>SHORT 2:</td><td>Seconds</td><td>(0 - 59)</td></tr>
     * </table>
     *
     * <p>The purpose of this field is to allow production houses (and others) to keep a running total of the amount of
     * time invested in a particular image. This may be useful for billing, costing, and time estimating. If the fields
     * are not used, you should fill them with binary zeros (0).</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    short[] jobTime,

    /**
     * <div class="en">
     * <p>Software ID - Field 16 (41 Bytes): Bytes 426-466</p>
     * <p>This field is an ASCII field of 41 bytes where the last byte must be a binary zero (null). This gives a total
     * of 40 ASCII characters for the Software ID. The purpose of this field is to allow software to determine and
     * record with what program a particular image was created. If the field is not used, you may fill it with a null
     * terminated series of blanks (spaces) or nulls. The 41st byte must always be a null.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    String softwareId,

    /**
     * <div class="en">
     * <p>Software Version - Field 17 (3 Bytes): Bytes 467-469</p>
     * <p>This field consists of two sub-fields, a SHORT and an ASCII BYTE. The purpose of this field is to define the
     * version of software defined by the “Software ID” field above. The SHORT contains the version number as a binary
     * integer times 100.<p>
     * <p>Therefore, software version 4.17 would be the integer value 417. This allows for two decimal positions of
     * sub-version. The ASCII BYTE supports developers who also tag a release letter to the end. For example, if the
     * version number is 1.17b, then the SHORT would contain 117. and the ASCII BYTE would contain “b”.</p>
     * <p>The organization is as follows:</p>
     * <table>
     * <tr><td>SHORT</td><td>(Bytes 0 - 1)</td><td>Version Number * 100</td></tr>
     * <tr><td>BYTE</td><td>(Byte 2)</td><td>Version Letter</td></tr>
     * </table>
     *
     * <p>If you do not use this field, set the SHORT to binary zero, and the BYTE to a space (“ “).</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    byte[] softwareVersion,

    /**
     * <div class="en">
     * <p>Key Color - Field 18 (4 Bytes): Bytes 470-473</p>
     * <p>This field contains a long value which is the key color in effect at the time the image is saved. The format
     * is in A:R:G:B where ‘A’ (most significant byte) is the alpha channel key color (if you don’t have an alpha
     * channel in your application, keep this byte zero [0]).</p>
     * <p>The Key Color can be thought of as the ‘background color’ or ‘transparent color’. This is the color of the
     * ‘non image’ area of the screen, and the same color that the screen would be cleared to if erased in the
     * application. If you don’t use this field, set it to all zeros (0). Setting the field to all zeros is the same as
     * selecting a key color of black.</p>
     * <p>A good example of a key color is the ‘transparent color’ used in TIPS for WINDOW loading/saving.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    int keyColor,

    /**
     * <div class="en">
     * <p>Pixel Aspect Ratio - Field 19 (4 Bytes): Bytes 474-477</p>
     * <p>This field contains two SHORT sub-fields, which when taken together specify a pixel size ratio. The format is
     * as follows:</p>
     * <table>
     * <tr><td>SHORT 0:</td><td>Pixel Ratio Numerator</td><td>(pixel width)</td></tr>
     * <tr><td>SHORT 1:</td><td>Pixel Ratio Denominator</td><td>(pixel height)</td></tr>
     * </table>
     *
     * <p>These sub-fields may be used to determine the aspect ratio of a pixel. This is useful when it is important to
     * preserve the proper aspect ratio of the saved image.</p>
     * <p>If the two values are set to the same non-zero value, then the image is composed of square pixels. A zero in
     * the second sub-field (denominator) indicates that no pixel aspect ratio is specified.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    short[] pixelAspectRatio,

    /**
     * <div class="en">
     * <p>Gamma Value - Field 20 (4 Bytes): Bytes 478-481</p>
     * <p>This field contains two SHORT sub-fields, which when taken together in a ratio, provide a fractional gamma
     * value.  The format is as follows:</p>
     *
     * <table>
     * <tr><td>SHORT 0:</td><td>Gamma Numerator</td></tr>
     * <tr><td>SHORT 1:</td><td>Gamma Denominator</td></tr>
     * </table>
     *
     * <p>The resulting value should be in the range of 0.0 to 10.0, with only one decimal place of precision necessary.
     * An uncorrected image (an image with no gamma) should have the value 1.0 as the result. This may be accomplished
     * by placing the same, non-zero values in both positions (i.e., 1/1). If you decide to totally ignore this field,
     * please set the denominator (the second SHORT) to the value zero. This will indicate that the Gamma Value field is
     * not being used.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    short[] gammaValue,

    /**
     * <div class="en">
     * <p>Color Correction Offset - Field 21 (4 Bytes): Bytes 482-485</p>
     * <p>This field is a 4-byte field containing a single offset value.  This is an offset from the beginning of the
     * file to the start of the Color Correction table.  This table may be written anywhere between the end of the Image
     * Data field (field 8) and the start of the TGA File Footer.</p>
     * <p>If the image has no Color Correction Table or if the Gamma Value setting is sufficient, set this value to zero
     * and do not write a Correction Table anywhere.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    int colorCorrectionOffset,

    /**
     * <div class="en">
     * <p>Postage Stamp Offset - Field 22 (4 Bytes): Bytes 486-489</p>
     * <p>This field is a 4-byte field containing a single offset value.  This is an offset from the beginning of the
     * file to the start of the Postage Stamp Image.  The Postage Stamp Image must be written after Field 25 (Scan Line
     * Table) but before the start of the TGA File Footer.</p>
     * <p>If no postage stamp is stored, set this field to the value zero (0).</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    int postageStampOffset,

    /**
     * <div class="en">
     * <p>Scan Line Offset - Field 23 (4 Bytes): Bytes 490-493</p>
     * <p>This field is a 4-byte field containing a single offset value.  This is an offset from the beginning of the
     * file to the start of the Scan Line Table.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    int scanLineOffset,

    /**
     * <div class="en">
     * <p>Attributes Type - Field 24 (1 Byte): Byte 494</p>
     * <p>This single byte field contains a value which specifies the type of Alpha channel data contained in the file.
     * </p>
     * <table>
     * <tr><td>0:</td><td>no Alpha data included (bits 3-0 of field 5.6 should also be set to zero)</td></tr>
     * <tr><td>1:</td><td>undefined data in the Alpha field, can be ignored</td></tr>
     * <tr><td>2:</td><td>undefined data in the Alpha field, but should be retained</td></tr>
     * <tr><td>3:</td><td>useful Alpha channel data is present</td></tr>
     * <tr><td>4:</td><td>pre-multiplied Alpha (see description below)</td></tr>
     * <tr><td>5 -127:</td><td>RESERVED</td></tr>
     * <tr><td>128-255:</td><td>Un-assigned</td></tr>
     * </table>
     * <p>Pre-multiplied Alpha Example:  Suppose the Alpha channel data is being used to specify the opacity of each
     * pixel (for use when the image is overlayed on another image), where 0 indicates that the pixel is completely
     * transparent and a value of 1 indicates that the pixel is completely opaque (assume all component values have been
     * normalized).  A quadruple (a, r, g, b) of  ( 0.5, 1, 0, 0) would indicate that the pixel is pure red with a
     * transparency of one-half.  For numerous reasons (including image compositing) is is better to pre-multiply the
     * individual color components with the value in the Alpha channel.  A pre-multiplication of the above would produce
     * a quadruple (0.5, 0.5, 0, 0).</p>
     * <p>A value of 3 in the Attributes Type Field (field 23) would indicate that the color components of the pixel
     * have already been scaled by the value in the Alpha channel.  For more information concerning pre-multiplied
     * values, refer to the 1984 SIGGRAPH Conference Proceedings.</p>
     * <p>Porter, T., T. Duff, “Compositing Digital Images,” SIGGRAPH ‘84 Conference Proceedings, vol. 18, no. 3,
     * p. 254 , ACM,  July 1984.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    byte attributesType,

    /**
     * <div class="en">
     * <p>Scan Line Table - Field 25 (Variable):</p>
     * <p>This information is provided, at the developers’ request, for two purposes:</p>
     * <ol>
     * <li>To make random access of compressed images easy.</li>
     * <li>To allow “giant picture” access in smaller “chunks”.</li>
     * </ol>
     * <p>This table should contain a series of 4-byte offsets.  Each offset you write should point to the start of the
     * next scan line, in the order that the image was saved (i.e., top down or bottom up).  The offset should be from
     * the start of the file.  Therefore, you will have a four byte value for each scan line in your image.  This means
     * that if your image is 768 pixels tall, you will have 768, 4-byte offset pointers (for a total of 3072 bytes).
     * This size is not extreme, and thus this table can be built and maintained in memory, and then written out at the
     * proper time.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    int[] scanLineTable,

    /**
     * <div class="en">
     * <p>Postage  Stamp Image - Field 26 (Variable):</p>
     * <p>The Postage Stamp area is a smaller representation of the original image.  This is useful for “browsing” a
     * collection of image files.  If your application can deal with a postage stamp image, it is recommended that you
     * create one using sub-sampling techniques to create the best representation possible. The postage stamp image must
     * be stored in the same format as the normal image specified in the file, but without any compression.  The first
     * byte of the postage stamp image specifies the X size of the stamp in pixels, the second byte of the stamp image
     * specifies the Y size of the stamp in pixels. Truevision does not recommend stamps larger than 64 x 64 pixels, and
     * suggests that any stamps stored larger be clipped.  Obviously, the storage of the postage stamp relies heavily on
     * the storage of the image.  The two images are stored using the same format under the assumption that if you can
     * read the image, then you can read the postage stamp.  If the original image is color mapped, DO NOT average the
     * postage stamp, as you will create new colors not in your map.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    byte[] postageStampImage,

    /**
     * <div class="en">
     * <p>Color Correction Table - Field 27 (2K Bytes):</p>
     * <p>The Color Correction Table is a block of 256 x 4 SHORT values, where each set of four contiguous values are
     * the desired A:R:G:B correction for that entry.  This allows the user to store a correction table for image
     * remapping or LUT driving.</p>
     * <p>Since each color in the block is a SHORT, the maximum value for a color gun (i.e., A, R, G, B) is 65535, and
     * the minimum value is zero.  Therefore, BLACK maps to 0, 0, 0, 0 and WHITE maps to 65535, 65535, 65535, 65535.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    short[][] colorCorrectionTable) {

    public static final int AUTHOR_NAME_LENGTH = 41;
    public static final int AUTHOR_COMMENTS_LENGTH = 324;
    public static final int JOB_NAME_LENGTH = 41;
    public static final int SOFTWARE_ID_LENGTH = 41;


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.extensionSize;
        hash = 67 * hash + Objects.hashCode(this.authorName);
        hash = 67 * hash + Objects.hashCode(this.authorComments);
        hash = 67 * hash + Arrays.hashCode(this.dateTimeStamp);
        hash = 67 * hash + Objects.hashCode(this.jobName);
        hash = 67 * hash + Arrays.hashCode(this.jobTime);
        hash = 67 * hash + Objects.hashCode(this.softwareId);
        hash = 67 * hash + Arrays.hashCode(this.softwareVersion);
        hash = 67 * hash + this.keyColor;
        hash = 67 * hash + Arrays.hashCode(this.pixelAspectRatio);
        hash = 67 * hash + Arrays.hashCode(this.gammaValue);
        hash = 67 * hash + this.colorCorrectionOffset;
        hash = 67 * hash + this.postageStampOffset;
        hash = 67 * hash + this.scanLineOffset;
        hash = 67 * hash + this.attributesType;
        hash = 67 * hash + Arrays.hashCode(this.scanLineTable);
        hash = 67 * hash + Arrays.hashCode(this.postageStampImage);
        hash = 67 * hash + Arrays.deepHashCode(this.colorCorrectionTable);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExtensionArea other = (ExtensionArea) obj;

        return this.extensionSize == other.extensionSize
                && this.keyColor == other.keyColor
                && this.colorCorrectionOffset == other.colorCorrectionOffset
                && this.postageStampOffset == other.postageStampOffset
                && this.scanLineOffset == other.scanLineOffset
                && this.attributesType == other.attributesType
                && Objects.equals(this.authorName, other.authorName)
                && Objects.equals(this.authorComments, other.authorComments)
                && Objects.equals(this.jobName, other.jobName)
                && Objects.equals(this.softwareId, other.softwareId)
                && Arrays.equals(this.dateTimeStamp, other.dateTimeStamp)
                && Arrays.equals(this.jobTime, other.jobTime)
                && Arrays.equals(this.softwareVersion, other.softwareVersion)
                && Arrays.equals(this.pixelAspectRatio, other.pixelAspectRatio)
                && Arrays.equals(this.gammaValue, other.gammaValue)
                && Arrays.equals(this.scanLineTable, other.scanLineTable)
                && Arrays.equals(this.postageStampImage, other.postageStampImage)
                && Arrays.deepEquals(this.colorCorrectionTable, other.colorCorrectionTable);
    }

    @Override
    public String toString() {
        return "ExtensionArea{" + "extensionSize=" + extensionSize + ", authorName=" + authorName
                + ", authorComments=" + authorComments + ", dateTimeStamp=" + Arrays.toString(dateTimeStamp)
                + ", jobName=" + jobName + ", jobTime=" + Arrays.toString(jobTime) + ", softwareId=" + softwareId
                + ", softwareVersion=" + Arrays.toString(softwareVersion) + ", keyColor=" + keyColor
                + ", pixelAspectRatio=" + Arrays.toString(pixelAspectRatio)
                + ", gammaValue=" + Arrays.toString(gammaValue) + ", colorCorrectionOffset=" + colorCorrectionOffset
                + ", postageStampOffset=" + postageStampOffset + ", scanLineOffset=" + scanLineOffset
                + ", attributesType=" + attributesType + ", scanLineTable=" + Arrays.toString(scanLineTable)
                + ", postageStampImage=" + Arrays.toString(postageStampImage)
                + ", colorCorrectionTable=" + Arrays.toString(colorCorrectionTable) + '}';
    }
}
