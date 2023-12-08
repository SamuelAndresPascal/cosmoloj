package com.cosmoloj.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class TrigonometryUtilTest {

    @Test
    public void testDmsToRadians() {
        Assertions.assertEquals(2 * Math.PI, TrigonometryUtil.dmsToRadians(360., 0, 0));
    }
    @Test
    public void testHmsToRadians() {
        Assertions.assertEquals(2 * Math.PI, TrigonometryUtil.hmsToRadians(24., 0, 0));
    }
}
