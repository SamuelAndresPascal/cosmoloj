package com.cosmoloj.util.bin;

import java.nio.ByteOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class ExpandCoreTest {

    @Test
    public void expandBigEndian1() {

        final ExpandCore expand = new ExpandCore(new byte[]{-128, 0, 6, 112, 49, 24, -28, 2, -64, -127, 64},
                FillOrder.FROM_HIGH, ByteOrder.LITTLE_ENDIAN);
        Assertions.assertEquals(256, expand.get(9));
        Assertions.assertEquals(0, expand.get(9));
        Assertions.assertEquals(51, expand.get(9));
        Assertions.assertEquals(259, expand.get(9));
        Assertions.assertEquals(35, expand.get(9));
        Assertions.assertEquals(57, expand.get(9));
        Assertions.assertEquals(1, expand.get(9));
        Assertions.assertEquals(192, expand.get(9));
        Assertions.assertEquals(258, expand.get(9));
    }
}
