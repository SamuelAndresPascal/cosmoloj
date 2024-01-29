package com.cosmoloj.learning.collection;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Principe de programmation : éviter la création d'instances dans des boucles et se méfier en particulier des créations
 * d'instances cachées lorsque des objets immuables semblent modifiés.
 *
 * C'est une faiblesse du Kotlin dont le principe de base de proposer des collections immuables est pourtant très bon.
 *
 * Mais Kotlin fournit aussi une API qui semble modifier les collections immuables. Puisque cela n'est pas possible,
 * cela signifie qu'on crée des instances sans s'en appercevoir à chaque modification (c'est à dire à chaque tour de
 * boucle).
 *
 * Kotlin incite donc à mal utiliser une principe de programmation pourtant excellent dans le cas général.
 *
 * Mais tout principe n'est bon que dans son cadre de validité. L'immuabilité est excellente, SAUF lorsqu'on a BESOIN
 * d'objets modifiables. Si l'on utilise des collections dont le contenu est ajusté dans le programme, il ne faut pas
 * utiliser de collections immuables sur lesquelles on appelle la méthode plus(), mais des collections muables sur
 * lesquelles on appelle la méthode addAll().
 *
 * @author Samuel Andrés
 */
public final class Main {

    private Main() {
    }

    public static void main(final String[] args) {

        final int size = 100000;
        List<String> list = new ArrayList<>();
        final List<String> listToAdd = List.of("coucou");

        // addAll() permet d'ajouter les éléments d'une liste dans la liste d'origine muable
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            list.addAll(listToAdd);
        }
        System.out.println(System.currentTimeMillis() - start);

        // équivalent de la méthode plus() des collections kotlin sur des collections immuables qui a donc pour effet de
        // créer une nouvelle instance à chaque appel (donc potentiellement très coûteux dans une boucle ou un stream
        // version naive
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            final List<String> tmp = new ArrayList<>(list);
            tmp.addAll(listToAdd);
            list = tmp;
        }

        System.out.println(System.currentTimeMillis() - start);

        // version optimisée par Kotlin
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            final List<String> tmp = new ArrayList<>(list.size() + listToAdd.size());
            tmp.addAll(list);
            tmp.addAll(listToAdd);
            list = tmp;
        }

        System.out.println(System.currentTimeMillis() - start);
    }
}
