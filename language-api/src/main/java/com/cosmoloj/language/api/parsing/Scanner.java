package com.cosmoloj.language.api.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import java.util.PrimitiveIterator;

/**
 *
 * @author Samuel Andrés
 */
public interface Scanner extends PrimitiveIterator.OfInt {

    /**
     * <span class="fr">Détermine s'il reste des points de code à lire.</span>
     *
     * @return <span class="fr">{@code true} s'il reste des points de code à lire, {@code false} sinon</span>
     */
    @Override
    boolean hasNext();

    /**
     * <span class="fr">Passe au point de code suivant.</span>
     *
     * @return <span class="fr">le point de code suivant suivant</span>
     */
    @Override
    int nextInt();

    /**
     * <span class="fr">Le numéro du point de code à lire.</span>
     *
     * @return <span class="fr">le numéro du point de code à lire</span>
     */
    int cursor();

    /**
     * <span class="fr">Détermine s'il reste des points de code à lire.</span>
     *
     * @param n
     * @return <span class="fr">{@code true} s'il reste des points de code à lire, {@code false} sinon</span>
     * @throws com.cosmoloj.language.api.exception.LanguageException
     */
    int peek(int n) throws LanguageException;
}
