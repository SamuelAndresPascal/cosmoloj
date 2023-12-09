package com.cosmoloj.language.common.impl.parsing;

import com.cosmoloj.language.api.exception.LanguageException;
import com.cosmoloj.language.api.parsing.Scanner;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * <div class="fr">Ce {@link Scanner} prend une chaîne de caractères en paramètres et la parcourt caractère après
 * caractère jusqu'à la fin de la chaîne.</div>
 *
 * @author Samuel Andrés
 */
public class DefaultStreamScanner implements Scanner, Closeable {

    private final BufferedReader reader;
    private String line;
    private int lineNb;
    private int linePosition;
    private int cursor;

    public DefaultStreamScanner(final BufferedReader reader) throws IOException {
        this.reader = reader;
        this.line = reader.readLine();
        linePosition = 0;
        lineNb = 0;
        cursor = 0;
    }

    @Override
    public boolean hasNext() {
        return this.line != null;
    }

    /**
     * <div class="fr">Retourne le caractère suivant dans la chaîne et incrémente la position. Pour vérifier qu'il
     * reste des caractères à explorer dans la chaîne de caractères, utiliser la méthode
     * {@link Scanner#hasNext() }.</div>
     *
     * @return <span class="fr">retourne le caractère suivant dans la chaîne et incrémente la position</span>
     */
    @Override
    public int nextInt() {
        final int result = line.codePointAt(linePosition++);
        if (linePosition == line.length()) {
            try {
                line = reader.readLine();
            } catch (final IOException ex) {
                throw new UncheckedIOException("Problem reading line", ex);
            }
            linePosition = 0;
            lineNb++;
        }
        cursor++;
        return result;
    }

    /**
     * <div class="fr">Retourne la position courante dans la chaîne de caractères.</div>
     *
     * @return <span class="fr">la position courante dans la chaîne de caractères</span>
     */
    protected int linePosition() {
        return linePosition;
    }

    /**
     * <div class="fr">Retourne le numéro de la ligne courante.</div>
     *
     * @return <span class="fr">la position courante du buffer</span>
     */
    protected int line() {
        return lineNb;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int cursor() {
        return cursor;
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
        if (linePosition + n < line.length()) {
            return line.codePointAt(linePosition + n);
        } else {
            throw new LanguageException("Fin de la chaîne d'entrée.");
        }
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
