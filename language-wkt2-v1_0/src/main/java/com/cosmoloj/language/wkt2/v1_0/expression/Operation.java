package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 * @param <M>
 * @param <P>
 */
public class Operation<M extends Method, P extends AbstractParam> extends AbstractExpression {

    private final QuotedLatinText name;
    private final M method;
    private final List<P> parameters;
    private final List<Identifier> identifiers;

    public Operation(final int start, final int end, final int index, final QuotedLatinText name,
            final M method, final List<P> parameters,
            final List<Identifier> identifiers) {
        super(start, end, index);
        this.name = name;
        this.method = method;
        this.parameters = parameters;
        this.identifiers = identifiers;
    }

    public QuotedLatinText getName() {
        return this.name;
    }

    public M getMethod() {
        return this.method;
    }

    public List<P> getParameters() {
        return this.parameters;
    }

    public List<Identifier> getIdentifiers() {
        return this.identifiers;
    }

    public static class MapProjection extends Operation<Method.MapProjectionMethod, Parameter> {

        public MapProjection(final int start, final int end, final int index, final QuotedLatinText name,
                final Method.MapProjectionMethod method, final List<Parameter> parameters,
                final List<Identifier> identifiers) {
            super(start, end, index, name, method, parameters, identifiers);
        }
    }

    public static class DerivingConversion extends Operation<Method.OperationMethod, AbstractParam> {

        public DerivingConversion(final int start, final int end, final int index, final QuotedLatinText name,
                final Method.OperationMethod method, final List<AbstractParam> parameters,
                final List<Identifier> identifiers) {
            super(start, end, index, name, method, parameters, identifiers);
        }
    }

    public static class CoordinateOperation extends Operation<Method.OperationMethod, AbstractParam>
            implements ScopeExtentIdentifierRemark {

        private final OperationCrs.SourceCrs source;
        private final OperationCrs.TargetCrs target;
        private final OperationCrs.InterpolationCrs interpolation;
        private final SimpleNumber.Accuracy accuracy;

        private final Scope scope;
        private final List<Extent> extents;
        private final Remark remark;

        public CoordinateOperation(final int start, final int end, final int index, final QuotedLatinText name,
                final OperationCrs.SourceCrs source, final OperationCrs.TargetCrs target,
                final Method.OperationMethod method, final List<AbstractParam> parameters,
                final OperationCrs.InterpolationCrs interpolation, final SimpleNumber.Accuracy accuracy,
                final Scope scope, final List<Extent> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, method, parameters, identifiers);
            this.source = source;
            this.target = target;
            this.interpolation = interpolation;
            this.accuracy = accuracy;
            this.scope = scope;
            this.extents = extents;
            this.remark = remark;
        }

        public OperationCrs.SourceCrs getSource() {
            return this.source;
        }

        public OperationCrs.TargetCrs getTarget() {
            return this.target;
        }

        public OperationCrs.InterpolationCrs getInterpolation() {
            return this.interpolation;
        }

        public SimpleNumber.Accuracy getAccuracy() {
            return this.accuracy;
        }

        @Override
        public Scope getScope() {
            return this.scope;
        }

        @Override
        public List<Extent> getExtents() {
            return this.extents;
        }

        @Override
        public Remark getRemark() {
            return this.remark;
        }
    }

    public static class AbridgedTransformation extends Operation<Method.OperationMethod, AbstractParam>
            implements ScopeExtentIdentifierRemark {

        private final Scope scope;
        private final List<Extent> extents;
        private final Remark remark;

        public AbridgedTransformation(final int start, final int end, final int index, final QuotedLatinText name,
                final Method.OperationMethod method, final List<AbstractParam> parameters,
                final Scope scope, final List<Extent> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, method, parameters, identifiers);
            this.scope = scope;
            this.extents = extents;
            this.remark = remark;
        }

        @Override
        public Scope getScope() {
            return this.scope;
        }

        @Override
        public List<Extent> getExtents() {
            return this.extents;
        }

        @Override
        public Remark getRemark() {
            return this.remark;
        }
    }
}
