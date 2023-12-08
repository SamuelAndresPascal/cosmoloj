package com.cosmoloj.util.sql;

/**
 *
 * @author Samuel Andrés
 */
public enum ReferenceType {
    // si la référence est mentionnée par EPSG
    NORMATIVE,
    // si la référence n'est pas mentionnée par EPSG mais est logique et peut être effectivement mise en pratique
    EFFECTIVE;
}
