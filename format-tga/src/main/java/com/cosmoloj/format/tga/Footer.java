package com.cosmoloj.format.tga;

/**
 * <div class="en">
 * <p>TGA FILE FOOTER</p>
 * <ol><li>A TGA Reader should begin by determining whether the desired file is in the Original TGA Format or the New
 * TGA Format.  This is accomplished by examining the last 26 bytes of the file (most operating systems support some
 * type of SEEK function).  Reading the last 26 bytes from the file will either retrieve the last 26 bytes of image data
 * (if the file is in the Original TGA Format), or it will retrieve the TGA File Footer (if the file is in the New TGA
 * Format).</li>
 * <li><p>To determine whether the acquired data constitutes a legal TGA File Footer, scan bytes 8-23 of the footer as
 * ASCII characters and determine whether they match the signature string:</p>
 * <p>TRUEVISION-XFILE</p>
 * <p>This string is exactly 16 bytes long and is formatted exactly as shown above (capital letters), with a hyphen
 * between “TRUEVISION” and “XFILE.”  If the signature is detected, the file is assumed to be in the New TGA format and
 * MAY, therefore, contain the Developer Area and/or the Extension Area fields.  If the signature is not found, then the
 * file is assumed to be in the Original TGA format and should only contain areas 1 and 2; therefore, the byte format
 * for the TGA File Footer is defined as follows:</p>
 * <dl>
 * <dt>Bytes 0-3</dt>
 * <dd>The Extension Area Offset</dd>
 * <dt>Bytes 4-7</dt>
 * <dd>The Developer Directory Offset</dd>
 * <dt>Bytes 8-23</dt>
 * <dd>The Signature</dd>
 * <dt>Byte 24</dt>
 * <dd>ASCII Character  “.”</dd>
 * <dt>Byte 25</dt>
 * <dd>Binary zero string terminator (0x00)</dd>
 * </dl>
 * </li>
 * </ol>
 * </div>
 * @see References#TGA_SPECIFICATION
 *
 * @author Samuel Andrés
 */
public record Footer(

    /**
     * <div class="en">
     * <p>Byte 0-3 - Extension Area Offset - Field 28</p>
     * <p>The first four bytes (bytes 0-3, the first LONG) of the TGA File Footer  contain an offset from the beginning
     * of the file to the start of the Extension  Area.  Simply SEEK to this location to position to the start of the
     * Extension Area.  If the Extension Area Offset is zero, no Extension Area exists in the file.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    int extensionAreaOffset,

    /**
     * <div class="en">
     * <p>Byte 4-7 - Developer Directory Offset - Field 29.</p>
     * <p>The next four bytes (bytes 4-7, the second LONG) contain an offset from the beginning of the file to the start
     * of the Developer Directory.  If the Developer Directory Offset is zero, then the Developer Area does not exist.
     * </p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    int developerDirectoryOffset,

    /**
     * <div class="en">
     * <p>Byte 8-23 - Signature - Field 30</p>
     * <p>This string is exactly 16 bytes long and is formatted exactly as shown below capital letters), with a hyphen
     * between “TRUEVISION” and “XFILE.”  If the signature is detected, the file is assumed to be of the New TGA format
     * and MAY, therefore, contain the Developer Area and/or the Extension Area fields.  If the signature is not found,
     * then the file is assumed to be in the Original TGA format.</p>
     * <p>TRUEVISION-XFILE</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    String signature,

    /**
     * <div class="en">
     * <p>Byte 24 - Reserved Character - Field 31</p>
     * <p>Byte 24 is an ASCII character “.” (period).  This character MUST BE a period or the file is not considered a
     * proper TGA file.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    byte reservedChar,

    /**
     * <div class="en">
     * <p>Byte 25 - Binary Zero String Terminator - Field 32</p>
     * <p>Byte 25 is a binary zero which acts as a final terminator and allows the entire TGA File Footer  to be read
     * and utilized as a “C” string.</p>
     * </div>
     * @return
     * @see References#TGA_SPECIFICATION
     */
    byte zero) {

    public static final int LENGTH = 26;

    public static final String SIGNATURE = "TRUEVISION-XFILE";

    public static long footerPosition(final long size) {
        return size - Footer.LENGTH;
    }
}
