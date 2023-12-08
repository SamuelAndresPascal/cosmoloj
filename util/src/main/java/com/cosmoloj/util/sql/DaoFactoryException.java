package com.cosmoloj.util.sql;

/**
 *
 * @author Samuel Andrés
 */
public class DaoFactoryException extends Exception {

    public DaoFactoryException(final String message) {
        super(message);
    }

    public DaoFactoryException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DaoFactoryException(final Throwable cause) {
        super(cause);
    }

    protected DaoFactoryException(final String message, final Throwable cause,
                        final boolean enableSuppression,
                        final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
