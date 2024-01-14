package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class BoundCrs extends AbstractExpression {

    private final OperationCrs.SourceCrs source;
    private final OperationCrs.TargetCrs target;
    private final Operation.AbridgedTransformation transformation;
    private final List<Identifier> identifiers;
    private final Remark remark;

    public BoundCrs(final int start, final int end, final int index, final OperationCrs.SourceCrs source,
            final OperationCrs.TargetCrs target, final Operation.AbridgedTransformation transformation,
            final List<Identifier> identifiers, final Remark remark) {
        super(start, end, index);
        this.source = source;
        this.target = target;
        this.transformation = transformation;
        this.identifiers = identifiers;
        this.remark = remark;
    }

    public OperationCrs.SourceCrs getSource() {
        return this.source;
    }

    public OperationCrs.TargetCrs getTarget() {
        return this.target;
    }

    public Operation.AbridgedTransformation getTransformation() {
        return transformation;
    }

    public List<Identifier> getIdentifiers() {
        return this.identifiers;
    }

    public Remark getRemark() {
        return this.remark;
    }
}
