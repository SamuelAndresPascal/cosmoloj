package com.cosmoloj.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class JulianMarchUtilTest {

    @Test
    public void getMarchMonth0() {
        for (int i = 0; i <= 365; i++) {
            final int marchMonth0 = JulianMarchUtil.getMarchMonth0(i);
            if (i < 31) { // mars
                Assertions.assertEquals(0, marchMonth0);
            } else if (i < 61) { // avril
                Assertions.assertEquals(1, marchMonth0);
            } else if (i < 92) { // mai
                Assertions.assertEquals(2, marchMonth0);
            } else if (i < 122) { // juin
                Assertions.assertEquals(3, marchMonth0);
            } else if (i < 153) { // juillet
                Assertions.assertEquals(4, marchMonth0);
            } else if (i < 184) { // août
                Assertions.assertEquals(5, marchMonth0);
            } else if (i < 214) { // septembre
                Assertions.assertEquals(6, marchMonth0);
            } else if (i < 245) { // octobre
                Assertions.assertEquals(7, marchMonth0);
            } else if (i < 275) { // novembre
                Assertions.assertEquals(8, marchMonth0);
            } else if (i < 306) { // décembre
                Assertions.assertEquals(9, marchMonth0);
            } else if (i < 337) { // janvier
                Assertions.assertEquals(10, marchMonth0);
            } else if (i < 366) { // février
                Assertions.assertEquals(11, marchMonth0);
            }
        }
    }
}
