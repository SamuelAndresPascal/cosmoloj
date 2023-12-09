package com.cosmoloj.language.api.semantic;

import java.util.Comparator;

/**
 * <div class="fr">Un jeton est caractérisé par sa position dans le texte parcouru : un index de début, un index de fin
 * et un ordre dans la lecture permettant d'ordonner les jetons imbriqués.</div>
 *
 * @author Samuel Andrés
 */
public interface Token {

    /**
     * <div class="fr">Index du premier caractère du jeton.</div>
     *
     * @return <span class="fr">l'index du premier caractère du jeton</span>
     */
    int first();

    /**
     * <div class="fr">Index du dernier caractère du jeton.</div>
     *
     * @return <span class="fr">l'index du dernier caractère du jeton</span>
     */
    int last();

    /**
     * <div class="fr">Ordre de l'interprétation du jeton lors de la lecture.</div>
     *
     * @return <span class="fr">l'ordre du jeton dans la liste des jetons lus</span>
     */
    int order();

    /*##################################################################################################################
    COMPARATEURS -- COMPARATEURS -- COMPARATEURS -- COMPARATEURS -- COMPARATEURS -- COMPARATEURS -- COMPARATEURS
    ##################################################################################################################*/

    /**
     * <div class="fr">On classe d'abord les lexemes par leur indice de départ et à indice de départ identique on classe
     * d'abord les lexèmes au plus grand indice de fin. À égalité d'indices de départ et de fin, on classe par ordre
     * d'interprétation décroissant (c'est à dire d'abord le lexème le plus englobant et ensuite le lexème le plus
     * élémentaire). Ce comparateur permet en particulier d'insérer les balises de départ.</div>
     */
    Comparator<Token> FIRST_OPEN_LAST_CLOSED = (final Token o1, final Token o2) -> {

        // on classe a priori par ordre croissant du premier caractère
        int diff = o1.first() - o2.first();

        // à position égale du premier caractère, on classe ordre décroissant du dernier caractère
        if (diff == 0) {
            diff =  o2.last() - o1.last();
            if (diff == 0) {
                // à égale position de premier et dernier caractères on classe par ordre d'interprétation décroissant
                return o2.order() - o1.order();
            }
        }
        return diff;
    };

    /**
     * <div class="fr">On classe d'abord les lexemes par leur indice de fin et à indice de fin identique on classe
     * d'abord les lexèmes au plus grand indice de départ. À égalité d'indices de départ et de fin, on classe par ordre
     * d'interprétation croissant (c'est à dire d'abord le lexème le plus élémentaire et ensuite le lexème le plus
     * englobant). Ce comparateur permet en particulier d'insérer les balises de fin.</div>
     */
    Comparator<Token> FIRST_OPEN_FIRT_CLOSED = (final Token o1, final Token o2) -> {

        // on classe a priori par ordre croissant du dernier caractère
        int diff = o1.last() - o2.last();

        // à position égale du dernier caractère, on classe ordre décroissant du premier caractère
        if (diff == 0) {
            diff = o1.first() - o2.first();
            if (diff == 0) {
                // à égale position de premier et dernier caractères on classe par ordre d'interprétation croissant
                return o1.order() - o2.order();
            }
        }
        return diff;
    };
}
