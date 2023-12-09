package com.cosmoloj.format.dbf.v3;

/**
 * <div class="fr">
 * <table border="1" width="100%" cellspacing="0">
 *     <caption>Allowable Input for dBASE Data Types
 * (<a href="http://www.oocities.org/geoff_wass/dBASE/GaryWhite/dBASE/FAQ/qformt.htm">source</a>)</caption>
 *     <tr>
 *       <th>Data Type
 *       <th>Data Input
 *
 *     <tr>
 *       <td>C (Character)
 *       <td>All OEM code page characters.
 *
 *     <tr>
 *       <td>D (Date)
 *       <td>Numbers and a character to separate month, day, and year (stored internally as 8 digits in YYYYMMDD
 * format).
 *
 *     <tr>
 *       <td>N (Numeric)
 *       <td>- . 0 1 2 3 4 5 6 7 8 9
 *
 *     <tr>
 *       <td>L (Logical)
 *       <td>? Y y N n T t F f (? when not initialized).
 *
 *     <tr>
 *       <td>M (Memo)
 *       <td>All OEM code page characters (stored internally as 10 digits representing a .DBT block number).
 *
 *   </table>
 * </div>
 *
 * @author Samuel Andrés
 */
public final class DbfType {

    private DbfType() {
    }

    public static final byte CHARACTER = 'C';
    public static final byte DATE = 'D';
    public static final byte NUMERIC = 'N';
    public static final byte LOGICAL = 'L';
    public static final byte MEMO = 'M';
}
