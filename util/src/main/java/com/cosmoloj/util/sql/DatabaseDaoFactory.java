package com.cosmoloj.util.sql;

import com.cosmoloj.configuration.Configurable;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public abstract class DatabaseDaoFactory extends DatabaseProperties implements DaoFactory {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected DatabaseDaoFactory(final String url, final Properties properties, final String schema) {
        super(url, properties, schema);
    }

    protected DatabaseDaoFactory(final String url, final String user, final String password, final String schema) {
        super(url, user, password, schema);
    }

    protected DatabaseDaoFactory(final Configurable configurable, final String urlConf, final String userConf,
            final String passwordConf, final String schemaConf) {
        super(configurable, urlConf, userConf, passwordConf, schemaConf);
    }

    protected DatabaseDaoFactory(final Configurable configurable) {
        super(configurable);
    }

    public <R> R mapInstance(final String query, final ResultSetMapper<R> mapper) throws DaoFactoryException {

        LOG.debug("query: {}", query);

        try (Connection connection = openConnection();
                Statement statement = connection.createStatement()) {

            final boolean execute = statement.execute(query);
            if (execute) {
                try (ResultSet resultSet = statement.getResultSet()) {
                    R result;
                    if (resultSet.next()) {
                        result = mapper.map(resultSet);
                    } else {
                        throw new DaoFactoryException("unexpected empty resultset");
                    }

                    if (resultSet.next()) {
                        throw new DaoFactoryException("unexpected not empty resultset");
                    }
                    return result;
                }
            } else {
                return null;
            }
        } catch (final SQLException ex) {
            LOG.warn("error while executing query", ex);
            throw new DaoFactoryException(ex);
        }
    }

    public <R> List<R> mapInstances(final String query, final ResultSetMapper<R> mapper) throws DaoFactoryException {

        LOG.debug("query: {}", query);

        try (Connection connection = openConnection();
                Statement statement = connection.createStatement()) {

            final boolean execute = statement.execute(query);
            if (execute) {
                try (ResultSet resultSet = statement.getResultSet()) {
                    final List<R> result = new ArrayList<>();
                    while (resultSet.next()) {
                        result.add(mapper.map(resultSet));
                    }
                    return result;
                }
            } else {
                return null;
            }
        } catch (final SQLException ex) {
            LOG.warn("error while executing query", ex);
            throw new DaoFactoryException(ex);
        }
    }

    public <R> List<R> buildInstances(final String query, final Class<R> type) throws DaoFactoryException {

        try (var connection = openConnection();
                var statement = connection.createStatement()) {
            final boolean execute = statement.execute(query);
            if (execute) {
                try (var resultSet = statement.getResultSet()) {
                    final List<R> result = new ArrayList<>();
                    while (resultSet.next()) {
                        result.add(type.getConstructor(IdentifiedDaoFactory.class, ResultSet.class)
                                .newInstance(this, resultSet));
                    }
                    return result;
                }
            } else {
                return List.of();
            }
        } catch (final SQLException | NoSuchMethodException | SecurityException | InstantiationException
                            | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LOG.warn("error while executing query", ex);
            throw new DaoFactoryException(ex);
        }
    }

    public <R> R buildInstance(final String query, final Class<R> type) throws DaoFactoryException {

        LOG.debug("query: {}", query);

        try (var connection = openConnection();
                var statement = connection.createStatement()) {
            final boolean execute = statement.execute(query);
            if (execute) {
                try (var resultSet = statement.getResultSet()) {
                    R result;
                    if (resultSet.next()) {
                        result = type.getConstructor(IdentifiedDaoFactory.class, ResultSet.class)
                                .newInstance(this, resultSet);
                    } else {
                        throw new DaoFactoryException("unexpected empty resultset");
                    }

                    if (resultSet.next()) {
                        throw new DaoFactoryException("unexpected not empty resultset");
                    }
                    return result;
                }
            } else {
                return null;
            }
        } catch (final SQLException | NoSuchMethodException | SecurityException | InstantiationException
                            | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LOG.warn("error while executing query", ex);
            throw new DaoFactoryException(ex);
        }
    }

    @Override
    public List<Object[]> project(final Class<?> type, final boolean withHeader, final String... others)
            throws DaoFactoryException {

        final var idFields = DaoFactory.getIdFields(type);

        final StringBuilder query = new StringBuilder("select ")
                .append(String.join(", ", idFields));

        if (others != null && others.length != 0) {
            query.append(", ");
            query.append(String.join(", ", others));
        }

        query.append(" from ").append(root(type).table());

        LOG.debug("query: {}", query);

        try (var connection = openConnection();
                var statement = connection.createStatement()) {
            final boolean execute = statement.execute(query.toString());
            if (execute) {
                final List<Object[]> result = new ArrayList<>();
                try (var resultSet = statement.getResultSet()) {
                    final int cols = resultSet.getMetaData().getColumnCount();
                    if (withHeader) {
                        final var arr = new Object[cols];
                        for (int i = 0; i < cols; i++) {
                            arr[i] = resultSet.getMetaData().getColumnName(i + 1);
                        }
                        result.add(arr);
                    }
                    while (resultSet.next()) {

                        final var arr = new Object[cols];
                        for (int i = 0; i < cols; i++) {
                            arr[i] = resultSet.getObject(i + 1);
                        }
                        result.add(arr);
                    }
                    return result;
                }
            } else {
                return null;
            }
        } catch (final SQLException ex) {
            LOG.warn("error while executing query", ex);
            throw new DaoFactoryException(ex);
        }
    }

    @Override
    public <R> List<R> where(final Class<R> type, final Predicate... clauses)
            throws DaoFactoryException {

        final StringBuilder query = new StringBuilder("select * from ").append(root(type).table()).append(" where");

        boolean firstClause = true;
        for (final Predicate clause : clauses) {

            if (firstClause) {
                firstClause = false;
            } else {
                query.append(" and");
            }

            query.append(" ").append(clause.toSql());
        }

        LOG.debug("query: {}", query);

        return buildInstances(query.toString(), type);
    }

    public final Root root(final Class<?> type) {
        return new Root(getSchema(), type(type));
    }

    public final Root root(final Class<?> type, final String as) {
        return new Root(getSchema(), type(type), as);
    }

    public static QueryExpression from(final Root... root) {
        return t -> t.from(root);
    }

    public static QueryExpression ilike(final Object first, final Object second) {
        return t -> t.ilike(first, second);
    }

    public static QueryExpression like(final Object first, final Object second) {
        return t -> t.like(first, second);
    }

    public static QueryExpression equal(final Object first, final Object second) {
        return t -> t.equal(first, second);
    }

    public static QueryExpression notEqual(final Object first, final Object second) {
        return t -> t.notEqual(first, second);
    }

    public static QueryExpression minus(final Object first, final Object second) {
        return t -> t.minus(first, second);
    }

    public static QueryExpression add(final Object first, final Object second) {
        return t -> t.add(first, second);
    }

    public static QueryExpression div(final Object first, final Object second) {
        return t -> t.div(first, second);
    }

    public static QueryExpression mult(final Object first, final Object second) {
        return t -> t.mult(first, second);
    }

    public static QueryExpression gt(final Object first, final Object second) {
        return t -> t.gt(first, second);
    }

    public static QueryExpression lt(final Object first, final Object second) {
        return t -> t.lt(first, second);
    }

    public static QueryExpression gte(final Object first, final Object second) {
        return t -> t.gte(first, second);
    }

    public static QueryExpression lte(final Object first, final Object second) {
        return t -> t.lte(first, second);
    }

    public static QueryExpression conjunction(final QueryExpression... arg) {
        return t -> t.conjunction(arg);
    }

    public static QueryExpression disjunction(final QueryExpression... arg) {
        return t -> t.disjunction(arg);
    }

    public static QueryExpression select(final QueryExpression arg) {
        return t -> t.select(arg);
    }

    public static QueryExpression projectDistinct(final Object... arg) {
        return t -> t.projectDistinct(arg);
    }

    public static QueryExpression project(final Object... arg) {
        return t -> t.project(arg);
    }

    public static QueryExpression on(final QueryExpression arg) {
        return t -> t.on(arg);
    }

    public static QueryExpression orderBy(final Object... arg) {
        return t -> t.orderBy(arg);
    }

    public static QueryExpression innerJoin(final Root root) {
        return t -> t.innerJoin(root);
    }

    public static QueryExpression leftJoin(final Root root) {
        return t -> t.leftJoin(root);
    }

    public static QueryExpression rightJoin(final Root root) {
        return t -> t.rightJoin(root);
    }

    public static QueryExpression innerJoin(final QueryExpression root) {
        return t -> t.innerJoin(root);
    }

    public static QueryExpression leftJoin(final QueryExpression root) {
        return t -> t.leftJoin(root);
    }

    public static QueryExpression rightJoin(final QueryExpression root) {
        return t -> t.rightJoin(root);
    }

    public static QueryExpression innerJoin(final Root root, final QueryExpression on) {
        return t -> t.innerJoin(root, on);
    }

    public static QueryExpression leftJoin(final Root root, final QueryExpression on) {
        return t -> t.leftJoin(root, on);
    }

    public static QueryExpression rightJoin(final Root root, final QueryExpression on) {
        return t -> t.rightJoin(root, on);
    }

    public static QueryExpression innerJoin(final QueryExpression root, final QueryExpression on) {
        return t -> t.innerJoin(root, on);
    }

    public static QueryExpression leftJoin(final QueryExpression root, final QueryExpression on) {
        return t -> t.leftJoin(root, on);
    }

    public static QueryExpression rightJoin(final QueryExpression root, final QueryExpression on) {
        return t -> t.rightJoin(root, on);
    }

    public static QueryExpression in(final Object arg, final Object... values) {
        return t -> t.in(arg, values);
    }

    public static QueryExpression quote(final Object arg) {
        return t -> t.quote(arg);
    }

    public static QueryExpression max(final Object arg) {
        return t -> t.max(arg);
    }

    public static QueryExpression block(final QueryExpression arg) {
        return t -> t.block(arg);
    }

    public static QueryExpression condition(final Object condition, final Object yes, final Object no) {
        return t -> t.condition(condition, yes, no);
    }
}
