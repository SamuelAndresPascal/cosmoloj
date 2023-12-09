package com.cosmoloj.language.wkt.sf.parsing;

import com.cosmoloj.language.api.builder.TokenBuilder;
import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.parsing.AbstractPredictiveParser;
import com.cosmoloj.language.common.impl.parsing.DefaultStringScanner;
import com.cosmoloj.language.common.number.parsing.NumberParser;
import com.cosmoloj.language.wkt.sf.expression.Datum;
import com.cosmoloj.language.wkt.sf.expression.DatumBuilder;
import com.cosmoloj.language.wkt.sf.expression.GeoCsBuilder;
import com.cosmoloj.language.wkt.sf.expression.GeocentricCs;
import com.cosmoloj.language.wkt.sf.expression.GeographicCs;
import com.cosmoloj.language.wkt.sf.expression.NameAndValueBuilder;
import com.cosmoloj.language.wkt.sf.expression.Parameter;
import com.cosmoloj.language.wkt.sf.expression.PrimeMeridian;
import com.cosmoloj.language.wkt.sf.expression.ProjectedCs;
import com.cosmoloj.language.wkt.sf.expression.ProjCsBuilder;
import com.cosmoloj.language.wkt.sf.expression.Projection;
import com.cosmoloj.language.wkt.sf.expression.ProjectionBuilder;
import com.cosmoloj.language.wkt.sf.expression.SpatialReferenceSystem;
import com.cosmoloj.language.wkt.sf.expression.Spheroid;
import com.cosmoloj.language.wkt.sf.expression.SpheroidBuilder;
import com.cosmoloj.language.wkt.sf.expression.Unit;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import com.cosmoloj.language.wkt.sf.lexeme.WktName;
import java.util.ArrayList;

/**
 *
 * @author Samuel Andrés
 */
public class WktParser extends AbstractPredictiveParser<WktLexer> {

    private final NumberParser<WktLexer> numberParser;

    public WktParser(final WktLexer lexer) {
        super(lexer);
        this.numberParser = new NumberParser<>(lexer, '.', 'E');
    }

    public Unit unit() throws LanguageException {
        return unit(flushAndLex(WktName.UNIT));
    }

    private Unit unit(final WktName.Lexeme label) throws LanguageException {
        return build(NameAndValueBuilder.unit().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral(),
                flushAndLex(RightDelimiter.class)));
    }

    public PrimeMeridian primeMeridian() throws LanguageException {
        return build(NameAndValueBuilder.primeMeridian().list(
                flushAndLex(WktName.PRIMEM),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral(),
                flushAndLex(RightDelimiter.class)));
    }

    public Parameter parameter() throws LanguageException {
        return parameter(flushAndLex(WktName.PARAMETER));
    }

    private Parameter parameter(final WktName.Lexeme name) throws LanguageException {
        return build(NameAndValueBuilder.parameter().list(
                name,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral(),
                flushAndLex(RightDelimiter.class)));
    }

    public Projection projection() throws LanguageException {
        return build(new ProjectionBuilder().list(
                flushAndLex(WktName.PROJECTION),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(RightDelimiter.class)));
    }

    public Spheroid spheroid() throws LanguageException {
        return build(new SpheroidBuilder().list(
                flushAndLex(WktName.ELLIPSOID, WktName.SPHEROID),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral(),
                flushAndLex(SpecialSymbol.COMMA),
                numberParser.signedNumericLiteral(),
                flushAndLex(RightDelimiter.class)));
    }

    public Datum datum() throws LanguageException {
        return build(new DatumBuilder().list(
                flushAndLex(WktName.DATUM),
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                spheroid(),
                flushAndLex(RightDelimiter.class)));
    }

    public GeocentricCs geoccs() throws LanguageException {
        return geoccs(flushAndLex(WktName.GEOCCS));
    }

    public GeocentricCs geoccs(final WktName.Lexeme label) throws LanguageException {
        return build(GeoCsBuilder.geoc().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                datum(),
                flushAndLex(SpecialSymbol.COMMA),
                primeMeridian(),
                flushAndLex(SpecialSymbol.COMMA),
                unit(),
                flushAndLex(RightDelimiter.class)));
    }

    public GeographicCs geogcs() throws LanguageException {
        return geogcs(flushAndLex(WktName.GEOGCS));
    }

    public GeographicCs geogcs(final WktName.Lexeme label) throws LanguageException {

        final TokenBuilder<Token, GeographicCs> builder = GeoCsBuilder.geog().list(label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                datum(),
                flushAndLex(SpecialSymbol.COMMA),
                primeMeridian(),
                flushAndLex(SpecialSymbol.COMMA),
                unit());

        flush(); // tolérance des caractères d'intervalles avant la virgule éventuelle

        if (',' == codePoint()) {
            builder.list(
                    flushAndLex(SpecialSymbol.COMMA),
                    unit());
        }

        builder.list(flushAndLex(RightDelimiter.class));

        return build(builder);
    }

    public ProjectedCs projcs() throws LanguageException {
        return projcs(flushAndLex(WktName.PROJCS));
    }

    public ProjectedCs projcs(final WktName.Lexeme label) throws LanguageException {

        final TokenBuilder<Token, ProjectedCs> builder = new ProjCsBuilder().list(
                label,
                flushAndLex(LeftDelimiter.class),
                flushAndLex(QuotedName.class),
                flushAndLex(SpecialSymbol.COMMA),
                geogcs(),
                flushAndLex(SpecialSymbol.COMMA),
                projection(),
                flushAndLex(SpecialSymbol.COMMA));

        WktName.Lexeme name = flushAndLex(WktName.PARAMETER, WktName.UNIT);
        while (WktName.PARAMETER.test(name)) {
            builder.list(
                    parameter(name),
                    flushAndLex(SpecialSymbol.COMMA));
            name = flushAndLex(WktName.PARAMETER, WktName.UNIT);
        }

        builder.list(
                unit(name),
                flushAndLex(RightDelimiter.class));

        return build(builder);
    }

    public SpatialReferenceSystem coordinateSystem() throws LanguageException {
        final WktName.Lexeme label = flushAndLex(WktName.PROJCS, WktName.GEOGCS, WktName.GEOCCS);

        return switch (label.getSemantics()) {
            case PROJCS -> projcs(label);
            case GEOGCS -> geogcs(label);
            case GEOCCS -> geoccs(label);
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public Token parse() throws LanguageException {
        return coordinateSystem();
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
