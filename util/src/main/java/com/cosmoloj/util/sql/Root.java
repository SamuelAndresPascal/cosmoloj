package com.cosmoloj.util.sql;

/**
 *
 * @author Samuel Andrés
 */
public class Root implements QueryExpression {

    private final String schema;
    private final String type;
    private final String as;

    public Root(final String schema, final String type, final String as) {
        this.schema = schema;
        this.type = type;
        this.as = as;
    }

    public Root(final String schema, final String type) {
        this(schema, type, null);
    }

    public String getType() {
        return as == null ? type : as;
    }

    public String getSchema() {
        return schema;
    }

    public String table() {
        if (as == null) {
            return schema + '.' + type;
        } else {
            return schema + '.' + type + " as " + as;
        }
    }

    public String column(final String column) {
        if (as == null) {
            return table() + '.' + column;
        } else {
            return as + '.' + column;
        }
    }

    @Override
    public void accept(final QueryBuilder t) {
        t.append(table());
    }
}
