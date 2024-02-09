package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.AxisNameAbrev;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class OrdinalDateTimeAxis extends AbstractExpression {

    private final AxisNameAbrev nameAbrev;
    private final AxisDirection direction;
    private final AxisOrder order;
    private final AxisRange range;
    private final List<Identifier> identifiers;

    public OrdinalDateTimeAxis(final int start, final int end, final int index, final AxisNameAbrev nameAbrev,
            final AxisDirection direction, final AxisOrder order, final AxisRange range,
            final List<Identifier> identifiers) {
        super(start, end, index);
        this.nameAbrev = nameAbrev;
        this.direction = direction;
        this.order = order;
        this.range = range;
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

    public AxisRange getRange() {
        return this.range;
    }

    public List<Identifier> getIdentifiers() {
        return this.identifiers;
    }
}
