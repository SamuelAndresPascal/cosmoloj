package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;

/**
 *
 * @author Samuel Andrés
 */
public class BBox extends Extent {

    private final SignedNumericLiteral lowerLeftLatitude;
    private final SignedNumericLiteral lowerLeftLongitude;
    private final SignedNumericLiteral upperRightLatitude;
    private final SignedNumericLiteral upperRightLongitude;

    public BBox(final int start, final int end, final int index,
            final SignedNumericLiteral lowerLeftLatitude, final SignedNumericLiteral lowerLeftLongitude,
            final SignedNumericLiteral upperRightLatitude, final SignedNumericLiteral upperLeftLongitude) {
        super(start, end, index);
        this.lowerLeftLatitude = lowerLeftLatitude;
        this.lowerLeftLongitude = lowerLeftLongitude;
        this.upperRightLatitude = upperRightLatitude;
        this.upperRightLongitude = upperLeftLongitude;
    }

    public SignedNumericLiteral getLowerLeftLatitude() {
        return lowerLeftLatitude;
    }

    public SignedNumericLiteral getLowerLeftLongitude() {
        return lowerLeftLongitude;
    }

    public SignedNumericLiteral getUpperRightLatitude() {
        return upperRightLatitude;
    }

    public SignedNumericLiteral getUpperRightLongitude() {
        return upperRightLongitude;
    }
}
