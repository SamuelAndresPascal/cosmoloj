package com.cosmoloj.language.wkt.cts.parsing;

import com.cosmoloj.language.api.builder.TokenBuilder;
import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.parsing.AbstractPredictiveMappingUnpredictiveParser;
import com.cosmoloj.language.common.impl.parsing.DefaultStringScanner;
import com.cosmoloj.language.common.number.parsing.NumberParser;
import com.cosmoloj.language.wkt.cts.expression.Authority;
import com.cosmoloj.language.wkt.cts.expression.AuthorityBuilder;
import com.cosmoloj.language.wkt.cts.expression.Axis;
import com.cosmoloj.language.wkt.cts.expression.AxisBuilder;
import com.cosmoloj.language.wkt.cts.expression.CompdCs;
import com.cosmoloj.language.wkt.cts.expression.CompdCsBuilder;
import com.cosmoloj.language.wkt.cts.expression.ConcatMt;
import com.cosmoloj.language.wkt.cts.expression.ConcatMtBuilder;
import com.cosmoloj.language.wkt.cts.expression.CoordinateSystem;
import com.cosmoloj.language.wkt.cts.expression.Datum;
import com.cosmoloj.language.wkt.cts.expression.DatumBuilder;
import com.cosmoloj.language.wkt.cts.expression.FittedCs;
import com.cosmoloj.language.wkt.cts.expression.FittedCsBuilder;
import com.cosmoloj.language.wkt.cts.expression.GeocentricCs;
import com.cosmoloj.language.wkt.cts.expression.GeocentricCsBuilder;
import com.cosmoloj.language.wkt.cts.expression.GeographicCs;
import com.cosmoloj.language.wkt.cts.expression.GeographicCsBuilder;
import com.cosmoloj.language.wkt.cts.expression.InverseMt;
import com.cosmoloj.language.wkt.cts.expression.InverseMtBuilder;
import com.cosmoloj.language.wkt.cts.expression.LocalCs;
import com.cosmoloj.language.wkt.cts.expression.LocalCsBuilder;
import com.cosmoloj.language.wkt.cts.expression.LocalDatum;
import com.cosmoloj.language.wkt.cts.expression.MathTransform;
import com.cosmoloj.language.wkt.cts.expression.NameAndValueBuilder;
import com.cosmoloj.language.wkt.cts.expression.ParamMt;
import com.cosmoloj.language.wkt.cts.expression.ParamMtBuilder;
import com.cosmoloj.language.wkt.cts.expression.PassthroughMt;
import com.cosmoloj.language.wkt.cts.expression.PassthroughMtBuilder;
import com.cosmoloj.language.wkt.cts.expression.PrimeMeridian;
import com.cosmoloj.language.wkt.cts.expression.ProjectedCs;
import com.cosmoloj.language.wkt.cts.expression.ProjectedCsBuilder;
import com.cosmoloj.language.wkt.cts.expression.Projection;
import com.cosmoloj.language.wkt.cts.expression.ProjectionBuilder;
import com.cosmoloj.language.wkt.cts.expression.Spheroid;
import com.cosmoloj.language.wkt.cts.expression.SpheroidBuilder;
import com.cosmoloj.language.wkt.cts.expression.ToWgs84;
import com.cosmoloj.language.wkt.cts.expression.ToWgs84Builder;
import com.cosmoloj.language.wkt.cts.expression.Unit;
import com.cosmoloj.language.wkt.cts.expression.VertCs;
import com.cosmoloj.language.wkt.cts.expression.VertCsBuilder;
import com.cosmoloj.language.wkt.cts.expression.VertDatum;
import com.cosmoloj.language.wkt.cts.lexeme.AxisDirectionName;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.expression.Parameter;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import java.util.ArrayList;
import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;

/**
 *
 * @author Samuel Andrés
 */
public class WktParser extends AbstractPredictiveMappingUnpredictiveParser<WktLexer> {

    private final NumberParser<WktLexer> numberParser;

    public WktParser(final WktLexer lexer) {
        super(lexer);
        this.numberParser = new NumberParser<>(lexer, '.', 'E');
    }

    private boolean comma() throws LanguageException {
        return ',' == flushTo();
    }

    public Authority authority() throws LanguageException {
        return authority(flushAndLex(WktName.AUTHORITY));
    }

    private Authority authority(final EnumLexeme<WktName> label) throws LanguageException {
        return build(new AuthorityBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                flushAndLex(QuotedName.class),
                flushAndLex(RightDelimiter.class)));
    }

    public Unit unit() throws LanguageException {
        return unit(flushAndLex(WktName.UNIT));
    }

    private Unit unit(final EnumLexeme<WktName> label) throws LanguageException {

        final TokenBuilder<Token, Unit> builder = NameAndValueBuilder.unit().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral());

        return build(patternAuthorityThenClose(builder));
    }

    public VertDatum vertDatum() throws LanguageException {

        final TokenBuilder<Token, VertDatum> builder = NameAndValueBuilder.vertDatum().list(
                flushAndLex(WktName.VERT_DATUM),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral());

        return build(patternAuthorityThenClose(builder));
    }

    public LocalDatum localDatum() throws LanguageException {

        final TokenBuilder<Token, LocalDatum> builder = NameAndValueBuilder.localDatum().list(
                flushAndLex(WktName.LOCAL_DATUM),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral());

        return build(patternAuthorityThenClose(builder));
    }

    public ToWgs84 toWgs84() throws LanguageException {
        return toWgs84(flushAndLex(WktName.TOWGS84));
    }

    public ToWgs84 toWgs84(final EnumLexeme<WktName> label) throws LanguageException {
        return build(new ToWgs84Builder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                numberParser.signedNumericLiteral(),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral(),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral(),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral(),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral(),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral(),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral(),
                flushAndLex(RightDelimiter.class)));
    }

    public Axis axis() throws LanguageException {
        return axis(flushAndLex(WktName.AXIS));
    }

    public Axis axis(final EnumLexeme<WktName> label) throws LanguageException {
        return build(new AxisBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                flushAndLexEnum(AxisDirectionName.class),
                flushAndLex(RightDelimiter.class)));
    }

    public PrimeMeridian primeMeridian() throws LanguageException {

        final TokenBuilder<Token, PrimeMeridian> builder = NameAndValueBuilder.primeMeridian().list(
                flushAndLex(WktName.PRIMEM),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral());

        return build(patternAuthorityThenClose(builder));
    }

    public Parameter parameter() throws LanguageException {
        return parameter(flushAndLex(WktName.PARAMETER));
    }

    private Parameter parameter(final EnumLexeme<WktName> name) throws LanguageException {
        return build(NameAndValueBuilder.parameter().list(
                name,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral(),
                flushAndLex(RightDelimiter.class)));
    }

    public Projection projection() throws LanguageException {

        final TokenBuilder<Token, Projection> builder = new ProjectionBuilder().list(
                flushAndLex(WktName.PROJECTION),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class));

        return build(patternAuthorityThenClose(builder));
    }

    public Spheroid spheroid() throws LanguageException {

        final TokenBuilder<Token, Spheroid> builder = new SpheroidBuilder().list(
                flushAndLex(WktName.ELLIPSOID, WktName.SPHEROID),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral(),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral());

        return build(patternAuthorityThenClose(builder));
    }

    public Datum datum() throws LanguageException {

        final TokenBuilder<Token, Datum> builder = new DatumBuilder().list(flushAndLex(WktName.DATUM),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                spheroid());

        if (comma()) {
            final EnumLexeme<SpecialSymbol> comma = flushAndLex(SpecialSymbol.COMMA);
            final EnumLexeme<WktName> toWgsOrAuth = flushAndLex(WktName.TOWGS84, WktName.AUTHORITY);

            switch (toWgsOrAuth.getSemantics()) {
                case TOWGS84 -> {

                    builder.list(comma, toWgs84(toWgsOrAuth));

                    if (comma()) {
                        builder.list(
                                flushAndLex(SpecialSymbol.COMMA),
                                authority());
                    }

                }
                case AUTHORITY -> builder.list(comma, authority(toWgsOrAuth));
                default -> throw new IllegalStateException();
            }
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public GeocentricCs geocentricCs() throws LanguageException {
        return geocentricCs(flushAndLex(WktName.GEOCCS));
    }

    public GeocentricCs geocentricCs(final EnumLexeme<WktName> label) throws LanguageException {

        final TokenBuilder<Token, GeocentricCs> builder = new GeocentricCsBuilder().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                datum(),
                flushAndLex(SpecialSymbol.COMMA),
                primeMeridian(),
                flushAndLex(SpecialSymbol.COMMA),
                unit());

        return build(patternAxisThenAuthorityThenClose(builder, 3));
    }

    public GeographicCs geographicCs() throws LanguageException {
        return geographicCs(flushAndLex(WktName.GEOGCS));
    }

    public GeographicCs geographicCs(final EnumLexeme<WktName> label) throws LanguageException {

        final TokenBuilder<Token, GeographicCs> builder = new GeographicCsBuilder().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                datum(),
                flushAndLex(SpecialSymbol.COMMA),
                primeMeridian(),
                flushAndLex(SpecialSymbol.COMMA),
                unit());

        if (comma()) {

            builder.list(flushAndLex(SpecialSymbol.COMMA));
            final EnumLexeme<WktName> unitOrAxisOrAuthority1
                    = flushAndLex(WktName.UNIT, WktName.AXIS, WktName.AUTHORITY);

            switch (unitOrAxisOrAuthority1.getSemantics()) {
                case UNIT -> {
                    builder.list(unit(unitOrAxisOrAuthority1));

                    if (comma()) {
                        builder.list(flushAndLex(SpecialSymbol.COMMA));
                        final EnumLexeme<WktName> axisOrAuthority2 = flushAndLex(WktName.AXIS, WktName.AUTHORITY);

                        switch (axisOrAuthority2.getSemantics()) {
                            case AXIS -> {
                                builder.list(
                                        axis(axisOrAuthority2),
                                        flushAndLex(SpecialSymbol.COMMA),
                                        axis());

                                if (comma()) {
                                    builder.list(flushAndLex(SpecialSymbol.COMMA),
                                            authority());
                                }
                            }
                            case AUTHORITY -> builder.list(authority(axisOrAuthority2));
                            default -> throw new IllegalStateException();
                        }
                    }
                }
                case AXIS -> {
                    builder.list(
                            axis(unitOrAxisOrAuthority1),
                            flushAndLex(SpecialSymbol.COMMA),
                            axis());

                    if (comma()) {
                        builder.list(
                                flushAndLex(SpecialSymbol.COMMA),
                                authority());
                    }
                }
                case AUTHORITY -> builder.list(authority(unitOrAxisOrAuthority1));
                default -> throw new IllegalStateException();
            }
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public ProjectedCs projectedCs() throws LanguageException {
        return projectedCs(flushAndLex(WktName.PROJCS));
    }

    public ProjectedCs projectedCs(final EnumLexeme<WktName> label) throws LanguageException {

        final TokenBuilder<Token, ProjectedCs> builder = new ProjectedCsBuilder().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                geographicCs(),
                flushAndLex(SpecialSymbol.COMMA),
                projection(),
                flushAndLex(SpecialSymbol.COMMA));

        EnumLexeme<WktName> name = flushAndLex(WktName.PARAMETER, WktName.UNIT);
        while (WktName.PARAMETER.test(name)) {

            builder.list(
                    parameter(name),
                    flushAndLex(SpecialSymbol.COMMA));
            name = flushAndLex(WktName.PARAMETER, WktName.UNIT);
        }

        builder.list(unit(name));

        return build(patternAxisThenAuthorityThenClose(builder, 2));
    }

    public ParamMt paramMt() throws LanguageException {
        return paramMt(flushAndLex(WktName.PARAM_MT));
    }

    public ParamMt paramMt(final EnumLexeme<WktName> label) throws LanguageException {

        final TokenBuilder<Token, ParamMt> builder = new ParamMtBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class));

        while (comma()) {
            builder.list(
                    flushAndLex(SpecialSymbol.COMMA),
                    parameter());
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public InverseMt invMt() throws LanguageException {
        return invMt(flushAndLex(WktName.INVERSE_MT));
    }

    public InverseMt invMt(final EnumLexeme<WktName> label) throws LanguageException {
        return build(new InverseMtBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                mathTransform(),
                flushAndLex(RightDelimiter.class)));
    }

    public PassthroughMt passthroughMt() throws LanguageException {
        return passthroughMt(flushAndLex(WktName.PASSTHROUGH_MT));
    }

    public PassthroughMt passthroughMt(final EnumLexeme<WktName> label) throws LanguageException {
        return build(new PassthroughMtBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                numberParser.signedNumericLiteral(),
                flushAndLex(SpecialSymbol.COMMA),
                mathTransform(),
                flushAndLex(RightDelimiter.class)));
    }

    public ConcatMt concatMt() throws LanguageException {
        return concatMt(flushAndLex(WktName.CONCAT_MT));
    }

    public ConcatMt concatMt(final EnumLexeme<WktName> label) throws LanguageException {

        final TokenBuilder<Token, ConcatMt> builder = new ConcatMtBuilder().list(label,
                flushAndLex(LeftDelimiter.class),
                mathTransform());

        while (comma()) {
            builder.list(
                    flushAndLex(SpecialSymbol.COMMA),
                    mathTransform());
        }

        return build(builder.list(flushAndLex(RightDelimiter.class)));
    }

    public VertCs vertCs() throws LanguageException {
        return vertCs(flushAndLex(WktName.VERT_CS));
    }

    public VertCs vertCs(final EnumLexeme<WktName> label) throws LanguageException {

        final TokenBuilder<Token, VertCs> builder = new VertCsBuilder().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                vertDatum(),
                flushAndLex(SpecialSymbol.COMMA),
                unit());

        return build(patternAxisThenAuthorityThenClose(builder, 1));
    }

    public LocalCs localCs() throws LanguageException {
        return localCs(flushAndLex(WktName.LOCAL_CS));
    }

    public LocalCs localCs(final EnumLexeme<WktName> label) throws LanguageException {

        final TokenBuilder<Token, LocalCs> builder = new LocalCsBuilder().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                localDatum(),
                flushAndLex(SpecialSymbol.COMMA),
                unit(),
                flushAndLex(SpecialSymbol.COMMA),
                axis());

        return build(patternAxisThenAuthorityThenClose(builder, -1)); // pas de vérification du nombre d'axes
    }

    public FittedCs fittedCs() throws LanguageException {
        return fittedCs(flushAndLex(WktName.FITTED_CS));
    }

    public FittedCs fittedCs(final EnumLexeme<WktName> label) throws LanguageException {
        return build(new FittedCsBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                mathTransform(),
                flushAndLex(SpecialSymbol.COMMA),
                coordinateSystem(),
                flushAndLex(RightDelimiter.class)));
    }

    public CompdCs compdCs() throws LanguageException {
        return compdCs(flushAndLex(WktName.COMPD_CS));
    }

    public CompdCs compdCs(final EnumLexeme<WktName> label) throws LanguageException {

        final TokenBuilder<Token, CompdCs> builder = new CompdCsBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                coordinateSystem(),
                flushAndLex(SpecialSymbol.COMMA),
                coordinateSystem());

        return build(patternAuthorityThenClose(builder));
    }

    public CoordinateSystem coordinateSystem() throws LanguageException {
        final EnumLexeme<WktName> label = flushAndLex(WktName.GEOGCS, WktName.PROJCS, WktName.GEOCCS, WktName.VERT_CS,
                            WktName.COMPD_CS, WktName.FITTED_CS, WktName.LOCAL_CS);

        return switch (label.getSemantics()) {
            case GEOGCS -> geographicCs(label);
            case PROJCS -> projectedCs(label);
            case GEOCCS -> geocentricCs(label);
            case VERT_CS -> vertCs(label);
            case COMPD_CS -> compdCs(label);
            case FITTED_CS -> fittedCs(label);
            case LOCAL_CS -> localCs(label);
            default -> throw new IllegalStateException();
        };
    }

    public CoordinateSystem horzCs() throws LanguageException {
        final EnumLexeme<WktName> label = flushAndLex(WktName.GEOGCS, WktName.PROJCS);

        return switch (label.getSemantics()) {
            case GEOGCS -> geographicCs(label);
            case PROJCS -> projectedCs(label);
            default -> throw new IllegalStateException();
        };
    }

    public MathTransform mathTransform() throws LanguageException {
        final EnumLexeme<WktName> label = flushAndLex(WktName.PARAM_MT, WktName.CONCAT_MT, WktName.INVERSE_MT,
                            WktName.PASSTHROUGH_MT);

        return switch (label.getSemantics()) {
            case PARAM_MT -> paramMt(label);
            case CONCAT_MT -> concatMt(label);
            case INVERSE_MT -> invMt(label);
            case PASSTHROUGH_MT -> passthroughMt(label);
            default -> throw new IllegalStateException();
        };
    }

    /**
     * Une suite d'axes optionnels suivie d'une éventuelle autorité. Le motif se termine par un délimiteur droit.
     *
     * @param <T>
     * @param builder
     * @param axisCheck un entier strictement positif pour contrôler le nombre d'axes
     * @return
     * @throws LanguageException
     */
    private <T extends Token> TokenBuilder<Token, T> patternAxisThenAuthorityThenClose(
            final TokenBuilder<Token, T> builder, final int axisCheck) throws LanguageException {

        Lexeme lex = flushAndLex();
        int cpt = 0;

        while (SpecialSymbol.COMMA.test(lex)) {
            builder.list(lex);
            final EnumLexeme<WktName> axisOrAuthority = flushAndLex(WktName.AXIS, WktName.AUTHORITY);

            if (WktName.AXIS.test(axisOrAuthority)) {
                cpt++;
                if (axisCheck >= 0 && cpt > axisCheck) {
                    throw unexpected(lex).semantics(WktName.AXIS).exception();
                }
                builder.list(axis(axisOrAuthority));
                lex = flushAndLex();
            } else if (WktName.AUTHORITY.test(axisOrAuthority)) {

                if (axisCheck >= 0 && cpt != axisCheck && cpt != 0) {
                    throw unexpected(axisOrAuthority).semantics(WktName.AXIS).exception();
                }
                builder.list(authority(axisOrAuthority));
                lex = flushAndLex(RightDelimiter.class);
                break; // uniquement une autorité
            }
        }

        if (axisCheck >= 0 && cpt != axisCheck && cpt != 0) {
            throw unexpected(lex).semantics(WktName.AUTHORITY).exception();
        } else if (lex instanceof RightDelimiter) {
            return builder.list(lex);
        }
        throw unexpected(lex).types(RightDelimiter.class).exception();
    }

    private <T extends Token> TokenBuilder<Token, T> patternAuthorityThenClose(final TokenBuilder<Token, T> builder)
            throws LanguageException {

        final Lexeme lexeme = flushAndLex();

        if (SpecialSymbol.COMMA.test(lexeme)) {
            builder.list(
                    lexeme,
                    authority(),
                    flushAndLex(RightDelimiter.class));
        } else if (lexeme instanceof RightDelimiter) {
            builder.list(lexeme);
        } else {
            throw unexpected(lexeme)
                    .semantics(SpecialSymbol.COMMA)
                    .types(RightDelimiter.class)
                    .exception();
        }

        return builder;
    }

    @Override
    public Token parse() throws LanguageException {
        return coordinateSystem();
    }

    public static WktParser of(final String body) {
        return of(body, '[', ']');
    }

    public static WktParser of(final String body, final int leftDelimiter, final int rightDelimiter) {

        final WktLexer lexer = new WktLexer(new DefaultStringScanner(body), new ArrayList<>(),
                leftDelimiter, rightDelimiter);
        lexer.initialize();

        return new WktParser(lexer);
    }
}
