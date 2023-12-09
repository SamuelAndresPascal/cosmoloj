package com.cosmoloj.format.dbf.v3;

/**
 *
 * @author Samuel Andrés
 */
public record DbfRecord(boolean deleted, byte[][] tuple) {
}
