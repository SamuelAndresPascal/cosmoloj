package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Expression;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public interface ScopeExtentIdentifierRemark extends Expression {

    Scope getScope();

    List<Extent> getExtents();

    List<Identifier> getIdentifiers();

    Remark getRemark();

    class Default extends AbstractExpression implements ScopeExtentIdentifierRemark {

        private final Scope scope;
        private final List<Extent> extents;
        private final List<Identifier> identifiers;
        private final Remark remark;

        public Default(final int start, final int end, final int index, final Scope scope,
                final List<Extent> extents, final List<Identifier> identifiers, final Remark remark) {
            super(start, end, index);
            this.scope = scope;
            this.extents = extents;
            this.identifiers = identifiers;
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
        public List<Identifier> getIdentifiers() {
            return this.identifiers;
        }

        @Override
        public Remark getRemark() {
            return this.remark;
        }
    }
}
