package com.cosmoloj.language.wkt2.v2_1.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.wkt2.v2_1.expression.Area;
import com.cosmoloj.language.wkt2.v2_1.expression.Usage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class WktParserTestM2m1 {


    @Test
    public void usage_area_test_1() throws LanguageException {

        final var text = """
                         USAGE[SCOPE["mon scope"],AREA["Netherlands offshore."]]""";

        final WktParser parser = WktParser.of(text);

        final Usage usage = parser.usage();

        final Area area = (Area) usage.getExtent();

        Assertions.assertEquals("Netherlands offshore.", area.getName().getSemantics());

        Assertions.assertEquals(30, area.getName().first());
        Assertions.assertEquals(52, area.getName().last());
        Assertions.assertEquals(10, area.getName().order());

        Assertions.assertEquals(25, area.first());
        Assertions.assertEquals(53, area.last());
        Assertions.assertEquals(12, area.order());
    }
}
