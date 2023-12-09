package com.cosmoloj.format.dbf.v3;

import java.time.LocalDate;

/**
 * <div class="en">
 * <table>
 *    <caption>The table file header
 * (<a href="http://www.oocities.org/geoff_wass/dBASE/GaryWhite/dBASE/FAQ/qformt.htm">source</a>)</caption>
 *    <tr>
 *       <th>Byte
 *       <th>Contents
 *       <th>Description
 *
 *     <tr>
 *       <td>0
 *       <td>1 byte
 *       <td>Valid dBASE III PLUS table file (03h without a memo (.DBT file; 83h with a memo).
 *
 *     <tr>
 *       <td>1-3
 *       <td>3 bytes
 *       <td>Date of last update; in YYMMDD format.
 *
 *     <tr>
 *       <td>4-7
 *       <td>32-bit number
 *       <td>Number of records in the table.
 *
 *     <tr>
 *       <td>8-9
 *       <td>16-bit number
 *       <td>Number of bytes in the header.
 *
 *     <tr>
 *       <td>10-11
 *       <td>16-bit number
 *       <td>Number of bytes in the record.
 *
 *
 *     <tr>
 *       <td>12-14
 *       <td>3 bytes
 *       <td>Reserved bytes.
 *
 *     <tr>
 *       <td>15-27
 *       <td>13 bytes
 *       <td>Reserved for dBASE III PLUS on a LAN.
 *
 *     <tr>
 *       <td>28-31
 *       <td>4 bytes
 *       <td>Reserved bytes.
 *
 *     <tr>
 *       <td>32-n
 *       <td>32 bytes
 *       <td>Field descriptor array (the structure of this array is each shown below)
 *
 *     <tr>
 *       <td>n+1
 *       <td>1 byte
 *       <td>0Dh stored as the field terminator.
 *
 *   </table>
 * </div>
 *
 * @author Samuel Andrés
 */
public record DbfHeader(
        byte valid,
        LocalDate lastUpdate,
        int recordNb,
        short headerByteNb,
        short recordByteNb,
        DbfColumnHeader[] columnHeaders) {
}
