package com.cosmoloj.math.tabular;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * Vérification de la différence de vitesse de parcours des tableaux en fonction
 * de l'ordre d'imbrication des indices.
 *
 * @author Samuel Andrés
 */
public class OtherTests {

    @BeforeEach
    public void before() {
        Assumptions.assumeTrue(false); // Skip these tests.
    }

    /**
     * Test rapide.
     */
    @Test
    public void test1() {
        System.out.println("test1");
        final int size = 20000;
        final boolean[][] tab = new boolean[size][size];

        final long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tab[i][j] = true;
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * Test lent.
     */
    @Test
    public void test2() {
        System.out.println("test2");
        final int size = 20000;
        final boolean[][] tab = new boolean[size][size];

        final long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tab[j][i] = true;
            }
        }
        System.out.println(System.currentTimeMillis() - start);

    }
}
