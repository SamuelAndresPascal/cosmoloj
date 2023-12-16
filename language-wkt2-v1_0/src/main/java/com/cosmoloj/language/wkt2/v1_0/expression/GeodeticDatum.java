package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class GeodeticDatum extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof GeodeticDatum;

    private final QuotedLatinText name;
    private final Ellipsoid ellipsoid;
    private final Anchor anchor;
    private final List<Identifier> identifiers;
    private final PrimeMeridian primeMeridian;

    public GeodeticDatum(final int start, final int end, final int index, final QuotedLatinText name,
            final Ellipsoid ellipsoid, final Anchor anchor, final List<Identifier> identifiers,
            final PrimeMeridian primeMeridian) {
        super(start, end, index);
        this.name = name;
        this.ellipsoid = ellipsoid;
        this.anchor = anchor;
        this.identifiers = identifiers;
        this.primeMeridian = primeMeridian;
    }

    public QuotedLatinText getName() {
        return this.name;
    }

    public Ellipsoid getEllipsoid() {
        return this.ellipsoid;
    }

    public Anchor getAnchor() {
        return this.anchor;
    }

    public List<Identifier> getIdentifiers() {
        return this.identifiers;
    }

    public PrimeMeridian getPrimeMeridian() {
        return this.primeMeridian;
    }
}
