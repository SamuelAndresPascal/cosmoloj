package com.cosmoloj.configuration;

import java.util.function.BiConsumer;

/**
 * <div class="fr">Une spécialisation d'interface configurable pratique pour les tests.</div>
 *
 * @author Samuel Andrés
 */
public interface TestConfigurable extends Configurable {

    default String configurationValue(final String parameter,
            final BiConsumer<Boolean, String> nullConsumer) {
        final String value = configurationValue(parameter);
        nullConsumer.accept(value == null, configurationKey(parameter) + " configuration not found");
        return value;
    }
}
