package com.cosmoloj.util.conversion;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class ConverterUtilTest {

    @Test
    public void comparablePriority_1() {
        Assertions.assertEquals(Double.class, ConverterUtil.comparablePriority(List.of(Integer.class, Double.class)));
    }
}
