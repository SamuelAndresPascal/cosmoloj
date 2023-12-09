package com.cosmoloj.language.api.exception;

/**
 *
 * @author Samuel Andrés
 */
public class LanguageRuntimeException extends RuntimeException {

    public LanguageRuntimeException(final String message) {
        super(message);
    }

    public LanguageRuntimeException(final Throwable cause) {
        super(cause);
    }

    public LanguageRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
