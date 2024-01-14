package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.AxisNameAbrev;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Axis extends AbstractExpression {

    public static final Predicate<Object> INSTANCE_OF = t -> t instanceof Axis;

    private final AxisNameAbrev nameAbrev;
    private final AxisDirection direction;
    private final AxisOrder order;
    private final Unit unit;
    private final List<Identifier> identifiers;

    public Axis(final int start, final int end, final int index, final AxisNameAbrev nameAbrev,
            final AxisDirection direction, final AxisOrder order, final Unit unit,
            final List<Identifier> identifiers) {
        super(start, end, index);
        this.nameAbrev = nameAbrev;
        this.direction = direction;
        this.order = order;
        this.unit = unit;
        this.identifiers = identifiers;
    }

    public AxisNameAbrev getNameAbrev() {
        return this.nameAbrev;
    }

    public AxisDirection getDirection() {
        return this.direction;
    }

    public AxisOrder getOrder() {
        return this.order;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public List<Identifier> getIdentifiers() {
        return this.identifiers;
    }
}
