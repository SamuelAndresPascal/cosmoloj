package com.cosmoloj.language.api.exception;

/**
 *
 * @author Samuel Andrés
 */
public class LanguageException extends Exception {

    public LanguageException() {
    }

    public LanguageException(final String message) {
        super(message);
    }

    public LanguageException(final Throwable cause) {
        super(cause);
    }

    public LanguageException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
