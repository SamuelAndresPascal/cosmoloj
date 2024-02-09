package com.cosmoloj.language.wkt2.v2_1.parsing;

import com.cosmoloj.language.api.builder.TokenBuilder;
import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.datetime.parsing.DateTimeParser;
import com.cosmoloj.language.common.impl.parsing.AbstractPredictiveMappingUnpredictiveParser;
import com.cosmoloj.language.common.impl.parsing.DefaultStringScanner;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.common.number.parsing.NumberParser;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.expression.AbridgedTransformationBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Anchor;
import com.cosmoloj.language.wkt2.v2_1.expression.Area;
import com.cosmoloj.language.wkt2.v2_1.expression.SpatialTemporalAxis;
import com.cosmoloj.language.wkt2.v2_1.expression.SpatialTemporalAxisBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.AxisDirection;
import com.cosmoloj.language.wkt2.v2_1.expression.AxisDirectionBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.AxisOrder;
import com.cosmoloj.language.wkt2.v2_1.expression.AxisOrderBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.BBox;
import com.cosmoloj.language.wkt2.v2_1.expression.BBoxBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.BaseCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.BaseDatumCrsBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.BaseGeodeticCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.BaseGeodeticCrsBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.BaseProjectedCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.BaseProjectedCrsBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Citation;
import com.cosmoloj.language.wkt2.v2_1.expression.CompoundCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.CompoundCrsBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.CoordinateOperationBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.SpatialCoordinateSystem;
import com.cosmoloj.language.wkt2.v2_1.expression.SpatialCoordinateSystemBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Crs;
import com.cosmoloj.language.wkt2.v2_1.expression.DerivedCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.DerivedCrsBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Ellipsoid;
import com.cosmoloj.language.wkt2.v2_1.expression.EllipsoidBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Extent;
import com.cosmoloj.language.wkt2.v2_1.expression.GeodeticCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.GeodeticCrsBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.GeodeticDatum;
import com.cosmoloj.language.wkt2.v2_1.expression.GeodeticDatumBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.HorizontalCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.Identifier;
import com.cosmoloj.language.wkt2.v2_1.expression.IdentifierBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.ImageDatum;
import com.cosmoloj.language.wkt2.v2_1.expression.ImageDatumBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Meridian;
import com.cosmoloj.language.wkt2.v2_1.expression.MeridianBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Method;
import com.cosmoloj.language.wkt2.v2_1.expression.MethodBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.NameAndAnchorDatum;
import com.cosmoloj.language.wkt2.v2_1.expression.NameAndAnchorDatumBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Operation;
import com.cosmoloj.language.wkt2.v2_1.expression.OperationBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.OperationCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.OperationCrsBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Parameter;
import com.cosmoloj.language.wkt2.v2_1.expression.ParameterAbridged;
import com.cosmoloj.language.wkt2.v2_1.expression.ParameterAbridgedBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.ParameterBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.ParameterFile;
import com.cosmoloj.language.wkt2.v2_1.expression.ParameterFileBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.PrimeMeridian;
import com.cosmoloj.language.wkt2.v2_1.expression.PrimeMeridianBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Remark;
import com.cosmoloj.language.wkt2.v2_1.expression.RemarkBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Scope;
import com.cosmoloj.language.wkt2.v2_1.expression.SimpleCrsShell;
import com.cosmoloj.language.wkt2.v2_1.expression.SimpleCrsShellBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.SimpleNumber;
import com.cosmoloj.language.wkt2.v2_1.expression.SimpleNumberBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.TaggedLatinTextBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.TemporalExtent;
import com.cosmoloj.language.wkt2.v2_1.expression.TemporalExtentBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.TimeOrigin;
import com.cosmoloj.language.wkt2.v2_1.expression.TimeOriginBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Unit;
import com.cosmoloj.language.wkt2.v2_1.expression.UnitBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Uri;
import com.cosmoloj.language.wkt2.v2_1.expression.VerticalExtent;
import com.cosmoloj.language.wkt2.v2_1.expression.VerticalExtentBuilder;
import com.cosmoloj.language.wkt2.v2_1.lexeme.compound.Clock;
import com.cosmoloj.language.wkt2.v2_1.lexeme.compound.ClockBuilder;
import com.cosmoloj.language.wkt2.v2_1.lexeme.compound.Datetime;
import com.cosmoloj.language.wkt2.v2_1.lexeme.compound.DatetimeBuilder;
import com.cosmoloj.language.wkt2.v2_1.lexeme.compound.GregorianDate;
import com.cosmoloj.language.wkt2.v2_1.lexeme.compound.GregorianDateBuilder;
import com.cosmoloj.language.wkt2.v2_1.lexeme.compound.TimeZoneDesignator;
import com.cosmoloj.language.wkt2.v2_1.lexeme.compound.TimeZoneDesignatorBuilder;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.AxisNameAbrev;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.CsType;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.Direction;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.PixelInCell;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedUnicodeText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import java.util.ArrayList;
import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.wkt2.v2_1.expression.AxisMaximumValue;
import com.cosmoloj.language.wkt2.v2_1.expression.AxisMaximumValueBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.AxisMinimumValue;
import com.cosmoloj.language.wkt2.v2_1.expression.AxisMinimumValueBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.AxisRange;
import com.cosmoloj.language.wkt2.v2_1.expression.AxisRangeBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.AxisRangeMeaning;
import com.cosmoloj.language.wkt2.v2_1.expression.AxisRangeMeaningBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.BoundCrs;
import com.cosmoloj.language.wkt2.v2_1.expression.BoundCrsBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.ExtentBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.TemporalCoordinateSystem;
import com.cosmoloj.language.wkt2.v2_1.expression.TemporalCoordinateSystemBuilder;
import com.cosmoloj.language.wkt2.v2_1.expression.Usage;
import com.cosmoloj.language.wkt2.v2_1.expression.UsageBuilder;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.RangeMeaningType;

/**
 *
 * @author Samuel Andrés
 */
public class WktParser extends AbstractPredictiveMappingUnpredictiveParser<WktLexer> {

    private final NumberParser<WktLexer> numberParser;

    private final DateTimeParser<WktLexer> dateTimeParser;

    public WktParser(final WktLexer lexer) {
        super(lexer);
        this.numberParser = new NumberParser<>(lexer, '.', 'E');
        this.dateTimeParser = new DateTimeParser<>(lexer, '.');
    }

    private boolean comma() throws LanguageException {
        return ',' == flushTo();
    }

    public OperationCrs.SourceCrs sourceCrs() throws LanguageException {
        return build(OperationCrsBuilder.source().list(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                coordinateReferenceSystem(),
                flushAndLex(RightDelimiter.class)));
    }

    public OperationCrs.TargetCrs targetCrs() throws LanguageException {
        return build(OperationCrsBuilder.target().list(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                coordinateReferenceSystem(),
                flushAndLex(RightDelimiter.class)));
    }

    public OperationCrs.InterpolationCrs interpolationCrs() throws LanguageException {
        return interpolationCrs(flushAndLexEnum(WktKeyword.class));
    }

    public OperationCrs.InterpolationCrs interpolationCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(OperationCrsBuilder.interpolation().list(
                label,
                flushAndLex(LeftDelimiter.class),
                coordinateReferenceSystem(),
                flushAndLex(RightDelimiter.class)));
    }

    public Crs coordinateReferenceSystem() throws LanguageException {
        final EnumLexeme<WktKeyword> crsLabel = flushAndLexEnum(WktKeyword.class);
        final LeftDelimiter delimiter = flushAndLex(LeftDelimiter.class);
        final QuotedLatinText crsName = flushAndLex(QuotedLatinText.class);
        final EnumLexeme<SpecialSymbol> comma = flushAndLexEnum(SpecialSymbol.class);
        final EnumLexeme<WktKeyword> datumOrBaseLabel = flushAndLexEnum(WktKeyword.class);

        return switch (crsLabel.getSemantics()) {
            case GEODCRS, GEODETICCRS, GEOCCS, GEOGCS ->
                switch (datumOrBaseLabel.getSemantics()) {
                    case DATUM, GEODETICDATUM -> geodeticCrs(crsLabel, delimiter, crsName, comma, datumOrBaseLabel);
                    case BASEGEODCRS -> derivedGeodeticCrs(crsLabel, delimiter, crsName, comma, datumOrBaseLabel);
                    default -> throw new IllegalStateException();
                };
            case PROJCRS, PROJECTEDCRS, PROJCS -> projectedCrs(crsLabel, delimiter, crsName, comma, datumOrBaseLabel);
            case ENGCRS, ENGINEERINGCRS, LOCAL_CS ->
                switch (datumOrBaseLabel.getSemantics()) {
                    case EDATUM, ENGINEERINGDATUM, LOCAL_DATUM ->
                        engineeringCrs(crsLabel, delimiter, crsName, comma, datumOrBaseLabel);
                    case BASEPROJCRS, BASEGEODCRS, BASEENGCRS ->
                        derivedEngineeringCrs(crsLabel, delimiter, crsName, comma, datumOrBaseLabel);
                    default -> throw new IllegalStateException();
                };
            case VERTCRS, VERTICALCRS, VERT_CS ->
                switch (datumOrBaseLabel.getSemantics()) {
                    case VDATUM, VERTICALDATUM, VERT_DATUM ->
                        verticalCrs(crsLabel, delimiter, crsName, comma, datumOrBaseLabel);
                    case BASEVERTCRS -> derivedVerticalCrs(crsLabel, delimiter, crsName, comma, datumOrBaseLabel);
                    default -> throw new IllegalStateException();
                };
            case PARAMETRICCRS ->
                switch (datumOrBaseLabel.getSemantics()) {
                    case PDATUM, PARAMETRICDATUM ->
                        parametricCrs(crsLabel, delimiter, crsName, comma, datumOrBaseLabel);
                    case BASEPARAMCRS -> derivedParametricCrs(crsLabel, delimiter, crsName, comma, datumOrBaseLabel);
                    default -> throw new IllegalStateException();
                };
            case TIMECRS ->
                switch (datumOrBaseLabel.getSemantics()) {
                    case TDATUM, TIMEDATUM -> temporalCrs(crsLabel, delimiter, crsName, comma, datumOrBaseLabel);
                    case BASETIMECRS -> derivedTemporalCrs(crsLabel, delimiter, crsName, comma, datumOrBaseLabel);
                    default -> throw new IllegalStateException();
                };
            case COMPOUNDCRS -> compoundCrs();
            default -> throw new IllegalStateException();
        };
    }

    public HorizontalCrs horizontalCrs() throws LanguageException {
        return horizontalCrs(flushAndLexEnum(WktKeyword.class));
    }

    public HorizontalCrs horizontalCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return switch (label.getSemantics()) {
            case GEODCRS, GEODETICCRS, GEOCCS, GEOGCS -> geographic2dCrs(label);
            case PROJCRS, PROJECTEDCRS, PROJCS -> projectedCrs(label);
            case ENGCRS, ENGINEERINGCRS, LOCAL_CS -> engineeringCrs(label);
            default -> throw new IllegalStateException();
        };
    }

    public CompoundCrs compoundCrs() throws LanguageException {
        return compoundCrs(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class));
    }

    public CompoundCrs compoundCrs(final EnumLexeme<WktKeyword> label,
            final LeftDelimiter leftDelimiter, final QuotedLatinText name, final EnumLexeme<SpecialSymbol> comma,
            final EnumLexeme<WktKeyword> horizontalCrsLabel) throws LanguageException {

        final TokenBuilder<Token, CompoundCrs> builder = new CompoundCrsBuilder().list(label,
                leftDelimiter,
                name,
                comma,
                horizontalCrs(horizontalCrsLabel),
                flushAndLexEnum(SpecialSymbol.class));

        final EnumLexeme<WktKeyword> secondCrsLabel = flushAndLexEnum(WktKeyword.class);

        builder.list(
                switch (secondCrsLabel.getSemantics()) {
                    case VERTCRS, VERTICALCRS, VERT_CS -> verticalCrs(secondCrsLabel);
                    case PARAMETRICCRS -> parametricCrs(secondCrsLabel);
                    case TIMECRS -> temporalCrs(secondCrsLabel);
                    default -> throw new IllegalStateException();
                });

        if (comma()) {

            final EnumLexeme<SpecialSymbol> nextComma = lexEnum(SpecialSymbol.class);
            final EnumLexeme<WktKeyword> nextLabel = flushAndLexEnum(WktKeyword.class);

            switch (nextLabel.getSemantics()) {
                case TIMECRS -> builder.list(temporalCrs(nextLabel));
                default -> patternScopeExtentIdentifierRemark(builder, new Token[]{nextComma, nextLabel});
            }
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public SimpleCrsShell.ImageCrs imageCrs() throws LanguageException {

        // jetons éventuellement lus en trop lors du parsing du système de coordonnées
        final Token[] outCs = new Token[2];

        return build(patternScopeExtentIdentifierRemark(
                SimpleCrsShellBuilder.imageCrs().list(
                        flushAndLexEnum(WktKeyword.class),
                        flushAndLex(LeftDelimiter.class),
                        flushAndLex(QuotedLatinText.class),
                        flushAndLexEnum(SpecialSymbol.class),
                        imageDatum(),
                        flushAndLexEnum(SpecialSymbol.class),
                        spatialCoordinateSystem(outCs)),
                outCs)
                .list(lex(RightDelimiter.class)));
    }

    public ImageDatum imageDatum() throws LanguageException {

        final TokenBuilder<Token, ImageDatum> builder = new ImageDatumBuilder().list(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(PixelInCell.class));

        if (comma()) {
            builder.list(lexEnum(SpecialSymbol.class));

            final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

            builder.list(
                    switch (lex.getSemantics()) {
                        case ANCHOR -> anchor(lex);
                        default -> identifier(lex);
                    });
        }

        return build(patternIndentifiers(builder)
                .list(flushAndLex(RightDelimiter.class)));
    }

    public SimpleCrsShell.VerticalCrs verticalCrs() throws LanguageException {
        return verticalCrs(flushAndLexEnum(WktKeyword.class));
    }

    public SimpleCrsShell.VerticalCrs verticalCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return verticalCrs(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class));
    }

    public SimpleCrsShell.VerticalCrs verticalCrs(final EnumLexeme<WktKeyword> label, final LeftDelimiter leftDelimiter,
            final QuotedLatinText name, final EnumLexeme<SpecialSymbol> comma, final EnumLexeme<WktKeyword> datumLabel)
            throws LanguageException {

            // jetons éventuellement lus en trop lors du parsing du système de coordonnées
            final Token[] outCs = new Token[2];

            return build(patternScopeExtentIdentifierRemark(
                    SimpleCrsShellBuilder.verticalCrs().list(
                            label,
                            leftDelimiter,
                            name,
                            comma,
                            verticalDatum(datumLabel),
                            flushAndLexEnum(SpecialSymbol.class),
                            spatialCoordinateSystem(outCs)),
                    outCs)
                    .list(lex(RightDelimiter.class)));
    }

    public SimpleCrsShell.EngineeringCrs engineeringCrs() throws LanguageException {
        return engineeringCrs(flushAndLexEnum(WktKeyword.class));
    }

    public SimpleCrsShell.EngineeringCrs engineeringCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return engineeringCrs(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class));
    }

    public SimpleCrsShell.EngineeringCrs engineeringCrs(final EnumLexeme<WktKeyword> label,
            final LeftDelimiter leftDelimiter, final QuotedLatinText name, final EnumLexeme<SpecialSymbol> comma,
            final EnumLexeme<WktKeyword> datumLabel) throws LanguageException {

        // jetons éventuellement lus en trop lors du parsing du système de coordonnées
        final Token[] outCs = new Token[2];

        return build(patternScopeExtentIdentifierRemark(
                SimpleCrsShellBuilder.engineeringCrs().list(
                        label,
                        leftDelimiter,
                        name,
                        comma,
                        engineeringDatum(datumLabel),
                        flushAndLexEnum(SpecialSymbol.class),
                        spatialCoordinateSystem(outCs)),
                outCs)
                .list(lex(RightDelimiter.class)));
    }

    public SimpleCrsShell.ParametricCrs parametricCrs() throws LanguageException {
        return parametricCrs(flushAndLexEnum(WktKeyword.class));
    }

    public SimpleCrsShell.ParametricCrs parametricCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return parametricCrs(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class));
    }

    public SimpleCrsShell.ParametricCrs parametricCrs(final EnumLexeme<WktKeyword> label,
            final LeftDelimiter leftDelimiter, final QuotedLatinText name, final EnumLexeme<SpecialSymbol> comma,
            final EnumLexeme<WktKeyword> datumLabel) throws LanguageException {

        // jetons éventuellement lus en trop lors du parsing du système de coordonnées
        final Token[] outCs = new Token[2];

        return build(patternScopeExtentIdentifierRemark(
                SimpleCrsShellBuilder.parametricCrs().list(
                        label,
                        leftDelimiter,
                        name,
                        comma,
                        parametricDatum(datumLabel),
                        flushAndLexEnum(SpecialSymbol.class),
                        spatialCoordinateSystem(outCs)),
                outCs)
                .list(lex(RightDelimiter.class)));
    }

    public SimpleCrsShell.TemporalCrs temporalCrs() throws LanguageException {
        return temporalCrs(flushAndLexEnum(WktKeyword.class));
    }

    public SimpleCrsShell.TemporalCrs temporalCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return temporalCrs(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class));
    }

    public SimpleCrsShell.TemporalCrs temporalCrs(final EnumLexeme<WktKeyword> label,
            final LeftDelimiter leftDelimiter, final QuotedLatinText name, final EnumLexeme<SpecialSymbol> comma,
            final EnumLexeme<WktKeyword> datumLabel) throws LanguageException {

        // jetons éventuellement lus en trop lors du parsing du système de coordonnées
        final Token[] outCs = new Token[2];

        return build(patternScopeExtentIdentifierRemark(
                SimpleCrsShellBuilder.temporalCrs().list(
                        label,
                        leftDelimiter,
                        name,
                        comma,
                        temporalDatum(datumLabel),
                        flushAndLexEnum(SpecialSymbol.class),
                        spatialCoordinateSystem(outCs)),
                outCs)
                .list(lex(RightDelimiter.class)));
    }

    public NameAndAnchorDatum.VerticalDatum verticalDatum() throws LanguageException {
        return build(patternNameAndAnchorDatum(NameAndAnchorDatumBuilder.verticalDatum()));
    }

    public NameAndAnchorDatum.EngineeringDatum engineeringDatum() throws LanguageException {
        return build(patternNameAndAnchorDatum(NameAndAnchorDatumBuilder.engineeringDatum()));
    }

    public NameAndAnchorDatum.ParametricDatum parametricDatum() throws LanguageException {
        return build(patternNameAndAnchorDatum(NameAndAnchorDatumBuilder.parametricDatum()));
    }

    public NameAndAnchorDatum.VerticalDatum verticalDatum(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(patternNameAndAnchorDatum(label, NameAndAnchorDatumBuilder.verticalDatum()));
    }

    public NameAndAnchorDatum.EngineeringDatum engineeringDatum(final EnumLexeme<WktKeyword> label)
            throws LanguageException {
        return build(patternNameAndAnchorDatum(label, NameAndAnchorDatumBuilder.engineeringDatum()));
    }

    public NameAndAnchorDatum.ParametricDatum parametricDatum(final EnumLexeme<WktKeyword> label)
            throws LanguageException {
        return build(patternNameAndAnchorDatum(label, NameAndAnchorDatumBuilder.parametricDatum()));
    }

    public NameAndAnchorDatum.TemporalDatum temporalDatum() throws LanguageException {
        return temporalDatum(flushAndLexEnum(WktKeyword.class));
    }

    public NameAndAnchorDatum.TemporalDatum temporalDatum(final EnumLexeme<WktKeyword> label) throws LanguageException {

        final TokenBuilder<Token, NameAndAnchorDatum.TemporalDatum> builder
                = NameAndAnchorDatumBuilder.temporalDatum().list(
                        label,
                        flushAndLex(LeftDelimiter.class),
                        flushAndLex(QuotedLatinText.class));

        if (comma()) {
            builder.list(lexEnum(SpecialSymbol.class));

            final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

            builder.list(
                    switch (lex.getSemantics()) {
                        case TIMEORIGIN -> temporalOrigin(lex);
                        default -> identifier(lex);
                    });
        }

        return build(
                patternIndentifiers(builder)
                        .list(flushAndLex(RightDelimiter.class)));
    }

    public DerivedCrs.ProjectedCrs projectedCrs() throws LanguageException {
        return projectedCrs(flushAndLexEnum(WktKeyword.class));
    }

    public DerivedCrs.ProjectedCrs projectedCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return projectedCrs(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class));
    }

    public DerivedCrs.ProjectedCrs projectedCrs(final EnumLexeme<WktKeyword> label, final LeftDelimiter leftDelimiter,
            final QuotedLatinText name, final EnumLexeme<SpecialSymbol> comma,
            final EnumLexeme<WktKeyword> baseCrsLabel) throws LanguageException {

        // jetons éventuellement lus en trop lors du parsing de la projection
        final Token[] outProj = new Token[2];
        // jetons éventuellement lus en trop lors du parsing du système de coordonnées
        final Token[] outCs = new Token[2];

        final TokenBuilder<Token, DerivedCrs.ProjectedCrs> builder = DerivedCrsBuilder.projectedCrs().list(
                label,
                leftDelimiter,
                name,
                comma,
                baseGeodeticCrs(baseCrsLabel),
                flushAndLex(SpecialSymbol.COMMA),
                mapProjection(flushAndLexEnum(WktKeyword.class), outProj));

        if (outProj[0] == null && outProj[1] == null) {
            builder.list(
                    flushAndLex(SpecialSymbol.COMMA),
                    spatialCoordinateSystem(outCs));
        } else {
            builder.list(
                    outProj[0],
                    spatialCoordinateSystem((EnumLexeme<WktKeyword>) outProj[1], outCs));
        }
        return build(patternScopeExtentIdentifierRemark(
                builder,
                outCs)
                .list(lex(RightDelimiter.class)));
    }

    public GeodeticCrs.Geographic2DCrs geographic2dCrs() throws LanguageException {
        return geographic2dCrs(flushAndLexEnum(WktKeyword.class));
    }

    public GeodeticCrs.Geographic2DCrs geographic2dCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return geographic2dCrs(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class));
    }

    public GeodeticCrs.Geographic2DCrs geographic2dCrs(final EnumLexeme<WktKeyword> label,
            final LeftDelimiter leftDelimiter, final QuotedLatinText name, final EnumLexeme<SpecialSymbol> comma,
            final EnumLexeme<WktKeyword> datumLabel) throws LanguageException {

        // jetons éventuellement lus en trop lors du parsing du datum
        final Token[] outDatum = new Token[2];

        // jetons éventuellement lus en trop lors du parsing du système de coordonnées
        final Token[] outCs = new Token[2];

        final TokenBuilder<Token, GeodeticCrs.Geographic2DCrs> builder = GeodeticCrsBuilder.geographic2DCrs().list(
                label,
                leftDelimiter,
                name,
                comma,
                geodeticDatum(datumLabel, outDatum));

        if (outDatum[0] == null && outDatum[1] == null) {
            builder.list(
                flushAndLexEnum(SpecialSymbol.class),
                ellipsoidal2dCoordinateSystem(outCs));
        } else {
            builder.list(
                outDatum[0],
                ellipsoidal2dCoordinateSystem((EnumLexeme<WktKeyword>) outDatum[1], outCs));
        }

        return build(patternScopeExtentIdentifierRemark(builder, outCs)
                .list(lex(RightDelimiter.class)));
    }

    public GeodeticCrs geodeticCrs() throws LanguageException {
        return geodeticCrs(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class));
    }

    public GeodeticCrs geodeticCrs(final EnumLexeme<WktKeyword> label, final LeftDelimiter leftDelimiter,
            final QuotedLatinText name, final EnumLexeme<SpecialSymbol> comma, final EnumLexeme<WktKeyword> datumLabel)
            throws LanguageException {

        // jetons éventuellement lus en trop lors du parsing du datum
        final Token[] outDatum = new Token[2];

        // jetons éventuellement lus en trop lors du parsing du système de coordonnées
        final Token[] outCs = new Token[2];

        final TokenBuilder<Token, GeodeticCrs> builder = GeodeticCrsBuilder.geodeticCrs().list(
                label,
                leftDelimiter,
                name,
                comma,
                geodeticDatum(datumLabel, outDatum));

        if (outDatum[0] == null && outDatum[1] == null) {
            builder.list(
                flushAndLexEnum(SpecialSymbol.class),
                spatialCoordinateSystem(outCs));
        } else {
            builder.list(
                outDatum[0],
                spatialCoordinateSystem((EnumLexeme<WktKeyword>) outDatum[1], outCs));
        }

        return build(patternScopeExtentIdentifierRemark(builder, outCs)
                .list(lex(RightDelimiter.class)));
    }

    public GeodeticDatum geodeticDatum(final Token[] out) throws LanguageException {
        return geodeticDatum(flushAndLexEnum(WktKeyword.class), out);
    }

    private GeodeticDatum geodeticDatum(final EnumLexeme<WktKeyword> label, final Token[] out)
            throws LanguageException {

        final TokenBuilder<Token, GeodeticDatum> builder = new GeodeticDatumBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                ellipsoid());

        if (comma()) {
            builder.list(lexEnum(SpecialSymbol.class));

            final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

            builder.list(
                    switch (lex.getSemantics()) {
                        case ANCHOR -> anchor(lex);
                        default -> identifier(lex);
                    });
        }

        patternIndentifiers(builder)
                .list(flushAndLex(RightDelimiter.class));

        if (comma()) {
            final EnumLexeme<SpecialSymbol> comma = flushAndLexEnum(SpecialSymbol.class);

            /*
            Comme on est à l'extérieur du délimiteur et que le méridien premier est optionnel, on n'est pas certain
            de commencer à lire un méridien premier. On peut très bien commencer à lire la suite d'un CRS en étant
            déjà sorti du datum. Si c'est le cas, il faut retourner les éléments déjà lus pour que le CRS puisse les
            utiliser. On est certains néanmoins de lire un mot-clef après la virgule car d'après la position d'un
            méridien permier, il est forcément suivi d'une virgule elle-même suivie d'un CS qui commence par un
            mot-clef.
            */
            final EnumLexeme<WktKeyword> pmLabel = flushAndLexEnum(WktKeyword.class);

            switch (pmLabel.getSemantics()) {
                case PRIMEM, PRIMEMERIDIAN -> builder.list(
                        comma,
                        primeMeridian(pmLabel));
                default -> {
                    out[0] = comma;
                    out[1] = pmLabel;
                }
            }
        }

        return build(builder);
    }

    public Ellipsoid ellipsoid() throws LanguageException {

        final TokenBuilder<Token, Ellipsoid> builder = new EllipsoidBuilder().list(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                numberParser.signedNumericLiteral(),
                flushAndLexEnum(SpecialSymbol.class),
                numberParser.signedNumericLiteral());

        if (comma()) {
            builder.list(flushAndLexEnum(SpecialSymbol.class));

            final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

            builder.list(
                    switch (lex.getSemantics()) {
                        case ID, AUTHORITY -> identifier(lex);
                        default -> lengthUnit(lex);
                    });

            patternIndentifiers(builder);
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public DerivedCrs.DerivedGeodeticCrs derivedGeodeticCrs() throws LanguageException {
        return derivedGeodeticCrs(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class));
    }

    public DerivedCrs.DerivedGeodeticCrs derivedGeodeticCrs(final EnumLexeme<WktKeyword> label,
            final LeftDelimiter leftDelimiter, final QuotedLatinText name, final EnumLexeme<SpecialSymbol> comma,
            final EnumLexeme<WktKeyword> baseCrsLabel) throws LanguageException {

        // jetons éventuellement lus en trop lors du parsing du système de coordonnées
        final Token[] outCs = new Token[2];

        return build(patternScopeExtentIdentifierRemark(
                DerivedCrsBuilder.derivedGeodeticCrs().list(
                        label,
                        leftDelimiter,
                        name,
                        comma,
                        baseGeodeticCrs(baseCrsLabel),
                        flushAndLexEnum(SpecialSymbol.class),
                        derivingConversion(),
                        flushAndLexEnum(SpecialSymbol.class),
                        spatialCoordinateSystem(outCs)),
                outCs)
                .list(lex(RightDelimiter.class)));
    }

    public DerivedCrs.DerivedVerticalCrs derivedVerticalCrs() throws LanguageException {
        return derivedVerticalCrs(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class));
    }

    public DerivedCrs.DerivedVerticalCrs derivedVerticalCrs(final EnumLexeme<WktKeyword> label,
            final LeftDelimiter leftDelimiter, final QuotedLatinText name, final EnumLexeme<SpecialSymbol> comma,
            final EnumLexeme<WktKeyword> baseCrsLabel) throws LanguageException {

        // jetons éventuellement lus en trop lors du parsing du système de coordonnées
        final Token[] outCs = new Token[2];

        return build(patternScopeExtentIdentifierRemark(
                DerivedCrsBuilder.derivedVerticalCrs().list(
                        label,
                        leftDelimiter,
                        name,
                        comma,
                        baseVerticalCrs(baseCrsLabel),
                        flushAndLexEnum(SpecialSymbol.class),
                        derivingConversion(),
                        flushAndLexEnum(SpecialSymbol.class),
                        spatialCoordinateSystem(outCs)),
                outCs)
                .list(lex(RightDelimiter.class)));
    }

    public DerivedCrs.DerivedEngineeringCrs<? extends BaseCrs> derivedEngineeringCrs() throws LanguageException {
        return derivedEngineeringCrs(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class));
    }

    public DerivedCrs.DerivedEngineeringCrs<? extends BaseCrs> derivedEngineeringCrs(final EnumLexeme<WktKeyword> label,
            final LeftDelimiter leftDelimiter, final QuotedLatinText name, final EnumLexeme<SpecialSymbol> comma,
            final EnumLexeme<WktKeyword> baseLabel) throws LanguageException {

        // jetons éventuellement lus en trop lors du parsing du système de coordonnées
        final Token[] outCs = new Token[2];

        return build(patternScopeExtentIdentifierRemark(
                DerivedCrsBuilder.derivedEngineeringCrs(baseEngineeringCrsClass(baseLabel)).list(
                        label,
                        leftDelimiter,
                        name,
                        comma,
                        baseEngineeringCrs(baseLabel),
                        flushAndLexEnum(SpecialSymbol.class),
                        derivingConversion(),
                        flushAndLexEnum(SpecialSymbol.class),
                        spatialCoordinateSystem(outCs)),
                outCs)
                .list(lex(RightDelimiter.class)));
    }

    public DerivedCrs.DerivedParametricCrs derivedParametricCrs() throws LanguageException {
        return derivedParametricCrs(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class));
    }

    public DerivedCrs.DerivedParametricCrs derivedParametricCrs(final EnumLexeme<WktKeyword> label,
            final LeftDelimiter leftDelimiter, final QuotedLatinText name, final EnumLexeme<SpecialSymbol> comma,
            final EnumLexeme<WktKeyword> baseCrsLabel) throws LanguageException {

        // jetons éventuellement lus en trop lors du parsing du système de coordonnées
        final Token[] outCs = new Token[2];

        return build(patternScopeExtentIdentifierRemark(
                DerivedCrsBuilder.derivedParametricCrs().list(
                    label,
                    leftDelimiter,
                    name,
                    comma,
                    baseParametricCrs(baseCrsLabel),
                    flushAndLexEnum(SpecialSymbol.class),
                    derivingConversion(),
                    flushAndLexEnum(SpecialSymbol.class),
                    spatialCoordinateSystem(outCs)),
                outCs)
                .list(lex(RightDelimiter.class)));
    }

    public DerivedCrs.DerivedTemporalCrs derivedTemporalCrs() throws LanguageException {
        return derivedTemporalCrs(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class));
    }

    public DerivedCrs.DerivedTemporalCrs derivedTemporalCrs(final EnumLexeme<WktKeyword> label,
            final LeftDelimiter leftDelimiter, final QuotedLatinText name, final EnumLexeme<SpecialSymbol> comma,
            final EnumLexeme<WktKeyword> baseCrsLabel) throws LanguageException {

        // jetons éventuellement lus en trop lors du parsing du système de coordonnées
        final Token[] outCs = new Token[2];

        return build(patternScopeExtentIdentifierRemark(
                DerivedCrsBuilder.derivedTemporalCrs().list(
                    label,
                    leftDelimiter,
                    name,
                    comma,
                    baseTemporalCrs(baseCrsLabel),
                    flushAndLexEnum(SpecialSymbol.class),
                    derivingConversion(),
                    flushAndLexEnum(SpecialSymbol.class),
                    spatialCoordinateSystem(outCs)),
                outCs)
                .list(lex(RightDelimiter.class)));
    }

    public Parameter derivingOperationParameter() throws LanguageException {
        return derivingOrCoordinateOperationParameter(flushAndLexEnum(WktKeyword.class));
    }

    public Parameter derivingOrCoordinateOperationParameter(final EnumLexeme<WktKeyword> label)
            throws LanguageException {

        final TokenBuilder<Token, Parameter> builder = ParameterBuilder.projectionParameter().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                numberParser.signedNumericLiteral(),
                flushAndLexEnum(SpecialSymbol.class));

        final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

        builder.list(
                switch (lex.getSemantics()) {
                    case LENGTHUNIT, ANGLEUNIT, SCALEUNIT, TIMEUNIT, TEMPORALQUANTITY, PARAMETRICUNIT -> unit(lex);
                    default -> throw new IllegalStateException();
                });

        return build(patternIndentifiers(builder)
                .list(flushAndLex(RightDelimiter.class)));
    }

    public ParameterAbridged abridgedTransformationParameter() throws LanguageException {
        return abridgedTransformationParameter(flushAndLexEnum(WktKeyword.class));
    }

    public ParameterAbridged abridgedTransformationParameter(final EnumLexeme<WktKeyword> label)
            throws LanguageException {

        return build(patternIndentifiers(
                new ParameterAbridgedBuilder().list(
                        label,
                        flushAndLex(LeftDelimiter.class),
                        flushAndLex(QuotedLatinText.class),
                        flushAndLexEnum(SpecialSymbol.class),
                        numberParser.signedNumericLiteral()))
                .list(flushAndLex(RightDelimiter.class)));
    }

    public ParameterFile parameterFile() throws LanguageException {
        return parameterFile(flushAndLexEnum(WktKeyword.class));
    }

    public ParameterFile parameterFile(final EnumLexeme<WktKeyword> label) throws LanguageException {

        return build(patternIndentifiers(new ParameterFileBuilder().list(
                        label,
                        flushAndLex(LeftDelimiter.class),
                        flushAndLex(QuotedLatinText.class),
                        flushAndLexEnum(SpecialSymbol.class),
                        flushAndLex(QuotedLatinText.class)))
                .list(flushAndLex(RightDelimiter.class)));
    }

    public Parameter projectionParameter() throws LanguageException {
        return projectionParameter(flushAndLexEnum(WktKeyword.class));
    }

    public Parameter projectionParameter(final EnumLexeme<WktKeyword> label) throws LanguageException {

        final TokenBuilder<Token, Parameter> builder = ParameterBuilder.projectionParameter().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                numberParser.signedNumericLiteral());

        if (comma()) {
            builder.list(flushAndLexEnum(SpecialSymbol.class));

            final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

            switch (lex.getSemantics()) {
                case ID, AUTHORITY -> builder.list(identifier(lex));
                case LENGTHUNIT, ANGLEUNIT, SCALEUNIT -> builder.list(unit(lex));
                default -> {
                }
            }

            patternIndentifiers(builder);
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public Operation.MapProjection mapProjection() throws LanguageException {
        return mapProjection(flushAndLexEnum(WktKeyword.class), new Token[2]);
    }

    public Operation.MapProjection mapProjection(final EnumLexeme<WktKeyword> label, final Token[] out)
            throws LanguageException {

        final TokenBuilder<Token, Operation.MapProjection> builder = new OperationBuilder.MapProjectionBuilder();
        if (WktKeyword.CONVERSION.test(label)) {
            builder.list(label,
                    flushAndLex(LeftDelimiter.class),
                    flushAndLex(QuotedLatinText.class),
                    flushAndLexEnum(SpecialSymbol.class),
                    mapProjectionMethod());
        } else if (WktKeyword.PROJECTION.test(label)) { // backward compatibility
            builder.list(mapProjectionMethod(label));
        } else {
            throw unexpected(label).semantics(WktKeyword.CONVERSION, WktKeyword.PROJECTION).exception();
        }

        while (comma()) {
            final EnumLexeme<SpecialSymbol> comma = flushAndLexEnum(SpecialSymbol.class);
            builder.list(comma);

            final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

            switch (lex.getSemantics()) {
                case PARAMETER -> builder.list(projectionParameter(lex));
                case ID, AUTHORITY -> {
                    builder.list(identifier(lex));
                    break;
                }
                default -> {
                    if (WktKeyword.PROJECTION.test(label)) {
                        // backward compatibility : en l'absence de délimiteur on peut alors déborder sur le CS qui suit
                        out[0] = comma;
                        out[1] = lex;
                        break;
                    } else {
                        throw unexpected(lex).semantics(WktKeyword.PARAMETER, WktKeyword.ID, WktKeyword.AUTHORITY)
                                .exception();
                    }
                }
            }
        }

        if (WktKeyword.CONVERSION.test(label)) {
            return build(patternIndentifiers(builder)
                .list(flushAndLex(RightDelimiter.class)));
        } else if (WktKeyword.PROJECTION.test(label)) { // backward compatibility
            return build(patternIndentifiers(builder));
        }
        throw unexpected(label).semantics(WktKeyword.CONVERSION, WktKeyword.PROJECTION).exception();
    }

    public Operation.DerivingConversion derivingConversion() throws LanguageException {
        return derivingConversion(flushAndLexEnum(WktKeyword.class));
    }

    public Operation.DerivingConversion derivingConversion(final EnumLexeme<WktKeyword> label)
            throws LanguageException {

        final TokenBuilder<Token, Operation.DerivingConversion> builder
                = new OperationBuilder.DerivingConversionBuilder().list(
                        label,
                        flushAndLex(LeftDelimiter.class),
                        flushAndLex(QuotedLatinText.class),
                        flushAndLexEnum(SpecialSymbol.class),
                        operationMethod());

        while (comma()) {

            builder.list(flushAndLexEnum(SpecialSymbol.class));

            final EnumLexeme<WktKeyword> lex = flushAndLex(WktKeyword.PARAMETER, WktKeyword.PARAMETERFILE,
                    WktKeyword.ID, WktKeyword.AUTHORITY);

            switch (lex.getSemantics()) {
                case PARAMETER -> builder.list(derivingOrCoordinateOperationParameter(lex));
                case PARAMETERFILE -> builder.list(parameterFile(lex));
                case ID, AUTHORITY -> {
                    builder.list(identifier(lex));
                    break;
                }
                default -> throw new IllegalStateException();
            }
        }

        return build(patternIndentifiers(builder)
                .list(flushAndLex(RightDelimiter.class)));
    }

    public Operation.AbridgedTransformation abridgedTransformation() throws LanguageException {

        final TokenBuilder<Token, Operation.AbridgedTransformation> builder = new AbridgedTransformationBuilder().list(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                operationMethod());

        int status = 0;
        while (comma()) {

            builder.list(flushAndLexEnum(SpecialSymbol.class));

            final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

            if (WktKeyword.PARAMETER.test(lex) && status < 1) {
                builder.list(abridgedTransformationParameter(lex));
            } else if (WktKeyword.PARAMETERFILE.test(lex) && status < 1) {
                builder.list(parameterFile(lex));
            } else if (WktKeyword.INTERPOLATIONCRS.test(lex) && status < 1) {
                status = 1;
                builder.list(interpolationCrs(lex));
            } else if (WktKeyword.OPERATIONACCURACY.test(lex) && status < 2) {
                status = 2;
                builder.list(operationAccuracy(lex));
            } else if (WktKeyword.SCOPE.test(lex) && status < 3) {
                status = 3;
                builder.list(scope(lex));
            } else if (WktKeyword.isExtent(lex) && status < 4) {
                status = 3;
                builder.list(extent(lex));
            } else if ((WktKeyword.ID.test(lex) || WktKeyword.AUTHORITY.test(lex)) && status < 5) {
                status = 4;
                builder.list(identifier(lex));
            } else if (WktKeyword.REMARK.test(lex) && status < 6) {
                status = 6;
                builder.list(remark(lex));
            } else {
                throw new IllegalStateException();
            }
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public Operation.CoordinateOperation coordinateOperation() throws LanguageException {

        final TokenBuilder<Token, Operation.CoordinateOperation> builder = new CoordinateOperationBuilder().list(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                sourceCrs(),
                flushAndLexEnum(SpecialSymbol.class),
                targetCrs(),
                flushAndLexEnum(SpecialSymbol.class),
                operationMethod());

        int status = 0;
        while (comma()) {

            builder.list(flushAndLexEnum(SpecialSymbol.class));

            final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

            if (WktKeyword.PARAMETER.test(lex) && status < 1) {
                builder.list(derivingOrCoordinateOperationParameter(lex));
            } else if (WktKeyword.PARAMETERFILE.test(lex) && status < 1) {
                builder.list(parameterFile(lex));
            } else if (WktKeyword.INTERPOLATIONCRS.test(lex) && status < 1) {
                status = 1;
                builder.list(interpolationCrs(lex));
            } else if (WktKeyword.OPERATIONACCURACY.test(lex) && status < 2) {
                status = 2;
                builder.list(operationAccuracy(lex));
            } else if (WktKeyword.USAGE.test(lex) && status < 4) {
                status = 3;
                builder.list(usage(lex));
            } else if ((WktKeyword.ID.test(lex) || WktKeyword.AUTHORITY.test(lex)) && status < 5) {
                status = 4;
                builder.list(identifier(lex));
            } else if (WktKeyword.REMARK.test(lex) && status < 6) {
                status = 6;
                builder.list(remark(lex));
            } else {
                throw new IllegalStateException();
            }
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public BoundCrs boundCrs() throws LanguageException {

        final TokenBuilder<Token, BoundCrs> builder = new BoundCrsBuilder().list(
                flushAndLexEnum(WktKeyword.class),
                flushAndLex(LeftDelimiter.class),
                sourceCrs(),
                flushAndLexEnum(SpecialSymbol.class),
                targetCrs(),
                flushAndLexEnum(SpecialSymbol.class),
                abridgedTransformation());

        while (comma()) {

            builder.list(flushAndLexEnum(SpecialSymbol.class));

            final EnumLexeme<WktKeyword> lex = flushAndLex(WktKeyword.ID, WktKeyword.AUTHORITY, WktKeyword.REMARK);

            switch (lex.getSemantics()) {
                case ID, AUTHORITY -> builder.list(identifier(lex));
                case REMARK -> {
                    builder.list(remark(lex));
                    break;
                }
                default -> throw new IllegalStateException();
            }
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public SpatialCoordinateSystem ellipsoidal2dCoordinateSystem(final Token[] out) throws LanguageException {
        return ellipsoidal2dCoordinateSystem(flushAndLexEnum(WktKeyword.class), out);
    }

    public SpatialCoordinateSystem ellipsoidal2dCoordinateSystem(final EnumLexeme<WktKeyword> label, final Token[] out)
            throws LanguageException {
        return patternSpatialCoordinateSystem(label,
                out,
                new SpatialCoordinateSystemBuilder.Ellipsoidal2DCoordinateSystemBuilder());
    }

    public SpatialCoordinateSystem spatialCoordinateSystem(final Token[] out) throws LanguageException {
        return spatialCoordinateSystem(flushAndLexEnum(WktKeyword.class), out);
    }

    public SpatialCoordinateSystem spatialCoordinateSystem(final EnumLexeme<WktKeyword> label, final Token[] out)
            throws LanguageException {
        return patternSpatialCoordinateSystem(label, out, new SpatialCoordinateSystemBuilder());
    }

    public TemporalCoordinateSystem temporalCoordinateSystem(final Token[] out) throws LanguageException {
        return temporalCoordinateSystem(flushAndLexEnum(WktKeyword.class), out);
    }

    public TemporalCoordinateSystem temporalCoordinateSystem(final EnumLexeme<WktKeyword> label, final Token[] out)
            throws LanguageException {

        final var builder = new TemporalCoordinateSystemBuilder();

        builder.list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLexEnum(CsType.class),
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLex(UnsignedInteger.class));

        patternIndentifiers(builder)
                .list(flushAndLex(RightDelimiter.class));

        final EnumLexeme<SpecialSymbol> key = flushAndLexEnum(SpecialSymbol.class);

        final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

        if (WktKeyword.AXIS.test(lex)) {
            builder.list(key, spatialTemporalAxis(lex));
        } else {
            // dans ce cas, on a dépassé la fin du CS, il faut récupérer les lexèmes lus en trop et sortir
            out[0] = key;
            out[1] = lex;
        }

        return build(builder);
    }

    public SpatialTemporalAxis spatialTemporalAxis() throws LanguageException {
        return spatialTemporalAxis(flushAndLexEnum(WktKeyword.class));
    }

    public SpatialTemporalAxis spatialTemporalAxis(final EnumLexeme<WktKeyword> label) throws LanguageException {

        Lexeme[] out = new Lexeme[2];

        final TokenBuilder<Token, SpatialTemporalAxis> builder = new SpatialTemporalAxisBuilder().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(AxisNameAbrev.class),
                flushAndLexEnum(SpecialSymbol.class),
                axisDirection(out));

        while (out[0] != null && out[1] != null) {
            builder.list(out[0]); // comma

            final EnumLexeme<WktKeyword> out1 = (EnumLexeme<WktKeyword>) out[1];
            out[0] = null;
            out[1] = null;


            switch (out1.getSemantics()) {
                case ORDER -> {
                    builder.list(axisOrder(out1));
                    out[0] = out[1] = null;
                }
                case AXISMINVALUE, AXISMAXVALUE, RANGEMEANING -> {
                    final Lexeme[] outOfRange = new Lexeme[2];
                    builder.list(axisRange(out1, outOfRange));
                    out = outOfRange;
                }
                case ID, AUTHORITY -> {
                    builder.list(identifier(out1));
                    out[0] = out[1] = null;
                }
                default -> {
                    builder.list(unit(out1));
                    out[0] = out[1] = null;
                }
            }
        }

        while (comma() || (out[0] != null && out[1] != null)) {

            final EnumLexeme<WktKeyword> lex;
            if (out[0] != null && out[1] != null) {
                builder.list(out[0]); // comma
                lex = (EnumLexeme<WktKeyword>) out[1];
            } else {
                builder.list(lexEnum(SpecialSymbol.class));
                lex = flushAndLexEnum(WktKeyword.class);
            }

            switch (lex.getSemantics()) {
                case ORDER -> {
                    builder.list(axisOrder(lex));
                    out[0] = out[1] = null;
                }
                case AXISMINVALUE, AXISMAXVALUE, RANGEMEANING -> {
                    final Lexeme[] outOfRange = new Lexeme[2];
                    builder.list(axisRange(lex, outOfRange));
                    out = outOfRange;
                }
                case ID, AUTHORITY -> {
                    builder.list(identifier(lex));
                    out[0] = out[1] = null;
                }
                default -> {
                    builder.list(unit(lex));
                    out[0] = out[1] = null;
                }
            }
        }

        return build(patternIndentifiers(builder)
                .list(flushAndLex(RightDelimiter.class)));
    }

    public AxisDirection axisDirection() throws LanguageException {
        return axisDirection(new Lexeme[2]);
    }

    public AxisDirection axisDirection(final Lexeme[] out) throws LanguageException {

        final TokenBuilder<Token, AxisDirection> builder = new AxisDirectionBuilder().list(
                flushAndLexEnum(Direction.class));

        // la direction peut éventuellement comporter un composant MERIDIAN ou BEARING
        if (comma()) {
            out[0] = lexEnum(SpecialSymbol.class);
            builder.list(out[0]);

            final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

            switch (lex.getSemantics()) {
                case MERIDIAN -> {
                    builder.list(meridian(lex));
                    out[0] = out[1] = null;
                }
                case BEARING -> {
                    builder.list(bearing(lex));
                    out[0] = out[1] = null;
                }
                default -> out[1] = lex; // si on a lu autre chose, alors c'est qu'on a dépassé la porté de la direction
            }
        }

        return build(builder);
    }

    public AxisOrder axisOrder() throws LanguageException {
        return axisOrder(flushAndLexEnum(WktKeyword.class));
    }

    public AxisOrder axisOrder(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(new AxisOrderBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(UnsignedInteger.class),
                flushAndLex(RightDelimiter.class)));
    }

    public AxisRange axisRange(final EnumLexeme<WktKeyword> label, final Lexeme[] out) throws LanguageException {

        final var builder = new AxisRangeBuilder();

        switch (label.getSemantics()) {
            case AXISMINVALUE -> builder.list(axisMinimumValue(label));
            case AXISMAXVALUE -> builder.list(axisMaximumValue(label));
            case RANGEMEANING -> builder.list(rangeMeaning(label));
            default -> {
            }
        }

        while (comma()) {
            out[0] = lexEnum(SpecialSymbol.class);

            final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

            switch (lex.getSemantics()) {
                case AXISMAXVALUE -> {
                    builder.list(out[0], axisMaximumValue(lex));
                    out[0] = out[1] = null;
                }
                case RANGEMEANING -> {
                    builder.list(out[0], rangeMeaning(lex));
                    out[0] = out[1] = null;
                }
                default -> {
                    out[1] = lex;
                }
            }
        }

        return build(builder);
    }

    public AxisRangeMeaning rangeMeaning() throws LanguageException {
        return rangeMeaning(flushAndLexEnum(WktKeyword.class));
    }

    public AxisRangeMeaning rangeMeaning(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(new AxisRangeMeaningBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLexEnum(RangeMeaningType.class),
                flushAndLex(RightDelimiter.class)));
    }

    public AxisMinimumValue axisMinimumValue() throws LanguageException {
        return axisMinimumValue(flushAndLexEnum(WktKeyword.class));
    }

    public AxisMinimumValue axisMinimumValue(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(new AxisMinimumValueBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                numberParser.signedNumericLiteral(),
                flushAndLex(RightDelimiter.class)));
    }

    public AxisMaximumValue axisMaximumValue() throws LanguageException {
        return axisMaximumValue(flushAndLexEnum(WktKeyword.class));
    }

    public AxisMaximumValue axisMaximumValue(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(new AxisMaximumValueBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                numberParser.signedNumericLiteral(),
                flushAndLex(RightDelimiter.class)));
    }

    public SimpleNumber.Bearing bearing() throws LanguageException {
        return bearing(flushAndLexEnum(WktKeyword.class));
    }

    public SimpleNumber.Bearing bearing(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(new SimpleNumberBuilder.BearingBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                numberParser.signedNumericLiteral(),
                flushAndLex(RightDelimiter.class)));
    }

    public SimpleNumber.Accuracy accuracy() throws LanguageException {
        return operationAccuracy(flushAndLexEnum(WktKeyword.class));
    }

    public SimpleNumber.Accuracy operationAccuracy(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(new SimpleNumberBuilder.AccuracyBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                numberParser.signedNumericLiteral(),
                flushAndLex(RightDelimiter.class)));
    }

    public Meridian meridian() throws LanguageException {
        return meridian(flushAndLexEnum(WktKeyword.class));
    }

    public Meridian meridian(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(new MeridianBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                numberParser.signedNumericLiteral(),
                flushAndLexEnum(SpecialSymbol.class),
                angleUnit(),
                flushAndLex(RightDelimiter.class)));
    }

    public BaseGeodeticCrs baseGeodeticCrs() throws LanguageException {
        return baseGeodeticCrs(flushAndLexEnum(WktKeyword.class));
    }

    private BaseGeodeticCrs baseGeodeticCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {

        final Token[] out = new Token[2];

        final TokenBuilder<Token, BaseGeodeticCrs> builder = new BaseGeodeticCrsBuilder().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                geodeticDatum(out));

        /*
        S'il y a une unité, elle a déjà forcément été lue lors du parsing du datum en l'absence de méridien premier
        car elle suit une virgule.
        */
        if (out[0] != null && out[1] != null) {
            builder.list(
                    out[0],
                    angleUnit((EnumLexeme<WktKeyword>) out[1]));
        } else if (comma()) {
            builder.list(
                    flushAndLexEnum(SpecialSymbol.class),
                    angleUnit(flushAndLexEnum(WktKeyword.class)));
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }


    public Class<? extends BaseCrs> baseEngineeringCrsClass(final EnumLexeme<WktKeyword> label)
            throws LanguageException {
        return switch (label.getSemantics()) {
            case BASEPROJCRS -> BaseProjectedCrs.class;
            case BASEGEODCRS -> BaseGeodeticCrs.class;
            case BASEENGCRS -> BaseCrs.BaseEngineeringCrs.class;
            default -> throw new IllegalStateException();
        };
    }

    public BaseCrs baseEngineeringCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return switch (label.getSemantics()) {
            case BASEPROJCRS -> baseProjectedCrs(label);
            case BASEGEODCRS -> baseGeodeticCrs(label);
            case BASEENGCRS -> base_engineering_crs(label);
            default -> throw new IllegalStateException();
        };
    }

    public BaseCrs.BaseVerticalCrs baseVerticalCrs() throws LanguageException {
        return baseVerticalCrs(flushAndLexEnum(WktKeyword.class));
    }

    public BaseCrs.BaseVerticalCrs baseVerticalCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(BaseDatumCrsBuilder.baseVerticalCrs().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                verticalDatum(),
                flushAndLex(RightDelimiter.class)));
    }

    public BaseCrs.BaseEngineeringCrs baseEngineeringCrs() throws LanguageException {
        return base_engineering_crs(flushAndLexEnum(WktKeyword.class));
    }

    public BaseCrs.BaseEngineeringCrs base_engineering_crs(final EnumLexeme<WktKeyword> label)
            throws LanguageException {
        return build(BaseDatumCrsBuilder.baseEngineeringCrs().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                engineeringDatum(),
                flushAndLex(RightDelimiter.class)));
    }

    public BaseProjectedCrs baseProjectedCrs() throws LanguageException {
        return baseProjectedCrs(flushAndLexEnum(WktKeyword.class));
    }

    public BaseProjectedCrs baseProjectedCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(new BaseProjectedCrsBuilder().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                baseGeodeticCrs(),
                flushAndLexEnum(SpecialSymbol.class),
                mapProjection(),
                flushAndLex(RightDelimiter.class)));
    }

    public BaseCrs.BaseParametricCrs baseParametricCrs() throws LanguageException {
        return baseParametricCrs(flushAndLexEnum(WktKeyword.class));
    }

    public BaseCrs.BaseParametricCrs baseParametricCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(BaseDatumCrsBuilder.baseParametricCrs().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                parametricDatum(),
                flushAndLex(RightDelimiter.class)));
    }

    public BaseCrs.BaseTemporalCrs baseTemporalCrs() throws LanguageException {
        return baseTemporalCrs(flushAndLexEnum(WktKeyword.class));
    }

    public BaseCrs.BaseTemporalCrs baseTemporalCrs(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(BaseDatumCrsBuilder.baseTemporalCrs().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                parametricDatum(),
                flushAndLex(RightDelimiter.class)));
    }

    public PrimeMeridian primeMeridian() throws LanguageException {
        return primeMeridian(flushAndLexEnum(WktKeyword.class));
    }

    public PrimeMeridian primeMeridian(final EnumLexeme<WktKeyword> label) throws LanguageException {

        final TokenBuilder<Token, PrimeMeridian> builder = new PrimeMeridianBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                numberParser.signedNumericLiteral());

        if (comma()) {
            builder.list(flushAndLexEnum(SpecialSymbol.class));

            final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

            builder.list(
                    switch (lex.getSemantics()) {
                        case ID, AUTHORITY -> identifier(lex);
                        default -> angleUnit(lex);
                    });

            patternIndentifiers(builder);
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public Unit unit() throws LanguageException {
        return unit(flushAndLexEnum(WktKeyword.class));
    }

    public Unit unit(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return switch (label.getSemantics()) {
            case ANGLEUNIT -> angleUnit(label);
            case LENGTHUNIT -> lengthUnit(label);
            case SCALEUNIT -> scaleUnit(label);
            case PARAMETRICUNIT -> parametricUnit(label);
            case TIMEUNIT, TEMPORALQUANTITY -> timeUnit(label);
            case UNIT -> abstractUnit(label);
            default -> throw new IllegalStateException();
        };
    }

    public Method.MapProjectionMethod mapProjectionMethod() throws LanguageException {
        return mapProjectionMethod(flushAndLexEnum(WktKeyword.class));
    }

    public Method.MapProjectionMethod mapProjectionMethod(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return patternMethod(label, new MethodBuilder.MapProjectionMethodBuilder());
    }

    public Method.OperationMethod operationMethod() throws LanguageException {
        return operationMethod(flushAndLexEnum(WktKeyword.class));
    }

    public Method.OperationMethod operationMethod(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return patternMethod(label, new MethodBuilder.OperationMethodBuilder());
    }

    public Unit abstractUnit() throws LanguageException {
        return abstractUnit(flushAndLexEnum(WktKeyword.class));
    }

    public Unit abstractUnit(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return patternSpaceUnit(label, new UnitBuilder<>());
    }

    public Unit.Time timeUnit() throws LanguageException {
        return timeUnit(flushAndLexEnum(WktKeyword.class));
    }

    public Unit.Time timeUnit(final EnumLexeme<WktKeyword> label) throws LanguageException {
        final UnitBuilder.TimeUnitBuilder builder = new UnitBuilder.TimeUnitBuilder();

        builder.list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class));

        if (comma()) {
            builder.list(lexEnum(SpecialSymbol.class));
            if (Character.isAlphabetic(flushTo())) {
                final EnumLexeme<WktKeyword> id = lex(WktKeyword.ID);
                builder.list(identifier(id));
            } else {
                builder.list(numberParser.unsignedNumericLiteral());
            }
        }

        return build(patternIndentifiers(builder)
                .list(flushAndLex(RightDelimiter.class)));
    }

    public Unit.Parametric parametricUnit() throws LanguageException {
        return parametricUnit(flushAndLexEnum(WktKeyword.class));
    }

    public Unit.Parametric parametricUnit(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return patternSpaceUnit(label, new UnitBuilder.ParametricUnitBuilder());
    }

    public Unit.Scale scaleUnit() throws LanguageException {
        return scaleUnit(flushAndLexEnum(WktKeyword.class));
    }

    public Unit.Scale scaleUnit(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return patternSpaceUnit(label, new UnitBuilder.ScaleUnitBuilder());
    }

    public Unit.Length lengthUnit() throws LanguageException {
        return lengthUnit(flushAndLexEnum(WktKeyword.class));
    }

    public Unit.Length lengthUnit(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return patternSpaceUnit(label, new UnitBuilder.LengthUnitBuilder());
    }

    public Unit.Angle angleUnit() throws LanguageException {
        return angleUnit(flushAndLexEnum(WktKeyword.class));
    }

    public Unit.Angle angleUnit(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return patternSpaceUnit(label, new UnitBuilder.AngleUnitBuilder());
    }

    public Identifier identifier() throws LanguageException {
        return identifier(flushAndLex(WktKeyword.ID, WktKeyword.AUTHORITY));
    }

    public Identifier identifier(final EnumLexeme<WktKeyword> label) throws LanguageException {

        final TokenBuilder<Token, Identifier> builder = new IdentifierBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class));

        final Lexeme idStart = flushAndLex();
        if (idStart instanceof UnsignedInteger) {
            builder.list(numberParser.signedNumericLiteral(idStart));
        } else if (idStart instanceof QuotedLatinText) {
            builder.list(idStart); // version
        }

        if (comma()) {
            builder.list(lexEnum(SpecialSymbol.class));

            final Lexeme optional = flushAndLex();
            if (optional instanceof UnsignedInteger) {
                builder.list(numberParser.signedNumericLiteral(optional));
            } else if (optional instanceof QuotedLatinText) {
                builder.list(optional); // version
            } else if (optional instanceof EnumLexeme keyword) {
                builder.list(
                        switch ((WktKeyword) keyword.getSemantics()) {
                            case CITATION -> citation(keyword);
                            case URI -> uri(keyword);
                            default -> throw unexpected(keyword).semantics(WktKeyword.CITATION, WktKeyword.URI)
                                    .exception();
                        });
            } else {
                throw unexpected(optional).types(UnsignedInteger.class, QuotedLatinText.class, WktKeyword.class)
                        .exception();
            }
        }

        if (comma()) {
            builder.list(lexEnum(SpecialSymbol.class));

            final EnumLexeme<WktKeyword> optional = flushAndLexEnum(WktKeyword.class);
            builder.list(
                switch (optional.getSemantics()) {
                    case CITATION -> citation(optional);
                    case URI -> uri(optional);
                    default -> throw unexpected(optional).semantics(WktKeyword.CITATION, WktKeyword.URI).exception();
                });
        }

        if (comma()) {
            builder.list(lexEnum(SpecialSymbol.class));

            final EnumLexeme<WktKeyword> optional = flushAndLexEnum(WktKeyword.class);
            builder.list(
                switch (optional.getSemantics()) {
                    case URI -> uri(optional);
                    default -> throw unexpected(optional).semantics(WktKeyword.CITATION, WktKeyword.URI).exception();
                });
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public Citation citation() throws LanguageException {
        return citation(flushAndLexEnum(WktKeyword.class));
    }

    public Citation citation(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(TaggedLatinTextBuilder.citation().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLex(RightDelimiter.class)));
    }

    public TimeOrigin temporalOrigin() throws LanguageException {
        return temporalOrigin(flushAndLexEnum(WktKeyword.class));
    }

    public TimeOrigin temporalOrigin(final EnumLexeme<WktKeyword> label) throws LanguageException {

        final TokenBuilder<Lexeme, TimeOrigin> builder = new TimeOriginBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class));

        if ('"' == flushTo()) {
            builder.list(lex(QuotedLatinText.class));
        } else {
            builder.list(dateTime());
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public Anchor anchor() throws LanguageException {
        return anchor(flushAndLexEnum(WktKeyword.class));
    }

    public Anchor anchor(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(TaggedLatinTextBuilder.anchor().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLex(RightDelimiter.class)));
    }

    public Uri uri() throws LanguageException {
        return uri(flushAndLexEnum(WktKeyword.class));
    }

    public Uri uri(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(TaggedLatinTextBuilder.uri().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLex(RightDelimiter.class)));
    }

    public Scope scope() throws LanguageException {
        return scope(flushAndLexEnum(WktKeyword.class));
    }

    public Scope scope(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(TaggedLatinTextBuilder.scope().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLex(RightDelimiter.class)));
    }

    public Remark remark() throws LanguageException {
        return remark(flushAndLexEnum(WktKeyword.class));
    }

    public Remark remark(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(new RemarkBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedUnicodeText.class),
                flushAndLex(RightDelimiter.class)));
    }

    public Extent extent() throws LanguageException {
        return extent(flushAndLexEnum(WktKeyword.class));
    }

    public Extent extent(final EnumLexeme<WktKeyword> initialLabel) throws LanguageException {

        final ExtentBuilder builder = new ExtentBuilder();

        EnumLexeme<WktKeyword> label = initialLabel;

        int status = 0;
        do {
            if (WktKeyword.AREA.test(label) && status < 1) {
                status = 1;
                builder.list(areaDescription(label));
            } else if (WktKeyword.BBOX.test(label) && status < 2) {
                status = 2;
                builder.list(geographicBoundingBox(label));
            } else if (WktKeyword.VERTICALEXTENT.test(label) && status < 3) {
                status = 3;
                builder.list(verticalExtent(label));
            } else if (WktKeyword.TIMEEXTENT.test(label) && status < 4) {
                status = 4;
                builder.list(temporalExtent(label));
            } else {
                throw new IllegalStateException();
            }

            if (comma()) {
                builder.list(flushAndLex(SpecialSymbol.COMMA));
                label = flushAndLexEnum(WktKeyword.class);
            } else {
                break;
            }
        } while (true);
        return build(builder);
    }

    public Usage usage() throws LanguageException {
        return usage(flushAndLexEnum(WktKeyword.class));
    }

    public Usage usage(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(new UsageBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                scope(),
                flushAndLex(SpecialSymbol.COMMA),
                extent(),
                flushAndLex(RightDelimiter.class)));
    }

    public Area areaDescription() throws LanguageException {
        return areaDescription(flushAndLexEnum(WktKeyword.class));
    }

    public Area areaDescription(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(TaggedLatinTextBuilder.area().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLex(RightDelimiter.class)));
    }

    public BBox geographicBoundingBox() throws LanguageException {
        return geographicBoundingBox(flushAndLexEnum(WktKeyword.class));
    }

    public BBox geographicBoundingBox(final EnumLexeme<WktKeyword> label) throws LanguageException {
        return build(new BBoxBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                numberParser.signedNumericLiteral(),
                flushAndLexEnum(SpecialSymbol.class),
                numberParser.signedNumericLiteral(),
                flushAndLexEnum(SpecialSymbol.class),
                numberParser.signedNumericLiteral(),
                flushAndLexEnum(SpecialSymbol.class),
                numberParser.signedNumericLiteral(),
                flushAndLex(RightDelimiter.class)));
    }

    public VerticalExtent verticalExtent() throws LanguageException {
        return verticalExtent(flushAndLexEnum(WktKeyword.class));
    }

    public VerticalExtent verticalExtent(final EnumLexeme<WktKeyword> label) throws LanguageException {

        final TokenBuilder<Token, VerticalExtent> builder = new VerticalExtentBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                numberParser.signedNumericLiteral(),
                flushAndLexEnum(SpecialSymbol.class),
                numberParser.signedNumericLiteral());

        if (comma()) {
            builder.list(flushAndLexEnum(SpecialSymbol.class),
                    lengthUnit());
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public TemporalExtent temporalExtent() throws LanguageException {
        return temporalExtent(flushAndLexEnum(WktKeyword.class));
    }

    public TemporalExtent temporalExtent(final EnumLexeme<WktKeyword> label) throws LanguageException {

        final TokenBuilder<Lexeme, TemporalExtent> builder = new TemporalExtentBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class));

        if ('"' == flushTo()) {
            builder.list(lex(QuotedLatinText.class));
        } else {
            builder.list(dateTime());
        }

        builder.list(flushAndLexEnum(SpecialSymbol.class));

        if ('"' == flushTo()) {
            builder.list(lex(QuotedLatinText.class));
        } else {
            builder.list(dateTime());
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    /**
     * <pre>
     * &lt;gregorian calendar date&gt; ::= &lt;year&gt; [&lt;hyphen&gt; &lt;month&gt; [&lt;hyphen&gt; &lt;day&gt;]]
     * &lt;gregorian ordinal date&gt; ::= &lt;year&gt; [&lt;hyphen&gt; &lt;ordinal day&gt;]
     * </pre>
     *
     * @return
     * @throws LanguageException
     */
    public GregorianDate gregorianDate() throws LanguageException {

        final TokenBuilder<Lexeme, GregorianDate> builder = new GregorianDateBuilder().list(
                flushAndLex(UnsignedInteger.class));

        for (int i = 0; i < 2; i++) {
            if ('-' == flushTo()) {
                builder.list(
                    flushAndLexEnum(SpecialSymbol.class),
                    flushAndLex(UnsignedInteger.class));
            }
        }

        return build(builder);
    }

    /**
     * <pre>
     * &lt;utc designator&gt; ::= Z
     * &lt;local time zone designator&gt; ::= {&lt;plus sign&gt; | &lt;minus sign&gt;} &lt;hour&gt;
     * [&lt;COLON&gt; &lt;minute&gt;]
     * </pre>
     *
     * @return
     * @throws LanguageException
     */
    public TimeZoneDesignator timeZoneDesignator() throws LanguageException {

        final TimeZoneDesignatorBuilder builder = new TimeZoneDesignatorBuilder();

        final EnumLexeme<SpecialSymbol> first = flushAndLexEnum(SpecialSymbol.class);
        builder.list(first);

        if (!SpecialSymbol.Z.test(first)) {
            builder.list(flushAndLex(UnsignedInteger.class));

            if (':' == flushTo()) {
                builder.list(
                        flushAndLexEnum(SpecialSymbol.class),
                        flushAndLex(UnsignedInteger.class));
            }
        }

        return build(builder);
    }

    public Clock clock() throws LanguageException {

        final TokenBuilder<Lexeme, Clock> builder = new ClockBuilder().list(
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLex(UnsignedInteger.class));

        if (':' == flushTo()) {
            builder.list(
                    flushAndLexEnum(SpecialSymbol.class),
                    flushAndLex(UnsignedInteger.class));

            if (':' == flushTo()) {
                builder.list(
                        flushAndLexEnum(SpecialSymbol.class),
                        dateTimeParser.seconds_value());
            }
        }

        return build(builder.list(timeZoneDesignator()));
    }

    public Datetime dateTime() throws LanguageException {

        final TokenBuilder<Lexeme, Datetime> builder = new DatetimeBuilder().list(gregorianDate());

        if ('T' == flushTo()) {
            builder.list(clock());
        }
        return build(builder);
    }

    @Override
    public Token parse() throws LanguageException {
        throw new UnsupportedOperationException();
    }

    private static final int ACCEPT_USAGE_ID_REMARK = 3;
    private static final int ACCEPT_ID_REMARK = 2;
    private static final int ACCEPT_REMARK = 1;

    private SpatialCoordinateSystem patternSpatialCoordinateSystem(
            final EnumLexeme<WktKeyword> label,
            final Token[] out,
            final SpatialCoordinateSystemBuilder builder) throws LanguageException {

        if (WktKeyword.CS.test(label)) {
            builder.list(
                    label,
                    flushAndLex(LeftDelimiter.class),
                    flushAndLexEnum(CsType.class),
                    flushAndLexEnum(SpecialSymbol.class),
                    flushAndLex(UnsignedInteger.class));

            patternIndentifiers(builder)
                    .list(flushAndLex(RightDelimiter.class));
        } else {

            if (WktKeyword.AXIS.test(label)) {
                builder.list(spatialTemporalAxis(label));
            } else if (WktKeyword.isUnit(label)) {
                builder.list(unit(label));
            } else {
                throw new IllegalStateException();
            }
        }

        while (comma()) {

            final EnumLexeme<SpecialSymbol> key = flushAndLexEnum(SpecialSymbol.class);

            final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

            if (WktKeyword.AXIS.test(lex)) {
                builder.list(key, spatialTemporalAxis(lex));
            } else if (WktKeyword.isUnit(lex)) {
                builder.list(key, unit(lex));
                break;
            } else {
                // dans ce cas, on a dépassé la fin du CS, il faut récupérer les lexèmes lus en trop et sortir
                out[0] = key;
                out[1] = lex;
                break;
            }
        }

        return build(builder);
    }

    private <M extends Method> M patternMethod(final EnumLexeme<WktKeyword> label, final MethodBuilder<M> builder)
            throws LanguageException {

        builder.list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class));

        return build(patternIndentifiers(builder)
                .list(flushAndLex(RightDelimiter.class)));
    }

    public <U extends Unit> U patternSpaceUnit(final EnumLexeme<WktKeyword> label, final UnitBuilder<U> builder)
            throws LanguageException {

        builder.list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class),
                flushAndLexEnum(SpecialSymbol.class),
                numberParser.unsignedNumericLiteral());

        return build(patternIndentifiers(builder)
                .list(flushAndLex(RightDelimiter.class)));
    }

    private <D extends NameAndAnchorDatum<Anchor>> TokenBuilder<Token, D> patternNameAndAnchorDatum(
            final NameAndAnchorDatumBuilder<D, Anchor> builder) throws LanguageException {
        return patternNameAndAnchorDatum(flushAndLexEnum(WktKeyword.class), builder);
    }

    private <D extends NameAndAnchorDatum<Anchor>> TokenBuilder<Token, D> patternNameAndAnchorDatum(
            final EnumLexeme<WktKeyword> label, final NameAndAnchorDatumBuilder<D, Anchor> builder)
            throws LanguageException {

        builder.list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedLatinText.class));

        if (comma()) {
            builder.list(lexEnum(SpecialSymbol.class));

            final EnumLexeme<WktKeyword> lex = flushAndLexEnum(WktKeyword.class);

            builder.list(
                    switch (lex.getSemantics()) {
                        case ANCHOR -> anchor(lex);
                        default -> identifier(lex);
                    });
        }

        return patternIndentifiers(builder)
                        .list(flushAndLex(RightDelimiter.class));
    }

    private <O extends Token> TokenBuilder<Token, O> patternIndentifiers(final TokenBuilder<Token, O> builder)
            throws LanguageException {

        while (comma()) {
            builder.list(
                    lexEnum(SpecialSymbol.class),
                    identifier());
        }
        return builder;
    }

    private <O extends Token> TokenBuilder<Token, O> patternScopeExtentIdentifierRemark(
            final TokenBuilder<Token, O> builder, final Token[] outCs) throws LanguageException {

        int accept = ACCEPT_USAGE_ID_REMARK;

        if (outCs[0] != null && outCs[1] != null) {
            /*
            Dans ce cas, c'est qu'on a déjà lu une virgule et un mot-clef à la suite du CS mais sans savoir a priori
            que ces éléments n'en faisaient pas partie.
            Il faut donc maintenant ajouter ces éléments dont on est sûr qu'ils ne font pas partie du CRS, puisqu'une
            virgule a été lue à la suite du CS et qu'on n'a donc pas rencontré le délimiteur de fin du CRS.
            */
            accept = patternScopeExtentIdentifierRemark(
                    (EnumLexeme<SpecialSymbol>) outCs[0],
                    (EnumLexeme<WktKeyword>) outCs[1],
                    builder, accept);
        }

        while (comma()) {

            accept = patternScopeExtentIdentifierRemark(
                flushAndLexEnum(SpecialSymbol.class),
                flushAndLexEnum(WktKeyword.class),
                builder, accept);
        }
        return builder;
    }

    private int patternScopeExtentIdentifierRemark(final EnumLexeme<SpecialSymbol> comma,
            final EnumLexeme<WktKeyword> lex, final TokenBuilder<Token, ?> builder, final int accept)
            throws LanguageException {

        builder.list(comma);

        return switch (lex.getSemantics()) {
            case USAGE -> {
                if (accept == ACCEPT_USAGE_ID_REMARK) {
                    builder.list(usage(lex));
                    yield ACCEPT_USAGE_ID_REMARK;
                }
                throw new IllegalStateException();
            }
            case ID, AUTHORITY -> {
                if (accept >= ACCEPT_ID_REMARK) {
                    builder.list(identifier(lex));
                    yield ACCEPT_ID_REMARK;
                }
                throw new IllegalStateException();
            }
            case REMARK -> {
                if (accept >= ACCEPT_REMARK) {
                    builder.list(remark(lex));
                    yield 0;
                }
                throw new IllegalStateException();
            }
            default -> throw new IllegalStateException();
        };
    }

    public static WktParser of(final String body) {
        return of(body, '[', ']');
    }

    public static WktParser of(final String body, final int leftDelimiter, final int rightDelimiter) {

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(body), new ArrayList<>(), leftDelimiter,
                rightDelimiter);
        lexer.initialize();

        return new WktParser(lexer);
    }
}
