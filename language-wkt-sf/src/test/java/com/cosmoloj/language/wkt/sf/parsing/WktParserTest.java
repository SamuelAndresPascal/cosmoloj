package com.cosmoloj.language.wkt.sf.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.common.impl.parsing.DefaultStreamScanner;
import com.cosmoloj.language.wkt.sf.expression.Datum;
import com.cosmoloj.language.wkt.sf.expression.GeocentricCs;
import com.cosmoloj.language.wkt.sf.expression.GeographicCs;
import com.cosmoloj.language.wkt.sf.expression.Parameter;
import com.cosmoloj.language.wkt.sf.expression.PrimeMeridian;
import com.cosmoloj.language.wkt.sf.expression.ProjectedCs;
import com.cosmoloj.language.wkt.sf.expression.Projection;
import com.cosmoloj.language.wkt.sf.expression.Spheroid;
import com.cosmoloj.language.wkt.sf.expression.Unit;
import com.cosmoloj.language.wkt.sf.lexeme.WktName;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class WktParserTest {

    private static final int LD = '[';
    private static final int RD = ']';

    @Test
    public void unit_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("UNIT[\"Degree\",0.0174532925199433]");

        final Unit unit = parser.unit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(5, unit.getName().first());
        Assertions.assertEquals(12, unit.getName().last());
        Assertions.assertEquals(2, unit.getName().order());

        Assertions.assertEquals(14, unit.getConversionFactor().first());
        Assertions.assertEquals(31, unit.getConversionFactor().last());
        Assertions.assertEquals(8, unit.getConversionFactor().order());

        Assertions.assertEquals(0, unit.first());
        Assertions.assertEquals(32, unit.last());
        Assertions.assertEquals(10, unit.order());
    }

    @Test
    public void prime_meridian_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("PRIMEM[\"Greenwitch\",0]");

        final PrimeMeridian primeMeridian = parser.primeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(7, primeMeridian.getName().first());
        Assertions.assertEquals(18, primeMeridian.getName().last());
        Assertions.assertEquals(2, primeMeridian.getName().order());

        Assertions.assertEquals(20, primeMeridian.getLongitude().first());
        Assertions.assertEquals(20, primeMeridian.getLongitude().last());
        Assertions.assertEquals(6, primeMeridian.getLongitude().order());

        Assertions.assertEquals(0, primeMeridian.first());
        Assertions.assertEquals(21, primeMeridian.last());
        Assertions.assertEquals(8, primeMeridian.order());
    }

    @Test
    public void parameter_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("PARAMETER[\"False_Easting\",500000.0]");

        final Parameter parameter = parser.parameter();

        Assertions.assertEquals("False_Easting", parameter.getName().getSemantics());
        Assertions.assertEquals(500000, parameter.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(10, parameter.getName().first());
        Assertions.assertEquals(24, parameter.getName().last());
        Assertions.assertEquals(2, parameter.getName().order());

        Assertions.assertEquals(26, parameter.getValue().first());
        Assertions.assertEquals(33, parameter.getValue().last());
        Assertions.assertEquals(8, parameter.getValue().order());

        Assertions.assertEquals(0, parameter.first());
        Assertions.assertEquals(34, parameter.last());
        Assertions.assertEquals(10, parameter.order());
    }

    @Test
    public void projection_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("PROJECTION[\"Transverse_Mercator\"]");

        final Projection projection = parser.projection();

        Assertions.assertEquals("Transverse_Mercator", projection.getName().getSemantics());

        Assertions.assertEquals(11, projection.getName().first());
        Assertions.assertEquals(31, projection.getName().last());
        Assertions.assertEquals(2, projection.getName().order());

        Assertions.assertEquals(0, projection.first());
        Assertions.assertEquals(32, projection.last());
        Assertions.assertEquals(4, projection.order());
    }

    @Test
    public void spheroid_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("SPHEROID[\"GRS_1980\",6378137,298.257222101]");

        final Spheroid spheroid = parser.spheroid();

        Assertions.assertEquals(WktName.SPHEROID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(9, spheroid.getName().first());
        Assertions.assertEquals(18, spheroid.getName().last());
        Assertions.assertEquals(2, spheroid.getName().order());

        Assertions.assertEquals(20, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(26, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(6, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(28, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(40, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(12, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(0, spheroid.first());
        Assertions.assertEquals(41, spheroid.last());
        Assertions.assertEquals(14, spheroid.order());
    }

    @Test
    public void ellipsoid_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("ELLIPSOID[\"GRS_1980\",6378137,298.257222101]");

        final Spheroid spheroid = parser.spheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(10, spheroid.getName().first());
        Assertions.assertEquals(19, spheroid.getName().last());
        Assertions.assertEquals(2, spheroid.getName().order());

        Assertions.assertEquals(21, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(27, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(6, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(29, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(41, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(12, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(0, spheroid.first());
        Assertions.assertEquals(42, spheroid.last());
        Assertions.assertEquals(14, spheroid.order());
    }

    @Test
    public void datum_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]]""");

        final Datum datum = parser.datum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(6, datum.getName().first());
        Assertions.assertEquals(28, datum.getName().last());
        Assertions.assertEquals(2, datum.getName().order());

        Assertions.assertEquals(0, datum.first());
        Assertions.assertEquals(73, datum.last());
        Assertions.assertEquals(20, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(40, spheroid.getName().first());
        Assertions.assertEquals(49, spheroid.getName().last());
        Assertions.assertEquals(6, spheroid.getName().order());

        Assertions.assertEquals(51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(10, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(16, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(30, spheroid.first());
        Assertions.assertEquals(72, spheroid.last());
        Assertions.assertEquals(18, spheroid.order());
    }

    @Test
    public void geoccs_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],UNIT["Degree",0.0174532925199433]]""");

        final GeocentricCs geoccs = parser.geoccs();

        Assertions.assertEquals("GCS_North_American_1983", geoccs.getName().getSemantics());

        Assertions.assertEquals(7, geoccs.getName().first());
        Assertions.assertEquals(31, geoccs.getName().last());
        Assertions.assertEquals(2, geoccs.getName().order());

        Assertions.assertEquals(0, geoccs.first());
        Assertions.assertEquals(164, geoccs.last());
        Assertions.assertEquals(48, geoccs.order());

        final Datum datum = geoccs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(33 + 73, datum.last());
        Assertions.assertEquals(24, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(33 + 40, spheroid.getName().first());
        Assertions.assertEquals(33 + 49, spheroid.getName().last());
        Assertions.assertEquals(10, spheroid.getName().order());

        Assertions.assertEquals(33 + 51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(33 + 57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(14, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(33 + 59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(33 + 71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(20, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(33 + 30, spheroid.first());
        Assertions.assertEquals(33 + 72, spheroid.last());
        Assertions.assertEquals(22, spheroid.order());

        final PrimeMeridian primeMeridian = geoccs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(28, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getLongitude().last());
        Assertions.assertEquals(32, primeMeridian.getLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(34, primeMeridian.order());

        final Unit unit = geoccs.getLinearUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(44, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(46, unit.order());
    }

    @Test
    public void geogcs_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],UNIT["Degree",0.0174532925199433]]""");

        final GeographicCs geogcs = parser.geogcs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(164, geogcs.last());
        Assertions.assertEquals(48, geogcs.order());

        final Datum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(33 + 73, datum.last());
        Assertions.assertEquals(24, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(33 + 40, spheroid.getName().first());
        Assertions.assertEquals(33 + 49, spheroid.getName().last());
        Assertions.assertEquals(10, spheroid.getName().order());

        Assertions.assertEquals(33 + 51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(33 + 57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(14, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(33 + 59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(33 + 71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(20, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(33 + 30, spheroid.first());
        Assertions.assertEquals(33 + 72, spheroid.last());
        Assertions.assertEquals(22, spheroid.order());

        final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(28, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getLongitude().last());
        Assertions.assertEquals(32, primeMeridian.getLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(34, primeMeridian.order());

        final Unit unit = geogcs.getAngularUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(44, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(46, unit.order());

        Assertions.assertNull(geogcs.getLinearUnit());
    }

    @Test
    public void geogcs_3d_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],\
                                              UNIT["Degree",0.0174532925199433],UNIT["Meter",1.0]]""");

        final GeographicCs geogcs = parser.geogcs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(182, geogcs.last());
        Assertions.assertEquals(60, geogcs.order());

        final Datum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(33 + 73, datum.last());
        Assertions.assertEquals(24, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(33 + 40, spheroid.getName().first());
        Assertions.assertEquals(33 + 49, spheroid.getName().last());
        Assertions.assertEquals(10, spheroid.getName().order());

        Assertions.assertEquals(33 + 51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(33 + 57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(14, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(33 + 59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(33 + 71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(20, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(33 + 30, spheroid.first());
        Assertions.assertEquals(33 + 72, spheroid.last());
        Assertions.assertEquals(22, spheroid.order());

        final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(28, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getLongitude().last());
        Assertions.assertEquals(32, primeMeridian.getLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(34, primeMeridian.order());

        final Unit angular = geogcs.getAngularUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, angular.getName().first());
        Assertions.assertEquals(143, angular.getName().last());
        Assertions.assertEquals(38, angular.getName().order());

        Assertions.assertEquals(145, angular.getConversionFactor().first());
        Assertions.assertEquals(162, angular.getConversionFactor().last());
        Assertions.assertEquals(44, angular.getConversionFactor().order());

        Assertions.assertEquals(131, angular.first());
        Assertions.assertEquals(163, angular.last());
        Assertions.assertEquals(46, angular.order());

        final Unit linear = geogcs.getLinearUnit();

        Assertions.assertEquals("Meter", linear.getName().getSemantics());
        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(170, linear.getName().first());
        Assertions.assertEquals(176, linear.getName().last());
        Assertions.assertEquals(50, linear.getName().order());

        Assertions.assertEquals(178, linear.getConversionFactor().first());
        Assertions.assertEquals(180, linear.getConversionFactor().last());
        Assertions.assertEquals(56, linear.getConversionFactor().order());

        Assertions.assertEquals(165, linear.first());
        Assertions.assertEquals(181, linear.last());
        Assertions.assertEquals(58, linear.order());
    }

    @Test
    public void projcs_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJCS["NAD_1983_UTM_Zone_10N",\
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],\
                                              UNIT["Degree",0.0174532925199433],UNIT["Meter",1.0]],\
                                              PROJECTION["Transverse_Mercator"],\
                                              PARAMETER["False_Easting",500000.0],\
                                              PARAMETER["False_Northing",0.0],\
                                              PARAMETER["Central_Meridian",-123.0],\
                                              PARAMETER["Scale_Factor",0.9996],\
                                              PARAMETER["Latitude_of_Origin",0.0],\
                                              UNIT["Meter",1.0]]""");

        final ProjectedCs projcs = parser.projcs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(440, projcs.last());
        Assertions.assertEquals(145, projcs.order());

        final GeographicCs geogcs = projcs.getGeographicCs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(38, geogcs.getName().first());
        Assertions.assertEquals(62, geogcs.getName().last());
        Assertions.assertEquals(6, geogcs.getName().order());

        Assertions.assertEquals(31, geogcs.first());
        Assertions.assertEquals(213, geogcs.last());
        Assertions.assertEquals(64, geogcs.order());

        final Datum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(70, datum.getName().first());
        Assertions.assertEquals(92, datum.getName().last());
        Assertions.assertEquals(10, datum.getName().order());

        Assertions.assertEquals(64, datum.first());
        Assertions.assertEquals(137, datum.last());
        Assertions.assertEquals(28, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(104, spheroid.getName().first());
        Assertions.assertEquals(113, spheroid.getName().last());
        Assertions.assertEquals(14, spheroid.getName().order());

        Assertions.assertEquals(115, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(121, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(18, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(123, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(135, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(24, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(94, spheroid.first());
        Assertions.assertEquals(136, spheroid.last());
        Assertions.assertEquals(26, spheroid.order());

        final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(146, primeMeridian.getName().first());
        Assertions.assertEquals(157, primeMeridian.getName().last());
        Assertions.assertEquals(32, primeMeridian.getName().order());

        Assertions.assertEquals(159, primeMeridian.getLongitude().first());
        Assertions.assertEquals(159, primeMeridian.getLongitude().last());
        Assertions.assertEquals(36, primeMeridian.getLongitude().order());

        Assertions.assertEquals(139, primeMeridian.first());
        Assertions.assertEquals(160, primeMeridian.last());
        Assertions.assertEquals(38, primeMeridian.order());

        final Unit angular = geogcs.getAngularUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(167, angular.getName().first());
        Assertions.assertEquals(174, angular.getName().last());
        Assertions.assertEquals(42, angular.getName().order());

        Assertions.assertEquals(176, angular.getConversionFactor().first());
        Assertions.assertEquals(193, angular.getConversionFactor().last());
        Assertions.assertEquals(48, angular.getConversionFactor().order());

        Assertions.assertEquals(162, angular.first());
        Assertions.assertEquals(194, angular.last());
        Assertions.assertEquals(50, angular.order());

        final Unit linear = geogcs.getLinearUnit();

        Assertions.assertEquals("Meter", linear.getName().getSemantics());
        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(201, linear.getName().first());
        Assertions.assertEquals(207, linear.getName().last());
        Assertions.assertEquals(54, linear.getName().order());

        Assertions.assertEquals(209, linear.getConversionFactor().first());
        Assertions.assertEquals(211, linear.getConversionFactor().last());
        Assertions.assertEquals(60, linear.getConversionFactor().order());

        Assertions.assertEquals(196, linear.first());
        Assertions.assertEquals(212, linear.last());
        Assertions.assertEquals(62, linear.order());

        final Projection projection = projcs.getProjection();

        Assertions.assertEquals("Transverse_Mercator", projection.getName().getSemantics());

        Assertions.assertEquals(226, projection.getName().first());
        Assertions.assertEquals(246, projection.getName().last());
        Assertions.assertEquals(68, projection.getName().order());

        Assertions.assertEquals(215, projection.first());
        Assertions.assertEquals(247, projection.last());
        Assertions.assertEquals(70, projection.order());

        final List<Parameter> parameters = projcs.getParameters();

        Assertions.assertEquals(5, parameters.size());

        final Parameter param0 = parameters.get(0);

        Assertions.assertEquals("False_Easting", param0.getName().getSemantics());
        Assertions.assertEquals(500000., param0.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(259, param0.getName().first());
        Assertions.assertEquals(273, param0.getName().last());
        Assertions.assertEquals(74, param0.getName().order());

        Assertions.assertEquals(275, param0.getValue().first());
        Assertions.assertEquals(282, param0.getValue().last());
        Assertions.assertEquals(80, param0.getValue().order());

        Assertions.assertEquals(249, param0.first());
        Assertions.assertEquals(283, param0.last());
        Assertions.assertEquals(82, param0.order());

        final Parameter param1 = parameters.get(1);

        Assertions.assertEquals("False_Northing", param1.getName().getSemantics());
        Assertions.assertEquals(0., param1.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(295, param1.getName().first());
        Assertions.assertEquals(310, param1.getName().last());
        Assertions.assertEquals(86, param1.getName().order());

        Assertions.assertEquals(312, param1.getValue().first());
        Assertions.assertEquals(314, param1.getValue().last());
        Assertions.assertEquals(92, param1.getValue().order());

        Assertions.assertEquals(285, param1.first());
        Assertions.assertEquals(315, param1.last());
        Assertions.assertEquals(94, param1.order());

        final Parameter param2 = parameters.get(2);

        Assertions.assertEquals("Central_Meridian", param2.getName().getSemantics());
        Assertions.assertEquals(-123., param2.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(327, param2.getName().first());
        Assertions.assertEquals(344, param2.getName().last());
        Assertions.assertEquals(98, param2.getName().order());

        Assertions.assertEquals(346, param2.getValue().first());
        Assertions.assertEquals(351, param2.getValue().last());
        Assertions.assertEquals(105, param2.getValue().order());

        Assertions.assertEquals(317, param2.first());
        Assertions.assertEquals(352, param2.last());
        Assertions.assertEquals(107, param2.order());

        final Parameter param3 = parameters.get(3);

        Assertions.assertEquals("Scale_Factor", param3.getName().getSemantics());
        Assertions.assertEquals(.9996, param3.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(364, param3.getName().first());
        Assertions.assertEquals(377, param3.getName().last());
        Assertions.assertEquals(111, param3.getName().order());

        Assertions.assertEquals(379, param3.getValue().first());
        Assertions.assertEquals(384, param3.getValue().last());
        Assertions.assertEquals(117, param3.getValue().order());

        Assertions.assertEquals(354, param3.first());
        Assertions.assertEquals(385, param3.last());
        Assertions.assertEquals(119, param3.order());

        final Parameter param4 = parameters.get(4);

        Assertions.assertEquals("Latitude_of_Origin", param4.getName().getSemantics());
        Assertions.assertEquals(0., param4.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(397, param4.getName().first());
        Assertions.assertEquals(416, param4.getName().last());
        Assertions.assertEquals(123, param4.getName().order());

        Assertions.assertEquals(418, param4.getValue().first());
        Assertions.assertEquals(420, param4.getValue().last());
        Assertions.assertEquals(129, param4.getValue().order());

        Assertions.assertEquals(387, param4.first());
        Assertions.assertEquals(421, param4.last());
        Assertions.assertEquals(131, param4.order());

        final Unit unit = projcs.getLinearUnit();

        Assertions.assertEquals("Meter", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(428, unit.getName().first());
        Assertions.assertEquals(434, unit.getName().last());
        Assertions.assertEquals(135, unit.getName().order());

        Assertions.assertEquals(436, unit.getConversionFactor().first());
        Assertions.assertEquals(438, unit.getConversionFactor().last());
        Assertions.assertEquals(141, unit.getConversionFactor().order());

        Assertions.assertEquals(423, unit.first());
        Assertions.assertEquals(439, unit.last());
        Assertions.assertEquals(143, unit.order());
    }

    @Test
    public void spatial_reference_system_test_1_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],UNIT["Degree",0.0174532925199433]]""");

        final GeocentricCs geoccs = (GeocentricCs) parser.coordinateSystem();

        Assertions.assertEquals("GCS_North_American_1983", geoccs.getName().getSemantics());

        Assertions.assertEquals(7, geoccs.getName().first());
        Assertions.assertEquals(31, geoccs.getName().last());
        Assertions.assertEquals(2, geoccs.getName().order());

        Assertions.assertEquals(0, geoccs.first());
        Assertions.assertEquals(164, geoccs.last());
        Assertions.assertEquals(48, geoccs.order());

        final Datum datum = geoccs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(33 + 73, datum.last());
        Assertions.assertEquals(24, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(33 + 40, spheroid.getName().first());
        Assertions.assertEquals(33 + 49, spheroid.getName().last());
        Assertions.assertEquals(10, spheroid.getName().order());

        Assertions.assertEquals(33 + 51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(33 + 57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(14, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(33 + 59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(33 + 71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(20, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(33 + 30, spheroid.first());
        Assertions.assertEquals(33 + 72, spheroid.last());
        Assertions.assertEquals(22, spheroid.order());

        final PrimeMeridian primeMeridian = geoccs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(28, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getLongitude().last());
        Assertions.assertEquals(32, primeMeridian.getLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(34, primeMeridian.order());

        final Unit unit = geoccs.getLinearUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(44, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(46, unit.order());
    }

    @Test
    public void spatial_reference_system_test_1_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],UNIT["Degree",0.0174532925199433]]""");

        final GeographicCs geogcs = (GeographicCs) parser.coordinateSystem();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(164, geogcs.last());
        Assertions.assertEquals(48, geogcs.order());

        final Datum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(33 + 73, datum.last());
        Assertions.assertEquals(24, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(33 + 40, spheroid.getName().first());
        Assertions.assertEquals(33 + 49, spheroid.getName().last());
        Assertions.assertEquals(10, spheroid.getName().order());

        Assertions.assertEquals(33 + 51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(33 + 57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(14, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(33 + 59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(33 + 71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(20, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(33 + 30, spheroid.first());
        Assertions.assertEquals(33 + 72, spheroid.last());
        Assertions.assertEquals(22, spheroid.order());

        final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(28, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getLongitude().last());
        Assertions.assertEquals(32, primeMeridian.getLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(34, primeMeridian.order());

        final Unit unit = geogcs.getAngularUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(44, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(46, unit.order());

        Assertions.assertNull(geogcs.getLinearUnit());
    }

    @Test
    public void spatial_reference_system_test_1_3() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],\
                                              UNIT["Degree",0.0174532925199433],UNIT["Meter",1.0]]""");

        final GeographicCs geogcs = (GeographicCs) parser.coordinateSystem();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(182, geogcs.last());
        Assertions.assertEquals(60, geogcs.order());

        final Datum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(33 + 73, datum.last());
        Assertions.assertEquals(24, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(33 + 40, spheroid.getName().first());
        Assertions.assertEquals(33 + 49, spheroid.getName().last());
        Assertions.assertEquals(10, spheroid.getName().order());

        Assertions.assertEquals(33 + 51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(33 + 57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(14, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(33 + 59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(33 + 71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(20, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(33 + 30, spheroid.first());
        Assertions.assertEquals(33 + 72, spheroid.last());
        Assertions.assertEquals(22, spheroid.order());

        final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(28, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getLongitude().last());
        Assertions.assertEquals(32, primeMeridian.getLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(34, primeMeridian.order());

        final Unit angular = geogcs.getAngularUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, angular.getName().first());
        Assertions.assertEquals(143, angular.getName().last());
        Assertions.assertEquals(38, angular.getName().order());

        Assertions.assertEquals(145, angular.getConversionFactor().first());
        Assertions.assertEquals(162, angular.getConversionFactor().last());
        Assertions.assertEquals(44, angular.getConversionFactor().order());

        Assertions.assertEquals(131, angular.first());
        Assertions.assertEquals(163, angular.last());
        Assertions.assertEquals(46, angular.order());

        final Unit linear = geogcs.getLinearUnit();

        Assertions.assertEquals("Meter", linear.getName().getSemantics());
        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(170, linear.getName().first());
        Assertions.assertEquals(176, linear.getName().last());
        Assertions.assertEquals(50, linear.getName().order());

        Assertions.assertEquals(178, linear.getConversionFactor().first());
        Assertions.assertEquals(180, linear.getConversionFactor().last());
        Assertions.assertEquals(56, linear.getConversionFactor().order());

        Assertions.assertEquals(165, linear.first());
        Assertions.assertEquals(181, linear.last());
        Assertions.assertEquals(58, linear.order());
    }

    @Test
    public void spatial_reference_system_test_1_4() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJCS["NAD_1983_UTM_Zone_10N",\
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],\
                                              UNIT["Degree",0.0174532925199433],UNIT["Meter",1.0]],\
                                              PROJECTION["Transverse_Mercator"],\
                                              PARAMETER["False_Easting",500000.0],\
                                              PARAMETER["False_Northing",0.0],\
                                              PARAMETER["Central_Meridian",-123.0],\
                                              PARAMETER["Scale_Factor",0.9996],\
                                              PARAMETER["Latitude_of_Origin",0.0],\
                                              UNIT["Meter",1.0]]""");

        final ProjectedCs projcs = (ProjectedCs) parser.coordinateSystem();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(440, projcs.last());
        Assertions.assertEquals(145, projcs.order());

        final GeographicCs geogcs = projcs.getGeographicCs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(38, geogcs.getName().first());
        Assertions.assertEquals(62, geogcs.getName().last());
        Assertions.assertEquals(6, geogcs.getName().order());

        Assertions.assertEquals(31, geogcs.first());
        Assertions.assertEquals(213, geogcs.last());
        Assertions.assertEquals(64, geogcs.order());

        final Datum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(70, datum.getName().first());
        Assertions.assertEquals(92, datum.getName().last());
        Assertions.assertEquals(10, datum.getName().order());

        Assertions.assertEquals(64, datum.first());
        Assertions.assertEquals(137, datum.last());
        Assertions.assertEquals(28, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(104, spheroid.getName().first());
        Assertions.assertEquals(113, spheroid.getName().last());
        Assertions.assertEquals(14, spheroid.getName().order());

        Assertions.assertEquals(115, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(121, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(18, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(123, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(135, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(24, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(94, spheroid.first());
        Assertions.assertEquals(136, spheroid.last());
        Assertions.assertEquals(26, spheroid.order());

        final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(146, primeMeridian.getName().first());
        Assertions.assertEquals(157, primeMeridian.getName().last());
        Assertions.assertEquals(32, primeMeridian.getName().order());

        Assertions.assertEquals(159, primeMeridian.getLongitude().first());
        Assertions.assertEquals(159, primeMeridian.getLongitude().last());
        Assertions.assertEquals(36, primeMeridian.getLongitude().order());

        Assertions.assertEquals(139, primeMeridian.first());
        Assertions.assertEquals(160, primeMeridian.last());
        Assertions.assertEquals(38, primeMeridian.order());

        final Unit angular = geogcs.getAngularUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(167, angular.getName().first());
        Assertions.assertEquals(174, angular.getName().last());
        Assertions.assertEquals(42, angular.getName().order());

        Assertions.assertEquals(176, angular.getConversionFactor().first());
        Assertions.assertEquals(193, angular.getConversionFactor().last());
        Assertions.assertEquals(48, angular.getConversionFactor().order());

        Assertions.assertEquals(162, angular.first());
        Assertions.assertEquals(194, angular.last());
        Assertions.assertEquals(50, angular.order());

        final Unit linear = geogcs.getLinearUnit();

        Assertions.assertEquals("Meter", linear.getName().getSemantics());
        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(201, linear.getName().first());
        Assertions.assertEquals(207, linear.getName().last());
        Assertions.assertEquals(54, linear.getName().order());

        Assertions.assertEquals(209, linear.getConversionFactor().first());
        Assertions.assertEquals(211, linear.getConversionFactor().last());
        Assertions.assertEquals(60, linear.getConversionFactor().order());

        Assertions.assertEquals(196, linear.first());
        Assertions.assertEquals(212, linear.last());
        Assertions.assertEquals(62, linear.order());

        final Projection projection = projcs.getProjection();

        Assertions.assertEquals("Transverse_Mercator", projection.getName().getSemantics());

        Assertions.assertEquals(226, projection.getName().first());
        Assertions.assertEquals(246, projection.getName().last());
        Assertions.assertEquals(68, projection.getName().order());

        Assertions.assertEquals(215, projection.first());
        Assertions.assertEquals(247, projection.last());
        Assertions.assertEquals(70, projection.order());

        final List<Parameter> parameters = projcs.getParameters();

        Assertions.assertEquals(5, parameters.size());

        final Parameter param0 = parameters.get(0);

        Assertions.assertEquals("False_Easting", param0.getName().getSemantics());
        Assertions.assertEquals(500000., param0.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(259, param0.getName().first());
        Assertions.assertEquals(273, param0.getName().last());
        Assertions.assertEquals(74, param0.getName().order());

        Assertions.assertEquals(275, param0.getValue().first());
        Assertions.assertEquals(282, param0.getValue().last());
        Assertions.assertEquals(80, param0.getValue().order());

        Assertions.assertEquals(249, param0.first());
        Assertions.assertEquals(283, param0.last());
        Assertions.assertEquals(82, param0.order());

        final Parameter param1 = parameters.get(1);

        Assertions.assertEquals("False_Northing", param1.getName().getSemantics());
        Assertions.assertEquals(0., param1.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(295, param1.getName().first());
        Assertions.assertEquals(310, param1.getName().last());
        Assertions.assertEquals(86, param1.getName().order());

        Assertions.assertEquals(312, param1.getValue().first());
        Assertions.assertEquals(314, param1.getValue().last());
        Assertions.assertEquals(92, param1.getValue().order());

        Assertions.assertEquals(285, param1.first());
        Assertions.assertEquals(315, param1.last());
        Assertions.assertEquals(94, param1.order());

        final Parameter param2 = parameters.get(2);

        Assertions.assertEquals("Central_Meridian", param2.getName().getSemantics());
        Assertions.assertEquals(-123., param2.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(327, param2.getName().first());
        Assertions.assertEquals(344, param2.getName().last());
        Assertions.assertEquals(98, param2.getName().order());

        Assertions.assertEquals(346, param2.getValue().first());
        Assertions.assertEquals(351, param2.getValue().last());
        Assertions.assertEquals(105, param2.getValue().order());

        Assertions.assertEquals(317, param2.first());
        Assertions.assertEquals(352, param2.last());
        Assertions.assertEquals(107, param2.order());

        final Parameter param3 = parameters.get(3);

        Assertions.assertEquals("Scale_Factor", param3.getName().getSemantics());
        Assertions.assertEquals(.9996, param3.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(364, param3.getName().first());
        Assertions.assertEquals(377, param3.getName().last());
        Assertions.assertEquals(111, param3.getName().order());

        Assertions.assertEquals(379, param3.getValue().first());
        Assertions.assertEquals(384, param3.getValue().last());
        Assertions.assertEquals(117, param3.getValue().order());

        Assertions.assertEquals(354, param3.first());
        Assertions.assertEquals(385, param3.last());
        Assertions.assertEquals(119, param3.order());

        final Parameter param4 = parameters.get(4);

        Assertions.assertEquals("Latitude_of_Origin", param4.getName().getSemantics());
        Assertions.assertEquals(0., param4.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(397, param4.getName().first());
        Assertions.assertEquals(416, param4.getName().last());
        Assertions.assertEquals(123, param4.getName().order());

        Assertions.assertEquals(418, param4.getValue().first());
        Assertions.assertEquals(420, param4.getValue().last());
        Assertions.assertEquals(129, param4.getValue().order());

        Assertions.assertEquals(387, param4.first());
        Assertions.assertEquals(421, param4.last());
        Assertions.assertEquals(131, param4.order());

        final Unit unit = projcs.getLinearUnit();

        Assertions.assertEquals("Meter", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(428, unit.getName().first());
        Assertions.assertEquals(434, unit.getName().last());
        Assertions.assertEquals(135, unit.getName().order());

        Assertions.assertEquals(436, unit.getConversionFactor().first());
        Assertions.assertEquals(438, unit.getConversionFactor().last());
        Assertions.assertEquals(141, unit.getConversionFactor().order());

        Assertions.assertEquals(423, unit.first());
        Assertions.assertEquals(439, unit.last());
        Assertions.assertEquals(143, unit.order());
    }

    @Test
    public void unit_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("UNIT(\"Degree\",0.0174532925199433)", '(', ')');

        final Unit unit = parser.unit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(5, unit.getName().first());
        Assertions.assertEquals(12, unit.getName().last());
        Assertions.assertEquals(2, unit.getName().order());

        Assertions.assertEquals(14, unit.getConversionFactor().first());
        Assertions.assertEquals(31, unit.getConversionFactor().last());
        Assertions.assertEquals(8, unit.getConversionFactor().order());

        Assertions.assertEquals(0, unit.first());
        Assertions.assertEquals(32, unit.last());
        Assertions.assertEquals(10, unit.order());
    }

    @Test
    public void prime_meridian_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("PRIMEM(\"Greenwitch\",0)", '(', ')');

        final PrimeMeridian primeMeridian = parser.primeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(7, primeMeridian.getName().first());
        Assertions.assertEquals(18, primeMeridian.getName().last());
        Assertions.assertEquals(2, primeMeridian.getName().order());

        Assertions.assertEquals(20, primeMeridian.getLongitude().first());
        Assertions.assertEquals(20, primeMeridian.getLongitude().last());
        Assertions.assertEquals(6, primeMeridian.getLongitude().order());

        Assertions.assertEquals(0, primeMeridian.first());
        Assertions.assertEquals(21, primeMeridian.last());
        Assertions.assertEquals(8, primeMeridian.order());
    }

    @Test
    public void parameter_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("PARAMETER(\"False_Easting\",500000.0)", '(', ')');

        final Parameter primeMeridian = parser.parameter();

        Assertions.assertEquals("False_Easting", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(500000, primeMeridian.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(10, primeMeridian.getName().first());
        Assertions.assertEquals(24, primeMeridian.getName().last());
        Assertions.assertEquals(2, primeMeridian.getName().order());

        Assertions.assertEquals(26, primeMeridian.getValue().first());
        Assertions.assertEquals(33, primeMeridian.getValue().last());
        Assertions.assertEquals(8, primeMeridian.getValue().order());

        Assertions.assertEquals(0, primeMeridian.first());
        Assertions.assertEquals(34, primeMeridian.last());
        Assertions.assertEquals(10, primeMeridian.order());
    }

    @Test
    public void projection_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("PROJECTION(\"Transverse_Mercator\")", '(', ')');

        final Projection projection = parser.projection();

        Assertions.assertEquals("Transverse_Mercator", projection.getName().getSemantics());

        Assertions.assertEquals(11, projection.getName().first());
        Assertions.assertEquals(31, projection.getName().last());
        Assertions.assertEquals(2, projection.getName().order());

        Assertions.assertEquals(0, projection.first());
        Assertions.assertEquals(32, projection.last());
        Assertions.assertEquals(4, projection.order());
    }

    @Test
    public void spheroid_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("SPHEROID(\"GRS_1980\",6378137,298.257222101)", '(', ')');

        final Spheroid spheroid = parser.spheroid();

        Assertions.assertEquals(WktName.SPHEROID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(9, spheroid.getName().first());
        Assertions.assertEquals(18, spheroid.getName().last());
        Assertions.assertEquals(2, spheroid.getName().order());

        Assertions.assertEquals(20, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(26, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(6, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(28, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(40, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(12, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(0, spheroid.first());
        Assertions.assertEquals(41, spheroid.last());
        Assertions.assertEquals(14, spheroid.order());
    }

    @Test
    public void ellipsoid_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("ELLIPSOID(\"GRS_1980\",6378137,298.257222101)", '(', ')');

        final Spheroid spheroid = parser.spheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(10, spheroid.getName().first());
        Assertions.assertEquals(19, spheroid.getName().last());
        Assertions.assertEquals(2, spheroid.getName().order());

        Assertions.assertEquals(21, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(27, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(6, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(29, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(41, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(12, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(0, spheroid.first());
        Assertions.assertEquals(42, spheroid.last());
        Assertions.assertEquals(14, spheroid.order());
    }

    @Test
    public void datum_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101))""", '(', ')');

        final Datum datum = parser.datum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(6, datum.getName().first());
        Assertions.assertEquals(28, datum.getName().last());
        Assertions.assertEquals(2, datum.getName().order());

        Assertions.assertEquals(0, datum.first());
        Assertions.assertEquals(73, datum.last());
        Assertions.assertEquals(20, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(40, spheroid.getName().first());
        Assertions.assertEquals(49, spheroid.getName().last());
        Assertions.assertEquals(6, spheroid.getName().order());

        Assertions.assertEquals(51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(10, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(16, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(30, spheroid.first());
        Assertions.assertEquals(72, spheroid.last());
        Assertions.assertEquals(18, spheroid.order());
    }

    @Test
    public void geoccs_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),UNIT("Degree",0.0174532925199433))""", '(', ')');

        final GeocentricCs geoccs = parser.geoccs();

        Assertions.assertEquals("GCS_North_American_1983", geoccs.getName().getSemantics());

        Assertions.assertEquals(7, geoccs.getName().first());
        Assertions.assertEquals(31, geoccs.getName().last());
        Assertions.assertEquals(2, geoccs.getName().order());

        Assertions.assertEquals(0, geoccs.first());
        Assertions.assertEquals(164, geoccs.last());
        Assertions.assertEquals(48, geoccs.order());

        final Datum datum = geoccs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(33 + 73, datum.last());
        Assertions.assertEquals(24, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(33 + 40, spheroid.getName().first());
        Assertions.assertEquals(33 + 49, spheroid.getName().last());
        Assertions.assertEquals(10, spheroid.getName().order());

        Assertions.assertEquals(33 + 51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(33 + 57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(14, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(33 + 59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(33 + 71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(20, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(33 + 30, spheroid.first());
        Assertions.assertEquals(33 + 72, spheroid.last());
        Assertions.assertEquals(22, spheroid.order());

        final PrimeMeridian primeMeridian = geoccs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(28, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getLongitude().last());
        Assertions.assertEquals(32, primeMeridian.getLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(34, primeMeridian.order());

        final Unit unit = geoccs.getLinearUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(44, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(46, unit.order());
    }

    @Test
    public void geogcs_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree",0.0174532925199433))""", '(', ')');

        final GeographicCs geogcs = parser.geogcs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(164, geogcs.last());
        Assertions.assertEquals(48, geogcs.order());

        final Datum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(33 + 73, datum.last());
        Assertions.assertEquals(24, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(33 + 40, spheroid.getName().first());
        Assertions.assertEquals(33 + 49, spheroid.getName().last());
        Assertions.assertEquals(10, spheroid.getName().order());

        Assertions.assertEquals(33 + 51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(33 + 57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(14, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(33 + 59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(33 + 71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(20, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(33 + 30, spheroid.first());
        Assertions.assertEquals(33 + 72, spheroid.last());
        Assertions.assertEquals(22, spheroid.order());

        final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(28, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getLongitude().last());
        Assertions.assertEquals(32, primeMeridian.getLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(34, primeMeridian.order());

        final Unit unit = geogcs.getAngularUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(44, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(46, unit.order());

        Assertions.assertNull(geogcs.getLinearUnit());
    }

    @Test
    public void geogcs_3d_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree",0.0174532925199433),UNIT("Meter",1.0))""", '(', ')');

        final GeographicCs geogcs = parser.geogcs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(182, geogcs.last());
        Assertions.assertEquals(60, geogcs.order());

        final Datum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(33 + 73, datum.last());
        Assertions.assertEquals(24, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(33 + 40, spheroid.getName().first());
        Assertions.assertEquals(33 + 49, spheroid.getName().last());
        Assertions.assertEquals(10, spheroid.getName().order());

        Assertions.assertEquals(33 + 51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(33 + 57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(14, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(33 + 59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(33 + 71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(20, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(33 + 30, spheroid.first());
        Assertions.assertEquals(33 + 72, spheroid.last());
        Assertions.assertEquals(22, spheroid.order());

        final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(28, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getLongitude().last());
        Assertions.assertEquals(32, primeMeridian.getLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(34, primeMeridian.order());

        final Unit angular = geogcs.getAngularUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, angular.getName().first());
        Assertions.assertEquals(143, angular.getName().last());
        Assertions.assertEquals(38, angular.getName().order());

        Assertions.assertEquals(145, angular.getConversionFactor().first());
        Assertions.assertEquals(162, angular.getConversionFactor().last());
        Assertions.assertEquals(44, angular.getConversionFactor().order());

        Assertions.assertEquals(131, angular.first());
        Assertions.assertEquals(163, angular.last());
        Assertions.assertEquals(46, angular.order());

        final Unit linear = geogcs.getLinearUnit();

        Assertions.assertEquals("Meter", linear.getName().getSemantics());
        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(170, linear.getName().first());
        Assertions.assertEquals(176, linear.getName().last());
        Assertions.assertEquals(50, linear.getName().order());

        Assertions.assertEquals(178, linear.getConversionFactor().first());
        Assertions.assertEquals(180, linear.getConversionFactor().last());
        Assertions.assertEquals(56, linear.getConversionFactor().order());

        Assertions.assertEquals(165, linear.first());
        Assertions.assertEquals(181, linear.last());
        Assertions.assertEquals(58, linear.order());
    }

    @Test
    public void projcs_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJCS("NAD_1983_UTM_Zone_10N",\
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree",0.0174532925199433),UNIT("Meter",1.0)),\
                                              PROJECTION("Transverse_Mercator"),\
                                              PARAMETER("False_Easting",500000.0),\
                                              PARAMETER("False_Northing",0.0),\
                                              PARAMETER("Central_Meridian",-123.0),\
                                              PARAMETER("Scale_Factor",0.9996),\
                                              PARAMETER("Latitude_of_Origin",0.0),\
                                              UNIT("Meter",1.0))""", '(', ')');

        final ProjectedCs projcs = parser.projcs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(440, projcs.last());
        Assertions.assertEquals(145, projcs.order());

        final GeographicCs geogcs = projcs.getGeographicCs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(38, geogcs.getName().first());
        Assertions.assertEquals(62, geogcs.getName().last());
        Assertions.assertEquals(6, geogcs.getName().order());

        Assertions.assertEquals(31, geogcs.first());
        Assertions.assertEquals(213, geogcs.last());
        Assertions.assertEquals(64, geogcs.order());

        final Datum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(70, datum.getName().first());
        Assertions.assertEquals(92, datum.getName().last());
        Assertions.assertEquals(10, datum.getName().order());

        Assertions.assertEquals(64, datum.first());
        Assertions.assertEquals(137, datum.last());
        Assertions.assertEquals(28, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(104, spheroid.getName().first());
        Assertions.assertEquals(113, spheroid.getName().last());
        Assertions.assertEquals(14, spheroid.getName().order());

        Assertions.assertEquals(115, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(121, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(18, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(123, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(135, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(24, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(94, spheroid.first());
        Assertions.assertEquals(136, spheroid.last());
        Assertions.assertEquals(26, spheroid.order());

        final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(146, primeMeridian.getName().first());
        Assertions.assertEquals(157, primeMeridian.getName().last());
        Assertions.assertEquals(32, primeMeridian.getName().order());

        Assertions.assertEquals(159, primeMeridian.getLongitude().first());
        Assertions.assertEquals(159, primeMeridian.getLongitude().last());
        Assertions.assertEquals(36, primeMeridian.getLongitude().order());

        Assertions.assertEquals(139, primeMeridian.first());
        Assertions.assertEquals(160, primeMeridian.last());
        Assertions.assertEquals(38, primeMeridian.order());

        final Unit angular = geogcs.getAngularUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(167, angular.getName().first());
        Assertions.assertEquals(174, angular.getName().last());
        Assertions.assertEquals(42, angular.getName().order());

        Assertions.assertEquals(176, angular.getConversionFactor().first());
        Assertions.assertEquals(193, angular.getConversionFactor().last());
        Assertions.assertEquals(48, angular.getConversionFactor().order());

        Assertions.assertEquals(162, angular.first());
        Assertions.assertEquals(194, angular.last());
        Assertions.assertEquals(50, angular.order());

        final Unit linear = geogcs.getLinearUnit();

        Assertions.assertEquals("Meter", linear.getName().getSemantics());
        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(201, linear.getName().first());
        Assertions.assertEquals(207, linear.getName().last());
        Assertions.assertEquals(54, linear.getName().order());

        Assertions.assertEquals(209, linear.getConversionFactor().first());
        Assertions.assertEquals(211, linear.getConversionFactor().last());
        Assertions.assertEquals(60, linear.getConversionFactor().order());

        Assertions.assertEquals(196, linear.first());
        Assertions.assertEquals(212, linear.last());
        Assertions.assertEquals(62, linear.order());

        final Projection projection = projcs.getProjection();

        Assertions.assertEquals("Transverse_Mercator", projection.getName().getSemantics());

        Assertions.assertEquals(226, projection.getName().first());
        Assertions.assertEquals(246, projection.getName().last());
        Assertions.assertEquals(68, projection.getName().order());

        Assertions.assertEquals(215, projection.first());
        Assertions.assertEquals(247, projection.last());
        Assertions.assertEquals(70, projection.order());

        final List<Parameter> parameters = projcs.getParameters();

        Assertions.assertEquals(5, parameters.size());

        final Parameter param0 = parameters.get(0);

        Assertions.assertEquals("False_Easting", param0.getName().getSemantics());
        Assertions.assertEquals(500000., param0.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(259, param0.getName().first());
        Assertions.assertEquals(273, param0.getName().last());
        Assertions.assertEquals(74, param0.getName().order());

        Assertions.assertEquals(275, param0.getValue().first());
        Assertions.assertEquals(282, param0.getValue().last());
        Assertions.assertEquals(80, param0.getValue().order());

        Assertions.assertEquals(249, param0.first());
        Assertions.assertEquals(283, param0.last());
        Assertions.assertEquals(82, param0.order());

        final Parameter param1 = parameters.get(1);

        Assertions.assertEquals("False_Northing", param1.getName().getSemantics());
        Assertions.assertEquals(0., param1.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(295, param1.getName().first());
        Assertions.assertEquals(310, param1.getName().last());
        Assertions.assertEquals(86, param1.getName().order());

        Assertions.assertEquals(312, param1.getValue().first());
        Assertions.assertEquals(314, param1.getValue().last());
        Assertions.assertEquals(92, param1.getValue().order());

        Assertions.assertEquals(285, param1.first());
        Assertions.assertEquals(315, param1.last());
        Assertions.assertEquals(94, param1.order());

        final Parameter param2 = parameters.get(2);

        Assertions.assertEquals("Central_Meridian", param2.getName().getSemantics());
        Assertions.assertEquals(-123., param2.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(327, param2.getName().first());
        Assertions.assertEquals(344, param2.getName().last());
        Assertions.assertEquals(98, param2.getName().order());

        Assertions.assertEquals(346, param2.getValue().first());
        Assertions.assertEquals(351, param2.getValue().last());
        Assertions.assertEquals(105, param2.getValue().order());

        Assertions.assertEquals(317, param2.first());
        Assertions.assertEquals(352, param2.last());
        Assertions.assertEquals(107, param2.order());

        final Parameter param3 = parameters.get(3);

        Assertions.assertEquals("Scale_Factor", param3.getName().getSemantics());
        Assertions.assertEquals(.9996, param3.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(364, param3.getName().first());
        Assertions.assertEquals(377, param3.getName().last());
        Assertions.assertEquals(111, param3.getName().order());

        Assertions.assertEquals(379, param3.getValue().first());
        Assertions.assertEquals(384, param3.getValue().last());
        Assertions.assertEquals(117, param3.getValue().order());

        Assertions.assertEquals(354, param3.first());
        Assertions.assertEquals(385, param3.last());
        Assertions.assertEquals(119, param3.order());

        final Parameter param4 = parameters.get(4);

        Assertions.assertEquals("Latitude_of_Origin", param4.getName().getSemantics());
        Assertions.assertEquals(0., param4.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(397, param4.getName().first());
        Assertions.assertEquals(416, param4.getName().last());
        Assertions.assertEquals(123, param4.getName().order());

        Assertions.assertEquals(418, param4.getValue().first());
        Assertions.assertEquals(420, param4.getValue().last());
        Assertions.assertEquals(129, param4.getValue().order());

        Assertions.assertEquals(387, param4.first());
        Assertions.assertEquals(421, param4.last());
        Assertions.assertEquals(131, param4.order());

        final Unit unit = projcs.getLinearUnit();

        Assertions.assertEquals("Meter", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(428, unit.getName().first());
        Assertions.assertEquals(434, unit.getName().last());
        Assertions.assertEquals(135, unit.getName().order());

        Assertions.assertEquals(436, unit.getConversionFactor().first());
        Assertions.assertEquals(438, unit.getConversionFactor().last());
        Assertions.assertEquals(141, unit.getConversionFactor().order());

        Assertions.assertEquals(423, unit.first());
        Assertions.assertEquals(439, unit.last());
        Assertions.assertEquals(143, unit.order());
    }

    @Test // fichier avec des crochets comme délimiteurs
    public void projcs_test_3a() throws LanguageException, IOException {

        try (var scanner = new DefaultStreamScanner(new BufferedReader(new InputStreamReader(
                WktParserTest.class.getResourceAsStream("example3a"))))) {

            final WktLexer lexer = new WktLexer(scanner, new ArrayList<>(), LD, RD);
            lexer.initialize();
            final WktParser parser = new WktParser(lexer);

            final ProjectedCs projcs = parser.projcs();

            Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

            Assertions.assertEquals(7, projcs.getName().first());
            Assertions.assertEquals(29, projcs.getName().last());
            Assertions.assertEquals(2, projcs.getName().order());

            Assertions.assertEquals(0, projcs.first());
            Assertions.assertEquals(440, projcs.last());
            Assertions.assertEquals(145, projcs.order());

            final GeographicCs geogcs = projcs.getGeographicCs();

            Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

            Assertions.assertEquals(38, geogcs.getName().first());
            Assertions.assertEquals(62, geogcs.getName().last());
            Assertions.assertEquals(6, geogcs.getName().order());

            Assertions.assertEquals(31, geogcs.first());
            Assertions.assertEquals(213, geogcs.last());
            Assertions.assertEquals(64, geogcs.order());

            final Datum datum = geogcs.getDatum();

            Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

            Assertions.assertEquals(70, datum.getName().first());
            Assertions.assertEquals(92, datum.getName().last());
            Assertions.assertEquals(10, datum.getName().order());

            Assertions.assertEquals(64, datum.first());
            Assertions.assertEquals(137, datum.last());
            Assertions.assertEquals(28, datum.order());

            final Spheroid spheroid = datum.getSpheroid();

            Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
            Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
            Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
            Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

            Assertions.assertEquals(104, spheroid.getName().first());
            Assertions.assertEquals(113, spheroid.getName().last());
            Assertions.assertEquals(14, spheroid.getName().order());

            Assertions.assertEquals(115, spheroid.getSemiMajorAxis().first());
            Assertions.assertEquals(121, spheroid.getSemiMajorAxis().last());
            Assertions.assertEquals(18, spheroid.getSemiMajorAxis().order());

            Assertions.assertEquals(123, spheroid.getInverseFlattening().first());
            Assertions.assertEquals(135, spheroid.getInverseFlattening().last());
            Assertions.assertEquals(24, spheroid.getInverseFlattening().order());

            Assertions.assertEquals(94, spheroid.first());
            Assertions.assertEquals(136, spheroid.last());
            Assertions.assertEquals(26, spheroid.order());

            final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

            Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
            Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

            Assertions.assertEquals(146, primeMeridian.getName().first());
            Assertions.assertEquals(157, primeMeridian.getName().last());
            Assertions.assertEquals(32, primeMeridian.getName().order());

            Assertions.assertEquals(159, primeMeridian.getLongitude().first());
            Assertions.assertEquals(159, primeMeridian.getLongitude().last());
            Assertions.assertEquals(36, primeMeridian.getLongitude().order());

            Assertions.assertEquals(139, primeMeridian.first());
            Assertions.assertEquals(160, primeMeridian.last());
            Assertions.assertEquals(38, primeMeridian.order());

            final Unit angular = geogcs.getAngularUnit();

            Assertions.assertEquals("Degree", angular.getName().getSemantics());
            Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

            Assertions.assertEquals(167, angular.getName().first());
            Assertions.assertEquals(174, angular.getName().last());
            Assertions.assertEquals(42, angular.getName().order());

            Assertions.assertEquals(176, angular.getConversionFactor().first());
            Assertions.assertEquals(193, angular.getConversionFactor().last());
            Assertions.assertEquals(48, angular.getConversionFactor().order());

            Assertions.assertEquals(162, angular.first());
            Assertions.assertEquals(194, angular.last());
            Assertions.assertEquals(50, angular.order());

            final Unit linear = geogcs.getLinearUnit();

            Assertions.assertEquals("Meter", linear.getName().getSemantics());
            Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());

            Assertions.assertEquals(201, linear.getName().first());
            Assertions.assertEquals(207, linear.getName().last());
            Assertions.assertEquals(54, linear.getName().order());

            Assertions.assertEquals(209, linear.getConversionFactor().first());
            Assertions.assertEquals(211, linear.getConversionFactor().last());
            Assertions.assertEquals(60, linear.getConversionFactor().order());

            Assertions.assertEquals(196, linear.first());
            Assertions.assertEquals(212, linear.last());
            Assertions.assertEquals(62, linear.order());

            final Projection projection = projcs.getProjection();

            Assertions.assertEquals("Transverse_Mercator", projection.getName().getSemantics());

            Assertions.assertEquals(226, projection.getName().first());
            Assertions.assertEquals(246, projection.getName().last());
            Assertions.assertEquals(68, projection.getName().order());

            Assertions.assertEquals(215, projection.first());
            Assertions.assertEquals(247, projection.last());
            Assertions.assertEquals(70, projection.order());

            final List<Parameter> parameters = projcs.getParameters();

            Assertions.assertEquals(5, parameters.size());

            final Parameter param0 = parameters.get(0);

            Assertions.assertEquals("False_Easting", param0.getName().getSemantics());
            Assertions.assertEquals(500000., param0.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(259, param0.getName().first());
            Assertions.assertEquals(273, param0.getName().last());
            Assertions.assertEquals(74, param0.getName().order());

            Assertions.assertEquals(275, param0.getValue().first());
            Assertions.assertEquals(282, param0.getValue().last());
            Assertions.assertEquals(80, param0.getValue().order());

            Assertions.assertEquals(249, param0.first());
            Assertions.assertEquals(283, param0.last());
            Assertions.assertEquals(82, param0.order());

            final Parameter param1 = parameters.get(1);

            Assertions.assertEquals("False_Northing", param1.getName().getSemantics());
            Assertions.assertEquals(0., param1.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(295, param1.getName().first());
            Assertions.assertEquals(310, param1.getName().last());
            Assertions.assertEquals(86, param1.getName().order());

            Assertions.assertEquals(312, param1.getValue().first());
            Assertions.assertEquals(314, param1.getValue().last());
            Assertions.assertEquals(92, param1.getValue().order());

            Assertions.assertEquals(285, param1.first());
            Assertions.assertEquals(315, param1.last());
            Assertions.assertEquals(94, param1.order());

            final Parameter param2 = parameters.get(2);

            Assertions.assertEquals("Central_Meridian", param2.getName().getSemantics());
            Assertions.assertEquals(-123., param2.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(327, param2.getName().first());
            Assertions.assertEquals(344, param2.getName().last());
            Assertions.assertEquals(98, param2.getName().order());

            Assertions.assertEquals(346, param2.getValue().first());
            Assertions.assertEquals(351, param2.getValue().last());
            Assertions.assertEquals(105, param2.getValue().order());

            Assertions.assertEquals(317, param2.first());
            Assertions.assertEquals(352, param2.last());
            Assertions.assertEquals(107, param2.order());

            final Parameter param3 = parameters.get(3);

            Assertions.assertEquals("Scale_Factor", param3.getName().getSemantics());
            Assertions.assertEquals(.9996, param3.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(364, param3.getName().first());
            Assertions.assertEquals(377, param3.getName().last());
            Assertions.assertEquals(111, param3.getName().order());

            Assertions.assertEquals(379, param3.getValue().first());
            Assertions.assertEquals(384, param3.getValue().last());
            Assertions.assertEquals(117, param3.getValue().order());

            Assertions.assertEquals(354, param3.first());
            Assertions.assertEquals(385, param3.last());
            Assertions.assertEquals(119, param3.order());

            final Parameter param4 = parameters.get(4);

            Assertions.assertEquals("Latitude_of_Origin", param4.getName().getSemantics());
            Assertions.assertEquals(0., param4.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(397, param4.getName().first());
            Assertions.assertEquals(416, param4.getName().last());
            Assertions.assertEquals(123, param4.getName().order());

            Assertions.assertEquals(418, param4.getValue().first());
            Assertions.assertEquals(420, param4.getValue().last());
            Assertions.assertEquals(129, param4.getValue().order());

            Assertions.assertEquals(387, param4.first());
            Assertions.assertEquals(421, param4.last());
            Assertions.assertEquals(131, param4.order());

            final Unit unit = projcs.getLinearUnit();

            Assertions.assertEquals("Meter", unit.getName().getSemantics());
            Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

            Assertions.assertEquals(428, unit.getName().first());
            Assertions.assertEquals(434, unit.getName().last());
            Assertions.assertEquals(135, unit.getName().order());

            Assertions.assertEquals(436, unit.getConversionFactor().first());
            Assertions.assertEquals(438, unit.getConversionFactor().last());
            Assertions.assertEquals(141, unit.getConversionFactor().order());

            Assertions.assertEquals(423, unit.first());
            Assertions.assertEquals(439, unit.last());
            Assertions.assertEquals(143, unit.order());
        }
    }

    @Test // fichier avec des parenthèses comme délimiteurs
    public void projcs_test_3b() throws LanguageException, IOException {

        try (var scanner = new DefaultStreamScanner(new BufferedReader(new InputStreamReader(
                WktParserTest.class.getResourceAsStream("example3b"))))) {

            final WktLexer lexer = new WktLexer(scanner, new ArrayList<>(), '(', ')');
            lexer.initialize();
            final WktParser parser = new WktParser(lexer);

            final ProjectedCs projcs = parser.projcs();

            Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

            Assertions.assertEquals(7, projcs.getName().first());
            Assertions.assertEquals(29, projcs.getName().last());
            Assertions.assertEquals(2, projcs.getName().order());

            Assertions.assertEquals(0, projcs.first());
            Assertions.assertEquals(440, projcs.last());
            Assertions.assertEquals(145, projcs.order());

            final GeographicCs geogcs = projcs.getGeographicCs();

            Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

            Assertions.assertEquals(38, geogcs.getName().first());
            Assertions.assertEquals(62, geogcs.getName().last());
            Assertions.assertEquals(6, geogcs.getName().order());

            Assertions.assertEquals(31, geogcs.first());
            Assertions.assertEquals(213, geogcs.last());
            Assertions.assertEquals(64, geogcs.order());

            final Datum datum = geogcs.getDatum();

            Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

            Assertions.assertEquals(70, datum.getName().first());
            Assertions.assertEquals(92, datum.getName().last());
            Assertions.assertEquals(10, datum.getName().order());

            Assertions.assertEquals(64, datum.first());
            Assertions.assertEquals(137, datum.last());
            Assertions.assertEquals(28, datum.order());

            final Spheroid spheroid = datum.getSpheroid();

            Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
            Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
            Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
            Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

            Assertions.assertEquals(104, spheroid.getName().first());
            Assertions.assertEquals(113, spheroid.getName().last());
            Assertions.assertEquals(14, spheroid.getName().order());

            Assertions.assertEquals(115, spheroid.getSemiMajorAxis().first());
            Assertions.assertEquals(121, spheroid.getSemiMajorAxis().last());
            Assertions.assertEquals(18, spheroid.getSemiMajorAxis().order());

            Assertions.assertEquals(123, spheroid.getInverseFlattening().first());
            Assertions.assertEquals(135, spheroid.getInverseFlattening().last());
            Assertions.assertEquals(24, spheroid.getInverseFlattening().order());

            Assertions.assertEquals(94, spheroid.first());
            Assertions.assertEquals(136, spheroid.last());
            Assertions.assertEquals(26, spheroid.order());

            final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

            Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
            Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

            Assertions.assertEquals(146, primeMeridian.getName().first());
            Assertions.assertEquals(157, primeMeridian.getName().last());
            Assertions.assertEquals(32, primeMeridian.getName().order());

            Assertions.assertEquals(159, primeMeridian.getLongitude().first());
            Assertions.assertEquals(159, primeMeridian.getLongitude().last());
            Assertions.assertEquals(36, primeMeridian.getLongitude().order());

            Assertions.assertEquals(139, primeMeridian.first());
            Assertions.assertEquals(160, primeMeridian.last());
            Assertions.assertEquals(38, primeMeridian.order());

            final Unit angular = geogcs.getAngularUnit();

            Assertions.assertEquals("Degree", angular.getName().getSemantics());
            Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

            Assertions.assertEquals(167, angular.getName().first());
            Assertions.assertEquals(174, angular.getName().last());
            Assertions.assertEquals(42, angular.getName().order());

            Assertions.assertEquals(176, angular.getConversionFactor().first());
            Assertions.assertEquals(193, angular.getConversionFactor().last());
            Assertions.assertEquals(48, angular.getConversionFactor().order());

            Assertions.assertEquals(162, angular.first());
            Assertions.assertEquals(194, angular.last());
            Assertions.assertEquals(50, angular.order());

            final Unit linear = geogcs.getLinearUnit();

            Assertions.assertEquals("Meter", linear.getName().getSemantics());
            Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());

            Assertions.assertEquals(201, linear.getName().first());
            Assertions.assertEquals(207, linear.getName().last());
            Assertions.assertEquals(54, linear.getName().order());

            Assertions.assertEquals(209, linear.getConversionFactor().first());
            Assertions.assertEquals(211, linear.getConversionFactor().last());
            Assertions.assertEquals(60, linear.getConversionFactor().order());

            Assertions.assertEquals(196, linear.first());
            Assertions.assertEquals(212, linear.last());
            Assertions.assertEquals(62, linear.order());

            final Projection projection = projcs.getProjection();

            Assertions.assertEquals("Transverse_Mercator", projection.getName().getSemantics());

            Assertions.assertEquals(226, projection.getName().first());
            Assertions.assertEquals(246, projection.getName().last());
            Assertions.assertEquals(68, projection.getName().order());

            Assertions.assertEquals(215, projection.first());
            Assertions.assertEquals(247, projection.last());
            Assertions.assertEquals(70, projection.order());

            final List<Parameter> parameters = projcs.getParameters();

            Assertions.assertEquals(5, parameters.size());

            final Parameter param0 = parameters.get(0);

            Assertions.assertEquals("False_Easting", param0.getName().getSemantics());
            Assertions.assertEquals(500000., param0.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(259, param0.getName().first());
            Assertions.assertEquals(273, param0.getName().last());
            Assertions.assertEquals(74, param0.getName().order());

            Assertions.assertEquals(275, param0.getValue().first());
            Assertions.assertEquals(282, param0.getValue().last());
            Assertions.assertEquals(80, param0.getValue().order());

            Assertions.assertEquals(249, param0.first());
            Assertions.assertEquals(283, param0.last());
            Assertions.assertEquals(82, param0.order());

            final Parameter param1 = parameters.get(1);

            Assertions.assertEquals("False_Northing", param1.getName().getSemantics());
            Assertions.assertEquals(0., param1.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(295, param1.getName().first());
            Assertions.assertEquals(310, param1.getName().last());
            Assertions.assertEquals(86, param1.getName().order());

            Assertions.assertEquals(312, param1.getValue().first());
            Assertions.assertEquals(314, param1.getValue().last());
            Assertions.assertEquals(92, param1.getValue().order());

            Assertions.assertEquals(285, param1.first());
            Assertions.assertEquals(315, param1.last());
            Assertions.assertEquals(94, param1.order());

            final Parameter param2 = parameters.get(2);

            Assertions.assertEquals("Central_Meridian", param2.getName().getSemantics());
            Assertions.assertEquals(-123., param2.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(327, param2.getName().first());
            Assertions.assertEquals(344, param2.getName().last());
            Assertions.assertEquals(98, param2.getName().order());

            Assertions.assertEquals(346, param2.getValue().first());
            Assertions.assertEquals(351, param2.getValue().last());
            Assertions.assertEquals(105, param2.getValue().order());

            Assertions.assertEquals(317, param2.first());
            Assertions.assertEquals(352, param2.last());
            Assertions.assertEquals(107, param2.order());

            final Parameter param3 = parameters.get(3);

            Assertions.assertEquals("Scale_Factor", param3.getName().getSemantics());
            Assertions.assertEquals(.9996, param3.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(364, param3.getName().first());
            Assertions.assertEquals(377, param3.getName().last());
            Assertions.assertEquals(111, param3.getName().order());

            Assertions.assertEquals(379, param3.getValue().first());
            Assertions.assertEquals(384, param3.getValue().last());
            Assertions.assertEquals(117, param3.getValue().order());

            Assertions.assertEquals(354, param3.first());
            Assertions.assertEquals(385, param3.last());
            Assertions.assertEquals(119, param3.order());

            final Parameter param4 = parameters.get(4);

            Assertions.assertEquals("Latitude_of_Origin", param4.getName().getSemantics());
            Assertions.assertEquals(0., param4.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(397, param4.getName().first());
            Assertions.assertEquals(416, param4.getName().last());
            Assertions.assertEquals(123, param4.getName().order());

            Assertions.assertEquals(418, param4.getValue().first());
            Assertions.assertEquals(420, param4.getValue().last());
            Assertions.assertEquals(129, param4.getValue().order());

            Assertions.assertEquals(387, param4.first());
            Assertions.assertEquals(421, param4.last());
            Assertions.assertEquals(131, param4.order());

            final Unit unit = projcs.getLinearUnit();

            Assertions.assertEquals("Meter", unit.getName().getSemantics());
            Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

            Assertions.assertEquals(428, unit.getName().first());
            Assertions.assertEquals(434, unit.getName().last());
            Assertions.assertEquals(135, unit.getName().order());

            Assertions.assertEquals(436, unit.getConversionFactor().first());
            Assertions.assertEquals(438, unit.getConversionFactor().last());
            Assertions.assertEquals(141, unit.getConversionFactor().order());

            Assertions.assertEquals(423, unit.first());
            Assertions.assertEquals(439, unit.last());
            Assertions.assertEquals(143, unit.order());
        }
    }

    @Test // vérification du support de l'indentation
    public void projcs_test_3c() throws LanguageException, IOException {

        try (var scanner = new DefaultStreamScanner(new BufferedReader(new InputStreamReader(
                WktParserTest.class.getResourceAsStream("example3c"))))) {

            final WktLexer lexer = new WktLexer(scanner, new ArrayList<>(), LD, RD);
            lexer.initialize();
            final WktParser parser = new WktParser(lexer);

            final ProjectedCs projcs = parser.projcs();

            Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

            final GeographicCs geogcs = projcs.getGeographicCs();

            Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

            final Datum datum = geogcs.getDatum();

            Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

            final Spheroid spheroid = datum.getSpheroid();

            Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
            Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
            Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
            Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

            final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

            Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
            Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

            final Unit angular = geogcs.getAngularUnit();

            Assertions.assertEquals("Degree", angular.getName().getSemantics());
            Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

            final Unit linear = geogcs.getLinearUnit();

            Assertions.assertEquals("Meter", linear.getName().getSemantics());
            Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());

            final Projection projection = projcs.getProjection();

            Assertions.assertEquals("Transverse_Mercator", projection.getName().getSemantics());

            final List<Parameter> parameters = projcs.getParameters();

            Assertions.assertEquals(5, parameters.size());

            final Parameter param0 = parameters.get(0);

            Assertions.assertEquals("False_Easting", param0.getName().getSemantics());
            Assertions.assertEquals(500000., param0.getValue().getSemantics().doubleValue());

            final Parameter param1 = parameters.get(1);

            Assertions.assertEquals("False_Northing", param1.getName().getSemantics());
            Assertions.assertEquals(0., param1.getValue().getSemantics().doubleValue());

            final Parameter param2 = parameters.get(2);

            Assertions.assertEquals("Central_Meridian", param2.getName().getSemantics());
            Assertions.assertEquals(-123., param2.getValue().getSemantics().doubleValue());

            final Parameter param3 = parameters.get(3);

            Assertions.assertEquals("Scale_Factor", param3.getName().getSemantics());
            Assertions.assertEquals(.9996, param3.getValue().getSemantics().doubleValue());

            final Parameter param4 = parameters.get(4);

            Assertions.assertEquals("Latitude_of_Origin", param4.getName().getSemantics());
            Assertions.assertEquals(0., param4.getValue().getSemantics().doubleValue());

            final Unit unit = projcs.getLinearUnit();

            Assertions.assertEquals("Meter", unit.getName().getSemantics());
            Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());
        }
    }

    @Test
    public void spatial_reference_system_test_2_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree",0.0174532925199433))""", '(', ')');

        final GeocentricCs geoccs = (GeocentricCs) parser.coordinateSystem();

        Assertions.assertEquals("GCS_North_American_1983", geoccs.getName().getSemantics());

        Assertions.assertEquals(7, geoccs.getName().first());
        Assertions.assertEquals(31, geoccs.getName().last());
        Assertions.assertEquals(2, geoccs.getName().order());

        Assertions.assertEquals(0, geoccs.first());
        Assertions.assertEquals(164, geoccs.last());
        Assertions.assertEquals(48, geoccs.order());

        final Datum datum = geoccs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(33 + 73, datum.last());
        Assertions.assertEquals(24, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(33 + 40, spheroid.getName().first());
        Assertions.assertEquals(33 + 49, spheroid.getName().last());
        Assertions.assertEquals(10, spheroid.getName().order());

        Assertions.assertEquals(33 + 51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(33 + 57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(14, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(33 + 59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(33 + 71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(20, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(33 + 30, spheroid.first());
        Assertions.assertEquals(33 + 72, spheroid.last());
        Assertions.assertEquals(22, spheroid.order());

        final PrimeMeridian primeMeridian = geoccs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(28, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getLongitude().last());
        Assertions.assertEquals(32, primeMeridian.getLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(34, primeMeridian.order());

        final Unit unit = geoccs.getLinearUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(44, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(46, unit.order());
    }

    @Test
    public void spatial_reference_system_test_2_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),UNIT("Degree",0.0174532925199433))""", '(', ')');

        final GeographicCs geogcs = (GeographicCs) parser.coordinateSystem();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(164, geogcs.last());
        Assertions.assertEquals(48, geogcs.order());

        final Datum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(33 + 73, datum.last());
        Assertions.assertEquals(24, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(33 + 40, spheroid.getName().first());
        Assertions.assertEquals(33 + 49, spheroid.getName().last());
        Assertions.assertEquals(10, spheroid.getName().order());

        Assertions.assertEquals(33 + 51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(33 + 57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(14, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(33 + 59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(33 + 71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(20, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(33 + 30, spheroid.first());
        Assertions.assertEquals(33 + 72, spheroid.last());
        Assertions.assertEquals(22, spheroid.order());

        final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(28, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getLongitude().last());
        Assertions.assertEquals(32, primeMeridian.getLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(34, primeMeridian.order());

        final Unit unit = geogcs.getAngularUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(44, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(46, unit.order());

        Assertions.assertNull(geogcs.getLinearUnit());
    }

    @Test
    public void spatial_reference_system_test_2_3() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree\",0.0174532925199433),UNIT("Meter",1.0))""", '(', ')');

        final GeographicCs geogcs = (GeographicCs) parser.coordinateSystem();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(182, geogcs.last());
        Assertions.assertEquals(60, geogcs.order());

        final Datum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(33 + 73, datum.last());
        Assertions.assertEquals(24, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(33 + 40, spheroid.getName().first());
        Assertions.assertEquals(33 + 49, spheroid.getName().last());
        Assertions.assertEquals(10, spheroid.getName().order());

        Assertions.assertEquals(33 + 51, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(33 + 57, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(14, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(33 + 59, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(33 + 71, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(20, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(33 + 30, spheroid.first());
        Assertions.assertEquals(33 + 72, spheroid.last());
        Assertions.assertEquals(22, spheroid.order());

        final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(28, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getLongitude().last());
        Assertions.assertEquals(32, primeMeridian.getLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(34, primeMeridian.order());

        final Unit angular = geogcs.getAngularUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, angular.getName().first());
        Assertions.assertEquals(143, angular.getName().last());
        Assertions.assertEquals(38, angular.getName().order());

        Assertions.assertEquals(145, angular.getConversionFactor().first());
        Assertions.assertEquals(162, angular.getConversionFactor().last());
        Assertions.assertEquals(44, angular.getConversionFactor().order());

        Assertions.assertEquals(131, angular.first());
        Assertions.assertEquals(163, angular.last());
        Assertions.assertEquals(46, angular.order());

        final Unit linear = geogcs.getLinearUnit();

        Assertions.assertEquals("Meter", linear.getName().getSemantics());
        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(170, linear.getName().first());
        Assertions.assertEquals(176, linear.getName().last());
        Assertions.assertEquals(50, linear.getName().order());

        Assertions.assertEquals(178, linear.getConversionFactor().first());
        Assertions.assertEquals(180, linear.getConversionFactor().last());
        Assertions.assertEquals(56, linear.getConversionFactor().order());

        Assertions.assertEquals(165, linear.first());
        Assertions.assertEquals(181, linear.last());
        Assertions.assertEquals(58, linear.order());
    }

    @Test
    public void spatial_reference_system_test_2_4() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJCS("NAD_1983_UTM_Zone_10N",\
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree",0.0174532925199433),UNIT("Meter",1.0)),\
                                              PROJECTION("Transverse_Mercator"),\
                                              PARAMETER("False_Easting",500000.0),\
                                              PARAMETER("False_Northing",0.0),\
                                              PARAMETER("Central_Meridian",-123.0),\
                                              PARAMETER("Scale_Factor",0.9996),\
                                              PARAMETER("Latitude_of_Origin",0.0),\
                                              UNIT("Meter",1.0))""", '(', ')');

        final ProjectedCs projcs = (ProjectedCs) parser.coordinateSystem();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(440, projcs.last());
        Assertions.assertEquals(145, projcs.order());

        final GeographicCs geogcs = projcs.getGeographicCs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(38, geogcs.getName().first());
        Assertions.assertEquals(62, geogcs.getName().last());
        Assertions.assertEquals(6, geogcs.getName().order());

        Assertions.assertEquals(31, geogcs.first());
        Assertions.assertEquals(213, geogcs.last());
        Assertions.assertEquals(64, geogcs.order());

        final Datum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(70, datum.getName().first());
        Assertions.assertEquals(92, datum.getName().last());
        Assertions.assertEquals(10, datum.getName().order());

        Assertions.assertEquals(64, datum.first());
        Assertions.assertEquals(137, datum.last());
        Assertions.assertEquals(28, datum.order());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertEquals(104, spheroid.getName().first());
        Assertions.assertEquals(113, spheroid.getName().last());
        Assertions.assertEquals(14, spheroid.getName().order());

        Assertions.assertEquals(115, spheroid.getSemiMajorAxis().first());
        Assertions.assertEquals(121, spheroid.getSemiMajorAxis().last());
        Assertions.assertEquals(18, spheroid.getSemiMajorAxis().order());

        Assertions.assertEquals(123, spheroid.getInverseFlattening().first());
        Assertions.assertEquals(135, spheroid.getInverseFlattening().last());
        Assertions.assertEquals(24, spheroid.getInverseFlattening().order());

        Assertions.assertEquals(94, spheroid.first());
        Assertions.assertEquals(136, spheroid.last());
        Assertions.assertEquals(26, spheroid.order());

        final PrimeMeridian primeMeridian = geogcs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(146, primeMeridian.getName().first());
        Assertions.assertEquals(157, primeMeridian.getName().last());
        Assertions.assertEquals(32, primeMeridian.getName().order());

        Assertions.assertEquals(159, primeMeridian.getLongitude().first());
        Assertions.assertEquals(159, primeMeridian.getLongitude().last());
        Assertions.assertEquals(36, primeMeridian.getLongitude().order());

        Assertions.assertEquals(139, primeMeridian.first());
        Assertions.assertEquals(160, primeMeridian.last());
        Assertions.assertEquals(38, primeMeridian.order());

        final Unit angular = geogcs.getAngularUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(167, angular.getName().first());
        Assertions.assertEquals(174, angular.getName().last());
        Assertions.assertEquals(42, angular.getName().order());

        Assertions.assertEquals(176, angular.getConversionFactor().first());
        Assertions.assertEquals(193, angular.getConversionFactor().last());
        Assertions.assertEquals(48, angular.getConversionFactor().order());

        Assertions.assertEquals(162, angular.first());
        Assertions.assertEquals(194, angular.last());
        Assertions.assertEquals(50, angular.order());

        final Unit linear = geogcs.getLinearUnit();

        Assertions.assertEquals("Meter", linear.getName().getSemantics());
        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(201, linear.getName().first());
        Assertions.assertEquals(207, linear.getName().last());
        Assertions.assertEquals(54, linear.getName().order());

        Assertions.assertEquals(209, linear.getConversionFactor().first());
        Assertions.assertEquals(211, linear.getConversionFactor().last());
        Assertions.assertEquals(60, linear.getConversionFactor().order());

        Assertions.assertEquals(196, linear.first());
        Assertions.assertEquals(212, linear.last());
        Assertions.assertEquals(62, linear.order());

        final Projection projection = projcs.getProjection();

        Assertions.assertEquals("Transverse_Mercator", projection.getName().getSemantics());

        Assertions.assertEquals(226, projection.getName().first());
        Assertions.assertEquals(246, projection.getName().last());
        Assertions.assertEquals(68, projection.getName().order());

        Assertions.assertEquals(215, projection.first());
        Assertions.assertEquals(247, projection.last());
        Assertions.assertEquals(70, projection.order());

        final List<Parameter> parameters = projcs.getParameters();

        Assertions.assertEquals(5, parameters.size());

        final Parameter param0 = parameters.get(0);

        Assertions.assertEquals("False_Easting", param0.getName().getSemantics());
        Assertions.assertEquals(500000., param0.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(259, param0.getName().first());
        Assertions.assertEquals(273, param0.getName().last());
        Assertions.assertEquals(74, param0.getName().order());

        Assertions.assertEquals(275, param0.getValue().first());
        Assertions.assertEquals(282, param0.getValue().last());
        Assertions.assertEquals(80, param0.getValue().order());

        Assertions.assertEquals(249, param0.first());
        Assertions.assertEquals(283, param0.last());
        Assertions.assertEquals(82, param0.order());

        final Parameter param1 = parameters.get(1);

        Assertions.assertEquals("False_Northing", param1.getName().getSemantics());
        Assertions.assertEquals(0., param1.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(295, param1.getName().first());
        Assertions.assertEquals(310, param1.getName().last());
        Assertions.assertEquals(86, param1.getName().order());

        Assertions.assertEquals(312, param1.getValue().first());
        Assertions.assertEquals(314, param1.getValue().last());
        Assertions.assertEquals(92, param1.getValue().order());

        Assertions.assertEquals(285, param1.first());
        Assertions.assertEquals(315, param1.last());
        Assertions.assertEquals(94, param1.order());

        final Parameter param2 = parameters.get(2);

        Assertions.assertEquals("Central_Meridian", param2.getName().getSemantics());
        Assertions.assertEquals(-123., param2.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(327, param2.getName().first());
        Assertions.assertEquals(344, param2.getName().last());
        Assertions.assertEquals(98, param2.getName().order());

        Assertions.assertEquals(346, param2.getValue().first());
        Assertions.assertEquals(351, param2.getValue().last());
        Assertions.assertEquals(105, param2.getValue().order());

        Assertions.assertEquals(317, param2.first());
        Assertions.assertEquals(352, param2.last());
        Assertions.assertEquals(107, param2.order());

        final Parameter param3 = parameters.get(3);

        Assertions.assertEquals("Scale_Factor", param3.getName().getSemantics());
        Assertions.assertEquals(.9996, param3.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(364, param3.getName().first());
        Assertions.assertEquals(377, param3.getName().last());
        Assertions.assertEquals(111, param3.getName().order());

        Assertions.assertEquals(379, param3.getValue().first());
        Assertions.assertEquals(384, param3.getValue().last());
        Assertions.assertEquals(117, param3.getValue().order());

        Assertions.assertEquals(354, param3.first());
        Assertions.assertEquals(385, param3.last());
        Assertions.assertEquals(119, param3.order());

        final Parameter param4 = parameters.get(4);

        Assertions.assertEquals("Latitude_of_Origin", param4.getName().getSemantics());
        Assertions.assertEquals(0., param4.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(397, param4.getName().first());
        Assertions.assertEquals(416, param4.getName().last());
        Assertions.assertEquals(123, param4.getName().order());

        Assertions.assertEquals(418, param4.getValue().first());
        Assertions.assertEquals(420, param4.getValue().last());
        Assertions.assertEquals(129, param4.getValue().order());

        Assertions.assertEquals(387, param4.first());
        Assertions.assertEquals(421, param4.last());
        Assertions.assertEquals(131, param4.order());

        final Unit unit = projcs.getLinearUnit();

        Assertions.assertEquals("Meter", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(428, unit.getName().first());
        Assertions.assertEquals(434, unit.getName().last());
        Assertions.assertEquals(135, unit.getName().order());

        Assertions.assertEquals(436, unit.getConversionFactor().first());
        Assertions.assertEquals(438, unit.getConversionFactor().last());
        Assertions.assertEquals(141, unit.getConversionFactor().order());

        Assertions.assertEquals(423, unit.first());
        Assertions.assertEquals(439, unit.last());
        Assertions.assertEquals(143, unit.order());
    }
}
