package com.cosmoloj.configuration;

import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public abstract class AbstractSystemConfiguration extends AbstractValueComputedConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public String getParameterValue(final String key) {

        // sinon, on regarde si une propriété système est définie
        final String system = System.getProperty(key);
        if (system != null) {
            LOG.debug("system value for parameter {} : {}", key, system);
            return computeParameterValue(system);
        }
        return null;
    }
}
