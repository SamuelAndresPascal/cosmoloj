package com.cosmoloj.configuration;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Samuel Andrés
 */
public class RecursivePropertyConfiguration extends AbstractFileConfiguration {

    private static final Pattern PROPERTY_PATTERN = Pattern.compile("\\$\\{([^${}]+)\\}");

    protected RecursivePropertyConfiguration(final String configurationPath, final String defaultConfigurationPath,
            final String configurationFile, final String defaultConfigurationFile) throws IOException {
        super(configurationPath, defaultConfigurationPath, configurationFile, defaultConfigurationFile);
    }

    @Override
    protected String computeParameterValue(final String value) {

        String result = value;
        Matcher matcher = PROPERTY_PATTERN.matcher(result);
        while (matcher.find()) {
            final String group = matcher.group(1);
            final String paramValue = getParameterValue(group);
            if (paramValue == null) {
                throw new IllegalStateException("parameter value not found for " + group);
            }
            result = matcher.replaceFirst(paramValue);
            matcher = PROPERTY_PATTERN.matcher(result);
        }
        return result;
    }

    public static RecursivePropertyConfiguration of(final String userFolder, final String configurationFile)
            throws IOException {
        return new RecursivePropertyConfiguration(
                    "astroloj.configuration.path", Paths.get(System.getProperty("user.home"), userFolder).toString(),
                    "astroloj.configuration.file", configurationFile);
    }
}
