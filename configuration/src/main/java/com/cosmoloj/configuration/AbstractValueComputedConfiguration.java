package com.cosmoloj.configuration;

/**
 *
 * @author Samuel Andrés
 */
public abstract class AbstractValueComputedConfiguration implements Configuration {

    protected abstract String computeParameterValue(String value);
}
