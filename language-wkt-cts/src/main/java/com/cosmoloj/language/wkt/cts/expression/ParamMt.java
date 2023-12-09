package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.wkt.sf.expression.Parameter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class ParamMt extends MathTransform {

    private final QuotedName name;
    private final List<Parameter> parameters;

    public ParamMt(final int start, final int end, final int index, final QuotedName name,
            final List<Parameter> parameters) {
        super(start, end, index);
        this.name = name;
        this.parameters = parameters;
    }

    public QuotedName getName() {
        return this.name;
    }

    public List<Parameter> getParameters() {
        return this.parameters;
    }
}
