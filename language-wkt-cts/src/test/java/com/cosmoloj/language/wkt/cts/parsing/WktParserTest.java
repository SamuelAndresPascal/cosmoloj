package com.cosmoloj.language.wkt.cts.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.exception.ParserException;
import com.cosmoloj.language.common.impl.parsing.DefaultStreamScanner;
import com.cosmoloj.language.wkt.cts.expression.Authority;
import com.cosmoloj.language.wkt.cts.expression.Axis;
import com.cosmoloj.language.wkt.cts.expression.CompdCs;
import com.cosmoloj.language.wkt.cts.expression.ConcatMt;
import com.cosmoloj.language.wkt.cts.expression.Datum;
import com.cosmoloj.language.wkt.cts.expression.FittedCs;
import com.cosmoloj.language.wkt.cts.expression.GeocentricCs;
import com.cosmoloj.language.wkt.cts.expression.GeographicCs;
import com.cosmoloj.language.wkt.cts.expression.InverseMt;
import com.cosmoloj.language.wkt.cts.expression.LocalCs;
import com.cosmoloj.language.wkt.cts.expression.LocalDatum;
import com.cosmoloj.language.wkt.cts.expression.ParamMt;
import com.cosmoloj.language.wkt.cts.expression.PassthroughMt;
import com.cosmoloj.language.wkt.cts.expression.PrimeMeridian;
import com.cosmoloj.language.wkt.cts.expression.ProjectedCs;
import com.cosmoloj.language.wkt.cts.expression.Projection;
import com.cosmoloj.language.wkt.cts.expression.Spheroid;
import com.cosmoloj.language.wkt.cts.expression.ToWgs84;
import com.cosmoloj.language.wkt.cts.expression.Unit;
import com.cosmoloj.language.wkt.cts.expression.VertCs;
import com.cosmoloj.language.wkt.cts.expression.VertDatum;
import com.cosmoloj.language.wkt.cts.lexeme.AxisDirectionName;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.expression.Parameter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class WktParserTest {

    private static final int LD = '[';
    private static final int RD = ']';

    @Test
    public void authority1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              AUTHORITY["EPSG","8901"]""");

        final Authority authority = parser.authority();

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());
        Assertions.assertEquals("8901", authority.getCode().getSemantics());

        Assertions.assertEquals(10, authority.getName().first());
        Assertions.assertEquals(15, authority.getName().last());
        Assertions.assertEquals(2, authority.getName().order());

        Assertions.assertEquals(17, authority.getCode().first());
        Assertions.assertEquals(22, authority.getCode().last());
        Assertions.assertEquals(4, authority.getCode().order());

        Assertions.assertEquals(0, authority.first());
        Assertions.assertEquals(23, authority.last());
        Assertions.assertEquals(6, authority.order());
    }

    @Test
    public void paramMt() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PARAM_MT["classification name",\
                                              PARAMETER["first",0.0174532925199433],\
                                              PARAMETER["second",0.174532925199433]]""");

        final ParamMt paramMt = parser.paramMt();

        Assertions.assertEquals("classification name", paramMt.getName().getSemantics());

        final List<Parameter> parameters = paramMt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());
    }

    @Test
    public void inverseMt1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              INVERSE_MT[PARAM_MT["classification name",\
                                              PARAMETER["first",0.0174532925199433],\
                                              PARAMETER["second",0.174532925199433]]]""");

        final InverseMt inverseMt = parser.invMt();

        Assertions.assertTrue(inverseMt.getTransform() instanceof ParamMt);

        final ParamMt paramMt = (ParamMt) inverseMt.getTransform();

        Assertions.assertEquals("classification name", paramMt.getName().getSemantics());

        final List<Parameter> parameters = paramMt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());
    }

    @Test
    public void passthroughMt1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PASSTHROUGH_MT[4,INVERSE_MT[PARAM_MT["classification name",\
                                              PARAMETER["first",0.0174532925199433],\
                                              PARAMETER["second",0.174532925199433]]]]""");

        final PassthroughMt passthroughMt = parser.passthroughMt();

        Assertions.assertEquals(4, passthroughMt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthroughMt.getTransform() instanceof InverseMt);

        final InverseMt inverseMt = (InverseMt) passthroughMt.getTransform();

        Assertions.assertTrue(inverseMt.getTransform() instanceof ParamMt);

        final ParamMt paramMt = (ParamMt) inverseMt.getTransform();

        Assertions.assertEquals("classification name", paramMt.getName().getSemantics());

        final List<Parameter> parameters = paramMt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());
    }

    @Test
    public void concatMt1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              CONCAT_MT[PASSTHROUGH_MT[4,INVERSE_MT[PARAM_MT["classification name",\
                                              PARAMETER["first",0.0174532925199433],\
                                              PARAMETER["second",0.174532925199433]]]],\
                                              CONCAT_MT[PARAM_MT["second name"]]]""");

        final ConcatMt concatMt = parser.concatMt();

        Assertions.assertEquals(2, concatMt.getTransforms().size());
        Assertions.assertTrue(concatMt.getTransforms().get(0) instanceof PassthroughMt);
        Assertions.assertTrue(concatMt.getTransforms().get(1) instanceof ConcatMt);

        final PassthroughMt passthroughMt = (PassthroughMt) concatMt.getTransforms().get(0);

        Assertions.assertEquals(4, passthroughMt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthroughMt.getTransform() instanceof InverseMt);

        final InverseMt inverseMt = (InverseMt) passthroughMt.getTransform();

        Assertions.assertTrue(inverseMt.getTransform() instanceof ParamMt);

        final ParamMt paramMt = (ParamMt) inverseMt.getTransform();

        Assertions.assertEquals("classification name", paramMt.getName().getSemantics());

        final List<Parameter> parameters = paramMt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());

        final ConcatMt inclConcatMt = (ConcatMt) concatMt.getTransforms().get(1);
        Assertions.assertEquals(1, inclConcatMt.getTransforms().size());
        Assertions.assertTrue(inclConcatMt.getTransforms().get(0) instanceof ParamMt);

        final ParamMt inclParamMt = (ParamMt) inclConcatMt.getTransforms().get(0);

        Assertions.assertEquals("second name", inclParamMt.getName().getSemantics());

        Assertions.assertTrue(inclParamMt.getParameters().isEmpty());
    }

    @Test
    public void unit1() throws LanguageException {

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
        Assertions.assertEquals(8, unit.getConversionFactor().order());

        Assertions.assertNull(unit.getAuthority());

        Assertions.assertEquals(0, unit.first());
        Assertions.assertEquals(32, unit.last());
        Assertions.assertEquals(10, unit.order());
    }

    @Test
    public void unitAuthority1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              UNIT["metre",1,AUTHORITY["EPSG","9001"]]""");

        final Unit unit = parser.unit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals("EPSG", unit.getAuthority().getName().getSemantics());
        Assertions.assertEquals("9001", unit.getAuthority().getCode().getSemantics());

        Assertions.assertEquals(5, unit.getName().first());
        Assertions.assertEquals(11, unit.getName().last());
        Assertions.assertEquals(2, unit.getName().order());

        Assertions.assertEquals(13, unit.getConversionFactor().first());
        Assertions.assertEquals(13, unit.getConversionFactor().last());
        Assertions.assertEquals(6, unit.getConversionFactor().order());

        final Authority authority = unit.getAuthority();
        Assertions.assertEquals(25, authority.getName().first());
        Assertions.assertEquals(30, authority.getName().last());
        Assertions.assertEquals(10, authority.getName().order());

        Assertions.assertEquals(32, authority.getCode().first());
        Assertions.assertEquals(37, authority.getCode().last());
        Assertions.assertEquals(12, authority.getCode().order());

        Assertions.assertEquals(15, authority.first());
        Assertions.assertEquals(38, authority.last());
        Assertions.assertEquals(14, authority.order());

        Assertions.assertEquals(0, unit.first());
        Assertions.assertEquals(39, unit.last());
        Assertions.assertEquals(16, unit.order());
    }

    @Test
    public void vertDatumAuthority1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_DATUM["Ordnance Datum Newlyn",2005,AUTHORITY["EPSG","5101"]]""");

        final VertDatum datum = parser.vertDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());
        Assertions.assertEquals(2005, datum.getDatumType().getSemantics().intValue());
        Assertions.assertEquals("EPSG", datum.getAuthority().getName().getSemantics());
        Assertions.assertEquals("5101", datum.getAuthority().getCode().getSemantics());

        Assertions.assertEquals(11, datum.getName().first());
        Assertions.assertEquals(33, datum.getName().last());
        Assertions.assertEquals(2, datum.getName().order());

        Assertions.assertEquals(35, datum.getDatumType().first());
        Assertions.assertEquals(38, datum.getDatumType().last());
        Assertions.assertEquals(6, datum.getDatumType().order());

        final Authority authority = datum.getAuthority();
        Assertions.assertEquals(50, authority.getName().first());
        Assertions.assertEquals(55, authority.getName().last());
        Assertions.assertEquals(10, authority.getName().order());

        Assertions.assertEquals(57, authority.getCode().first());
        Assertions.assertEquals(62, authority.getCode().last());
        Assertions.assertEquals(12, authority.getCode().order());

        Assertions.assertEquals(40, authority.first());
        Assertions.assertEquals(63, authority.last());
        Assertions.assertEquals(14, authority.order());

        Assertions.assertEquals(0, datum.first());
        Assertions.assertEquals(64, datum.last());
        Assertions.assertEquals(16, datum.order());
    }

    @Test
    public void localDatumAuthority1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              LOCAL_DATUM["datum name",1,AUTHORITY["LOCAL","1"]]""");

        final LocalDatum datum = parser.localDatum();

        Assertions.assertEquals("datum name", datum.getName().getSemantics());
        Assertions.assertEquals(1, datum.getDatumType().getSemantics().intValue());
        Assertions.assertEquals("LOCAL", datum.getAuthority().getName().getSemantics());
        Assertions.assertEquals("1", datum.getAuthority().getCode().getSemantics());

        Assertions.assertEquals(12, datum.getName().first());
        Assertions.assertEquals(23, datum.getName().last());
        Assertions.assertEquals(2, datum.getName().order());

        Assertions.assertEquals(25, datum.getDatumType().first());
        Assertions.assertEquals(25, datum.getDatumType().last());
        Assertions.assertEquals(6, datum.getDatumType().order());

        final Authority authority = datum.getAuthority();
        Assertions.assertEquals(37, authority.getName().first());
        Assertions.assertEquals(43, authority.getName().last());
        Assertions.assertEquals(10, authority.getName().order());

        Assertions.assertEquals(45, authority.getCode().first());
        Assertions.assertEquals(47, authority.getCode().last());
        Assertions.assertEquals(12, authority.getCode().order());

        Assertions.assertEquals(27, authority.first());
        Assertions.assertEquals(48, authority.last());
        Assertions.assertEquals(14, authority.order());

        Assertions.assertEquals(0, datum.first());
        Assertions.assertEquals(49, datum.last());
        Assertions.assertEquals(16, datum.order());
    }

    @Test
    public void toWgs84a() throws LanguageException {

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

    @Test
    public void axis1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              AXIS["Lat",NORTH]""");

        final Axis axis = parser.axis();

        Assertions.assertEquals("Lat", axis.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.NORTH, axis.getDirection().getSemantics());

        Assertions.assertEquals(5, axis.getName().first());
        Assertions.assertEquals(9, axis.getName().last());
        Assertions.assertEquals(2, axis.getName().order());

        Assertions.assertEquals(11, axis.getDirection().first());
        Assertions.assertEquals(15, axis.getDirection().last());
        Assertions.assertEquals(4, axis.getDirection().order());

        Assertions.assertEquals(0, axis.first());
        Assertions.assertEquals(16, axis.last());
        Assertions.assertEquals(6, axis.order());
    }

    @Test
    public void primeMeridian1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PRIMEM["Greenwitch",0]""");

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
    public void primeMeridianAuthority1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PRIMEM["Greenwitch",0,AUTHORITY["EPSG","8901"]]""");

        final PrimeMeridian primeMeridian = parser.primeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());
        Assertions.assertEquals("EPSG", primeMeridian.getAuthority().getName().getSemantics());
        Assertions.assertEquals("8901", primeMeridian.getAuthority().getCode().getSemantics());

        Assertions.assertEquals(7, primeMeridian.getName().first());
        Assertions.assertEquals(18, primeMeridian.getName().last());
        Assertions.assertEquals(2, primeMeridian.getName().order());

        Assertions.assertEquals(20, primeMeridian.getLongitude().first());
        Assertions.assertEquals(20, primeMeridian.getLongitude().last());
        Assertions.assertEquals(6, primeMeridian.getLongitude().order());

        final Authority authority = primeMeridian.getAuthority();
        Assertions.assertEquals(32, authority.getName().first());
        Assertions.assertEquals(37, authority.getName().last());
        Assertions.assertEquals(10, authority.getName().order());

        Assertions.assertEquals(39, authority.getCode().first());
        Assertions.assertEquals(44, authority.getCode().last());
        Assertions.assertEquals(12, authority.getCode().order());

        Assertions.assertEquals(22, authority.first());
        Assertions.assertEquals(45, authority.last());
        Assertions.assertEquals(14, authority.order());

        Assertions.assertEquals(0, primeMeridian.first());
        Assertions.assertEquals(46, primeMeridian.last());
        Assertions.assertEquals(16, primeMeridian.order());
    }

    @Test
    public void parameter1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PARAMETER["False_Easting",500000.0]""");

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
    public void projection1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJECTION["Transverse_Mercator"]""");

        final Projection projection = parser.projection();

        Assertions.assertEquals("Transverse_Mercator", projection.getName().getSemantics());

        Assertions.assertEquals(11, projection.getName().first());
        Assertions.assertEquals(31, projection.getName().last());
        Assertions.assertEquals(2, projection.getName().order());

        Assertions.assertEquals(0, projection.first());
        Assertions.assertEquals(32, projection.last());
        Assertions.assertEquals(4, projection.order());

        Assertions.assertNull(projection.getAuthority());
    }

    @Test
    public void projectionAuthority1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJECTION["projection name",AUTHORITY["LOCAL","10"]]""");

        final Projection projection = parser.projection();

        Assertions.assertEquals("projection name", projection.getName().getSemantics());

        Assertions.assertEquals(11, projection.getName().first());
        Assertions.assertEquals(27, projection.getName().last());
        Assertions.assertEquals(2, projection.getName().order());

        final Authority authority = projection.getAuthority();

        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());

        Assertions.assertEquals(39, authority.getName().first());
        Assertions.assertEquals(45, authority.getName().last());
        Assertions.assertEquals(6, authority.getName().order());

        Assertions.assertEquals("10", authority.getCode().getSemantics());

        Assertions.assertEquals(47, authority.getCode().first());
        Assertions.assertEquals(50, authority.getCode().last());
        Assertions.assertEquals(8, authority.getCode().order());

        Assertions.assertEquals(29, authority.first());
        Assertions.assertEquals(51, authority.last());
        Assertions.assertEquals(10, authority.order());

        Assertions.assertEquals(0, projection.first());
        Assertions.assertEquals(52, projection.last());
        Assertions.assertEquals(12, projection.order());
    }

    @Test
    public void spheroid1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              SPHEROID["GRS_1980",6378137,298.257222101]""");

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
    public void spheroidAuthority1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              SPHEROID["Airy 1830",637563.396,299.3249646,AUTHORITY["EPSG","7001"]]""");

        final Spheroid spheroid = parser.spheroid();

        Assertions.assertEquals(WktName.SPHEROID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("Airy 1830", spheroid.getName().getSemantics());
        Assertions.assertEquals(637563.396, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(299.3249646, spheroid.getInverseFlattening().getSemantics().doubleValue());

        final Authority authority = spheroid.getAuthority();

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());
        Assertions.assertEquals("7001", authority.getCode().getSemantics());
    }

    @Test
    public void ellipsoid1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]""");

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
    public void datum1() throws LanguageException {

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
    public void vertCs1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS["Newlyn",\
                                              VERT_DATUM["Ordnance Datum Newlyn",2005,AUTHORITY["EPSG","5101"]],\
                                              UNIT["metre",1,AUTHORITY["EPSG","9001"]]]""");

        final VertCs vertCs = parser.vertCs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(123, vertCs.last());
        Assertions.assertEquals(40, vertCs.order());

        final VertDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(2005, datum.getDatumType().getSemantics().intValue());

        Assertions.assertEquals(52, datum.getDatumType().first());
        Assertions.assertEquals(55, datum.getDatumType().last());
        Assertions.assertEquals(10, datum.getDatumType().order());

        final Authority datumAuthority = datum.getAuthority();

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(67, datumAuthority.getName().first());
        Assertions.assertEquals(72, datumAuthority.getName().last());
        Assertions.assertEquals(14, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getCode().getSemantics());
        Assertions.assertEquals(74, datumAuthority.getCode().first());
        Assertions.assertEquals(79, datumAuthority.getCode().last());
        Assertions.assertEquals(16, datumAuthority.getCode().order());

        Assertions.assertEquals(57, datumAuthority.first());
        Assertions.assertEquals(80, datumAuthority.last());
        Assertions.assertEquals(18, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(81, datum.last());
        Assertions.assertEquals(20, datum.order());

        final Unit unit = vertCs.getLinearUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(88, unit.getName().first());
        Assertions.assertEquals(94, unit.getName().last());
        Assertions.assertEquals(24, unit.getName().order());

        Assertions.assertEquals(96, unit.getConversionFactor().first());
        Assertions.assertEquals(96, unit.getConversionFactor().last());
        Assertions.assertEquals(28, unit.getConversionFactor().order());

        final Authority unitAuthority = unit.getAuthority();

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(108, unitAuthority.getName().first());
        Assertions.assertEquals(113, unitAuthority.getName().last());
        Assertions.assertEquals(32, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getCode().getSemantics());
        Assertions.assertEquals(115, unitAuthority.getCode().first());
        Assertions.assertEquals(120, unitAuthority.getCode().last());
        Assertions.assertEquals(34, unitAuthority.getCode().order());

        Assertions.assertEquals(98, unitAuthority.first());
        Assertions.assertEquals(121, unitAuthority.last());
        Assertions.assertEquals(36, unitAuthority.order());

        Assertions.assertEquals(83, unit.first());
        Assertions.assertEquals(122, unit.last());
        Assertions.assertEquals(38, unit.order());

        Assertions.assertNull(vertCs.getAxis());

        Assertions.assertNull(vertCs.getAuthority());
    }

    @Test
    @DisplayName("exception should fail for vertCs with two axes")
    public void vertCs_axis_two_axis() throws LanguageException {

        final ParserException ex = Assertions.assertThrows(ParserException.class, () -> {

            WktParser.of("""
                         VERT_CS["Newlyn",\
                         VERT_DATUM["Ordnance Datum Newlyn",2005,AUTHORITY["EPSG","5101"]],\
                         UNIT["metre",1,AUTHORITY["EPSG","9001"]],\
                         AXIS["Up",UP],AXIS["Up",UP]]""").vertCs();
        });

        Assertions.assertEquals("""
                                unexpected token {codePoints=,} at 143, but expected:
                                AXIS
                                """, ex.getMessage());
    }

    @Test
    public void vertCs_axis1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS["Newlyn",\
                                              VERT_DATUM["Ordnance Datum Newlyn",2005,AUTHORITY["EPSG","5101"]],\
                                              UNIT["metre",1,AUTHORITY["EPSG","9001"]],\
                                              AXIS["Up",UP]]""");

        final VertCs vertCs = parser.vertCs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(137, vertCs.last());
        Assertions.assertEquals(48, vertCs.order());

        final VertDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(2005, datum.getDatumType().getSemantics().intValue());

        Assertions.assertEquals(52, datum.getDatumType().first());
        Assertions.assertEquals(55, datum.getDatumType().last());
        Assertions.assertEquals(10, datum.getDatumType().order());

        final Authority datumAuthority = datum.getAuthority();

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(67, datumAuthority.getName().first());
        Assertions.assertEquals(72, datumAuthority.getName().last());
        Assertions.assertEquals(14, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getCode().getSemantics());
        Assertions.assertEquals(74, datumAuthority.getCode().first());
        Assertions.assertEquals(79, datumAuthority.getCode().last());
        Assertions.assertEquals(16, datumAuthority.getCode().order());

        Assertions.assertEquals(57, datumAuthority.first());
        Assertions.assertEquals(80, datumAuthority.last());
        Assertions.assertEquals(18, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(81, datum.last());
        Assertions.assertEquals(20, datum.order());

        final Unit unit = vertCs.getLinearUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(88, unit.getName().first());
        Assertions.assertEquals(94, unit.getName().last());
        Assertions.assertEquals(24, unit.getName().order());

        Assertions.assertEquals(96, unit.getConversionFactor().first());
        Assertions.assertEquals(96, unit.getConversionFactor().last());
        Assertions.assertEquals(28, unit.getConversionFactor().order());

        final Authority unitAuthority = unit.getAuthority();

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(108, unitAuthority.getName().first());
        Assertions.assertEquals(113, unitAuthority.getName().last());
        Assertions.assertEquals(32, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getCode().getSemantics());
        Assertions.assertEquals(115, unitAuthority.getCode().first());
        Assertions.assertEquals(120, unitAuthority.getCode().last());
        Assertions.assertEquals(34, unitAuthority.getCode().order());

        Assertions.assertEquals(98, unitAuthority.first());
        Assertions.assertEquals(121, unitAuthority.last());
        Assertions.assertEquals(36, unitAuthority.order());

        Assertions.assertEquals(83, unit.first());
        Assertions.assertEquals(122, unit.last());
        Assertions.assertEquals(38, unit.order());

        final Axis axis = vertCs.getAxis();

        Assertions.assertEquals("Up", axis.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis.getDirection().getSemantics());

        Assertions.assertEquals(129, axis.getName().first());
        Assertions.assertEquals(132, axis.getName().last());
        Assertions.assertEquals(42, axis.getName().order());

        Assertions.assertEquals(134, axis.getDirection().first());
        Assertions.assertEquals(135, axis.getDirection().last());
        Assertions.assertEquals(44, axis.getDirection().order());

        Assertions.assertEquals(124, axis.first());
        Assertions.assertEquals(136, axis.last());
        Assertions.assertEquals(46, axis.order());

        Assertions.assertNull(vertCs.getAuthority());
    }

    @Test
    public void vertCsAuthority1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                            VERT_CS["Newlyn",\
                            VERT_DATUM["Ordnance Datum Newlyn",2005,AUTHORITY["EPSG","5101"]],\
                            UNIT["metre",1,AUTHORITY["EPSG","9001"]],\
                            AUTHORITY["EPSG","5701"]]""");

        final VertCs vertCs = parser.vertCs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(148, vertCs.last());
        Assertions.assertEquals(48, vertCs.order());

        final VertDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(2005, datum.getDatumType().getSemantics().intValue());

        Assertions.assertEquals(52, datum.getDatumType().first());
        Assertions.assertEquals(55, datum.getDatumType().last());
        Assertions.assertEquals(10, datum.getDatumType().order());

        final Authority datumAuthority = datum.getAuthority();

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(67, datumAuthority.getName().first());
        Assertions.assertEquals(72, datumAuthority.getName().last());
        Assertions.assertEquals(14, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getCode().getSemantics());
        Assertions.assertEquals(74, datumAuthority.getCode().first());
        Assertions.assertEquals(79, datumAuthority.getCode().last());
        Assertions.assertEquals(16, datumAuthority.getCode().order());

        Assertions.assertEquals(57, datumAuthority.first());
        Assertions.assertEquals(80, datumAuthority.last());
        Assertions.assertEquals(18, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(81, datum.last());
        Assertions.assertEquals(20, datum.order());

        final Unit unit = vertCs.getLinearUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(88, unit.getName().first());
        Assertions.assertEquals(94, unit.getName().last());
        Assertions.assertEquals(24, unit.getName().order());

        Assertions.assertEquals(96, unit.getConversionFactor().first());
        Assertions.assertEquals(96, unit.getConversionFactor().last());
        Assertions.assertEquals(28, unit.getConversionFactor().order());

        final Authority unitAuthority = unit.getAuthority();

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(108, unitAuthority.getName().first());
        Assertions.assertEquals(113, unitAuthority.getName().last());
        Assertions.assertEquals(32, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getCode().getSemantics());
        Assertions.assertEquals(115, unitAuthority.getCode().first());
        Assertions.assertEquals(120, unitAuthority.getCode().last());
        Assertions.assertEquals(34, unitAuthority.getCode().order());

        Assertions.assertEquals(98, unitAuthority.first());
        Assertions.assertEquals(121, unitAuthority.last());
        Assertions.assertEquals(36, unitAuthority.order());

        Assertions.assertEquals(83, unit.first());
        Assertions.assertEquals(122, unit.last());
        Assertions.assertEquals(38, unit.order());

        Assertions.assertNull(vertCs.getAxis());

        final Authority authority = vertCs.getAuthority();

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());

        Assertions.assertEquals(134, authority.getName().first());
        Assertions.assertEquals(139, authority.getName().last());
        Assertions.assertEquals(42, authority.getName().order());

        Assertions.assertEquals("5701", authority.getCode().getSemantics());
        Assertions.assertEquals(141, authority.getCode().first());
        Assertions.assertEquals(146, authority.getCode().last());
        Assertions.assertEquals(44, authority.getCode().order());

        Assertions.assertEquals(124, authority.first());
        Assertions.assertEquals(147, authority.last());
        Assertions.assertEquals(46, authority.order());
    }

    @Test
    public void vertCs_axisAuthority1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS["Newlyn",\
                                              VERT_DATUM["Ordnance Datum Newlyn",2005,AUTHORITY["EPSG","5101"]],\
                                              UNIT["metre",1,AUTHORITY["EPSG","9001"]],\
                                              AXIS["Up",UP],\
                                              AUTHORITY["EPSG","5701"]]""");

        final VertCs vertCs = parser.vertCs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(162, vertCs.last());
        Assertions.assertEquals(56, vertCs.order());

        final VertDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(2005, datum.getDatumType().getSemantics().intValue());

        Assertions.assertEquals(52, datum.getDatumType().first());
        Assertions.assertEquals(55, datum.getDatumType().last());
        Assertions.assertEquals(10, datum.getDatumType().order());

        final Authority datumAuthority = datum.getAuthority();

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(67, datumAuthority.getName().first());
        Assertions.assertEquals(72, datumAuthority.getName().last());
        Assertions.assertEquals(14, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getCode().getSemantics());
        Assertions.assertEquals(74, datumAuthority.getCode().first());
        Assertions.assertEquals(79, datumAuthority.getCode().last());
        Assertions.assertEquals(16, datumAuthority.getCode().order());

        Assertions.assertEquals(57, datumAuthority.first());
        Assertions.assertEquals(80, datumAuthority.last());
        Assertions.assertEquals(18, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(81, datum.last());
        Assertions.assertEquals(20, datum.order());

        final Unit unit = vertCs.getLinearUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(88, unit.getName().first());
        Assertions.assertEquals(94, unit.getName().last());
        Assertions.assertEquals(24, unit.getName().order());

        Assertions.assertEquals(96, unit.getConversionFactor().first());
        Assertions.assertEquals(96, unit.getConversionFactor().last());
        Assertions.assertEquals(28, unit.getConversionFactor().order());

        final Authority unitAuthority = unit.getAuthority();

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(108, unitAuthority.getName().first());
        Assertions.assertEquals(113, unitAuthority.getName().last());
        Assertions.assertEquals(32, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getCode().getSemantics());
        Assertions.assertEquals(115, unitAuthority.getCode().first());
        Assertions.assertEquals(120, unitAuthority.getCode().last());
        Assertions.assertEquals(34, unitAuthority.getCode().order());

        Assertions.assertEquals(98, unitAuthority.first());
        Assertions.assertEquals(121, unitAuthority.last());
        Assertions.assertEquals(36, unitAuthority.order());

        Assertions.assertEquals(83, unit.first());
        Assertions.assertEquals(122, unit.last());
        Assertions.assertEquals(38, unit.order());

        final Axis axis = vertCs.getAxis();

        Assertions.assertEquals("Up", axis.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis.getDirection().getSemantics());

        Assertions.assertEquals(129, axis.getName().first());
        Assertions.assertEquals(132, axis.getName().last());
        Assertions.assertEquals(42, axis.getName().order());

        Assertions.assertEquals(134, axis.getDirection().first());
        Assertions.assertEquals(135, axis.getDirection().last());
        Assertions.assertEquals(44, axis.getDirection().order());

        Assertions.assertEquals(124, axis.first());
        Assertions.assertEquals(136, axis.last());
        Assertions.assertEquals(46, axis.order());

        final Authority authority = vertCs.getAuthority();

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());

        Assertions.assertEquals(148, authority.getName().first());
        Assertions.assertEquals(153, authority.getName().last());
        Assertions.assertEquals(50, authority.getName().order());

        Assertions.assertEquals("5701", authority.getCode().getSemantics());
        Assertions.assertEquals(155, authority.getCode().first());
        Assertions.assertEquals(160, authority.getCode().last());
        Assertions.assertEquals(52, authority.getCode().order());

        Assertions.assertEquals(138, authority.first());
        Assertions.assertEquals(161, authority.last());
        Assertions.assertEquals(54, authority.order());
    }

    @Test
    public void localCs_axisAuthority1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              LOCAL_CS["local cs name",\
                                              LOCAL_DATUM["local datum name",1,AUTHORITY["LOCAL","2"]],\
                                              UNIT["metre",1,AUTHORITY["EPSG","9001"]],\
                                              AXIS["Up",UP],\
                                              AXIS["East",EAST],\
                                              AUTHORITY["LOCAL","3"]]""");

        final LocalCs localCs = parser.localCs();

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

        final Axis axis0 = localCs.getAxis().get(0);
        Assertions.assertEquals("Up", axis0.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis0.getDirection().getSemantics());

        final Axis axis1 = localCs.getAxis().get(1);
        Assertions.assertEquals("East", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());

        final Authority authority = localCs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("3", authority.getCode().getSemantics());
    }

    @Test
    public void fittedCs_axisAuthority1() throws LanguageException {

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

        final FittedCs fittedCs = parser.fittedCs();

        Assertions.assertTrue(fittedCs.getMathTransform() instanceof ConcatMt);

        final ConcatMt concatMt = (ConcatMt) fittedCs.getMathTransform();

        Assertions.assertEquals(2, concatMt.getTransforms().size());
        Assertions.assertTrue(concatMt.getTransforms().get(0) instanceof PassthroughMt);
        Assertions.assertTrue(concatMt.getTransforms().get(1) instanceof ConcatMt);

        final PassthroughMt passthroughMt = (PassthroughMt) concatMt.getTransforms().get(0);

        Assertions.assertEquals(4, passthroughMt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthroughMt.getTransform() instanceof InverseMt);

        final InverseMt inverseMt = (InverseMt) passthroughMt.getTransform();

        Assertions.assertTrue(inverseMt.getTransform() instanceof ParamMt);

        final ParamMt paramMt = (ParamMt) inverseMt.getTransform();

        Assertions.assertEquals("classification name", paramMt.getName().getSemantics());

        final List<Parameter> parameters = paramMt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());

        final ConcatMt inclConcatMt = (ConcatMt) concatMt.getTransforms().get(1);
        Assertions.assertEquals(1, inclConcatMt.getTransforms().size());
        Assertions.assertTrue(inclConcatMt.getTransforms().get(0) instanceof ParamMt);

        final ParamMt inclParamMt = (ParamMt) inclConcatMt.getTransforms().get(0);

        Assertions.assertEquals("second name", inclParamMt.getName().getSemantics());

        Assertions.assertTrue(inclParamMt.getParameters().isEmpty());

        Assertions.assertTrue(fittedCs.getCoordinateSystem() instanceof LocalCs);

        final LocalCs localCs = (LocalCs) fittedCs.getCoordinateSystem();

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

        final Axis axis0 = localCs.getAxis().get(0);
        Assertions.assertEquals("Up", axis0.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis0.getDirection().getSemantics());

        final Axis axis1 = localCs.getAxis().get(1);
        Assertions.assertEquals("East", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());

        final Authority authority = localCs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("3", authority.getCode().getSemantics());
    }

    @Test
    public void compdCs_axisAuthority1() throws LanguageException {

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

        final CompdCs compdCs = parser.compdCs();

        Assertions.assertTrue(compdCs.getHead() instanceof FittedCs);

        final FittedCs fittedCs = (FittedCs) compdCs.getHead();

        Assertions.assertTrue(fittedCs.getMathTransform() instanceof ConcatMt);

        final ConcatMt concatMt = (ConcatMt) fittedCs.getMathTransform();

        Assertions.assertEquals(2, concatMt.getTransforms().size());
        Assertions.assertTrue(concatMt.getTransforms().get(0) instanceof PassthroughMt);
        Assertions.assertTrue(concatMt.getTransforms().get(1) instanceof ConcatMt);

        final PassthroughMt passthroughMt = (PassthroughMt) concatMt.getTransforms().get(0);

        Assertions.assertEquals(4, passthroughMt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthroughMt.getTransform() instanceof InverseMt);

        final InverseMt inverseMt = (InverseMt) passthroughMt.getTransform();

        Assertions.assertTrue(inverseMt.getTransform() instanceof ParamMt);

        final ParamMt paramMt = (ParamMt) inverseMt.getTransform();

        Assertions.assertEquals("classification name", paramMt.getName().getSemantics());

        final List<Parameter> parameters = paramMt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());

        final ConcatMt inclConcatMt = (ConcatMt) concatMt.getTransforms().get(1);
        Assertions.assertEquals(1, inclConcatMt.getTransforms().size());
        Assertions.assertTrue(inclConcatMt.getTransforms().get(0) instanceof ParamMt);

        final ParamMt inclParamMt = (ParamMt) inclConcatMt.getTransforms().get(0);

        Assertions.assertEquals("second name", inclParamMt.getName().getSemantics());

        Assertions.assertTrue(inclParamMt.getParameters().isEmpty());

        Assertions.assertTrue(fittedCs.getCoordinateSystem() instanceof LocalCs);

        final LocalCs localCs = (LocalCs) fittedCs.getCoordinateSystem();

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

        final Axis axis0 = localCs.getAxis().get(0);
        Assertions.assertEquals("Up", axis0.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis0.getDirection().getSemantics());

        final Axis axis1 = localCs.getAxis().get(1);
        Assertions.assertEquals("East", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());

        final Authority authority = localCs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("3", authority.getCode().getSemantics());


        Assertions.assertTrue(compdCs.getTail() instanceof VertCs);

        final VertCs vertCs = (VertCs) compdCs.getTail();

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

        final Axis axis = vertCs.getAxis();

        Assertions.assertEquals("Up", axis.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis.getDirection().getSemantics());

        final Authority vertCsAuthority = vertCs.getAuthority();

        Assertions.assertEquals("EPSG", vertCsAuthority.getName().getSemantics());
        Assertions.assertEquals("5701", vertCsAuthority.getCode().getSemantics());


        final Authority compdCsAuthority = compdCs.getAuthority();
        Assertions.assertEquals("LOCAL", compdCsAuthority.getName().getSemantics());
        Assertions.assertEquals("10", compdCsAuthority.getCode().getSemantics());
    }

    @Test
    public void geoccs1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],UNIT["Degree",0.0174532925199433]]""");

        final GeocentricCs geoccs = parser.geocentricCs();

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
    public void geoccs_axisAuthority1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],UNIT["Degree",0.0174532925199433],\
                                              AXIS["E",EAST],AXIS["N",NORTH],AXIS["U",UP],AUTHORITY["LOCAL","1"]]""");

        final GeocentricCs geoccs = parser.geocentricCs();

        Assertions.assertEquals("GCS_North_American_1983", geoccs.getName().getSemantics());

        final Datum datum = geoccs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        final PrimeMeridian primeMeridian = geoccs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        final Unit unit = geoccs.getLinearUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        final Axis axis1 = geoccs.getAxis1();
        Assertions.assertEquals("E", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());
        final Axis axis2 = geoccs.getAxis2();
        Assertions.assertEquals("N", axis2.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.NORTH, axis2.getDirection().getSemantics());
        final Axis axis3 = geoccs.getAxis3();
        Assertions.assertEquals("U", axis3.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis3.getDirection().getSemantics());
        final Authority authority = geoccs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("1", authority.getCode().getSemantics());
    }

    @Test
    public void geogcs1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],UNIT["Degree",0.0174532925199433]]""");

        final GeographicCs geogcs = parser.geographicCs();

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
    public void geogcs_3d1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],\
                                              UNIT["Degree",0.0174532925199433],UNIT["Meter",1.0]]""");

        final GeographicCs geogcs = parser.geographicCs();

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
    public void geogcs_axisAuthority1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS["GCS_North_American_1983",\
                                              DATUM["D_North_American_1983",\
                                              ELLIPSOID["GRS_1980",6378137,298.257222101]],\
                                              PRIMEM["Greenwitch",0],UNIT["Degree",0.0174532925199433],\
                                              AXIS["E",EAST],AXIS["N",NORTH],AUTHORITY["LOCAL","1"]]""");

        final GeographicCs geogcs = parser.geographicCs();

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

        Assertions.assertNull(geogcs.getLinearUnit());

        final Axis axis1 = geogcs.getAxis1();
        Assertions.assertEquals("E", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());
        final Axis axis2 = geogcs.getAxis2();
        Assertions.assertEquals("N", axis2.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.NORTH, axis2.getDirection().getSemantics());

        final Authority authority = geogcs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("1", authority.getCode().getSemantics());
    }

    @Test
    public void projcs1() throws LanguageException {

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

        final ProjectedCs projcs = parser.projectedCs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(440, projcs.last());
        Assertions.assertEquals(145, projcs.order());

        final GeographicCs geogcs = projcs.getGeogCs();

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
    public void projcs_axis1() throws LanguageException {

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
                                              UNIT["Meter",1.0],\
                                              AXIS["Easting",EAST],AXIS["Northing",NORTH]]""");

        final ProjectedCs projcs = parser.projectedCs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(484, projcs.last());
        Assertions.assertEquals(161, projcs.order());

        final GeographicCs geogcs = projcs.getGeogCs();

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

        final Axis axis1 = projcs.getAxis1();

        Assertions.assertEquals("Easting", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());

        final Axis axis2 = projcs.getAxis2();

        Assertions.assertEquals("Northing", axis2.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.NORTH, axis2.getDirection().getSemantics());
    }

    @Test
    public void projcs_axisAuthority1() throws LanguageException {

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
                                              UNIT["Meter",1.0],\
                                              AXIS["Easting",EAST],AXIS["Northing",NORTH],\
                                              AUTHORITY["LOCAL","10"]]""");

        final ProjectedCs projcs = parser.projectedCs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(508, projcs.last());
        Assertions.assertEquals(169, projcs.order());

        final GeographicCs geogcs = projcs.getGeogCs();

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

        final Axis axis1 = projcs.getAxis1();

        Assertions.assertEquals("Easting", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());

        final Axis axis2 = projcs.getAxis2();

        Assertions.assertEquals("Northing", axis2.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.NORTH, axis2.getDirection().getSemantics());

        final Authority authority = projcs.getAuthority();

        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("10", authority.getCode().getSemantics());
    }

    @Test
    public void projcsAuthority1() throws LanguageException {

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
                                              UNIT["Meter",1.0],\
                                              AUTHORITY["LOCAL","10"]]""");

        final ProjectedCs projcs = parser.projectedCs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(464, projcs.last());
        Assertions.assertEquals(153, projcs.order());

        final GeographicCs geogcs = projcs.getGeogCs();

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

        Assertions.assertNull(projcs.getAxis1());
        Assertions.assertNull(projcs.getAxis2());

        final Authority authority = projcs.getAuthority();

        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("10", authority.getCode().getSemantics());
    }

    @Test
    public void spatial_reference_system1_1() throws LanguageException {

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
    public void spatial_reference_system1_2() throws LanguageException {

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
    public void spatial_reference_system1_3() throws LanguageException {

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
    public void spatial_reference_system1_4() throws LanguageException {

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

        final GeographicCs geogcs = projcs.getGeogCs();

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
    public void authority2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              AUTHORITY("EPSG","8901")""", '(', ')');

        final Authority authority = parser.authority();

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());
        Assertions.assertEquals("8901", authority.getCode().getSemantics());

        Assertions.assertEquals(10, authority.getName().first());
        Assertions.assertEquals(15, authority.getName().last());
        Assertions.assertEquals(2, authority.getName().order());

        Assertions.assertEquals(17, authority.getCode().first());
        Assertions.assertEquals(22, authority.getCode().last());
        Assertions.assertEquals(4, authority.getCode().order());

        Assertions.assertEquals(0, authority.first());
        Assertions.assertEquals(23, authority.last());
        Assertions.assertEquals(6, authority.order());
    }

    @Test
    public void paramMt2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PARAM_MT("classification name",\
                                              PARAMETER("first",0.0174532925199433),\
                                              PARAMETER("second",0.174532925199433))""", '(', ')');

        final ParamMt paramMt = parser.paramMt();

        Assertions.assertEquals("classification name", paramMt.getName().getSemantics());

        final List<Parameter> parameters = paramMt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());
    }

    @Test
    public void inverseMt2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              INVERSE_MT(PARAM_MT("classification name",\
                                              PARAMETER("first",0.0174532925199433),\
                                              PARAMETER("second",0.174532925199433)))""", '(', ')');

        final InverseMt inverseMt = parser.invMt();

        Assertions.assertTrue(inverseMt.getTransform() instanceof ParamMt);

        final ParamMt paramMt = (ParamMt) inverseMt.getTransform();

        Assertions.assertEquals("classification name", paramMt.getName().getSemantics());

        final List<Parameter> parameters = paramMt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());
    }

    @Test
    public void passthroughMt2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PASSTHROUGH_MT(4,INVERSE_MT(PARAM_MT("classification name",\
                                              PARAMETER("first",0.0174532925199433),\
                                             PARAMETER("second",0.174532925199433))))""", '(', ')');

        final PassthroughMt passthroughMt = parser.passthroughMt();

        Assertions.assertEquals(4, passthroughMt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthroughMt.getTransform() instanceof InverseMt);

        final InverseMt inverseMt = (InverseMt) passthroughMt.getTransform();

        Assertions.assertTrue(inverseMt.getTransform() instanceof ParamMt);

        final ParamMt paramMt = (ParamMt) inverseMt.getTransform();

        Assertions.assertEquals("classification name", paramMt.getName().getSemantics());

        final List<Parameter> parameters = paramMt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());
    }

    @Test
    public void concatMt2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              CONCAT_MT(PASSTHROUGH_MT(4,INVERSE_MT(PARAM_MT("classification name",\
                                              PARAMETER("first",0.0174532925199433),\
                                              PARAMETER("second",0.174532925199433)))),\
                                              CONCAT_MT(PARAM_MT("second name")))""", '(', ')');

        final ConcatMt concatMt = parser.concatMt();

        Assertions.assertEquals(2, concatMt.getTransforms().size());
        Assertions.assertTrue(concatMt.getTransforms().get(0) instanceof PassthroughMt);
        Assertions.assertTrue(concatMt.getTransforms().get(1) instanceof ConcatMt);

        final PassthroughMt passthroughMt = (PassthroughMt) concatMt.getTransforms().get(0);

        Assertions.assertEquals(4, passthroughMt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthroughMt.getTransform() instanceof InverseMt);

        final InverseMt inverseMt = (InverseMt) passthroughMt.getTransform();

        Assertions.assertTrue(inverseMt.getTransform() instanceof ParamMt);

        final ParamMt paramMt = (ParamMt) inverseMt.getTransform();

        Assertions.assertEquals("classification name", paramMt.getName().getSemantics());

        final List<Parameter> parameters = paramMt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());

        final ConcatMt inclConcatMt = (ConcatMt) concatMt.getTransforms().get(1);
        Assertions.assertEquals(1, inclConcatMt.getTransforms().size());
        Assertions.assertTrue(inclConcatMt.getTransforms().get(0) instanceof ParamMt);

        final ParamMt inclParamMt = (ParamMt) inclConcatMt.getTransforms().get(0);

        Assertions.assertEquals("second name", inclParamMt.getName().getSemantics());

        Assertions.assertTrue(inclParamMt.getParameters().isEmpty());
    }

    @Test
    public void unit2() throws LanguageException {

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
        Assertions.assertEquals(8, unit.getConversionFactor().order());

        Assertions.assertNull(unit.getAuthority());

        Assertions.assertEquals(0, unit.first());
        Assertions.assertEquals(32, unit.last());
        Assertions.assertEquals(10, unit.order());
    }

    @Test
    public void unitAuthority2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              UNIT("metre",1,AUTHORITY("EPSG","9001"))""", '(', ')');

        final Unit unit = parser.unit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals("EPSG", unit.getAuthority().getName().getSemantics());
        Assertions.assertEquals("9001", unit.getAuthority().getCode().getSemantics());

        Assertions.assertEquals(5, unit.getName().first());
        Assertions.assertEquals(11, unit.getName().last());
        Assertions.assertEquals(2, unit.getName().order());

        Assertions.assertEquals(13, unit.getConversionFactor().first());
        Assertions.assertEquals(13, unit.getConversionFactor().last());
        Assertions.assertEquals(6, unit.getConversionFactor().order());

        final Authority authority = unit.getAuthority();
        Assertions.assertEquals(25, authority.getName().first());
        Assertions.assertEquals(30, authority.getName().last());
        Assertions.assertEquals(10, authority.getName().order());

        Assertions.assertEquals(32, authority.getCode().first());
        Assertions.assertEquals(37, authority.getCode().last());
        Assertions.assertEquals(12, authority.getCode().order());

        Assertions.assertEquals(15, authority.first());
        Assertions.assertEquals(38, authority.last());
        Assertions.assertEquals(14, authority.order());

        Assertions.assertEquals(0, unit.first());
        Assertions.assertEquals(39, unit.last());
        Assertions.assertEquals(16, unit.order());
    }

    @Test
    public void vert_datumAuthority2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_DATUM("Ordnance Datum Newlyn",2005,AUTHORITY("EPSG","5101"))""",
                '(', ')');

        final VertDatum datum = parser.vertDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());
        Assertions.assertEquals(2005, datum.getDatumType().getSemantics().intValue());
        Assertions.assertEquals("EPSG", datum.getAuthority().getName().getSemantics());
        Assertions.assertEquals("5101", datum.getAuthority().getCode().getSemantics());

        Assertions.assertEquals(11, datum.getName().first());
        Assertions.assertEquals(33, datum.getName().last());
        Assertions.assertEquals(2, datum.getName().order());

        Assertions.assertEquals(35, datum.getDatumType().first());
        Assertions.assertEquals(38, datum.getDatumType().last());
        Assertions.assertEquals(6, datum.getDatumType().order());

        final Authority authority = datum.getAuthority();
        Assertions.assertEquals(50, authority.getName().first());
        Assertions.assertEquals(55, authority.getName().last());
        Assertions.assertEquals(10, authority.getName().order());

        Assertions.assertEquals(57, authority.getCode().first());
        Assertions.assertEquals(62, authority.getCode().last());
        Assertions.assertEquals(12, authority.getCode().order());

        Assertions.assertEquals(40, authority.first());
        Assertions.assertEquals(63, authority.last());
        Assertions.assertEquals(14, authority.order());

        Assertions.assertEquals(0, datum.first());
        Assertions.assertEquals(64, datum.last());
        Assertions.assertEquals(16, datum.order());
    }

    @Test
    public void local_datumAuthority2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              LOCAL_DATUM("datum name",1,AUTHORITY("LOCAL","1"))""", '(', ')');

        final LocalDatum datum = parser.localDatum();

        Assertions.assertEquals("datum name", datum.getName().getSemantics());
        Assertions.assertEquals(1, datum.getDatumType().getSemantics().intValue());
        Assertions.assertEquals("LOCAL", datum.getAuthority().getName().getSemantics());
        Assertions.assertEquals("1", datum.getAuthority().getCode().getSemantics());

        Assertions.assertEquals(12, datum.getName().first());
        Assertions.assertEquals(23, datum.getName().last());
        Assertions.assertEquals(2, datum.getName().order());

        Assertions.assertEquals(25, datum.getDatumType().first());
        Assertions.assertEquals(25, datum.getDatumType().last());
        Assertions.assertEquals(6, datum.getDatumType().order());

        final Authority authority = datum.getAuthority();
        Assertions.assertEquals(37, authority.getName().first());
        Assertions.assertEquals(43, authority.getName().last());
        Assertions.assertEquals(10, authority.getName().order());

        Assertions.assertEquals(45, authority.getCode().first());
        Assertions.assertEquals(47, authority.getCode().last());
        Assertions.assertEquals(12, authority.getCode().order());

        Assertions.assertEquals(27, authority.first());
        Assertions.assertEquals(48, authority.last());
        Assertions.assertEquals(14, authority.order());

        Assertions.assertEquals(0, datum.first());
        Assertions.assertEquals(49, datum.last());
        Assertions.assertEquals(16, datum.order());
    }

    @Test
    public void toWgs842() throws LanguageException {

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

    @Test
    public void axis2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              AXIS("Lat",NORTH)""", '(', ')');

        final Axis axis = parser.axis();

        Assertions.assertEquals("Lat", axis.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.NORTH, axis.getDirection().getSemantics());

        Assertions.assertEquals(5, axis.getName().first());
        Assertions.assertEquals(9, axis.getName().last());
        Assertions.assertEquals(2, axis.getName().order());

        Assertions.assertEquals(11, axis.getDirection().first());
        Assertions.assertEquals(15, axis.getDirection().last());
        Assertions.assertEquals(4, axis.getDirection().order());

        Assertions.assertEquals(0, axis.first());
        Assertions.assertEquals(16, axis.last());
        Assertions.assertEquals(6, axis.order());
    }

    @Test
    public void prime_meridian2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PRIMEM("Greenwitch",0)""", '(', ')');

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
    public void prime_meridianAuthority2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PRIMEM("Greenwitch",0,AUTHORITY("EPSG","8901"))""", '(', ')');

        final PrimeMeridian primeMeridian = parser.primeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());
        Assertions.assertEquals("EPSG", primeMeridian.getAuthority().getName().getSemantics());
        Assertions.assertEquals("8901", primeMeridian.getAuthority().getCode().getSemantics());

        Assertions.assertEquals(7, primeMeridian.getName().first());
        Assertions.assertEquals(18, primeMeridian.getName().last());
        Assertions.assertEquals(2, primeMeridian.getName().order());

        Assertions.assertEquals(20, primeMeridian.getLongitude().first());
        Assertions.assertEquals(20, primeMeridian.getLongitude().last());
        Assertions.assertEquals(6, primeMeridian.getLongitude().order());

        final Authority authority = primeMeridian.getAuthority();
        Assertions.assertEquals(32, authority.getName().first());
        Assertions.assertEquals(37, authority.getName().last());
        Assertions.assertEquals(10, authority.getName().order());

        Assertions.assertEquals(39, authority.getCode().first());
        Assertions.assertEquals(44, authority.getCode().last());
        Assertions.assertEquals(12, authority.getCode().order());

        Assertions.assertEquals(22, authority.first());
        Assertions.assertEquals(45, authority.last());
        Assertions.assertEquals(14, authority.order());

        Assertions.assertEquals(0, primeMeridian.first());
        Assertions.assertEquals(46, primeMeridian.last());
        Assertions.assertEquals(16, primeMeridian.order());
    }

    @Test
    public void parameter2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PARAMETER("False_Easting",500000.0)""", '(', ')');

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
    public void projection2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJECTION("Transverse_Mercator")""", '(', ')');

        final Projection projection = parser.projection();

        Assertions.assertEquals("Transverse_Mercator", projection.getName().getSemantics());

        Assertions.assertEquals(11, projection.getName().first());
        Assertions.assertEquals(31, projection.getName().last());
        Assertions.assertEquals(2, projection.getName().order());

        Assertions.assertEquals(0, projection.first());
        Assertions.assertEquals(32, projection.last());
        Assertions.assertEquals(4, projection.order());

        Assertions.assertNull(projection.getAuthority());
    }

    @Test
    public void projectionAuthority2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              PROJECTION("projection name",AUTHORITY("LOCAL","10"))""", '(', ')');

        final Projection projection = parser.projection();

        Assertions.assertEquals("projection name", projection.getName().getSemantics());

        Assertions.assertEquals(11, projection.getName().first());
        Assertions.assertEquals(27, projection.getName().last());
        Assertions.assertEquals(2, projection.getName().order());

        final Authority authority = projection.getAuthority();

        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());

        Assertions.assertEquals(39, authority.getName().first());
        Assertions.assertEquals(45, authority.getName().last());
        Assertions.assertEquals(6, authority.getName().order());

        Assertions.assertEquals("10", authority.getCode().getSemantics());

        Assertions.assertEquals(47, authority.getCode().first());
        Assertions.assertEquals(50, authority.getCode().last());
        Assertions.assertEquals(8, authority.getCode().order());

        Assertions.assertEquals(29, authority.first());
        Assertions.assertEquals(51, authority.last());
        Assertions.assertEquals(10, authority.order());

        Assertions.assertEquals(0, projection.first());
        Assertions.assertEquals(52, projection.last());
        Assertions.assertEquals(12, projection.order());
    }

    @Test
    public void spheroid2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              SPHEROID("GRS_1980",6378137,298.257222101)""", '(', ')');

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
    public void spheroidAuthority2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              SPHEROID("Airy 1830",637563.396,299.3249646,AUTHORITY("EPSG","7001"))""",
                '(', ')');

        final Spheroid spheroid = parser.spheroid();

        Assertions.assertEquals(WktName.SPHEROID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("Airy 1830", spheroid.getName().getSemantics());
        Assertions.assertEquals(637563.396, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(299.3249646, spheroid.getInverseFlattening().getSemantics().doubleValue());

        final Authority authority = spheroid.getAuthority();

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());
        Assertions.assertEquals("7001", authority.getCode().getSemantics());
    }

    @Test
    public void ellipsoid2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)""", '(', ')');

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
    public void datum2() throws LanguageException {

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
    public void vertCs2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS("Newlyn",\
                                              VERT_DATUM("Ordnance Datum Newlyn",2005,AUTHORITY("EPSG","5101")),\
                                              UNIT("metre",1,AUTHORITY("EPSG","9001")))""", '(', ')');

        final VertCs vertCs = parser.vertCs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(123, vertCs.last());
        Assertions.assertEquals(40, vertCs.order());

        final VertDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(2005, datum.getDatumType().getSemantics().intValue());

        Assertions.assertEquals(52, datum.getDatumType().first());
        Assertions.assertEquals(55, datum.getDatumType().last());
        Assertions.assertEquals(10, datum.getDatumType().order());

        final Authority datumAuthority = datum.getAuthority();

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(67, datumAuthority.getName().first());
        Assertions.assertEquals(72, datumAuthority.getName().last());
        Assertions.assertEquals(14, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getCode().getSemantics());
        Assertions.assertEquals(74, datumAuthority.getCode().first());
        Assertions.assertEquals(79, datumAuthority.getCode().last());
        Assertions.assertEquals(16, datumAuthority.getCode().order());

        Assertions.assertEquals(57, datumAuthority.first());
        Assertions.assertEquals(80, datumAuthority.last());
        Assertions.assertEquals(18, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(81, datum.last());
        Assertions.assertEquals(20, datum.order());

        final Unit unit = vertCs.getLinearUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(88, unit.getName().first());
        Assertions.assertEquals(94, unit.getName().last());
        Assertions.assertEquals(24, unit.getName().order());

        Assertions.assertEquals(96, unit.getConversionFactor().first());
        Assertions.assertEquals(96, unit.getConversionFactor().last());
        Assertions.assertEquals(28, unit.getConversionFactor().order());

        final Authority unitAuthority = unit.getAuthority();

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(108, unitAuthority.getName().first());
        Assertions.assertEquals(113, unitAuthority.getName().last());
        Assertions.assertEquals(32, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getCode().getSemantics());
        Assertions.assertEquals(115, unitAuthority.getCode().first());
        Assertions.assertEquals(120, unitAuthority.getCode().last());
        Assertions.assertEquals(34, unitAuthority.getCode().order());

        Assertions.assertEquals(98, unitAuthority.first());
        Assertions.assertEquals(121, unitAuthority.last());
        Assertions.assertEquals(36, unitAuthority.order());

        Assertions.assertEquals(83, unit.first());
        Assertions.assertEquals(122, unit.last());
        Assertions.assertEquals(38, unit.order());

        Assertions.assertNull(vertCs.getAxis());

        Assertions.assertNull(vertCs.getAuthority());
    }

    @Test
    public void vertCs_axis2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS("Newlyn",\
                                              VERT_DATUM("Ordnance Datum Newlyn",2005,AUTHORITY("EPSG","5101")),\
                                              UNIT("metre",1,AUTHORITY("EPSG","9001")),\
                                              AXIS("Up",UP))""", '(', ')');

        final VertCs vertCs = parser.vertCs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(137, vertCs.last());
        Assertions.assertEquals(48, vertCs.order());

        final VertDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(2005, datum.getDatumType().getSemantics().intValue());

        Assertions.assertEquals(52, datum.getDatumType().first());
        Assertions.assertEquals(55, datum.getDatumType().last());
        Assertions.assertEquals(10, datum.getDatumType().order());

        final Authority datumAuthority = datum.getAuthority();

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(67, datumAuthority.getName().first());
        Assertions.assertEquals(72, datumAuthority.getName().last());
        Assertions.assertEquals(14, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getCode().getSemantics());
        Assertions.assertEquals(74, datumAuthority.getCode().first());
        Assertions.assertEquals(79, datumAuthority.getCode().last());
        Assertions.assertEquals(16, datumAuthority.getCode().order());

        Assertions.assertEquals(57, datumAuthority.first());
        Assertions.assertEquals(80, datumAuthority.last());
        Assertions.assertEquals(18, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(81, datum.last());
        Assertions.assertEquals(20, datum.order());

        final Unit unit = vertCs.getLinearUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(88, unit.getName().first());
        Assertions.assertEquals(94, unit.getName().last());
        Assertions.assertEquals(24, unit.getName().order());

        Assertions.assertEquals(96, unit.getConversionFactor().first());
        Assertions.assertEquals(96, unit.getConversionFactor().last());
        Assertions.assertEquals(28, unit.getConversionFactor().order());

        final Authority unitAuthority = unit.getAuthority();

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(108, unitAuthority.getName().first());
        Assertions.assertEquals(113, unitAuthority.getName().last());
        Assertions.assertEquals(32, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getCode().getSemantics());
        Assertions.assertEquals(115, unitAuthority.getCode().first());
        Assertions.assertEquals(120, unitAuthority.getCode().last());
        Assertions.assertEquals(34, unitAuthority.getCode().order());

        Assertions.assertEquals(98, unitAuthority.first());
        Assertions.assertEquals(121, unitAuthority.last());
        Assertions.assertEquals(36, unitAuthority.order());

        Assertions.assertEquals(83, unit.first());
        Assertions.assertEquals(122, unit.last());
        Assertions.assertEquals(38, unit.order());

        final Axis axis = vertCs.getAxis();

        Assertions.assertEquals("Up", axis.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis.getDirection().getSemantics());

        Assertions.assertEquals(129, axis.getName().first());
        Assertions.assertEquals(132, axis.getName().last());
        Assertions.assertEquals(42, axis.getName().order());

        Assertions.assertEquals(134, axis.getDirection().first());
        Assertions.assertEquals(135, axis.getDirection().last());
        Assertions.assertEquals(44, axis.getDirection().order());

        Assertions.assertEquals(124, axis.first());
        Assertions.assertEquals(136, axis.last());
        Assertions.assertEquals(46, axis.order());

        Assertions.assertNull(vertCs.getAuthority());
    }

    @Test
    public void vertCsAuthority2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS("Newlyn",\
                                              VERT_DATUM("Ordnance Datum Newlyn",2005,AUTHORITY("EPSG","5101")),\
                                              UNIT("metre",1,AUTHORITY("EPSG","9001")),\
                                              AUTHORITY("EPSG","5701"))""", '(', ')');

        final VertCs vertCs = parser.vertCs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(148, vertCs.last());
        Assertions.assertEquals(48, vertCs.order());

        final VertDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(2005, datum.getDatumType().getSemantics().intValue());

        Assertions.assertEquals(52, datum.getDatumType().first());
        Assertions.assertEquals(55, datum.getDatumType().last());
        Assertions.assertEquals(10, datum.getDatumType().order());

        final Authority datumAuthority = datum.getAuthority();

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(67, datumAuthority.getName().first());
        Assertions.assertEquals(72, datumAuthority.getName().last());
        Assertions.assertEquals(14, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getCode().getSemantics());
        Assertions.assertEquals(74, datumAuthority.getCode().first());
        Assertions.assertEquals(79, datumAuthority.getCode().last());
        Assertions.assertEquals(16, datumAuthority.getCode().order());

        Assertions.assertEquals(57, datumAuthority.first());
        Assertions.assertEquals(80, datumAuthority.last());
        Assertions.assertEquals(18, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(81, datum.last());
        Assertions.assertEquals(20, datum.order());

        final Unit unit = vertCs.getLinearUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(88, unit.getName().first());
        Assertions.assertEquals(94, unit.getName().last());
        Assertions.assertEquals(24, unit.getName().order());

        Assertions.assertEquals(96, unit.getConversionFactor().first());
        Assertions.assertEquals(96, unit.getConversionFactor().last());
        Assertions.assertEquals(28, unit.getConversionFactor().order());

        final Authority unitAuthority = unit.getAuthority();

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(108, unitAuthority.getName().first());
        Assertions.assertEquals(113, unitAuthority.getName().last());
        Assertions.assertEquals(32, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getCode().getSemantics());
        Assertions.assertEquals(115, unitAuthority.getCode().first());
        Assertions.assertEquals(120, unitAuthority.getCode().last());
        Assertions.assertEquals(34, unitAuthority.getCode().order());

        Assertions.assertEquals(98, unitAuthority.first());
        Assertions.assertEquals(121, unitAuthority.last());
        Assertions.assertEquals(36, unitAuthority.order());

        Assertions.assertEquals(83, unit.first());
        Assertions.assertEquals(122, unit.last());
        Assertions.assertEquals(38, unit.order());

        Assertions.assertNull(vertCs.getAxis());

        final Authority authority = vertCs.getAuthority();

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());

        Assertions.assertEquals(134, authority.getName().first());
        Assertions.assertEquals(139, authority.getName().last());
        Assertions.assertEquals(42, authority.getName().order());

        Assertions.assertEquals("5701", authority.getCode().getSemantics());
        Assertions.assertEquals(141, authority.getCode().first());
        Assertions.assertEquals(146, authority.getCode().last());
        Assertions.assertEquals(44, authority.getCode().order());

        Assertions.assertEquals(124, authority.first());
        Assertions.assertEquals(147, authority.last());
        Assertions.assertEquals(46, authority.order());
    }

    @Test
    public void vertCs_axisAuthority2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              VERT_CS("Newlyn",\
                                              VERT_DATUM("Ordnance Datum Newlyn",2005,AUTHORITY("EPSG","5101")),\
                                              UNIT("metre",1,AUTHORITY("EPSG","9001")),\
                                              AXIS("Up",UP),\
                                              AUTHORITY("EPSG","5701"))""", '(', ')');

        final VertCs vertCs = parser.vertCs();

        Assertions.assertEquals("Newlyn", vertCs.getName().getSemantics());

        Assertions.assertEquals(8, vertCs.getName().first());
        Assertions.assertEquals(15, vertCs.getName().last());
        Assertions.assertEquals(2, vertCs.getName().order());

        Assertions.assertEquals(0, vertCs.first());
        Assertions.assertEquals(162, vertCs.last());
        Assertions.assertEquals(56, vertCs.order());

        final VertDatum datum = vertCs.getDatum();

        Assertions.assertEquals("Ordnance Datum Newlyn", datum.getName().getSemantics());

        Assertions.assertEquals(28, datum.getName().first());
        Assertions.assertEquals(50, datum.getName().last());
        Assertions.assertEquals(6, datum.getName().order());

        Assertions.assertEquals(2005, datum.getDatumType().getSemantics().intValue());

        Assertions.assertEquals(52, datum.getDatumType().first());
        Assertions.assertEquals(55, datum.getDatumType().last());
        Assertions.assertEquals(10, datum.getDatumType().order());

        final Authority datumAuthority = datum.getAuthority();

        Assertions.assertEquals("EPSG", datumAuthority.getName().getSemantics());

        Assertions.assertEquals(67, datumAuthority.getName().first());
        Assertions.assertEquals(72, datumAuthority.getName().last());
        Assertions.assertEquals(14, datumAuthority.getName().order());

        Assertions.assertEquals("5101", datumAuthority.getCode().getSemantics());
        Assertions.assertEquals(74, datumAuthority.getCode().first());
        Assertions.assertEquals(79, datumAuthority.getCode().last());
        Assertions.assertEquals(16, datumAuthority.getCode().order());

        Assertions.assertEquals(57, datumAuthority.first());
        Assertions.assertEquals(80, datumAuthority.last());
        Assertions.assertEquals(18, datumAuthority.order());

        Assertions.assertEquals(17, datum.first());
        Assertions.assertEquals(81, datum.last());
        Assertions.assertEquals(20, datum.order());

        final Unit unit = vertCs.getLinearUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(88, unit.getName().first());
        Assertions.assertEquals(94, unit.getName().last());
        Assertions.assertEquals(24, unit.getName().order());

        Assertions.assertEquals(96, unit.getConversionFactor().first());
        Assertions.assertEquals(96, unit.getConversionFactor().last());
        Assertions.assertEquals(28, unit.getConversionFactor().order());

        final Authority unitAuthority = unit.getAuthority();

        Assertions.assertEquals("EPSG", unitAuthority.getName().getSemantics());

        Assertions.assertEquals(108, unitAuthority.getName().first());
        Assertions.assertEquals(113, unitAuthority.getName().last());
        Assertions.assertEquals(32, unitAuthority.getName().order());

        Assertions.assertEquals("9001", unitAuthority.getCode().getSemantics());
        Assertions.assertEquals(115, unitAuthority.getCode().first());
        Assertions.assertEquals(120, unitAuthority.getCode().last());
        Assertions.assertEquals(34, unitAuthority.getCode().order());

        Assertions.assertEquals(98, unitAuthority.first());
        Assertions.assertEquals(121, unitAuthority.last());
        Assertions.assertEquals(36, unitAuthority.order());

        Assertions.assertEquals(83, unit.first());
        Assertions.assertEquals(122, unit.last());
        Assertions.assertEquals(38, unit.order());

        final Axis axis = vertCs.getAxis();

        Assertions.assertEquals("Up", axis.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis.getDirection().getSemantics());

        Assertions.assertEquals(129, axis.getName().first());
        Assertions.assertEquals(132, axis.getName().last());
        Assertions.assertEquals(42, axis.getName().order());

        Assertions.assertEquals(134, axis.getDirection().first());
        Assertions.assertEquals(135, axis.getDirection().last());
        Assertions.assertEquals(44, axis.getDirection().order());

        Assertions.assertEquals(124, axis.first());
        Assertions.assertEquals(136, axis.last());
        Assertions.assertEquals(46, axis.order());

        final Authority authority = vertCs.getAuthority();

        Assertions.assertEquals("EPSG", authority.getName().getSemantics());

        Assertions.assertEquals(148, authority.getName().first());
        Assertions.assertEquals(153, authority.getName().last());
        Assertions.assertEquals(50, authority.getName().order());

        Assertions.assertEquals("5701", authority.getCode().getSemantics());
        Assertions.assertEquals(155, authority.getCode().first());
        Assertions.assertEquals(160, authority.getCode().last());
        Assertions.assertEquals(52, authority.getCode().order());

        Assertions.assertEquals(138, authority.first());
        Assertions.assertEquals(161, authority.last());
        Assertions.assertEquals(54, authority.order());
    }

    @Test
    public void localCs_axisAuthority2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              LOCAL_CS("local cs name",\
                                              LOCAL_DATUM("local datum name",1,AUTHORITY("LOCAL","2")),\
                                              UNIT("metre",1,AUTHORITY("EPSG","9001")),\
                                              AXIS("Up",UP),\
                                              AXIS("East",EAST),\
                                              AUTHORITY("LOCAL","3"))""", '(', ')');

        final LocalCs localCs = parser.localCs();

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

        final Axis axis0 = localCs.getAxis().get(0);
        Assertions.assertEquals("Up", axis0.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis0.getDirection().getSemantics());

        final Axis axis1 = localCs.getAxis().get(1);
        Assertions.assertEquals("East", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());

        final Authority authority = localCs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("3", authority.getCode().getSemantics());
    }

    @Test
    public void fittedCs_axisAuthority2() throws LanguageException {

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

        final FittedCs fittedCs = parser.fittedCs();

        Assertions.assertTrue(fittedCs.getMathTransform() instanceof ConcatMt);

        final ConcatMt concatMt = (ConcatMt) fittedCs.getMathTransform();

        Assertions.assertEquals(2, concatMt.getTransforms().size());
        Assertions.assertTrue(concatMt.getTransforms().get(0) instanceof PassthroughMt);
        Assertions.assertTrue(concatMt.getTransforms().get(1) instanceof ConcatMt);

        final PassthroughMt passthroughMt = (PassthroughMt) concatMt.getTransforms().get(0);

        Assertions.assertEquals(4, passthroughMt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthroughMt.getTransform() instanceof InverseMt);

        final InverseMt inverseMt = (InverseMt) passthroughMt.getTransform();

        Assertions.assertTrue(inverseMt.getTransform() instanceof ParamMt);

        final ParamMt paramMt = (ParamMt) inverseMt.getTransform();

        Assertions.assertEquals("classification name", paramMt.getName().getSemantics());

        final List<Parameter> parameters = paramMt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());

        final ConcatMt inclConcatMt = (ConcatMt) concatMt.getTransforms().get(1);
        Assertions.assertEquals(1, inclConcatMt.getTransforms().size());
        Assertions.assertTrue(inclConcatMt.getTransforms().get(0) instanceof ParamMt);

        final ParamMt inclParamMt = (ParamMt) inclConcatMt.getTransforms().get(0);

        Assertions.assertEquals("second name", inclParamMt.getName().getSemantics());

        Assertions.assertTrue(inclParamMt.getParameters().isEmpty());

        Assertions.assertTrue(fittedCs.getCoordinateSystem() instanceof LocalCs);

        final LocalCs localCs = (LocalCs) fittedCs.getCoordinateSystem();

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

        final Axis axis0 = localCs.getAxis().get(0);
        Assertions.assertEquals("Up", axis0.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis0.getDirection().getSemantics());

        final Axis axis1 = localCs.getAxis().get(1);
        Assertions.assertEquals("East", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());

        final Authority authority = localCs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("3", authority.getCode().getSemantics());
    }

    @Test
    public void compdCs_axisAuthority2() throws LanguageException {

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

        final CompdCs compdCs = parser.compdCs();

        Assertions.assertTrue(compdCs.getHead() instanceof FittedCs);

        final FittedCs fittedCs = (FittedCs) compdCs.getHead();

        Assertions.assertTrue(fittedCs.getMathTransform() instanceof ConcatMt);

        final ConcatMt concatMt = (ConcatMt) fittedCs.getMathTransform();

        Assertions.assertEquals(2, concatMt.getTransforms().size());
        Assertions.assertTrue(concatMt.getTransforms().get(0) instanceof PassthroughMt);
        Assertions.assertTrue(concatMt.getTransforms().get(1) instanceof ConcatMt);

        final PassthroughMt passthroughMt = (PassthroughMt) concatMt.getTransforms().get(0);

        Assertions.assertEquals(4, passthroughMt.getInteger().getSemantics().intValue());
        Assertions.assertTrue(passthroughMt.getTransform() instanceof InverseMt);

        final InverseMt inverseMt = (InverseMt) passthroughMt.getTransform();

        Assertions.assertTrue(inverseMt.getTransform() instanceof ParamMt);

        final ParamMt paramMt = (ParamMt) inverseMt.getTransform();

        Assertions.assertEquals("classification name", paramMt.getName().getSemantics());

        final List<Parameter> parameters = paramMt.getParameters();

        Assertions.assertEquals(2, parameters.size());
        Assertions.assertEquals("first", parameters.get(0).getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, parameters.get(0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals("second", parameters.get(1).getName().getSemantics());
        Assertions.assertEquals(0.174532925199433, parameters.get(1).getValue().getSemantics().doubleValue());

        final ConcatMt inclConcatMt = (ConcatMt) concatMt.getTransforms().get(1);
        Assertions.assertEquals(1, inclConcatMt.getTransforms().size());
        Assertions.assertTrue(inclConcatMt.getTransforms().get(0) instanceof ParamMt);

        final ParamMt inclParamMt = (ParamMt) inclConcatMt.getTransforms().get(0);

        Assertions.assertEquals("second name", inclParamMt.getName().getSemantics());

        Assertions.assertTrue(inclParamMt.getParameters().isEmpty());

        Assertions.assertTrue(fittedCs.getCoordinateSystem() instanceof LocalCs);

        final LocalCs localCs = (LocalCs) fittedCs.getCoordinateSystem();

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

        final Axis axis0 = localCs.getAxis().get(0);
        Assertions.assertEquals("Up", axis0.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis0.getDirection().getSemantics());

        final Axis axis1 = localCs.getAxis().get(1);
        Assertions.assertEquals("East", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());

        final Authority authority = localCs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("3", authority.getCode().getSemantics());


        Assertions.assertTrue(compdCs.getTail() instanceof VertCs);

        final VertCs vertCs = (VertCs) compdCs.getTail();

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

        final Axis axis = vertCs.getAxis();

        Assertions.assertEquals("Up", axis.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis.getDirection().getSemantics());

        final Authority vertCsAuthority = vertCs.getAuthority();

        Assertions.assertEquals("EPSG", vertCsAuthority.getName().getSemantics());
        Assertions.assertEquals("5701", vertCsAuthority.getCode().getSemantics());


        final Authority compdCsAuthority = compdCs.getAuthority();
        Assertions.assertEquals("LOCAL", compdCsAuthority.getName().getSemantics());
        Assertions.assertEquals("10", compdCsAuthority.getCode().getSemantics());
    }

    @Test
    public void geoccs2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),UNIT("Degree",0.0174532925199433))""", '(', ')');

        final GeocentricCs geoccs = parser.geocentricCs();

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
    public void geoccs_axisAuthority2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),UNIT("Degree",0.0174532925199433),\
                                              AXIS("E",EAST),AXIS("N",NORTH),AXIS("U",UP),\
                                              AUTHORITY("LOCAL","1"))""", '(', ')');

        final GeocentricCs geoccs = parser.geocentricCs();

        Assertions.assertEquals("GCS_North_American_1983", geoccs.getName().getSemantics());

        final Datum datum = geoccs.getDatum();

        Assertions.assertEquals("D_North_American_1983", datum.getName().getSemantics());

        final Spheroid spheroid = datum.getSpheroid();

        Assertions.assertEquals(WktName.ELLIPSOID, spheroid.getLabel().getSemantics());
        Assertions.assertEquals("GRS_1980", spheroid.getName().getSemantics());
        Assertions.assertEquals(6378137, spheroid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertEquals(298.257222101, spheroid.getInverseFlattening().getSemantics().doubleValue());

        final PrimeMeridian primeMeridian = geoccs.getPrimeMeridian();

        Assertions.assertEquals("Greenwitch", primeMeridian.getName().getSemantics());
        Assertions.assertEquals(0., primeMeridian.getLongitude().getSemantics().doubleValue());

        final Unit unit = geoccs.getLinearUnit();

        Assertions.assertEquals("Degree", unit.getName().getSemantics());
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());

        final Axis axis1 = geoccs.getAxis1();
        Assertions.assertEquals("E", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());
        final Axis axis2 = geoccs.getAxis2();
        Assertions.assertEquals("N", axis2.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.NORTH, axis2.getDirection().getSemantics());
        final Axis axis3 = geoccs.getAxis3();
        Assertions.assertEquals("U", axis3.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.UP, axis3.getDirection().getSemantics());
        final Authority authority = geoccs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("1", authority.getCode().getSemantics());
    }

    @Test
    public void geogcs2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),UNIT("Degree",0.0174532925199433))""", '(', ')');

        final GeographicCs geogcs = parser.geographicCs();

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
    public void geogcs_3d2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree",0.0174532925199433),UNIT("Meter",1.0))""", '(', ')');

        final GeographicCs geogcs = parser.geographicCs();

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
    public void geogcs_axisAuthority2() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),UNIT("Degree",0.0174532925199433),\
                                              AXIS("E",EAST),AXIS("N",NORTH),AUTHORITY("LOCAL","1"))""", '(', ')');

        final GeographicCs geogcs = parser.geographicCs();

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

        Assertions.assertNull(geogcs.getLinearUnit());

        final Axis axis1 = geogcs.getAxis1();
        Assertions.assertEquals("E", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());
        final Axis axis2 = geogcs.getAxis2();
        Assertions.assertEquals("N", axis2.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.NORTH, axis2.getDirection().getSemantics());

        final Authority authority = geogcs.getAuthority();
        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("1", authority.getCode().getSemantics());
    }

    @Test
    public void projcs2() throws LanguageException {

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

        final ProjectedCs projcs = parser.projectedCs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(440, projcs.last());
        Assertions.assertEquals(145, projcs.order());

        final GeographicCs geogcs = projcs.getGeogCs();

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
    public void projcs_axis2() throws LanguageException {

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
                                              UNIT("Meter",1.0),\
                                              AXIS("Easting",EAST),AXIS("Northing",NORTH))""", '(', ')');

        final ProjectedCs projcs = parser.projectedCs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(484, projcs.last());
        Assertions.assertEquals(161, projcs.order());

        final GeographicCs geogcs = projcs.getGeogCs();

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

        final Axis axis1 = projcs.getAxis1();

        Assertions.assertEquals("Easting", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());

        final Axis axis2 = projcs.getAxis2();

        Assertions.assertEquals("Northing", axis2.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.NORTH, axis2.getDirection().getSemantics());
    }

    @Test
    public void projcs_axisAuthority2() throws LanguageException {

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
                                              UNIT("Meter",1.0),\
                                              AXIS("Easting",EAST),AXIS("Northing",NORTH),\
                                              AUTHORITY("LOCAL","10"))""", '(', ')');

        final ProjectedCs projcs = parser.projectedCs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(508, projcs.last());
        Assertions.assertEquals(169, projcs.order());

        final GeographicCs geogcs = projcs.getGeogCs();

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

        final Axis axis1 = projcs.getAxis1();

        Assertions.assertEquals("Easting", axis1.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.EAST, axis1.getDirection().getSemantics());

        final Axis axis2 = projcs.getAxis2();

        Assertions.assertEquals("Northing", axis2.getName().getSemantics());
        Assertions.assertEquals(AxisDirectionName.NORTH, axis2.getDirection().getSemantics());

        final Authority authority = projcs.getAuthority();

        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("10", authority.getCode().getSemantics());
    }

    @Test
    public void projcsAuthority2() throws LanguageException {

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
                                              UNIT("Meter",1.0),\
                                              AUTHORITY("LOCAL","10"))""", '(', ')');

        final ProjectedCs projcs = parser.projectedCs();

        Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

        Assertions.assertEquals(7, projcs.getName().first());
        Assertions.assertEquals(29, projcs.getName().last());
        Assertions.assertEquals(2, projcs.getName().order());

        Assertions.assertEquals(0, projcs.first());
        Assertions.assertEquals(464, projcs.last());
        Assertions.assertEquals(153, projcs.order());

        final GeographicCs geogcs = projcs.getGeogCs();

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

        Assertions.assertNull(projcs.getAxis1());
        Assertions.assertNull(projcs.getAxis2());

        final Authority authority = projcs.getAuthority();

        Assertions.assertEquals("LOCAL", authority.getName().getSemantics());
        Assertions.assertEquals("10", authority.getCode().getSemantics());
    }

    @Test
    public void spatial_reference_system2_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOCCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),UNIT("Degree",0.0174532925199433))""", '(', ')');

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
    public void spatial_reference_system2_2() throws LanguageException {

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
    public void spatial_reference_system2_3() throws LanguageException {

        final WktParser parser = WktParser.of("""
                                              GEOGCS("GCS_North_American_1983",\
                                              DATUM("D_North_American_1983",\
                                              ELLIPSOID("GRS_1980",6378137,298.257222101)),\
                                              PRIMEM("Greenwitch",0),\
                                              UNIT("Degree",0.0174532925199433),UNIT("Meter",1.0))""", '(', ')');

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
    public void spatial_reference_system2_4() throws LanguageException {

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

        final GeographicCs geogcs = projcs.getGeogCs();

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
    public void projcs3a() throws LanguageException, IOException {

        try (DefaultStreamScanner scanner = new DefaultStreamScanner(new BufferedReader(new InputStreamReader(
                WktParserTest.class.getResourceAsStream("example3a"))))) {

            final WktLexer lexer = new WktLexer(scanner, new ArrayList<>(), LD, RD);
            lexer.initialize();
            final WktParser parser = new WktParser(lexer);

            final ProjectedCs projcs = parser.projectedCs();

            Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

            Assertions.assertEquals(7, projcs.getName().first());
            Assertions.assertEquals(29, projcs.getName().last());
            Assertions.assertEquals(2, projcs.getName().order());

            Assertions.assertEquals(0, projcs.first());
            Assertions.assertEquals(440, projcs.last());
            Assertions.assertEquals(145, projcs.order());

            final GeographicCs geogcs = projcs.getGeogCs();

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
    public void projcs3b() throws LanguageException, IOException {

        try (DefaultStreamScanner scanner = new DefaultStreamScanner(new BufferedReader(new InputStreamReader(
                WktParserTest.class.getResourceAsStream("example3b"))))) {

            final WktLexer lexer = new WktLexer(scanner, new ArrayList<>(), '(', ')');
            lexer.initialize();
            final WktParser parser = new WktParser(lexer);

            final ProjectedCs projcs = parser.projectedCs();

            Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

            Assertions.assertEquals(7, projcs.getName().first());
            Assertions.assertEquals(29, projcs.getName().last());
            Assertions.assertEquals(2, projcs.getName().order());

            Assertions.assertEquals(0, projcs.first());
            Assertions.assertEquals(440, projcs.last());
            Assertions.assertEquals(145, projcs.order());

            final GeographicCs geogcs = projcs.getGeogCs();

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
    public void projcs3c() throws LanguageException, IOException {

        try (DefaultStreamScanner scanner = new DefaultStreamScanner(new BufferedReader(new InputStreamReader(
                WktParserTest.class.getResourceAsStream("example3c"))))) {

            final WktLexer lexer = new WktLexer(scanner, new ArrayList<>(), LD, RD);
            lexer.initialize();
            final WktParser parser = new WktParser(lexer);

            final ProjectedCs projcs = parser.projectedCs();

            Assertions.assertEquals("NAD_1983_UTM_Zone_10N", projcs.getName().getSemantics());

            final GeographicCs geogcs = projcs.getGeogCs();

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
}
