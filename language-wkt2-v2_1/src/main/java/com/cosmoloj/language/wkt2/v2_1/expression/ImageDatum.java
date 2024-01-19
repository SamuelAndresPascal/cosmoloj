package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.PixelInCell;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class ImageDatum extends AbstractExpression {

    private final QuotedLatinText name;
    private final EnumLexeme<PixelInCell> pixelInCell;
    private final Anchor anchor;
    private final List<Identifier> identifiers;

    public ImageDatum(final int start, final int end, final int index, final QuotedLatinText name,
            final EnumLexeme<PixelInCell> pixelInCell, final Anchor anchor, final List<Identifier> identifiers) {
        super(start, end, index);
        this.name = name;
        this.pixelInCell = pixelInCell;
        this.anchor = anchor;
        this.identifiers = identifiers;
    }

    public QuotedLatinText getName() {
        return this.name;
    }

    public EnumLexeme<PixelInCell> getPixelInCell() {
        return this.pixelInCell;
    }

    public Anchor getAnchor() {
        return this.anchor;
    }

    public List<Identifier> getIdentifiers() {
        return this.identifiers;
    }
}
