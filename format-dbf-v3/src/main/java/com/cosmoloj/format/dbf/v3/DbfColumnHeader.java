package com.cosmoloj.format.dbf.v3;

import java.nio.charset.Charset;

/**
 * <div class="en">
 * <table>
 *     <caption>Table Field Descriptor Bytes
 * (<a href="http://www.oocities.org/geoff_wass/dBASE/GaryWhite/dBASE/FAQ/qformt.htm">source</a>)</caption>
 *     <tr>
 *       <th>Byte
 *       <th>Contents
 *       <th>Description
 *
 *     <tr>
 *       <td>0-10
 *       <td>11 bytes
 *       <td>Field name in ASCII (zero-filled).
 *
 *     <tr>
 *       <td>11
 *       <td>1 byte
 *       <td>Field type in ASCII (C, D, L, M, or N).
 *
 *     <tr>
 *       <td>12-15
 *       <td>4 bytes
 *       <td>Field data address (address is set in memory; not useful on disk).
 *
 *     <tr>
 *       <td>16
 *       <td>1 byte
 *       <td>Field length in binary.
 *
 *     <tr>
 *       <td>17
 *       <td>1 byte
 *       <td>Field decimal count in binary.
 *
 *     <tr>
 *       <td>18-19
 *       <td>2 bytes
 *       <td>Reserved for dBASE III PLUS on a LAN.
 *
 *     <tr>
 *       <td>20
 *       <td>1 byte
 *       <td>Work area ID.
 *
 *     <tr>
 *       <td>21-22
 *       <td>2 bytes
 *       <td>Reserved for dBASE III PLUS on a LAN.
 *
 *     <tr>
 *       <td>23
 *       <td>1 byte
 *       <td>SET FIELDS flag.
 *
 *     <tr>
 *       <td>24-31
 *       <td>1 byte
 *       <td>Reserved bytes.
 *
 *   </table>
 * </div>
 *
 * @author Samuel Andrés
 * @param <T> <span class="fr">type de données correspondant</span>
 */
public abstract sealed class DbfColumnHeader<T>
        permits DbfCharacterColumnHeader, DbfLocalDateColumnHeader,
        DbfNumericDoubleColumnHeader, DbfNumericIntegerColumnHeader {

    private final String name;
    private final byte type;
    private final int memory;
    private final byte size;
    private final byte decimals;

    protected DbfColumnHeader(final String name, final byte type, final int memory, final byte size,
            final byte decimals) {
        this.name = name;
        this.type = type;
        this.memory = memory;
        this.size = size;
        this.decimals = decimals;
    }

    /**
     * <div class="fr">Nom de colonne (ASCII sur 11 bytes).</div>
     *
     * @return <span class="fr"></span>
     */
    public String getName() {
        return name;
    }

    /**
     * <div class="fr"></div>
     *
     * @return <span class="fr"></span>
     */
    public byte getType() {
        return type;
    }

    /**
     * <div class="fr"></div>
     *
     * @return <span class="fr"></span>
     */
    public int getMemory() {
        return memory;
    }

    /**
     * <div class="fr"></div>
     *
     * @return <span class="fr"></span>
     */
    public byte getSize() {
        return size;
    }

    /**
     * <div class="fr"></div>
     *
     * @return <span class="fr"></span>
     */
    public byte getDecimals() {
        return decimals;
    }

    /**
     * <div class="fr"></div>
     *
     * @param bytes <span class="fr"></span>
     * @return <span class="fr"></span>
     */
    public abstract T fromBytes(byte[] bytes);

    /**
     * <div class="fr"></div>
     *
     * @param data <span class="fr"></span>
     * @return <span class="fr"></span>
     */
    public abstract byte[] toBytes(T data);

    /**
     * <div class="fr"></div>
     *
     * @param name <span class="fr"></span>
     * @param type <span class="fr"></span>
     * @param memory <span class="fr"></span>
     * @param size <span class="fr"></span>
     * @param decimals <span class="fr"></span>
     * @param charset <span class="fr"></span>
     * @param trimValues
     * @return <span class="fr"></span>
     */
    public static DbfColumnHeader<?> of(final String name, final byte type, final int memory, final byte size,
            final byte decimals, final Charset charset, final boolean trimValues) {
        return switch (type) {
            case DbfType.CHARACTER -> new DbfCharacterColumnHeader(name, memory, size, decimals, charset, trimValues);
            case DbfType.NUMERIC -> decimals == 0
                        ? new DbfNumericIntegerColumnHeader(name, memory, size)
                        : new DbfNumericDoubleColumnHeader(name, memory, size, decimals);
            case DbfType.DATE -> new DbfLocalDateColumnHeader(name, memory, size, decimals);
            default -> throw new UnsupportedOperationException("dbf type not supported:" + type);
        };
    }
}
