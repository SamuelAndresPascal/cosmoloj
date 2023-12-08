package com.cosmoloj.configuration;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * <div class="fr">Une configuration associe à une clef une valeur définie dans le cadre d'un contexte particulier.
 * </div>
 *
 * @author Samuel Andrés
 */
public interface Configuration {

    /**
     * <div class="fr">Fournit la valeur associée à la clef dans le contexte de la configuration, ou à défaut
     * {@code null}.</div>
     *
     * @param key <span class="fr">la clef d'accès à une valeur de la configuration</span>
     * @return <span class="fr">la valeur de la configuration associée à la clef indiquée, ou à défaut {@code null}
     * </span>
     */
    String getParameterValue(String key);


    static Configuration of(final String userFolder, final String configurationFile) {
        try {
            return RecursivePropertyConfiguration.of(userFolder, configurationFile);
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    static Configuration ofFolder(final String userFolder) {
        return of(userFolder, "configuration.properties");
    }
}
