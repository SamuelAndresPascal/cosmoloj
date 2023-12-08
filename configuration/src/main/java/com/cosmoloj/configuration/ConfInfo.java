package com.cosmoloj.configuration;

/**
 *
 * @author Samuel Andrés
 */
public record ConfInfo(String description, String defaultValue) {

    public static ConfInfo of(final String description, final String defaultValue) {
        return new ConfInfo(description, defaultValue);
    }

    public static ConfInfo desc(final String description) {
        return new ConfInfo(description, null);
    }

    public static ConfInfo empty() {
        return new ConfInfo(null, null);
    }
}
