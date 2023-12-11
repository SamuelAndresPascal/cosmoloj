package com.cosmoloj.format.tga;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Samuel Andrés
 */
public class TgaFooterTest {

    @Test
    public void test() {
        Assertions.assertEquals(26, Footer.LENGTH, "");
        Assertions.assertEquals("TRUEVISION-XFILE", Footer.SIGNATURE, "");
    }
}
