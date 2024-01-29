package com.cosmoloj.learning.array;

/**
 * Principe de programmation : prendre garde à l'organisation de la mémoire dans le parcours des tableaux
 * multidimentionnels.
 *
 * Tout tableau multidimentionnel est arrangé en mémoire comme un tableau de tableaux et en dernière instance comme un
 * simple tableau d'une seule dimension.
 *
 * Autant que possible il faut essayer de parcourir les éléments du tableau multidimentionnel dans l'ordre des éléments
 * du tableau à une seule dimension en mémoire pour éviter les allers-retours qui constituent une perte de temps bien
 * plus importante que les accès en lecture/écriture eux-mêmes.
 *
 * La convention en Java est contraire à la convention en C/C++.
 *
 * @author Samuel Andrés
 */
public final class Main {

    private Main() {
    }

    public static void main(final String[] args) {

        final int size = 10000;
        final double[][] array = new double[size][size];

        // les indices doivent être parcourus dans des boucles imbriquées dans l'ordre des dimensions (contraire du C)
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                array[i][j]++;
            }
        }

        // sinon le temps de parcours s'allonge de manière très importante
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                array[j][i]++;
            }
        }

        System.out.println(System.currentTimeMillis() - start);
    }
}
