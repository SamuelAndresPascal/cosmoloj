package com.cosmoloj.configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <div class="fr">Object configurable par une configuration donnant accès à un certain nombre de paramètres. Un
 * objet configurable doit :
 * <ol>
 * <li>être en capacité de fournir sa configuration courante,</li>
 * <li>déclarer un tableau associatif dont les clefs sont les noms des paramètres qu'il utilise et les valeurs un
 * tableau d'attributs de ces paramètres (description, valeur par défaut…),</li>
 * <li>implémenter la correspondance entre les noms des paramètres qu'il utilise et les clefs de la configuration pour
 * accéder aux valeurs de ces paramètres (voir l'implémentation par défaut),</li>
 * <li>implémenter l'accès aux valeurs d'un paramètre défini dans la configuration à partir du nom du paramètre défini
 * dans le configurable (voir l'implémentation par défaut).</li>
 * </ol>
 * </div>
 *
 * @author Samuel Andrés
 *
 * @see Configuration
 */
public interface Configurable {

    int PARAMETER_DESCRIPTION = 0;
    int PARAMETER_DEFAULT_VALUE = 1;

    /**
     *
     * @return <span class="fr">La configuration utilisée par l'objet configurable.</span>
     */
    Configuration configuration();

    /**
     * <div class="fr">Un tableau associatif descriptif des paramètres. Les clefs sont les noms des paramètres servant
     * d'indentifiants dans le cadre de l'objet configurable (pour déterminer la clef utilisée par la configuration,
     * voir {@link Configurable#configurationKey(java.lang.String) }). Les valeurs sont constituées par un
     * tableau des caractéristiques du paramètre. L'implémentation par défaut considère que la valeur par défaut d'un
     * paramètre, si elle est présente, est définie à l'index {@link Configurable#PARAMETER_DEFAULT_VALUE}.</div>
     * @return <span class="fr">le tableau associatif des paramètres de configuration</span>
     */
    Map<String, ConfInfo> configurationInfo();

    /**
     * <div class="fr">Donne la correspondance entre le nom du paramètre dans le configurable et la clef qui lui
     * correspond dans la configuration. Par défaut,la clef est définie par concaténation du nom canonique de la classe
     * de l'objet configurable, d'un caractère '.' et du nom du paramètre.</div>
     *
     * @param parameter <span class="fr">nom du paramètre</span>
     * @return <span class="fr">clef de définition du paramètre du paramètre</span>
     */
    default String configurationKey(final String parameter) {
        return this.getClass().getCanonicalName() + '.' + parameter;
    }

    /**
     * <div class="fr">Donne la valeur du paramètre définie par la configuration, ou à défaut la valeur définie dans le
     * tableau associatif des paramètres comme valeur par défaut. L'implémentation par défaut considère que la valeur
     * par défaut d'un paramètre, si elle est présente, est définie à l'index
     * {@link Configurable#PARAMETER_DEFAULT_VALUE}.</div>
     *
     * @param parameter <span class="fr">nom du paramètre</span>
     * @return <span class="fr">valeur du paramètre déduite de la configuration</span>
     */
    default String configurationValue(final String parameter) {
        final String value = configuration().getParameterValue(configurationKey(parameter));
        return value == null ? getDefaultValue(parameter) : value;
    }

    default String getDefaultValue(final String parameter) {
        return configurationInfo().get(parameter).defaultValue();
    }

    default String getDescription(final String parameter) {
        return configurationInfo().get(parameter).description();
    }

    /**
     * <div class="fr">Donne la valeur du paramètre définie par la configuration, interprété comme une liste.</div>
     *
     * @param parameter <span class="fr">nom du paramètre</span>
     * @param itemSeparator <span class="fr">séparateur des éléments de la liste</span>
     * @return <span class="fr">valeur du paramètre déduite de la configuration, interprété comme une liste</span>
     */
    default List<String> configurationValueAsList(final String parameter, final String itemSeparator) {
        return List.of(configurationValue(parameter).split(itemSeparator));
    }

    /**
     * <div class="fr">Donne la valeur du paramètre définie par la configuration, interprété comme une liste.</div>
     *
     * @param parameter <span class="fr">nom du paramètre</span>
     * @return <span class="fr">valeur du paramètre déduite de la configuration, interprété comme une liste</span>
     */
    default List<String> configurationValueAsList(final String parameter) {
        return List.of(configurationValue(parameter).split(getItemSeparator()));
    }

    /**
     * <div class="fr">Donne la valeur du paramètre définie par la configuration, interprété comme un ensemble.</div>
     *
     * @param parameter <span class="fr">nom du paramètre</span>
     * @param itemSeparator <span class="fr">séparateur des éléments de l'ensemble</span>
     * @return <span class="fr">valeur du paramètre déduite de la configuration, interprété comme un ensemble</span>
     */
    default Set<String> configurationValueAsSet(final String parameter, final String itemSeparator) {
        return Set.of(configurationValue(parameter).split(itemSeparator));
    }

    /**
     * <div class="fr">Donne la valeur du paramètre définie par la configuration, interprété comme un ensemble.</div>
     *
     * @param parameter <span class="fr">nom du paramètre</span>
     * @return <span class="fr">valeur du paramètre déduite de la configuration, interprété comme un ensemble</span>
     */
    default Set<String> configurationValueAsSet(final String parameter) {
        return Set.of(configurationValue(parameter).split(getItemSeparator()));
    }

    /**
     * <div class="fr">Donne la valeur du paramètre définie par la configuration, interprété comme un tableau
     * associatif.</div>
     *
     * @param parameter <span class="fr">nom du paramètre</span>
     * @param entrySeparator <span class="fr">séparateur des entrées du tableau associatif</span>
     * @param keyValueSeparator <span class="fr">séparateur clef/valeur de chaque entrée</span>
     * @return <span class="fr">valeur du paramètre déduite de la configuration, interprété comme un tableau associatif
     * </span>
     */
    default Map<String, String> configurationValueAsMap(final String parameter, final String entrySeparator,
            final String keyValueSeparator) {
        return Stream.of(configurationValue(parameter).split(entrySeparator))
                .map(entry -> entry.split(keyValueSeparator))
                .collect(Collectors.toMap(splt -> splt[0], splt -> splt[1]));
    }

    /**
     * <div class="fr">Donne la valeur du paramètre définie par la configuration, interprété comme un tablea associatif
     * en utilisant les séparateurs définis par défaut.</div>
     *
     * @param parameter <span class="fr">nom du paramètre</span>
     * @return <span class="fr">valeur du paramètre déduite de la configuration, interprété comme un tableau associatif
     * </span>
     */
    default Map<String, String> configurationValueAsMap(final String parameter) {
        return configurationValueAsMap(parameter, getEntrySeparator(), getKeyValueSeparator());
    }

    /**
     * @return <span class="fr">séparateur d'entrée par défaut pour l'interprétation des valeurs comme tableaux
     * associatifs</span>
     */
    default String getItemSeparator() {
        return ",";
    }

    /**
     * @return <span class="fr">séparateur d'entrée par défaut pour l'interprétation des valeurs comme tableaux
     * associatifs</span>
     */
    default String getEntrySeparator() {
        return ",";
    }

    /**
     * @return <span class="fr">séparateur clef/valeur par défaut pour l'interprétation des valeurs comme tableaux
     * associatifs</span>
     */
    default String getKeyValueSeparator() {
        return ":";
    }
}
