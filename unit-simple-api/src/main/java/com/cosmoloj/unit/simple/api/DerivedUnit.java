package com.cosmoloj.unit.simple.api;

import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public interface DerivedUnit extends Unit {

    List<Factor> definition();
}
