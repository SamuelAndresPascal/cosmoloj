package com.cosmoloj.format.tga;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class ImageTypeTest {

    @Test
    public void test() {

        Assertions.assertEquals(0, ImageType.NO_IMAGE_DATA_INCLUDED.getValue());
        Assertions.assertEquals(1, ImageType.UNCOMPRESSED_COLOR_MAPPED_IMAGE.getValue());
        Assertions.assertEquals(2, ImageType.UNCOMPRESSED_TRUE_COLOR_IMAGE.getValue());
        Assertions.assertEquals(3, ImageType.UNCOMPRESSED_BLACK_AND_WHITE_IMAGE.getValue());
        Assertions.assertEquals(9, ImageType.RLE_ENCODED_COLOR_MAPPED_IMAGE.getValue());
        Assertions.assertEquals(10, ImageType.RLE_ENCODED_TRUE_COLOR_IMAGE.getValue());
        Assertions.assertEquals(11, ImageType.RLE_ENCODED_BLACK_AND_WHITE_IMAGE.getValue());
    }
}
