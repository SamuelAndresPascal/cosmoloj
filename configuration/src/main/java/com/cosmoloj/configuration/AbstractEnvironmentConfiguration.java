package com.cosmoloj.configuration;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public abstract class AbstractEnvironmentConfiguration extends AbstractSystemConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public String getParameterValue(final String key) {

        final String system = super.getParameterValue(key);

        // sinon, on cherche une variable d'environnement construite à partir du nom du paramètre
        final String env = System.getenv(key.toUpperCase(Locale.ROOT).replace('.', '_'));
        if (env != null) {
            LOG.debug("environment value for parameter {} : {}", key, env);
            return computeParameterValue(env);
        }

        return system;
    }
}
