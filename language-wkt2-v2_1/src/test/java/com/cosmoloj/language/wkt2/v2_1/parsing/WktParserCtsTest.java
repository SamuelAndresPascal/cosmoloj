package com.cosmoloj.language.wkt2.v2_1.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.parsing.DefaultStreamScanner;
import com.cosmoloj.language.wkt2.v2_1.expression.SpatialTemporalAxis;
import com.cosmoloj.language.wkt2.v2_1.expression.BaseGeodeticCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.SpatialCoordinateSystem;
import com.cosmoloj.language.wkt2.v2_1.expression.DerivedCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.Ellipsoid;
import com.cosmoloj.language.wkt2.v2_1.expression.GeodeticCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.GeodeticDatum;
import com.cosmoloj.language.wkt2.v2_1.expression.Identifier;
import com.cosmoloj.language.wkt2.v2_1.expression.Method;
import com.cosmoloj.language.wkt2.v2_1.expression.NameAndAnchorDatum;
import com.cosmoloj.language.wkt2.v2_1.expression.Operation;
import com.cosmoloj.language.wkt2.v2_1.expression.Parameter;
import com.cosmoloj.language.wkt2.v2_1.expression.PrimeMeridian;
import com.cosmoloj.language.wkt2.v2_1.expression.SimpleCrsShell;
import com.cosmoloj.language.wkt2.v2_1.expression.Unit;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.Direction;
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
public class WktParserCtsTest {

    @Test
    public void authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              AUTHORITY["EPSG","8901"]""");

        final Identifier authority = parser.identifier();

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());
        Assertions.assertEquals("8901", authority.getId().getSemantics());

        Assertions.assertEquals(10, authority.getName().first());
        Assertions.assertEquals(15, authority.getName().last());
        Assertions.assertEquals(2, authority.getName().order());

        Assertions.assertEquals(17, authority.getId().first());
        Assertions.assertEquals(22, authority.getId().last());
        Assertions.assertEquals(4, authority.getId().order());

        Assertions.assertEquals(0, authority.first());
        Assertions.assertEquals(23, authority.last());
        Assertions.assertEquals(6, authority.order());
    }
/*
    @Test
    public void param_mt_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PARAM_MT["classification name",\
                                              PARAMETER["first",0.0174532925199433],\
                                              PARAMETER["second",0.174532925199433]]""");

        final ParamMt param_mt = parser.paramMt();

        Assertions.assertEquals("classification name", param_mt.getName().getSemantics());

        final List<Parameter> parameters = param_mt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());
    }

    @Test
    public void inverse_mt_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              INVERSE_MT[PARAM_MT["classification name",\
                                              PARAMETER["first",0.0174532925199433],\
                                              PARAMETER["second",0.174532925199433]]]""");

        final InverseMt inverse_mt = parser.invMt();

        Assertions.assertTrue(inverse_mt.getTransform() instanceof ParamMt);

        final ParamMt param_mt = (ParamMt) inverse_mt.getTransform();

        Assertions.assertEquals("classification name", param_mt.getName().getSemantics());

        final List<Parameter> parameters = param_mt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());
    }

    @Test
    public void passthrough_mt_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PASSTHROUGH_MT[4,INVERSE_MT[PARAM_MT["classification name",\
                                              PARAMETER["first",0.0174532925199433],\
                                              PARAMETER["second",0.174532925199433]]]]""");

        final PassthroughMt passthrough_mt = parser.passthroughMt();

        Assertions.assertEquals(4, passthrough_mt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthrough_mt.getTransform() instanceof InverseMt);

        final InverseMt inverse_mt = (InverseMt) passthrough_mt.getTransform();

        Assertions.assertTrue(inverse_mt.getTransform() instanceof ParamMt);

        final ParamMt param_mt = (ParamMt) inverse_mt.getTransform();

        Assertions.assertEquals("classification name", param_mt.getName().getSemantics());

        final List<Parameter> parameters = param_mt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());
    }

    @Test
    public void concat_mt_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              CONCAT_MT[PASSTHROUGH_MT[4,INVERSE_MT[PARAM_MT["classification name",\
                                              PARAMETER["first",0.0174532925199433],\
                                              PARAMETER["second",0.174532925199433]]]],\
                                              CONCAT_MT[PARAM_MT["second name"]]]""");

        final ConcatMt concat_mt = parser.concatMt();

        Assertions.assertEquals(2, concat_mt.getTransforms().size());
        Assertions.assertTrue(concat_mt.getTransforms().get(0) instanceof PassthroughMt);
        Assertions.assertTrue(concat_mt.getTransforms().get(1) instanceof ConcatMt);

        final PassthroughMt passthrough_mt = (PassthroughMt) concat_mt.getTransforms().get(0);

        Assertions.assertEquals(4, passthrough_mt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthrough_mt.getTransform() instanceof InverseMt);

        final InverseMt inverse_mt = (InverseMt) passthrough_mt.getTransform();

        Assertions.assertTrue(inverse_mt.getTransform() instanceof ParamMt);

        final ParamMt param_mt = (ParamMt) inverse_mt.getTransform();

        Assertions.assertEquals("classification name", param_mt.getName().getSemantics());

        final List<Parameter> parameters = param_mt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());

        final ConcatMt incl_concat_mt = (ConcatMt) concat_mt.getTransforms().get(1);
        Assertions.assertEquals(1, incl_concat_mt.getTransforms().size());
        Assertions.assertTrue(incl_concat_mt.getTransforms().get(0) instanceof ParamMt);

        final ParamMt incl_param_mt = (ParamMt) incl_concat_mt.getTransforms().get(0);

        Assertions.assertEquals("second name", incl_param_mt.getName().getSemantics());

        Assertions.assertTrue(incl_param_mt.getParameters().isEmpty());
    }
*/
    @Test
    public void unit_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              UNIT["Degree",0.0174532925199433]""");

        final Unit unit = parser.unit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(5, unit.getName().first());
        Assertions.assertEquals(12, unit.getName().last());
        Assertions.assertEquals(2, unit.getName().order());

        Assertions.assertEquals(14, unit.getConversionFactor().first());
        Assertions.assertEquals(31, unit.getConversionFactor().last());
        Assertions.assertEquals(7, unit.getConversionFactor().order());

        Assertions.assertTrue(unit.getIdentifiers().isEmpty());

        Assertions.assertEquals(0, unit.first());
        Assertions.assertEquals(32, unit.last());
        Assertions.assertEquals(9, unit.order());
    }

    @Test
    public void unit_authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              UNIT["metre",1,AUTHORITY["EPSG","9001"]]""");

        final Unit unit = parser.unit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals("EPSG", unit.getIdentifiers().get(0).getName().getSemantics());
        Assertions.assertEquals("9001", unit.getIdentifiers().get(0).getId().getSemantics());

        Assertions.assertEquals(5, unit.getName().first());
        Assertions.assertEquals(11, unit.getName().last());
        Assertions.assertEquals(2, unit.getName().order());

        Assertions.assertEquals(13, unit.getConversionFactor().first());
        Assertions.assertEquals(13, unit.getConversionFactor().last());
        Assertions.assertEquals(5, unit.getConversionFactor().order());

        final Identifier authority = unit.getIdentifiers().get(0);
        Assertions.assertEquals(25, authority.getName().first());
        Assertions.assertEquals(30, authority.getName().last());
        Assertions.assertEquals(9, authority.getName().order());

        Assertions.assertEquals(32, authority.getId().first());
        Assertions.assertEquals(37, authority.getId().last());
        Assertions.assertEquals(11, authority.getId().order());

        Assertions.assertEquals(15, authority.first());
        Assertions.assertEquals(38, authority.last());
        Assertions.assertEquals(13, authority.order());

        Assertions.assertEquals(0, unit.first());
        Assertions.assertEquals(39, unit.last());
        Assertions.assertEquals(15, unit.order());
    }

    @Test
    public void vert_datum_authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_DATUM["Ordnance Datum Newlyn",AUTHORITY["EPSG","5101"]]""");

        final NameAndAnchorDatum.VerticalDatum datum = parser.verticalDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());
        Assertions.assertEquals("EPSG", datum.getIdentifiers().get(0).getName().getSemantics());
        Assertions.assertEquals("5101", datum.getIdentifiers().get(0).getId().getSemantics());

        Assertions.assertEquals(11, datum.getName().first());
        Assertions.assertEquals(33, datum.getName().last());
        Assertions.assertEquals(2, datum.getName().order());

        final Identifier authority = datum.getIdentifiers().get(0);
        Assertions.assertEquals(45, authority.getName().first());
        Assertions.assertEquals(50, authority.getName().last());
        Assertions.assertEquals(6, authority.getName().order());

        Assertions.assertEquals(52, authority.getId().first());
        Assertions.assertEquals(57, authority.getId().last());
        Assertions.assertEquals(8, authority.getId().order());

        Assertions.assertEquals(35, authority.first());
        Assertions.assertEquals(58, authority.last());
        Assertions.assertEquals(10, authority.order());

        Assertions.assertEquals(0, datum.first());
        Assertions.assertEquals(59, datum.last());
        Assertions.assertEquals(12, datum.order());
    }

    @Test
    public void local_datum_authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              LOCAL_DATUM["datum name",AUTHORITY["LOCAL","1"]]""");

        final NameAndAnchorDatum.EngineeringDatum datum = parser.engineeringDatum();

        Assertions.assertEquals("datum name", datum.getName().getSemantics());
        Assertions.assertEquals("LOCAL", datum.getIdentifiers().get(0).getName().getSemantics());
        Assertions.assertEquals("1", datum.getIdentifiers().get(0).getId().getSemantics());

        Assertions.assertEquals(12, datum.getName().first());
        Assertions.assertEquals(23, datum.getName().last());
        Assertions.assertEquals(2, datum.getName().order());

        final Identifier authority = datum.getIdentifiers().get(0);
        Assertions.assertEquals(35, authority.getName().first());
        Assertions.assertEquals(41, authority.getName().last());
        Assertions.assertEquals(6, authority.getName().order());

        Assertions.assertEquals(43, authority.getId().first());
        Assertions.assertEquals(45, authority.getId().last());
        Assertions.assertEquals(8, authority.getId().order());

        Assertions.assertEquals(25, authority.first());
        Assertions.assertEquals(46, authority.last());
        Assertions.assertEquals(10, authority.order());

        Assertions.assertEquals(0, datum.first());
        Assertions.assertEquals(47, datum.last());
        Assertions.assertEquals(12, datum.order());
    }
/*
    @Test
    public void toWgs84_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              TOWGS84[375,-111,431,0,0,0,0]""");

        final ToWgs84 toWgs84 = parser.toWgs84();

        Assertions.assertEquals(375., toWgs84.getDx().getSemantics().doubleValue());
        Assertions.assertEquals(-111., toWgs84.getDy().getSemantics().doubleValue());
        Assertions.assertEquals(431., toWgs84.getDz().getSemantics().doubleValue());
        Assertions.assertEquals(0., toWgs84.getEx().getSemantics().doubleValue());
        Assertions.assertEquals(0., toWgs84.getEy().getSemantics().doubleValue());
        Assertions.assertEquals(0., toWgs84.getEz().getSemantics().doubleValue());
        Assertions.assertEquals(0., toWgs84.getPpm().getSemantics().doubleValue());

        Assertions.assertEquals(8, toWgs84.getDx().first());
        Assertions.assertEquals(10, toWgs84.getDx().last());
        Assertions.assertEquals(4, toWgs84.getDx().order());

        Assertions.assertEquals(12, toWgs84.getDy().first());
        Assertions.assertEquals(15, toWgs84.getDy().last());
        Assertions.assertEquals(9, toWgs84.getDy().order());

        Assertions.assertEquals(17, toWgs84.getDz().first());
        Assertions.assertEquals(19, toWgs84.getDz().last());
        Assertions.assertEquals(13, toWgs84.getDz().order());

        Assertions.assertEquals(21, toWgs84.getEx().first());
        Assertions.assertEquals(21, toWgs84.getEx().last());
        Assertions.assertEquals(17, toWgs84.getEx().order());

        Assertions.assertEquals(23, toWgs84.getEy().first());
        Assertions.assertEquals(23, toWgs84.getEy().last());
        Assertions.assertEquals(21, toWgs84.getEy().order());

        Assertions.assertEquals(25, toWgs84.getEz().first());
        Assertions.assertEquals(25, toWgs84.getEz().last());
        Assertions.assertEquals(25, toWgs84.getEz().order());

        Assertions.assertEquals(27, toWgs84.getPpm().first());
        Assertions.assertEquals(27, toWgs84.getPpm().last());
        Assertions.assertEquals(29, toWgs84.getPpm().order());

        Assertions.assertEquals(0, toWgs84.first());
        Assertions.assertEquals(28, toWgs84.last());
        Assertions.assertEquals(31, toWgs84.order());
    }
*/
    @Test
    public void axis1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              AXIS["Lat",NORTH]""");

        final SpatialTemporalAxis axis = parser.spatialAxis();

        Assertions.assertEquals("Lat", axis.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis.getDirection().getType().getSemantics());

        Assertions.assertEquals(5, axis.getNameAbrev().first());
        Assertions.assertEquals(9, axis.getNameAbrev().last());
        Assertions.assertEquals(2, axis.getNameAbrev().order());

        Assertions.assertEquals(11, axis.getDirection().first());
        Assertions.assertEquals(15, axis.getDirection().last());
        Assertions.assertEquals(5, axis.getDirection().order());

        Assertions.assertEquals(0, axis.first());
        Assertions.assertEquals(16, axis.last());
        Assertions.assertEquals(7, axis.order());
    }

    @Test
    public void prime_meridian_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PRIMEM["Greenwitch",0]""");

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
    public void prime_meridian_authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PRIMEM["Greenwitch",0,AUTHORITY["EPSG","8901"]]""");

        final PrimeMeridian primeMeridian = parser.primeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());
        Assertions.assertEquals("EPSG", primeMeridian.getIdentifiers().get(0).getName().getSemantics());
        Assertions.assertEquals("8901", primeMeridian.getIdentifiers().get(0).getId().getSemantics());

        Assertions.assertEquals(7, primeMeridian.getName().first());
        Assertions.assertEquals(18, primeMeridian.getName().last());
        Assertions.assertEquals(2, primeMeridian.getName().order());

        Assertions.assertEquals(20, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(20, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(6, primeMeridian.getIrmLongitude().order());

        final Identifier authority = primeMeridian.getIdentifiers().get(0);
        Assertions.assertEquals(32, authority.getName().first());
        Assertions.assertEquals(37, authority.getName().last());
        Assertions.assertEquals(10, authority.getName().order());

        Assertions.assertEquals(39, authority.getId().first());
        Assertions.assertEquals(44, authority.getId().last());
        Assertions.assertEquals(12, authority.getId().order());

        Assertions.assertEquals(22, authority.first());
        Assertions.assertEquals(45, authority.last());
        Assertions.assertEquals(14, authority.order());

        Assertions.assertEquals(0, primeMeridian.first());
        Assertions.assertEquals(46, primeMeridian.last());
        Assertions.assertEquals(16, primeMeridian.order());
    }

    @Test
    public void parameter_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PARAMETER["False_Easting",500000.0]""");

        final Parameter primeMeridian = parser.projectionParameter();

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
    public void projection_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJECTION["Transverse_Mercator"]""");

        final Method.MapProjectionMethod projection = parser.mapProjectionMethod();

        Assertions.assertEquals("Transverse_Mercator", projection.getName().getSemantics());

        Assertions.assertEquals(11, projection.getName().first());
        Assertions.assertEquals(31, projection.getName().last());
        Assertions.assertEquals(2, projection.getName().order());

        Assertions.assertEquals(0, projection.first());
        Assertions.assertEquals(32, projection.last());
        Assertions.assertEquals(4, projection.order());

        Assertions.assertTrue(projection.getIdentifiers().isEmpty());
    }

    @Test
    public void projection_authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJECTION["projection name",AUTHORITY["LOCAL","10"]]""");

        final Method.MapProjectionMethod projection = parser.mapProjectionMethod();

        Assertions.assertEquals("projection name", projection.getName().getSemantics());

        Assertions.assertEquals(11, projection.getName().first());
        Assertions.assertEquals(27, projection.getName().last());
        Assertions.assertEquals(2, projection.getName().order());

        final Identifier authority = projection.getIdentifiers().get(0);

        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());

        Assertions.assertEquals(39, authority.getName().first());
        Assertions.assertEquals(45, authority.getName().last());
        Assertions.assertEquals(6, authority.getName().order());

        Assertions.assertEquals("10", authority.getId().getSemantics());

        Assertions.assertEquals(47, authority.getId().first());
        Assertions.assertEquals(50, authority.getId().last());
        Assertions.assertEquals(8, authority.getId().order());

        Assertions.assertEquals(29, authority.first());
        Assertions.assertEquals(51, authority.last());
        Assertions.assertEquals(10, authority.order());

        Assertions.assertEquals(0, projection.first());
        Assertions.assertEquals(52, projection.last());
        Assertions.assertEquals(12, projection.order());
    }

    @Test
    public void spheroid_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              SPHEROID["GRS_1980",6378137,298.257222101]""");

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
    public void spheroid_authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              SPHEROID["Airy 1830",637563.396,299.3249646,AUTHORITY["EPSG","7001"]]""");

        final Ellipsoid spheroid = parser.ellipsoid();

        Assertions.assertEquals(WktKeyword.SPHEROID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("Airy 1830", spheroid.getName().getSemantics());
        Assertions.assertEquals(637563.396, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(299.3249646, spheroid.getInverseFlattening().getSemantics().doubleValue());

        final Identifier authority = spheroid.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());
        Assertions.assertEquals("7001", authority.getId().getSemantics());
    }

    @Test
    public void ellipsoid_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]""");

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

        final GeodeticDatum datum = parser.geodeticDatum(new Token[0]);

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
    public void vert_cs_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS["Newlyn",\
                                              VERT_DATUM["Ordnance Datum Newlyn",AUTHORITY["EPSG","5101"]],\
                                              UNIT["metre",1,AUTHORITY["EPSG","9001"]]]""");

        final SimpleCrsShell.VerticalCrs vertCs = parser.verticalCrs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(118, vertCs.last());
        Assertions.assertEquals(36, vertCs.order());

        final NameAndAnchorDatum.VerticalDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        final Identifier datumAuthority = datum.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(62, datumAuthority.getName().first());
        Assertions.assertEquals(67, datumAuthority.getName().last());
        Assertions.assertEquals(10, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getId().getSemantics());
        Assertions.assertEquals(69, datumAuthority.getId().first());
        Assertions.assertEquals(74, datumAuthority.getId().last());
        Assertions.assertEquals(12, datumAuthority.getId().order());

        Assertions.assertEquals(52, datumAuthority.first());
        Assertions.assertEquals(75, datumAuthority.last());
        Assertions.assertEquals(14, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(76, datum.last());
        Assertions.assertEquals(16, datum.order());

        final Unit unit = vertCs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(83, unit.getName().first());
        Assertions.assertEquals(89, unit.getName().last());
        Assertions.assertEquals(20, unit.getName().order());

        Assertions.assertEquals(91, unit.getConversionFactor().first());
        Assertions.assertEquals(91, unit.getConversionFactor().last());
        Assertions.assertEquals(23, unit.getConversionFactor().order());

        final Identifier unitAuthority = unit.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(103, unitAuthority.getName().first());
        Assertions.assertEquals(108, unitAuthority.getName().last());
        Assertions.assertEquals(27, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getId().getSemantics());
        Assertions.assertEquals(110, unitAuthority.getId().first());
        Assertions.assertEquals(115, unitAuthority.getId().last());
        Assertions.assertEquals(29, unitAuthority.getId().order());

        Assertions.assertEquals(93, unitAuthority.first());
        Assertions.assertEquals(116, unitAuthority.last());
        Assertions.assertEquals(31, unitAuthority.order());

        Assertions.assertEquals(78, unit.first());
        Assertions.assertEquals(117, unit.last());
        Assertions.assertEquals(33, unit.order());

        Assertions.assertTrue(vertCs.getCoordinateSystem().getAxis().isEmpty());

        Assertions.assertTrue(vertCs.getIdentifiers().isEmpty());
    }
/*
    @Test
    @DisplayName("exception should fail for vert_cs with two axes")
    public void vert_cs_axis_two_axis() throws LanguageException {

        final ParserException ex = Assertions.assertThrows(ParserException.class, () -> {

            WktParser.of("""
                         VERT_CS["Newlyn",\
                         VERT_DATUM["Ordnance Datum Newlyn",AUTHORITY["EPSG","5101"]],\
                         UNIT["metre",1,AUTHORITY["EPSG","9001"]],\
                         AXIS["Up",UP],AXIS["Up",UP]]""").verticalCrs();
        });

        Assertions.assertEquals("""
                                unexpected token {codePoints=,} at 143, but expected:
                                AXIS
                                """, ex.getMessage());
    }
*/
    @Test
    public void vert_cs_axis_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS["Newlyn",\
                                              VERT_DATUM["Ordnance Datum Newlyn",AUTHORITY["EPSG","5101"]],\
                                              UNIT["metre",1,AUTHORITY["EPSG","9001"]],\
                                              AXIS["Up",UP]]""");

        final SimpleCrsShell.VerticalCrs vertCs = parser.verticalCrs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(132, vertCs.last());
        Assertions.assertEquals(45, vertCs.order());

        final NameAndAnchorDatum.VerticalDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        final Identifier datumAuthority = datum.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(62, datumAuthority.getName().first());
        Assertions.assertEquals(67, datumAuthority.getName().last());
        Assertions.assertEquals(10, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getId().getSemantics());
        Assertions.assertEquals(69, datumAuthority.getId().first());
        Assertions.assertEquals(74, datumAuthority.getId().last());
        Assertions.assertEquals(12, datumAuthority.getId().order());

        Assertions.assertEquals(52, datumAuthority.first());
        Assertions.assertEquals(75, datumAuthority.last());
        Assertions.assertEquals(14, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(76, datum.last());
        Assertions.assertEquals(16, datum.order());

        final Unit unit = vertCs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(83, unit.getName().first());
        Assertions.assertEquals(89, unit.getName().last());
        Assertions.assertEquals(20, unit.getName().order());

        Assertions.assertEquals(91, unit.getConversionFactor().first());
        Assertions.assertEquals(91, unit.getConversionFactor().last());
        Assertions.assertEquals(23, unit.getConversionFactor().order());

        final Identifier unitAuthority = unit.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(103, unitAuthority.getName().first());
        Assertions.assertEquals(108, unitAuthority.getName().last());
        Assertions.assertEquals(27, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getId().getSemantics());
        Assertions.assertEquals(110, unitAuthority.getId().first());
        Assertions.assertEquals(115, unitAuthority.getId().last());
        Assertions.assertEquals(29, unitAuthority.getId().order());

        Assertions.assertEquals(93, unitAuthority.first());
        Assertions.assertEquals(116, unitAuthority.last());
        Assertions.assertEquals(31, unitAuthority.order());

        Assertions.assertEquals(78, unit.first());
        Assertions.assertEquals(117, unit.last());
        Assertions.assertEquals(33, unit.order());

        final SpatialTemporalAxis axis = vertCs.getCoordinateSystem().getAxis().get(0);

        Assertions.assertEquals("Up", axis.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.up, axis.getDirection().getType().getSemantics());

        Assertions.assertEquals(124, axis.getNameAbrev().first());
        Assertions.assertEquals(127, axis.getNameAbrev().last());
        Assertions.assertEquals(37, axis.getNameAbrev().order());

        Assertions.assertEquals(129, axis.getDirection().first());
        Assertions.assertEquals(130, axis.getDirection().last());
        Assertions.assertEquals(40, axis.getDirection().order());

        Assertions.assertEquals(119, axis.first());
        Assertions.assertEquals(131, axis.last());
        Assertions.assertEquals(42, axis.order());

        Assertions.assertTrue(vertCs.getIdentifiers().isEmpty());
    }

    @Test
    public void vert_cs_authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                            VERT_CS["Newlyn",\
                            VERT_DATUM["Ordnance Datum Newlyn",AUTHORITY["EPSG","5101"]],\
                            UNIT["metre",1,AUTHORITY["EPSG","9001"]],\
                            AUTHORITY["EPSG","5701"]]""");

        final SimpleCrsShell.VerticalCrs vertCs = parser.verticalCrs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(143, vertCs.last());
        Assertions.assertEquals(44, vertCs.order());

        final NameAndAnchorDatum.VerticalDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        final Identifier datumAuthority = datum.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(62, datumAuthority.getName().first());
        Assertions.assertEquals(67, datumAuthority.getName().last());
        Assertions.assertEquals(10, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getId().getSemantics());
        Assertions.assertEquals(69, datumAuthority.getId().first());
        Assertions.assertEquals(74, datumAuthority.getId().last());
        Assertions.assertEquals(12, datumAuthority.getId().order());

        Assertions.assertEquals(52, datumAuthority.first());
        Assertions.assertEquals(75, datumAuthority.last());
        Assertions.assertEquals(14, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(76, datum.last());
        Assertions.assertEquals(16, datum.order());

        final Unit unit = vertCs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(83, unit.getName().first());
        Assertions.assertEquals(89, unit.getName().last());
        Assertions.assertEquals(20, unit.getName().order());

        Assertions.assertEquals(91, unit.getConversionFactor().first());
        Assertions.assertEquals(91, unit.getConversionFactor().last());
        Assertions.assertEquals(23, unit.getConversionFactor().order());

        final Identifier unitAuthority = unit.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(103, unitAuthority.getName().first());
        Assertions.assertEquals(108, unitAuthority.getName().last());
        Assertions.assertEquals(27, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getId().getSemantics());
        Assertions.assertEquals(110, unitAuthority.getId().first());
        Assertions.assertEquals(115, unitAuthority.getId().last());
        Assertions.assertEquals(29, unitAuthority.getId().order());

        Assertions.assertEquals(93, unitAuthority.first());
        Assertions.assertEquals(116, unitAuthority.last());
        Assertions.assertEquals(31, unitAuthority.order());

        Assertions.assertEquals(78, unit.first());
        Assertions.assertEquals(117, unit.last());
        Assertions.assertEquals(33, unit.order());

        Assertions.assertTrue(vertCs.getCoordinateSystem().getAxis().isEmpty());

        final Identifier authority = vertCs.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());

        Assertions.assertEquals(129, authority.getName().first());
        Assertions.assertEquals(134, authority.getName().last());
        Assertions.assertEquals(38, authority.getName().order());

        Assertions.assertEquals("5701", authority.getId().getSemantics());
        Assertions.assertEquals(136, authority.getId().first());
        Assertions.assertEquals(141, authority.getId().last());
        Assertions.assertEquals(40, authority.getId().order());

        Assertions.assertEquals(119, authority.first());
        Assertions.assertEquals(142, authority.last());
        Assertions.assertEquals(42, authority.order());
    }

    @Test
    public void vert_cs_axis_authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS["Newlyn",\
                                              VERT_DATUM["Ordnance Datum Newlyn",AUTHORITY["EPSG","5101"]],\
                                              UNIT["metre",1,AUTHORITY["EPSG","9001"]],\
                                              AXIS["Up",UP],\
                                              AUTHORITY["EPSG","5701"]]""");

        final SimpleCrsShell.VerticalCrs vertCs = parser.verticalCrs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(157, vertCs.last());
        Assertions.assertEquals(53, vertCs.order());

        final NameAndAnchorDatum.VerticalDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        final Identifier datumAuthority = datum.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(62, datumAuthority.getName().first());
        Assertions.assertEquals(67, datumAuthority.getName().last());
        Assertions.assertEquals(10, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getId().getSemantics());
        Assertions.assertEquals(69, datumAuthority.getId().first());
        Assertions.assertEquals(74, datumAuthority.getId().last());
        Assertions.assertEquals(12, datumAuthority.getId().order());

        Assertions.assertEquals(52, datumAuthority.first());
        Assertions.assertEquals(75, datumAuthority.last());
        Assertions.assertEquals(14, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(76, datum.last());
        Assertions.assertEquals(16, datum.order());

        final Unit unit = vertCs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(83, unit.getName().first());
        Assertions.assertEquals(89, unit.getName().last());
        Assertions.assertEquals(20, unit.getName().order());

        Assertions.assertEquals(91, unit.getConversionFactor().first());
        Assertions.assertEquals(91, unit.getConversionFactor().last());
        Assertions.assertEquals(23, unit.getConversionFactor().order());

        final Identifier unitAuthority = unit.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(103, unitAuthority.getName().first());
        Assertions.assertEquals(108, unitAuthority.getName().last());
        Assertions.assertEquals(27, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getId().getSemantics());
        Assertions.assertEquals(110, unitAuthority.getId().first());
        Assertions.assertEquals(115, unitAuthority.getId().last());
        Assertions.assertEquals(29, unitAuthority.getId().order());

        Assertions.assertEquals(93, unitAuthority.first());
        Assertions.assertEquals(116, unitAuthority.last());
        Assertions.assertEquals(31, unitAuthority.order());

        Assertions.assertEquals(78, unit.first());
        Assertions.assertEquals(117, unit.last());
        Assertions.assertEquals(33, unit.order());

        final SpatialTemporalAxis axis = vertCs.getCoordinateSystem().getAxis().get(0);

        Assertions.assertEquals("Up", axis.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.up, axis.getDirection().getType().getSemantics());

        Assertions.assertEquals(124, axis.getNameAbrev().first());
        Assertions.assertEquals(127, axis.getNameAbrev().last());
        Assertions.assertEquals(37, axis.getNameAbrev().order());

        Assertions.assertEquals(129, axis.getDirection().first());
        Assertions.assertEquals(130, axis.getDirection().last());
        Assertions.assertEquals(40, axis.getDirection().order());

        Assertions.assertEquals(119, axis.first());
        Assertions.assertEquals(131, axis.last());
        Assertions.assertEquals(42, axis.order());

        final Identifier authority = vertCs.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());

        Assertions.assertEquals(143, authority.getName().first());
        Assertions.assertEquals(148, authority.getName().last());
        Assertions.assertEquals(47, authority.getName().order());

        Assertions.assertEquals("5701", authority.getId().getSemantics());
        Assertions.assertEquals(150, authority.getId().first());
        Assertions.assertEquals(155, authority.getId().last());
        Assertions.assertEquals(49, authority.getId().order());

        Assertions.assertEquals(133, authority.first());
        Assertions.assertEquals(156, authority.last());
        Assertions.assertEquals(51, authority.order());
    }

    @Test
    public void local_cs_axis_authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              LOCAL_CS["local cs name",\
                                              LOCAL_DATUM["local datum name",AUTHORITY["LOCAL","2"]],\
                                              UNIT["metre",1,AUTHORITY["EPSG","9001"]],\
                                              AXIS["Up",UP],\
                                              AXIS["East",EAST],\
                                              AUTHORITY["LOCAL","3"]]""");

        final SimpleCrsShell.EngineeringCrs localCs = parser.engineeringCrs();

        Assertions.assertEquals("local cs name", localCs.getName().getSemantics());

        final NameAndAnchorDatum.EngineeringDatum datum = localCs.getDatum();
        Assertions.assertEquals("local datum name", datum.getName().getSemantics());

        final Identifier datumAuthority = datum.getIdentifiers().get(0);
        Assertions.assertEquals("LOCAL", datumAuthority.getName().getSemantics());
        Assertions.assertEquals("2", datumAuthority.getId().getSemantics());

        final SpatialCoordinateSystem cs = localCs.getCoordinateSystem();

        final Unit unit = cs.getUnit();
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        final Identifier unitAuthority = unit.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());
        Assertions.assertEquals("9001", unitAuthority.getId().getSemantics());

        Assertions.assertEquals(2, cs.getAxis().size());

        final SpatialTemporalAxis axis0 = cs.getAxis().get(0);
        Assertions.assertEquals("Up", axis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.up, axis0.getDirection().getType().getSemantics());

        final SpatialTemporalAxis axis1 = cs.getAxis().get(1);
        Assertions.assertEquals("East", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());

        final Identifier authority = localCs.getIdentifiers().get(0);
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("3", authority.getId().getSemantics());
    }
/*
    @Test
    public void fitted_cs_axis_authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              FITTED_CS["fitted cs name",\
                                              CONCAT_MT[PASSTHROUGH_MT[4,INVERSE_MT[PARAM_MT["classification name",\
                                              PARAMETER["first",0.0174532925199433],\
                                              PARAMETER["second",0.174532925199433]]]],\
                                              CONCAT_MT[PARAM_MT["second name"]]],\
                                              LOCAL_CS["local cs name",\
                                              LOCAL_DATUM["local datum name",1,AUTHORITY["LOCAL","2"]],\
                                              UNIT["metre",1,AUTHORITY["EPSG","9001"]],\
                                              AXIS["Up",UP],\
                                              AXIS["East",EAST],\
                                              AUTHORITY["LOCAL","3"]]]""");

        final FittedCs fitted_cs = parser.fittedCs();

        Assertions.assertTrue(fitted_cs.getMathTransform() instanceof ConcatMt);

        final ConcatMt concat_mt = (ConcatMt) fitted_cs.getMathTransform();

        Assertions.assertEquals(2, concat_mt.getTransforms().size());
        Assertions.assertTrue(concat_mt.getTransforms().get(0) instanceof PassthroughMt);
        Assertions.assertTrue(concat_mt.getTransforms().get(1) instanceof ConcatMt);

        final PassthroughMt passthrough_mt = (PassthroughMt) concat_mt.getTransforms().get(0);

        Assertions.assertEquals(4, passthrough_mt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthrough_mt.getTransform() instanceof InverseMt);

        final InverseMt inverse_mt = (InverseMt) passthrough_mt.getTransform();

        Assertions.assertTrue(inverse_mt.getTransform() instanceof ParamMt);

        final ParamMt param_mt = (ParamMt) inverse_mt.getTransform();

        Assertions.assertEquals("classification name", param_mt.getName().getSemantics());

        final List<Parameter> parameters = param_mt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());

        final ConcatMt incl_concat_mt = (ConcatMt) concat_mt.getTransforms().get(1);
        Assertions.assertEquals(1, incl_concat_mt.getTransforms().size());
        Assertions.assertTrue(incl_concat_mt.getTransforms().get(0) instanceof ParamMt);

        final ParamMt incl_param_mt = (ParamMt) incl_concat_mt.getTransforms().get(0);

        Assertions.assertEquals("second name", incl_param_mt.getName().getSemantics());

        Assertions.assertTrue(incl_param_mt.getParameters().isEmpty());

        Assertions.assertTrue(fitted_cs.getCoordinateSystem() instanceof LocalCs);

        final LocalCs localCs = (LocalCs) fitted_cs.getCoordinateSystem();

        Assertions.assertEquals("local cs name", localCs.getName().getSemantics());

        final LocalDatum datum = localCs.getDatum();
        Assertions.assertEquals("local datum name", datum.getName().getSemantics());
        Assertions.assertEquals(1, datum.getDatumType().getSemantics().intValue());

        final Authority datumAuthority = datum.getAuthority();
        Assertions.assertEquals("LOCAL", datumAuthority.getName().getSemantics());
        Assertions.assertEquals("2", datumAuthority.getCode().getSemantics());

        final Unit unit = localCs.getLinearUnit();
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        final Authority unitAuthority = unit.getAuthority();
        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());
        Assertions.assertEquals("9001", unitAuthority.getCode().getSemantics());

        Assertions.assertEquals(2, localCs.getAxis().size());

        final SpatialTemporalAxis axis0 = localCs.getAxis().get(0);
        Assertions.assertEquals("Up", axis0.getName().getSemantics());
        Assertions.assertEquals(Direction.UP, axis0.getDirection().getSemantics());

        final SpatialTemporalAxis axis1 = localCs.getAxis().get(1);
        Assertions.assertEquals("East", axis1.getName().getSemantics());
        Assertions.assertEquals(Direction.EAST, axis1.getDirection().getSemantics());

        final Authority authority = localCs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("3", authority.getCode().getSemantics());
    }

    @Test
    public void compd_cs_axis_authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              COMPD_CS["compound cs",\
                                              FITTED_CS["fitted cs name",\
                                              CONCAT_MT[PASSTHROUGH_MT[4,INVERSE_MT[PARAM_MT["classification name",\
                                              PARAMETER["first",0.0174532925199433],\
                                              PARAMETER["second",0.174532925199433]]]],\
                                              CONCAT_MT[PARAM_MT["second name"]]],\
                                              LOCAL_CS["local cs name",\
                                              LOCAL_DATUM["local datum name",1,AUTHORITY["LOCAL","2"]],\
                                              UNIT["metre",1,AUTHORITY["EPSG","9001"]],\
                                              AXIS["Up",UP],\
                                              AXIS["East",EAST],\
                                              AUTHORITY["LOCAL","3"]]],\
                                              VERT_CS["Newlyn",\
                                              VERT_DATUM["Ordnance Datum Newlyn",2005,AUTHORITY["EPSG","5101"]],\
                                              UNIT["metre",1,AUTHORITY["EPSG","9001"]],\
                                              AXIS["Up",UP],\
                                              AUTHORITY["EPSG","5701"]],\
                                              AUTHORITY["LOCAL","10"]]""");

        final CompdCs compd_cs = parser.compdCs();

        Assertions.assertTrue(compd_cs.getHead() instanceof FittedCs);

        final FittedCs fitted_cs = (FittedCs) compd_cs.getHead();

        Assertions.assertTrue(fitted_cs.getMathTransform() instanceof ConcatMt);

        final ConcatMt concat_mt = (ConcatMt) fitted_cs.getMathTransform();

        Assertions.assertEquals(2, concat_mt.getTransforms().size());
        Assertions.assertTrue(concat_mt.getTransforms().get(0) instanceof PassthroughMt);
        Assertions.assertTrue(concat_mt.getTransforms().get(1) instanceof ConcatMt);

        final PassthroughMt passthrough_mt = (PassthroughMt) concat_mt.getTransforms().get(0);

        Assertions.assertEquals(4, passthrough_mt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthrough_mt.getTransform() instanceof InverseMt);

        final InverseMt inverse_mt = (InverseMt) passthrough_mt.getTransform();

        Assertions.assertTrue(inverse_mt.getTransform() instanceof ParamMt);

        final ParamMt param_mt = (ParamMt) inverse_mt.getTransform();

        Assertions.assertEquals("classification name", param_mt.getName().getSemantics());

        final List<Parameter> parameters = param_mt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());

        final ConcatMt incl_concat_mt = (ConcatMt) concat_mt.getTransforms().get(1);
        Assertions.assertEquals(1, incl_concat_mt.getTransforms().size());
        Assertions.assertTrue(incl_concat_mt.getTransforms().get(0) instanceof ParamMt);

        final ParamMt incl_param_mt = (ParamMt) incl_concat_mt.getTransforms().get(0);

        Assertions.assertEquals("second name", incl_param_mt.getName().getSemantics());

        Assertions.assertTrue(incl_param_mt.getParameters().isEmpty());

        Assertions.assertTrue(fitted_cs.getCoordinateSystem() instanceof LocalCs);

        final LocalCs localCs = (LocalCs) fitted_cs.getCoordinateSystem();

        Assertions.assertEquals("local cs name", localCs.getName().getSemantics());

        final LocalDatum datum = localCs.getDatum();
        Assertions.assertEquals("local datum name", datum.getName().getSemantics());
        Assertions.assertEquals(1, datum.getDatumType().getSemantics().intValue());

        final Authority datumAuthority = datum.getAuthority();
        Assertions.assertEquals("LOCAL", datumAuthority.getName().getSemantics());
        Assertions.assertEquals("2", datumAuthority.getCode().getSemantics());

        final Unit unit = localCs.getLinearUnit();
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        final Authority unitAuthority = unit.getAuthority();
        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());
        Assertions.assertEquals("9001", unitAuthority.getCode().getSemantics());

        Assertions.assertEquals(2, localCs.getAxis().size());

        final SpatialTemporalAxis axis0 = localCs.getAxis().get(0);
        Assertions.assertEquals("Up", axis0.getName().getSemantics());
        Assertions.assertEquals(Direction.UP, axis0.getDirection().getSemantics());

        final SpatialTemporalAxis axis1 = localCs.getAxis().get(1);
        Assertions.assertEquals("East", axis1.getName().getSemantics());
        Assertions.assertEquals(Direction.EAST, axis1.getDirection().getSemantics());

        final Authority authority = localCs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("3", authority.getCode().getSemantics());


        Assertions.assertTrue(compd_cs.getTail() instanceof VertCs);

        final VertCs vertCs = (VertCs) compd_cs.getTail();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        final VertDatum vertCsDatum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", vertCsDatum.getName().getSemantics());
        Assertions.assertEquals(2005, vertCsDatum.getDatumType().getSemantics().intValue());

        final Authority vertCsDatumAuthority = vertCsDatum.getAuthority();
        Assertions.assertEquals("EPSG", vertCsDatumAuthority.getName().getSemantics());
        Assertions.assertEquals("5101", vertCsDatumAuthority.getCode().getSemantics());

        final Unit vertCsUnit = vertCs.getLinearUnit();

        Assertions.assertEquals("metre", vertCsUnit.getName().getSemantics());
        Assertions.assertEquals(1., vertCsUnit.getConversionFactor().getSemantics().doubleValue());

        final Authority vertCsUnitAuthority = vertCsUnit.getAuthority();

        Assertions.assertEquals("EPSG", vertCsUnitAuthority.getName().getSemantics());

        Assertions.assertEquals("9001", vertCsUnitAuthority.getCode().getSemantics());

        final SpatialTemporalAxis spatialAxis = vertCs.getAxis();

        Assertions.assertEquals("Up", spatialAxis.getName().getSemantics());
        Assertions.assertEquals(Direction.UP, spatialAxis.getDirection().getSemantics());

        final Authority vertCsAuthority = vertCs.getAuthority();

        Assertions.assertEquals("EPSG", vertCsAuthority.getName().getSemantics());
        Assertions.assertEquals("5701", vertCsAuthority.getCode().getSemantics());


        final Authority compdCsAuthority = compd_cs.getAuthority();
        Assertions.assertEquals("LOCAL", compdCsAuthority.getName().getSemantics());
        Assertions.assertEquals("10", compdCsAuthority.getCode().getSemantics());
    }
*/
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
        Assertions.assertEquals(61, datum.getName().last());
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
    public void geoccs_axis_authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],UNIT["Degree",0.0174532925199433],\
                                              AXIS["E",EAST],AXIS["N",NORTH],AXIS["U",UP],AUTHORITY["LOCAL","1"]]""");

        final GeodeticCrs geoccs = parser.geodeticCrs();

        Assertions.assertEquals("GCS_North_American_1983", geoccs.getName().getSemantics());

        final GeodeticDatum datum = geoccs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        final PrimeMeridian primeMeridian = geoccs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        final Unit unit = geoccs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        final SpatialTemporalAxis axis1 = geoccs.getCoordinateSystem().getAxis().get(0);
        Assertions.assertEquals("E", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());
        final SpatialTemporalAxis axis2 = geoccs.getCoordinateSystem().getAxis().get(1);
        Assertions.assertEquals("N", axis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis2.getDirection().getType().getSemantics());
        final SpatialTemporalAxis axis3 = geoccs.getCoordinateSystem().getAxis().get(2);
        Assertions.assertEquals("U", axis3.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.up, axis3.getDirection().getType().getSemantics());
        final Identifier authority = geoccs.getIdentifiers().get(0);
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("1", authority.getId().getSemantics());
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

        /*final Unit linear = geogcs.getLinearUnit();

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
        Assertions.assertEquals(58, linear.order());*/
    }

    @Test
    public void geogcs_axis_authority_test_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],UNIT["Degree",0.0174532925199433],\
                                              AXIS["E",EAST],AXIS["N",NORTH],AUTHORITY["LOCAL","1"]]""");

        final GeodeticCrs geogcs = parser.geodeticCrs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        final Unit angular = geogcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        final SpatialTemporalAxis axis1 = geogcs.getCoordinateSystem().getAxis().get(0);
        Assertions.assertEquals("E", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());
        final SpatialTemporalAxis axis2 = geogcs.getCoordinateSystem().getAxis().get(1);
        Assertions.assertEquals("N", axis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis2.getDirection().getType().getSemantics());

        final Identifier authority = geogcs.getIdentifiers().get(0);
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("1", authority.getId().getSemantics());
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

        Assertions.assertEquals(197, method.first());
        Assertions.assertEquals(229, method.last());
        Assertions.assertEquals(57, method.order());

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
    public void projcs_axis_test_1() throws LanguageException {

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
                                              UNIT["Meter",1.0],\
                                              AXIS["Easting",EAST],AXIS["Northing",NORTH]]""");

        final DerivedCrs.ProjectedCrs projcs = parser.projectedCrs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(466, projcs.last());
        Assertions.assertEquals(151, projcs.order());

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

        Assertions.assertEquals(197, method.first());
        Assertions.assertEquals(229, method.last());
        Assertions.assertEquals(57, method.order());

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

        final SpatialTemporalAxis axis1 = projcs.getCoordinateSystem().getAxis().get(0);

        Assertions.assertEquals("Easting", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());

        final SpatialTemporalAxis axis2 = projcs.getCoordinateSystem().getAxis().get(1);

        Assertions.assertEquals("Northing", axis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis2.getDirection().getType().getSemantics());
    }

    @Test
    public void projcs_axis_authority_test_1() throws LanguageException {

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
                                              UNIT["Meter",1.0],\
                                              AXIS["Easting",EAST],AXIS["Northing",NORTH],\
                                              AUTHORITY["LOCAL","10"]]""");

        final DerivedCrs.ProjectedCrs projcs = parser.projectedCrs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(490, projcs.last());
        Assertions.assertEquals(159, projcs.order());

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

        Assertions.assertEquals(197, method.first());
        Assertions.assertEquals(229, method.last());
        Assertions.assertEquals(57, method.order());

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

        final SpatialTemporalAxis axis1 = projcs.getCoordinateSystem().getAxis().get(0);

        Assertions.assertEquals("Easting", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());

        final SpatialTemporalAxis axis2 = projcs.getCoordinateSystem().getAxis().get(1);

        Assertions.assertEquals("Northing", axis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis2.getDirection().getType().getSemantics());

        final Identifier authority = projcs.getIdentifiers().get(0);

        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("10", authority.getId().getSemantics());
    }

    @Test
    public void projcs_authority_test_1() throws LanguageException {

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
                                              UNIT["Meter",1.0],\
                                              AUTHORITY["LOCAL","10"]]""");

        final DerivedCrs.ProjectedCrs projcs = parser.projectedCrs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(446, projcs.last());
        Assertions.assertEquals(141, projcs.order());

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

        Assertions.assertEquals(197, method.first());
        Assertions.assertEquals(229, method.last());
        Assertions.assertEquals(57, method.order());

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

        final Identifier authority = projcs.getIdentifiers().get(0);

        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("10", authority.getId().getSemantics());
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

    @SectionReference(type = SectionReferenceType.SECTION, id = "C.4.1") // pas de rétrocompatibilité ellipsoidal CS 3D
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
        /*
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
        */
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

        Assertions.assertEquals(197, method.first());
        Assertions.assertEquals(229, method.last());
        Assertions.assertEquals(57, method.order());

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
    public void authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              AUTHORITY("EPSG","8901")""", '(', ')');

        final Identifier authority = parser.identifier();

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());
        Assertions.assertEquals("8901", authority.getId().getSemantics());

        Assertions.assertEquals(10, authority.getName().first());
        Assertions.assertEquals(15, authority.getName().last());
        Assertions.assertEquals(2, authority.getName().order());

        Assertions.assertEquals(17, authority.getId().first());
        Assertions.assertEquals(22, authority.getId().last());
        Assertions.assertEquals(4, authority.getId().order());

        Assertions.assertEquals(0, authority.first());
        Assertions.assertEquals(23, authority.last());
        Assertions.assertEquals(6, authority.order());
    }
/*
    @Test
    public void param_mt_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PARAM_MT("classification name",\
                                              PARAMETER("first",0.0174532925199433),\
                                              PARAMETER("second",0.174532925199433))""", '(', ')');

        final ParamMt param_mt = parser.paramMt();

        Assertions.assertEquals("classification name", param_mt.getName().getSemantics());

        final List<Parameter> parameters = param_mt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());
    }

    @Test
    public void inverse_mt_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              INVERSE_MT(PARAM_MT("classification name",\
                                              PARAMETER("first",0.0174532925199433),\
                                              PARAMETER("second",0.174532925199433)))""", '(', ')');

        final InverseMt inverse_mt = parser.invMt();

        Assertions.assertTrue(inverse_mt.getTransform() instanceof ParamMt);

        final ParamMt param_mt = (ParamMt) inverse_mt.getTransform();

        Assertions.assertEquals("classification name", param_mt.getName().getSemantics());

        final List<Parameter> parameters = param_mt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());
    }

    @Test
    public void passthrough_mt_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PASSTHROUGH_MT(4,INVERSE_MT(PARAM_MT("classification name",\
                                              PARAMETER("first",0.0174532925199433),\
                                             PARAMETER("second",0.174532925199433))))""", '(', ')');

        final PassthroughMt passthrough_mt = parser.passthroughMt();

        Assertions.assertEquals(4, passthrough_mt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthrough_mt.getTransform() instanceof InverseMt);

        final InverseMt inverse_mt = (InverseMt) passthrough_mt.getTransform();

        Assertions.assertTrue(inverse_mt.getTransform() instanceof ParamMt);

        final ParamMt param_mt = (ParamMt) inverse_mt.getTransform();

        Assertions.assertEquals("classification name", param_mt.getName().getSemantics());

        final List<Parameter> parameters = param_mt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());
    }

    @Test
    public void concat_mt_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              CONCAT_MT(PASSTHROUGH_MT(4,INVERSE_MT(PARAM_MT("classification name",\
                                              PARAMETER("first",0.0174532925199433),\
                                              PARAMETER("second",0.174532925199433)))),\
                                              CONCAT_MT(PARAM_MT("second name")))""", '(', ')');

        final ConcatMt concat_mt = parser.concatMt();

        Assertions.assertEquals(2, concat_mt.getTransforms().size());
        Assertions.assertTrue(concat_mt.getTransforms().get(0) instanceof PassthroughMt);
        Assertions.assertTrue(concat_mt.getTransforms().get(1) instanceof ConcatMt);

        final PassthroughMt passthrough_mt = (PassthroughMt) concat_mt.getTransforms().get(0);

        Assertions.assertEquals(4, passthrough_mt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthrough_mt.getTransform() instanceof InverseMt);

        final InverseMt inverse_mt = (InverseMt) passthrough_mt.getTransform();

        Assertions.assertTrue(inverse_mt.getTransform() instanceof ParamMt);

        final ParamMt param_mt = (ParamMt) inverse_mt.getTransform();

        Assertions.assertEquals("classification name", param_mt.getName().getSemantics());

        final List<Parameter> parameters = param_mt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());

        final ConcatMt incl_concat_mt = (ConcatMt) concat_mt.getTransforms().get(1);
        Assertions.assertEquals(1, incl_concat_mt.getTransforms().size());
        Assertions.assertTrue(incl_concat_mt.getTransforms().get(0) instanceof ParamMt);

        final ParamMt incl_param_mt = (ParamMt) incl_concat_mt.getTransforms().get(0);

        Assertions.assertEquals("second name", incl_param_mt.getName().getSemantics());

        Assertions.assertTrue(incl_param_mt.getParameters().isEmpty());
    }
*/
    @Test
    public void unit_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              UNIT("Degree",0.0174532925199433)""", '(', ')');

        final Unit unit = parser.unit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(5, unit.getName().first());
        Assertions.assertEquals(12, unit.getName().last());
        Assertions.assertEquals(2, unit.getName().order());

        Assertions.assertEquals(14, unit.getConversionFactor().first());
        Assertions.assertEquals(31, unit.getConversionFactor().last());
        Assertions.assertEquals(7, unit.getConversionFactor().order());

        Assertions.assertTrue(unit.getIdentifiers().isEmpty());

        Assertions.assertEquals(0, unit.first());
        Assertions.assertEquals(32, unit.last());
        Assertions.assertEquals(9, unit.order());
    }

    @Test
    public void unit_authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              UNIT("metre",1,AUTHORITY("EPSG","9001"))""", '(', ')');

        final Unit unit = parser.unit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals("EPSG", unit.getIdentifiers().get(0).getName().getSemantics());
        Assertions.assertEquals("9001", unit.getIdentifiers().get(0).getId().getSemantics());

        Assertions.assertEquals(5, unit.getName().first());
        Assertions.assertEquals(11, unit.getName().last());
        Assertions.assertEquals(2, unit.getName().order());

        Assertions.assertEquals(13, unit.getConversionFactor().first());
        Assertions.assertEquals(13, unit.getConversionFactor().last());
        Assertions.assertEquals(5, unit.getConversionFactor().order());

        final Identifier authority = unit.getIdentifiers().get(0);
        Assertions.assertEquals(25, authority.getName().first());
        Assertions.assertEquals(30, authority.getName().last());
        Assertions.assertEquals(9, authority.getName().order());

        Assertions.assertEquals(32, authority.getId().first());
        Assertions.assertEquals(37, authority.getId().last());
        Assertions.assertEquals(11, authority.getId().order());

        Assertions.assertEquals(15, authority.first());
        Assertions.assertEquals(38, authority.last());
        Assertions.assertEquals(13, authority.order());

        Assertions.assertEquals(0, unit.first());
        Assertions.assertEquals(39, unit.last());
        Assertions.assertEquals(15, unit.order());
    }

    @Test
    public void vert_datum_authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_DATUM("Ordnance Datum Newlyn",AUTHORITY("EPSG","5101"))""",
                '(', ')');

        final NameAndAnchorDatum.VerticalDatum datum = parser.verticalDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());
        Assertions.assertEquals("EPSG", datum.getIdentifiers().get(0).getName().getSemantics());
        Assertions.assertEquals("5101", datum.getIdentifiers().get(0).getId().getSemantics());

        Assertions.assertEquals(11, datum.getName().first());
        Assertions.assertEquals(33, datum.getName().last());
        Assertions.assertEquals(2, datum.getName().order());

        final Identifier authority = datum.getIdentifiers().get(0);
        Assertions.assertEquals(45, authority.getName().first());
        Assertions.assertEquals(50, authority.getName().last());
        Assertions.assertEquals(6, authority.getName().order());

        Assertions.assertEquals(52, authority.getId().first());
        Assertions.assertEquals(57, authority.getId().last());
        Assertions.assertEquals(8, authority.getId().order());

        Assertions.assertEquals(35, authority.first());
        Assertions.assertEquals(58, authority.last());
        Assertions.assertEquals(10, authority.order());

        Assertions.assertEquals(0, datum.first());
        Assertions.assertEquals(59, datum.last());
        Assertions.assertEquals(12, datum.order());
    }

    @Test
    public void local_datum_authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              LOCAL_DATUM("datum name",AUTHORITY("LOCAL","1"))""", '(', ')');

        final NameAndAnchorDatum.EngineeringDatum datum = parser.engineeringDatum();

        Assertions.assertEquals("datum name", datum.getName().getSemantics());
        Assertions.assertEquals("LOCAL", datum.getIdentifiers().get(0).getName().getSemantics());
        Assertions.assertEquals("1", datum.getIdentifiers().get(0).getId().getSemantics());

        Assertions.assertEquals(12, datum.getName().first());
        Assertions.assertEquals(23, datum.getName().last());
        Assertions.assertEquals(2, datum.getName().order());

        final Identifier authority = datum.getIdentifiers().get(0);
        Assertions.assertEquals(35, authority.getName().first());
        Assertions.assertEquals(41, authority.getName().last());
        Assertions.assertEquals(6, authority.getName().order());

        Assertions.assertEquals(43, authority.getId().first());
        Assertions.assertEquals(45, authority.getId().last());
        Assertions.assertEquals(8, authority.getId().order());

        Assertions.assertEquals(25, authority.first());
        Assertions.assertEquals(46, authority.last());
        Assertions.assertEquals(10, authority.order());

        Assertions.assertEquals(0, datum.first());
        Assertions.assertEquals(47, datum.last());
        Assertions.assertEquals(12, datum.order());
    }
/*
    @Test
    public void toWgs84_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              TOWGS84(375,-111,431,0,0,0,0)""", '(', ')');

        final ToWgs84 toWgs84 = parser.toWgs84();

        Assertions.assertEquals(375., toWgs84.getDx().getSemantics().doubleValue());
        Assertions.assertEquals(-111., toWgs84.getDy().getSemantics().doubleValue());
        Assertions.assertEquals(431., toWgs84.getDz().getSemantics().doubleValue());
        Assertions.assertEquals(0., toWgs84.getEx().getSemantics().doubleValue());
        Assertions.assertEquals(0., toWgs84.getEy().getSemantics().doubleValue());
        Assertions.assertEquals(0., toWgs84.getEz().getSemantics().doubleValue());
        Assertions.assertEquals(0., toWgs84.getPpm().getSemantics().doubleValue());

        Assertions.assertEquals(8, toWgs84.getDx().first());
        Assertions.assertEquals(10, toWgs84.getDx().last());
        Assertions.assertEquals(4, toWgs84.getDx().order());

        Assertions.assertEquals(12, toWgs84.getDy().first());
        Assertions.assertEquals(15, toWgs84.getDy().last());
        Assertions.assertEquals(9, toWgs84.getDy().order());

        Assertions.assertEquals(17, toWgs84.getDz().first());
        Assertions.assertEquals(19, toWgs84.getDz().last());
        Assertions.assertEquals(13, toWgs84.getDz().order());

        Assertions.assertEquals(21, toWgs84.getEx().first());
        Assertions.assertEquals(21, toWgs84.getEx().last());
        Assertions.assertEquals(17, toWgs84.getEx().order());

        Assertions.assertEquals(23, toWgs84.getEy().first());
        Assertions.assertEquals(23, toWgs84.getEy().last());
        Assertions.assertEquals(21, toWgs84.getEy().order());

        Assertions.assertEquals(25, toWgs84.getEz().first());
        Assertions.assertEquals(25, toWgs84.getEz().last());
        Assertions.assertEquals(25, toWgs84.getEz().order());

        Assertions.assertEquals(27, toWgs84.getPpm().first());
        Assertions.assertEquals(27, toWgs84.getPpm().last());
        Assertions.assertEquals(29, toWgs84.getPpm().order());

        Assertions.assertEquals(0, toWgs84.first());
        Assertions.assertEquals(28, toWgs84.last());
        Assertions.assertEquals(31, toWgs84.order());
    }
*/
    @Test
    public void axis2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              AXIS("Lat",NORTH)""", '(', ')');

        final SpatialTemporalAxis axis = parser.spatialAxis();

        Assertions.assertEquals("Lat", axis.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis.getDirection().getType().getSemantics());

        Assertions.assertEquals(5, axis.getNameAbrev().first());
        Assertions.assertEquals(9, axis.getNameAbrev().last());
        Assertions.assertEquals(2, axis.getNameAbrev().order());

        Assertions.assertEquals(11, axis.getDirection().first());
        Assertions.assertEquals(15, axis.getDirection().last());
        Assertions.assertEquals(5, axis.getDirection().order());

        Assertions.assertEquals(0, axis.first());
        Assertions.assertEquals(16, axis.last());
        Assertions.assertEquals(7, axis.order());
    }

    @Test
    public void prime_meridian_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PRIMEM("Greenwitch",0)""", '(', ')');

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
    public void prime_meridian_authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PRIMEM("Greenwitch",0,AUTHORITY("EPSG","8901"))""", '(', ')');

        final PrimeMeridian primeMeridian = parser.primeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());
        Assertions.assertEquals("EPSG", primeMeridian.getIdentifiers().get(0).getName().getSemantics());
        Assertions.assertEquals("8901", primeMeridian.getIdentifiers().get(0).getId().getSemantics());

        Assertions.assertEquals(7, primeMeridian.getName().first());
        Assertions.assertEquals(18, primeMeridian.getName().last());
        Assertions.assertEquals(2, primeMeridian.getName().order());

        Assertions.assertEquals(20, primeMeridian.getIrmLongitude().first());
        Assertions.assertEquals(20, primeMeridian.getIrmLongitude().last());
        Assertions.assertEquals(6, primeMeridian.getIrmLongitude().order());

        final Identifier authority = primeMeridian.getIdentifiers().get(0);
        Assertions.assertEquals(32, authority.getName().first());
        Assertions.assertEquals(37, authority.getName().last());
        Assertions.assertEquals(10, authority.getName().order());

        Assertions.assertEquals(39, authority.getId().first());
        Assertions.assertEquals(44, authority.getId().last());
        Assertions.assertEquals(12, authority.getId().order());

        Assertions.assertEquals(22, authority.first());
        Assertions.assertEquals(45, authority.last());
        Assertions.assertEquals(14, authority.order());

        Assertions.assertEquals(0, primeMeridian.first());
        Assertions.assertEquals(46, primeMeridian.last());
        Assertions.assertEquals(16, primeMeridian.order());
    }
    @Test
    public void parameter_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PARAMETER("False_Easting",500000.0)""", '(', ')');

        final Parameter primeMeridian = parser.projectionParameter();

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

        final WktParser parser = WktParser.of("""
                                              PROJECTION("Transverse_Mercator")""", '(', ')');

        final Method.MapProjectionMethod projection = parser.mapProjectionMethod();

        Assertions.assertEquals("Transverse_Mercator", projection.getName().getSemantics());

        Assertions.assertEquals(11, projection.getName().first());
        Assertions.assertEquals(31, projection.getName().last());
        Assertions.assertEquals(2, projection.getName().order());

        Assertions.assertEquals(0, projection.first());
        Assertions.assertEquals(32, projection.last());
        Assertions.assertEquals(4, projection.order());

        Assertions.assertTrue(projection.getIdentifiers().isEmpty());
    }

    @Test
    public void projection_authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJECTION["projection name",AUTHORITY["LOCAL","10"]]""");

        final Method.MapProjectionMethod projection = parser.mapProjectionMethod();

        Assertions.assertEquals("projection name", projection.getName().getSemantics());

        Assertions.assertEquals(11, projection.getName().first());
        Assertions.assertEquals(27, projection.getName().last());
        Assertions.assertEquals(2, projection.getName().order());

        final Identifier authority = projection.getIdentifiers().get(0);

        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());

        Assertions.assertEquals(39, authority.getName().first());
        Assertions.assertEquals(45, authority.getName().last());
        Assertions.assertEquals(6, authority.getName().order());

        Assertions.assertEquals("10", authority.getId().getSemantics());

        Assertions.assertEquals(47, authority.getId().first());
        Assertions.assertEquals(50, authority.getId().last());
        Assertions.assertEquals(8, authority.getId().order());

        Assertions.assertEquals(29, authority.first());
        Assertions.assertEquals(51, authority.last());
        Assertions.assertEquals(10, authority.order());

        Assertions.assertEquals(0, projection.first());
        Assertions.assertEquals(52, projection.last());
        Assertions.assertEquals(12, projection.order());
    }

    @Test
    public void spheroid_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              SPHEROID("GRS_1980",6378137,298.257222101)""", '(', ')');

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
    public void spheroid_authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              SPHEROID("Airy 1830",637563.396,299.3249646,AUTHORITY("EPSG","7001"))""",
                '(', ')');

        final Ellipsoid spheroid = parser.ellipsoid();

        Assertions.assertEquals(WktKeyword.SPHEROID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("Airy 1830", spheroid.getName().getSemantics());
        Assertions.assertEquals(637563.396, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(299.3249646, spheroid.getInverseFlattening().getSemantics().doubleValue());

        final Identifier authority = spheroid.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());
        Assertions.assertEquals("7001", authority.getId().getSemantics());
    }

    @Test
    public void ellipsoid_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)""", '(', ')');

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

        final GeodeticDatum datum = parser.geodeticDatum(new Token[0]);

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
    public void vert_cs_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS("Newlyn",\
                                              VERT_DATUM("Ordnance Datum Newlyn",AUTHORITY("EPSG","5101")),\
                                              UNIT("metre",1,AUTHORITY("EPSG","9001")))""", '(', ')');

        final SimpleCrsShell.VerticalCrs vertCs = parser.verticalCrs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(118, vertCs.last());
        Assertions.assertEquals(36, vertCs.order());

        final NameAndAnchorDatum.VerticalDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        final Identifier datumAuthority = datum.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(62, datumAuthority.getName().first());
        Assertions.assertEquals(67, datumAuthority.getName().last());
        Assertions.assertEquals(10, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getId().getSemantics());
        Assertions.assertEquals(69, datumAuthority.getId().first());
        Assertions.assertEquals(74, datumAuthority.getId().last());
        Assertions.assertEquals(12, datumAuthority.getId().order());

        Assertions.assertEquals(52, datumAuthority.first());
        Assertions.assertEquals(75, datumAuthority.last());
        Assertions.assertEquals(14, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(76, datum.last());
        Assertions.assertEquals(16, datum.order());

        final Unit unit = vertCs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(83, unit.getName().first());
        Assertions.assertEquals(89, unit.getName().last());
        Assertions.assertEquals(20, unit.getName().order());

        Assertions.assertEquals(91, unit.getConversionFactor().first());
        Assertions.assertEquals(91, unit.getConversionFactor().last());
        Assertions.assertEquals(23, unit.getConversionFactor().order());

        final Identifier unitAuthority = unit.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(103, unitAuthority.getName().first());
        Assertions.assertEquals(108, unitAuthority.getName().last());
        Assertions.assertEquals(27, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getId().getSemantics());
        Assertions.assertEquals(110, unitAuthority.getId().first());
        Assertions.assertEquals(115, unitAuthority.getId().last());
        Assertions.assertEquals(29, unitAuthority.getId().order());

        Assertions.assertEquals(93, unitAuthority.first());
        Assertions.assertEquals(116, unitAuthority.last());
        Assertions.assertEquals(31, unitAuthority.order());

        Assertions.assertEquals(78, unit.first());
        Assertions.assertEquals(117, unit.last());
        Assertions.assertEquals(33, unit.order());

        Assertions.assertTrue(vertCs.getCoordinateSystem().getAxis().isEmpty());

        Assertions.assertTrue(vertCs.getIdentifiers().isEmpty());
    }

    @Test
    public void vert_cs_axis_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS("Newlyn",\
                                              VERT_DATUM("Ordnance Datum Newlyn",AUTHORITY("EPSG","5101")),\
                                              UNIT("metre",1,AUTHORITY("EPSG","9001")),\
                                              AXIS("Up",UP))""", '(', ')');

        final SimpleCrsShell.VerticalCrs vertCs = parser.verticalCrs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(132, vertCs.last());
        Assertions.assertEquals(45, vertCs.order());

        final NameAndAnchorDatum.VerticalDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        final Identifier datumAuthority = datum.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(62, datumAuthority.getName().first());
        Assertions.assertEquals(67, datumAuthority.getName().last());
        Assertions.assertEquals(10, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getId().getSemantics());
        Assertions.assertEquals(69, datumAuthority.getId().first());
        Assertions.assertEquals(74, datumAuthority.getId().last());
        Assertions.assertEquals(12, datumAuthority.getId().order());

        Assertions.assertEquals(52, datumAuthority.first());
        Assertions.assertEquals(75, datumAuthority.last());
        Assertions.assertEquals(14, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(76, datum.last());
        Assertions.assertEquals(16, datum.order());

        final Unit unit = vertCs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(83, unit.getName().first());
        Assertions.assertEquals(89, unit.getName().last());
        Assertions.assertEquals(20, unit.getName().order());

        Assertions.assertEquals(91, unit.getConversionFactor().first());
        Assertions.assertEquals(91, unit.getConversionFactor().last());
        Assertions.assertEquals(23, unit.getConversionFactor().order());

        final Identifier unitAuthority = unit.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(103, unitAuthority.getName().first());
        Assertions.assertEquals(108, unitAuthority.getName().last());
        Assertions.assertEquals(27, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getId().getSemantics());
        Assertions.assertEquals(110, unitAuthority.getId().first());
        Assertions.assertEquals(115, unitAuthority.getId().last());
        Assertions.assertEquals(29, unitAuthority.getId().order());

        Assertions.assertEquals(93, unitAuthority.first());
        Assertions.assertEquals(116, unitAuthority.last());
        Assertions.assertEquals(31, unitAuthority.order());

        Assertions.assertEquals(78, unit.first());
        Assertions.assertEquals(117, unit.last());
        Assertions.assertEquals(33, unit.order());

        final SpatialTemporalAxis axis = vertCs.getCoordinateSystem().getAxis().get(0);

        Assertions.assertEquals("Up", axis.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.up, axis.getDirection().getType().getSemantics());

        Assertions.assertEquals(124, axis.getNameAbrev().first());
        Assertions.assertEquals(127, axis.getNameAbrev().last());
        Assertions.assertEquals(37, axis.getNameAbrev().order());

        Assertions.assertEquals(129, axis.getDirection().first());
        Assertions.assertEquals(130, axis.getDirection().last());
        Assertions.assertEquals(40, axis.getDirection().order());

        Assertions.assertEquals(119, axis.first());
        Assertions.assertEquals(131, axis.last());
        Assertions.assertEquals(42, axis.order());

        Assertions.assertTrue(vertCs.getIdentifiers().isEmpty());
    }

    @Test
    public void vert_cs_authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS("Newlyn",\
                                              VERT_DATUM("Ordnance Datum Newlyn",AUTHORITY("EPSG","5101")),\
                                              UNIT("metre",1,AUTHORITY("EPSG","9001")),\
                                              AUTHORITY("EPSG","5701"))""", '(', ')');

        final SimpleCrsShell.VerticalCrs vertCs = parser.verticalCrs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(143, vertCs.last());
        Assertions.assertEquals(44, vertCs.order());

        final NameAndAnchorDatum.VerticalDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        final Identifier datumAuthority = datum.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(62, datumAuthority.getName().first());
        Assertions.assertEquals(67, datumAuthority.getName().last());
        Assertions.assertEquals(10, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getId().getSemantics());
        Assertions.assertEquals(69, datumAuthority.getId().first());
        Assertions.assertEquals(74, datumAuthority.getId().last());
        Assertions.assertEquals(12, datumAuthority.getId().order());

        Assertions.assertEquals(52, datumAuthority.first());
        Assertions.assertEquals(75, datumAuthority.last());
        Assertions.assertEquals(14, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(76, datum.last());
        Assertions.assertEquals(16, datum.order());

        final Unit unit = vertCs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(83, unit.getName().first());
        Assertions.assertEquals(89, unit.getName().last());
        Assertions.assertEquals(20, unit.getName().order());

        Assertions.assertEquals(91, unit.getConversionFactor().first());
        Assertions.assertEquals(91, unit.getConversionFactor().last());
        Assertions.assertEquals(23, unit.getConversionFactor().order());

        final Identifier unitAuthority = unit.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(103, unitAuthority.getName().first());
        Assertions.assertEquals(108, unitAuthority.getName().last());
        Assertions.assertEquals(27, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getId().getSemantics());
        Assertions.assertEquals(110, unitAuthority.getId().first());
        Assertions.assertEquals(115, unitAuthority.getId().last());
        Assertions.assertEquals(29, unitAuthority.getId().order());

        Assertions.assertEquals(93, unitAuthority.first());
        Assertions.assertEquals(116, unitAuthority.last());
        Assertions.assertEquals(31, unitAuthority.order());

        Assertions.assertEquals(78, unit.first());
        Assertions.assertEquals(117, unit.last());
        Assertions.assertEquals(33, unit.order());

        Assertions.assertTrue(vertCs.getCoordinateSystem().getAxis().isEmpty());

        final Identifier authority = vertCs.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());

        Assertions.assertEquals(129, authority.getName().first());
        Assertions.assertEquals(134, authority.getName().last());
        Assertions.assertEquals(38, authority.getName().order());

        Assertions.assertEquals("5701", authority.getId().getSemantics());
        Assertions.assertEquals(136, authority.getId().first());
        Assertions.assertEquals(141, authority.getId().last());
        Assertions.assertEquals(40, authority.getId().order());

        Assertions.assertEquals(119, authority.first());
        Assertions.assertEquals(142, authority.last());
        Assertions.assertEquals(42, authority.order());
    }

    @Test
    public void vert_cs_axis_authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS("Newlyn",\
                                              VERT_DATUM("Ordnance Datum Newlyn",AUTHORITY("EPSG","5101")),\
                                              UNIT("metre",1,AUTHORITY("EPSG","9001")),\
                                              AXIS("Up",UP),\
                                              AUTHORITY("EPSG","5701"))""", '(', ')');

        final SimpleCrsShell.VerticalCrs vertCs = parser.verticalCrs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(157, vertCs.last());
        Assertions.assertEquals(53, vertCs.order());

        final NameAndAnchorDatum.VerticalDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        final Identifier datumAuthority = datum.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(62, datumAuthority.getName().first());
        Assertions.assertEquals(67, datumAuthority.getName().last());
        Assertions.assertEquals(10, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getId().getSemantics());
        Assertions.assertEquals(69, datumAuthority.getId().first());
        Assertions.assertEquals(74, datumAuthority.getId().last());
        Assertions.assertEquals(12, datumAuthority.getId().order());

        Assertions.assertEquals(52, datumAuthority.first());
        Assertions.assertEquals(75, datumAuthority.last());
        Assertions.assertEquals(14, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(76, datum.last());
        Assertions.assertEquals(16, datum.order());

        final Unit unit = vertCs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(83, unit.getName().first());
        Assertions.assertEquals(89, unit.getName().last());
        Assertions.assertEquals(20, unit.getName().order());

        Assertions.assertEquals(91, unit.getConversionFactor().first());
        Assertions.assertEquals(91, unit.getConversionFactor().last());
        Assertions.assertEquals(23, unit.getConversionFactor().order());

        final Identifier unitAuthority = unit.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(103, unitAuthority.getName().first());
        Assertions.assertEquals(108, unitAuthority.getName().last());
        Assertions.assertEquals(27, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getId().getSemantics());
        Assertions.assertEquals(110, unitAuthority.getId().first());
        Assertions.assertEquals(115, unitAuthority.getId().last());
        Assertions.assertEquals(29, unitAuthority.getId().order());

        Assertions.assertEquals(93, unitAuthority.first());
        Assertions.assertEquals(116, unitAuthority.last());
        Assertions.assertEquals(31, unitAuthority.order());

        Assertions.assertEquals(78, unit.first());
        Assertions.assertEquals(117, unit.last());
        Assertions.assertEquals(33, unit.order());

        final SpatialTemporalAxis axis = vertCs.getCoordinateSystem().getAxis().get(0);

        Assertions.assertEquals("Up", axis.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.up, axis.getDirection().getType().getSemantics());

        Assertions.assertEquals(124, axis.getNameAbrev().first());
        Assertions.assertEquals(127, axis.getNameAbrev().last());
        Assertions.assertEquals(37, axis.getNameAbrev().order());

        Assertions.assertEquals(129, axis.getDirection().first());
        Assertions.assertEquals(130, axis.getDirection().last());
        Assertions.assertEquals(40, axis.getDirection().order());

        Assertions.assertEquals(119, axis.first());
        Assertions.assertEquals(131, axis.last());
        Assertions.assertEquals(42, axis.order());

        final Identifier authority = vertCs.getIdentifiers().get(0);

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());

        Assertions.assertEquals(143, authority.getName().first());
        Assertions.assertEquals(148, authority.getName().last());
        Assertions.assertEquals(47, authority.getName().order());

        Assertions.assertEquals("5701", authority.getId().getSemantics());
        Assertions.assertEquals(150, authority.getId().first());
        Assertions.assertEquals(155, authority.getId().last());
        Assertions.assertEquals(49, authority.getId().order());

        Assertions.assertEquals(133, authority.first());
        Assertions.assertEquals(156, authority.last());
        Assertions.assertEquals(51, authority.order());
    }

    @Test
    public void local_cs_axis_authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              LOCAL_CS("local cs name",\
                                              LOCAL_DATUM("local datum name",AUTHORITY("LOCAL","2")),\
                                              UNIT("metre",1,AUTHORITY("EPSG","9001")),\
                                              AXIS("Up",UP),\
                                              AXIS("East",EAST),\
                                              AUTHORITY("LOCAL","3"))""", '(', ')');

        final SimpleCrsShell.EngineeringCrs localCs = parser.engineeringCrs();

        Assertions.assertEquals("local cs name", localCs.getName().getSemantics());

        final NameAndAnchorDatum.EngineeringDatum datum = localCs.getDatum();
        Assertions.assertEquals("local datum name", datum.getName().getSemantics());

        final Identifier datumAuthority = datum.getIdentifiers().get(0);
        Assertions.assertEquals("LOCAL", datumAuthority.getName().getSemantics());
        Assertions.assertEquals("2", datumAuthority.getId().getSemantics());

        final SpatialCoordinateSystem cs = localCs.getCoordinateSystem();

        final Unit unit = cs.getUnit();
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        final Identifier unitAuthority = unit.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());
        Assertions.assertEquals("9001", unitAuthority.getId().getSemantics());

        Assertions.assertEquals(2, cs.getAxis().size());

        final SpatialTemporalAxis axis0 = cs.getAxis().get(0);
        Assertions.assertEquals("Up", axis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.up, axis0.getDirection().getType().getSemantics());

        final SpatialTemporalAxis axis1 = cs.getAxis().get(1);
        Assertions.assertEquals("East", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());

        final Identifier authority = localCs.getIdentifiers().get(0);
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("3", authority.getId().getSemantics());
    }
/*
    @Test
    public void fitted_cs_axis_authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              FITTED_CS("fitted cs name",\
                                              CONCAT_MT(PASSTHROUGH_MT(4,INVERSE_MT(PARAM_MT("classification name",\
                                              PARAMETER("first",0.0174532925199433),\
                                              PARAMETER("second",0.174532925199433)))),\
                                              CONCAT_MT(PARAM_MT("second name"))),\
                                              LOCAL_CS("local cs name",\
                                              LOCAL_DATUM("local datum name",1,AUTHORITY("LOCAL","2")),\
                                              UNIT("metre",1,AUTHORITY("EPSG","9001")),\
                                              AXIS("Up",UP),\
                                              AXIS("East",EAST),\
                                              AUTHORITY("LOCAL","3")))""", '(', ')');

        final FittedCs fitted_cs = parser.fittedCs();

        Assertions.assertTrue(fitted_cs.getMathTransform() instanceof ConcatMt);

        final ConcatMt concat_mt = (ConcatMt) fitted_cs.getMathTransform();

        Assertions.assertEquals(2, concat_mt.getTransforms().size());
        Assertions.assertTrue(concat_mt.getTransforms().get(0) instanceof PassthroughMt);
        Assertions.assertTrue(concat_mt.getTransforms().get(1) instanceof ConcatMt);

        final PassthroughMt passthrough_mt = (PassthroughMt) concat_mt.getTransforms().get(0);

        Assertions.assertEquals(4, passthrough_mt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthrough_mt.getTransform() instanceof InverseMt);

        final InverseMt inverse_mt = (InverseMt) passthrough_mt.getTransform();

        Assertions.assertTrue(inverse_mt.getTransform() instanceof ParamMt);

        final ParamMt param_mt = (ParamMt) inverse_mt.getTransform();

        Assertions.assertEquals("classification name", param_mt.getName().getSemantics());

        final List<Parameter> parameters = param_mt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());

        final ConcatMt incl_concat_mt = (ConcatMt) concat_mt.getTransforms().get(1);
        Assertions.assertEquals(1, incl_concat_mt.getTransforms().size());
        Assertions.assertTrue(incl_concat_mt.getTransforms().get(0) instanceof ParamMt);

        final ParamMt incl_param_mt = (ParamMt) incl_concat_mt.getTransforms().get(0);

        Assertions.assertEquals("second name", incl_param_mt.getName().getSemantics());

        Assertions.assertTrue(incl_param_mt.getParameters().isEmpty());

        Assertions.assertTrue(fitted_cs.getCoordinateSystem() instanceof LocalCs);

        final LocalCs localCs = (LocalCs) fitted_cs.getCoordinateSystem();

        Assertions.assertEquals("local cs name", localCs.getName().getSemantics());

        final LocalDatum datum = localCs.getDatum();
        Assertions.assertEquals("local datum name", datum.getName().getSemantics());
        Assertions.assertEquals(1, datum.getDatumType().getSemantics().intValue());

        final Authority datumAuthority = datum.getAuthority();
        Assertions.assertEquals("LOCAL", datumAuthority.getName().getSemantics());
        Assertions.assertEquals("2", datumAuthority.getCode().getSemantics());

        final Unit unit = localCs.getLinearUnit();
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        final Authority unitAuthority = unit.getAuthority();
        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());
        Assertions.assertEquals("9001", unitAuthority.getCode().getSemantics());

        Assertions.assertEquals(2, localCs.getAxis().size());

        final SpatialTemporalAxis axis0 = localCs.getAxis().get(0);
        Assertions.assertEquals("Up", axis0.getName().getSemantics());
        Assertions.assertEquals(Direction.UP, axis0.getDirection().getSemantics());

        final SpatialTemporalAxis axis1 = localCs.getAxis().get(1);
        Assertions.assertEquals("East", axis1.getName().getSemantics());
        Assertions.assertEquals(Direction.EAST, axis1.getDirection().getSemantics());

        final Authority authority = localCs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("3", authority.getCode().getSemantics());
    }

    @Test
    public void compd_cs_axis_authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              COMPD_CS("compound cs",\
                                              FITTED_CS("fitted cs name",\
                                              CONCAT_MT(PASSTHROUGH_MT(4,INVERSE_MT(PARAM_MT("classification name",\
                                              PARAMETER("first",0.0174532925199433),\
                                              PARAMETER("second",0.174532925199433)))),\
                                              CONCAT_MT(PARAM_MT("second name"))),\
                                              LOCAL_CS("local cs name",\
                                              LOCAL_DATUM("local datum name",1,AUTHORITY("LOCAL","2")),\
                                              UNIT("metre",1,AUTHORITY("EPSG","9001")),\
                                              AXIS("Up",UP),\
                                              AXIS("East",EAST),\
                                              AUTHORITY("LOCAL","3"))),\
                                              VERT_CS("Newlyn",\
                                              VERT_DATUM("Ordnance Datum Newlyn",2005,AUTHORITY("EPSG","5101")),\
                                              UNIT("metre",1,AUTHORITY("EPSG","9001")),\
                                              AXIS("Up",UP),\
                                              AUTHORITY("EPSG","5701")),\
                                              AUTHORITY("LOCAL","10"))""", '(', ')');

        final CompdCs compd_cs = parser.compdCs();

        Assertions.assertTrue(compd_cs.getHead() instanceof FittedCs);

        final FittedCs fitted_cs = (FittedCs) compd_cs.getHead();

        Assertions.assertTrue(fitted_cs.getMathTransform() instanceof ConcatMt);

        final ConcatMt concat_mt = (ConcatMt) fitted_cs.getMathTransform();

        Assertions.assertEquals(2, concat_mt.getTransforms().size());
        Assertions.assertTrue(concat_mt.getTransforms().get(0) instanceof PassthroughMt);
        Assertions.assertTrue(concat_mt.getTransforms().get(1) instanceof ConcatMt);

        final PassthroughMt passthrough_mt = (PassthroughMt) concat_mt.getTransforms().get(0);

        Assertions.assertEquals(4, passthrough_mt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthrough_mt.getTransform() instanceof InverseMt);

        final InverseMt inverse_mt = (InverseMt) passthrough_mt.getTransform();

        Assertions.assertTrue(inverse_mt.getTransform() instanceof ParamMt);

        final ParamMt param_mt = (ParamMt) inverse_mt.getTransform();

        Assertions.assertEquals("classification name", param_mt.getName().getSemantics());

        final List<Parameter> parameters = param_mt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());

        final ConcatMt incl_concat_mt = (ConcatMt) concat_mt.getTransforms().get(1);
        Assertions.assertEquals(1, incl_concat_mt.getTransforms().size());
        Assertions.assertTrue(incl_concat_mt.getTransforms().get(0) instanceof ParamMt);

        final ParamMt incl_param_mt = (ParamMt) incl_concat_mt.getTransforms().get(0);

        Assertions.assertEquals("second name", incl_param_mt.getName().getSemantics());

        Assertions.assertTrue(incl_param_mt.getParameters().isEmpty());

        Assertions.assertTrue(fitted_cs.getCoordinateSystem() instanceof LocalCs);

        final LocalCs localCs = (LocalCs) fitted_cs.getCoordinateSystem();

        Assertions.assertEquals("local cs name", localCs.getName().getSemantics());

        final LocalDatum datum = localCs.getDatum();
        Assertions.assertEquals("local datum name", datum.getName().getSemantics());
        Assertions.assertEquals(1, datum.getDatumType().getSemantics().intValue());

        final Authority datumAuthority = datum.getAuthority();
        Assertions.assertEquals("LOCAL", datumAuthority.getName().getSemantics());
        Assertions.assertEquals("2", datumAuthority.getCode().getSemantics());

        final Unit unit = localCs.getLinearUnit();
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        final Authority unitAuthority = unit.getAuthority();
        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());
        Assertions.assertEquals("9001", unitAuthority.getCode().getSemantics());

        Assertions.assertEquals(2, localCs.getAxis().size());

        final SpatialTemporalAxis axis0 = localCs.getAxis().get(0);
        Assertions.assertEquals("Up", axis0.getName().getSemantics());
        Assertions.assertEquals(Direction.UP, axis0.getDirection().getSemantics());

        final SpatialTemporalAxis axis1 = localCs.getAxis().get(1);
        Assertions.assertEquals("East", axis1.getName().getSemantics());
        Assertions.assertEquals(Direction.EAST, axis1.getDirection().getSemantics());

        final Authority authority = localCs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("3", authority.getCode().getSemantics());


        Assertions.assertTrue(compd_cs.getTail() instanceof VertCs);

        final VertCs vertCs = (VertCs) compd_cs.getTail();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        final VertDatum vertCsDatum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", vertCsDatum.getName().getSemantics());
        Assertions.assertEquals(2005, vertCsDatum.getDatumType().getSemantics().intValue());

        final Authority vertCsDatumAuthority = vertCsDatum.getAuthority();
        Assertions.assertEquals("EPSG", vertCsDatumAuthority.getName().getSemantics());
        Assertions.assertEquals("5101", vertCsDatumAuthority.getCode().getSemantics());

        final Unit vertCsUnit = vertCs.getLinearUnit();

        Assertions.assertEquals("metre", vertCsUnit.getName().getSemantics());
        Assertions.assertEquals(1., vertCsUnit.getConversionFactor().getSemantics().doubleValue());

        final Authority vertCsUnitAuthority = vertCsUnit.getAuthority();

        Assertions.assertEquals("EPSG", vertCsUnitAuthority.getName().getSemantics());

        Assertions.assertEquals("9001", vertCsUnitAuthority.getCode().getSemantics());

        final SpatialTemporalAxis spatialAxis = vertCs.getAxis();

        Assertions.assertEquals("Up", spatialAxis.getName().getSemantics());
        Assertions.assertEquals(Direction.UP, spatialAxis.getDirection().getSemantics());

        final Authority vertCsAuthority = vertCs.getAuthority();

        Assertions.assertEquals("EPSG", vertCsAuthority.getName().getSemantics());
        Assertions.assertEquals("5701", vertCsAuthority.getCode().getSemantics());


        final Authority compdCsAuthority = compd_cs.getAuthority();
        Assertions.assertEquals("LOCAL", compdCsAuthority.getName().getSemantics());
        Assertions.assertEquals("10", compdCsAuthority.getCode().getSemantics());
    }
*/
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
        Assertions.assertEquals(61, datum.getName().last());
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
    public void geoccs_axis_authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),UNIT("Degree",0.0174532925199433),\
                                              AXIS("E",EAST),AXIS("N",NORTH),AXIS("U",UP),\
                                              AUTHORITY("LOCAL","1"))""", '(', ')');

        final GeodeticCrs geoccs = parser.geodeticCrs();

        Assertions.assertEquals("GCS_North_American_1983", geoccs.getName().getSemantics());

        final GeodeticDatum datum = geoccs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        final PrimeMeridian primeMeridian = geoccs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        final Unit unit = geoccs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        final SpatialTemporalAxis axis1 = geoccs.getCoordinateSystem().getAxis().get(0);
        Assertions.assertEquals("E", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());
        final SpatialTemporalAxis axis2 = geoccs.getCoordinateSystem().getAxis().get(1);
        Assertions.assertEquals("N", axis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis2.getDirection().getType().getSemantics());
        final SpatialTemporalAxis axis3 = geoccs.getCoordinateSystem().getAxis().get(2);
        Assertions.assertEquals("U", axis3.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.up, axis3.getDirection().getType().getSemantics());
        final Identifier authority = geoccs.getIdentifiers().get(0);
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("1", authority.getId().getSemantics());
    }

    @Test
    public void geogcs_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),UNIT("Degree",0.0174532925199433))""", '(', ')');

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

        /*final Unit linear = geogcs.getLinearUnit();

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
        Assertions.assertEquals(58, linear.order());*/
    }

    @Test
    public void geogcs_axis_authority_test_2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),UNIT("Degree",0.0174532925199433),\
                                              AXIS("E",EAST),AXIS("N",NORTH),AUTHORITY("LOCAL","1"))""", '(', ')');

        final GeodeticCrs geogcs = parser.geodeticCrs();

        Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

        final GeodeticDatum datum = geogcs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        final Ellipsoid spheroid = datum.getEllipsoid();

        Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        final Unit angular = geogcs.getCoordinateSystem().getUnit();

        Assertions.assertEquals("Degree", angular.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

        final SpatialTemporalAxis axis1 = geogcs.getCoordinateSystem().getAxis().get(0);
        Assertions.assertEquals("E", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());
        final SpatialTemporalAxis axis2 = geogcs.getCoordinateSystem().getAxis().get(1);
        Assertions.assertEquals("N", axis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis2.getDirection().getType().getSemantics());

        final Identifier authority = geogcs.getIdentifiers().get(0);
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("1", authority.getId().getSemantics());
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

        Assertions.assertEquals(197, method.first());
        Assertions.assertEquals(229, method.last());
        Assertions.assertEquals(57, method.order());

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
    public void projcs_axis_test_2() throws LanguageException {

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
                                              UNIT("Meter",1.0),\
                                              AXIS("Easting",EAST),AXIS("Northing",NORTH))""", '(', ')');

        final DerivedCrs.ProjectedCrs projcs = parser.projectedCrs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(466, projcs.last());
        Assertions.assertEquals(151, projcs.order());

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

        Assertions.assertEquals(197, method.first());
        Assertions.assertEquals(229, method.last());
        Assertions.assertEquals(57, method.order());

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

        final SpatialTemporalAxis axis1 = projcs.getCoordinateSystem().getAxis().get(0);

        Assertions.assertEquals("Easting", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());

        final SpatialTemporalAxis axis2 = projcs.getCoordinateSystem().getAxis().get(1);

        Assertions.assertEquals("Northing", axis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis2.getDirection().getType().getSemantics());
    }

    @Test
    public void projcs_axis_authority_test_2() throws LanguageException {

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
                                              UNIT("Meter",1.0),\
                                              AXIS("Easting",EAST),AXIS("Northing",NORTH),\
                                              AUTHORITY("LOCAL","10"))""", '(', ')');

        final DerivedCrs.ProjectedCrs projcs = parser.projectedCrs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(490, projcs.last());
        Assertions.assertEquals(159, projcs.order());

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

        Assertions.assertEquals(197, method.first());
        Assertions.assertEquals(229, method.last());
        Assertions.assertEquals(57, method.order());

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

        final SpatialTemporalAxis axis1 = projcs.getCoordinateSystem().getAxis().get(0);

        Assertions.assertEquals("Easting", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());

        final SpatialTemporalAxis axis2 = projcs.getCoordinateSystem().getAxis().get(1);

        Assertions.assertEquals("Northing", axis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis2.getDirection().getType().getSemantics());

        final Identifier authority = projcs.getIdentifiers().get(0);

        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("10", authority.getId().getSemantics());
    }

    @Test
    public void projcs_authority_test_2() throws LanguageException {

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
                                              UNIT("Meter",1.0),\
                                              AUTHORITY("LOCAL","10"))""", '(', ')');

        final DerivedCrs.ProjectedCrs projcs = parser.projectedCrs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(446, projcs.last());
        Assertions.assertEquals(141, projcs.order());

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

        Assertions.assertEquals(197, method.first());
        Assertions.assertEquals(229, method.last());
        Assertions.assertEquals(57, method.order());

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

        final Identifier authority = projcs.getIdentifiers().get(0);

        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("10", authority.getId().getSemantics());
    }

    @Test
    public void spatial_reference_system_test_2_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),UNIT("Degree",0.0174532925199433))""", '(', ')');

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

    @SectionReference(type = SectionReferenceType.SECTION, id = "C.4.1") // pas de rétrocompatibilité ellipsoidal CS 3D
    @Test
    public void spatial_reference_system_test_2_3() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree",0.0174532925199433),UNIT("Meter",1.0))""", '(', ')');

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
        /*
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
        */
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

        Assertions.assertEquals(197, method.first());
        Assertions.assertEquals(229, method.last());
        Assertions.assertEquals(57, method.order());

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

        try (DefaultStreamScanner scanner = new DefaultStreamScanner(new BufferedReader(new InputStreamReader(
                WktParserBracketTest.class.getResourceAsStream("example3a"))))) {

            final WktLexer lexer = new WktLexer(scanner, new ArrayList<>(), '[', ']');
            lexer.initialize();
            final WktParser parser = new WktParser(lexer);

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

            Assertions.assertEquals(197, method.first());
            Assertions.assertEquals(229, method.last());
            Assertions.assertEquals(57, method.order());

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

        try (DefaultStreamScanner scanner = new DefaultStreamScanner(new BufferedReader(new InputStreamReader(
                WktParserBracketTest.class.getResourceAsStream("example3b"))))) {

            final WktLexer lexer = new WktLexer(scanner, new ArrayList<>(), '(', ')');
            lexer.initialize();
            final WktParser parser = new WktParser(lexer);

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

            Assertions.assertEquals(197, method.first());
            Assertions.assertEquals(229, method.last());
            Assertions.assertEquals(57, method.order());

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

    @Test // vérification du support de l'indentation
    public void projcs_test_3c() throws LanguageException, IOException {

        try (DefaultStreamScanner scanner = new DefaultStreamScanner(new BufferedReader(new InputStreamReader(
                WktParserBracketTest.class.getResourceAsStream("example3c"))))) {

            final WktLexer lexer = new WktLexer(scanner, new ArrayList<>(), '[', ']');
            lexer.initialize();
            final WktParser parser = new WktParser(lexer);

            final DerivedCrs.ProjectedCrs projcs = parser.projectedCrs();

            Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

            final BaseGeodeticCrs geogcs = projcs.getBaseCrs();

            Assertions.assertEquals("GCS_North_American_1983", geogcs.getName().getSemantics());

            final GeodeticDatum datum = geogcs.getDatum();

            Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

            final Ellipsoid spheroid = datum.getEllipsoid();

            Assertions.assertEquals(WktKeyword.ELLIPSOID, spheroid.getLabel().getSemantics());
            Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
            Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
            Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

            final PrimeMeridian primeMeridian = geogcs.getDatum().getPrimeMeridian();

            Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
            Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());

            final Unit angular = geogcs.getUnit();

            Assertions.assertEquals("Degree", angular.getName().getSemantics());
            Assertions.assertEquals(0.0174532925199433, angular.getConversionFactor().getSemantics().doubleValue());

            final Operation.MapProjection projection = projcs.getOperation();

            final Method.MapProjectionMethod method = projection.getMethod();

            Assertions.assertEquals("Transverse_Mercator", method.getName().getSemantics());

            final List<Parameter> parameters = projection.getParameters();

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

            final Unit unit = projcs.getCoordinateSystem().getUnit();

            Assertions.assertEquals("Meter", unit.getName().getSemantics());
            Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());
        }
    }

}
