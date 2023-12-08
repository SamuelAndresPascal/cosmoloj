package com.cosmoloj.util.sql;

import java.util.Objects;

public final class QueryBuilder implements Comparable<QueryBuilder>, CharSequence {

    private final StringBuilder sb;

    public QueryBuilder() {
        this.sb = new StringBuilder(16);
    }

    public QueryBuilder(final int capacity) {
        this.sb = new StringBuilder(capacity);
    }

    public QueryBuilder(final String str) {
        this.sb = new StringBuilder(str);
    }

    public QueryBuilder(final CharSequence seq) {
        this.sb = new StringBuilder(seq);
    }

    @Override
    public int compareTo(final QueryBuilder another) {
        return sb.compareTo(another.sb);
    }

    public QueryBuilder append(final Object obj) {
        if (obj instanceof SqlValue val) {
            sb.append(val.getValue());
        } else if (obj instanceof QueryExpression expr) {
            expr.accept(this);
        } else {
            sb.append(obj);
        }
        return this;
    }

    public QueryBuilder append(final String str) {
        sb.append(str);
        return this;
    }

    public QueryBuilder append(final CharSequence s) {
        sb.append(s);
        return this;
    }

    public QueryBuilder append(final CharSequence s, final int start, final int end) {
        sb.append(s, start, end);
        return this;
    }

    public QueryBuilder append(final char[] str) {
        sb.append(str);
        return this;
    }

    public QueryBuilder append(final char[] str, final int offset, final int len) {
        sb.append(str, offset, len);
        return this;
    }

    public QueryBuilder append(final boolean b) {
        sb.append(b);
        return this;
    }

    public QueryBuilder append(final char c) {
        sb.append(c);
        return this;
    }

    public QueryBuilder append(final int i) {
        sb.append(i);
        return this;
    }

    public QueryBuilder append(final long lng) {
        sb.append(lng);
        return this;
    }

    public QueryBuilder append(final float f) {
        sb.append(f);
        return this;
    }

    public QueryBuilder append(final double d) {
        sb.append(d);
        return this;
    }

    public QueryBuilder appendCodePoint(final int codePoint) {
        sb.appendCodePoint(codePoint);
        return this;
    }

    public QueryBuilder delete(final int start, final int end) {
        sb.delete(start, end);
        return this;
    }

    public QueryBuilder deleteCharAt(final int index) {
        sb.deleteCharAt(index);
        return this;
    }

    public QueryBuilder replace(final int start, final int end, final String str) {
        sb.replace(start, end, str);
        return this;
    }

    public QueryBuilder insert(final int index, final char[] str, final int offset, final int len) {
        sb.insert(index, str, offset, len);
        return this;
    }

    public QueryBuilder insert(final int offset, final Object obj) {
            sb.insert(offset, obj);
            return this;
    }

    public QueryBuilder insert(final int offset, final String str) {
        sb.insert(offset, str);
        return this;
    }

    public QueryBuilder insert(final int offset, final char[] str) {
        sb.insert(offset, str);
        return this;
    }

    public QueryBuilder insert(final int dstOffset, final CharSequence s) {
            sb.insert(dstOffset, s);
            return this;
    }

    public QueryBuilder insert(final int dstOffset, final CharSequence s, final int start, final int end) {
        sb.insert(dstOffset, s, start, end);
        return this;
    }

    public QueryBuilder insert(final int offset, final boolean b) {
        sb.insert(offset, b);
        return this;
    }

    public QueryBuilder insert(final int offset, final char c) {
        sb.insert(offset, c);
        return this;
    }

    public QueryBuilder insert(final int offset, final int i) {
        sb.insert(offset, i);
        return this;
    }

    public QueryBuilder insert(final int offset, final long l) {
        sb.insert(offset, l);
        return this;
    }

    public QueryBuilder insert(final int offset, final float f) {
        sb.insert(offset, f);
        return this;
    }

    public QueryBuilder insert(final int offset, final double d) {
        sb.insert(offset, d);
        return this;
    }

    public int indexOf(final String str) {
        return sb.indexOf(str);
    }

    public int indexOf(final String str, final int fromIndex) {
        return sb.indexOf(str, fromIndex);
    }

    public int lastIndexOf(final String str) {
        return sb.lastIndexOf(str);
    }

    public int lastIndexOf(final String str, final int fromIndex) {
        return sb.lastIndexOf(str, fromIndex);
    }

    public QueryBuilder reverse() {
        sb.reverse();
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    @Override
    public int length() {
        return sb.length();
    }

    @Override
    public char charAt(final int index) {
        return sb.charAt(index);
    }

    @Override
    public CharSequence subSequence(final int start, final int end) {
        return sb.subSequence(start, end);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.sb);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QueryBuilder other = (QueryBuilder) obj;
        if (!Objects.equals(this.sb, other.sb)) {
            return false;
        }
        return true;
    }

    public QueryBuilder projectDistinct(final Object... arg) {
        append("select distinct ");

        for (int i = 0; i < arg.length; i++) {
            if (i != 0) {
                append(", ");
            }
            append(arg[i]);
        }
        return this;
    }

    public QueryBuilder project(final Object... arg) {
        append("select ");

        for (int i = 0; i < arg.length; i++) {
            if (i != 0) {
                append(", ");
            }
            append(arg[i]);
        }
        return this;
    }

    public QueryBuilder from(final Root... arg) {
        append(" from ");
        for (int i = 0; i < arg.length; i++) {
            if (i != 0) {
                append(", ");
            }
            append(arg[i]);
        }
        return this;
    }

    public QueryBuilder innerJoin(final Root root) {
        append(" inner join ").append(root);
        return this;
    }

    public QueryBuilder innerJoin(final QueryExpression join) {
        append(" inner join (").append(join).append(")");
        return this;
    }

    public QueryBuilder leftJoin(final Root root) {
        append(" left join ").append(root);
        return this;
    }

    public QueryBuilder leftJoin(final QueryExpression join) {
        append(" left join (").append(join).append(")");
        return this;
    }

    public QueryBuilder rightJoin(final Root root) {
        append(" right join ").append(root);
        return this;
    }

    public QueryBuilder rightJoin(final QueryExpression join) {
        append(" right join (").append(join).append(")");
        return this;
    }

    public QueryBuilder innerJoin(final Root root, final QueryExpression on) {
        append(" inner join ").append(root).on(on);
        return this;
    }

    public QueryBuilder innerJoin(final QueryExpression join, final QueryExpression on) {
        append(" inner join (").append(join).append(")").on(on);
        return this;
    }

    public QueryBuilder leftJoin(final Root root, final QueryExpression on) {
        append(" left join ").append(root).on(on);
        return this;
    }

    public QueryBuilder leftJoin(final QueryExpression join, final QueryExpression on) {
        append(" left join (").append(join).append(")").on(on);
        return this;
    }

    public QueryBuilder rightJoin(final Root root, final QueryExpression on) {
        append(" right join ").append(root).on(on);
        return this;
    }

    public QueryBuilder rightJoin(final QueryExpression join, final QueryExpression on) {
        append(" right join (").append(join).append(")").on(on);
        return this;
    }

    public QueryBuilder ilike(final Object first, final Object second) {
        append(first).append(" ilike ").append(second);
        return this;
    }

    public QueryBuilder like(final Object first, final Object second) {
        append(first).append(" like ").append(second);
        return this;
    }

    public QueryBuilder equal(final Object first, final Object second) {
        append(first).append(" = ").append(second);
        return this;
    }

    public QueryBuilder notEqual(final Object first, final Object second) {
        append(first).append(" <> ").append(second);
        return this;
    }

    public QueryBuilder minus(final Object first, final Object second) {
        append(first).append(" - ").append(second);
        return this;
    }

    public QueryBuilder add(final Object first, final Object second) {
        append(first).append(" + ").append(second);
        return this;
    }

    public QueryBuilder div(final Object first, final Object second) {
        append(first).append(" / ").append(second);
        return this;
    }

    public QueryBuilder mult(final Object first, final Object second) {
        append(first).append(" * ").append(second);
        return this;
    }

    public QueryBuilder gt(final Object first, final Object second) {
        append(first).append(" > ").append(second);
        return this;
    }

    public QueryBuilder lt(final Object first, final Object second) {
        append(first).append(" < ").append(second);
        return this;
    }

    public QueryBuilder gte(final Object first, final Object second) {
        append(first).append(" >= ").append(second);
        return this;
    }

    public QueryBuilder lte(final Object first, final Object second) {
        append(first).append(" <= ").append(second);
        return this;
    }

    public QueryBuilder quote(final Object arg) {
        append('\'').append(arg).append('\'');
        return this;
    }

    public QueryBuilder conjunction(final QueryExpression... conjunctions) {
        for (int i = 0; i < conjunctions.length; i++) {
            append(i == 0 ? " (" : " and (");
            conjunctions[i].accept(this);
            append(")");
        }
        return this;
    }

    public QueryBuilder disjunction(final QueryExpression... disjuctions) {
        for (int i = 0; i < disjuctions.length; i++) {
            append(i == 0 ? " (" : " or (");
            disjuctions[i].accept(this);
            append(")");
        }
        return this;
    }

    public QueryBuilder select(final QueryExpression arg) {
        return append(" where (").append(arg).append(")");
    }

    public QueryBuilder on(final QueryExpression arg) {
        return append(" on (").append(arg).append(")");
    }

    public QueryBuilder orderBy(final Object... arg) {
        append(" order by (");
        for (int i = 0; i < arg.length; i++) {
            if (i != 0) {
                append(", ");
            }
            append(arg[i]);
        }
        return append(")");
    }

    public QueryBuilder in(final Object arg, final Object... values) {
        append(arg);
        append(" in (");
        for (int i = 0; i < values.length; i++) {
            if (i != 0) {
                append(", ");
            }
            append(values[i]);
        }
        return append(")");
    }

    public QueryBuilder max(final Object arg) {
        return append(" max(").append(arg).append(")");
    }

    public QueryBuilder block(final QueryExpression join) {
        append(" (").append(join).append(")");
        return this;
    }

    public QueryBuilder condition(final Object condition, final Object yes, final Object no) {
        append(" case when ").append(condition).append(" then ").append(yes).append(" else ").append(no).append(" end");
        return this;
    }
}
