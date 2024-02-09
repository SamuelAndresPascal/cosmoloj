package com.cosmoloj.language.wkt2.v2_1.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt2.v2_1.expression.Anchor;
import com.cosmoloj.language.wkt2.v2_1.expression.Area;
import com.cosmoloj.language.wkt2.v2_1.expression.AxisRangeMeaning;
import com.cosmoloj.language.wkt2.v2_1.expression.BBox;
import com.cosmoloj.language.wkt2.v2_1.expression.BaseGeodeticCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.BaseProjectedCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.Citation;
import com.cosmoloj.language.wkt2.v2_1.expression.CoordinateSystem;
import com.cosmoloj.language.wkt2.v2_1.expression.DerivedCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.Extent;
import com.cosmoloj.language.wkt2.v2_1.expression.GeodeticCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.Identifier;
import com.cosmoloj.language.wkt2.v2_1.expression.Meridian;
import com.cosmoloj.language.wkt2.v2_1.expression.Parameter;
import com.cosmoloj.language.wkt2.v2_1.expression.ParameterAbridged;
import com.cosmoloj.language.wkt2.v2_1.expression.ParameterFile;
import com.cosmoloj.language.wkt2.v2_1.expression.PrimeMeridian;
import com.cosmoloj.language.wkt2.v2_1.expression.Remark;
import com.cosmoloj.language.wkt2.v2_1.expression.SimpleCrsShell;
import com.cosmoloj.language.wkt2.v2_1.expression.SimpleNumber;
import com.cosmoloj.language.wkt2.v2_1.expression.TemporalExtent;
import com.cosmoloj.language.wkt2.v2_1.expression.Unit;
import com.cosmoloj.language.wkt2.v2_1.expression.Uri;
import com.cosmoloj.language.wkt2.v2_1.expression.VerticalExtent;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.CsType;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.Direction;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.PixelInCell;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.RangeMeaningType;
import java.time.LocalDate;
import java.time.Month;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class WktParserBracketTest {

    @Test
    public void bound_crs_test_c_1() throws LanguageException {

        final WktParser parser = WktParser.of("""
                         BOUNDCRS[
                         SOURCECRS[
                         GEODCRS["NAD27",
                         DATUM["North American Datum 1927",
                         ELLIPSOID["Clarke 1866",6378206.4,294.978698213]],
                         CS[ellipsoidal,2],
                         AXIS["latitude",north],AXIS["longitude",east],
                         ANGLEUNIT["degree",0.0174532925199433]]],
                         TARGETCRS[
                         GEODCRS["NAD83",
                         DATUM["North American Datum 1983",
                         ELLIPSOID["GRS 1980",6378137,298.2572221]],
                         CS[ellipsoidal,2],
                         AXIS["latitude",north],AXIS["longitude",east],
                         ANGLEUNIT["degree",0.0174532925199433]]],
                         ABRIDGEDTRANSFORMATION["Amersfoort to ETRS89 (3)",
                         METHOD["Coordinate Frame",ID["EPSG",1032]],
                         PARAMETER["X-axis translation",565.2369,ID["EPSG",8605]],
                         PARAMETER["Y-axis translation",50.0087,ID["EPSG",8606]],
                         PARAMETER["Z-axis translation",465.658,ID["EPSG",8607]],
                         PARAMETER["X-axis rotation",0.407,ID["EPSG",8608]],
                         PARAMETER["Y-axis rotation",-0.351,ID["EPSG",8609]],
                         PARAMETER["Z-axis rotation",1.870,ID["EPSG",8610]],
                         PARAMETER["Scale difference",1.000004812,ID["EPSG",8611]]]]""");

        final var bound = parser.boundCrs();

        // source

        Assertions.assertTrue(bound.getSource().getCrs() instanceof GeodeticCrs);
        final var source = (GeodeticCrs) bound.getSource().getCrs();
        Assertions.assertEquals("NAD27", source.getName().getSemantics());

        final var sourceDatum = source.getDatum();
        Assertions.assertEquals("North American Datum 1927", sourceDatum.getName().getSemantics());

        final var sourceEllipsoid = sourceDatum.getEllipsoid();
        Assertions.assertEquals("Clarke 1866", sourceEllipsoid.getName().getSemantics());
        Assertions.assertTrue(sourceEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6378206.4, sourceEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(sourceEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(294.978698213, sourceEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        Assertions.assertNull(sourceEllipsoid.getUnit());

        final var sourceCs = source.getCoordinateSystem();
        Assertions.assertEquals(CsType.ELLIPSOIDAL, sourceCs.getType().getSemantics());
        Assertions.assertEquals(2, sourceCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(2, sourceCs.getAxis().size());

        final var sAxis0 = sourceCs.getAxis().get(0);
        Assertions.assertEquals("latitude", sAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, sAxis0.getDirection().getType().getSemantics());
        Assertions.assertNull(sAxis0.getOrder());

        final var sAxis1 = sourceCs.getAxis().get(1);
        Assertions.assertEquals("longitude", sAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, sAxis1.getDirection().getType().getSemantics());
        Assertions.assertNull(sAxis1.getOrder());

        final var sourceCsUnit = sourceCs.getUnit();
        Assertions.assertTrue(sourceCsUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", sourceCsUnit.getName().getSemantics());
        Assertions.assertTrue(sourceCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, sourceCsUnit.getConversionFactor().getSemantics().doubleValue());

        // target

        Assertions.assertTrue(bound.getTarget().getCrs() instanceof GeodeticCrs);
        final var target = (GeodeticCrs) bound.getTarget().getCrs();
        Assertions.assertEquals("NAD83", target.getName().getSemantics());

        final var targetDatum = target.getDatum();
        Assertions.assertEquals("North American Datum 1983", targetDatum.getName().getSemantics());

        final var targetEllipsoid = targetDatum.getEllipsoid();
        Assertions.assertEquals("GRS 1980", targetEllipsoid.getName().getSemantics());
        Assertions.assertTrue(targetEllipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, targetEllipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(targetEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.2572221, targetEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        Assertions.assertNull(targetEllipsoid.getUnit());

        final var targetCs = target.getCoordinateSystem();
        Assertions.assertEquals(CsType.ELLIPSOIDAL, targetCs.getType().getSemantics());
        Assertions.assertEquals(2, targetCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(2, targetCs.getAxis().size());

        final var tAxis0 = targetCs.getAxis().get(0);
        Assertions.assertEquals("latitude", tAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, tAxis0.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis0.getOrder());

        final var tAxis1 = targetCs.getAxis().get(1);
        Assertions.assertEquals("longitude", tAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, tAxis1.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis1.getOrder());

        final var targetCsUnit = targetCs.getUnit();
        Assertions.assertTrue(targetCsUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", targetCsUnit.getName().getSemantics());
        Assertions.assertTrue(targetCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, targetCsUnit.getConversionFactor().getSemantics().doubleValue());

        final var abridged = bound.getTransformation();

        Assertions.assertEquals("Amersfoort to ETRS89 (3)", abridged.getName().getSemantics());

        final var method = abridged.getMethod();

        Assertions.assertEquals("Coordinate Frame", method.getName().getSemantics());
        Assertions.assertEquals(1, method.getIdentifiers().size());

        final var id = method.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id.getName().getSemantics());
        Assertions.assertTrue(id.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(1032, ((Integer) id.getId().getSemantics()).intValue());


        Assertions.assertEquals(7, abridged.getParameters().size());

        final var param0 = abridged.getParameters().get(0);
        Assertions.assertTrue(param0 instanceof ParameterAbridged);
        Assertions.assertEquals("X-axis translation", param0.getName().getSemantics());
        Assertions.assertTrue(((ParameterAbridged) param0).getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(565.2369, ((ParameterAbridged) param0).getValue().getSemantics().doubleValue());
        Assertions.assertEquals(1, param0.getIdentifiers().size());
        final var id0 = param0.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id0.getName().getSemantics());
        Assertions.assertTrue(id0.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8605, ((Integer) id0.getId().getSemantics()).intValue());

        final var param1 = abridged.getParameters().get(1);
        Assertions.assertTrue(param1 instanceof ParameterAbridged);
        Assertions.assertEquals("Y-axis translation", param1.getName().getSemantics());
        Assertions.assertTrue(((ParameterAbridged) param1).getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(50.0087, ((ParameterAbridged) param1).getValue().getSemantics().doubleValue());
        Assertions.assertEquals(1, param1.getIdentifiers().size());
        final var id1 = param1.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id1.getName().getSemantics());
        Assertions.assertTrue(id1.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8606, ((Integer) id1.getId().getSemantics()).intValue());

        final var param2 = abridged.getParameters().get(2);
        Assertions.assertTrue(param2 instanceof ParameterAbridged);
        Assertions.assertEquals("Z-axis translation", param2.getName().getSemantics());
        Assertions.assertTrue(((ParameterAbridged) param2).getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(465.658, ((ParameterAbridged) param2).getValue().getSemantics().doubleValue());
        Assertions.assertEquals(1, param2.getIdentifiers().size());
        final var id2 = param2.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id2.getName().getSemantics());
        Assertions.assertTrue(id2.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8607, ((Integer) id2.getId().getSemantics()).intValue());

        final var param3 = abridged.getParameters().get(3);
        Assertions.assertTrue(param3 instanceof ParameterAbridged);
        Assertions.assertEquals("X-axis rotation", param3.getName().getSemantics());
        Assertions.assertTrue(((ParameterAbridged) param3).getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(0.407, ((ParameterAbridged) param3).getValue().getSemantics().doubleValue());
        Assertions.assertEquals(1, param3.getIdentifiers().size());
        final var id3 = param3.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id3.getName().getSemantics());
        Assertions.assertTrue(id3.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8608, ((Integer) id3.getId().getSemantics()).intValue());

        final var param4 = abridged.getParameters().get(4);
        Assertions.assertTrue(param4 instanceof ParameterAbridged);
        Assertions.assertEquals("Y-axis rotation", param4.getName().getSemantics());
        Assertions.assertTrue(((ParameterAbridged) param4).getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(-0.351, ((ParameterAbridged) param4).getValue().getSemantics().doubleValue());
        Assertions.assertEquals(1, param4.getIdentifiers().size());
        final var id4 = param4.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id4.getName().getSemantics());
        Assertions.assertTrue(id4.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8609, ((Integer) id4.getId().getSemantics()).intValue());

        final var param5 = abridged.getParameters().get(5);
        Assertions.assertTrue(param5 instanceof ParameterAbridged);
        Assertions.assertEquals("Z-axis rotation", param5.getName().getSemantics());
        Assertions.assertTrue(((ParameterAbridged) param5).getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(1.870, ((ParameterAbridged) param5).getValue().getSemantics().doubleValue());
        Assertions.assertEquals(1, param5.getIdentifiers().size());
        final var id5 = param5.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id5.getName().getSemantics());
        Assertions.assertTrue(id5.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8610, ((Integer) id5.getId().getSemantics()).intValue());

        final var param6 = abridged.getParameters().get(6);
        Assertions.assertTrue(param6 instanceof ParameterAbridged);
        Assertions.assertEquals("Scale difference", param6.getName().getSemantics());
        Assertions.assertTrue(((ParameterAbridged) param6).getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(1.000004812, ((ParameterAbridged) param6).getValue().getSemantics().doubleValue());
        Assertions.assertEquals(1, param6.getIdentifiers().size());
        final var id6 = param6.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id6.getName().getSemantics());
        Assertions.assertTrue(id6.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8611, ((Integer) id6.getId().getSemantics()).intValue());
    }

    @Test
    public void bound_crs_test_b_1() throws LanguageException {

        final var text = "BOUNDCRS["
                + "SOURCECRS["
                + "GEODCRS[\"NAD27\","
                + "DATUM[\"North American Datum 1927\","
                + "ELLIPSOID[\"Clarke 1866\",6378206.4,294.978698213]],"
                + "CS[ellipsoidal,2],"
                + "AXIS[\"latitude\",north],AXIS[\"longitude\",east],"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]]],"
                + "TARGETCRS["
                + "GEODCRS[\"NAD83\","
                + "DATUM[\"North American Datum 1983\","
                + "ELLIPSOID[\"GRS 1980\",6378137,298.2572221]],"
                + "CS[ellipsoidal,2],"
                + "AXIS[\"latitude\",north],AXIS[\"longitude\",east],"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]]],"
                + "ABRIDGEDTRANSFORMATION[\"NAD27 to NAD83(86) National\","
                + "METHOD[\"NTv2\",ID[\"EPSG\",9615]],"
                + "PARAMETERFILE[\"Latitude and longitude difference file\",\"NTv2_0.gsb\"]]]";

        final WktParser parser = WktParser.of(text);

        final var bound = parser.boundCrs();

        // source

        Assertions.assertTrue(bound.getSource().getCrs() instanceof GeodeticCrs);
        final var source = (GeodeticCrs) bound.getSource().getCrs();
        Assertions.assertEquals("NAD27", source.getName().getSemantics());

        final var sourceDatum = source.getDatum();
        Assertions.assertEquals("North American Datum 1927", sourceDatum.getName().getSemantics());

        final var sourceEllipsoid = sourceDatum.getEllipsoid();
        Assertions.assertEquals("Clarke 1866", sourceEllipsoid.getName().getSemantics());
        Assertions.assertTrue(sourceEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6378206.4, sourceEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(sourceEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(294.978698213, sourceEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        Assertions.assertNull(sourceEllipsoid.getUnit());

        final var sourceCs = source.getCoordinateSystem();
        Assertions.assertEquals(CsType.ELLIPSOIDAL, sourceCs.getType().getSemantics());
        Assertions.assertEquals(2, sourceCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(2, sourceCs.getAxis().size());

        final var sAxis0 = sourceCs.getAxis().get(0);
        Assertions.assertEquals("latitude", sAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, sAxis0.getDirection().getType().getSemantics());
        Assertions.assertNull(sAxis0.getOrder());

        final var sAxis1 = sourceCs.getAxis().get(1);
        Assertions.assertEquals("longitude", sAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, sAxis1.getDirection().getType().getSemantics());
        Assertions.assertNull(sAxis1.getOrder());

        final var sourceCsUnit = sourceCs.getUnit();
        Assertions.assertTrue(sourceCsUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", sourceCsUnit.getName().getSemantics());
        Assertions.assertTrue(sourceCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, sourceCsUnit.getConversionFactor().getSemantics().doubleValue());

        // target

        Assertions.assertTrue(bound.getTarget().getCrs() instanceof GeodeticCrs);
        final var target = (GeodeticCrs) bound.getTarget().getCrs();
        Assertions.assertEquals("NAD83", target.getName().getSemantics());

        final var targetDatum = target.getDatum();
        Assertions.assertEquals("North American Datum 1983", targetDatum.getName().getSemantics());

        final var targetEllipsoid = targetDatum.getEllipsoid();
        Assertions.assertEquals("GRS 1980", targetEllipsoid.getName().getSemantics());
        Assertions.assertTrue(targetEllipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, targetEllipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(targetEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.2572221, targetEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        Assertions.assertNull(targetEllipsoid.getUnit());

        final var targetCs = target.getCoordinateSystem();
        Assertions.assertEquals(CsType.ELLIPSOIDAL, targetCs.getType().getSemantics());
        Assertions.assertEquals(2, targetCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(2, targetCs.getAxis().size());

        final var tAxis0 = targetCs.getAxis().get(0);
        Assertions.assertEquals("latitude", tAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, tAxis0.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis0.getOrder());

        final var tAxis1 = targetCs.getAxis().get(1);
        Assertions.assertEquals("longitude", tAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, tAxis1.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis1.getOrder());

        final var targetCsUnit = targetCs.getUnit();
        Assertions.assertTrue(targetCsUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", targetCsUnit.getName().getSemantics());
        Assertions.assertTrue(targetCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, targetCsUnit.getConversionFactor().getSemantics().doubleValue());

        final var abridged = bound.getTransformation();

        Assertions.assertEquals("NAD27 to NAD83(86) National", abridged.getName().getSemantics());

        final var method = abridged.getMethod();

        Assertions.assertEquals("NTv2", method.getName().getSemantics());
        Assertions.assertEquals(1, method.getIdentifiers().size());

        final var id = method.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id.getName().getSemantics());
        Assertions.assertTrue(id.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(9615, ((Integer) id.getId().getSemantics()).intValue());

        Assertions.assertEquals(1, abridged.getParameters().size());

        final var param0 = abridged.getParameters().get(0);
        Assertions.assertTrue(param0 instanceof ParameterFile);
        Assertions.assertEquals("Latitude and longitude difference file", param0.getName().getSemantics());
        Assertions.assertEquals("NTv2_0.gsb", ((ParameterFile) param0).getFileName().getSemantics());
    }

    @Test
    public void bound_crs_test_a_1() throws LanguageException {

        final var text = "BOUNDCRS["
                + "SOURCECRS["
                + "GEODCRS[\"NAD27\","
                + "DATUM[\"North American Datum 1927\","
                + "ELLIPSOID[\"Clarke 1866\",6378206.4,294.978698213]],"
                + "CS[ellipsoidal,2],"
                + "AXIS[\"latitude\",north],AXIS[\"longitude\",east],"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]]],"
                + "TARGETCRS["
                + "GEODCRS[\"NAD83\","
                + "DATUM[\"North American Datum 1983\","
                + "ELLIPSOID[\"GRS 1980\",6378137,298.2572221]],"
                + "CS[ellipsoidal,2],"
                + "AXIS[\"latitude\",north],AXIS[\"longitude\",east],"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]]],"
                + "ABRIDGEDTRANSFORMATION[\"NAD27 to NAD83 Alaska\","
                + "METHOD[\"NADCON\",ID[\"EPSG\",9613]],"
                + "PARAMETERFILE[\"Latitude difference file\",\"alaska.las\"],"
                + "PARAMETERFILE[\"Longitude difference file\",\"alaska.los\"]]]";

        final WktParser parser = WktParser.of(text);

        final var bound = parser.boundCrs();

        // source

        Assertions.assertTrue(bound.getSource().getCrs() instanceof GeodeticCrs);
        final var source = (GeodeticCrs) bound.getSource().getCrs();
        Assertions.assertEquals("NAD27", source.getName().getSemantics());

        final var sourceDatum = source.getDatum();
        Assertions.assertEquals("North American Datum 1927", sourceDatum.getName().getSemantics());

        final var sourceEllipsoid = sourceDatum.getEllipsoid();
        Assertions.assertEquals("Clarke 1866", sourceEllipsoid.getName().getSemantics());
        Assertions.assertTrue(sourceEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6378206.4, sourceEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(sourceEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(294.978698213, sourceEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        Assertions.assertNull(sourceEllipsoid.getUnit());

        final var sourceCs = source.getCoordinateSystem();
        Assertions.assertEquals(CsType.ELLIPSOIDAL, sourceCs.getType().getSemantics());
        Assertions.assertEquals(2, sourceCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(2, sourceCs.getAxis().size());

        final var sAxis0 = sourceCs.getAxis().get(0);
        Assertions.assertEquals("latitude", sAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, sAxis0.getDirection().getType().getSemantics());
        Assertions.assertNull(sAxis0.getOrder());

        final var sAxis1 = sourceCs.getAxis().get(1);
        Assertions.assertEquals("longitude", sAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, sAxis1.getDirection().getType().getSemantics());
        Assertions.assertNull(sAxis1.getOrder());

        final var sourceCsUnit = sourceCs.getUnit();
        Assertions.assertTrue(sourceCsUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", sourceCsUnit.getName().getSemantics());
        Assertions.assertTrue(sourceCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, sourceCsUnit.getConversionFactor().getSemantics().doubleValue());

        // target

        Assertions.assertTrue(bound.getTarget().getCrs() instanceof GeodeticCrs);
        final var target = (GeodeticCrs) bound.getTarget().getCrs();
        Assertions.assertEquals("NAD83", target.getName().getSemantics());

        final var targetDatum = target.getDatum();
        Assertions.assertEquals("North American Datum 1983", targetDatum.getName().getSemantics());

        final var targetEllipsoid = targetDatum.getEllipsoid();
        Assertions.assertEquals("GRS 1980", targetEllipsoid.getName().getSemantics());
        Assertions.assertTrue(targetEllipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, targetEllipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(targetEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.2572221, targetEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        Assertions.assertNull(targetEllipsoid.getUnit());

        final var targetCs = target.getCoordinateSystem();
        Assertions.assertEquals(CsType.ELLIPSOIDAL, targetCs.getType().getSemantics());
        Assertions.assertEquals(2, targetCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(2, targetCs.getAxis().size());

        final var tAxis0 = targetCs.getAxis().get(0);
        Assertions.assertEquals("latitude", tAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, tAxis0.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis0.getOrder());

        final var tAxis1 = targetCs.getAxis().get(1);
        Assertions.assertEquals("longitude", tAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, tAxis1.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis1.getOrder());

        final var targetCsUnit = targetCs.getUnit();
        Assertions.assertTrue(targetCsUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", targetCsUnit.getName().getSemantics());
        Assertions.assertTrue(targetCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, targetCsUnit.getConversionFactor().getSemantics().doubleValue());

        final var abridged = bound.getTransformation();

        Assertions.assertEquals("NAD27 to NAD83 Alaska", abridged.getName().getSemantics());

        final var method = abridged.getMethod();

        Assertions.assertEquals("NADCON", method.getName().getSemantics());
        Assertions.assertEquals(1, method.getIdentifiers().size());

        final var id = method.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id.getName().getSemantics());
        Assertions.assertTrue(id.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(9613, ((Integer) id.getId().getSemantics()).intValue());

        Assertions.assertEquals(2, abridged.getParameters().size());

        final var param0 = abridged.getParameters().get(0);
        Assertions.assertTrue(param0 instanceof ParameterFile);
        Assertions.assertEquals("Latitude difference file", param0.getName().getSemantics());
        Assertions.assertEquals("alaska.las", ((ParameterFile) param0).getFileName().getSemantics());
        final var param1 = abridged.getParameters().get(1);
        Assertions.assertTrue(param1 instanceof ParameterFile);
        Assertions.assertEquals("Longitude difference file", param1.getName().getSemantics());
        Assertions.assertEquals("alaska.los", ((ParameterFile) param1).getFileName().getSemantics());
    }

    @Test
    public void abridged_transformation_test_a_1() throws LanguageException {

        final var text = "ABRIDGEDTRANSFORMATION[\"Tokyo to JGD2000 (GSI)\","
                + "METHOD[\"Geocentric translations\",ID[\"EPSG\",1031]],"
                + "PARAMETER[\"X-axis translation\",-146.414],"
                + "PARAMETER[\"Y-axis translation\",507.337],"
                + "PARAMETER[\"Z-axis translation\",680.507]]";

        final WktParser parser = WktParser.of(text);

        final var abridged = parser.abridgedTransformation();

        Assertions.assertEquals("Tokyo to JGD2000 (GSI)", abridged.getName().getSemantics());

        final var method = abridged.getMethod();

        Assertions.assertEquals("Geocentric translations", method.getName().getSemantics());
        Assertions.assertEquals(1, method.getIdentifiers().size());

        final var id = method.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id.getName().getSemantics());
        Assertions.assertTrue(id.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(1031, ((Integer) id.getId().getSemantics()).intValue());

        Assertions.assertEquals(3, abridged.getParameters().size());

        final var param0 = abridged.getParameters().get(0);
        Assertions.assertTrue(param0 instanceof ParameterAbridged);
        Assertions.assertEquals("X-axis translation", param0.getName().getSemantics());
        Assertions.assertTrue(((ParameterAbridged) param0).getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(-146.414, ((ParameterAbridged) param0).getValue().getSemantics().doubleValue());
        final var param1 = abridged.getParameters().get(1);
        Assertions.assertTrue(param1 instanceof ParameterAbridged);
        Assertions.assertEquals("Y-axis translation", param1.getName().getSemantics());
        Assertions.assertTrue(((ParameterAbridged) param1).getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(507.337, ((ParameterAbridged) param1).getValue().getSemantics().doubleValue());
        final var param2 = abridged.getParameters().get(2);
        Assertions.assertTrue(param2 instanceof ParameterAbridged);
        Assertions.assertEquals("Z-axis translation", param2.getName().getSemantics());
        Assertions.assertTrue(((ParameterAbridged) param2).getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(680.507, ((ParameterAbridged) param2).getValue().getSemantics().doubleValue());
    }

    @Test
    public void coordinate_operation_test_a_1() throws LanguageException {

        final var text = "COORDINATEOPERATION[\"Tokyo to JGD2000 (GSI)\","
                + "SOURCECRS["
                + "GEODCRS[\"Tokyo\","
                + "DATUM[\"Tokyo 1918\","
                + "ELLIPSOID[\"Bessel 1841\",6377397.155,299.1528128,LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[Cartesian,3],"
                + "AXIS[\"(X)\",geocentricX,ORDER[1]],"
                + "AXIS[\"(Y)\",geocentricY,ORDER[2]],"
                + "AXIS[\"(Z)\",geocentricZ,ORDER[3]],"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "TARGETCRS["
                + "GEODCRS[\"JGD2000\","
                + "DATUM[\"Japanese Geodetic Datum 2000\","
                + "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101,LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[Cartesian,3],"
                + "AXIS[\"(X)\",geocentricX],"
                + "AXIS[\"(Y)\",geocentricY],"
                + "AXIS[\"(Z)\",geocentricZ],"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "METHOD[\"Geocentric translations\",ID[\"EPSG\",1031]],"
                + "PARAMETER[\"X-axis translation\",-146.414,"
                + "LENGTHUNIT[\"metre\",1.0],ID[\"EPSG\",8605]],"
                + "PARAMETER[\"Y-axis translation\",507.337,"
                + "LENGTHUNIT[\"metre\",1.0],ID[\"EPSG\",8606]],"
                + "PARAMETER[\"Z-axis translation\",680.507,"
                + "LENGTHUNIT[\"metre\",1.0],ID[\"EPSG\",8607]]]";

        final WktParser parser = WktParser.of(text);

        final var coordOp = parser.coordinateOperation();

        Assertions.assertEquals("Tokyo to JGD2000 (GSI)", coordOp.getName().getSemantics());

        // source

        Assertions.assertTrue(coordOp.getSource().getCrs() instanceof GeodeticCrs);
        final var source = (GeodeticCrs) coordOp.getSource().getCrs();
        Assertions.assertEquals("Tokyo", source.getName().getSemantics());

        final var sourceDatum = source.getDatum();
        Assertions.assertEquals("Tokyo 1918", sourceDatum.getName().getSemantics());

        final var sourceEllipsoid = sourceDatum.getEllipsoid();
        Assertions.assertEquals("Bessel 1841", sourceEllipsoid.getName().getSemantics());
        Assertions.assertTrue(sourceEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6377397.155, sourceEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(sourceEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(299.1528128, sourceEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        final var sourceEllUnit = sourceEllipsoid.getUnit();
        Assertions.assertEquals("metre", sourceEllUnit.getName().getSemantics());
        Assertions.assertTrue(sourceEllUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, sourceEllUnit.getConversionFactor().getSemantics().doubleValue());

        final var sourceCs = source.getCoordinateSystem();
        Assertions.assertEquals(CsType.CARTESIAN, sourceCs.getType().getSemantics());
        Assertions.assertEquals(3, sourceCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(3, sourceCs.getAxis().size());

        final var sAxis0 = sourceCs.getAxis().get(0);
        Assertions.assertEquals("(X)", sAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricX, sAxis0.getDirection().getType().getSemantics());
        Assertions.assertEquals(1, sAxis0.getOrder().getValue().getSemantics().intValue());

        final var sAxis1 = sourceCs.getAxis().get(1);
        Assertions.assertEquals("(Y)", sAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricY, sAxis1.getDirection().getType().getSemantics());
        Assertions.assertEquals(2, sAxis1.getOrder().getValue().getSemantics().intValue());

        final var sAxis2 = sourceCs.getAxis().get(2);
        Assertions.assertEquals("(Z)", sAxis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricZ, sAxis2.getDirection().getType().getSemantics());
        Assertions.assertEquals(3, sAxis2.getOrder().getValue().getSemantics().intValue());

        final var sourceCsUnit = sourceCs.getUnit();
        Assertions.assertTrue(sourceCsUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", sourceCsUnit.getName().getSemantics());
        Assertions.assertTrue(sourceCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, sourceCsUnit.getConversionFactor().getSemantics().doubleValue());

        // target

        Assertions.assertTrue(coordOp.getTarget().getCrs() instanceof GeodeticCrs);
        final var target = (GeodeticCrs) coordOp.getTarget().getCrs();
        Assertions.assertEquals("JGD2000", target.getName().getSemantics());

        final var targetDatum = target.getDatum();
        Assertions.assertEquals("Japanese Geodetic Datum 2000", targetDatum.getName().getSemantics());

        final var targetEllipsoid = targetDatum.getEllipsoid();
        Assertions.assertEquals("GRS 1980", targetEllipsoid.getName().getSemantics());
        Assertions.assertTrue(targetEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6378137.0, targetEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(targetEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257222101, targetEllipsoid.getInverseFlattening().getSemantics().doubleValue());

        final var targetEllUnit = targetEllipsoid.getUnit();
        Assertions.assertEquals("metre", targetEllUnit.getName().getSemantics());
        Assertions.assertTrue(targetEllUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, targetEllUnit.getConversionFactor().getSemantics().doubleValue());

        final var targetCs = target.getCoordinateSystem();
        Assertions.assertEquals(CsType.CARTESIAN, targetCs.getType().getSemantics());
        Assertions.assertEquals(3, targetCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(3, targetCs.getAxis().size());

        final var tAxis0 = targetCs.getAxis().get(0);
        Assertions.assertEquals("(X)", tAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricX, tAxis0.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis0.getOrder());

        final var tAxis1 = targetCs.getAxis().get(1);
        Assertions.assertEquals("(Y)", tAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricY, tAxis1.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis1.getOrder());

        final var tAxis2 = targetCs.getAxis().get(2);
        Assertions.assertEquals("(Z)", tAxis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricZ, tAxis2.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis2.getOrder());

        final var targetCsUnit = targetCs.getUnit();
        Assertions.assertTrue(targetCsUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", targetCsUnit.getName().getSemantics());
        Assertions.assertTrue(targetCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, targetCsUnit.getConversionFactor().getSemantics().doubleValue());

        // operation

        final var method = coordOp.getMethod();
        Assertions.assertEquals("Geocentric translations", method.getName().getSemantics());
        Assertions.assertEquals(1, method.getIdentifiers().size());
        final var id = method.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id.getName().getSemantics());
        Assertions.assertTrue(id.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(1031, ((Integer) id.getId().getSemantics()).intValue());

        Assertions.assertEquals(3, coordOp.getParameters().size());

        Assertions.assertTrue(coordOp.getParameters().get(0) instanceof Parameter);
        final var param0 = (Parameter) coordOp.getParameters().get(0);
        Assertions.assertEquals("X-axis translation", param0.getName().getSemantics());
        Assertions.assertTrue(param0.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(-146.414, param0.getValue().getSemantics().doubleValue());
        final var param0Unit = param0.getUnit();
        Assertions.assertTrue(param0Unit instanceof Unit.Length);
        Assertions.assertEquals("metre", param0Unit.getName().getSemantics());
        Assertions.assertTrue(param0Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., param0Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, param0.getIdentifiers().size());
        final var param0Id = param0.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", param0Id.getName().getSemantics());
        Assertions.assertTrue(param0Id.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8605, ((Integer) param0Id.getId().getSemantics()).intValue());

        Assertions.assertTrue(coordOp.getParameters().get(1) instanceof Parameter);
        final var param1 = (Parameter) coordOp.getParameters().get(1);
        Assertions.assertEquals("Y-axis translation", param1.getName().getSemantics());
        Assertions.assertTrue(param1.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(507.337, param1.getValue().getSemantics().doubleValue());
        final var param1Unit = param1.getUnit();
        Assertions.assertTrue(param1Unit instanceof Unit.Length);
        Assertions.assertEquals("metre", param1Unit.getName().getSemantics());
        Assertions.assertTrue(param1Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., param1Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, param1.getIdentifiers().size());
        final var param1Id = param1.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", param1Id.getName().getSemantics());
        Assertions.assertTrue(param1Id.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8606, ((Integer) param1Id.getId().getSemantics()).intValue());

        Assertions.assertTrue(coordOp.getParameters().get(2) instanceof Parameter);
        final var param2 = (Parameter) coordOp.getParameters().get(2);
        Assertions.assertEquals("Z-axis translation", param2.getName().getSemantics());
        Assertions.assertTrue(param2.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(680.507, param2.getValue().getSemantics().doubleValue());
        final var param2Unit = param2.getUnit();
        Assertions.assertTrue(param2Unit instanceof Unit.Length);
        Assertions.assertEquals("metre", param2Unit.getName().getSemantics());
        Assertions.assertTrue(param2Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., param2Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, param2.getIdentifiers().size());
        final var param2Id = param2.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", param2Id.getName().getSemantics());
        Assertions.assertTrue(param2Id.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8607, ((Integer) param2Id.getId().getSemantics()).intValue());
    }

    @Test
    public void coordinate_operation_test_b_1() throws LanguageException {

        final var text = "COORDINATEOPERATION[\"AGD84 to GDA94 Auslig 5m\","
                + "SOURCECRS["
                + "GEODCRS[\"Tokyo\","
                + "DATUM[\"Tokyo 1918\","
                + "ELLIPSOID[\"Bessel 1841\",6377397.155,299.1528128,LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[Cartesian,3],"
                + "AXIS[\"(X)\",geocentricX,ORDER[1]],"
                + "AXIS[\"(Y)\",geocentricY,ORDER[2]],"
                + "AXIS[\"(Z)\",geocentricZ,ORDER[3]],"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "TARGETCRS["
                + "GEODCRS[\"JGD2000\","
                + "DATUM[\"Japanese Geodetic Datum 2000\","
                + "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101,LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[Cartesian,3],"
                + "AXIS[\"(X)\",geocentricX],"
                + "AXIS[\"(Y)\",geocentricY],"
                + "AXIS[\"(Z)\",geocentricZ],"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "METHOD[\"Geocentric translations\",ID[\"EPSG\",1031]],"
                + "PARAMETER[\"X-axis translation\",-128.5,LENGTHUNIT[\"metre\",1.0]],"
                + "PARAMETER[\"Y-axis translation\",-53.0,LENGTHUNIT[\"metre\",1.0]],"
                + "PARAMETER[\"Z-axis translation\",153.4,LENGTHUNIT[\"metre\",1.0]],"
                + "OPERATIONACCURACY[5],"
                + "USAGE[SCOPE[\"area\"],AREA[\"Australia onshore\"]],"
                + "USAGE[SCOPE[\"bbox\"],BBOX[-43.7,112.85,-9.87,153.68]],"
                + "REMARK[\"Use NTv2 file for better accuracy\"]]";

        final WktParser parser = WktParser.of(text);

        final var coordOp = parser.coordinateOperation();

        Assertions.assertEquals("AGD84 to GDA94 Auslig 5m", coordOp.getName().getSemantics());

        // source

        Assertions.assertTrue(coordOp.getSource().getCrs() instanceof GeodeticCrs);
        final var source = (GeodeticCrs) coordOp.getSource().getCrs();
        Assertions.assertEquals("Tokyo", source.getName().getSemantics());

        final var sourceDatum = source.getDatum();
        Assertions.assertEquals("Tokyo 1918", sourceDatum.getName().getSemantics());

        final var sourceEllipsoid = sourceDatum.getEllipsoid();
        Assertions.assertEquals("Bessel 1841", sourceEllipsoid.getName().getSemantics());
        Assertions.assertTrue(sourceEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6377397.155, sourceEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(sourceEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(299.1528128, sourceEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        final var sourceEllUnit = sourceEllipsoid.getUnit();
        Assertions.assertEquals("metre", sourceEllUnit.getName().getSemantics());
        Assertions.assertTrue(sourceEllUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, sourceEllUnit.getConversionFactor().getSemantics().doubleValue());

        final var sourceCs = source.getCoordinateSystem();
        Assertions.assertEquals(CsType.CARTESIAN, sourceCs.getType().getSemantics());
        Assertions.assertEquals(3, sourceCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(3, sourceCs.getAxis().size());

        final var sAxis0 = sourceCs.getAxis().get(0);
        Assertions.assertEquals("(X)", sAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricX, sAxis0.getDirection().getType().getSemantics());
        Assertions.assertEquals(1, sAxis0.getOrder().getValue().getSemantics().intValue());

        final var sAxis1 = sourceCs.getAxis().get(1);
        Assertions.assertEquals("(Y)", sAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricY, sAxis1.getDirection().getType().getSemantics());
        Assertions.assertEquals(2, sAxis1.getOrder().getValue().getSemantics().intValue());

        final var sAxis2 = sourceCs.getAxis().get(2);
        Assertions.assertEquals("(Z)", sAxis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricZ, sAxis2.getDirection().getType().getSemantics());
        Assertions.assertEquals(3, sAxis2.getOrder().getValue().getSemantics().intValue());

        final var sourceCsUnit = sourceCs.getUnit();
        Assertions.assertTrue(sourceCsUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", sourceCsUnit.getName().getSemantics());
        Assertions.assertTrue(sourceCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, sourceCsUnit.getConversionFactor().getSemantics().doubleValue());

        // target
        Assertions.assertTrue(coordOp.getTarget().getCrs() instanceof GeodeticCrs);
        final var target = (GeodeticCrs) coordOp.getTarget().getCrs();
        Assertions.assertEquals("JGD2000", target.getName().getSemantics());

        final var targetDatum = target.getDatum();
        Assertions.assertEquals("Japanese Geodetic Datum 2000", targetDatum.getName().getSemantics());

        final var targetEllipsoid = targetDatum.getEllipsoid();
        Assertions.assertEquals("GRS 1980", targetEllipsoid.getName().getSemantics());
        Assertions.assertTrue(targetEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6378137.0, targetEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(targetEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257222101, targetEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        final var targetEllUnit = targetEllipsoid.getUnit();
        Assertions.assertEquals("metre", targetEllUnit.getName().getSemantics());
        Assertions.assertTrue(targetEllUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, targetEllUnit.getConversionFactor().getSemantics().doubleValue());

        final var targetCs = target.getCoordinateSystem();
        Assertions.assertEquals(CsType.CARTESIAN, targetCs.getType().getSemantics());
        Assertions.assertEquals(3, targetCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(3, targetCs.getAxis().size());

        final var tAxis0 = targetCs.getAxis().get(0);
        Assertions.assertEquals("(X)", tAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricX, tAxis0.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis0.getOrder());

        final var tAxis1 = targetCs.getAxis().get(1);
        Assertions.assertEquals("(Y)", tAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricY, tAxis1.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis1.getOrder());

        final var tAxis2 = targetCs.getAxis().get(2);
        Assertions.assertEquals("(Z)", tAxis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricZ, tAxis2.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis2.getOrder());

        final var targetCsUnit = targetCs.getUnit();
        Assertions.assertTrue(targetCsUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", targetCsUnit.getName().getSemantics());
        Assertions.assertTrue(targetCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, targetCsUnit.getConversionFactor().getSemantics().doubleValue());

        // operation

        final var method = coordOp.getMethod();
        Assertions.assertEquals("Geocentric translations", method.getName().getSemantics());
        Assertions.assertEquals(1, method.getIdentifiers().size());
        final var id = method.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id.getName().getSemantics());
        Assertions.assertTrue(id.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(1031, ((Integer) id.getId().getSemantics()).intValue());

        Assertions.assertEquals(3, coordOp.getParameters().size());

        Assertions.assertTrue(coordOp.getParameters().get(0) instanceof Parameter);
        final var param0 = (Parameter) coordOp.getParameters().get(0);
        Assertions.assertEquals("X-axis translation", param0.getName().getSemantics());
        Assertions.assertTrue(param0.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(-128.5, param0.getValue().getSemantics().doubleValue());
        final var param0Unit = param0.getUnit();
        Assertions.assertTrue(param0Unit instanceof Unit.Length);
        Assertions.assertEquals("metre", param0Unit.getName().getSemantics());
        Assertions.assertTrue(param0Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., param0Unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertTrue(coordOp.getParameters().get(1) instanceof Parameter);
        final var param1 = (Parameter) coordOp.getParameters().get(1);
        Assertions.assertEquals("Y-axis translation", param1.getName().getSemantics());
        Assertions.assertTrue(param1.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(-53.0, param1.getValue().getSemantics().doubleValue());
        final var param1Unit = param1.getUnit();
        Assertions.assertTrue(param1Unit instanceof Unit.Length);
        Assertions.assertEquals("metre", param1Unit.getName().getSemantics());
        Assertions.assertTrue(param1Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., param1Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param1.getIdentifiers().isEmpty());

        Assertions.assertTrue(coordOp.getParameters().get(2) instanceof Parameter);
        final var param2 = (Parameter) coordOp.getParameters().get(2);
        Assertions.assertEquals("Z-axis translation", param2.getName().getSemantics());
        Assertions.assertTrue(param2.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(153.4, param2.getValue().getSemantics().doubleValue());
        final var param2Unit = param2.getUnit();
        Assertions.assertTrue(param2Unit instanceof Unit.Length);
        Assertions.assertEquals("metre", param2Unit.getName().getSemantics());
        Assertions.assertTrue(param2Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., param2Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param2.getIdentifiers().isEmpty());

        final var acc = coordOp.getAccuracy();
        Assertions.assertTrue(acc.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(5, acc.getValue().getSemantics().intValue());

        Assertions.assertEquals(2, coordOp.getUsages().size());
        Assertions.assertTrue(coordOp.getUsages().get(0).getExtent() instanceof Area);
        final var area = (Area) coordOp.getUsages().get(0).getExtent();
        Assertions.assertEquals("Australia onshore", area.getName().getSemantics());
        Assertions.assertTrue(coordOp.getUsages().get(1).getExtent() instanceof BBox);
        final var bbox = (BBox) coordOp.getUsages().get(1).getExtent();
        Assertions.assertTrue(bbox.getLowerLeftLatitude().getSemantics() instanceof Double);
        Assertions.assertEquals(-43.7, bbox.getLowerLeftLatitude().getSemantics().doubleValue());
        Assertions.assertTrue(bbox.getLowerLeftLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(112.85, bbox.getLowerLeftLongitude().getSemantics().doubleValue());
        Assertions.assertTrue(bbox.getUpperRightLatitude().getSemantics() instanceof Double);
        Assertions.assertEquals(-9.87, bbox.getUpperRightLatitude().getSemantics().doubleValue());
        Assertions.assertTrue(bbox.getUpperRightLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(153.68, bbox.getUpperRightLongitude().getSemantics().doubleValue());

        Assertions.assertEquals("Use NTv2 file for better accuracy", coordOp.getRemark().getText().getSemantics());
    }

    @Test
    public void coordinate_operation_test_c_1() throws LanguageException {

        final var text = "COORDINATEOPERATION[\"NZGD49 to NZGD2000\","
                + "SOURCECRS["
                + "GEODCRS[\"Tokyo\","
                + "DATUM[\"Tokyo 1918\","
                + "ELLIPSOID[\"Bessel 1841\",6377397.155,299.1528128,LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[Cartesian,3],"
                + "AXIS[\"(X)\",geocentricX,ORDER[1]],"
                + "AXIS[\"(Y)\",geocentricY,ORDER[2]],"
                + "AXIS[\"(Z)\",geocentricZ,ORDER[3]],"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "TARGETCRS["
                + "GEODCRS[\"JGD2000\","
                + "DATUM[\"Japanese Geodetic Datum 2000\","
                + "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101,LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[Cartesian,3],"
                + "AXIS[\"(X)\",geocentricX],"
                + "AXIS[\"(Y)\",geocentricY],"
                + "AXIS[\"(Z)\",geocentricZ],"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "METHOD[\"NTv2\",ID[\"EPSG\",9615]],"
                + "PARAMETERFILE[\"Latitude and longitude difference file\",\"nzgd2kgrid0005.gsb\"],"
                + "ID[\"EPSG\",1568,CITATION[\"LINZS25000\"],"
                + "URI[\"http://www.linz.govt.nz/geodetic/software-downloads/\"]],"
                + "REMARK[\"Coordinate transformation accuracy 0.1-1.0m\"]]";

        final WktParser parser = WktParser.of(text);

        final var coordOp = parser.coordinateOperation();

        Assertions.assertEquals("NZGD49 to NZGD2000", coordOp.getName().getSemantics());

        // source

        Assertions.assertTrue(coordOp.getSource().getCrs() instanceof GeodeticCrs);
        final var source = (GeodeticCrs) coordOp.getSource().getCrs();
        Assertions.assertEquals("Tokyo", source.getName().getSemantics());

        final var sourceDatum = source.getDatum();
        Assertions.assertEquals("Tokyo 1918", sourceDatum.getName().getSemantics());

        final var sourceEllipsoid = sourceDatum.getEllipsoid();
        Assertions.assertEquals("Bessel 1841", sourceEllipsoid.getName().getSemantics());
        Assertions.assertTrue(sourceEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6377397.155, sourceEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(sourceEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(299.1528128, sourceEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        final var sourceEllUnit = sourceEllipsoid.getUnit();
        Assertions.assertEquals("metre", sourceEllUnit.getName().getSemantics());
        Assertions.assertTrue(sourceEllUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, sourceEllUnit.getConversionFactor().getSemantics().doubleValue());

        final var sourceCs = source.getCoordinateSystem();
        Assertions.assertEquals(CsType.CARTESIAN, sourceCs.getType().getSemantics());
        Assertions.assertEquals(3, sourceCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(3, sourceCs.getAxis().size());

        final var sAxis0 = sourceCs.getAxis().get(0);
        Assertions.assertEquals("(X)", sAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricX, sAxis0.getDirection().getType().getSemantics());
        Assertions.assertEquals(1, sAxis0.getOrder().getValue().getSemantics().intValue());

        final var sAxis1 = sourceCs.getAxis().get(1);
        Assertions.assertEquals("(Y)", sAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricY, sAxis1.getDirection().getType().getSemantics());
        Assertions.assertEquals(2, sAxis1.getOrder().getValue().getSemantics().intValue());

        final var sAxis2 = sourceCs.getAxis().get(2);
        Assertions.assertEquals("(Z)", sAxis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricZ, sAxis2.getDirection().getType().getSemantics());
        Assertions.assertEquals(3, sAxis2.getOrder().getValue().getSemantics().intValue());

        final var sourceCsUnit = sourceCs.getUnit();
        Assertions.assertTrue(sourceCsUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", sourceCsUnit.getName().getSemantics());
        Assertions.assertTrue(sourceCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, sourceCsUnit.getConversionFactor().getSemantics().doubleValue());

        // target

        Assertions.assertTrue(coordOp.getTarget().getCrs() instanceof GeodeticCrs);
        final var target = (GeodeticCrs) coordOp.getTarget().getCrs();
        Assertions.assertEquals("JGD2000", target.getName().getSemantics());

        final var targetDatum = target.getDatum();
        Assertions.assertEquals("Japanese Geodetic Datum 2000", targetDatum.getName().getSemantics());

        final var targetEllipsoid = targetDatum.getEllipsoid();
        Assertions.assertEquals("GRS 1980", targetEllipsoid.getName().getSemantics());
        Assertions.assertTrue(targetEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6378137.0, targetEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(targetEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257222101, targetEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        final var targetEllUnit = targetEllipsoid.getUnit();
        Assertions.assertEquals("metre", targetEllUnit.getName().getSemantics());
        Assertions.assertTrue(targetEllUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, targetEllUnit.getConversionFactor().getSemantics().doubleValue());

        final var targetCs = target.getCoordinateSystem();
        Assertions.assertEquals(CsType.CARTESIAN, targetCs.getType().getSemantics());
        Assertions.assertEquals(3, targetCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(3, targetCs.getAxis().size());

        final var tAxis0 = targetCs.getAxis().get(0);
        Assertions.assertEquals("(X)", tAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricX, tAxis0.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis0.getOrder());

        final var tAxis1 = targetCs.getAxis().get(1);
        Assertions.assertEquals("(Y)", tAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricY, tAxis1.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis1.getOrder());

        final var tAxis2 = targetCs.getAxis().get(2);
        Assertions.assertEquals("(Z)", tAxis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricZ, tAxis2.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis2.getOrder());

        final var targetCsUnit = targetCs.getUnit();
        Assertions.assertTrue(targetCsUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", targetCsUnit.getName().getSemantics());
        Assertions.assertTrue(targetCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, targetCsUnit.getConversionFactor().getSemantics().doubleValue());

        // operation

        final var method = coordOp.getMethod();
        Assertions.assertEquals("NTv2", method.getName().getSemantics());
        Assertions.assertEquals(1, method.getIdentifiers().size());
        final var id = method.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id.getName().getSemantics());
        Assertions.assertTrue(id.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(9615, ((Integer) id.getId().getSemantics()).intValue());

        Assertions.assertEquals(1, coordOp.getParameters().size());

        Assertions.assertTrue(coordOp.getParameters().get(0) instanceof ParameterFile);
        final var param0 = (ParameterFile) coordOp.getParameters().get(0);
        Assertions.assertEquals("Latitude and longitude difference file", param0.getName().getSemantics());
        Assertions.assertEquals("nzgd2kgrid0005.gsb", param0.getFileName().getSemantics());

        Assertions.assertEquals(1, coordOp.getIdentifiers().size());
        final var coordOpId = coordOp.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", coordOpId.getName().getSemantics());
        Assertions.assertTrue(coordOpId.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(1568, ((Integer) coordOpId.getId().getSemantics()).intValue());
        Assertions.assertEquals("LINZS25000", coordOpId.getCitation().getText().getSemantics());
        Assertions.assertEquals("http://www.linz.govt.nz/geodetic/software-downloads/",
        coordOpId.getUri().getValue().getSemantics());

        Assertions.assertEquals("Coordinate transformation accuracy 0.1-1.0m",
        coordOp.getRemark().getText().getSemantics());
    }

    @Test
    public void coordinate_operation_test_d_1() throws LanguageException {

        final var text = "COORDINATEOPERATION[\"Amersfoort to ETRS89 (3)\","
                + "SOURCECRS["
                + "GEODCRS[\"Tokyo\","
                + "DATUM[\"Tokyo 1918\","
                + "ELLIPSOID[\"Bessel 1841\",6377397.155,299.1528128,LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[Cartesian,3],"
                + "AXIS[\"(X)\",geocentricX,ORDER[1]],"
                + "AXIS[\"(Y)\",geocentricY,ORDER[2]],"
                + "AXIS[\"(Z)\",geocentricZ,ORDER[3]],"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "TARGETCRS["
                + "GEODCRS[\"JGD2000\","
                + "DATUM[\"Japanese Geodetic Datum 2000\","
                + "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101,LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[Cartesian,3],"
                + "AXIS[\"(X)\",geocentricX],"
                + "AXIS[\"(Y)\",geocentricY],"
                + "AXIS[\"(Z)\",geocentricZ],"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "METHOD[\"Coordinate Frame\"],"
                + "PARAMETER[\"X-axis translation\",565.2369,LENGTHUNIT[\"metre\",1.0]],"
                + "PARAMETER[\"Y-axis translation\",50.0087,LENGTHUNIT[\"metre\",1.0]],"
                + "PARAMETER[\"Z-axis translation\",465.658,LENGTHUNIT[\"metre\",1.0]],"
                + "PARAMETER[\"X-axis rotation\",1.9725,ANGLEUNIT[\"microradian\",1E-06]],"
                + "PARAMETER[\"Y-axis rotation\",-1.7004,ANGLEUNIT[\"microradian\",1E-06]],"
                + "PARAMETER[\"Z-axis rotation\",9.0677,ANGLEUNIT[\"microradian\",1E-06]],"
                + "PARAMETER[\"Scale difference\",4.0812,SCALEUNIT[\"parts per million\",1E-06]],"
                + "ID[\"EPSG\",15739]]";

        final WktParser parser = WktParser.of(text);

        final var coordOp = parser.coordinateOperation();

        Assertions.assertEquals("Amersfoort to ETRS89 (3)", coordOp.getName().getSemantics());

        // source

        Assertions.assertTrue(coordOp.getSource().getCrs() instanceof GeodeticCrs);
        final var source = (GeodeticCrs) coordOp.getSource().getCrs();
        Assertions.assertEquals("Tokyo", source.getName().getSemantics());

        final var sourceDatum = source.getDatum();
        Assertions.assertEquals("Tokyo 1918", sourceDatum.getName().getSemantics());

        final var sourceEllipsoid = sourceDatum.getEllipsoid();
        Assertions.assertEquals("Bessel 1841", sourceEllipsoid.getName().getSemantics());
        Assertions.assertTrue(sourceEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6377397.155, sourceEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(sourceEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(299.1528128, sourceEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        final var sourceEllUnit = sourceEllipsoid.getUnit();
        Assertions.assertEquals("metre", sourceEllUnit.getName().getSemantics());
        Assertions.assertTrue(sourceEllUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, sourceEllUnit.getConversionFactor().getSemantics().doubleValue());

        final var sourceCs = source.getCoordinateSystem();
        Assertions.assertEquals(CsType.CARTESIAN, sourceCs.getType().getSemantics());
        Assertions.assertEquals(3, sourceCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(3, sourceCs.getAxis().size());

        final var sAxis0 = sourceCs.getAxis().get(0);
        Assertions.assertEquals("(X)", sAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricX, sAxis0.getDirection().getType().getSemantics());
        Assertions.assertEquals(1, sAxis0.getOrder().getValue().getSemantics().intValue());

        final var sAxis1 = sourceCs.getAxis().get(1);
        Assertions.assertEquals("(Y)", sAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricY, sAxis1.getDirection().getType().getSemantics());
        Assertions.assertEquals(2, sAxis1.getOrder().getValue().getSemantics().intValue());

        final var sAxis2 = sourceCs.getAxis().get(2);
        Assertions.assertEquals("(Z)", sAxis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricZ, sAxis2.getDirection().getType().getSemantics());
        Assertions.assertEquals(3, sAxis2.getOrder().getValue().getSemantics().intValue());

        final var sourceCsUnit = sourceCs.getUnit();
        Assertions.assertTrue(sourceCsUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", sourceCsUnit.getName().getSemantics());
        Assertions.assertTrue(sourceCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, sourceCsUnit.getConversionFactor().getSemantics().doubleValue());

        // target

        Assertions.assertTrue(coordOp.getTarget().getCrs() instanceof GeodeticCrs);
        final var target = (GeodeticCrs) coordOp.getTarget().getCrs();
        Assertions.assertEquals("JGD2000", target.getName().getSemantics());

        final var targetDatum = target.getDatum();
        Assertions.assertEquals("Japanese Geodetic Datum 2000", targetDatum.getName().getSemantics());

        final var targetEllipsoid = targetDatum.getEllipsoid();
        Assertions.assertEquals("GRS 1980", targetEllipsoid.getName().getSemantics());
        Assertions.assertTrue(targetEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6378137.0, targetEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(targetEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257222101, targetEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        final var targetEllUnit = targetEllipsoid.getUnit();
        Assertions.assertEquals("metre", targetEllUnit.getName().getSemantics());
        Assertions.assertTrue(targetEllUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, targetEllUnit.getConversionFactor().getSemantics().doubleValue());

        final var targetCs = target.getCoordinateSystem();
        Assertions.assertEquals(CsType.CARTESIAN, targetCs.getType().getSemantics());
        Assertions.assertEquals(3, targetCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(3, targetCs.getAxis().size());

        final var tAxis0 = targetCs.getAxis().get(0);
        Assertions.assertEquals("(X)", tAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricX, tAxis0.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis0.getOrder());

        final var tAxis1 = targetCs.getAxis().get(1);
        Assertions.assertEquals("(Y)", tAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricY, tAxis1.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis1.getOrder());

        final var tAxis2 = targetCs.getAxis().get(2);
        Assertions.assertEquals("(Z)", tAxis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricZ, tAxis2.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis2.getOrder());

        final var targetCsUnit = targetCs.getUnit();
        Assertions.assertTrue(targetCsUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", targetCsUnit.getName().getSemantics());
        Assertions.assertTrue(targetCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, targetCsUnit.getConversionFactor().getSemantics().doubleValue());

        // operation

        final var method = coordOp.getMethod();
        Assertions.assertEquals("Coordinate Frame", method.getName().getSemantics());
        Assertions.assertTrue(method.getIdentifiers().isEmpty());

        Assertions.assertEquals(7, coordOp.getParameters().size());

        Assertions.assertTrue(coordOp.getParameters().get(0) instanceof Parameter);
        final var param0 = (Parameter) coordOp.getParameters().get(0);
        Assertions.assertEquals("X-axis translation", param0.getName().getSemantics());
        Assertions.assertTrue(param0.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(565.2369, param0.getValue().getSemantics().doubleValue());
        final var param0Unit = param0.getUnit();
        Assertions.assertTrue(param0Unit instanceof Unit.Length);
        Assertions.assertEquals("metre", param0Unit.getName().getSemantics());
        Assertions.assertTrue(param0Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., param0Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param0.getIdentifiers().isEmpty());

        Assertions.assertTrue(coordOp.getParameters().get(1) instanceof Parameter);
        final var param1 = (Parameter) coordOp.getParameters().get(1);
        Assertions.assertEquals("Y-axis translation", param1.getName().getSemantics());
        Assertions.assertTrue(param1.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(50.0087, param1.getValue().getSemantics().doubleValue());
        final var param1Unit = param1.getUnit();
        Assertions.assertTrue(param1Unit instanceof Unit.Length);
        Assertions.assertEquals("metre", param1Unit.getName().getSemantics());
        Assertions.assertTrue(param1Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., param1Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param1.getIdentifiers().isEmpty());

        Assertions.assertTrue(coordOp.getParameters().get(2) instanceof Parameter);
        final var param2 = (Parameter) coordOp.getParameters().get(2);
        Assertions.assertEquals("Z-axis translation", param2.getName().getSemantics());
        Assertions.assertTrue(param2.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(465.658, param2.getValue().getSemantics().doubleValue());
        final var param2Unit = param2.getUnit();
        Assertions.assertTrue(param2Unit instanceof Unit.Length);
        Assertions.assertEquals("metre", param2Unit.getName().getSemantics());
        Assertions.assertTrue(param2Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., param2Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param2.getIdentifiers().isEmpty());

        Assertions.assertTrue(coordOp.getParameters().get(3) instanceof Parameter);
        final var param3 = (Parameter) coordOp.getParameters().get(3);
        Assertions.assertEquals("X-axis rotation", param3.getName().getSemantics());
        Assertions.assertTrue(param3.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(1.9725, param3.getValue().getSemantics().doubleValue());
        final var param3Unit = param3.getUnit();
        Assertions.assertTrue(param3Unit instanceof Unit.Angle);
        Assertions.assertEquals("microradian", param3Unit.getName().getSemantics());
        Assertions.assertTrue(param3Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1e-6, param3Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param3.getIdentifiers().isEmpty());

        Assertions.assertTrue(coordOp.getParameters().get(4) instanceof Parameter);
        final var param4 = (Parameter) coordOp.getParameters().get(4);
        Assertions.assertEquals("Y-axis rotation", param4.getName().getSemantics());
        Assertions.assertTrue(param4.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(-1.7004, param4.getValue().getSemantics().doubleValue());
        final var param4Unit = param4.getUnit();
        Assertions.assertTrue(param4Unit instanceof Unit.Angle);
        Assertions.assertEquals("microradian", param4Unit.getName().getSemantics());
        Assertions.assertTrue(param4Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1e-6, param4Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param4.getIdentifiers().isEmpty());

        Assertions.assertTrue(coordOp.getParameters().get(5) instanceof Parameter);
        final var param5 = (Parameter) coordOp.getParameters().get(5);
        Assertions.assertEquals("Z-axis rotation", param5.getName().getSemantics());
        Assertions.assertTrue(param5.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(9.0677, param5.getValue().getSemantics().doubleValue());
        final var param5Unit = param5.getUnit();
        Assertions.assertTrue(param5Unit instanceof Unit.Angle);
        Assertions.assertEquals("microradian", param5Unit.getName().getSemantics());
        Assertions.assertTrue(param5Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1e-6, param5Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param5.getIdentifiers().isEmpty());

        Assertions.assertTrue(coordOp.getParameters().get(6) instanceof Parameter);
        final var param6 = (Parameter) coordOp.getParameters().get(6);
        Assertions.assertEquals("Scale difference", param6.getName().getSemantics());
        Assertions.assertTrue(param6.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(4.0812, param6.getValue().getSemantics().doubleValue());
        final var param6Unit = param6.getUnit();
        Assertions.assertTrue(param6Unit instanceof Unit.Scale);
        Assertions.assertEquals("parts per million", param6Unit.getName().getSemantics());
        Assertions.assertTrue(param6Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1e-6, param6Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param6.getIdentifiers().isEmpty());

        Assertions.assertEquals(1, coordOp.getIdentifiers().size());
        final var coordOpId = coordOp.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", coordOpId.getName().getSemantics());
        Assertions.assertTrue(coordOpId.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(15739, ((Integer) coordOpId.getId().getSemantics()).intValue());
    }

    @Test
    public void coordinate_operation_test_e_1() throws LanguageException {

        final var text = "COORDINATEOPERATION[\"DHHN92 height to EVRF2007 height\","
                + "SOURCECRS["
                + "GEODCRS[\"Tokyo\","
                + "DATUM[\"Tokyo 1918\","
                + "ELLIPSOID[\"Bessel 1841\",6377397.155,299.1528128,LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[Cartesian,3],"
                + "AXIS[\"(X)\",geocentricX,ORDER[1]],"
                + "AXIS[\"(Y)\",geocentricY,ORDER[2]],"
                + "AXIS[\"(Z)\",geocentricZ,ORDER[3]],"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "TARGETCRS["
                + "GEODCRS[\"JGD2000\","
                + "DATUM[\"Japanese Geodetic Datum 2000\","
                + "ELLIPSOID[\"GRS 1980\",6378137.0,298.257222101,LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[Cartesian,3],"
                + "AXIS[\"(X)\",geocentricX],"
                + "AXIS[\"(Y)\",geocentricY],"
                + "AXIS[\"(Z)\",geocentricZ],"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "METHOD[\"Vertical Offset and Slope\",ID[\"EPSG\",1046]],"
                + "PARAMETER[\"Inclination in latitude\",-0.010,"
                + "ANGLEUNIT[\"arc-second\",4.84813681109535E-06]],"
                + "PARAMETER[\"Inclination in longitude\",0.002,"
                + "ANGLEUNIT[\"arc-second\",4.84813681109535E-06]],"
                + "PARAMETER[\"Vertical offset\",0.015,LENGTHUNIT[\"metre\",1.0]],"
                + "PARAMETER[\"Ordinate 1 of evaluation point\",51.05,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "PARAMETER[\"Ordinate 2 of evaluation point\",10.2166666666667,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "INTERPOLATIONCRS[GEODCRS[\"Tokyo\","
                + "DATUM[\"Tokyo 1918\","
                + "ELLIPSOID[\"Bessel 1841\",6377397.155,299.1528128,LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[Cartesian,3],"
                + "AXIS[\"(X)\",geocentricX,ORDER[1]],"
                + "AXIS[\"(Y)\",geocentricY,ORDER[2]],"
                + "AXIS[\"(Z)\",geocentricZ,ORDER[3]],"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "OPERATIONACCURACY[0.1],"
                + "REMARK[\"Determined at 427 points. RMS residual 0.002m, maximum residual 0.007m\"]]";

        final WktParser parser = WktParser.of(text);

        final var coordOp = parser.coordinateOperation();

        Assertions.assertEquals("DHHN92 height to EVRF2007 height", coordOp.getName().getSemantics());

        // source

        Assertions.assertTrue(coordOp.getSource().getCrs() instanceof GeodeticCrs);
        final var source = (GeodeticCrs) coordOp.getSource().getCrs();
        Assertions.assertEquals("Tokyo", source.getName().getSemantics());

        final var sourceDatum = source.getDatum();
        Assertions.assertEquals("Tokyo 1918", sourceDatum.getName().getSemantics());

        final var sourceEllipsoid = sourceDatum.getEllipsoid();
        Assertions.assertEquals("Bessel 1841", sourceEllipsoid.getName().getSemantics());
        Assertions.assertTrue(sourceEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6377397.155, sourceEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(sourceEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(299.1528128, sourceEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        final var sourceEllUnit = sourceEllipsoid.getUnit();
        Assertions.assertEquals("metre", sourceEllUnit.getName().getSemantics());
        Assertions.assertTrue(sourceEllUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, sourceEllUnit.getConversionFactor().getSemantics().doubleValue());

        final var sourceCs = source.getCoordinateSystem();
        Assertions.assertEquals(CsType.CARTESIAN, sourceCs.getType().getSemantics());
        Assertions.assertEquals(3, sourceCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(3, sourceCs.getAxis().size());

        final var sAxis0 = sourceCs.getAxis().get(0);
        Assertions.assertEquals("(X)", sAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricX, sAxis0.getDirection().getType().getSemantics());
        Assertions.assertEquals(1, sAxis0.getOrder().getValue().getSemantics().intValue());

        final var sAxis1 = sourceCs.getAxis().get(1);
        Assertions.assertEquals("(Y)", sAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricY, sAxis1.getDirection().getType().getSemantics());
        Assertions.assertEquals(2, sAxis1.getOrder().getValue().getSemantics().intValue());

        final var sAxis2 = sourceCs.getAxis().get(2);
        Assertions.assertEquals("(Z)", sAxis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricZ, sAxis2.getDirection().getType().getSemantics());
        Assertions.assertEquals(3, sAxis2.getOrder().getValue().getSemantics().intValue());

        final var sourceCsUnit = sourceCs.getUnit();
        Assertions.assertTrue(sourceCsUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", sourceCsUnit.getName().getSemantics());
        Assertions.assertTrue(sourceCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, sourceCsUnit.getConversionFactor().getSemantics().doubleValue());

        // target

        Assertions.assertTrue(coordOp.getTarget().getCrs() instanceof GeodeticCrs);
        final var target = (GeodeticCrs) coordOp.getTarget().getCrs();
        Assertions.assertEquals("JGD2000", target.getName().getSemantics());

        final var targetDatum = target.getDatum();
        Assertions.assertEquals("Japanese Geodetic Datum 2000", targetDatum.getName().getSemantics());

        final var targetEllipsoid = targetDatum.getEllipsoid();
        Assertions.assertEquals("GRS 1980", targetEllipsoid.getName().getSemantics());
        Assertions.assertTrue(targetEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6378137.0, targetEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(targetEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257222101, targetEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        final var targetEllUnit = targetEllipsoid.getUnit();
        Assertions.assertEquals("metre", targetEllUnit.getName().getSemantics());
        Assertions.assertTrue(targetEllUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, targetEllUnit.getConversionFactor().getSemantics().doubleValue());

        final var targetCs = target.getCoordinateSystem();
        Assertions.assertEquals(CsType.CARTESIAN, targetCs.getType().getSemantics());
        Assertions.assertEquals(3, targetCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(3, targetCs.getAxis().size());

        final var tAxis0 = targetCs.getAxis().get(0);
        Assertions.assertEquals("(X)", tAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricX, tAxis0.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis0.getOrder());

        final var tAxis1 = targetCs.getAxis().get(1);
        Assertions.assertEquals("(Y)", tAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricY, tAxis1.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis1.getOrder());

        final var tAxis2 = targetCs.getAxis().get(2);
        Assertions.assertEquals("(Z)", tAxis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricZ, tAxis2.getDirection().getType().getSemantics());
        Assertions.assertNull(tAxis2.getOrder());

        final var targetCsUnit = targetCs.getUnit();
        Assertions.assertTrue(targetCsUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", targetCsUnit.getName().getSemantics());
        Assertions.assertTrue(targetCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, targetCsUnit.getConversionFactor().getSemantics().doubleValue());

        // operation

        final var method = coordOp.getMethod();
        Assertions.assertEquals("Vertical Offset and Slope", method.getName().getSemantics());
        Assertions.assertEquals(1, method.getIdentifiers().size());
        final var id = method.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id.getName().getSemantics());
        Assertions.assertTrue(id.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(1046, ((Integer) id.getId().getSemantics()).intValue());

        Assertions.assertEquals(5, coordOp.getParameters().size());

        Assertions.assertTrue(coordOp.getParameters().get(0) instanceof Parameter);
        final var param0 = (Parameter) coordOp.getParameters().get(0);
        Assertions.assertEquals("Inclination in latitude", param0.getName().getSemantics());
        Assertions.assertTrue(param0.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(-0.010, param0.getValue().getSemantics().doubleValue());
        final var param0Unit = param0.getUnit();
        Assertions.assertTrue(param0Unit instanceof Unit.Angle);
        Assertions.assertEquals("arc-second", param0Unit.getName().getSemantics());
        Assertions.assertTrue(param0Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(4.84813681109535E-06, param0Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param0.getIdentifiers().isEmpty());

        Assertions.assertTrue(coordOp.getParameters().get(1) instanceof Parameter);
        final var param1 = (Parameter) coordOp.getParameters().get(1);
        Assertions.assertEquals("Inclination in longitude", param1.getName().getSemantics());
        Assertions.assertTrue(param1.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(0.002, param1.getValue().getSemantics().doubleValue());
        final var param1Unit = param1.getUnit();
        Assertions.assertTrue(param1Unit instanceof Unit.Angle);
        Assertions.assertEquals("arc-second", param1Unit.getName().getSemantics());
        Assertions.assertTrue(param1Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(4.84813681109535E-06, param1Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param0.getIdentifiers().isEmpty());

        Assertions.assertTrue(coordOp.getParameters().get(2) instanceof Parameter);
        final var param2 = (Parameter) coordOp.getParameters().get(2);
        Assertions.assertEquals("Vertical offset", param2.getName().getSemantics());
        Assertions.assertTrue(param2.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(0.015, param2.getValue().getSemantics().doubleValue());
        final var param2Unit = param2.getUnit();
        Assertions.assertTrue(param2Unit instanceof Unit.Length);
        Assertions.assertEquals("metre", param2Unit.getName().getSemantics());
        Assertions.assertTrue(param2Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., param2Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param0.getIdentifiers().isEmpty());

        Assertions.assertTrue(coordOp.getParameters().get(3) instanceof Parameter);
        final var param3 = (Parameter) coordOp.getParameters().get(3);
        Assertions.assertEquals("Ordinate 1 of evaluation point", param3.getName().getSemantics());
        Assertions.assertTrue(param3.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(51.05, param3.getValue().getSemantics().doubleValue());
        final var param3Unit = param3.getUnit();
        Assertions.assertTrue(param3Unit instanceof Unit.Angle);
        Assertions.assertEquals("degree", param3Unit.getName().getSemantics());
        Assertions.assertTrue(param3Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, param3Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param0.getIdentifiers().isEmpty());

        Assertions.assertTrue(coordOp.getParameters().get(4) instanceof Parameter);
        final var param4 = (Parameter) coordOp.getParameters().get(4);
        Assertions.assertEquals("Ordinate 2 of evaluation point", param4.getName().getSemantics());
        Assertions.assertTrue(param4.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(10.2166666666667, param4.getValue().getSemantics().doubleValue());
        final var param4Unit = param4.getUnit();
        Assertions.assertTrue(param4Unit instanceof Unit.Angle);
        Assertions.assertEquals("degree", param4Unit.getName().getSemantics());
        Assertions.assertTrue(param4Unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, param4Unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param0.getIdentifiers().isEmpty());

        // interpolation

        Assertions.assertTrue(coordOp.getInterpolation().getCrs() instanceof GeodeticCrs);
        final var inter = (GeodeticCrs) coordOp.getInterpolation().getCrs();
        Assertions.assertEquals("Tokyo", inter.getName().getSemantics());

        final var interDatum = inter.getDatum();
        Assertions.assertEquals("Tokyo 1918", interDatum.getName().getSemantics());

        final var interEllipsoid = interDatum.getEllipsoid();
        Assertions.assertEquals("Bessel 1841", interEllipsoid.getName().getSemantics());
        Assertions.assertTrue(interEllipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6377397.155, interEllipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(interEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(299.1528128, interEllipsoid.getInverseFlattening().getSemantics().doubleValue());
        final var interEllUnit = interEllipsoid.getUnit();
        Assertions.assertEquals("metre", interEllUnit.getName().getSemantics());
        Assertions.assertTrue(interEllUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, interEllUnit.getConversionFactor().getSemantics().doubleValue());

        final var interCs = inter.getCoordinateSystem();
        Assertions.assertEquals(CsType.CARTESIAN, interCs.getType().getSemantics());
        Assertions.assertEquals(3, interCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(3, interCs.getAxis().size());

        final var iAxis0 = interCs.getAxis().get(0);
        Assertions.assertEquals("(X)", iAxis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricX, iAxis0.getDirection().getType().getSemantics());
        Assertions.assertEquals(1, iAxis0.getOrder().getValue().getSemantics().intValue());

        final var iAxis1 = interCs.getAxis().get(1);
        Assertions.assertEquals("(Y)", iAxis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricY, iAxis1.getDirection().getType().getSemantics());
        Assertions.assertEquals(2, iAxis1.getOrder().getValue().getSemantics().intValue());

        final var iAxis2 = interCs.getAxis().get(2);
        Assertions.assertEquals("(Z)", iAxis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricZ, iAxis2.getDirection().getType().getSemantics());
        Assertions.assertEquals(3, iAxis2.getOrder().getValue().getSemantics().intValue());

        final var interCsUnit = interCs.getUnit();
        Assertions.assertTrue(interCsUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", interCsUnit.getName().getSemantics());
        Assertions.assertTrue(interCsUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, interCsUnit.getConversionFactor().getSemantics().doubleValue());

        final var acc = coordOp.getAccuracy();
        Assertions.assertTrue(acc.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(0.1, acc.getValue().getSemantics().doubleValue());

        Assertions.assertTrue(coordOp.getUsages().isEmpty());

        Assertions.assertEquals("Determined at 427 points. RMS residual 0.002m, maximum residual 0.007m",
                coordOp.getRemark().getText().getSemantics());
    }

    @Test
    public void compound_crs_test_a_1() throws LanguageException {

        final var text = "COMPOUNDCRS[\"NAD83 + NAVD88\","
                + "GEODCRS[\"NAD83\","
                + "DATUM[\"North American Datum 1983\","
                + "ELLIPSOID[\"GRS 1980\",6378137,298.257222101,"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "PRIMEMERIDIAN[\"Greenwich\",0],"
                + "CS[ellipsoidal,2],"
                + "AXIS[\"latitude\",north,ORDER[1]],"
                + "AXIS[\"longitude\",east,ORDER[2]],"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "VERTCRS[\"NAVD88\","
                + "VDATUM[\"North American Vertical Datum 1983\"],"
                + "CS[vertical,1],"
                + "AXIS[\"gravity-related height (H)\",up],"
                + "LENGTHUNIT[\"metre\",1]]]";

        final WktParser parser = WktParser.of(text);

        final var compoundCrs = parser.compoundCrs();

        Assertions.assertEquals("NAD83 + NAVD88", compoundCrs.getName().getSemantics());

        Assertions.assertTrue(compoundCrs.getHorizontal() instanceof GeodeticCrs.Geographic2DCrs);
        final var geodetic = (GeodeticCrs.Geographic2DCrs) compoundCrs.getHorizontal();

        Assertions.assertEquals("NAD83", geodetic.getName().getSemantics());

        final var datum = geodetic.getDatum();

        Assertions.assertEquals("North American Datum 1983", datum.getName().getSemantics());

        final var ellipsoid = datum.getEllipsoid();

        Assertions.assertEquals("GRS 1980", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257222101, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        final var ellUnit = ellipsoid.getUnit();

        Assertions.assertEquals("metre", ellUnit.getName().getSemantics());
        Assertions.assertTrue(ellUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., ellUnit.getConversionFactor().getSemantics().intValue());

        final var pm = datum.getPrimeMeridian();
        Assertions.assertEquals("Greenwich", pm.getName().getSemantics());
        Assertions.assertTrue(pm.getIrmLongitude().getSemantics() instanceof Integer);
        Assertions.assertEquals(0, pm.getIrmLongitude().getSemantics().intValue());

        final var cs = geodetic.getCoordinateSystem();
        Assertions.assertTrue(cs instanceof CoordinateSystem.Ellipsoidal2DCoordinateSystem);
        Assertions.assertEquals(CsType.ELLIPSOIDAL, cs.getType().getSemantics());
        Assertions.assertEquals(2, cs.getDimension().getSemantics().intValue());

        Assertions.assertEquals(2, cs.getAxis().size());

        final var axis0 = cs.getAxis().get(0);
        Assertions.assertEquals("latitude", axis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis0.getDirection().getType().getSemantics());
        Assertions.assertEquals(1, axis0.getOrder().getValue().getSemantics().intValue());
        final var axis1 = cs.getAxis().get(1);
        Assertions.assertEquals("longitude", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());
        Assertions.assertEquals(2, axis1.getOrder().getValue().getSemantics().intValue());

        final var csUnit = cs.getUnit();
        Assertions.assertTrue(csUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", csUnit.getName().getSemantics());
        Assertions.assertTrue(csUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, csUnit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertTrue(compoundCrs.getSecondCrs() instanceof SimpleCrsShell.VerticalCrs);

        final var verticalCrs = (SimpleCrsShell.VerticalCrs) compoundCrs.getSecondCrs();
        Assertions.assertEquals("NAVD88", verticalCrs.getName().getSemantics());

        final var verticalDatum = verticalCrs.getDatum();
        Assertions.assertEquals("North American Vertical Datum 1983", verticalDatum.getName().getSemantics());

        final var verticalCs = verticalCrs.getCoordinateSystem();
        Assertions.assertEquals(CsType.VERTICAL, verticalCs.getType().getSemantics());
        Assertions.assertEquals(1, verticalCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(1, verticalCs.getAxis().size());

        final var axisVertical = verticalCs.getAxis().get(0);
        Assertions.assertEquals("gravity-related height (H)", axisVertical.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.up, axisVertical.getDirection().getType().getSemantics());

        final var verticalUnit = verticalCs.getUnit();
        Assertions.assertTrue(verticalUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", verticalUnit.getName().getSemantics());
        Assertions.assertTrue(verticalUnit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(1, verticalUnit.getConversionFactor().getSemantics().intValue());

        Assertions.assertNull(compoundCrs.getThirdCrs());
    }

    @Test
    public void compound_crs_test_b_1() throws LanguageException {

        final var text = "COMPOUNDCRS[\"ICAO layer 0\","
                + "GEODETICCRS[\"WGS 84\","
                + "DATUM[\"World Geodetic System 1984\","
                + "ELLIPSOID[\"WGS 84\",6378137,298.257223563,"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[ellipsoidal,2],"
                + "AXIS[\"latitude\",north,ORDER[1]],"
                + "AXIS[\"longitude\",east,ORDER[2]],"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "PARAMETRICCRS[\"WMO standard atmosphere\","
                + "PARAMETRICDATUM[\"Mean Sea Level\","
                + "ANCHOR[\"Mean Sea Level = 1013.25 hPa\"]],"
                + "CS[parametric,1],"
                + "AXIS[\"pressure (P)\",unspecified],"
                + "PARAMETRICUNIT[\"HectoPascal\",100]]]";

        final WktParser parser = WktParser.of(text);

        final var compoundCrs = parser.compoundCrs();

        Assertions.assertEquals("ICAO layer 0", compoundCrs.getName().getSemantics());

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

        final var ellUnit = ellipsoid.getUnit();

        Assertions.assertEquals("metre", ellUnit.getName().getSemantics());
        Assertions.assertTrue(ellUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., ellUnit.getConversionFactor().getSemantics().intValue());

        Assertions.assertNull(datum.getPrimeMeridian());

        final var cs = geodetic.getCoordinateSystem();
        Assertions.assertTrue(cs instanceof CoordinateSystem.Ellipsoidal2DCoordinateSystem);
        Assertions.assertEquals(CsType.ELLIPSOIDAL, cs.getType().getSemantics());
        Assertions.assertEquals(2, cs.getDimension().getSemantics().intValue());

        Assertions.assertEquals(2, cs.getAxis().size());

        final var axis0 = cs.getAxis().get(0);
        Assertions.assertEquals("latitude", axis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis0.getDirection().getType().getSemantics());
        Assertions.assertEquals(1, axis0.getOrder().getValue().getSemantics().intValue());
        final var axis1 = cs.getAxis().get(1);
        Assertions.assertEquals("longitude", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());
        Assertions.assertEquals(2, axis1.getOrder().getValue().getSemantics().intValue());

        final var csUnit = cs.getUnit();
        Assertions.assertTrue(csUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", csUnit.getName().getSemantics());
        Assertions.assertTrue(csUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, csUnit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertTrue(compoundCrs.getSecondCrs() instanceof SimpleCrsShell.ParametricCrs);

        final var parametricCrs = (SimpleCrsShell.ParametricCrs) compoundCrs.getSecondCrs();
        Assertions.assertEquals("WMO standard atmosphere", parametricCrs.getName().getSemantics());

        final var parametricDatum = parametricCrs.getDatum();
        Assertions.assertEquals("Mean Sea Level", parametricDatum.getName().getSemantics());

        final var anchor = parametricDatum.getAnchor();
        Assertions.assertEquals("Mean Sea Level = 1013.25 hPa", anchor.getDescription().getSemantics());

        final var parametricCs = parametricCrs.getCoordinateSystem();
        Assertions.assertEquals(CsType.PARAMETRIC, parametricCs.getType().getSemantics());
        Assertions.assertEquals(1, parametricCs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(1, parametricCs.getAxis().size());

        final var axisParametric = parametricCs.getAxis().get(0);
        Assertions.assertEquals("pressure (P)", axisParametric.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.unspecified, axisParametric.getDirection().getType().getSemantics());

        final var parametricUnit = parametricCs.getUnit();
        Assertions.assertTrue(parametricUnit instanceof Unit.Parametric);
        Assertions.assertEquals("HectoPascal", parametricUnit.getName().getSemantics());
        Assertions.assertTrue(parametricUnit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(100, parametricUnit.getConversionFactor().getSemantics().intValue());

        Assertions.assertNull(compoundCrs.getThirdCrs());
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
                + "AXIS[\"time (T)\",future,TIMEUNIT[\"day\",86400]]]]";

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

        final var parametricUnit = axisParametric.getUnit();
        Assertions.assertTrue(parametricUnit instanceof Unit.Time);
        Assertions.assertEquals("day", parametricUnit.getName().getSemantics());
        Assertions.assertTrue(parametricUnit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(86400, parametricUnit.getConversionFactor().getSemantics().intValue());

        Assertions.assertNull(compoundCrs.getThirdCrs());
    }

    @Test
    public void derived_engineering_crs_test_a_1() throws LanguageException {

        final var text = "ENGCRS[\"Topocentric example A\","
                + "BASEGEODCRS[\"WGS 84\","
                + "DATUM[\"WGS 84\","
                + "ELLIPSOID[\"WGS 84\",6378137,298.2572236,LENGTHUNIT[\"metre\",1.0]]]],"
                + "DERIVINGCONVERSION[\"Topocentric example A\","
                + "METHOD[\"Geographic/topocentric conversions\",ID[\"EPSG\",9837]],"
                + "PARAMETER[\"Latitude of topocentric origin\",55.0,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "PARAMETER[\"Longitude of topocentric origin\",5.0,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "PARAMETER[\"Ellipsoidal height of topocentric origin\",0.0,"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[Cartesian,3],"
                + "AXIS[\"Topocentric East (U)\",north,ORDER[1]],"
                + "AXIS[\"Topocentric North (V)\",east,ORDER[2]],"
                + "AXIS[\"Topocentric height (W)\",east,ORDER[3]],"
                + "LENGTHUNIT[\"metre\",1.0]]";


        final WktParser parser = WktParser.of(text);

        final var derivedEngineering
                = (DerivedCrs.DerivedEngineeringCrs<BaseGeodeticCrs>) parser.derivedEngineeringCrs();

        Assertions.assertEquals("Topocentric example A",
                derivedEngineering.getName().getSemantics());

        final var baseGeodetic = derivedEngineering.getBaseCrs();

        Assertions.assertEquals("WGS 84", baseGeodetic.getName().getSemantics());

        final var datum = baseGeodetic.getDatum();

        Assertions.assertEquals("WGS 84", datum.getName().getSemantics());

        final var ellipsoid = datum.getEllipsoid();

        Assertions.assertEquals("WGS 84", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.2572236, ellipsoid.getInverseFlattening().getSemantics().doubleValue());
        Assertions.assertTrue(ellipsoid.getIdentifiers().isEmpty());

        final var ellUnit = ellipsoid.getUnit();
        Assertions.assertEquals("metre", ellUnit.getName().getSemantics());
        Assertions.assertTrue(ellUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1, ellUnit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(ellUnit.getIdentifiers().isEmpty());

        final var derivingConvertion = derivedEngineering.getOperation();

        Assertions.assertEquals("Topocentric example A", derivingConvertion.getName().getSemantics());

        final var convMeth = derivingConvertion.getMethod();

        Assertions.assertEquals("Geographic/topocentric conversions", convMeth.getName().getSemantics());
        Assertions.assertEquals(1, convMeth.getIdentifiers().size());
        final var idCm = convMeth.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idCm.getName().getSemantics());
        Assertions.assertTrue(idCm.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(9837, ((Integer) idCm.getId().getSemantics()).intValue());
        Assertions.assertNull(idCm.getCitation());
        Assertions.assertNull(idCm.getUri());
        Assertions.assertNull(idCm.getVersion());

        Assertions.assertEquals(3, derivingConvertion.getParameters().size());

        Assertions.assertTrue(derivingConvertion.getParameters().get(0) instanceof Parameter);
        final var paramC0 = (Parameter) derivingConvertion.getParameters().get(0);
        Assertions.assertEquals("Latitude of topocentric origin", paramC0.getName().getSemantics());
        Assertions.assertTrue(paramC0.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(55., paramC0.getValue().getSemantics().doubleValue());
        final var unitC0 = paramC0.getUnit();
        Assertions.assertTrue(unitC0 instanceof Unit.Angle);
        Assertions.assertEquals("degree", unitC0.getName().getSemantics());
        Assertions.assertTrue(unitC0.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unitC0.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(paramC0.getIdentifiers().isEmpty());


        Assertions.assertTrue(derivingConvertion.getParameters().get(1) instanceof Parameter);
        final var paramC1 = (Parameter) derivingConvertion.getParameters().get(1);
        Assertions.assertEquals("Longitude of topocentric origin", paramC1.getName().getSemantics());
        Assertions.assertTrue(paramC1.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(5., paramC1.getValue().getSemantics().doubleValue());
        final var unitC1 = paramC1.getUnit();
        Assertions.assertTrue(unitC1 instanceof Unit.Angle);
        Assertions.assertEquals("degree", unitC1.getName().getSemantics());
        Assertions.assertTrue(unitC1.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unitC1.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(paramC1.getIdentifiers().isEmpty());

        Assertions.assertTrue(derivingConvertion.getParameters().get(2) instanceof Parameter);
        final var paramC2 = (Parameter) derivingConvertion.getParameters().get(2);
        Assertions.assertEquals("Ellipsoidal height of topocentric origin", paramC2.getName().getSemantics());
        Assertions.assertTrue(paramC2.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(0., paramC2.getValue().getSemantics().doubleValue());
        final var unitC2 = paramC2.getUnit();
        Assertions.assertTrue(unitC2 instanceof Unit.Length);
        Assertions.assertEquals("metre", unitC2.getName().getSemantics());
        Assertions.assertTrue(unitC2.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., unitC2.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(paramC2.getIdentifiers().isEmpty());

        final var cs = derivedEngineering.getCoordinateSystem();
        Assertions.assertEquals(CsType.CARTESIAN, cs.getType().getSemantics());
        Assertions.assertEquals(3, cs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(3, cs.getAxis().size());

        final var axis0 = cs.getAxis().get(0);
        Assertions.assertEquals("Topocentric East (U)", axis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, axis0.getDirection().getType().getSemantics());
        Assertions.assertEquals(1, axis0.getOrder().getValue().getSemantics().intValue());

        final var axis1 = cs.getAxis().get(1);
        Assertions.assertEquals("Topocentric North (V)", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis1.getDirection().getType().getSemantics());
        Assertions.assertEquals(2, axis1.getOrder().getValue().getSemantics().intValue());

        final var axis2 = cs.getAxis().get(2);
        Assertions.assertEquals("Topocentric height (W)", axis2.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, axis2.getDirection().getType().getSemantics());
        Assertions.assertEquals(3, axis2.getOrder().getValue().getSemantics().intValue());

        final var unitCs = cs.getUnit();
        Assertions.assertTrue(unitCs instanceof Unit.Length);
        Assertions.assertEquals("metre", unitCs.getName().getSemantics());
        Assertions.assertTrue(unitCs.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., unitCs.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(unitCs.getIdentifiers().isEmpty());
    }


    @Test
    public void derived_engineering_crs_test_b_1() throws LanguageException {

        final var text = "ENGCRS[\"Gulf of Mexico speculative seismic survey bin grid\","
       + "BASEPROJCRS[\"NAD27 / Texas South Central\","
       + "BASEGEODCRS[\"NAD27\","
       + "DATUM[\"North American Datum 1927\","
       + "ELLIPSOID[\"Clarke 1866\",20925832.164,294.97869821,"
       + "LENGTHUNIT[\"US survey foot\",0.304800609601219]]]],"
       + "CONVERSION[\"Texas South CentralSPCS27\","
       + "METHOD[\"Lambert Conic Conformal (2SP)\",ID[\"EPSG\",9802]],"
       + "PARAMETER[\"Latitude of false origin\",27.83333333333333,"
       + "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8821]],"
       + "PARAMETER[\"Longitude of false origin\",-99.0,"
       + "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8822]],"
       + "PARAMETER[\"Latitude of 1st standard parallel\",28.383333333333,"
       + "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8823]],"
       + "PARAMETER[\"Latitude of 2nd standard parallel\",30.283333333333,"
       + "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8824]],"
       + "PARAMETER[\"Easting at false origin\",2000000.0,"
       + "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8826]],"
       + "PARAMETER[\"Northing at false origin\",0.0,"
       + "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8827]]]],"
       + "DERIVINGCONVERSION[\"Gulf of Mexico speculative survey bin grid\","
       + "METHOD[\"P6 (I = J-90°) seismic bin grid transformation\",ID[\"EPSG\",1049]],"
       + "PARAMETER[\"Bin grid origin I\",5000,SCALEUNIT[\"Bin\",1.0],ID[\"EPSG\",8733]],"
       + "PARAMETER[\"Bin grid origin J\",0,SCALEUNIT[\"Bin\",1.0],ID[\"EPSG\",8734]],"
       + "PARAMETER[\"Bin grid origin Easting\",871200,"
       + "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8735]],"
       + "PARAMETER[\"Bin grid origin Northing\", 10280160,"
       + "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8736]],"
       + "PARAMETER[\"Scale factor of bin grid\",1.0,"
       + "SCALEUNIT[\"Unity\",1.0],ID[\"EPSG\",8737]],"
       + "PARAMETER[\"Bin width on I-axis\",82.5,"
       + "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8738]],"
       + "PARAMETER[\"Bin width on J-axis\",41.25,"
       + "LENGTHUNIT[\"US survey foot\",0.304800609601219],ID[\"EPSG\",8739]],"
       + "PARAMETER[\"Map grid bearing of bin grid J-axis\",340,"
       + "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8740]],"
       + "PARAMETER[\"Bin node increment on I-axis\",1.0,"
       + "SCALEUNIT[\"Bin\",1.0],ID[\"EPSG\",8741]],"
       + "PARAMETER[\"Bin node increment on J-axis\",1.0,"
       + "SCALEUNIT[\"Bin\",1.0],ID[\"EPSG\",8742]]],"
       + "CS[Cartesian,2],"
       + "AXIS[\"(I)\",northNorthWest],"
       + "AXIS[\"(J)\",westSouthWest],"
       + "SCALEUNIT[\"Bin\",1.0]]";


        final WktParser parser = WktParser.of(text);

        final var derivedEngineering
                = (DerivedCrs.DerivedEngineeringCrs<BaseProjectedCrs>) parser.derivedEngineeringCrs();

        Assertions.assertEquals("Gulf of Mexico speculative seismic survey bin grid",
                derivedEngineering.getName().getSemantics());

        final var baseProj = derivedEngineering.getBaseCrs();

        Assertions.assertEquals("NAD27 / Texas South Central", baseProj.getName().getSemantics());

        final var baseGeodetic = baseProj.getBaseGeodeticCrs();

        Assertions.assertEquals("NAD27", baseGeodetic.getName().getSemantics());

        final var datum = baseGeodetic.getDatum();

        Assertions.assertEquals("North American Datum 1927", datum.getName().getSemantics());

        final var ellipsoid = datum.getEllipsoid();

        Assertions.assertEquals("Clarke 1866", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(20925832.164, ellipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(294.97869821, ellipsoid.getInverseFlattening().getSemantics().doubleValue());
        Assertions.assertTrue(ellipsoid.getIdentifiers().isEmpty());

        final var ellUnit = ellipsoid.getUnit();
        Assertions.assertTrue(ellUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.304800609601219, ellUnit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(ellUnit.getIdentifiers().isEmpty());

        Assertions.assertEquals("US survey foot", ellUnit.getName().getSemantics());

        final var projection = baseProj.getProjection();

        Assertions.assertEquals("Texas South CentralSPCS27", projection.getName().getSemantics());

        final var projectionMeth = projection.getMethod();

        Assertions.assertEquals("Lambert Conic Conformal (2SP)", projectionMeth.getName().getSemantics());
        Assertions.assertEquals(1, projectionMeth.getIdentifiers().size());
        final var idPm = projectionMeth.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idPm.getName().getSemantics());
        Assertions.assertTrue(idPm.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(9802, ((Integer) idPm.getId().getSemantics()).intValue());
        Assertions.assertNull(idPm.getCitation());
        Assertions.assertNull(idPm.getUri());
        Assertions.assertNull(idPm.getVersion());

        Assertions.assertEquals(6, projection.getParameters().size());

        final var paramP0 = projection.getParameters().get(0);
        Assertions.assertEquals("Latitude of false origin", paramP0.getName().getSemantics());
        Assertions.assertTrue(paramP0.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(27.83333333333333, paramP0.getValue().getSemantics().doubleValue());
        final var unitP0 = paramP0.getUnit();
        Assertions.assertTrue(unitP0 instanceof Unit.Angle);
        Assertions.assertEquals("degree", unitP0.getName().getSemantics());
        Assertions.assertTrue(unitP0.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unitP0.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramP0.getIdentifiers().size());
        final var idP0 = paramP0.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idP0.getName().getSemantics());
        Assertions.assertTrue(idP0.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8821, ((Integer) idP0.getId().getSemantics()).intValue());
        Assertions.assertNull(idP0.getCitation());
        Assertions.assertNull(idP0.getUri());
        Assertions.assertNull(idP0.getVersion());

        final var paramP1 = projection.getParameters().get(1);
        Assertions.assertEquals("Longitude of false origin", paramP1.getName().getSemantics());
        Assertions.assertTrue(paramP1.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(-99., paramP1.getValue().getSemantics().doubleValue());
        final var unitP1 = paramP1.getUnit();
        Assertions.assertTrue(unitP1 instanceof Unit.Angle);
        Assertions.assertEquals("degree", unitP1.getName().getSemantics());
        Assertions.assertTrue(unitP1.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unitP1.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramP1.getIdentifiers().size());
        final var idP1 = paramP1.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idP1.getName().getSemantics());
        Assertions.assertTrue(idP1.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8822, ((Integer) idP1.getId().getSemantics()).intValue());
        Assertions.assertNull(idP1.getCitation());
        Assertions.assertNull(idP1.getUri());
        Assertions.assertNull(idP1.getVersion());

        final var paramP2 = projection.getParameters().get(2);
        Assertions.assertEquals("Latitude of 1st standard parallel", paramP2.getName().getSemantics());
        Assertions.assertTrue(paramP2.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(28.383333333333, paramP2.getValue().getSemantics().doubleValue());
        final var unitP2 = paramP2.getUnit();
        Assertions.assertTrue(unitP2 instanceof Unit.Angle);
        Assertions.assertEquals("degree", unitP2.getName().getSemantics());
        Assertions.assertTrue(unitP2.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unitP2.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramP2.getIdentifiers().size());
        final var idP2 = paramP2.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idP2.getName().getSemantics());
        Assertions.assertTrue(idP2.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8823, ((Integer) idP2.getId().getSemantics()).intValue());
        Assertions.assertNull(idP2.getCitation());
        Assertions.assertNull(idP2.getUri());
        Assertions.assertNull(idP2.getVersion());

        final var paramP3 = projection.getParameters().get(3);
        Assertions.assertEquals("Latitude of 2nd standard parallel", paramP3.getName().getSemantics());
        Assertions.assertTrue(paramP3.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(30.283333333333, paramP3.getValue().getSemantics().doubleValue());
        final var unitP3 = paramP3.getUnit();
        Assertions.assertTrue(unitP3 instanceof Unit.Angle);
        Assertions.assertEquals("degree", unitP3.getName().getSemantics());
        Assertions.assertTrue(unitP3.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unitP3.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramP3.getIdentifiers().size());
        final var idP3 = paramP3.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idP3.getName().getSemantics());
        Assertions.assertTrue(idP3.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8824, ((Integer) idP3.getId().getSemantics()).intValue());
        Assertions.assertNull(idP3.getCitation());
        Assertions.assertNull(idP3.getUri());
        Assertions.assertNull(idP3.getVersion());

        final var paramP4 = projection.getParameters().get(4);
        Assertions.assertEquals("Easting at false origin", paramP4.getName().getSemantics());
        Assertions.assertTrue(paramP4.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(2000000.0, paramP4.getValue().getSemantics().doubleValue());
        final var unitP4 = paramP4.getUnit();
        Assertions.assertTrue(unitP4 instanceof Unit.Length);
        Assertions.assertEquals("US survey foot", unitP4.getName().getSemantics());
        Assertions.assertTrue(unitP4.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.304800609601219, unitP4.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramP4.getIdentifiers().size());
        final var idP4 = paramP4.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idP4.getName().getSemantics());
        Assertions.assertTrue(idP4.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8826, ((Integer) idP4.getId().getSemantics()).intValue());
        Assertions.assertNull(idP4.getCitation());
        Assertions.assertNull(idP4.getUri());
        Assertions.assertNull(idP4.getVersion());

        final var paramP5 = projection.getParameters().get(5);
        Assertions.assertEquals("Northing at false origin", paramP5.getName().getSemantics());
        Assertions.assertTrue(paramP5.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(.0, paramP5.getValue().getSemantics().doubleValue());
        final var unitP5 = paramP5.getUnit();
        Assertions.assertTrue(unitP5 instanceof Unit.Length);
        Assertions.assertEquals("US survey foot", unitP5.getName().getSemantics());
        Assertions.assertTrue(unitP5.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.304800609601219, unitP5.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramP5.getIdentifiers().size());
        final var idP5 = paramP5.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idP5.getName().getSemantics());
        Assertions.assertTrue(idP5.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8827, ((Integer) idP5.getId().getSemantics()).intValue());
        Assertions.assertNull(idP5.getCitation());
        Assertions.assertNull(idP5.getUri());
        Assertions.assertNull(idP5.getVersion());


        final var derivingConvertion = derivedEngineering.getOperation();

        Assertions.assertEquals("Gulf of Mexico speculative survey bin grid",
         derivingConvertion.getName().getSemantics());

        final var convMeth = derivingConvertion.getMethod();

        Assertions.assertEquals("P6 (I = J-90°) seismic bin grid transformation", convMeth.getName().getSemantics());
        Assertions.assertEquals(1, convMeth.getIdentifiers().size());
        final var idCm = convMeth.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idCm.getName().getSemantics());
        Assertions.assertTrue(idCm.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(1049, ((Integer) idCm.getId().getSemantics()).intValue());
        Assertions.assertNull(idCm.getCitation());
        Assertions.assertNull(idCm.getUri());
        Assertions.assertNull(idCm.getVersion());

        Assertions.assertEquals(10, derivingConvertion.getParameters().size());

        Assertions.assertTrue(derivingConvertion.getParameters().get(0) instanceof Parameter);
        final var paramC0 = (Parameter) derivingConvertion.getParameters().get(0);
        Assertions.assertEquals("Bin grid origin I", paramC0.getName().getSemantics());
        Assertions.assertTrue(paramC0.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(5000, paramC0.getValue().getSemantics().intValue());
        final var unitC0 = paramC0.getUnit();
        Assertions.assertTrue(unitC0 instanceof Unit.Scale);
        Assertions.assertEquals("Bin", unitC0.getName().getSemantics());
        Assertions.assertTrue(unitC0.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., unitC0.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramC0.getIdentifiers().size());
        final var idC0 = paramC0.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idC0.getName().getSemantics());
        Assertions.assertTrue(idC0.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8733, ((Integer) idC0.getId().getSemantics()).intValue());
        Assertions.assertNull(idC0.getCitation());
        Assertions.assertNull(idC0.getUri());
        Assertions.assertNull(idC0.getVersion());

        Assertions.assertTrue(derivingConvertion.getParameters().get(1) instanceof Parameter);
        final var paramC1 = (Parameter) derivingConvertion.getParameters().get(1);
        Assertions.assertEquals("Bin grid origin J", paramC1.getName().getSemantics());
        Assertions.assertTrue(paramC1.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(0, paramC1.getValue().getSemantics().intValue());
        final var unitC1 = paramC1.getUnit();
        Assertions.assertTrue(unitC1 instanceof Unit.Scale);
        Assertions.assertEquals("Bin", unitC1.getName().getSemantics());
        Assertions.assertTrue(unitC1.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., unitC1.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramC1.getIdentifiers().size());
        final var idC1 = paramC1.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idC1.getName().getSemantics());
        Assertions.assertTrue(idC1.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8734, ((Integer) idC1.getId().getSemantics()).intValue());
        Assertions.assertNull(idC1.getCitation());
        Assertions.assertNull(idC1.getUri());
        Assertions.assertNull(idC1.getVersion());

        Assertions.assertTrue(derivingConvertion.getParameters().get(2) instanceof Parameter);
        final var paramC2 = (Parameter) derivingConvertion.getParameters().get(2);
        Assertions.assertEquals("Bin grid origin Easting", paramC2.getName().getSemantics());
        Assertions.assertTrue(paramC2.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(871200, paramC2.getValue().getSemantics().intValue());
        final var unitC2 = paramC2.getUnit();
        Assertions.assertTrue(unitC2 instanceof Unit.Length);
        Assertions.assertEquals("US survey foot", unitC2.getName().getSemantics());
        Assertions.assertTrue(unitC2.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.304800609601219, unitC2.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramC2.getIdentifiers().size());
        final var idC2 = paramC2.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idC2.getName().getSemantics());
        Assertions.assertTrue(idC2.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8735, ((Integer) idC2.getId().getSemantics()).intValue());
        Assertions.assertNull(idC2.getCitation());
        Assertions.assertNull(idC2.getUri());
        Assertions.assertNull(idC2.getVersion());

        Assertions.assertTrue(derivingConvertion.getParameters().get(3) instanceof Parameter);
        final var paramC3 = (Parameter) derivingConvertion.getParameters().get(3);
        Assertions.assertEquals("Bin grid origin Northing", paramC3.getName().getSemantics());
        Assertions.assertTrue(paramC3.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(10280160, paramC3.getValue().getSemantics().intValue());
        final var unitC3 = paramC3.getUnit();
        Assertions.assertTrue(unitC3 instanceof Unit.Length);
        Assertions.assertEquals("US survey foot", unitC3.getName().getSemantics());
        Assertions.assertTrue(unitC3.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.304800609601219, unitC3.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramC3.getIdentifiers().size());
        final var idC3 = paramC3.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idC3.getName().getSemantics());
        Assertions.assertTrue(idC3.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8736, ((Integer) idC3.getId().getSemantics()).intValue());
        Assertions.assertNull(idC3.getCitation());
        Assertions.assertNull(idC3.getUri());
        Assertions.assertNull(idC3.getVersion());

        Assertions.assertTrue(derivingConvertion.getParameters().get(4) instanceof Parameter);
        final var paramC4 = (Parameter) derivingConvertion.getParameters().get(4);
        Assertions.assertEquals("Scale factor of bin grid", paramC4.getName().getSemantics());
        Assertions.assertTrue(paramC4.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(1., paramC4.getValue().getSemantics().doubleValue());
        final var unitC4 = paramC4.getUnit();
        Assertions.assertTrue(unitC4 instanceof Unit.Scale);
        Assertions.assertEquals("Unity", unitC4.getName().getSemantics());
        Assertions.assertTrue(unitC4.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., unitC4.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramC4.getIdentifiers().size());
        final var idC4 = paramC4.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idC4.getName().getSemantics());
        Assertions.assertTrue(idC4.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8737, ((Integer) idC4.getId().getSemantics()).intValue());
        Assertions.assertNull(idC4.getCitation());
        Assertions.assertNull(idC4.getUri());
        Assertions.assertNull(idC4.getVersion());

        Assertions.assertTrue(derivingConvertion.getParameters().get(5) instanceof Parameter);
        final var paramC5 = (Parameter) derivingConvertion.getParameters().get(5);
        Assertions.assertEquals("Bin width on I-axis", paramC5.getName().getSemantics());
        Assertions.assertTrue(paramC5.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(82.5, paramC5.getValue().getSemantics().doubleValue());
        final var unitC5 = paramC5.getUnit();
        Assertions.assertTrue(unitC5 instanceof Unit.Length);
        Assertions.assertEquals("US survey foot", unitC5.getName().getSemantics());
        Assertions.assertTrue(unitC5.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.304800609601219, unitC5.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramC5.getIdentifiers().size());
        final var idC5 = paramC5.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idC5.getName().getSemantics());
        Assertions.assertTrue(idC5.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8738, ((Integer) idC5.getId().getSemantics()).intValue());
        Assertions.assertNull(idC5.getCitation());
        Assertions.assertNull(idC5.getUri());
        Assertions.assertNull(idC5.getVersion());

        Assertions.assertTrue(derivingConvertion.getParameters().get(6) instanceof Parameter);
        final var paramC6 = (Parameter) derivingConvertion.getParameters().get(6);
        Assertions.assertEquals("Bin width on J-axis", paramC6.getName().getSemantics());
        Assertions.assertTrue(paramC6.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(41.25, paramC6.getValue().getSemantics().doubleValue());
        final var unitC6 = paramC6.getUnit();
        Assertions.assertTrue(unitC6 instanceof Unit.Length);
        Assertions.assertEquals("US survey foot", unitC6.getName().getSemantics());
        Assertions.assertTrue(unitC6.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.304800609601219, unitC6.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramC6.getIdentifiers().size());
        final var idC6 = paramC6.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idC6.getName().getSemantics());
        Assertions.assertTrue(idC6.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8739, ((Integer) idC6.getId().getSemantics()).intValue());
        Assertions.assertNull(idC6.getCitation());
        Assertions.assertNull(idC6.getUri());
        Assertions.assertNull(idC6.getVersion());

        Assertions.assertTrue(derivingConvertion.getParameters().get(7) instanceof Parameter);
        final var paramC7 = (Parameter) derivingConvertion.getParameters().get(7);
        Assertions.assertEquals("Map grid bearing of bin grid J-axis", paramC7.getName().getSemantics());
        Assertions.assertTrue(paramC7.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(340, paramC7.getValue().getSemantics().intValue());
        final var unitC7 = paramC7.getUnit();
        Assertions.assertTrue(unitC7 instanceof Unit.Angle);
        Assertions.assertEquals("degree", unitC7.getName().getSemantics());
        Assertions.assertTrue(unitC7.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unitC7.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramC7.getIdentifiers().size());
        final var idC7 = paramC7.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idC7.getName().getSemantics());
        Assertions.assertTrue(idC7.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8740, ((Integer) idC7.getId().getSemantics()).intValue());
        Assertions.assertNull(idC7.getCitation());
        Assertions.assertNull(idC7.getUri());
        Assertions.assertNull(idC7.getVersion());

        Assertions.assertTrue(derivingConvertion.getParameters().get(8) instanceof Parameter);
        final var paramC8 = (Parameter) derivingConvertion.getParameters().get(8);
        Assertions.assertEquals("Bin node increment on I-axis", paramC8.getName().getSemantics());
        Assertions.assertTrue(paramC8.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, paramC8.getValue().getSemantics().doubleValue());
        final var unitC8 = paramC8.getUnit();
        Assertions.assertTrue(unitC8 instanceof Unit.Scale);
        Assertions.assertEquals("Bin", unitC8.getName().getSemantics());
        Assertions.assertTrue(unitC8.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., unitC8.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramC8.getIdentifiers().size());
        final var idC8 = paramC8.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idC8.getName().getSemantics());
        Assertions.assertTrue(idC8.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8741, ((Integer) idC8.getId().getSemantics()).intValue());
        Assertions.assertNull(idC8.getCitation());
        Assertions.assertNull(idC8.getUri());
        Assertions.assertNull(idC8.getVersion());

        Assertions.assertTrue(derivingConvertion.getParameters().get(9) instanceof Parameter);
        final var paramC9 = (Parameter) derivingConvertion.getParameters().get(9);
        Assertions.assertEquals("Bin node increment on J-axis", paramC9.getName().getSemantics());
        Assertions.assertTrue(paramC9.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, paramC9.getValue().getSemantics().doubleValue());
        final var unitC9 = paramC9.getUnit();
        Assertions.assertTrue(unitC9 instanceof Unit.Scale);
        Assertions.assertEquals("Bin", unitC9.getName().getSemantics());
        Assertions.assertTrue(unitC9.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., unitC9.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertEquals(1, paramC9.getIdentifiers().size());
        final var idC9 = paramC9.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", idC9.getName().getSemantics());
        Assertions.assertTrue(idC9.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8742, ((Integer) idC9.getId().getSemantics()).intValue());
        Assertions.assertNull(idC9.getCitation());
        Assertions.assertNull(idC9.getUri());
        Assertions.assertNull(idC9.getVersion());

        final var cs = derivedEngineering.getCoordinateSystem();
        Assertions.assertEquals(CsType.CARTESIAN, cs.getType().getSemantics());
        Assertions.assertEquals(2, cs.getDimension().getSemantics().intValue());
        Assertions.assertEquals(2, cs.getAxis().size());

        final var axis0 = cs.getAxis().get(0);
        Assertions.assertEquals("(I)", axis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.northNorthWest, axis0.getDirection().getType().getSemantics());

        final var axis1 = cs.getAxis().get(1);
        Assertions.assertEquals("(J)", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.westSouthWest, axis1.getDirection().getType().getSemantics());

        final var unitCs = cs.getUnit();
        Assertions.assertTrue(unitCs instanceof Unit.Scale);
        Assertions.assertEquals("Bin", unitCs.getName().getSemantics());
        Assertions.assertTrue(unitCs.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., unitCs.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(unitCs.getIdentifiers().isEmpty());
    }

    @Test
    public void derived_geodetic_crs_test_a_1() throws LanguageException {

        final var text = "GEODCRS[\"ETRS89 Lambert Azimuthal Equal Area CRS\","
                + "BASEGEODCRS[\"WGS 84\","
                + "DATUM[\"WGS 84\","
                + "ELLIPSOID[\"WGS 84\",6378137,298.2572236,LENGTHUNIT[\"metre\",1.0]]]],"
                + "DERIVINGCONVERSION[\"Atlantic pole\","
                + "METHOD[\"Pole rotation\",ID[\"Authority\",1234]],"
                + "PARAMETER[\"Latitude of rotated pole\",52.0,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "PARAMETER[\"Longitude of rotated pole\",-30.0,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "PARAMETER[\"Axis rotation\",-25.0,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]]],"
                + "CS[ellipsoidal,2],"
                + "AXIS[\"latitude\",north,ORDER[1]],"
                + "AXIS[\"longitude\",east,ORDER[2]],"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]]";

        final WktParser parser = WktParser.of(text);

        final var derivedGeodetic = parser.derivedGeodeticCrs();

        Assertions.assertEquals("ETRS89 Lambert Azimuthal Equal Area CRS", derivedGeodetic.getName().getSemantics());

        final var base = derivedGeodetic.getBaseCrs();

        Assertions.assertEquals("WGS 84", base.getName().getSemantics());

        final var baseDatum = base.getDatum();

        Assertions.assertEquals("WGS 84", baseDatum.getName().getSemantics());

        final var baseDEllipsoid = baseDatum.getEllipsoid();

        Assertions.assertEquals("WGS 84", baseDEllipsoid.getName().getSemantics());
        Assertions.assertTrue(baseDEllipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, baseDEllipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(baseDEllipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.2572236, baseDEllipsoid.getInverseFlattening().getSemantics().doubleValue());

        final var ellUnit = baseDEllipsoid.getUnit();
        Assertions.assertEquals("metre", ellUnit.getName().getSemantics());
        Assertions.assertTrue(ellUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., ellUnit.getConversionFactor().getSemantics().doubleValue());

        final var conversion = derivedGeodetic.getOperation();

        Assertions.assertEquals("Atlantic pole", conversion.getName().getSemantics());

        final var method = conversion.getMethod();

        Assertions.assertEquals("Pole rotation", method.getName().getSemantics());
        Assertions.assertEquals(1, method.getIdentifiers().size());

        final var methodId = method.getIdentifiers().get(0);
        Assertions.assertEquals("Authority", methodId.getName().getSemantics());
        Assertions.assertTrue(methodId.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(1234, ((Integer) methodId.getId().getSemantics()).intValue());
        Assertions.assertNull(methodId.getCitation());
        Assertions.assertNull(methodId.getUri());
        Assertions.assertNull(methodId.getVersion());

        Assertions.assertEquals(3, conversion.getParameters().size());

        Assertions.assertTrue(conversion.getParameters().get(0) instanceof Parameter);
        final var param0 = (Parameter) conversion.getParameters().get(0);
        Assertions.assertEquals("Latitude of rotated pole", param0.getName().getSemantics());
        Assertions.assertTrue(param0.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(52.0, param0.getValue().getSemantics().doubleValue());
        Assertions.assertTrue(param0.getIdentifiers().isEmpty());
        final var unit0 = param0.getUnit();
        Assertions.assertEquals("degree", unit0.getName().getSemantics());
        Assertions.assertTrue(unit0.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unit0.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(unit0.getIdentifiers().isEmpty());

        Assertions.assertTrue(conversion.getParameters().get(1) instanceof Parameter);
        final var param1 = (Parameter) conversion.getParameters().get(1);
        Assertions.assertEquals("Longitude of rotated pole", param1.getName().getSemantics());
        Assertions.assertTrue(param1.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(-30., param1.getValue().getSemantics().doubleValue());
        Assertions.assertTrue(param1.getIdentifiers().isEmpty());
        final var unit1 = param1.getUnit();
        Assertions.assertEquals("degree", unit1.getName().getSemantics());
        Assertions.assertTrue(unit1.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unit1.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(unit1.getIdentifiers().isEmpty());

        Assertions.assertTrue(conversion.getParameters().get(2) instanceof Parameter);
        final var param2 = (Parameter) conversion.getParameters().get(2);
        Assertions.assertEquals("Axis rotation", param2.getName().getSemantics());
        Assertions.assertTrue(param2.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(-25., param2.getValue().getSemantics().doubleValue());
        Assertions.assertTrue(param2.getIdentifiers().isEmpty());
        final var unit2 = param2.getUnit();
        Assertions.assertEquals("degree", unit2.getName().getSemantics());
        Assertions.assertTrue(unit2.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unit2.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(unit2.getIdentifiers().isEmpty());


        final var cs = derivedGeodetic.getCoordinateSystem();

        Assertions.assertEquals(CsType.ELLIPSOIDAL, cs.getType().getSemantics());
        Assertions.assertEquals(2, cs.getDimension().getSemantics().intValue());

        Assertions.assertEquals(2, cs.getAxis().size());

        final var x = cs.getAxis().get(0);
        Assertions.assertEquals("latitude", x.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, x.getDirection().getType().getSemantics());
        Assertions.assertNull(x.getDirection().getComplement());
        Assertions.assertTrue(x.getIdentifiers().isEmpty());
        Assertions.assertEquals(1, x.getOrder().getValue().getSemantics().intValue());
        Assertions.assertNull(x.getUnit());

        final var y = cs.getAxis().get(1);
        Assertions.assertEquals("longitude", y.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, y.getDirection().getType().getSemantics());
        Assertions.assertNull(y.getDirection().getComplement());
        Assertions.assertTrue(y.getIdentifiers().isEmpty());
        Assertions.assertEquals(2, y.getOrder().getValue().getSemantics().intValue());
        Assertions.assertNull(y.getUnit());

        Assertions.assertTrue(cs.getIdentifiers().isEmpty());
        final var csUnit = cs.getUnit();
        Assertions.assertEquals("degree", csUnit.getName().getSemantics());
        Assertions.assertTrue(csUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, csUnit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(csUnit.getIdentifiers().isEmpty());
    }

    @Test
    public void deriving_conversion_test_a_1() throws LanguageException {

        final var text = "DERIVINGCONVERSION[\"conversion name\","
                + "METHOD[\"method name\","
                + "ID[\"authority\",123]],"
                + "PARAMETER[\"parameter 1 name\",0,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433],"
                + "ID[\"authority\",456]],"
                + "PARAMETER[\"parameter 2 name\",-123,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433],"
                + "ID[\"authority\",789]]]";

        final WktParser parser = WktParser.of(text);

        final var conversion = parser.derivingConversion();

        Assertions.assertEquals("conversion name", conversion.getName().getSemantics());

        final var method = conversion.getMethod();

        Assertions.assertEquals("method name", method.getName().getSemantics());
        Assertions.assertEquals(1, method.getIdentifiers().size());

        final var methodId = method.getIdentifiers().get(0);
        Assertions.assertEquals("authority", methodId.getName().getSemantics());
        Assertions.assertTrue(methodId.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(123, ((Integer) methodId.getId().getSemantics()).intValue());
        Assertions.assertNull(methodId.getCitation());
        Assertions.assertNull(methodId.getUri());
        Assertions.assertNull(methodId.getVersion());

        Assertions.assertEquals(2, conversion.getParameters().size());

        Assertions.assertTrue(conversion.getParameters().get(0) instanceof Parameter);
        final var param0 = (Parameter) conversion.getParameters().get(0);
        Assertions.assertEquals("parameter 1 name", param0.getName().getSemantics());
        Assertions.assertTrue(param0.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(0, param0.getValue().getSemantics().intValue());
        final var unit0 = param0.getUnit();
        Assertions.assertEquals("degree", unit0.getName().getSemantics());
        Assertions.assertTrue(unit0.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unit0.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(unit0.getIdentifiers().isEmpty());

        Assertions.assertEquals(1, param0.getIdentifiers().size());
        final var param0Id = param0.getIdentifiers().get(0);
        Assertions.assertEquals("authority", param0Id.getName().getSemantics());
        Assertions.assertTrue(param0Id.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(456, ((Integer) param0Id.getId().getSemantics()).intValue());
        Assertions.assertNull(param0Id.getCitation());
        Assertions.assertNull(param0Id.getUri());
        Assertions.assertNull(param0Id.getVersion());

        Assertions.assertTrue(conversion.getParameters().get(1) instanceof Parameter);
        final var param1 = (Parameter) conversion.getParameters().get(1);
        Assertions.assertEquals("parameter 2 name", param1.getName().getSemantics());
        Assertions.assertTrue(param1.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(-123, param1.getValue().getSemantics().intValue());
        final var unit1 = param1.getUnit();
        Assertions.assertEquals("degree", unit1.getName().getSemantics());
        Assertions.assertTrue(unit1.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unit1.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(unit1.getIdentifiers().isEmpty());

        Assertions.assertEquals(1, param1.getIdentifiers().size());
        final var param1Id = param1.getIdentifiers().get(0);
        Assertions.assertEquals("authority", param1Id.getName().getSemantics());
        Assertions.assertTrue(param1Id.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(789, ((Integer) param1Id.getId().getSemantics()).intValue());
        Assertions.assertNull(param1Id.getCitation());
        Assertions.assertNull(param1Id.getUri());
        Assertions.assertNull(param1Id.getVersion());
    }

    @Test
    public void temporal_crs_test_a_1() throws LanguageException {

        final var text = "TIMECRS[\"GPS Time\","
                + "TDATUM[\"Time origin\",TIMEORIGIN[1980-01-01T00:00:00.0Z]],"
                + "CS[temporal,1],AXIS[\"time\",future,TIMEUNIT[\"day\",86400.0]]]";

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

        final var unit = axis0.getUnit();
        Assertions.assertTrue(unit instanceof Unit.Time);
        Assertions.assertEquals("day", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(86400.0, unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertTrue(temporal.getUsages().isEmpty());
    }

    @Test
    public void temporal_datum_test_a_1() throws LanguageException {

        final var text = "TDATUM[\"Time origin\",TIMEORIGIN[1980-01-01T00:00:00.0Z]]";

        final WktParser parser = WktParser.of(text);

        final var datum = parser.temporalDatum();

        Assertions.assertEquals("Time origin", datum.getName().getSemantics());

        Assertions.assertEquals(OffsetDateTime.parse("1980-01-01T00:00:00.0Z"),
                datum.getAnchor().getDescription().getSemantics());
    }

    @Test
    public void temporal_origin_test_a_1() throws LanguageException {

        final var text = "TIMEORIGIN[1980-01-01T00:00:00.0Z]";

        final WktParser parser = WktParser.of(text);

        final var origin = parser.temporalOrigin();

        Assertions.assertEquals(OffsetDateTime.parse("1980-01-01T00:00:00.0Z"), origin.getDescription().getSemantics());
    }

    @Test
    public void parametric_crs_test_a_1() throws LanguageException {

        final var text = "PARAMETRICCRS[\"WMO standard atmosphere layer 0\","
                + "PDATUM[\"Mean Sea Level\",ANCHOR[\"1013.25 hPa at 15°C\"]],"
                + "CS[parametric,1],"
                + "AXIS[\"pressure (hPa)\",up],PARAMETRICUNIT[\"HectoPascal\",100.0]]";

        final WktParser parser = WktParser.of(text);

        final var image = parser.parametricCrs();

        Assertions.assertEquals("WMO standard atmosphere layer 0", image.getName().getSemantics());

        final var datum = image.getDatum();

        Assertions.assertEquals("Mean Sea Level", datum.getName().getSemantics());

        Assertions.assertEquals("1013.25 hPa at 15°C", datum.getAnchor().getDescription().getSemantics());

        final var cs = image.getCoordinateSystem();

        Assertions.assertEquals(CsType.PARAMETRIC, cs.getType().getSemantics());
        Assertions.assertEquals(1, cs.getDimension().getSemantics().intValue());

        Assertions.assertEquals(1, cs.getAxis().size());

        final var axis0 = cs.getAxis().get(0);
        Assertions.assertEquals("pressure (hPa)", axis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.up, axis0.getDirection().getType().getSemantics());
        Assertions.assertNull(axis0.getOrder());

        final var unit = cs.getUnit();
        Assertions.assertTrue(unit instanceof Unit.Parametric);
        Assertions.assertEquals("HectoPascal", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(100., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertTrue(image.getUsages().isEmpty());
    }

    @Test
    public void parametric_datum_test_a_1() throws LanguageException {

        final var text = "PDATUM[\"Mean Sea Level\",ANCHOR[\"1013.25 hPa at 15°C\"]]";

        final WktParser parser = WktParser.of(text);

        final var datum = parser.parametricDatum();

        Assertions.assertEquals("Mean Sea Level", datum.getName().getSemantics());

        Assertions.assertEquals("1013.25 hPa at 15°C", datum.getAnchor().getDescription().getSemantics());
    }

    @Test
    public void image_crs_test_a_1() throws LanguageException {

        final var text = "IMAGECRS[\"A construction site CRS\","
                + "IDATUM[\"P1\",cellCorner,ANCHOR[\"Peg in south corner\"]],"
                + "CS[Cartesian,2],"
                + "AXIS[\"site east\",southWest,ORDER[1]],"
                + "AXIS[\"site north\",southEast,ORDER[2]],"
                + "LENGTHUNIT[\"metre\",1.0],"
                + "USAGE[SCOPE[\"scope\"],TIMEEXTENT[\"date/time t1\",\"date/time t2\"]]]";

        final WktParser parser = WktParser.of(text);

        final var image = parser.imageCrs();

        Assertions.assertEquals("A construction site CRS", image.getName().getSemantics());

        final var datum = image.getDatum();

        Assertions.assertEquals("P1", datum.getName().getSemantics());
        Assertions.assertEquals(PixelInCell.CELL_CORNER, datum.getPixelInCell().getSemantics());

        Assertions.assertEquals("Peg in south corner", datum.getAnchor().getDescription().getSemantics());

        final var cs = image.getCoordinateSystem();

        Assertions.assertEquals(CsType.CARTESIAN, cs.getType().getSemantics());
        Assertions.assertEquals(2, cs.getDimension().getSemantics().intValue());

        Assertions.assertEquals(2, cs.getAxis().size());

        final var axis0 = cs.getAxis().get(0);
        Assertions.assertEquals("site east", axis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.southWest, axis0.getDirection().getType().getSemantics());
        Assertions.assertEquals(1, axis0.getOrder().getValue().getSemantics().intValue());

        final var axis1 = cs.getAxis().get(1);
        Assertions.assertEquals("site north", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.southEast, axis1.getDirection().getType().getSemantics());
        Assertions.assertEquals(2, axis1.getOrder().getValue().getSemantics().intValue());

        final var unit = cs.getUnit();
        Assertions.assertTrue(unit instanceof Unit.Length);
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(1, image.getUsages().size());
        Assertions.assertTrue(image.getUsages().get(0).getExtent() instanceof TemporalExtent);

        final var extent = (TemporalExtent) image.getUsages().get(0).getExtent();
        Assertions.assertEquals("date/time t1", extent.getExtentStart().getSemantics());
        Assertions.assertEquals("date/time t2", extent.getExtentEnd().getSemantics());
    }

    @Test
    public void image_datum_test_a_1() throws LanguageException {

        final var text = "IDATUM[\"P1\",cellCorner,ANCHOR[\"Peg in south corner\"]]";

        final WktParser parser = WktParser.of(text);

        final var datum = parser.imageDatum();

        Assertions.assertEquals("P1", datum.getName().getSemantics());
        Assertions.assertEquals(PixelInCell.CELL_CORNER, datum.getPixelInCell().getSemantics());

        Assertions.assertEquals("Peg in south corner", datum.getAnchor().getDescription().getSemantics());
    }

    @Test
    public void engineering_crs_test_a_1() throws LanguageException {

        final var text = "ENGCRS[\"A construction site CRS\","
                + "EDATUM[\"P1\",ANCHOR[\"Peg in south corner\"]],"
                + "CS[Cartesian,2],"
                + "AXIS[\"site east\",southWest,ORDER[1]],"
                + "AXIS[\"site north\",southEast,ORDER[2]],"
                + "LENGTHUNIT[\"metre\",1.0],"
                + "USAGE[SCOPE[\"scope1\"],TIMEEXTENT[\"date/time t1\", \"date/time t2\"]]]";

        final WktParser parser = WktParser.of(text);

        final var engineering = parser.engineeringCrs();

        Assertions.assertEquals("A construction site CRS", engineering.getName().getSemantics());

        final var datum = engineering.getDatum();

        Assertions.assertEquals("P1", datum.getName().getSemantics());

        Assertions.assertEquals("Peg in south corner", datum.getAnchor().getDescription().getSemantics());

        final var cs = engineering.getCoordinateSystem();

        Assertions.assertEquals(CsType.CARTESIAN, cs.getType().getSemantics());
        Assertions.assertEquals(2, cs.getDimension().getSemantics().intValue());

        Assertions.assertEquals(2, cs.getAxis().size());

        final var axis0 = cs.getAxis().get(0);
        Assertions.assertEquals("site east", axis0.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.southWest, axis0.getDirection().getType().getSemantics());
        Assertions.assertEquals(1, axis0.getOrder().getValue().getSemantics().intValue());

        final var axis1 = cs.getAxis().get(1);
        Assertions.assertEquals("site north", axis1.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.southEast, axis1.getDirection().getType().getSemantics());
        Assertions.assertEquals(2, axis1.getOrder().getValue().getSemantics().intValue());

        final var unit = cs.getUnit();
        Assertions.assertTrue(unit instanceof Unit.Length);
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());

        Assertions.assertEquals(1, engineering.getUsages().size());
        Assertions.assertTrue(engineering.getUsages().get(0).getExtent() instanceof TemporalExtent);

        final var extent = (TemporalExtent) engineering.getUsages().get(0).getExtent();
        Assertions.assertEquals("date/time t1", extent.getExtentStart().getSemantics());
        Assertions.assertEquals("date/time t2", extent.getExtentEnd().getSemantics());
    }

    @Test
    public void engineering_datum_test_a_1() throws LanguageException {

        final var text = "EDATUM[\"P1\",ANCHOR[\"Peg in south corner\"]]";

        final WktParser parser = WktParser.of(text);

        final var datum = parser.engineeringDatum();

        Assertions.assertEquals("P1", datum.getName().getSemantics());

        Assertions.assertEquals("Peg in south corner", datum.getAnchor().getDescription().getSemantics());
    }

    @Test
    public void vertical_crs_test_a_1() throws LanguageException {

        final var text = "VERTCRS[\"NAVD88\","
                + "VDATUM[\"North American Vertical Datum 1988\"],"
                + "CS[vertical,1],"
                + "AXIS[\"gravity-related height (H)\",up],LENGTHUNIT[\"metre\",1.0]]";

        final WktParser parser = WktParser.of(text);

        final var vertical = parser.verticalCrs();

        Assertions.assertEquals("NAVD88", vertical.getName().getSemantics());

        final var datum = vertical.getDatum();

        Assertions.assertEquals("North American Vertical Datum 1988", datum.getName().getSemantics());

        final var cs = vertical.getCoordinateSystem();

        Assertions.assertEquals(CsType.VERTICAL, cs.getType().getSemantics());
        Assertions.assertEquals(1, cs.getDimension().getSemantics().intValue());

        Assertions.assertEquals(1, cs.getAxis().size());

        final var axis = cs.getAxis().get(0);
        Assertions.assertEquals("gravity-related height (H)", axis.getNameAbrev().getSemantics());

        final var unit = cs.getUnit();
        Assertions.assertTrue(unit instanceof Unit.Length);
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., unit.getConversionFactor().getSemantics().doubleValue());
    }

    @Test
    public void vertical_datum_test_a_1() throws LanguageException {

        final var text = "VDATUM[\"North American Vertical Datum 1988\"]";

        final WktParser parser = WktParser.of(text);

        final var datum = parser.verticalDatum();

        Assertions.assertEquals("North American Vertical Datum 1988", datum.getName().getSemantics());
    }

    @Test
    public void projected_crs_test_a_1() throws LanguageException {

        final var text = "PROJCRS[\"ETRS89 Lambert Azimuthal Equal Area CRS\",  BASEGEODCRS[\"ETRS89\","
                + "DATUM[\"ETRS89\","
                + "ELLIPSOID[\"GRS 80\",6378137,298.257222101,LENGTHUNIT[\"metre\",1.0]]]],"
                + "CONVERSION[\"LAEA\","
                + "METHOD[\"Lambert Azimuthal Equal Area\",ID[\"EPSG\",9820]],"
                + "PARAMETER[\"Latitude of origin\",52.0,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "PARAMETER[\"Longitude of origin\",10.0,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "PARAMETER[\"False easting\",4321000.0,LENGTHUNIT[\"metre\",1.0]],"
                + "PARAMETER[\"False northing\",3210000.0,LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[Cartesian,2],"
                + "AXIS[\"(Y)\",north,ORDER[1]],"
                + "AXIS[\"(X)\",east,ORDER[2]],"
                + "LENGTHUNIT[\"metre\",1.0],"
                + "USAGE[SCOPE[\"Description of a purpose\"],"
                + "AREA[\"An area description\"]],"
                + "ID[\"EuroGeographics\",\"ETRS-LAEA\"]]";

        final WktParser parser = WktParser.of(text);

        final var projected = parser.projectedCrs();

        Assertions.assertEquals("ETRS89 Lambert Azimuthal Equal Area CRS", projected.getName().getSemantics());

        final var base = projected.getBaseCrs();

        Assertions.assertEquals("ETRS89", base.getName().getSemantics());

        final var baseDatum = base.getDatum();

        Assertions.assertEquals("ETRS89", baseDatum.getName().getSemantics());

        final var baseDatumEll = baseDatum.getEllipsoid();

        Assertions.assertEquals("GRS 80", baseDatumEll.getName().getSemantics());

        Assertions.assertEquals("Description of a purpose",
                projected.getUsages().get(0).getScope().getDescription().getSemantics());
        Assertions.assertEquals("An area description",
                ((Area) projected.getUsages().get(0).getExtent()).getName().getSemantics());
        Assertions.assertEquals("EuroGeographics", projected.getIdentifiers().get(0).getName().getSemantics());
        Assertions.assertEquals("ETRS-LAEA", projected.getIdentifiers().get(0).getId().getSemantics());
    }

    @Test
    public void map_projection_test_a_1() throws LanguageException {

        final var text = "CONVERSION[\"UTM zone 10N\","
                + "METHOD[\"Transverse Mercator\",ID[\"EPSG\",9807]],"
                + "PARAMETER[\"Latitude of natural origin\",0,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433],"
                + "ID[\"EPSG\",8801]],"
                + "PARAMETER[\"Longitude of natural origin\",-123,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433],ID[\"EPSG\",8802]],"
                + "PARAMETER[\"Scale factor at natural origin\",0.9996,"
                + "SCALEUNIT[\"unity\",1.0],ID[\"EPSG\",8805]],"
                + "PARAMETER[\"False easting\",500000,"
                + "LENGTHUNIT[\"metre\",1.0],ID[\"EPSG\",8806]],"
                + "PARAMETER[\"False northing\",0,LENGTHUNIT[\"metre\",1.0],ID[\"EPSG\",8807]]]";

        final WktParser parser = WktParser.of(text);

        final var projection = parser.mapProjection();

        Assertions.assertTrue(projection.getIdentifiers().isEmpty());

        final var method = projection.getMethod();

        Assertions.assertEquals("Transverse Mercator", method.getName().getSemantics());

        Assertions.assertEquals(1, method.getIdentifiers().size());
        final var methodId = method.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", methodId.getName().getSemantics());
        Assertions.assertTrue(methodId.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(9807, ((Integer) methodId.getId().getSemantics()).intValue());
        Assertions.assertNull(methodId.getVersion());
        Assertions.assertNull(methodId.getCitation());
        Assertions.assertNull(methodId.getUri());

        Assertions.assertEquals(5, projection.getParameters().size());

        final var param0 = projection.getParameters().get(0);
        Assertions.assertEquals("Latitude of natural origin", param0.getName().getSemantics());
        Assertions.assertTrue(param0.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(0, param0.getValue().getSemantics().intValue());
        Assertions.assertTrue(param0.getUnit() instanceof Unit.Angle);
        Assertions.assertEquals("degree", param0.getUnit().getName().getSemantics());
        Assertions.assertTrue(param0.getUnit().getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433,
         param0.getUnit().getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param0.getUnit().getIdentifiers().isEmpty());
        Assertions.assertEquals(1, param0.getIdentifiers().size());
        final var id0 = param0.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id0.getName().getSemantics());
        Assertions.assertTrue(id0.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8801, ((Integer) id0.getId().getSemantics()).intValue());
        Assertions.assertNull(id0.getCitation());
        Assertions.assertNull(id0.getVersion());
        Assertions.assertNull(id0.getUri());

        final var param1 = projection.getParameters().get(1);
        Assertions.assertEquals("Longitude of natural origin", param1.getName().getSemantics());
        Assertions.assertTrue(param1.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(-123, param1.getValue().getSemantics().intValue());
        Assertions.assertTrue(param1.getUnit() instanceof Unit.Angle);
        Assertions.assertEquals("degree", param1.getUnit().getName().getSemantics());
        Assertions.assertTrue(param1.getUnit().getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433,
         param1.getUnit().getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param1.getUnit().getIdentifiers().isEmpty());
        Assertions.assertEquals(1, param1.getIdentifiers().size());
        final var id1 = param1.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id1.getName().getSemantics());
        Assertions.assertTrue(id1.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8802, ((Integer) id1.getId().getSemantics()).intValue());
        Assertions.assertNull(id1.getCitation());
        Assertions.assertNull(id1.getVersion());
        Assertions.assertNull(id1.getUri());

        final var param2 = projection.getParameters().get(2);
        Assertions.assertEquals("Scale factor at natural origin", param2.getName().getSemantics());
        Assertions.assertTrue(param2.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(0.9996, param2.getValue().getSemantics().doubleValue());
        Assertions.assertTrue(param2.getUnit() instanceof Unit.Scale);
        Assertions.assertEquals("unity", param2.getUnit().getName().getSemantics());
        Assertions.assertTrue(param2.getUnit().getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, param2.getUnit().getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param2.getUnit().getIdentifiers().isEmpty());
        Assertions.assertEquals(1, param2.getIdentifiers().size());
        final var id2 = param2.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id2.getName().getSemantics());
        Assertions.assertTrue(id2.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8805, ((Integer) id2.getId().getSemantics()).intValue());
        Assertions.assertNull(id2.getCitation());
        Assertions.assertNull(id2.getVersion());
        Assertions.assertNull(id2.getUri());

        final var param3 = projection.getParameters().get(3);
        Assertions.assertEquals("False easting", param3.getName().getSemantics());
        Assertions.assertTrue(param3.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(500000, param3.getValue().getSemantics().intValue());
        Assertions.assertTrue(param3.getUnit() instanceof Unit.Length);
        Assertions.assertEquals("metre", param3.getUnit().getName().getSemantics());
        Assertions.assertTrue(param3.getUnit().getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, param3.getUnit().getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param3.getUnit().getIdentifiers().isEmpty());
        Assertions.assertEquals(1, param3.getIdentifiers().size());
        final var id3 = param3.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id3.getName().getSemantics());
        Assertions.assertTrue(id3.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8806, ((Integer) id3.getId().getSemantics()).intValue());
        Assertions.assertNull(id3.getCitation());
        Assertions.assertNull(id3.getVersion());
        Assertions.assertNull(id3.getUri());

        final var param4 = projection.getParameters().get(4);
        Assertions.assertEquals("False northing", param4.getName().getSemantics());
        Assertions.assertTrue(param4.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(0, param4.getValue().getSemantics().intValue());
        Assertions.assertTrue(param4.getUnit() instanceof Unit.Length);
        Assertions.assertEquals("metre", param4.getUnit().getName().getSemantics());
        Assertions.assertTrue(param4.getUnit().getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, param4.getUnit().getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param4.getUnit().getIdentifiers().isEmpty());
        Assertions.assertEquals(1, param4.getIdentifiers().size());
        final var id4 = param4.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", id4.getName().getSemantics());
        Assertions.assertTrue(id4.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8807, ((Integer) id4.getId().getSemantics()).intValue());
        Assertions.assertNull(id4.getCitation());
        Assertions.assertNull(id4.getVersion());
        Assertions.assertNull(id4.getUri());
    }

    @Test
    public void map_projection_test_b_1() throws LanguageException {

        final var text = "CONVERSION[\"UTM zone 10N\","
                + "METHOD[\"Transverse Mercator\"],"
                + "PARAMETER[\"Latitude of natural origin\",0,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "PARAMETER[\"Longitude of natural origin\",-123,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "PARAMETER[\"Scale factor at natural origin\",0.9996,"
                + "SCALEUNIT[\"unity\",1.0]],"
                + "PARAMETER[\"False easting\",500000,"
                + "LENGTHUNIT[\"metre\",1.0]],"
                + "PARAMETER[\"False northing\",0,LENGTHUNIT[\"metre\",1.0]],"
                + "ID[\"EPSG\",16010]]";

        final WktParser parser = WktParser.of(text);

        final var projection = parser.mapProjection();

        Assertions.assertEquals(1, projection.getIdentifiers().size());
        final var methodId = projection.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", methodId.getName().getSemantics());
        Assertions.assertTrue(methodId.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(16010, ((Integer) methodId.getId().getSemantics()).intValue());
        Assertions.assertNull(methodId.getVersion());
        Assertions.assertNull(methodId.getCitation());
        Assertions.assertNull(methodId.getUri());

        final var method = projection.getMethod();

        Assertions.assertEquals("Transverse Mercator", method.getName().getSemantics());
        Assertions.assertTrue(method.getIdentifiers().isEmpty());

        Assertions.assertEquals(5, projection.getParameters().size());

        final var param0 = projection.getParameters().get(0);
        Assertions.assertEquals("Latitude of natural origin", param0.getName().getSemantics());
        Assertions.assertTrue(param0.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(0, param0.getValue().getSemantics().intValue());
        Assertions.assertTrue(param0.getUnit() instanceof Unit.Angle);
        Assertions.assertEquals("degree", param0.getUnit().getName().getSemantics());
        Assertions.assertTrue(param0.getUnit().getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433,
         param0.getUnit().getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param0.getUnit().getIdentifiers().isEmpty());
        Assertions.assertTrue(param0.getIdentifiers().isEmpty());

        final var param1 = projection.getParameters().get(1);
        Assertions.assertEquals("Longitude of natural origin", param1.getName().getSemantics());
        Assertions.assertTrue(param1.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(-123, param1.getValue().getSemantics().intValue());
        Assertions.assertTrue(param1.getUnit() instanceof Unit.Angle);
        Assertions.assertEquals("degree", param1.getUnit().getName().getSemantics());
        Assertions.assertTrue(param1.getUnit().getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433,
        param1.getUnit().getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param1.getUnit().getIdentifiers().isEmpty());
        Assertions.assertTrue(param1.getIdentifiers().isEmpty());

        final var param2 = projection.getParameters().get(2);
        Assertions.assertEquals("Scale factor at natural origin", param2.getName().getSemantics());
        Assertions.assertTrue(param2.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(0.9996, param2.getValue().getSemantics().doubleValue());
        Assertions.assertTrue(param2.getUnit() instanceof Unit.Scale);
        Assertions.assertEquals("unity", param2.getUnit().getName().getSemantics());
        Assertions.assertTrue(param2.getUnit().getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, param2.getUnit().getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param2.getUnit().getIdentifiers().isEmpty());
        Assertions.assertTrue(param2.getIdentifiers().isEmpty());

        final var param3 = projection.getParameters().get(3);
        Assertions.assertEquals("False easting", param3.getName().getSemantics());
        Assertions.assertTrue(param3.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(500000, param3.getValue().getSemantics().intValue());
        Assertions.assertTrue(param3.getUnit() instanceof Unit.Length);
        Assertions.assertEquals("metre", param3.getUnit().getName().getSemantics());
        Assertions.assertTrue(param3.getUnit().getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, param3.getUnit().getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param3.getUnit().getIdentifiers().isEmpty());
        Assertions.assertTrue(param3.getIdentifiers().isEmpty());

        final var param4 = projection.getParameters().get(4);
        Assertions.assertEquals("False northing", param4.getName().getSemantics());
        Assertions.assertTrue(param4.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(0, param4.getValue().getSemantics().intValue());
        Assertions.assertTrue(param4.getUnit() instanceof Unit.Length);
        Assertions.assertEquals("metre", param4.getUnit().getName().getSemantics());
        Assertions.assertTrue(param4.getUnit().getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, param4.getUnit().getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(param4.getUnit().getIdentifiers().isEmpty());
        Assertions.assertTrue(param4.getIdentifiers().isEmpty());
    }

    @Test
    public void map_projection_method_test_a_1() throws LanguageException {

        final var text = "METHOD[\"Transverse Mercator\",ID[\"EPSG\",9807]]";

        final WktParser parser = WktParser.of(text);

        final var method = parser.mapProjectionMethod();

        Assertions.assertEquals("Transverse Mercator", method.getName().getSemantics());

        Assertions.assertEquals(1, method.getIdentifiers().size());
        final var identifier = method.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", identifier.getName().getSemantics());
        Assertions.assertTrue(identifier.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(9807, ((Integer) identifier.getId().getSemantics()).intValue());
        Assertions.assertNull(identifier.getVersion());
        Assertions.assertNull(identifier.getCitation());
        Assertions.assertNull(identifier.getUri());
    }

    @Test
    public void map_projection_method_test_b_1() throws LanguageException {

        final var text = "METHOD[\"Transverse Mercator\"]";

        final WktParser parser = WktParser.of(text);

        final var method = parser.mapProjectionMethod();

        Assertions.assertEquals("Transverse Mercator", method.getName().getSemantics());

        Assertions.assertTrue(method.getIdentifiers().isEmpty());
    }

    @Test
    public void projection_parameter_test_a_1() throws LanguageException {

        final var text = "PARAMETER[\"Latitude of natural origin\",0,"
                + "ANGLEUNIT[\"degree\",0.0174532925199433],"
                + "ID[\"EPSG\",8801]]";

        final WktParser parser = WktParser.of(text);

        final var parameter = parser.projectionParameter();

        Assertions.assertEquals("Latitude of natural origin", parameter.getName().getSemantics());
        Assertions.assertTrue(parameter.getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(0, parameter.getValue().getSemantics().intValue());

        final var unit = parameter.getUnit();
        Assertions.assertTrue(unit instanceof Unit.Angle);
        Assertions.assertEquals("degree", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());


        Assertions.assertEquals(1, parameter.getIdentifiers().size());
        final var identifier = parameter.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", identifier.getName().getSemantics());
        Assertions.assertTrue(identifier.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(8801, ((Integer) identifier.getId().getSemantics()).intValue());
        Assertions.assertNull(identifier.getVersion());
        Assertions.assertNull(identifier.getCitation());
        Assertions.assertNull(identifier.getUri());
    }

    @Test
    public void projection_parameter_test_b_1() throws LanguageException {

        final var text = "PARAMETER[\"Scale factor at natural origin\",0.9996,"
                + "SCALEUNIT[\"unity\",1.0]]";

        final WktParser parser = WktParser.of(text);

        final var parameter = parser.projectionParameter();

        Assertions.assertEquals("Scale factor at natural origin", parameter.getName().getSemantics());
        Assertions.assertTrue(parameter.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(0.9996, parameter.getValue().getSemantics().doubleValue());

        final var unit = parameter.getUnit();
        Assertions.assertTrue(unit instanceof Unit.Scale);
        Assertions.assertEquals("unity", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, unit.getConversionFactor().getSemantics().doubleValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());


        Assertions.assertTrue(parameter.getIdentifiers().isEmpty());
    }

    @Test
    public void geodetic_crs_test_a_1() throws LanguageException {

        final var text = "GEODETICCRS[\"JGD2000\","
               + "DATUM[\"Japanese Geodetic Datum 2000\","
               + "ELLIPSOID[\"GRS 1980\",6378137,298.257222101]],"
               + "PRIMEM[\"Paris\",2.5969213],"
               + "CS[Cartesian,3],"
               + "AXIS[\"(X)\",geocentricX],"
               + "AXIS[\"(Y)\",geocentricY],"
               + "AXIS[\"(Z)\",geocentricZ],"
               + "LENGTHUNIT[\"metre\",1.0],"
               + "USAGE[SCOPE[\"Geodesy, topographic mapping and cadastre\"],"
               + "AREA[\"Japan\"],"
               + "BBOX[17.09,122.38,46.05,157.64],"
               + "TIMEEXTENT[2002-04-01,2011-10-21]],"
               + "ID[\"EPSG\",4946,URI[\"urn:ogc:def:crs:EPSG::4946\"]],"
               + "REMARK[\"注：JGD2000ジオセントリックは現在JGD2011に代わりました。\"]]";

        final WktParser parser = WktParser.of(text);

        final var crs = parser.geodeticCrs();

        Assertions.assertEquals("JGD2000", crs.getName().getSemantics());

        final var datum = crs.getDatum();

        Assertions.assertEquals("Japanese Geodetic Datum 2000", datum.getName().getSemantics());
        Assertions.assertNull(datum.getAnchor());
        Assertions.assertTrue(datum.getIdentifiers().isEmpty());

        final var primeMeridian = datum.getPrimeMeridian();
        Assertions.assertEquals("Paris", primeMeridian.getName().getSemantics());
        Assertions.assertTrue(primeMeridian.getIdentifiers().isEmpty());
        Assertions.assertNull(primeMeridian.getUnit());
        Assertions.assertTrue(primeMeridian.getIrmLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(2.5969213, primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        final var ellipsoid = datum.getEllipsoid();

        Assertions.assertEquals("GRS 1980", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257222101, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertNull(ellipsoid.getUnit());

        final var cs = crs.getCoordinateSystem();

        Assertions.assertEquals(CsType.CARTESIAN, cs.getType().getSemantics());
        Assertions.assertEquals(3, cs.getDimension().getSemantics().intValue());

        Assertions.assertTrue(cs.getIdentifiers().isEmpty());

        Assertions.assertEquals(3, cs.getAxis().size());

        final var x = cs.getAxis().get(0);
        Assertions.assertEquals("(X)", x.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricX, x.getDirection().getType().getSemantics());
        Assertions.assertNull(x.getDirection().getComplement());
        Assertions.assertTrue(x.getIdentifiers().isEmpty());
        Assertions.assertNull(x.getOrder());
        Assertions.assertNull(x.getUnit());

        final var y = cs.getAxis().get(1);
        Assertions.assertEquals("(Y)", y.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricY, y.getDirection().getType().getSemantics());
        Assertions.assertNull(y.getDirection().getComplement());
        Assertions.assertTrue(y.getIdentifiers().isEmpty());
        Assertions.assertNull(y.getOrder());
        Assertions.assertNull(y.getUnit());

        final var z = cs.getAxis().get(2);
        Assertions.assertEquals("(Z)", z.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricZ, z.getDirection().getType().getSemantics());
        Assertions.assertNull(z.getDirection().getComplement());
        Assertions.assertTrue(z.getIdentifiers().isEmpty());
        Assertions.assertNull(z.getOrder());
        Assertions.assertNull(z.getUnit());

        final var csUnit = cs.getUnit();
        Assertions.assertTrue(csUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", csUnit.getName().getSemantics());
        Assertions.assertTrue(csUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1., ((Double) csUnit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(csUnit.getIdentifiers().isEmpty());

        Assertions.assertEquals("Geodesy, topographic mapping and cadastre",
                crs.getUsages().get(0).getScope().getDescription().getSemantics());

        Assertions.assertEquals(1, crs.getUsages().size());
        Assertions.assertTrue(crs.getUsages().get(0).getExtent() instanceof Extent.Coumpound);

        final Area area = ((Extent.Coumpound) crs.getUsages().get(0).getExtent()).getArea();
        Assertions.assertEquals("Japan", area.getName().getSemantics());

        final BBox bbox = ((Extent.Coumpound) crs.getUsages().get(0).getExtent()).getBbox();
        Assertions.assertTrue(bbox.getLowerLeftLatitude().getSemantics() instanceof Double);
        Assertions.assertTrue(bbox.getLowerLeftLongitude().getSemantics() instanceof Double);
        Assertions.assertTrue(bbox.getUpperRightLatitude().getSemantics() instanceof Double);
        Assertions.assertTrue(bbox.getUpperRightLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(17.09, bbox.getLowerLeftLatitude().getSemantics().doubleValue());
        Assertions.assertEquals(122.38, bbox.getLowerLeftLongitude().getSemantics().doubleValue());
        Assertions.assertEquals(46.05, bbox.getUpperRightLatitude().getSemantics().doubleValue());
        Assertions.assertEquals(157.64, bbox.getUpperRightLongitude().getSemantics().doubleValue());

        final TemporalExtent timeExtent = ((Extent.Coumpound) crs.getUsages().get(0).getExtent()).getTemporal();
        Assertions.assertTrue(timeExtent.getExtentStart().getSemantics() instanceof LocalDate);
        Assertions.assertTrue(timeExtent.getExtentEnd().getSemantics() instanceof LocalDate);
        Assertions.assertEquals(LocalDate.of(2002, Month.APRIL, 1), timeExtent.getExtentStart().getSemantics());
        Assertions.assertEquals(LocalDate.of(2011, Month.OCTOBER, 21), timeExtent.getExtentEnd().getSemantics());

        Assertions.assertEquals(1, crs.getIdentifiers().size());
        final var identifier = crs.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", identifier.getName().getSemantics());
        Assertions.assertTrue(identifier.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(4946, ((Integer) identifier.getId().getSemantics()).intValue());
        Assertions.assertNull(identifier.getVersion());
        Assertions.assertNull(identifier.getCitation());
        Assertions.assertEquals("urn:ogc:def:crs:EPSG::4946", identifier.getUri().getValue().getSemantics());

        final var remark = crs.getRemark();

        Assertions.assertEquals("注：JGD2000ジオセントリックは現在JGD2011に代わりました。", remark.getText().getSemantics());
    }

    @Test
    public void geodetic_crs_test_b_1() throws LanguageException {

        final var text = "GEODCRS[\"WGS 84\","
                + "DATUM[\"World Geodetic System 1984\","
                + "ELLIPSOID[\"WGS 84\",6378137,298.257223563,"
                + "LENGTHUNIT[\"metre\",1.0]]],"
                + "CS[ellipsoidal,3],"
                + "AXIS[\"(lat)\",north,ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "AXIS[\"(lon)\",east,ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "AXIS[\"ellipsoidal height (h)\",up,LENGTHUNIT[\"metre\",1.0]]]";

        final WktParser parser = WktParser.of(text);

        final var crs = parser.geodeticCrs();

        Assertions.assertEquals("WGS 84", crs.getName().getSemantics());

        final var datum = crs.getDatum();

        Assertions.assertEquals("World Geodetic System 1984", datum.getName().getSemantics());
        Assertions.assertNull(datum.getAnchor());
        Assertions.assertNull(datum.getPrimeMeridian());
        Assertions.assertTrue(datum.getIdentifiers().isEmpty());

        final var ellipsoid = datum.getEllipsoid();

        Assertions.assertEquals("WGS 84", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257223563, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        final var unit = ellipsoid.getUnit();
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, unit.getConversionFactor().getSemantics().doubleValue());

        final var cs = crs.getCoordinateSystem();

        Assertions.assertEquals(CsType.ELLIPSOIDAL, cs.getType().getSemantics());
        Assertions.assertEquals(3, cs.getDimension().getSemantics().intValue());

        Assertions.assertTrue(cs.getIdentifiers().isEmpty());

        Assertions.assertEquals(3, cs.getAxis().size());

        final var x = cs.getAxis().get(0);
        Assertions.assertEquals("(lat)", x.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, x.getDirection().getType().getSemantics());
        Assertions.assertNull(x.getDirection().getComplement());
        Assertions.assertTrue(x.getIdentifiers().isEmpty());
        Assertions.assertNull(x.getOrder());
        final var xUnit = x.getUnit();
        Assertions.assertTrue(xUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", xUnit.getName().getSemantics());
        Assertions.assertTrue(xUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433,
        ((Double) xUnit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(xUnit.getIdentifiers().isEmpty());

        final var y = cs.getAxis().get(1);
        Assertions.assertEquals("(lon)", y.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, y.getDirection().getType().getSemantics());
        Assertions.assertNull(y.getDirection().getComplement());
        Assertions.assertTrue(y.getIdentifiers().isEmpty());
        Assertions.assertNull(y.getOrder());
        final var yUnit = y.getUnit();
        Assertions.assertTrue(yUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", yUnit.getName().getSemantics());
        Assertions.assertTrue(yUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433,
        ((Double) yUnit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(yUnit.getIdentifiers().isEmpty());

        final var z = cs.getAxis().get(2);
        Assertions.assertEquals("ellipsoidal height (h)", z.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.up, z.getDirection().getType().getSemantics());
        Assertions.assertNull(z.getDirection().getComplement());
        Assertions.assertTrue(z.getIdentifiers().isEmpty());
        Assertions.assertNull(z.getOrder());
        final var zUnit = z.getUnit();
        Assertions.assertTrue(zUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", zUnit.getName().getSemantics());
        Assertions.assertTrue(zUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, ((Double) zUnit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(zUnit.getIdentifiers().isEmpty());

        Assertions.assertNull(cs.getUnit());

        Assertions.assertTrue(crs.getUsages().isEmpty());
        Assertions.assertTrue(crs.getIdentifiers().isEmpty());
        Assertions.assertNull(crs.getRemark());
    }

    @Test
    public void geodetic_crs_test_c_1() throws LanguageException {

        final var text = "GEODCRS[\"NAD83\","
            + "DATUM[\"North American Datum 1983\","
            + "ELLIPSOID[\"GRS 1980\",6378137,298.257222101,LENGTHUNIT[\"metre\",1.0]]],"
            + "CS[ellipsoidal,2],"
            + "AXIS[\"latitude\",north],"
            + "AXIS[\"longitude\",east],"
            + "ANGLEUNIT[\"degree\",0.017453292519943],"
            + "ID[\"EPSG\",4269],"
            + "REMARK[\"1986 realisation\"]]";

        final WktParser parser = WktParser.of(text);

        final var crs = parser.geodeticCrs();

        Assertions.assertEquals("NAD83", crs.getName().getSemantics());

        final var datum = crs.getDatum();

        Assertions.assertEquals("North American Datum 1983", datum.getName().getSemantics());
        Assertions.assertNull(datum.getAnchor());
        Assertions.assertNull(datum.getPrimeMeridian());
        Assertions.assertTrue(datum.getIdentifiers().isEmpty());

        final var ellipsoid = datum.getEllipsoid();

        Assertions.assertEquals("GRS 1980", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257222101, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        final var ellipsoidUnit = ellipsoid.getUnit();
        Assertions.assertEquals("metre", ellipsoidUnit.getName().getSemantics());
        Assertions.assertTrue(ellipsoidUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, ellipsoidUnit.getConversionFactor().getSemantics().doubleValue());

        final var cs = crs.getCoordinateSystem();

        Assertions.assertEquals(CsType.ELLIPSOIDAL, cs.getType().getSemantics());
        Assertions.assertEquals(2, cs.getDimension().getSemantics().intValue());

        Assertions.assertTrue(cs.getIdentifiers().isEmpty());

        Assertions.assertEquals(2, cs.getAxis().size());

        final var x = cs.getAxis().get(0);
        Assertions.assertEquals("latitude", x.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, x.getDirection().getType().getSemantics());
        Assertions.assertNull(x.getDirection().getComplement());
        Assertions.assertTrue(x.getIdentifiers().isEmpty());
        Assertions.assertNull(x.getOrder());
        Assertions.assertNull(x.getUnit());

        final var y = cs.getAxis().get(1);
        Assertions.assertEquals("longitude", y.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, y.getDirection().getType().getSemantics());
        Assertions.assertNull(y.getDirection().getComplement());
        Assertions.assertTrue(y.getIdentifiers().isEmpty());
        Assertions.assertNull(y.getOrder());
        Assertions.assertNull(y.getUnit());

        final var csUnit = cs.getUnit();
        Assertions.assertTrue(csUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", csUnit.getName().getSemantics());
        Assertions.assertTrue(csUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.017453292519943,
        ((Double) csUnit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(csUnit.getIdentifiers().isEmpty());

        Assertions.assertTrue(crs.getUsages().isEmpty());
        Assertions.assertEquals(1, crs.getIdentifiers().size());

        final var crsId = crs.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", crsId.getName().getSemantics());
        Assertions.assertTrue(crsId.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(4269, ((Integer) crsId.getId().getSemantics()).intValue());
        Assertions.assertNull(crsId.getUri());
        Assertions.assertNull(crsId.getVersion());
        Assertions.assertNull(crsId.getCitation());

        final var remark = crs.getRemark();

        Assertions.assertEquals("1986 realisation", remark.getText().getSemantics());
    }

    @Test
    public void geodetic_crs_test_d_1() throws LanguageException {

        final var text = "GEODCRS[\"NTF (Paris)\","
                + "DATUM[\"Nouvelle Triangulation Francaise\","
                + "ELLIPSOID[\"Clarke 1880 (IGN)\",6378249.2,293.4660213]],"
                + "PRIMEM[\"Paris\",2.5969213],"
                + "CS[ellipsoidal,2],"
                + "AXIS[\"latitude\",north,ORDER[1]],"
                + "AXIS[\"longitude\",east,ORDER[2]],"
                + "ANGLEUNIT[\"grad\",0.015707963267949],"
                + "REMARK[\"Nouvelle Triangulation Française\"]]";

        final WktParser parser = WktParser.of(text);

        final var crs = parser.geodeticCrs();

        Assertions.assertEquals("NTF (Paris)", crs.getName().getSemantics());

        final var datum = crs.getDatum();

        Assertions.assertEquals("Nouvelle Triangulation Francaise", datum.getName().getSemantics());
        Assertions.assertNull(datum.getAnchor());
        Assertions.assertTrue(datum.getIdentifiers().isEmpty());

        final var primeMeridian = datum.getPrimeMeridian();
        Assertions.assertEquals("Paris", primeMeridian.getName().getSemantics());
        Assertions.assertTrue(primeMeridian.getIdentifiers().isEmpty());
        Assertions.assertNull(primeMeridian.getUnit());
        Assertions.assertTrue(primeMeridian.getIrmLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(2.5969213, primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        final var ellipsoid = datum.getEllipsoid();

        Assertions.assertEquals("Clarke 1880 (IGN)", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6378249.2, ellipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(293.4660213, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertNull(ellipsoid.getUnit());

        final var cs = crs.getCoordinateSystem();

        Assertions.assertEquals(CsType.ELLIPSOIDAL, cs.getType().getSemantics());
        Assertions.assertEquals(2, cs.getDimension().getSemantics().intValue());

        Assertions.assertTrue(cs.getIdentifiers().isEmpty());

        Assertions.assertEquals(2, cs.getAxis().size());

        final var x = cs.getAxis().get(0);
        Assertions.assertEquals("latitude", x.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, x.getDirection().getType().getSemantics());
        Assertions.assertNull(x.getDirection().getComplement());
        Assertions.assertTrue(x.getIdentifiers().isEmpty());
        Assertions.assertEquals(1, x.getOrder().getValue().getSemantics().intValue());
        Assertions.assertNull(x.getUnit());

        final var y = cs.getAxis().get(1);
        Assertions.assertEquals("longitude", y.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, y.getDirection().getType().getSemantics());
        Assertions.assertNull(y.getDirection().getComplement());
        Assertions.assertTrue(y.getIdentifiers().isEmpty());
        Assertions.assertEquals(2, y.getOrder().getValue().getSemantics().intValue());
        Assertions.assertNull(y.getUnit());

        final var csUnit = cs.getUnit();
        Assertions.assertTrue(csUnit instanceof Unit.Angle);
        Assertions.assertEquals("grad", csUnit.getName().getSemantics());
        Assertions.assertTrue(csUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.015707963267949,
        ((Double) csUnit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(csUnit.getIdentifiers().isEmpty());

        Assertions.assertTrue(crs.getUsages().isEmpty());
        Assertions.assertTrue(crs.getIdentifiers().isEmpty());

        final var remark = crs.getRemark();

        Assertions.assertEquals("Nouvelle Triangulation Française", remark.getText().getSemantics());
    }

    @Test
    public void geodetic_datum_test_a_1() throws LanguageException {

        final var text = "DATUM[\"North American Datum 1983\","
                + "ELLIPSOID[\"GRS 1980\",6378137,298.257222101,LENGTHUNIT[\"metre\",1.0]]]";

        final WktParser parser = WktParser.of(text);

        final Token[] out = new Token[2];
        final var datum = parser.geodeticDatum(out);
        Assertions.assertNull(out[0]);
        Assertions.assertNull(out[1]);

        Assertions.assertEquals("North American Datum 1983", datum.getName().getSemantics());
        Assertions.assertNull(datum.getAnchor());
        Assertions.assertNull(datum.getPrimeMeridian());
        Assertions.assertTrue(datum.getIdentifiers().isEmpty());

        final var ellipsoid = datum.getEllipsoid();

        Assertions.assertEquals("GRS 1980", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257222101, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        final var unit = ellipsoid.getUnit();
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, unit.getConversionFactor().getSemantics().doubleValue());
    }

    @Test
    public void geodetic_datum_test_b_1() throws LanguageException {

        final var text = "DATUM[\"World Geodetic System 1984\","
                + "ELLIPSOID[\"WGS 84\",6378388.0,298.257223563,LENGTHUNIT[\"metre\",1.0]]],"
                + "PRIMEM[\"Greenwich\",0.0]";

        final WktParser parser = WktParser.of(text);

        final Token[] out = new Token[2];
        final var datum = parser.geodeticDatum(out);
        Assertions.assertNull(out[0]);
        Assertions.assertNull(out[1]);

        Assertions.assertEquals("World Geodetic System 1984", datum.getName().getSemantics());
        Assertions.assertNull(datum.getAnchor());
        Assertions.assertTrue(datum.getIdentifiers().isEmpty());

        final var ellipsoid = datum.getEllipsoid();

        Assertions.assertEquals("WGS 84", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6378388.0, ellipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257223563, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        final var unit = ellipsoid.getUnit();
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, unit.getConversionFactor().getSemantics().doubleValue());

        final var primeMeridian = datum.getPrimeMeridian();
        Assertions.assertEquals("Greenwich", primeMeridian.getName().getSemantics());
        Assertions.assertTrue(primeMeridian.getIrmLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(0., primeMeridian.getIrmLongitude().getSemantics().doubleValue());
    }

    @Test
    public void geodetic_datum_test_c_1() throws LanguageException {

        final var text = "DATUM[\"Tananarive 1925\","
                + "ELLIPSOID[\"International 1924\",6378388.0,297.0,LENGTHUNIT[\"metre\",1.0]],"
                + "ANCHOR[\"Tananarive observatory:21.0191667gS, 50.23849537gE (of Paris)\"]],"
                + "PRIMEM[\"Paris\",2.5969213,ANGLEUNIT[\"grad\",0.015707963267949]]";

        final WktParser parser = WktParser.of(text);

        final Token[] out = new Token[2];
        final var datum = parser.geodeticDatum(out);
        Assertions.assertNull(out[0]);
        Assertions.assertNull(out[1]);

        Assertions.assertEquals("Tananarive 1925", datum.getName().getSemantics());
        Assertions.assertTrue(datum.getIdentifiers().isEmpty());

        final var ellipsoid = datum.getEllipsoid();

        Assertions.assertEquals("International 1924", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(6378388.0, ellipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(297.0, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        final var ellipsoidUnit = ellipsoid.getUnit();
        Assertions.assertEquals("metre", ellipsoidUnit.getName().getSemantics());
        Assertions.assertTrue(ellipsoidUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, ellipsoidUnit.getConversionFactor().getSemantics().doubleValue());

        final var primeMeridian = datum.getPrimeMeridian();
        Assertions.assertEquals("Paris", primeMeridian.getName().getSemantics());
        Assertions.assertTrue(primeMeridian.getIrmLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(2.5969213, primeMeridian.getIrmLongitude().getSemantics().doubleValue());

        final var primeMeridianUnit = primeMeridian.getUnit();
        Assertions.assertEquals("grad", primeMeridianUnit.getName().getSemantics());
        Assertions.assertTrue(primeMeridianUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.015707963267949,
        primeMeridianUnit.getConversionFactor().getSemantics().doubleValue());


        final Anchor anchor = datum.getAnchor();

        Assertions.assertEquals("Tananarive observatory:21.0191667gS, 50.23849537gE (of Paris)",
                anchor.getDescription().getSemantics());
    }

    @Test
    public void anchor_test_1() throws LanguageException {

        final var text = "ANCHOR[\"Tananarive observatory:21.0191667gS, 50.23849537gE (of Paris)\"]";

        final WktParser parser = WktParser.of(text);

        final Anchor anchor = parser.anchor();

        Assertions.assertEquals("Tananarive observatory:21.0191667gS, 50.23849537gE (of Paris)",
                anchor.getDescription().getSemantics());
    }

    @Test
    public void ellipsoid_test_a_1() throws LanguageException {

        final var text = "ELLIPSOID[\"GRS 1980\",6378137,298.257222101,LENGTHUNIT[\"metre\",1.0]]";

        final WktParser parser = WktParser.of(text);

        final var ellipsoid = parser.ellipsoid();

        Assertions.assertEquals("GRS 1980", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257222101, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        final var unit = ellipsoid.getUnit();
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, unit.getConversionFactor().getSemantics().doubleValue());
    }

    @Test
    public void ellipsoid_test_b_1() throws LanguageException {

        final var text = "ELLIPSOID[\"GRS 1980\",6378137,298.257222101]";

        final WktParser parser = WktParser.of(text);

        final var ellipsoid = parser.ellipsoid();

        Assertions.assertEquals("GRS 1980", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257222101, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertNull(ellipsoid.getUnit());
    }

    @Test
    public void ellipsoid_test_c_1() throws LanguageException {

        final var text = "ELLIPSOID[\"Clark 1866\",20925832.164,294.97869821,"
                + "LENGTHUNIT[\"US survey foot\",0.304800609601219]]";

        final WktParser parser = WktParser.of(text);

        final var ellipsoid = parser.ellipsoid();

        Assertions.assertEquals("Clark 1866", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(20925832.164, ellipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(294.97869821, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        final var unit = ellipsoid.getUnit();
        Assertions.assertEquals("US survey foot", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.304800609601219, unit.getConversionFactor().getSemantics().doubleValue());
    }

    @Test
    public void ellipsoid_test_d_1() throws LanguageException {

        final var text = "ELLIPSOID[\"GRS 1980\",6371000,0,LENGTHUNIT[\"metre\",1.0]]";

        final WktParser parser = WktParser.of(text);

        final var ellipsoid = parser.ellipsoid();

        Assertions.assertEquals("GRS 1980", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6371000, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Integer);
        Assertions.assertEquals(0, ellipsoid.getInverseFlattening().getSemantics().intValue());

        final var unit = ellipsoid.getUnit();
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, unit.getConversionFactor().getSemantics().doubleValue());
    }

    @Test
    public void ellipsoid_test_e_1() throws LanguageException {

        final var text = "SPHEROID[\"GRS 1980\",6378137,298.257222101,LENGTHUNIT[\"metre\",1.0]]";

        final WktParser parser = WktParser.of(text);

        final var ellipsoid = parser.ellipsoid();

        Assertions.assertEquals("GRS 1980", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257222101, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        final var unit = ellipsoid.getUnit();
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, unit.getConversionFactor().getSemantics().doubleValue());
    }

    @Test
    public void ellipsoid_test_f_1() throws LanguageException {

        final var text = "SPHEROID[\"GRS 1980\",6378137,298.257222101]";

        final WktParser parser = WktParser.of(text);

        final var ellipsoid = parser.ellipsoid();

        Assertions.assertEquals("GRS 1980", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6378137, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(298.257222101, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        Assertions.assertNull(ellipsoid.getUnit());
    }

    @Test
    public void ellipsoid_test_g_1() throws LanguageException {

        final var text = "SPHEROID[\"Clark 1866\",20925832.164,294.97869821,"
        + "LENGTHUNIT[\"US survey foot\",0.304800609601219]]";

        final WktParser parser = WktParser.of(text);

        final var ellipsoid = parser.ellipsoid();

        Assertions.assertEquals("Clark 1866", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Double);
        Assertions.assertEquals(20925832.164, ellipsoid.getSemiMajorAxis().getSemantics().doubleValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Double);
        Assertions.assertEquals(294.97869821, ellipsoid.getInverseFlattening().getSemantics().doubleValue());

        final var unit = ellipsoid.getUnit();
        Assertions.assertEquals("US survey foot", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.304800609601219, unit.getConversionFactor().getSemantics().doubleValue());
    }

    @Test
    public void ellipsoid_test_h_1() throws LanguageException {

        final var text = "SPHEROID[\"GRS 1980\",6371000,0,LENGTHUNIT[\"metre\",1.0]]";

        final WktParser parser = WktParser.of(text);

        final var ellipsoid = parser.ellipsoid();

        Assertions.assertEquals("GRS 1980", ellipsoid.getName().getSemantics());
        Assertions.assertTrue(ellipsoid.getSemiMajorAxis().getSemantics() instanceof Integer);
        Assertions.assertEquals(6371000, ellipsoid.getSemiMajorAxis().getSemantics().intValue());
        Assertions.assertTrue(ellipsoid.getInverseFlattening().getSemantics() instanceof Integer);
        Assertions.assertEquals(0, ellipsoid.getInverseFlattening().getSemantics().intValue());

        final var unit = ellipsoid.getUnit();
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, unit.getConversionFactor().getSemantics().doubleValue());
    }

    @Test
    public void coordinate_system_test_a_1() throws LanguageException {

        final var text = "CS[Cartesian,3],"
                + "AXIS[\"(X)\",geocentricX],"
                + "AXIS[\"(Y)\",geocentricY],"
                + "AXIS[\"(Z)\",geocentricZ],"
                + "LENGTHUNIT[\"metre\",1.0]";

        final WktParser parser = WktParser.of(text);

        final Token[] out = new Token[2];
        final var cs = parser.coordinateSystem(out);
        Assertions.assertNull(out[0]);
        Assertions.assertNull(out[1]);

        Assertions.assertEquals(CsType.CARTESIAN, cs.getType().getSemantics());
        Assertions.assertEquals(3, cs.getDimension().getSemantics().intValue());

        Assertions.assertTrue(cs.getIdentifiers().isEmpty());

        Assertions.assertEquals(3, cs.getAxis().size());

        final var x = cs.getAxis().get(0);
        Assertions.assertEquals("(X)", x.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricX, x.getDirection().getType().getSemantics());
        Assertions.assertNull(x.getDirection().getComplement());
        Assertions.assertTrue(x.getIdentifiers().isEmpty());
        Assertions.assertNull(x.getOrder());
        Assertions.assertNull(x.getUnit());

        final var y = cs.getAxis().get(1);
        Assertions.assertEquals("(Y)", y.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricY, y.getDirection().getType().getSemantics());
        Assertions.assertNull(y.getDirection().getComplement());
        Assertions.assertTrue(y.getIdentifiers().isEmpty());
        Assertions.assertNull(y.getOrder());
        Assertions.assertNull(y.getUnit());

        final var z = cs.getAxis().get(2);
        Assertions.assertEquals("(Z)", z.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.geocentricZ, z.getDirection().getType().getSemantics());
        Assertions.assertNull(z.getDirection().getComplement());
        Assertions.assertTrue(z.getIdentifiers().isEmpty());
        Assertions.assertNull(z.getOrder());
        Assertions.assertNull(z.getUnit());

        final var unit = cs.getUnit();
        Assertions.assertTrue(unit instanceof Unit.Length);
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, ((Double) unit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());
    }

    @Test
    public void coordinate_system_test_b_1() throws LanguageException {

        final var text = "CS[cartesian,3],"
                + "AXIS[\"(X)\",east],"
                + "AXIS[\"(Y)\",north],"
                + "AXIS[\"(Z)\",up],"
                + "LENGTHUNIT[\"metre\",1.0]";

        final WktParser parser = WktParser.of(text);

        final Token[] out = new Token[2];
        final var cs = parser.coordinateSystem(out);
        Assertions.assertNull(out[0]);
        Assertions.assertNull(out[1]);

        Assertions.assertEquals(CsType.CARTESIAN, cs.getType().getSemantics());
        Assertions.assertEquals(3, cs.getDimension().getSemantics().intValue());

        Assertions.assertTrue(cs.getIdentifiers().isEmpty());

        Assertions.assertEquals(3, cs.getAxis().size());

        final var x = cs.getAxis().get(0);
        Assertions.assertEquals("(X)", x.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, x.getDirection().getType().getSemantics());
        Assertions.assertNull(x.getDirection().getComplement());
        Assertions.assertTrue(x.getIdentifiers().isEmpty());
        Assertions.assertNull(x.getOrder());
        Assertions.assertNull(x.getUnit());

        final var y = cs.getAxis().get(1);
        Assertions.assertEquals("(Y)", y.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, y.getDirection().getType().getSemantics());
        Assertions.assertNull(y.getDirection().getComplement());
        Assertions.assertTrue(y.getIdentifiers().isEmpty());
        Assertions.assertNull(y.getOrder());
        Assertions.assertNull(y.getUnit());

        final var z = cs.getAxis().get(2);
        Assertions.assertEquals("(Z)", z.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.up, z.getDirection().getType().getSemantics());
        Assertions.assertNull(z.getDirection().getComplement());
        Assertions.assertTrue(z.getIdentifiers().isEmpty());
        Assertions.assertNull(z.getOrder());
        Assertions.assertNull(z.getUnit());

        final var unit = cs.getUnit();
        Assertions.assertTrue(unit instanceof Unit.Length);
        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, ((Double) unit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());
    }

    @Test
    public void coordinate_system_test_c_1() throws LanguageException {

        final var text = "CS[ellipsoidal,3],"
                + "AXIS[\"latitude\",north,ORDER[1],ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "AXIS[\"longitude\",east,ORDER[2],ANGLEUNIT[\"degree\",0.0174532925199433]],"
                + "AXIS[\"ellipsoidal height (h)\",up,ORDER[3],LENGTHUNIT[\"metre\",1.0]]";

        final WktParser parser = WktParser.of(text);

        final Token[] out = new Token[2];
        final var cs = parser.coordinateSystem(out);
        Assertions.assertNull(out[0]);
        Assertions.assertNull(out[1]);

        Assertions.assertEquals(CsType.ELLIPSOIDAL, cs.getType().getSemantics());
        Assertions.assertEquals(3, cs.getDimension().getSemantics().intValue());

        Assertions.assertTrue(cs.getIdentifiers().isEmpty());

        Assertions.assertEquals(3, cs.getAxis().size());

        final var x = cs.getAxis().get(0);
        Assertions.assertEquals("latitude", x.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.north, x.getDirection().getType().getSemantics());
        Assertions.assertNull(x.getDirection().getComplement());
        Assertions.assertTrue(x.getIdentifiers().isEmpty());
        Assertions.assertEquals(1, x.getOrder().getValue().getSemantics().intValue());
        final var xUnit = x.getUnit();
        Assertions.assertTrue(xUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", xUnit.getName().getSemantics());
        Assertions.assertTrue(xUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433,
        ((Double) xUnit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(xUnit.getIdentifiers().isEmpty());

        final var y = cs.getAxis().get(1);
        Assertions.assertEquals("longitude", y.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.east, y.getDirection().getType().getSemantics());
        Assertions.assertNull(y.getDirection().getComplement());
        Assertions.assertTrue(y.getIdentifiers().isEmpty());
        Assertions.assertEquals(2, y.getOrder().getValue().getSemantics().intValue());
        final var yUnit = y.getUnit();
        Assertions.assertTrue(yUnit instanceof Unit.Angle);
        Assertions.assertEquals("degree", yUnit.getName().getSemantics());
        Assertions.assertTrue(yUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433,
        ((Double) yUnit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(yUnit.getIdentifiers().isEmpty());

        final var z = cs.getAxis().get(2);
        Assertions.assertEquals("ellipsoidal height (h)", z.getNameAbrev().getSemantics());
        Assertions.assertEquals(Direction.up, z.getDirection().getType().getSemantics());
        Assertions.assertNull(z.getDirection().getComplement());
        Assertions.assertTrue(z.getIdentifiers().isEmpty());
        Assertions.assertEquals(3, z.getOrder().getValue().getSemantics().intValue());
        final var zUnit = z.getUnit();
        Assertions.assertTrue(zUnit instanceof Unit.Length);
        Assertions.assertEquals("metre", zUnit.getName().getSemantics());
        Assertions.assertTrue(zUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, ((Double) zUnit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(zUnit.getIdentifiers().isEmpty());

        Assertions.assertNull(cs.getUnit());
    }

    @Test
    public void axis1() throws LanguageException {

        final var text = """
                         AXIS["distance (r)",awayFrom,ORDER[1],LENGTHUNIT["kilometre",1000]]""";

        final WktParser parser = WktParser.of(text);

        final var axis = parser.axis();

        Assertions.assertEquals("distance (r)", axis.getNameAbrev().getSemantics());

        final var direction = axis.getDirection();

        Assertions.assertEquals(Direction.awayFrom, direction.getType().getSemantics());
        Assertions.assertNull(direction.getComplement());

        final var order = axis.getOrder();
        Assertions.assertEquals(1, order.getValue().getSemantics().intValue());

        final var unit = axis.getUnit();
        Assertions.assertEquals("kilometre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(1000, unit.getConversionFactor().getSemantics().intValue());
    }

    @Test
    public void axis2() throws LanguageException {

        final var text = """
                         AXIS["distance (r)",awayFrom,ORDER[1],RANGEMEANING[exact],LENGTHUNIT["kilometre",1000]]""";

        final WktParser parser = WktParser.of(text);

        final var axis = parser.axis();

        Assertions.assertEquals("distance (r)", axis.getNameAbrev().getSemantics());

        final var direction = axis.getDirection();

        Assertions.assertEquals(Direction.awayFrom, direction.getType().getSemantics());
        Assertions.assertNull(direction.getComplement());

        final var order = axis.getOrder();
        Assertions.assertEquals(1, order.getValue().getSemantics().intValue());

        final var range = axis.getRange();
        Assertions.assertNull(range.getMin());
        Assertions.assertNull(range.getMax());
        Assertions.assertEquals(RangeMeaningType.EXACT, range.getMeaning().getType().getSemantics());

        final var unit = axis.getUnit();
        Assertions.assertEquals("kilometre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(1000, unit.getConversionFactor().getSemantics().intValue());
    }

    @Test
    public void axis3() throws LanguageException {

        final var text = """
                         AXIS["distance (r)",awayFrom,
                         ORDER[1],
                         AXISMINVALUE[-2],
                         AXISMAXVALUE[85],
                         RANGEMEANING[exact],
                         LENGTHUNIT["kilometre",1000]]""";

        final WktParser parser = WktParser.of(text);

        final var axis = parser.axis();

        Assertions.assertEquals("distance (r)", axis.getNameAbrev().getSemantics());

        final var direction = axis.getDirection();

        Assertions.assertEquals(Direction.awayFrom, direction.getType().getSemantics());
        Assertions.assertNull(direction.getComplement());

        final var order = axis.getOrder();
        Assertions.assertEquals(1, order.getValue().getSemantics().intValue());

        final var range = axis.getRange();
        Assertions.assertEquals(-2, range.getMin().getValue().getSemantics());
        Assertions.assertEquals(85, range.getMax().getValue().getSemantics());
        Assertions.assertEquals(RangeMeaningType.EXACT, range.getMeaning().getType().getSemantics());

        final var unit = axis.getUnit();
        Assertions.assertEquals("kilometre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(1000, unit.getConversionFactor().getSemantics().intValue());
    }

    @Test
    public void axis4() throws LanguageException {

        final var text = """
                         AXIS["distance (r)",awayFrom,
                         ORDER[1],
                         AXISMINVALUE[-2],
                         AXISMAXVALUE[85],
                         LENGTHUNIT["kilometre",1000]]""";

        final WktParser parser = WktParser.of(text);

        final var axis = parser.axis();

        Assertions.assertEquals("distance (r)", axis.getNameAbrev().getSemantics());

        final var direction = axis.getDirection();

        Assertions.assertEquals(Direction.awayFrom, direction.getType().getSemantics());
        Assertions.assertNull(direction.getComplement());

        final var order = axis.getOrder();
        Assertions.assertEquals(1, order.getValue().getSemantics().intValue());

        final var range = axis.getRange();
        Assertions.assertEquals(-2, range.getMin().getValue().getSemantics());
        Assertions.assertEquals(85, range.getMax().getValue().getSemantics());
        Assertions.assertNull(range.getMeaning());

        final var unit = axis.getUnit();
        Assertions.assertEquals("kilometre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(1000, unit.getConversionFactor().getSemantics().intValue());
    }

    @Test
    public void axis5() throws LanguageException {

        final var text = """
                         AXIS["longitude (U)",counterClockwise,
                         BEARING[0],
                         ORDER[2],
                         ANGLEUNIT["degree",0.0174532925199433]]""";

        final WktParser parser = WktParser.of(text);

        final var axis = parser.axis();

        Assertions.assertEquals("longitude (U)", axis.getNameAbrev().getSemantics());

        final var direction = axis.getDirection();

        Assertions.assertEquals(Direction.counterClockwise, direction.getType().getSemantics());
        Assertions.assertTrue(direction.getComplement() instanceof SimpleNumber.Bearing);
        Assertions.assertTrue(
        ((SimpleNumber.Bearing) direction.getComplement()).getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(0,
        ((Integer) ((SimpleNumber.Bearing) direction.getComplement()).getValue().getSemantics()).intValue());

        final var order = axis.getOrder();
        Assertions.assertEquals(2, order.getValue().getSemantics().intValue());

        final var unit = axis.getUnit();
        Assertions.assertEquals("degree", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());
    }

    @Test
    public void axis_test_c_1() throws LanguageException {

        final var text = "AXIS[\"longitude (U)\",counterClockwise,BEARING[0],ORDER[2],"
                + "UNIT[\"degree\",0.0174532925199433]]";

        final WktParser parser = WktParser.of(text);

        final var axis = parser.axis();

        Assertions.assertEquals("longitude (U)", axis.getNameAbrev().getSemantics());

        final var direction = axis.getDirection();

        Assertions.assertEquals(Direction.counterClockwise, direction.getType().getSemantics());
        Assertions.assertTrue(direction.getComplement() instanceof SimpleNumber.Bearing);
        Assertions.assertTrue(
        ((SimpleNumber.Bearing) direction.getComplement()).getValue().getSemantics() instanceof Integer);
        Assertions.assertEquals(0,
        ((Integer) ((SimpleNumber.Bearing) direction.getComplement()).getValue().getSemantics()).intValue());

        final var order = axis.getOrder();
        Assertions.assertEquals(2, order.getValue().getSemantics().intValue());

        final var unit = axis.getUnit();
        Assertions.assertEquals("degree", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.0174532925199433, unit.getConversionFactor().getSemantics().doubleValue());
    }

    @Test
    public void axis_direction_test_b_1() throws LanguageException {

        final var text = "north,MERIDIAN[-17.6666667,UNIT[\"radian\",0.]]";

        final WktParser parser = WktParser.of(text);

        final var direction = parser.axisDirection();

        Assertions.assertEquals(Direction.north, direction.getType().getSemantics());
        Assertions.assertTrue(direction.getComplement() instanceof Meridian);

        final var meridian = (Meridian) direction.getComplement();

        Assertions.assertTrue(meridian.getLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(-17.6666667, ((Double) meridian.getLongitude().getSemantics()).doubleValue());

        final var angle = meridian.getUnit();
        Assertions.assertEquals("radian", angle.getName().getSemantics());
        Assertions.assertTrue(angle.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0., angle.getConversionFactor().getSemantics().doubleValue());
    }

    @Test
    public void axis_direction_test_c_1() throws LanguageException {

        final var text = "clockwise,BEARING[-17.6666667]";

        final WktParser parser = WktParser.of(text);

        final var direction = parser.axisDirection();

        Assertions.assertEquals(Direction.clockwise, direction.getType().getSemantics());
        Assertions.assertTrue(direction.getComplement() instanceof SimpleNumber.Bearing);

        final var bearing = (SimpleNumber.Bearing) direction.getComplement();

        Assertions.assertTrue(bearing.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(-17.6666667, ((Double) bearing.getValue().getSemantics()).doubleValue());
    }

    @Test
    public void order_test_1() throws LanguageException {

        final var text = "ORDER[3]";

        final WktParser parser = WktParser.of(text);

        final var order = parser.axisOrder();

        Assertions.assertEquals(3, order.getValue().getSemantics().intValue());
    }

    @Test
    public void bearing_test_1() throws LanguageException {

        final var text = "BEARING[-17.6666667]";

        final WktParser parser = WktParser.of(text);

        final var bearing = parser.bearing();

        Assertions.assertTrue(bearing.getValue().getSemantics() instanceof Double);
        Assertions.assertEquals(-17.6666667, ((Double) bearing.getValue().getSemantics()).doubleValue());
    }

    @Test
    public void meridian_test_1() throws LanguageException {

        final var text = "MERIDIAN[-17.6666667,UNIT[\"radian\",0.]]";

        final WktParser parser = WktParser.of(text);

        final var meridian = parser.meridian();

        Assertions.assertTrue(meridian.getLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(-17.6666667, ((Double) meridian.getLongitude().getSemantics()).doubleValue());

        final var angle = meridian.getUnit();
        Assertions.assertEquals("radian", angle.getName().getSemantics());
        Assertions.assertTrue(angle.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0., angle.getConversionFactor().getSemantics().doubleValue());
    }

    @Test
    public void prime_meridian_test_a_1() throws LanguageException {

        final var text = "PRIMEM[\"Ferro\",-17.6666667]";

        final WktParser parser = WktParser.of(text);

        final PrimeMeridian meridian = parser.primeMeridian();

        Assertions.assertEquals("Ferro", meridian.getName().getSemantics());
        Assertions.assertTrue(meridian.getIrmLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(-17.6666667, ((Double) meridian.getIrmLongitude().getSemantics()).doubleValue());
        Assertions.assertNull(meridian.getUnit());
        Assertions.assertTrue(meridian.getIdentifiers().isEmpty());
    }

    @Test
    public void prime_meridian_test_b_1() throws LanguageException {

        final var text = "PRIMEM[\"Paris\",2.5969213]";

        final WktParser parser = WktParser.of(text);

        final PrimeMeridian meridian = parser.primeMeridian();

        Assertions.assertEquals("Paris", meridian.getName().getSemantics());
        Assertions.assertTrue(meridian.getIrmLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(2.5969213, ((Double) meridian.getIrmLongitude().getSemantics()).doubleValue());
        Assertions.assertNull(meridian.getUnit());
        Assertions.assertTrue(meridian.getIdentifiers().isEmpty());
    }

    @Test
    public void prime_meridian_test_c_1() throws LanguageException {

        final var text = "PRIMEM[\"Paris\",2.5969213,ANGLEUNIT[\"grad\",0.015707963267949]]";

        final WktParser parser = WktParser.of(text);

        final PrimeMeridian meridian = parser.primeMeridian();

        Assertions.assertEquals("Paris", meridian.getName().getSemantics());
        Assertions.assertTrue(meridian.getIrmLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(2.5969213, ((Double) meridian.getIrmLongitude().getSemantics()).doubleValue());

        final Unit.Angle unit = meridian.getUnit();

        Assertions.assertEquals("grad", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(0.015707963267949, ((Double) unit.getConversionFactor().getSemantics()).doubleValue());

        Assertions.assertTrue(meridian.getIdentifiers().isEmpty());
    }

    @Test
    public void prime_meridian_test_d_1() throws LanguageException {

        final var text = "PRIMEM[\"Greenwich\",0.0]";

        final WktParser parser = WktParser.of(text);

        final PrimeMeridian meridian = parser.primeMeridian();

        Assertions.assertEquals("Greenwich", meridian.getName().getSemantics());
        Assertions.assertTrue(meridian.getIrmLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(0., ((Double) meridian.getIrmLongitude().getSemantics()).doubleValue());
        Assertions.assertNull(meridian.getUnit());
        Assertions.assertTrue(meridian.getIdentifiers().isEmpty());
    }

    @Test
    public void prime_meridian_test_e_1() throws LanguageException {

        final var text = "PRIMEM[\"Greenwich\",0.0,ID[\"EPSG\",9102],ID[\"LOCAL\",\"1\",CITATION[\"citation\"]]]";

        final WktParser parser = WktParser.of(text);

        final PrimeMeridian meridian = parser.primeMeridian();

        Assertions.assertEquals("Greenwich", meridian.getName().getSemantics());
        Assertions.assertTrue(meridian.getIrmLongitude().getSemantics() instanceof Double);
        Assertions.assertEquals(0., ((Double) meridian.getIrmLongitude().getSemantics()).doubleValue());
        Assertions.assertNull(meridian.getUnit());

        final var identifiers = meridian.getIdentifiers();
        Assertions.assertEquals(2, identifiers.size());

        final var id0 = identifiers.get(0);
        Assertions.assertEquals("EPSG", id0.getName().getSemantics());
        Assertions.assertTrue(id0.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(9102, ((Integer) id0.getId().getSemantics()).intValue());
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
    public void angle_unit_test_a_1() throws LanguageException {

        final var text = "ANGLEUNIT[\"radian\",1]";

        final WktParser parser = WktParser.of(text);

        final Unit.Angle unit = parser.angleUnit();

        Assertions.assertEquals("radian", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(1, ((Integer) unit.getConversionFactor().getSemantics()).intValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());
    }

    @Test
    public void angle_unit_test_b_1() throws LanguageException {

        final var text = "UNIT[\"radian\",1]";

        final WktParser parser = WktParser.of(text);

        final Unit.Angle unit = parser.angleUnit();

        Assertions.assertEquals("radian", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(1, ((Integer) unit.getConversionFactor().getSemantics()).intValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());
    }

    @Test
    public void length_unit_test_a_1() throws LanguageException {

        final var text = "LENGTHUNIT[\"metre\",1]";

        final WktParser parser = WktParser.of(text);

        final Unit.Length unit = parser.lengthUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(1, ((Integer) unit.getConversionFactor().getSemantics()).intValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());
    }

    @Test
    public void length_unit_test_b_1() throws LanguageException {

        final var text = "UNIT[\"metre\",1]";

        final WktParser parser = WktParser.of(text);

        final Unit.Length unit = parser.lengthUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(1, ((Integer) unit.getConversionFactor().getSemantics()).intValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());
    }

    @Test
    public void length_unit_test_c_1() throws LanguageException {

        final var text = "UNIT[\"metre\",1,ID[\"EPSG\",9001]]";

        final WktParser parser = WktParser.of(text);

        final Unit.Length unit = parser.lengthUnit();

        Assertions.assertEquals("metre", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(1, ((Integer) unit.getConversionFactor().getSemantics()).intValue());

        Assertions.assertEquals(1, unit.getIdentifiers().size());
        final Identifier identifier = unit.getIdentifiers().get(0);
        Assertions.assertEquals("EPSG", identifier.getName().getSemantics());
        Assertions.assertTrue(identifier.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(9001, ((Integer) identifier.getId().getSemantics()).intValue());
        Assertions.assertNull(identifier.getVersion());
        Assertions.assertNull(identifier.getUri());
        Assertions.assertNull(identifier.getCitation());
    }

    @Test
    public void scale_unit_test_a_1() throws LanguageException {

        final var text = "SCALEUNIT[\"parts per million\",1E-06]";

        final WktParser parser = WktParser.of(text);

        final Unit.Scale unit = parser.scaleUnit();

        Assertions.assertEquals("parts per million", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1e-6, ((Double) unit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());
    }

    @Test
    public void scale_unit_test_b_1() throws LanguageException {

        final var text = "UNIT[\"parts per million\",1E-06]";

        final WktParser parser = WktParser.of(text);

        final Unit.Scale unit = parser.scaleUnit();

        Assertions.assertEquals("parts per million", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1e-6, ((Double) unit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());
    }

    @Test
    public void parametric_unit_test_1() throws LanguageException {

        final var text = "PARAMETRICUNIT[\"hectopascal\",100]";

        final WktParser parser = WktParser.of(text);

        final Unit.Parametric unit = parser.parametricUnit();

        Assertions.assertEquals("hectopascal", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Integer);
        Assertions.assertEquals(100, ((Integer) unit.getConversionFactor().getSemantics()).intValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());
    }

    @Test
    public void time_unit_test_a_1() throws LanguageException {

        final var text = "TIMEUNIT[\"day\",86400.0]";

        final WktParser parser = WktParser.of(text);

        final Unit.Time unit = parser.timeUnit();

        Assertions.assertEquals("day", unit.getName().getSemantics());
        Assertions.assertTrue(unit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(86400., ((Double) unit.getConversionFactor().getSemantics()).doubleValue());
        Assertions.assertTrue(unit.getIdentifiers().isEmpty());
    }

    @Test
    public void time_unit_test_b_1() throws LanguageException {

        final var text = "TIMEUNIT[\"day\",86400.0,ID[\"EPSG\",1029],ID[\"LOCAL\",\"1\",CITATION[\"citation\"]]]";

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
    public void identifier_test_a_1() throws LanguageException {

        final var text = "ID[\"Authority name\",\"Abcd_Ef\",7.1]";

        final WktParser parser = WktParser.of(text);

        final Identifier identifier = parser.identifier();

        Assertions.assertEquals("Authority name", identifier.getName().getSemantics());
        Assertions.assertEquals("Abcd_Ef", identifier.getId().getSemantics());
        Assertions.assertTrue(identifier.getVersion() instanceof SignedNumericLiteral);
        Assertions.assertTrue(identifier.getVersion().getSemantics() instanceof Double);
        Assertions.assertEquals(7.1, ((Double) identifier.getVersion().getSemantics()).doubleValue());
        Assertions.assertNull(identifier.getCitation());
        Assertions.assertNull(identifier.getUri());

    }

    @Test
    public void identifier_test_b_1() throws LanguageException {

        final var text = "ID[\"EPSG\",4326]";

        final WktParser parser = WktParser.of(text);

        final Identifier identifier = parser.identifier();

        Assertions.assertEquals("EPSG", identifier.getName().getSemantics());
        Assertions.assertTrue(identifier.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(4326, ((Integer) identifier.getId().getSemantics()).intValue());
        Assertions.assertNull(identifier.getVersion());
        Assertions.assertNull(identifier.getCitation());
        Assertions.assertNull(identifier.getUri());

    }

    @Test
    public void identifier_test_c_1() throws LanguageException {

        final var text = "ID[\"EPSG\",4326,URI[\"urn:ogc:def:crs:EPSG::4326\"]]";

        final WktParser parser = WktParser.of(text);

        final Identifier identifier = parser.identifier();

        Assertions.assertEquals("EPSG", identifier.getName().getSemantics());
        Assertions.assertTrue(identifier.getId().getSemantics() instanceof Integer);
        Assertions.assertEquals(4326, ((Integer) identifier.getId().getSemantics()).intValue());
        Assertions.assertNull(identifier.getVersion());
        Assertions.assertNull(identifier.getCitation());
        Assertions.assertEquals("urn:ogc:def:crs:EPSG::4326", identifier.getUri().getValue().getSemantics());

    }

    @Test
    public void identifier_test_d_1() throws LanguageException {

        final var text = "ID[\"EuroGeographics\",\"ES_ED50 (BAL99) to ETRS89\",\"2001-04-20\"]";

        final WktParser parser = WktParser.of(text);

        final Identifier identifier = parser.identifier();

        Assertions.assertEquals("EuroGeographics", identifier.getName().getSemantics());
        Assertions.assertTrue(identifier.getId().getSemantics() instanceof String);
        Assertions.assertEquals("ES_ED50 (BAL99) to ETRS89", ((String) identifier.getId().getSemantics()));
        Assertions.assertEquals("2001-04-20", identifier.getVersion().getSemantics());
        Assertions.assertNull(identifier.getCitation());
        Assertions.assertNull(identifier.getUri());

    }

    @Test
    public void citation_test_1() throws LanguageException {

        final var text = "CITATION[\"un exemple de citation\"]";

        final WktParser parser = WktParser.of(text);

        final Citation citation = parser.citation();

        Assertions.assertEquals("un exemple de citation", citation.getText().getSemantics());
    }

    @Test
    public void uri_test_1() throws LanguageException {

        final var text = "URI[\"urn:ogc:def:crs:EPSG::4326\"]";

        final WktParser parser = WktParser.of(text);

        final Uri uri = parser.uri();

        Assertions.assertEquals("urn:ogc:def:crs:EPSG::4326", uri.getValue().getSemantics());
    }

    @Test
    public void remark_test_1() throws LanguageException {

        final var text = "REMARK[\"Замечание на русском языке.\"]";

        final WktParser parser = WktParser.of(text);

        final Remark remark = parser.remark();

        Assertions.assertEquals("Замечание на русском языке.", remark.getText().getSemantics());

        Assertions.assertEquals(7, remark.getText().first());
        Assertions.assertEquals(35, remark.getText().last());
        Assertions.assertEquals(2, remark.getText().order());

        Assertions.assertEquals(0, remark.first());
        Assertions.assertEquals(36, remark.last());
        Assertions.assertEquals(4, remark.order());
    }

    @Test
    public void area_test_1() throws LanguageException {

        final var text = "AREA[\"Netherlands offshore.\"]";

        final WktParser parser = WktParser.of(text);

        final Area area = parser.areaDescription();

        Assertions.assertEquals("Netherlands offshore.", area.getName().getSemantics());

        Assertions.assertEquals(5, area.getName().first());
        Assertions.assertEquals(27, area.getName().last());
        Assertions.assertEquals(2, area.getName().order());

        Assertions.assertEquals(0, area.first());
        Assertions.assertEquals(28, area.last());
        Assertions.assertEquals(4, area.order());
    }

    @Test
    public void bbox_test_1() throws LanguageException {

        final var text = "BBOX[375,-111,431,-2.3]";

        final WktParser parser = WktParser.of(text);

        final BBox bbox = parser.geographicBoundingBox();

        Assertions.assertEquals(375., bbox.getLowerLeftLatitude().getSemantics().doubleValue());
        Assertions.assertEquals(-111., bbox.getLowerLeftLongitude().getSemantics().doubleValue());
        Assertions.assertEquals(431., bbox.getUpperRightLatitude().getSemantics().doubleValue());
        Assertions.assertEquals(-2.3, bbox.getUpperRightLongitude().getSemantics().doubleValue());
    }

    @Test
    public void vertical_extent_a_1() throws LanguageException {

        final var text = "VERTICALEXTENT[-1000,0,LENGTHUNIT[\"metre\",1.0]]";

        final WktParser parser = WktParser.of(text);

        final VerticalExtent verticalExtent = parser.verticalExtent();

        Assertions.assertTrue(verticalExtent.getMinimumHeight().getSemantics() instanceof Integer);
        Assertions.assertEquals(-1000, verticalExtent.getMinimumHeight().getSemantics().intValue());
        Assertions.assertTrue(verticalExtent.getMaximumHeight().getSemantics() instanceof Integer);
        Assertions.assertEquals(0, verticalExtent.getMaximumHeight().getSemantics().intValue());

        final Unit lengthUnit = verticalExtent.getLengthUnit();
        Assertions.assertEquals("metre", lengthUnit.getName().getSemantics());
        Assertions.assertTrue(lengthUnit.getConversionFactor().getSemantics() instanceof Double);
        Assertions.assertEquals(1.0, lengthUnit.getConversionFactor().getSemantics().doubleValue());
    }

    @Test
    public void vertical_extent_b_1() throws LanguageException {

        final var text = "VERTICALEXTENT[-1000,0]";

        final WktParser parser = WktParser.of(text);

        final VerticalExtent verticalExtent = parser.verticalExtent();

        Assertions.assertTrue(verticalExtent.getMinimumHeight().getSemantics() instanceof Integer);
        Assertions.assertEquals(-1000, verticalExtent.getMinimumHeight().getSemantics().intValue());
        Assertions.assertTrue(verticalExtent.getMaximumHeight().getSemantics() instanceof Integer);
        Assertions.assertEquals(0, verticalExtent.getMaximumHeight().getSemantics().intValue());
        Assertions.assertNull(verticalExtent.getLengthUnit());
    }

    @Test
    public void time_extent_a_1() throws LanguageException {

        final var text = """
                         TIMEEXTENT[2013-01-01,2013-12-31]""";

        final WktParser parser = WktParser.of(text);

        final TemporalExtent timeExtent = parser.temporalExtent();

        Assertions.assertTrue(timeExtent.getExtentStart().getSemantics() instanceof LocalDate);
        Assertions.assertEquals(LocalDate.of(2013, Month.JANUARY, 1), timeExtent.getExtentStart().getSemantics());
        Assertions.assertTrue(timeExtent.getExtentEnd().getSemantics() instanceof LocalDate);
        Assertions.assertEquals(LocalDate.of(2013, Month.DECEMBER, 31), timeExtent.getExtentEnd().getSemantics());
    }

    @Test
    public void time_extent_b_1() throws LanguageException {

        final var text = """
                         TIMEEXTENT["Jurassic","Quaternary"]""";

        final WktParser parser = WktParser.of(text);

        final TemporalExtent timeExtent = parser.temporalExtent();

        Assertions.assertEquals("Jurassic", timeExtent.getExtentStart().getSemantics());
        Assertions.assertEquals("Quaternary", timeExtent.getExtentEnd().getSemantics());
    }

    @Test
    public void rangeMeaning1() throws LanguageException {

        final var text = """
                         RANGEMEANING[exact]""";

        final WktParser parser = WktParser.of(text);

        final AxisRangeMeaning meaning = parser.rangeMeaning();

        Assertions.assertEquals(RangeMeaningType.EXACT, meaning.getType().getSemantics());
    }

    @Test
    public void rangeMeaning2() throws LanguageException {

        final var text = """
                         RANGEMEANING[wraparound]""";

        final WktParser parser = WktParser.of(text);

        final AxisRangeMeaning meaning = parser.rangeMeaning();

        Assertions.assertEquals(RangeMeaningType.WRAPAROUND, meaning.getType().getSemantics());
    }
}
