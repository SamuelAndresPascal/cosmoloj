package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.PixelInCell;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class ImageDatum extends AbstractExpression {

    public static final Predicate<Object> IMAGE_DATUM = t -> t instanceof ImageDatum;

    private final QuotedLatinText name;
    private final PixelInCell.Lexeme pixelInCell;
    private final Anchor anchor;
    private final List<Identifier> identifiers;

    public ImageDatum(final int start, final int end, final int index, final QuotedLatinText name,
            final PixelInCell.Lexeme pixelInCell, final Anchor anchor, final List<Identifier> identifiers) {
        super(start, end, index);
        this.name = name;
        this.pixelInCell = pixelInCell;
        this.anchor = anchor;
        this.identifiers = identifiers;
    }

    public QuotedLatinText getName() {
        return this.name;
    }

    public PixelInCell.Lexeme getPixelInCell() {
        return this.pixelInCell;
    }

    public Anchor getAnchor() {
        return this.anchor;
    }

    public List<Identifier> getIdentifiers() {
        return this.identifiers;
    }
}
