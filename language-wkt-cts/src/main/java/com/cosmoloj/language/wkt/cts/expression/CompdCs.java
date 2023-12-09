package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;

/**
 *
 * @author Samuel Andrés
 */
public class CompdCs extends CoordinateSystem {

    private final QuotedName name;
    private final CoordinateSystem head;
    private final CoordinateSystem tail;
    private final Authority authority;

    public CompdCs(final int start, final int end, final int index, final QuotedName name,
            final CoordinateSystem head, final CoordinateSystem tail, final Authority authority) {
        super(start, end, index);
        this.name = name;
        this.head = head;
        this.tail = tail;
        this.authority = authority;
    }

    @Override
    public QuotedName getName() {
        return this.name;
    }

    public CoordinateSystem getHead() {
        return this.head;
    }

    public CoordinateSystem getTail() {
        return this.tail;
    }

    public Authority getAuthority() {
        return this.authority;
    }
}
