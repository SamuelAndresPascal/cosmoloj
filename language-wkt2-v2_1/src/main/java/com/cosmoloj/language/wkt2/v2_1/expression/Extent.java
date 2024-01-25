package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;

/**
 *
 * @author Samuel Andrés
 */
public abstract class Extent extends AbstractExpression {

    protected Extent(final int first, final int last, final int index) {
        super(first, last, index);
    }

    public static class Coumpound extends Extent {

        private final Area area;
        private final BBox bbox;
        private final VerticalExtent vertical;
        private final TemporalExtent temporal;

        public Coumpound(final int first, final int last, final int index, final Area area, final BBox bbox,
                final VerticalExtent vertical, final TemporalExtent temporal) {
            super(first, last, index);
            this.area = area;
            this.bbox = bbox;
            this.vertical = vertical;
            this.temporal = temporal;
        }

        public Area getArea() {
            return area;
        }

        public BBox getBbox() {
            return bbox;
        }

        public VerticalExtent getVertical() {
            return vertical;
        }

        public TemporalExtent getTemporal() {
            return temporal;
        }
    }
}
