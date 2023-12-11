package com.cosmoloj.format.tga;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class ColorMapTypeTest {

    @Test
    public void test() {

        Assertions.assertEquals(0, ColorMapType.NO_COLOR_MAP.getValue());
        Assertions.assertEquals(1, ColorMapType.COLOR_MAP.getValue());
    }
}
