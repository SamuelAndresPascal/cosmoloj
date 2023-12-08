package com.cosmoloj.configuration;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public abstract class AbstractFileConfiguration extends AbstractEnvironmentConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Properties root = new Properties();

    protected AbstractFileConfiguration(final String configurationPathKey, final String defaultConfigurationPath,
            final String configurationFileKey, final String defaultConfigurationFile) throws IOException {
        final String path = getParameterValue(configurationPathKey);
        final String file = getParameterValue(configurationFileKey);

        final Path configFile = Paths.get(
                path == null ? defaultConfigurationPath : path,
                file == null ? defaultConfigurationFile : file);
        LOG.info("configuration file resolved to {}", configFile);
        if (Files.exists(configFile)) {
            try (var is = Files.newInputStream(configFile, StandardOpenOption.READ)) {
                this.root.load(is);
            }
        } else {
            LOG.warn("no configuration file exists at {}", configFile);
        }
    }

    @Override
    public final String getParameterValue(final String key) {

        final String env = super.getParameterValue(key);

        // sinon, on va chercher dans le fichier de configuration s'il est défini
        final String conf = this.root.getProperty(key);
        if (conf != null) {
            LOG.debug("configuration value for parameter {} : {}", key, conf);
            return computeParameterValue(conf);
        }

        return env;
    }
}
