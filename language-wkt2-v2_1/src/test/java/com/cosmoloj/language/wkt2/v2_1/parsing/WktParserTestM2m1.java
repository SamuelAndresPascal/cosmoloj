package com.cosmoloj.language.wkt2.v2_1.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.wkt2.v2_1.expression.Area;
import com.cosmoloj.language.wkt2.v2_1.expression.CoordinateSystem;
import com.cosmoloj.language.wkt2.v2_1.expression.GeodeticCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.SimpleCrsShell;
import com.cosmoloj.language.wkt2.v2_1.expression.Unit;
import com.cosmoloj.language.wkt2.v2_1.expression.Usage;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.CsType;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.Direction;
import java.time.LocalDate;
import java.time.Month;
import java.time.OffsetDateTime;
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

    @Test
    public void compound_crs_test_c_1() throws LanguageException {

        final var text = "COMPOUNDCRS[\"GPS position and time\","
                + "GEODCRS[\"WGS 84\","
                + "DATUM[\"World Geodetic System 1984\","
                + "ELLIPSOID[\"WGS 84\",6378137,298.257223563]],"
                + "CS[ellipsoidal,2],"
                + "AXIS[\"(lat)\",north,ORDER[1]],"
                + "AXIS[\"(lon)\",east,ORDER[2]],"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "TIMECRS[\"GPS Time\","
                + "TIMEDATUM[\"Time origin\",TIMEORIGIN[1980-01-01]],"
                + "CS[temporal,1],"
                + "AXIS[\"time (T)\",future],"
                + "TEMPORALQUANTITY[\"day\",86400]]]";

        final WktParser parser = WktParser.of(text);

        final var compoundCrs = parser.compoundCrs();

        Assertions.assertEquals("GPS position and time", compoundCrs.getName().getSemantics());

        Assertions.assertTrue(compoundCrs.getHorizontal() instanceof GeodeticCrs.Geographic2DCrs);
        final var geodetic = (GeodeticCrs.Geographic2DCrs) compoundCrs.getHorizontal();

        Assertions.assertEquals("WGS 84", geodetic.getName().getSemantics());

        final var datum = geodetic.getDatum();

        Assertions.assertEquals("World Geodetic System 1984", datum.getName().getSemantics());

        final var ellipsoid = datum.getEllipsoid();

        Assertions.assertEquals("WGS 84", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257223563, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertNull(ellipsoid.getUnit());

        Assertions.assertNull(datum.getPrimeMeridian());

        final var cs = geodetic.getCoordinateSystem();
        Assertions.assertTrue(cs instanceof CoordinateSystem.Ellipsoidal2DCoordinateSystem);
        Assertions.assertEquals(CsType.ELLIPSOIDAL, cs.getType().getSemantics());
        Assertions.assertEquals(2, cs.getDimension().getSemantics().intValue());

        Assertions.assertEquals(2, cs.getAxis().size());

        final var axis0 = cs.getAxis().get(0);
        Assertions.assertEquals("(lat)", axis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis0.getDirection().getType().getSemantics());
        Assertions.assertEquals(1, axis0.getOrder().getValue().getSemantics().intValue());
        final var axis1 = cs.getAxis().get(1);
        Assertions.assertEquals("(lon)", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());
        Assertions.assertEquals(2, axis1.getOrder().getValue().getSemantics().intValue());

        final var csUnit = cs.getUnit();
        Assertions.assertTrue(csUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", csUnit.getName().getSemantics());
        Assertions.assertTrue(csUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, csUnit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertTrue(compoundCrs.getSecondCrs() instanceof SimpleCrsShell.TemporalCrs);

        final var timeCrs = (SimpleCrsShell.TemporalCrs) compoundCrs.getSecondCrs();
        Assertions.assertEquals("GPS Time", timeCrs.getName().getSemantics());

        final var timeDatum = timeCrs.getDatum();
        Assertions.assertEquals("Time origin", timeDatum.getName().getSemantics());

        final var timeOrigin = timeDatum.getAnchor();
        Assertions.assertTrue(timeOrigin.getDescription().getSemantics() instanceof LocalDate);
        Assertions.assertEquals(LocalDate.of(1980, Month.JANUARY, 1), timeOrigin.getDescription().getSemantics());

        final var timeCs = timeCrs.getCoordinateSystem();
        Assertions.assertEquals(CsType.TEMPORAL, timeCs.getType().getSemantics());
        Assertions.assertEquals(1, timeCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(1, timeCs.getAxis().size());

        final var axisParametric = timeCs.getAxis().get(0);
        Assertions.assertEquals("time (T)", axisParametric.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.future, axisParametric.getDirection().getType().getSemantics());

        final var parametricUnit = timeCs.getUnit();
        Assertions.assertTrue(parametricUnit instanceof Unit.Time);
        Assertions.assertEquals("day", parametricUnit.getName().getSemantics());
        Assertions.assertTrue(parametricUnit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(86400, parametricUnit.getConversionFactor().getSemantics().intValue());

        Assertions.assertNull(compoundCrs.getThirdCrs());
    }

    @Test
    public void temporal_crs_test_a_1() throws LanguageException {

        final var text = "TIMECRS[\"GPS Time\","
                + "TDATUM[\"Time origin\",TIMEORIGIN[1980-01-01T00:00:00.0Z]],"
                + "CS[temporal,1],AXIS[\"time\",future],TEMPORALQUANTITY[\"day\",86400.0]]";

        final WktParser parser = WktParser.of(text);

        final var temporal = parser.temporalCrs();

        Assertions.assertEquals("GPS Time", temporal.getName().getSemantics());

        final var datum = temporal.getDatum();

        Assertions.assertEquals("Time origin", datum.getName().getSemantics());

        Assertions.assertEquals(OffsetDateTime.parse("1980-01-01T00:00:00.0Z"),
                datum.getAnchor().getDescription().getSemantics());

        final var cs = temporal.getCoordinateSystem();

        Assertions.assertEquals(CsType.TEMPORAL, cs.getType().getSemantics());
        Assertions.assertEquals(1, cs.getDimension().getSemantics().intValue());

        Assertions.assertEquals(1, cs.getAxis().size());

        final var axis0 = cs.getAxis().get(0);
        Assertions.assertEquals("time", axis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.future, axis0.getDirection().getType().getSemantics());
        Assertions.assertNull(axis0.getOrder());

        final var unit = cs.getUnit();
        Assertions.assertTrue(unit instanceof Unit.Time);
        Assertions.assertEquals("day", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(86400.0, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertTrue(temporal.getUsages().isEmpty());
    }

    @Test
    public void time_unit_test_a_1() throws LanguageException {

        final var text = "TEMPORALQUANTITY[\"day\",86400.0]";

        final WktParser parser = WktParser.of(text);

        final Unit.Time unit = parser.timeUnit();

        Assertions.assertEquals("day", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(86400., ((Double) unit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());
    }

    @Test
    public void time_unit_test_b_1() throws LanguageException {

        final var text = """
                         TEMPORALQUANTITY["day",86400.0,ID["EPSG",1029],ID["LOCAL","1",CITATION["citation"]]]""";

        final WktParser parser = WktParser.of(text);

        final Unit.Time unit = parser.timeUnit();

        Assertions.assertEquals("day", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(86400., ((Double) unit.getConversionFactor().getSemantics()).doubleValue());

        final var identifiers = unit.getIdentifiers();
        Assertions.assertEquals(2, identifiers.size());

        final var id0 = identifiers.get(0);
        Assertions.assertEquals("EPSG", id0.getName().getSemantics());
        Assertions.assertTrue(id0.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(1029, ((Integer) id0.getId().getSemantics()).intValue());
        Assertions.assertNull(id0.getVersion());
        Assertions.assertNull(id0.getUri());
        Assertions.assertNull(id0.getCitation());

        final var id1 = identifiers.get(1);
        Assertions.assertEquals("LOCAL", id1.getName().getSemantics());
        Assertions.assertTrue(id1.getId().getSemantics() instanceof String);
        Assertions.assertEquals("1", id1.getId().getSemantics());
        Assertions.assertNull(id1.getVersion());
        Assertions.assertNull(id1.getUri());

        final var citation1 = id1.getCitation();
        Assertions.assertEquals("citation", citation1.getText().getSemantics());
    }


    @Test
    public void usage_area_test_2() throws LanguageException {

        final var text = """
                         USAGE(SCOPE("mon scope"),AREA("Netherlands offshore."))""";

        final WktParser parser = WktParser.of(text, '(', ')');

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

    @Test
    public void compound_crs_test_c_2() throws LanguageException {

        final var text = "COMPOUNDCRS(\"GPS position and time\","
                + "GEODCRS(\"WGS 84\","
                + "DATUM(\"World Geodetic System 1984\","
                + "ELLIPSOID(\"WGS 84\",6378137,298.257223563)),"
                + "CS(ellipsoidal,2),"
                + "AXIS(\"(lat)\",north,ORDER(1)),"
                + "AXIS(\"(lon)\",east,ORDER(2)),"
                + "ANGLEUNIT(\"degree\",0.0174532925199433)),"
                + "TIMECRS(\"GPS Time\","
                + "TIMEDATUM(\"Time origin\",TIMEORIGIN(1980-01-01)),"
                + "CS(temporal,1),"
                + "AXIS(\"time (T)\",future),"
                + "TEMPORALQUANTITY(\"day\",86400)))";

        final WktParser parser = WktParser.of(text, '(', ')');

        final var compoundCrs = parser.compoundCrs();

        Assertions.assertEquals("GPS position and time", compoundCrs.getName().getSemantics());

        Assertions.assertTrue(compoundCrs.getHorizontal() instanceof GeodeticCrs.Geographic2DCrs);
        final var geodetic = (GeodeticCrs.Geographic2DCrs) compoundCrs.getHorizontal();

        Assertions.assertEquals("WGS 84", geodetic.getName().getSemantics());

        final var datum = geodetic.getDatum();

        Assertions.assertEquals("World Geodetic System 1984", datum.getName().getSemantics());

        final var ellipsoid = datum.getEllipsoid();

        Assertions.assertEquals("WGS 84", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257223563, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertNull(ellipsoid.getUnit());

        Assertions.assertNull(datum.getPrimeMeridian());

        final var cs = geodetic.getCoordinateSystem();
        Assertions.assertTrue(cs instanceof CoordinateSystem.Ellipsoidal2DCoordinateSystem);
        Assertions.assertEquals(CsType.ELLIPSOIDAL, cs.getType().getSemantics());
        Assertions.assertEquals(2, cs.getDimension().getSemantics().intValue());

        Assertions.assertEquals(2, cs.getAxis().size());

        final var axis0 = cs.getAxis().get(0);
        Assertions.assertEquals("(lat)", axis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis0.getDirection().getType().getSemantics());
        Assertions.assertEquals(1, axis0.getOrder().getValue().getSemantics().intValue());
        final var axis1 = cs.getAxis().get(1);
        Assertions.assertEquals("(lon)", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());
        Assertions.assertEquals(2, axis1.getOrder().getValue().getSemantics().intValue());

        final var csUnit = cs.getUnit();
        Assertions.assertTrue(csUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", csUnit.getName().getSemantics());
        Assertions.assertTrue(csUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, csUnit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertTrue(compoundCrs.getSecondCrs() instanceof SimpleCrsShell.TemporalCrs);

        final var timeCrs = (SimpleCrsShell.TemporalCrs) compoundCrs.getSecondCrs();
        Assertions.assertEquals("GPS Time", timeCrs.getName().getSemantics());

        final var timeDatum = timeCrs.getDatum();
        Assertions.assertEquals("Time origin", timeDatum.getName().getSemantics());

        final var timeOrigin = timeDatum.getAnchor();
        Assertions.assertTrue(timeOrigin.getDescription().getSemantics() instanceof LocalDate);
        Assertions.assertEquals(LocalDate.of(1980, Month.JANUARY, 1), timeOrigin.getDescription().getSemantics());

        final var timeCs = timeCrs.getCoordinateSystem();
        Assertions.assertEquals(CsType.TEMPORAL, timeCs.getType().getSemantics());
        Assertions.assertEquals(1, timeCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(1, timeCs.getAxis().size());

        final var axisParametric = timeCs.getAxis().get(0);
        Assertions.assertEquals("time (T)", axisParametric.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.future, axisParametric.getDirection().getType().getSemantics());

        final var parametricUnit = timeCs.getUnit();
        Assertions.assertTrue(parametricUnit instanceof Unit.Time);
        Assertions.assertEquals("day", parametricUnit.getName().getSemantics());
        Assertions.assertTrue(parametricUnit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(86400, parametricUnit.getConversionFactor().getSemantics().intValue());

        Assertions.assertNull(compoundCrs.getThirdCrs());
    }

    @Test
    public void temporal_crs_test_a_2() throws LanguageException {

        final var text = "TIMECRS(\"GPS Time\","
                + "TDATUM(\"Time origin\",TIMEORIGIN(1980-01-01T00:00:00.0Z)),"
                + "CS(temporal,1),AXIS(\"time\",future),TEMPORALQUANTITY(\"day\",86400.0))";

        final WktParser parser = WktParser.of(text, '(', ')');

        final var temporal = parser.temporalCrs();

        Assertions.assertEquals("GPS Time", temporal.getName().getSemantics());

        final var datum = temporal.getDatum();

        Assertions.assertEquals("Time origin", datum.getName().getSemantics());

        Assertions.assertEquals(OffsetDateTime.parse("1980-01-01T00:00:00.0Z"),
                datum.getAnchor().getDescription().getSemantics());

        final var cs = temporal.getCoordinateSystem();

        Assertions.assertEquals(CsType.TEMPORAL, cs.getType().getSemantics());
        Assertions.assertEquals(1, cs.getDimension().getSemantics().intValue());

        Assertions.assertEquals(1, cs.getAxis().size());

        final var axis0 = cs.getAxis().get(0);
        Assertions.assertEquals("time", axis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.future, axis0.getDirection().getType().getSemantics());
        Assertions.assertNull(axis0.getOrder());

        final var unit = cs.getUnit();
        Assertions.assertTrue(unit instanceof Unit.Time);
        Assertions.assertEquals("day", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(86400.0, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertTrue(temporal.getUsages().isEmpty());
    }

    @Test
    public void time_unit_test_a_2() throws LanguageException {

        final var text = "TEMPORALQUANTITY(\"day\",86400.0)";

        final WktParser parser = WktParser.of(text, '(', ')');

        final Unit.Time unit = parser.timeUnit();

        Assertions.assertEquals("day", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(86400., ((Double) unit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());
    }

    @Test
    public void time_unit_test_b_2() throws LanguageException {

        final var text = """
                         TEMPORALQUANTITY("day",86400.0,ID("EPSG",1029),ID("LOCAL","1",CITATION("citation")))""";

        final WktParser parser = WktParser.of(text, '(', ')');

        final Unit.Time unit = parser.timeUnit();

        Assertions.assertEquals("day", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(86400., ((Double) unit.getConversionFactor().getSemantics()).doubleValue());

        final var identifiers = unit.getIdentifiers();
        Assertions.assertEquals(2, identifiers.size());

        final var id0 = identifiers.get(0);
        Assertions.assertEquals("EPSG", id0.getName().getSemantics());
        Assertions.assertTrue(id0.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(1029, ((Integer) id0.getId().getSemantics()).intValue());
        Assertions.assertNull(id0.getVersion());
        Assertions.assertNull(id0.getUri());
        Assertions.assertNull(id0.getCitation());

        final var id1 = identifiers.get(1);
        Assertions.assertEquals("LOCAL", id1.getName().getSemantics());
        Assertions.assertTrue(id1.getId().getSemantics() instanceof String);
        Assertions.assertEquals("1", id1.getId().getSemantics());
        Assertions.assertNull(id1.getVersion());
        Assertions.assertNull(id1.getUri());

        final var citation1 = id1.getCitation();
        Assertions.assertEquals("citation", citation1.getText().getSemantics());
    }
}
