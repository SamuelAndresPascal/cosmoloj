package com.cosmoloj.language.wkt2.v2_1.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.parsing.DefaultStreamScanner;
import com.cosmoloj.language.wkt2.v2_1.expression.BaseGeodeticCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.DerivedCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.Ellipsoid;
import com.cosmoloj.language.wkt2.v2_1.expression.GeodeticCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.GeodeticDatum;
import com.cosmoloj.language.wkt2.v2_1.expression.Method;
import com.cosmoloj.language.wkt2.v2_1.expression.Operation;
import com.cosmoloj.language.wkt2.v2_1.expression.Parameter;
import com.cosmoloj.language.wkt2.v2_1.expression.PrimeMeridian;
import com.cosmoloj.language.wkt2.v2_1.expression.Unit;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;
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
public class WktParserSfTest {

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
        Assertions.assertEquals(7, unit.getConversionFactor().order());

        Assertions.assertEquals(0, unit.first());
        Assertions.assertEquals(32, unit.last());
        Assertions.assertEquals(9, unit.order());
    }

    @Test
    public void prime_meridian_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("PRIMEM[\"Greenwitch\",0]");

        final PrimeMeridian primeMeridian = parser.primeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(7, primeMeridian.getName().first());
        Assertions.assertEquals(18, primeMeridian.getName().last());
        Assertions.assertEquals(2, primeMeridian.getName().order());

        Assertions.assertEquals(20, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(20, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(6, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(0, primeMeridian.first());
        Assertions.assertEquals(21, primeMeridian.last());
        Assertions.assertEquals(8, primeMeridian.order());
    }

    @Test
    public void parameter_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("PARAMETER[\"False_Easting\",500000.0]");

        final Parameter parameter = parser.projectionParameter();

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

        final Operation.MapProjection projection = parser.mapProjection();

        final Method.MapProjectionMethod method = projection.getMethod();

        Assertions.assertEquals("Transverse_Mercator", method.getName().getSemantics());

        Assertions.assertEquals(11, method.getName().first());
        Assertions.assertEquals(31, method.getName().last());
        Assertions.assertEquals(2, method.getName().order());

        Assertions.assertEquals(0, projection.first());
        Assertions.assertEquals(32, projection.last());
        Assertions.assertEquals(5, projection.order());
    }

    @Test
    public void spheroid_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("SPHEROID[\"GRS_1980\",6378137,298.257222101]");

        final Ellipsoid spheroid = parser.ellipsoid();

        Assertions.assertEquals(WktKeyword.SPHEROID, spheroid.getLabel().getSemantics());
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

        final Ellipsoid spheroid = parser.ellipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final GeodeticDatum datum = parser.geodeticDatum(new Token[2]);

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(6, datum.getName().first());
        Assertions.assertEquals(28, datum.getName().last());
        Assertions.assertEquals(2, datum.getName().order());

        Assertions.assertEquals(0, datum.first());
        Assertions.assertEquals(73, datum.last());
        Assertions.assertEquals(20, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final GeodeticCrs geoccs = parser.geodeticCrs();

        Assertions.assertEquals("GCS_North_American_1983", geoccs.getName().getSemantics());

        Assertions.assertEquals(7, geoccs.getName().first());
        Assertions.assertEquals(31, geoccs.getName().last());
        Assertions.assertEquals(2, geoccs.getName().order());

        Assertions.assertEquals(0, geoccs.first());
        Assertions.assertEquals(164, geoccs.last());
        Assertions.assertEquals(48, geoccs.order());

        final GeodeticDatum datum = geoccs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(129, datum.last());
        Assertions.assertEquals(34, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geoccs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(27, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(31, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(33, primeMeridian.order());

        final Unit unit = geoccs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(43, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(45, unit.order());
    }

    @Test
    public void geogcs_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],UNIT["Degree",0.0174532925199433]]""");

        final GeodeticCrs geogcs = parser.geodeticCrs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(164, geogcs.last());
        Assertions.assertEquals(48, geogcs.order());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(129, datum.last());
        Assertions.assertEquals(34, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(27, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(31, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(33, primeMeridian.order());

        final Unit unit = geogcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(43, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(45, unit.order());
    }

    @SectionReference(type = SectionReferenceType.SECTION, id = "C.4.1") // pas de rétrocompatibilité ellipsoidal CS 3D
    @Test
    public void geogcs_3d_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],\
                                              UNIT["Degree",0.0174532925199433],UNIT["Meter",1.0]]""");

        final GeodeticCrs geogcs = parser.geodeticCrs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(182, geogcs.last());
        Assertions.assertEquals(59, geogcs.order());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(129, datum.last());
        Assertions.assertEquals(34, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(27, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(31, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(33, primeMeridian.order());

        final Unit angular = geogcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, angular.getName().first());
        Assertions.assertEquals(143, angular.getName().last());
        Assertions.assertEquals(38, angular.getName().order());

        Assertions.assertEquals(145, angular.getConversionFactor().first());
        Assertions.assertEquals(162, angular.getConversionFactor().last());
        Assertions.assertEquals(43, angular.getConversionFactor().order());

        Assertions.assertEquals(131, angular.first());
        Assertions.assertEquals(163, angular.last());
        Assertions.assertEquals(45, angular.order());

//        final Unit linear = geogcs.getLinearUnit();
//
//        Assertions.assertEquals("Meter", linear.getName().getSemantics());
//        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());
//
//        Assertions.assertEquals(170, linear.getName().first());
//        Assertions.assertEquals(176, linear.getName().last());
//        Assertions.assertEquals(50, linear.getName().order());
//
//        Assertions.assertEquals(178, linear.getConversionFactor().first());
//        Assertions.assertEquals(180, linear.getConversionFactor().last());
//        Assertions.assertEquals(56, linear.getConversionFactor().order());
//
//        Assertions.assertEquals(165, linear.first());
//        Assertions.assertEquals(181, linear.last());
//        Assertions.assertEquals(58, linear.order());
    }

    @Test
    public void projcs_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJCS["NAD_1983_UTM_Zone_10N",\
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],\
                                              UNIT["Degree",0.0174532925199433]],\
                                              PROJECTION["Transverse_Mercator"],\
                                              PARAMETER["False_Easting",500000.0],\
                                              PARAMETER["False_Northing",0.0],\
                                              PARAMETER["Central_Meridian",-123.0],\
                                              PARAMETER["Scale_Factor",0.9996],\
                                              PARAMETER["Latitude_of_Origin",0.0],\
                                              UNIT["Meter",1.0]]""");

        final DerivedCrs.ProjectedCrs projcs = parser.projectedCrs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(422, projcs.last());
        Assertions.assertEquals(133, projcs.order());

        final BaseGeodeticCrs geogcs = projcs.getBaseCrs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(38, geogcs.getName().first());
        Assertions.assertEquals(62, geogcs.getName().last());
        Assertions.assertEquals(6, geogcs.getName().order());

        Assertions.assertEquals(31, geogcs.first());
        Assertions.assertEquals(195, geogcs.last());
        Assertions.assertEquals(51, geogcs.order());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(70, datum.getName().first());
        Assertions.assertEquals(92, datum.getName().last());
        Assertions.assertEquals(10, datum.getName().order());

        Assertions.assertEquals(64, datum.first());
        Assertions.assertEquals(160, datum.last());
        Assertions.assertEquals(38, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(146, primeMeridian.getName().first());
        Assertions.assertEquals(157, primeMeridian.getName().last());
        Assertions.assertEquals(31, primeMeridian.getName().order());

        Assertions.assertEquals(159, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(159, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(35, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(139, primeMeridian.first());
        Assertions.assertEquals(160, primeMeridian.last());
        Assertions.assertEquals(37, primeMeridian.order());

        final Unit angular = geogcs.getUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(167, angular.getName().first());
        Assertions.assertEquals(174, angular.getName().last());
        Assertions.assertEquals(42, angular.getName().order());

        Assertions.assertEquals(176, angular.getConversionFactor().first());
        Assertions.assertEquals(193, angular.getConversionFactor().last());
        Assertions.assertEquals(47, angular.getConversionFactor().order());

        Assertions.assertEquals(162, angular.first());
        Assertions.assertEquals(194, angular.last());
        Assertions.assertEquals(49, angular.order());

//        final Unit linear = geogcs.getLinearUnit();
//
//        Assertions.assertEquals("Meter", linear.getName().getSemantics());
//        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());
//
//        Assertions.assertEquals(201, linear.getName().first());
//        Assertions.assertEquals(207, linear.getName().last());
//        Assertions.assertEquals(54, linear.getName().order());
//
//        Assertions.assertEquals(209, linear.getConversionFactor().first());
//        Assertions.assertEquals(211, linear.getConversionFactor().last());
//        Assertions.assertEquals(60, linear.getConversionFactor().order());
//
//        Assertions.assertEquals(196, linear.first());
//        Assertions.assertEquals(212, linear.last());
//        Assertions.assertEquals(62, linear.order());

        final Operation.MapProjection projection = projcs.getOperation();

        final Method.MapProjectionMethod method = projection.getMethod();

        Assertions.assertEquals("Transverse_Mercator", method.getName().getSemantics());

        Assertions.assertEquals(208, method.getName().first());
        Assertions.assertEquals(228, method.getName().last());
        Assertions.assertEquals(55, method.getName().order());

        Assertions.assertEquals(197, projection.first());
        Assertions.assertEquals(404, projection.last());
        Assertions.assertEquals(120, projection.order());

        final List<Parameter> parameters = projection.getParameters();

        Assertions.assertEquals(5, parameters.size());

        final Parameter param0 = parameters.get(0);

        Assertions.assertEquals("False_Easting", param0.getName().getSemantics());
        Assertions.assertEquals(500000., param0.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(241, param0.getName().first());
        Assertions.assertEquals(255, param0.getName().last());
        Assertions.assertEquals(61, param0.getName().order());

        Assertions.assertEquals(257, param0.getValue().first());
        Assertions.assertEquals(264, param0.getValue().last());
        Assertions.assertEquals(67, param0.getValue().order());

        Assertions.assertEquals(231, param0.first());
        Assertions.assertEquals(265, param0.last());
        Assertions.assertEquals(69, param0.order());

        final Parameter param1 = parameters.get(1);

        Assertions.assertEquals("False_Northing", param1.getName().getSemantics());
        Assertions.assertEquals(0., param1.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(277, param1.getName().first());
        Assertions.assertEquals(292, param1.getName().last());
        Assertions.assertEquals(73, param1.getName().order());

        Assertions.assertEquals(294, param1.getValue().first());
        Assertions.assertEquals(296, param1.getValue().last());
        Assertions.assertEquals(79, param1.getValue().order());

        Assertions.assertEquals(267, param1.first());
        Assertions.assertEquals(297, param1.last());
        Assertions.assertEquals(81, param1.order());

        final Parameter param2 = parameters.get(2);

        Assertions.assertEquals("Central_Meridian", param2.getName().getSemantics());
        Assertions.assertEquals(-123., param2.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(309, param2.getName().first());
        Assertions.assertEquals(326, param2.getName().last());
        Assertions.assertEquals(85, param2.getName().order());

        Assertions.assertEquals(328, param2.getValue().first());
        Assertions.assertEquals(333, param2.getValue().last());
        Assertions.assertEquals(92, param2.getValue().order());

        Assertions.assertEquals(299, param2.first());
        Assertions.assertEquals(334, param2.last());
        Assertions.assertEquals(94, param2.order());

        final Parameter param3 = parameters.get(3);

        Assertions.assertEquals("Scale_Factor", param3.getName().getSemantics());
        Assertions.assertEquals(.9996, param3.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(346, param3.getName().first());
        Assertions.assertEquals(359, param3.getName().last());
        Assertions.assertEquals(98, param3.getName().order());

        Assertions.assertEquals(361, param3.getValue().first());
        Assertions.assertEquals(366, param3.getValue().last());
        Assertions.assertEquals(104, param3.getValue().order());

        Assertions.assertEquals(336, param3.first());
        Assertions.assertEquals(367, param3.last());
        Assertions.assertEquals(106, param3.order());

        final Parameter param4 = parameters.get(4);

        Assertions.assertEquals("Latitude_of_Origin", param4.getName().getSemantics());
        Assertions.assertEquals(0., param4.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(379, param4.getName().first());
        Assertions.assertEquals(398, param4.getName().last());
        Assertions.assertEquals(110, param4.getName().order());

        Assertions.assertEquals(400, param4.getValue().first());
        Assertions.assertEquals(402, param4.getValue().last());
        Assertions.assertEquals(116, param4.getValue().order());

        Assertions.assertEquals(369, param4.first());
        Assertions.assertEquals(403, param4.last());
        Assertions.assertEquals(118, param4.order());

        final Unit unit = projcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Meter", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(410, unit.getName().first());
        Assertions.assertEquals(416, unit.getName().last());
        Assertions.assertEquals(123, unit.getName().order());

        Assertions.assertEquals(418, unit.getConversionFactor().first());
        Assertions.assertEquals(420, unit.getConversionFactor().last());
        Assertions.assertEquals(128, unit.getConversionFactor().order());

        Assertions.assertEquals(405, unit.first());
        Assertions.assertEquals(421, unit.last());
        Assertions.assertEquals(130, unit.order());
    }

    @Test
    public void spatial_reference_system_test_1_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],UNIT["Degree",0.0174532925199433]]""");

        final GeodeticCrs geoccs = (GeodeticCrs) parser.coordinateReferenceSystem();

        Assertions.assertEquals("GCS_North_American_1983", geoccs.getName().getSemantics());

        Assertions.assertEquals(7, geoccs.getName().first());
        Assertions.assertEquals(31, geoccs.getName().last());
        Assertions.assertEquals(2, geoccs.getName().order());

        Assertions.assertEquals(0, geoccs.first());
        Assertions.assertEquals(164, geoccs.last());
        Assertions.assertEquals(48, geoccs.order());

        final GeodeticDatum datum = geoccs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(129, datum.last());
        Assertions.assertEquals(34, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geoccs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(27, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(31, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(33, primeMeridian.order());

        final Unit unit = geoccs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(43, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(45, unit.order());
    }

    @Test
    public void spatial_reference_system_test_1_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],UNIT["Degree",0.0174532925199433]]""");

        final GeodeticCrs geogcs = (GeodeticCrs) parser.coordinateReferenceSystem();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(164, geogcs.last());
        Assertions.assertEquals(48, geogcs.order());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(129, datum.last());
        Assertions.assertEquals(34, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(27, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(31, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(33, primeMeridian.order());

        final Unit unit = geogcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(43, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(45, unit.order());
    }

    @Test
    public void spatial_reference_system_test_1_3() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],\
                                              UNIT["Degree",0.0174532925199433],UNIT["Meter",1.0]]""");

        final GeodeticCrs geogcs = (GeodeticCrs) parser.coordinateReferenceSystem();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(182, geogcs.last());
        Assertions.assertEquals(59, geogcs.order());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(129, datum.last());
        Assertions.assertEquals(34, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(27, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(31, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(33, primeMeridian.order());

        final Unit angular = geogcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, angular.getName().first());
        Assertions.assertEquals(143, angular.getName().last());
        Assertions.assertEquals(38, angular.getName().order());

        Assertions.assertEquals(145, angular.getConversionFactor().first());
        Assertions.assertEquals(162, angular.getConversionFactor().last());
        Assertions.assertEquals(43, angular.getConversionFactor().order());

        Assertions.assertEquals(131, angular.first());
        Assertions.assertEquals(163, angular.last());
        Assertions.assertEquals(45, angular.order());

//        final Unit linear = geogcs.getLinearUnit();
//
//        Assertions.assertEquals("Meter", linear.getName().getSemantics());
//        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());
//
//        Assertions.assertEquals(170, linear.getName().first());
//        Assertions.assertEquals(176, linear.getName().last());
//        Assertions.assertEquals(50, linear.getName().order());
//
//        Assertions.assertEquals(178, linear.getConversionFactor().first());
//        Assertions.assertEquals(180, linear.getConversionFactor().last());
//        Assertions.assertEquals(56, linear.getConversionFactor().order());
//
//        Assertions.assertEquals(165, linear.first());
//        Assertions.assertEquals(181, linear.last());
//        Assertions.assertEquals(58, linear.order());
    }

    @Test
    public void spatial_reference_system_test_1_4() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJCS["NAD_1983_UTM_Zone_10N",\
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],\
                                              UNIT["Degree",0.0174532925199433]],\
                                              PROJECTION["Transverse_Mercator"],\
                                              PARAMETER["False_Easting",500000.0],\
                                              PARAMETER["False_Northing",0.0],\
                                              PARAMETER["Central_Meridian",-123.0],\
                                              PARAMETER["Scale_Factor",0.9996],\
                                              PARAMETER["Latitude_of_Origin",0.0],\
                                              UNIT["Meter",1.0]]""");

        final DerivedCrs.ProjectedCrs projcs = (DerivedCrs.ProjectedCrs) parser.coordinateReferenceSystem();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(422, projcs.last());
        Assertions.assertEquals(133, projcs.order());

        final BaseGeodeticCrs geogcs = projcs.getBaseCrs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(38, geogcs.getName().first());
        Assertions.assertEquals(62, geogcs.getName().last());
        Assertions.assertEquals(6, geogcs.getName().order());

        Assertions.assertEquals(31, geogcs.first());
        Assertions.assertEquals(195, geogcs.last());
        Assertions.assertEquals(51, geogcs.order());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(70, datum.getName().first());
        Assertions.assertEquals(92, datum.getName().last());
        Assertions.assertEquals(10, datum.getName().order());

        Assertions.assertEquals(64, datum.first());
        Assertions.assertEquals(160, datum.last());
        Assertions.assertEquals(38, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(146, primeMeridian.getName().first());
        Assertions.assertEquals(157, primeMeridian.getName().last());
        Assertions.assertEquals(31, primeMeridian.getName().order());

        Assertions.assertEquals(159, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(159, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(35, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(139, primeMeridian.first());
        Assertions.assertEquals(160, primeMeridian.last());
        Assertions.assertEquals(37, primeMeridian.order());

        final Unit angular = geogcs.getUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(167, angular.getName().first());
        Assertions.assertEquals(174, angular.getName().last());
        Assertions.assertEquals(42, angular.getName().order());

        Assertions.assertEquals(176, angular.getConversionFactor().first());
        Assertions.assertEquals(193, angular.getConversionFactor().last());
        Assertions.assertEquals(47, angular.getConversionFactor().order());

        Assertions.assertEquals(162, angular.first());
        Assertions.assertEquals(194, angular.last());
        Assertions.assertEquals(49, angular.order());

//        final Unit linear = geogcs.getLinearUnit();
//
//        Assertions.assertEquals("Meter", linear.getName().getSemantics());
//        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());
//
//        Assertions.assertEquals(201, linear.getName().first());
//        Assertions.assertEquals(207, linear.getName().last());
//        Assertions.assertEquals(54, linear.getName().order());
//
//        Assertions.assertEquals(209, linear.getConversionFactor().first());
//        Assertions.assertEquals(211, linear.getConversionFactor().last());
//        Assertions.assertEquals(60, linear.getConversionFactor().order());
//
//        Assertions.assertEquals(196, linear.first());
//        Assertions.assertEquals(212, linear.last());
//        Assertions.assertEquals(62, linear.order());

        final Operation.MapProjection projection = projcs.getOperation();

        final Method.MapProjectionMethod method = projection.getMethod();

        Assertions.assertEquals("Transverse_Mercator", method.getName().getSemantics());

        Assertions.assertEquals(208, method.getName().first());
        Assertions.assertEquals(228, method.getName().last());
        Assertions.assertEquals(55, method.getName().order());

        Assertions.assertEquals(197, projection.first());
        Assertions.assertEquals(404, projection.last());
        Assertions.assertEquals(120, projection.order());

        final List<Parameter> parameters = projection.getParameters();

        Assertions.assertEquals(5, parameters.size());

        final Parameter param0 = parameters.get(0);

        Assertions.assertEquals("False_Easting", param0.getName().getSemantics());
        Assertions.assertEquals(500000., param0.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(241, param0.getName().first());
        Assertions.assertEquals(255, param0.getName().last());
        Assertions.assertEquals(61, param0.getName().order());

        Assertions.assertEquals(257, param0.getValue().first());
        Assertions.assertEquals(264, param0.getValue().last());
        Assertions.assertEquals(67, param0.getValue().order());

        Assertions.assertEquals(231, param0.first());
        Assertions.assertEquals(265, param0.last());
        Assertions.assertEquals(69, param0.order());

        final Parameter param1 = parameters.get(1);

        Assertions.assertEquals("False_Northing", param1.getName().getSemantics());
        Assertions.assertEquals(0., param1.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(277, param1.getName().first());
        Assertions.assertEquals(292, param1.getName().last());
        Assertions.assertEquals(73, param1.getName().order());

        Assertions.assertEquals(294, param1.getValue().first());
        Assertions.assertEquals(296, param1.getValue().last());
        Assertions.assertEquals(79, param1.getValue().order());

        Assertions.assertEquals(267, param1.first());
        Assertions.assertEquals(297, param1.last());
        Assertions.assertEquals(81, param1.order());

        final Parameter param2 = parameters.get(2);

        Assertions.assertEquals("Central_Meridian", param2.getName().getSemantics());
        Assertions.assertEquals(-123., param2.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(309, param2.getName().first());
        Assertions.assertEquals(326, param2.getName().last());
        Assertions.assertEquals(85, param2.getName().order());

        Assertions.assertEquals(328, param2.getValue().first());
        Assertions.assertEquals(333, param2.getValue().last());
        Assertions.assertEquals(92, param2.getValue().order());

        Assertions.assertEquals(299, param2.first());
        Assertions.assertEquals(334, param2.last());
        Assertions.assertEquals(94, param2.order());

        final Parameter param3 = parameters.get(3);

        Assertions.assertEquals("Scale_Factor", param3.getName().getSemantics());
        Assertions.assertEquals(.9996, param3.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(346, param3.getName().first());
        Assertions.assertEquals(359, param3.getName().last());
        Assertions.assertEquals(98, param3.getName().order());

        Assertions.assertEquals(361, param3.getValue().first());
        Assertions.assertEquals(366, param3.getValue().last());
        Assertions.assertEquals(104, param3.getValue().order());

        Assertions.assertEquals(336, param3.first());
        Assertions.assertEquals(367, param3.last());
        Assertions.assertEquals(106, param3.order());

        final Parameter param4 = parameters.get(4);

        Assertions.assertEquals("Latitude_of_Origin", param4.getName().getSemantics());
        Assertions.assertEquals(0., param4.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(379, param4.getName().first());
        Assertions.assertEquals(398, param4.getName().last());
        Assertions.assertEquals(110, param4.getName().order());

        Assertions.assertEquals(400, param4.getValue().first());
        Assertions.assertEquals(402, param4.getValue().last());
        Assertions.assertEquals(116, param4.getValue().order());

        Assertions.assertEquals(369, param4.first());
        Assertions.assertEquals(403, param4.last());
        Assertions.assertEquals(118, param4.order());

        final Unit unit = projcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Meter", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(410, unit.getName().first());
        Assertions.assertEquals(416, unit.getName().last());
        Assertions.assertEquals(123, unit.getName().order());

        Assertions.assertEquals(418, unit.getConversionFactor().first());
        Assertions.assertEquals(420, unit.getConversionFactor().last());
        Assertions.assertEquals(128, unit.getConversionFactor().order());

        Assertions.assertEquals(405, unit.first());
        Assertions.assertEquals(421, unit.last());
        Assertions.assertEquals(130, unit.order());
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
        Assertions.assertEquals(7, unit.getConversionFactor().order());

        Assertions.assertEquals(0, unit.first());
        Assertions.assertEquals(32, unit.last());
        Assertions.assertEquals(9, unit.order());
    }

    @Test
    public void prime_meridian_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("PRIMEM(\"Greenwitch\",0)", '(', ')');

        final PrimeMeridian primeMeridian = parser.primeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(7, primeMeridian.getName().first());
        Assertions.assertEquals(18, primeMeridian.getName().last());
        Assertions.assertEquals(2, primeMeridian.getName().order());

        Assertions.assertEquals(20, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(20, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(6, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(0, primeMeridian.first());
        Assertions.assertEquals(21, primeMeridian.last());
        Assertions.assertEquals(8, primeMeridian.order());
    }

    @Test
    public void parameter_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("PARAMETER(\"False_Easting\",500000.0)", '(', ')');

        final Parameter parameter = parser.projectionParameter();

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
    public void projection_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("PROJECTION(\"Transverse_Mercator\")", '(', ')');

        final Operation.MapProjection projection = parser.mapProjection();

        final Method.MapProjectionMethod method = projection.getMethod();

        Assertions.assertEquals("Transverse_Mercator", method.getName().getSemantics());

        Assertions.assertEquals(11, method.getName().first());
        Assertions.assertEquals(31, method.getName().last());
        Assertions.assertEquals(2, method.getName().order());

        Assertions.assertEquals(0, projection.first());
        Assertions.assertEquals(32, projection.last());
        Assertions.assertEquals(5, projection.order());
    }

    @Test
    public void spheroid_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("SPHEROID(\"GRS_1980\",6378137,298.257222101)", '(', ')');

        final Ellipsoid spheroid = parser.ellipsoid();

        Assertions.assertEquals(WktKeyword.SPHEROID, spheroid.getLabel().getSemantics());
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

        final Ellipsoid spheroid = parser.ellipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final GeodeticDatum datum = parser.geodeticDatum(new Token[2]);

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(6, datum.getName().first());
        Assertions.assertEquals(28, datum.getName().last());
        Assertions.assertEquals(2, datum.getName().order());

        Assertions.assertEquals(0, datum.first());
        Assertions.assertEquals(73, datum.last());
        Assertions.assertEquals(20, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final GeodeticCrs geoccs = parser.geodeticCrs();

        Assertions.assertEquals("GCS_North_American_1983", geoccs.getName().getSemantics());

        Assertions.assertEquals(7, geoccs.getName().first());
        Assertions.assertEquals(31, geoccs.getName().last());
        Assertions.assertEquals(2, geoccs.getName().order());

        Assertions.assertEquals(0, geoccs.first());
        Assertions.assertEquals(164, geoccs.last());
        Assertions.assertEquals(48, geoccs.order());

        final GeodeticDatum datum = geoccs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(129, datum.last());
        Assertions.assertEquals(34, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geoccs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(27, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(31, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(33, primeMeridian.order());

        final Unit unit = geoccs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(43, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(45, unit.order());
    }

    @Test
    public void geogcs_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree",0.0174532925199433))""", '(', ')');

        final GeodeticCrs geogcs = parser.geodeticCrs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(164, geogcs.last());
        Assertions.assertEquals(48, geogcs.order());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(129, datum.last());
        Assertions.assertEquals(34, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(27, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(31, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(33, primeMeridian.order());

        final Unit unit = geogcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(43, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(45, unit.order());
    }

    @SectionReference(type = SectionReferenceType.SECTION, id = "C.4.1") // pas de rétrocompatibilité ellipsoidal CS 3D
    @Test
    public void geogcs_3d_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree",0.0174532925199433),UNIT("Meter",1.0))""", '(', ')');

        final GeodeticCrs geogcs = parser.geodeticCrs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(182, geogcs.last());
        Assertions.assertEquals(59, geogcs.order());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(129, datum.last());
        Assertions.assertEquals(34, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(27, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(31, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(33, primeMeridian.order());

        final Unit angular = geogcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, angular.getName().first());
        Assertions.assertEquals(143, angular.getName().last());
        Assertions.assertEquals(38, angular.getName().order());

        Assertions.assertEquals(145, angular.getConversionFactor().first());
        Assertions.assertEquals(162, angular.getConversionFactor().last());
        Assertions.assertEquals(43, angular.getConversionFactor().order());

        Assertions.assertEquals(131, angular.first());
        Assertions.assertEquals(163, angular.last());
        Assertions.assertEquals(45, angular.order());

//        final Unit linear = geogcs.getLinearUnit();
//
//        Assertions.assertEquals("Meter", linear.getName().getSemantics());
//        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());
//
//        Assertions.assertEquals(170, linear.getName().first());
//        Assertions.assertEquals(176, linear.getName().last());
//        Assertions.assertEquals(50, linear.getName().order());
//
//        Assertions.assertEquals(178, linear.getConversionFactor().first());
//        Assertions.assertEquals(180, linear.getConversionFactor().last());
//        Assertions.assertEquals(56, linear.getConversionFactor().order());
//
//        Assertions.assertEquals(165, linear.first());
//        Assertions.assertEquals(181, linear.last());
//        Assertions.assertEquals(58, linear.order());
    }

    @Test
    public void projcs_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJCS("NAD_1983_UTM_Zone_10N",\
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree",0.0174532925199433)),\
                                              PROJECTION("Transverse_Mercator"),\
                                              PARAMETER("False_Easting",500000.0),\
                                              PARAMETER("False_Northing",0.0),\
                                              PARAMETER("Central_Meridian",-123.0),\
                                              PARAMETER("Scale_Factor",0.9996),\
                                              PARAMETER("Latitude_of_Origin",0.0),\
                                              UNIT("Meter",1.0))""", '(', ')');

        final DerivedCrs.ProjectedCrs projcs = parser.projectedCrs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(422, projcs.last());
        Assertions.assertEquals(133, projcs.order());

        final BaseGeodeticCrs geogcs = projcs.getBaseCrs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(38, geogcs.getName().first());
        Assertions.assertEquals(62, geogcs.getName().last());
        Assertions.assertEquals(6, geogcs.getName().order());

        Assertions.assertEquals(31, geogcs.first());
        Assertions.assertEquals(195, geogcs.last());
        Assertions.assertEquals(51, geogcs.order());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(70, datum.getName().first());
        Assertions.assertEquals(92, datum.getName().last());
        Assertions.assertEquals(10, datum.getName().order());

        Assertions.assertEquals(64, datum.first());
        Assertions.assertEquals(160, datum.last());
        Assertions.assertEquals(38, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(146, primeMeridian.getName().first());
        Assertions.assertEquals(157, primeMeridian.getName().last());
        Assertions.assertEquals(31, primeMeridian.getName().order());

        Assertions.assertEquals(159, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(159, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(35, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(139, primeMeridian.first());
        Assertions.assertEquals(160, primeMeridian.last());
        Assertions.assertEquals(37, primeMeridian.order());

        final Unit angular = geogcs.getUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(167, angular.getName().first());
        Assertions.assertEquals(174, angular.getName().last());
        Assertions.assertEquals(42, angular.getName().order());

        Assertions.assertEquals(176, angular.getConversionFactor().first());
        Assertions.assertEquals(193, angular.getConversionFactor().last());
        Assertions.assertEquals(47, angular.getConversionFactor().order());

        Assertions.assertEquals(162, angular.first());
        Assertions.assertEquals(194, angular.last());
        Assertions.assertEquals(49, angular.order());

//        final Unit linear = geogcs.getLinearUnit();
//
//        Assertions.assertEquals("Meter", linear.getName().getSemantics());
//        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());
//
//        Assertions.assertEquals(201, linear.getName().first());
//        Assertions.assertEquals(207, linear.getName().last());
//        Assertions.assertEquals(54, linear.getName().order());
//
//        Assertions.assertEquals(209, linear.getConversionFactor().first());
//        Assertions.assertEquals(211, linear.getConversionFactor().last());
//        Assertions.assertEquals(60, linear.getConversionFactor().order());
//
//        Assertions.assertEquals(196, linear.first());
//        Assertions.assertEquals(212, linear.last());
//        Assertions.assertEquals(62, linear.order());

        final Operation.MapProjection projection = projcs.getOperation();

        final Method.MapProjectionMethod method = projection.getMethod();

        Assertions.assertEquals("Transverse_Mercator", method.getName().getSemantics());

        Assertions.assertEquals(208, method.getName().first());
        Assertions.assertEquals(228, method.getName().last());
        Assertions.assertEquals(55, method.getName().order());

        Assertions.assertEquals(197, projection.first());
        Assertions.assertEquals(404, projection.last());
        Assertions.assertEquals(120, projection.order());

        final List<Parameter> parameters = projection.getParameters();

        Assertions.assertEquals(5, parameters.size());

        final Parameter param0 = parameters.get(0);

        Assertions.assertEquals("False_Easting", param0.getName().getSemantics());
        Assertions.assertEquals(500000., param0.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(241, param0.getName().first());
        Assertions.assertEquals(255, param0.getName().last());
        Assertions.assertEquals(61, param0.getName().order());

        Assertions.assertEquals(257, param0.getValue().first());
        Assertions.assertEquals(264, param0.getValue().last());
        Assertions.assertEquals(67, param0.getValue().order());

        Assertions.assertEquals(231, param0.first());
        Assertions.assertEquals(265, param0.last());
        Assertions.assertEquals(69, param0.order());

        final Parameter param1 = parameters.get(1);

        Assertions.assertEquals("False_Northing", param1.getName().getSemantics());
        Assertions.assertEquals(0., param1.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(277, param1.getName().first());
        Assertions.assertEquals(292, param1.getName().last());
        Assertions.assertEquals(73, param1.getName().order());

        Assertions.assertEquals(294, param1.getValue().first());
        Assertions.assertEquals(296, param1.getValue().last());
        Assertions.assertEquals(79, param1.getValue().order());

        Assertions.assertEquals(267, param1.first());
        Assertions.assertEquals(297, param1.last());
        Assertions.assertEquals(81, param1.order());

        final Parameter param2 = parameters.get(2);

        Assertions.assertEquals("Central_Meridian", param2.getName().getSemantics());
        Assertions.assertEquals(-123., param2.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(309, param2.getName().first());
        Assertions.assertEquals(326, param2.getName().last());
        Assertions.assertEquals(85, param2.getName().order());

        Assertions.assertEquals(328, param2.getValue().first());
        Assertions.assertEquals(333, param2.getValue().last());
        Assertions.assertEquals(92, param2.getValue().order());

        Assertions.assertEquals(299, param2.first());
        Assertions.assertEquals(334, param2.last());
        Assertions.assertEquals(94, param2.order());

        final Parameter param3 = parameters.get(3);

        Assertions.assertEquals("Scale_Factor", param3.getName().getSemantics());
        Assertions.assertEquals(.9996, param3.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(346, param3.getName().first());
        Assertions.assertEquals(359, param3.getName().last());
        Assertions.assertEquals(98, param3.getName().order());

        Assertions.assertEquals(361, param3.getValue().first());
        Assertions.assertEquals(366, param3.getValue().last());
        Assertions.assertEquals(104, param3.getValue().order());

        Assertions.assertEquals(336, param3.first());
        Assertions.assertEquals(367, param3.last());
        Assertions.assertEquals(106, param3.order());

        final Parameter param4 = parameters.get(4);

        Assertions.assertEquals("Latitude_of_Origin", param4.getName().getSemantics());
        Assertions.assertEquals(0., param4.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(379, param4.getName().first());
        Assertions.assertEquals(398, param4.getName().last());
        Assertions.assertEquals(110, param4.getName().order());

        Assertions.assertEquals(400, param4.getValue().first());
        Assertions.assertEquals(402, param4.getValue().last());
        Assertions.assertEquals(116, param4.getValue().order());

        Assertions.assertEquals(369, param4.first());
        Assertions.assertEquals(403, param4.last());
        Assertions.assertEquals(118, param4.order());

        final Unit unit = projcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Meter", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(410, unit.getName().first());
        Assertions.assertEquals(416, unit.getName().last());
        Assertions.assertEquals(123, unit.getName().order());

        Assertions.assertEquals(418, unit.getConversionFactor().first());
        Assertions.assertEquals(420, unit.getConversionFactor().last());
        Assertions.assertEquals(128, unit.getConversionFactor().order());

        Assertions.assertEquals(405, unit.first());
        Assertions.assertEquals(421, unit.last());
        Assertions.assertEquals(130, unit.order());
    }

    @Test
    public void spatial_reference_system_test_2_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree",0.0174532925199433))""", '(', ')');

        final GeodeticCrs geoccs = (GeodeticCrs) parser.coordinateReferenceSystem();

        Assertions.assertEquals("GCS_North_American_1983", geoccs.getName().getSemantics());

        Assertions.assertEquals(7, geoccs.getName().first());
        Assertions.assertEquals(31, geoccs.getName().last());
        Assertions.assertEquals(2, geoccs.getName().order());

        Assertions.assertEquals(0, geoccs.first());
        Assertions.assertEquals(164, geoccs.last());
        Assertions.assertEquals(48, geoccs.order());

        final GeodeticDatum datum = geoccs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(129, datum.last());
        Assertions.assertEquals(34, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geoccs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(27, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(31, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(33, primeMeridian.order());

        final Unit unit = geoccs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(43, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(45, unit.order());
    }

    @Test
    public void spatial_reference_system_test_2_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),UNIT("Degree",0.0174532925199433))""", '(', ')');

        final GeodeticCrs geogcs = (GeodeticCrs) parser.coordinateReferenceSystem();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(164, geogcs.last());
        Assertions.assertEquals(48, geogcs.order());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(129, datum.last());
        Assertions.assertEquals(34, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(27, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(31, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(33, primeMeridian.order());

        final Unit unit = geogcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, unit.getName().first());
        Assertions.assertEquals(143, unit.getName().last());
        Assertions.assertEquals(38, unit.getName().order());

        Assertions.assertEquals(145, unit.getConversionFactor().first());
        Assertions.assertEquals(162, unit.getConversionFactor().last());
        Assertions.assertEquals(43, unit.getConversionFactor().order());

        Assertions.assertEquals(131, unit.first());
        Assertions.assertEquals(163, unit.last());
        Assertions.assertEquals(45, unit.order());
    }

    @Test
    public void spatial_reference_system_test_2_3() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree\",0.0174532925199433),UNIT("Meter",1.0))""", '(', ')');


        final GeodeticCrs geogcs = (GeodeticCrs) parser.coordinateReferenceSystem();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(7, geogcs.getName().first());
        Assertions.assertEquals(31, geogcs.getName().last());
        Assertions.assertEquals(2, geogcs.getName().order());

        Assertions.assertEquals(0, geogcs.first());
        Assertions.assertEquals(182, geogcs.last());
        Assertions.assertEquals(59, geogcs.order());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(33 + 6, datum.getName().first());
        Assertions.assertEquals(33 + 28, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(33 + 0, datum.first());
        Assertions.assertEquals(129, datum.last());
        Assertions.assertEquals(34, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(115, primeMeridian.getName().first());
        Assertions.assertEquals(126, primeMeridian.getName().last());
        Assertions.assertEquals(27, primeMeridian.getName().order());

        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(128, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(31, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(108, primeMeridian.first());
        Assertions.assertEquals(129, primeMeridian.last());
        Assertions.assertEquals(33, primeMeridian.order());

        final Unit angular = geogcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(136, angular.getName().first());
        Assertions.assertEquals(143, angular.getName().last());
        Assertions.assertEquals(38, angular.getName().order());

        Assertions.assertEquals(145, angular.getConversionFactor().first());
        Assertions.assertEquals(162, angular.getConversionFactor().last());
        Assertions.assertEquals(43, angular.getConversionFactor().order());

        Assertions.assertEquals(131, angular.first());
        Assertions.assertEquals(163, angular.last());
        Assertions.assertEquals(45, angular.order());

//        final Unit linear = geogcs.getLinearUnit();
//
//        Assertions.assertEquals("Meter", linear.getName().getSemantics());
//        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());
//
//        Assertions.assertEquals(170, linear.getName().first());
//        Assertions.assertEquals(176, linear.getName().last());
//        Assertions.assertEquals(50, linear.getName().order());
//
//        Assertions.assertEquals(178, linear.getConversionFactor().first());
//        Assertions.assertEquals(180, linear.getConversionFactor().last());
//        Assertions.assertEquals(56, linear.getConversionFactor().order());
//
//        Assertions.assertEquals(165, linear.first());
//        Assertions.assertEquals(181, linear.last());
//        Assertions.assertEquals(58, linear.order());
    }

    @Test
    public void spatial_reference_system_test_2_4() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJCS("NAD_1983_UTM_Zone_10N",\
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree",0.0174532925199433)),\
                                              PROJECTION("Transverse_Mercator"),\
                                              PARAMETER("False_Easting",500000.0),\
                                              PARAMETER("False_Northing",0.0),\
                                              PARAMETER("Central_Meridian",-123.0),\
                                              PARAMETER("Scale_Factor",0.9996),\
                                              PARAMETER("Latitude_of_Origin",0.0),\
                                              UNIT("Meter",1.0))""", '(', ')');

        final DerivedCrs.ProjectedCrs projcs = (DerivedCrs.ProjectedCrs) parser.coordinateReferenceSystem();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(422, projcs.last());
        Assertions.assertEquals(133, projcs.order());

        final BaseGeodeticCrs geogcs = projcs.getBaseCrs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        Assertions.assertEquals(38, geogcs.getName().first());
        Assertions.assertEquals(62, geogcs.getName().last());
        Assertions.assertEquals(6, geogcs.getName().order());

        Assertions.assertEquals(31, geogcs.first());
        Assertions.assertEquals(195, geogcs.last());
        Assertions.assertEquals(51, geogcs.order());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        Assertions.assertEquals(70, datum.getName().first());
        Assertions.assertEquals(92, datum.getName().last());
        Assertions.assertEquals(10, datum.getName().order());

        Assertions.assertEquals(64, datum.first());
        Assertions.assertEquals(160, datum.last());
        Assertions.assertEquals(38, datum.order());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        Assertions.assertEquals(146, primeMeridian.getName().first());
        Assertions.assertEquals(157, primeMeridian.getName().last());
        Assertions.assertEquals(31, primeMeridian.getName().order());

        Assertions.assertEquals(159, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(159, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(35, primeMeridian.getIrmLongitude().order());

        Assertions.assertEquals(139, primeMeridian.first());
        Assertions.assertEquals(160, primeMeridian.last());
        Assertions.assertEquals(37, primeMeridian.order());

        final Unit angular = geogcs.getUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(167, angular.getName().first());
        Assertions.assertEquals(174, angular.getName().last());
        Assertions.assertEquals(42, angular.getName().order());

        Assertions.assertEquals(176, angular.getConversionFactor().first());
        Assertions.assertEquals(193, angular.getConversionFactor().last());
        Assertions.assertEquals(47, angular.getConversionFactor().order());

        Assertions.assertEquals(162, angular.first());
        Assertions.assertEquals(194, angular.last());
        Assertions.assertEquals(49, angular.order());

//        final Unit linear = geogcs.getLinearUnit();
//
//        Assertions.assertEquals("Meter", linear.getName().getSemantics());
//        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());
//
//        Assertions.assertEquals(201, linear.getName().first());
//        Assertions.assertEquals(207, linear.getName().last());
//        Assertions.assertEquals(54, linear.getName().order());
//
//        Assertions.assertEquals(209, linear.getConversionFactor().first());
//        Assertions.assertEquals(211, linear.getConversionFactor().last());
//        Assertions.assertEquals(60, linear.getConversionFactor().order());
//
//        Assertions.assertEquals(196, linear.first());
//        Assertions.assertEquals(212, linear.last());
//        Assertions.assertEquals(62, linear.order());

        final Operation.MapProjection projection = projcs.getOperation();

        final Method.MapProjectionMethod method = projection.getMethod();

        Assertions.assertEquals("Transverse_Mercator", method.getName().getSemantics());

        Assertions.assertEquals(208, method.getName().first());
        Assertions.assertEquals(228, method.getName().last());
        Assertions.assertEquals(55, method.getName().order());

        Assertions.assertEquals(197, projection.first());
        Assertions.assertEquals(404, projection.last());
        Assertions.assertEquals(120, projection.order());

        final List<Parameter> parameters = projection.getParameters();

        Assertions.assertEquals(5, parameters.size());

        final Parameter param0 = parameters.get(0);

        Assertions.assertEquals("False_Easting", param0.getName().getSemantics());
        Assertions.assertEquals(500000., param0.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(241, param0.getName().first());
        Assertions.assertEquals(255, param0.getName().last());
        Assertions.assertEquals(61, param0.getName().order());

        Assertions.assertEquals(257, param0.getValue().first());
        Assertions.assertEquals(264, param0.getValue().last());
        Assertions.assertEquals(67, param0.getValue().order());

        Assertions.assertEquals(231, param0.first());
        Assertions.assertEquals(265, param0.last());
        Assertions.assertEquals(69, param0.order());

        final Parameter param1 = parameters.get(1);

        Assertions.assertEquals("False_Northing", param1.getName().getSemantics());
        Assertions.assertEquals(0., param1.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(277, param1.getName().first());
        Assertions.assertEquals(292, param1.getName().last());
        Assertions.assertEquals(73, param1.getName().order());

        Assertions.assertEquals(294, param1.getValue().first());
        Assertions.assertEquals(296, param1.getValue().last());
        Assertions.assertEquals(79, param1.getValue().order());

        Assertions.assertEquals(267, param1.first());
        Assertions.assertEquals(297, param1.last());
        Assertions.assertEquals(81, param1.order());

        final Parameter param2 = parameters.get(2);

        Assertions.assertEquals("Central_Meridian", param2.getName().getSemantics());
        Assertions.assertEquals(-123., param2.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(309, param2.getName().first());
        Assertions.assertEquals(326, param2.getName().last());
        Assertions.assertEquals(85, param2.getName().order());

        Assertions.assertEquals(328, param2.getValue().first());
        Assertions.assertEquals(333, param2.getValue().last());
        Assertions.assertEquals(92, param2.getValue().order());

        Assertions.assertEquals(299, param2.first());
        Assertions.assertEquals(334, param2.last());
        Assertions.assertEquals(94, param2.order());

        final Parameter param3 = parameters.get(3);

        Assertions.assertEquals("Scale_Factor", param3.getName().getSemantics());
        Assertions.assertEquals(.9996, param3.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(346, param3.getName().first());
        Assertions.assertEquals(359, param3.getName().last());
        Assertions.assertEquals(98, param3.getName().order());

        Assertions.assertEquals(361, param3.getValue().first());
        Assertions.assertEquals(366, param3.getValue().last());
        Assertions.assertEquals(104, param3.getValue().order());

        Assertions.assertEquals(336, param3.first());
        Assertions.assertEquals(367, param3.last());
        Assertions.assertEquals(106, param3.order());

        final Parameter param4 = parameters.get(4);

        Assertions.assertEquals("Latitude_of_Origin", param4.getName().getSemantics());
        Assertions.assertEquals(0., param4.getValue().getSemantics().doubleValue());

        Assertions.assertEquals(379, param4.getName().first());
        Assertions.assertEquals(398, param4.getName().last());
        Assertions.assertEquals(110, param4.getName().order());

        Assertions.assertEquals(400, param4.getValue().first());
        Assertions.assertEquals(402, param4.getValue().last());
        Assertions.assertEquals(116, param4.getValue().order());

        Assertions.assertEquals(369, param4.first());
        Assertions.assertEquals(403, param4.last());
        Assertions.assertEquals(118, param4.order());

        final Unit unit = projcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Meter", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(410, unit.getName().first());
        Assertions.assertEquals(416, unit.getName().last());
        Assertions.assertEquals(123, unit.getName().order());

        Assertions.assertEquals(418, unit.getConversionFactor().first());
        Assertions.assertEquals(420, unit.getConversionFactor().last());
        Assertions.assertEquals(128, unit.getConversionFactor().order());

        Assertions.assertEquals(405, unit.first());
        Assertions.assertEquals(421, unit.last());
        Assertions.assertEquals(130, unit.order());
    }

    @Test // fichier avec des crochets comme délimiteurs
    public void projcs_test_3a() throws LanguageException, IOException {

        try (var scanner = new DefaultStreamScanner(new BufferedReader(new InputStreamReader(
                WktParserBracketTest.class.getResourceAsStream("example3a"))))) {

            final WktLexer lexer = new WktLexer(scanner, new ArrayList<>(), LD, RD);
            lexer.initialize();
            final WktParser parser = new WktParser(lexer);

            final DerivedCrs.ProjectedCrs projcs = parser.projectedCrs();

            Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

            Assertions.assertEquals(7, projcs.getName().first());
            Assertions.assertEquals(29, projcs.getName().last());
            Assertions.assertEquals(2, projcs.getName().order());

            Assertions.assertEquals(0, projcs.first());
            Assertions.assertEquals(422, projcs.last());
            Assertions.assertEquals(133, projcs.order());

            final BaseGeodeticCrs geogcs = projcs.getBaseCrs();

            Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

            Assertions.assertEquals(38, geogcs.getName().first());
            Assertions.assertEquals(62, geogcs.getName().last());
            Assertions.assertEquals(6, geogcs.getName().order());

            Assertions.assertEquals(31, geogcs.first());
            Assertions.assertEquals(195, geogcs.last());
            Assertions.assertEquals(51, geogcs.order());

            final GeodeticDatum datum = geogcs.getDatum();

            Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

            Assertions.assertEquals(70, datum.getName().first());
            Assertions.assertEquals(92, datum.getName().last());
            Assertions.assertEquals(10, datum.getName().order());

            Assertions.assertEquals(64, datum.first());
            Assertions.assertEquals(160, datum.last());
            Assertions.assertEquals(38, datum.order());

            final Ellipsoid spheroid = datum.getEllipsoid();

            Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

            final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

            Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
            Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

            Assertions.assertEquals(146, primeMeridian.getName().first());
            Assertions.assertEquals(157, primeMeridian.getName().last());
            Assertions.assertEquals(31, primeMeridian.getName().order());

            Assertions.assertEquals(159, primeMeridian.getIrmLongitude().first());
            Assertions.assertEquals(159, primeMeridian.getIrmLongitude().last());
            Assertions.assertEquals(35, primeMeridian.getIrmLongitude().order());

            Assertions.assertEquals(139, primeMeridian.first());
            Assertions.assertEquals(160, primeMeridian.last());
            Assertions.assertEquals(37, primeMeridian.order());

            final Unit angular = geogcs.getUnit();

            Assertions.assertEquals("Degree", angular.getName().getSemantics());
            Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

            Assertions.assertEquals(167, angular.getName().first());
            Assertions.assertEquals(174, angular.getName().last());
            Assertions.assertEquals(42, angular.getName().order());

            Assertions.assertEquals(176, angular.getConversionFactor().first());
            Assertions.assertEquals(193, angular.getConversionFactor().last());
            Assertions.assertEquals(47, angular.getConversionFactor().order());

            Assertions.assertEquals(162, angular.first());
            Assertions.assertEquals(194, angular.last());
            Assertions.assertEquals(49, angular.order());

    //        final Unit linear = geogcs.getLinearUnit();
    //
    //        Assertions.assertEquals("Meter", linear.getName().getSemantics());
    //        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());
    //
    //        Assertions.assertEquals(201, linear.getName().first());
    //        Assertions.assertEquals(207, linear.getName().last());
    //        Assertions.assertEquals(54, linear.getName().order());
    //
    //        Assertions.assertEquals(209, linear.getConversionFactor().first());
    //        Assertions.assertEquals(211, linear.getConversionFactor().last());
    //        Assertions.assertEquals(60, linear.getConversionFactor().order());
    //
    //        Assertions.assertEquals(196, linear.first());
    //        Assertions.assertEquals(212, linear.last());
    //        Assertions.assertEquals(62, linear.order());

            final Operation.MapProjection projection = projcs.getOperation();

            final Method.MapProjectionMethod method = projection.getMethod();

            Assertions.assertEquals("Transverse_Mercator", method.getName().getSemantics());

            Assertions.assertEquals(208, method.getName().first());
            Assertions.assertEquals(228, method.getName().last());
            Assertions.assertEquals(55, method.getName().order());

            Assertions.assertEquals(197, projection.first());
            Assertions.assertEquals(404, projection.last());
            Assertions.assertEquals(120, projection.order());

            final List<Parameter> parameters = projection.getParameters();

            Assertions.assertEquals(5, parameters.size());

            final Parameter param0 = parameters.get(0);

            Assertions.assertEquals("False_Easting", param0.getName().getSemantics());
            Assertions.assertEquals(500000., param0.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(241, param0.getName().first());
            Assertions.assertEquals(255, param0.getName().last());
            Assertions.assertEquals(61, param0.getName().order());

            Assertions.assertEquals(257, param0.getValue().first());
            Assertions.assertEquals(264, param0.getValue().last());
            Assertions.assertEquals(67, param0.getValue().order());

            Assertions.assertEquals(231, param0.first());
            Assertions.assertEquals(265, param0.last());
            Assertions.assertEquals(69, param0.order());

            final Parameter param1 = parameters.get(1);

            Assertions.assertEquals("False_Northing", param1.getName().getSemantics());
            Assertions.assertEquals(0., param1.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(277, param1.getName().first());
            Assertions.assertEquals(292, param1.getName().last());
            Assertions.assertEquals(73, param1.getName().order());

            Assertions.assertEquals(294, param1.getValue().first());
            Assertions.assertEquals(296, param1.getValue().last());
            Assertions.assertEquals(79, param1.getValue().order());

            Assertions.assertEquals(267, param1.first());
            Assertions.assertEquals(297, param1.last());
            Assertions.assertEquals(81, param1.order());

            final Parameter param2 = parameters.get(2);

            Assertions.assertEquals("Central_Meridian", param2.getName().getSemantics());
            Assertions.assertEquals(-123., param2.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(309, param2.getName().first());
            Assertions.assertEquals(326, param2.getName().last());
            Assertions.assertEquals(85, param2.getName().order());

            Assertions.assertEquals(328, param2.getValue().first());
            Assertions.assertEquals(333, param2.getValue().last());
            Assertions.assertEquals(92, param2.getValue().order());

            Assertions.assertEquals(299, param2.first());
            Assertions.assertEquals(334, param2.last());
            Assertions.assertEquals(94, param2.order());

            final Parameter param3 = parameters.get(3);

            Assertions.assertEquals("Scale_Factor", param3.getName().getSemantics());
            Assertions.assertEquals(.9996, param3.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(346, param3.getName().first());
            Assertions.assertEquals(359, param3.getName().last());
            Assertions.assertEquals(98, param3.getName().order());

            Assertions.assertEquals(361, param3.getValue().first());
            Assertions.assertEquals(366, param3.getValue().last());
            Assertions.assertEquals(104, param3.getValue().order());

            Assertions.assertEquals(336, param3.first());
            Assertions.assertEquals(367, param3.last());
            Assertions.assertEquals(106, param3.order());

            final Parameter param4 = parameters.get(4);

            Assertions.assertEquals("Latitude_of_Origin", param4.getName().getSemantics());
            Assertions.assertEquals(0., param4.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(379, param4.getName().first());
            Assertions.assertEquals(398, param4.getName().last());
            Assertions.assertEquals(110, param4.getName().order());

            Assertions.assertEquals(400, param4.getValue().first());
            Assertions.assertEquals(402, param4.getValue().last());
            Assertions.assertEquals(116, param4.getValue().order());

            Assertions.assertEquals(369, param4.first());
            Assertions.assertEquals(403, param4.last());
            Assertions.assertEquals(118, param4.order());

            final Unit unit = projcs.getCoordinateSystem().getUnit();

            Assertions.assertEquals("Meter", unit.getName().getSemantics());
            Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

            Assertions.assertEquals(410, unit.getName().first());
            Assertions.assertEquals(416, unit.getName().last());
            Assertions.assertEquals(123, unit.getName().order());

            Assertions.assertEquals(418, unit.getConversionFactor().first());
            Assertions.assertEquals(420, unit.getConversionFactor().last());
            Assertions.assertEquals(128, unit.getConversionFactor().order());

            Assertions.assertEquals(405, unit.first());
            Assertions.assertEquals(421, unit.last());
            Assertions.assertEquals(130, unit.order());
        }
    }

    @Test // fichier avec des parenthèses comme délimiteurs
    public void projcs_test_3b() throws LanguageException, IOException {

        try (var scanner = new DefaultStreamScanner(new BufferedReader(new InputStreamReader(
                WktParserBracketTest.class.getResourceAsStream("example3b"))))) {

            final WktLexer lexer = new WktLexer(scanner, new ArrayList<>(), '(', ')');
            lexer.initialize();
            final WktParser parser = new WktParser(lexer);

            final DerivedCrs.ProjectedCrs projcs = parser.projectedCrs();

            Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

            Assertions.assertEquals(7, projcs.getName().first());
            Assertions.assertEquals(29, projcs.getName().last());
            Assertions.assertEquals(2, projcs.getName().order());

            Assertions.assertEquals(0, projcs.first());
            Assertions.assertEquals(422, projcs.last());
            Assertions.assertEquals(133, projcs.order());

            final BaseGeodeticCrs geogcs = projcs.getBaseCrs();

            Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

            Assertions.assertEquals(38, geogcs.getName().first());
            Assertions.assertEquals(62, geogcs.getName().last());
            Assertions.assertEquals(6, geogcs.getName().order());

            Assertions.assertEquals(31, geogcs.first());
            Assertions.assertEquals(195, geogcs.last());
            Assertions.assertEquals(51, geogcs.order());

            final GeodeticDatum datum = geogcs.getDatum();

            Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

            Assertions.assertEquals(70, datum.getName().first());
            Assertions.assertEquals(92, datum.getName().last());
            Assertions.assertEquals(10, datum.getName().order());

            Assertions.assertEquals(64, datum.first());
            Assertions.assertEquals(160, datum.last());
            Assertions.assertEquals(38, datum.order());

            final Ellipsoid spheroid = datum.getEllipsoid();

            Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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

            final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

            Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
            Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

            Assertions.assertEquals(146, primeMeridian.getName().first());
            Assertions.assertEquals(157, primeMeridian.getName().last());
            Assertions.assertEquals(31, primeMeridian.getName().order());

            Assertions.assertEquals(159, primeMeridian.getIrmLongitude().first());
            Assertions.assertEquals(159, primeMeridian.getIrmLongitude().last());
            Assertions.assertEquals(35, primeMeridian.getIrmLongitude().order());

            Assertions.assertEquals(139, primeMeridian.first());
            Assertions.assertEquals(160, primeMeridian.last());
            Assertions.assertEquals(37, primeMeridian.order());

            final Unit angular = geogcs.getUnit();

            Assertions.assertEquals("Degree", angular.getName().getSemantics());
            Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

            Assertions.assertEquals(167, angular.getName().first());
            Assertions.assertEquals(174, angular.getName().last());
            Assertions.assertEquals(42, angular.getName().order());

            Assertions.assertEquals(176, angular.getConversionFactor().first());
            Assertions.assertEquals(193, angular.getConversionFactor().last());
            Assertions.assertEquals(47, angular.getConversionFactor().order());

            Assertions.assertEquals(162, angular.first());
            Assertions.assertEquals(194, angular.last());
            Assertions.assertEquals(49, angular.order());

    //        final Unit linear = geogcs.getLinearUnit();
    //
    //        Assertions.assertEquals("Meter", linear.getName().getSemantics());
    //        Assertions.assertEquals(1., linear.getConversionFactor().getSemantics().doubleValue());
    //
    //        Assertions.assertEquals(201, linear.getName().first());
    //        Assertions.assertEquals(207, linear.getName().last());
    //        Assertions.assertEquals(54, linear.getName().order());
    //
    //        Assertions.assertEquals(209, linear.getConversionFactor().first());
    //        Assertions.assertEquals(211, linear.getConversionFactor().last());
    //        Assertions.assertEquals(60, linear.getConversionFactor().order());
    //
    //        Assertions.assertEquals(196, linear.first());
    //        Assertions.assertEquals(212, linear.last());
    //        Assertions.assertEquals(62, linear.order());

            final Operation.MapProjection projection = projcs.getOperation();

            final Method.MapProjectionMethod method = projection.getMethod();

            Assertions.assertEquals("Transverse_Mercator", method.getName().getSemantics());

            Assertions.assertEquals(208, method.getName().first());
            Assertions.assertEquals(228, method.getName().last());
            Assertions.assertEquals(55, method.getName().order());

            Assertions.assertEquals(197, projection.first());
            Assertions.assertEquals(404, projection.last());
            Assertions.assertEquals(120, projection.order());

            final List<Parameter> parameters = projection.getParameters();

            Assertions.assertEquals(5, parameters.size());

            final Parameter param0 = parameters.get(0);

            Assertions.assertEquals("False_Easting", param0.getName().getSemantics());
            Assertions.assertEquals(500000., param0.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(241, param0.getName().first());
            Assertions.assertEquals(255, param0.getName().last());
            Assertions.assertEquals(61, param0.getName().order());

            Assertions.assertEquals(257, param0.getValue().first());
            Assertions.assertEquals(264, param0.getValue().last());
            Assertions.assertEquals(67, param0.getValue().order());

            Assertions.assertEquals(231, param0.first());
            Assertions.assertEquals(265, param0.last());
            Assertions.assertEquals(69, param0.order());

            final Parameter param1 = parameters.get(1);

            Assertions.assertEquals("False_Northing", param1.getName().getSemantics());
            Assertions.assertEquals(0., param1.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(277, param1.getName().first());
            Assertions.assertEquals(292, param1.getName().last());
            Assertions.assertEquals(73, param1.getName().order());

            Assertions.assertEquals(294, param1.getValue().first());
            Assertions.assertEquals(296, param1.getValue().last());
            Assertions.assertEquals(79, param1.getValue().order());

            Assertions.assertEquals(267, param1.first());
            Assertions.assertEquals(297, param1.last());
            Assertions.assertEquals(81, param1.order());

            final Parameter param2 = parameters.get(2);

            Assertions.assertEquals("Central_Meridian", param2.getName().getSemantics());
            Assertions.assertEquals(-123., param2.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(309, param2.getName().first());
            Assertions.assertEquals(326, param2.getName().last());
            Assertions.assertEquals(85, param2.getName().order());

            Assertions.assertEquals(328, param2.getValue().first());
            Assertions.assertEquals(333, param2.getValue().last());
            Assertions.assertEquals(92, param2.getValue().order());

            Assertions.assertEquals(299, param2.first());
            Assertions.assertEquals(334, param2.last());
            Assertions.assertEquals(94, param2.order());

            final Parameter param3 = parameters.get(3);

            Assertions.assertEquals("Scale_Factor", param3.getName().getSemantics());
            Assertions.assertEquals(.9996, param3.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(346, param3.getName().first());
            Assertions.assertEquals(359, param3.getName().last());
            Assertions.assertEquals(98, param3.getName().order());

            Assertions.assertEquals(361, param3.getValue().first());
            Assertions.assertEquals(366, param3.getValue().last());
            Assertions.assertEquals(104, param3.getValue().order());

            Assertions.assertEquals(336, param3.first());
            Assertions.assertEquals(367, param3.last());
            Assertions.assertEquals(106, param3.order());

            final Parameter param4 = parameters.get(4);

            Assertions.assertEquals("Latitude_of_Origin", param4.getName().getSemantics());
            Assertions.assertEquals(0., param4.getValue().getSemantics().doubleValue());

            Assertions.assertEquals(379, param4.getName().first());
            Assertions.assertEquals(398, param4.getName().last());
            Assertions.assertEquals(110, param4.getName().order());

            Assertions.assertEquals(400, param4.getValue().first());
            Assertions.assertEquals(402, param4.getValue().last());
            Assertions.assertEquals(116, param4.getValue().order());

            Assertions.assertEquals(369, param4.first());
            Assertions.assertEquals(403, param4.last());
            Assertions.assertEquals(118, param4.order());

            final Unit unit = projcs.getCoordinateSystem().getUnit();

            Assertions.assertEquals("Meter", unit.getName().getSemantics());
            Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

            Assertions.assertEquals(410, unit.getName().first());
            Assertions.assertEquals(416, unit.getName().last());
            Assertions.assertEquals(123, unit.getName().order());

            Assertions.assertEquals(418, unit.getConversionFactor().first());
            Assertions.assertEquals(420, unit.getConversionFactor().last());
            Assertions.assertEquals(128, unit.getConversionFactor().order());

            Assertions.assertEquals(405, unit.first());
            Assertions.assertEquals(421, unit.last());
            Assertions.assertEquals(130, unit.order());
        }
    }
/*
    @Test // vérification du support de l'indentation
    public void projcs_test_3c() throws LanguageException, IOException {

        try (var scanner = new DefaultStreamScanner(new BufferedReader(new InputStreamReader(
                WktParserBracketTest.class.getResourceAsStream("example3c"))))) {

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

            Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
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
*/
}
