package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class ParameterFile extends AbstractParam {

    private final QuotedLatinText fileName;

    public ParameterFile(final int start, final int end, final int index, final QuotedLatinText name,
            final QuotedLatinText fileName, final List<Identifier> identifiers) {
        super(start, end, index, name, identifiers);
        this.fileName = fileName;
    }

    public QuotedLatinText getFileName() {
        return fileName;
    }
}
