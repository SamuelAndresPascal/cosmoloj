package com.cosmoloj.format.csv;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class CsvSource {

    // détermine si le fichier a une en-tête
    private final boolean header;

    // caractère séparateur des champs
    private final int separator;

    // délimiteur de début de valeur
    private final int leftDelimiter;

    // délimiteur de fin de valeur
    private final int rightDelimiter;

    // caractère d'échappement
    private final int escape;

    /*
    Indique si on exige que le caractère d'échappement précède exclusivement le délimiteur de fin et lui-même DANS LES
    VALEURS DÉLIMITÉES.
    remarque : dans les valeurs non délimitées, le caractère d'échappement est un caractère comme un autre car n'ayant
    pas d'utilité particulière pour échapper le délimiteur de fin, il n'a pas de rôle particulier et n'a donc pas besoin
    de s'échapper lui-même.
    vrai : si le caractère d'échappement précède un autre caractère que le délimiteur de fin de valeur ou lui-même, on
    lance une exception
    faux : si le caractère d'échappement précède un autre caractère que le délimiteur de fin de valeur ou lui-même, on
    le tolère et on l'ignore
    */
    private final boolean strictEscape;

    private int character = 0;

    public CsvSource(final boolean header, final int separator, final int leftDelimiter, final int rightDelimiter,
            final int escape, final boolean strictEscape) {
        this.header = header;
        this.separator = separator;
        this.leftDelimiter = leftDelimiter;
        this.rightDelimiter = rightDelimiter;
        this.escape = escape;
        this.strictEscape = strictEscape;
    }

    public boolean isHeader() {
        return header;
    }

    public int getSeparator() {
        return separator;
    }

    public int getLeftDelimiter() {
        return leftDelimiter;
    }

    public int getRightDelimiter() {
        return rightDelimiter;
    }

    public int getEscape() {
        return escape;
    }

    public boolean isStrictEscape() {
        return strictEscape;
    }

    /**
     *
     * @param reader
     * @param nbFields
     * @return <span class="fr">une ligne de données ou null en cas de ligne vide</span>
     * @throws IOException
     */
    protected String[] readLine(final Reader reader, final int nbFields) throws IOException {
        final String[] line = new String[nbFields];

        int i = 0;
        while (true) {

            final String field = readField(reader);


            // opérations de fin de ligne ou de fin de flux
            if (character == '\n' || character == '\r' || character == -1) {
                // Linux | Mac | Win

                /*
                '\n' marque les fins de ligne Unix et '\r' marque les fins de ligne Mac
                '\r' suivi de '\n' marque les fins de ligne sous Windows : dans ce cas on considère que '\r' marque
                la fin de ligne et '\n' qui le suit marque la fin d'une autre ligne, vide.
                */

                /*
                Si on rencontre une fin de ligne, que le champ lu était vide et qu'il n'y a pas encore de champ dans la
                liste, c'est qu'on est arrivé à la dernière ligne du fichier. Ce n'est pas une ligne de données, dont on
                renvoie null pour le signaler.
                */
                if (i == 0 && field.isEmpty()) {
                    return null;
                } else {
                    /*
                    Sinon, il s'agit simplement d'une fin de ligne ordinaire. On ajoute le champ et on renvoie casse la
                    boucle pour renvoyer la ligne.
                    */
                    line[i++] = field;
                    break;
                }
            }

            line[i++] = field;
        }

        // on doit avoir rempli tous les champs
        if (i != line.length) {
            if (i == 0) {
                // si on n'a pas eu l'occasion d'incrémenter l'indice des champs, c'est que la ligne est vide
                return null;
            }
            throw new IllegalStateException();
        }

        return line;
    }

    protected String[] readFirstLine(final Reader reader) throws IOException {
        final List<String> line = new ArrayList<>();

        while (reader.ready()) {

            final String field = readField(reader);


            // opérations de fin de ligne
            if (character == '\n' || character == '\r') {
                // Linux | Mac | Win

                /*
                '\n' marque les fins de ligne Unix et '\r' marque les fins de ligne Mac
                '\r' suivi de '\n' marque les fins de ligne sous Windows : dans ce cas on considère que '\r' marque
                la fin de ligne et '\n' qui le suit marque la fin d'une autre ligne, vide.
                */

                /*
                Si on rencontre une fin de ligne, que le champ lu était vide et qu'il n'y a pas encore de champ dans la
                liste, c'est qu'on est arrivé à la dernière ligne du fichier. Ce n'est pas une ligne de données, dont on
                renvoie null pour le signaler.
                */
                if (field.isEmpty() && line.isEmpty()) {
                    return null;
                } else {
                    /*
                    Sinon, il s'agit simplement d'une fin de ligne ordinaire. On ajoute le champ et on renvoie casse la
                    boucle pour renvoyer la ligne.
                    */
                    line.add(field);
                    break;
                }
            }

            line.add(field);

        }


        final int nbCols = line.size();
        return line.toArray(new String[nbCols]);
    }

    private String readField(final Reader reader) throws IOException {
        final StringBuilder valueBuilder = new StringBuilder();

        // indique qu'on parse le premier caractère de la valeur
        boolean start = true;

        // indique qu'on s'attend à avoir fini le champ
        boolean end = false;

        // indique que la valeur est délimitée
        boolean delimited = false;

        // indique qu'on doit échapper le caractère suivant
        boolean escapeNext = false;

        while (true) {
            character = reader.read();

            // A- au début, on cherche à déterminer si la valeur est délimitée
            //*****************************************************************
            if (start) {
                start = false;
                if (character == leftDelimiter) {
                    delimited = true;
                    continue;
                }
            }

            // B- si la valeur est délimitée…
            //*******************************
            if (delimited) {

                // B1- détection du marqueur de fin de champ et/ou du caractère d'échappement :
                //-----------------------------------------------------------------------------

                if (escape == rightDelimiter) {
                    // a- dans le cas où le caractère d'échappement est identique au délimiteur de fin
                    if (!escapeNext && !end && character == rightDelimiter && character == escape) {
                        escapeNext = true;
                        end = true;
                        continue;
                    }
                } else {
                    // b- dans le cas où le caractère d'échappement et le délimiteur de fin sont différents
                    if (!escapeNext && character == escape) {
                        escapeNext = true;
                        continue;
                    }

                    // on ne peut pas avoir un véritable marqueur de fin si on est en cours d'échappement
                    if (!escapeNext && !end && character == rightDelimiter) {
                        end = true;
                        continue;
                    }
                }


                // B2- traitement du caractère courant si le caractères précédent était soit un délimiteur de fin, soit
                // un caractère d'échappement (sans qu'on puisse le déterminer avec certitude avant le carctère suivant
                // car  c'est le cas où le caractère d'échappement est identique au délimiteur de fin
                //-----------------------------------------------------------------------------------------------------

                if (escapeNext && end) {
                    // Si le caractère suivant est un délimiteur, on l'échappe en l'ajoutant à la chaîne
                    if (character == rightDelimiter) {

                        // dans ce cas, on n'était pas en fin de chaîne et il faut donc réinitialiser les détecteurs
                        escapeNext = false; end = false;
                        valueBuilder.append((char) character);
                    } else if (character == separator
                            || character == '\n'
                            || character == '\r'
                            || character == -1) {
                        /*
                        Dans les trois cas suivants, le délimiteur précédemment rencontré définissait vraiment une fin
                        de chaine et on doit donc maintenant tomber sur un séparateur, une fin de ligne ou une fin de
                        flux.
                        */
                        break;
                    } else {
                        /*
                        Si on trouve un autre caractère, on lance une exception car dans ce cas on n'accepte d'échapper
                        que le délimiteur de fin.
                        */
                        throw new IllegalStateException(String.format("unexpected character %s", character));
                    }
                } else if (escapeNext) {


                    // B3- si le caractère précédent était, de manière certaine, un caractère d'échappement
                    //-------------------------------------------------------------------------------------


                    /*
                    a) si le caractère suivant est un délimiteur, on l'échappe en l'ajoutant à la chaîne
                    b) si le carctère suivant est un caractère d'échappement, on l'échappe aussi en l'ajoutant à la
                    chaîne
                    c) s'il s'agit d'un autre caractère, on l'ajoute à la chaîne (et on ignore donc l'échappement
                    précédent)
                    uniquement si on n'est pas en mode d'échappement strict ; sinon, on lance une exeption
                    */
                    if (character == rightDelimiter || character == escape || !strictEscape) {

                        // dans ce cas, on n'était pas en fin de chaîne et il faut donc réinitialiser les détecteurs
                        escapeNext = false;
                        valueBuilder.append((char) character);
                    } else {
                        // cas d'un caractère d'échappement différent du délimiteur de fin
                        throw new IllegalStateException(
                                String.format("the escape character (%s) must only escape the right delimiter (%s) or"
                                        + " himself (encountered : %s)", escape, rightDelimiter, character));
                    }
                } else if (end) {

                    // B4- si le caractère précédent était, de manière certaine, un caractère de fin
                    //------------------------------------------------------------------------------

                    /*
                    Dans ce cas, il faut obligatoirement tomber sur un séparateur, un caractère de fin de ligne ou la
                    fin du flux.
                    */

                    /*
                    opérations de fin de valeur par séparateur
                    */
                    if (character == separator
                            || character == '\n'
                            || character == '\r'
                            || character == -1) {
                        break;
                    } else {
                        /*
                        lorsqu'on a détecté une fin de valeur, on s'attend obligatoirement à un séparateur ou une fin de
                        ligne
                        */
                        throw new IllegalStateException(
                                String.format("expected separtator (%s) or end of line (%n and/or \\r)", separator));
                    }
                } else {
                    // B5- si le caractère n'est pas susceptible d'avoir été échappé ou de se trouver après la fin de
                    // chaîne
                    //--------------------------------------------------------------------------------------------------

                    // on ajoute alors simplement le caractère
                    valueBuilder.append((char) character);
                }
            } else {

                // C- si la valeur n'est pas délimitée
                //************************************

                /*
                Dans ce cas, c'est très simple, il suffit d'attendre le séparateur de valeur ou le changement de ligne
                ou de fin de flux.
                Tous les autres caractères sont acceptés.
                */
                if (character == separator) {
                    break;
                } else if (character == '\n') {
                    break;
                } else if (character == '\r') {
                    break;
                } else if (character == -1) {
                    break;
                } else {
                    valueBuilder.append((char) character);
                }
            }
        }

        return valueBuilder.toString();
    }
}
