package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Expression;
import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public interface ScopeExtentIdentifierRemark extends Expression {

    List<Usage> getUsages();

    List<Identifier> getIdentifiers();

    Remark getRemark();

    class Default extends AbstractExpression implements ScopeExtentIdentifierRemark {

        private final List<Usage> usages;
        private final List<Identifier> identifiers;
        private final Remark remark;

        public Default(final int start, final int end, final int index, final List<Usage> usages,
                final List<Identifier> identifiers, final Remark remark) {
            super(start, end, index);
            this.usages = usages;
            this.identifiers = identifiers;
            this.remark = remark;
        }

        @Override
        public List<Usage> getUsages() {
            return this.usages;
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
