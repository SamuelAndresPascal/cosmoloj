package com.cosmoloj.language.common.impl.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.parsing.Scanner;

/**
 * <div class="fr">Ce {@link Scanner} prend une chaîne de caractères en paramètres et la parcourt caractère après
 * caractère jusqu'à la fin de la chaîne.</div>
 *
 * @author Samuel Andrés
 *
 */
public class DefaultStringScanner implements Scanner {

    private int position;
    private final String text;

    public DefaultStringScanner(final String text) {
        this.text = text;
        position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < text.length();
    }

    /**
     * <div class="fr">Retourne le caractère suivant dans la chaîne et incrémente la position. Pour vérifier qu'il
     * reste des caractères à explorer dans la chaîne de caractères, utiliser la méthode
     * {@link DefaultStringScanner#hasNext() }.</div>
     *
     * @return <span class="fr">retourne le caractère suivant dans la chaîne et incrémente la position</span>
     */
    @Override
    public int nextInt() {
        return text.codePointAt(position++);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int cursor() {
        return position;
    }

    /**
     * <div class="fr">Retourne le caractère suivant le carctères courant avec un décalage donné en paramètre dans la
     * chaîne sans incrémenter la position.</div>
     *
     * @param n <span class="fr">nombre de caractères de décalage</span>
     * @return <span class="fr">retourne le caractère suivant le suivant dans la chaîne sans incrémenter la position
     * </span>
     * @throws LanguageException <span class="fr">Si l'on cherche à lire une position dépassant la longueur de la chaîne
     * de caractères</span>
     */
    @Override
    public int peek(final int n) throws LanguageException {
        if (position + n < text.length()) {
            return text.codePointAt(position + n);
        } else {
            throw new LanguageException("Fin de la chaîne d'entrée.");
        }
    }
}
