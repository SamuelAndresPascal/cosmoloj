package com.cosmoloj.util.sql;

import com.cosmoloj.configuration.ConfInfo;
import com.cosmoloj.configuration.Configurable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Samuel Andrés
 */
public abstract class DatabaseProperties {

    public static final String DEFAULT_CONF_URL = "url";
    public static final String DEFAULT_CONF_USER = "user";
    public static final String DEFAULT_CONF_PASSWORD = "password";
    public static final String DEFAULT_CONF_SCHEMA = "schema";

    private final String url;
    private final Properties properties;
    private final String schema;

    protected DatabaseProperties(final String url, final Properties properties, final String schema) {
        this.url = url;
        this.properties = properties;
        this.schema = schema;
    }

    protected DatabaseProperties(final String url, final String user, final String password, final String schema) {
        this.url = url;
        final Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        this.properties = props;
        this.schema = schema;
    }

    protected DatabaseProperties(final Configurable configurable, final String urlConf, final String userConf,
            final String passwordConf, final String schemaConf) {
        this(configurable.configurationValue(urlConf),
                configurable.configurationValue(userConf),
                configurable.configurationValue(passwordConf),
                configurable.configurationValue(schemaConf));
    }

    protected DatabaseProperties(final Configurable configurable) {
        this(configurable.configurationValue(DEFAULT_CONF_URL),
                configurable.configurationValue(DEFAULT_CONF_USER),
                configurable.configurationValue(DEFAULT_CONF_PASSWORD),
                configurable.configurationValue(DEFAULT_CONF_SCHEMA));
    }

    protected final Connection openConnection() throws SQLException {
        return DriverManager.getConnection(url, properties);
    }

    protected final String getSchema() {
        return schema;
    }


    private static final Map<String, ConfInfo> DEFAULT_CONFIGURATION = Map.of(
            DEFAULT_CONF_URL, ConfInfo.desc("url de la base"),
            DEFAULT_CONF_USER, ConfInfo.desc("utilisateur de la base"),
            DEFAULT_CONF_PASSWORD, ConfInfo.desc("mot de passe de la base"),
            DEFAULT_CONF_SCHEMA, ConfInfo.desc("schéma dans lequel sont définies les relations de la base"));

    public static final Map<String, ConfInfo> defaultConfigurationParameters() {
        return DEFAULT_CONFIGURATION;
    }

    public static final Map<String, ConfInfo> defaultConfigurationParameters(
            final Map.Entry<String, ConfInfo> entries) {
        return Map.ofEntries(
                Map.entry(DEFAULT_CONF_URL, ConfInfo.desc("url de la base")),
                Map.entry(DEFAULT_CONF_USER, ConfInfo.desc("utilisateur de la base")),
                Map.entry(DEFAULT_CONF_PASSWORD, ConfInfo.desc("mot de passe de la base")),
                Map.entry(DEFAULT_CONF_SCHEMA,
                        ConfInfo.desc("schéma dans lequel sont définies les relations de la base")),
                entries);
    }
}
