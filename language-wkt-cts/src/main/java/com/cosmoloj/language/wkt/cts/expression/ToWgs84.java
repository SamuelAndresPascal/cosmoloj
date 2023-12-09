package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class ToWgs84 extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof ToWgs84;

    private final SignedNumericLiteral dx;
    private final SignedNumericLiteral dy;
    private final SignedNumericLiteral dz;
    private final SignedNumericLiteral ex;
    private final SignedNumericLiteral ey;
    private final SignedNumericLiteral ez;
    private final SignedNumericLiteral ppm;

    public ToWgs84(final int start, final int end, final int index,
            final SignedNumericLiteral dx, final SignedNumericLiteral dy, final SignedNumericLiteral dz,
            final SignedNumericLiteral ex, final SignedNumericLiteral ey, final SignedNumericLiteral ez,
            final SignedNumericLiteral ppm) {
        super(start, end, index);
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.ex = ex;
        this.ey = ey;
        this.ez = ez;
        this.ppm = ppm;
    }

    public SignedNumericLiteral getDx() {
        return dx;
    }

    public SignedNumericLiteral getDy() {
        return dy;
    }

    public SignedNumericLiteral getDz() {
        return dz;
    }

    public SignedNumericLiteral getEx() {
        return ex;
    }

    public SignedNumericLiteral getEy() {
        return ey;
    }

    public SignedNumericLiteral getEz() {
        return ez;
    }

    public SignedNumericLiteral getPpm() {
        return ppm;
    }
}
