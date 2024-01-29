package com.cosmoloj.learning.immutable;

import java.util.HashSet;
import java.util.Set;

/**
 * Principe de programmation : favoriser l'implémentation de classes immuables.
 *
 * Comme tout principe, bien qu'excellent, celui-ci ne doit pas être pris en un sens absolu. Lorsqu'on a
 * structurellement BESOIN de travailler avec des objets dont on doit modifier l'état (par exemple construction d'une
 * liste d'éléments par ajouts successifs ou retraits d'éléments), il faut utiliser des implementations muables.
 *
 * De manière générale, les classes immuables sont belles et bonnes.
 *
 * Belles car :
 * - en première approximation elles réduisent d'autant le nombre d'accesseurs que du nombre d'attribut (tous les
 * setters) ;
 * - très souvent il peut sembler gênant de se trouver forcé à initialiser un objet dès son instanciation, mais en
 * réalité on se rend souvent compte que le code s'en trouve éclairci et simplifié : dans 99% des cas, ne pas disposer
 * des attributs nécessaires à la définition de l'état d'un objet lorsqu'on l'instancie révèle un défaut d'algorithme.
 *
 * Bonnes car :
 * - un objet immuable est presque équivalent à un objet threadsafe (son état ne variant pas, on ne risque pas de le
 * trouver dans un état différent d'un thread à l'autre).
 * - les implémentation standards de collections et de maps qui utilisent la méthode hashCode() ont besoin que cette
 * méthode renvoie pour une instance donnée un résultat constant dans le temps (voir la documentation du CONTRAT de la
 * méthode hashCode dans la classe Object) afin de retrouver les éléments ou les clefs après qu'elles ont été insérées
 * (à l'insertion on calcule le hashCode pour classer l'instance et c'est ce même code que doit fournir l'élément qu'on
 * recherche pour être retrouvé).
 *
 * Remarque : Kotlin favorise la violation du contrat de hashCode() via les dataclasses en implémentant automatiquement
 * hashCode() à partir de champs dont la référence peut explicitement être modifiée.
 *
 * @author Samuel Andrés
 */
public final class Main {

    private Main() {
    }

    public static void main(final String[] args) {

        final Able muable = new Muable(0);

        final Set<Able> set = new HashSet<>();
        set.add(muable);

        // vrai, bien évidemment
        System.out.println(set.contains(muable));

        // un élément dans l'ensemble
        System.out.println(set.size());

        // retrait de l'élément
        System.out.println(set.remove(muable));

        // l'ensemble est vide
        System.out.println(set.size());

        // on ajoute de nouveau l'élément dans l'ensemble
        set.add(muable);

        // un élément dans l'ensemble
        System.out.println(set.size());

        // changement d'état du muable impactant le calcul de hashcode
        ((Muable) muable).value(2);

        // on ne retrouve pas l'élément dans l'ensemble alors que l'instance y est bien dedans !!!!
        System.out.println(set.contains(muable));

        // tentative de retrait de l'élément après son changement : échec car le hashcode a changé depuis l'insertion
        // il aurait fallu 1) sortir l'objet du set 2) le modifier 3) le réinsérer (beurk !)
        System.out.println(set.remove(muable));

        // l'ensemble fait toujours une taille de 1
        System.out.println(set.size());

       // Résultat : joli bogue si on ne fait pas attention à ne pas modifier l'état de l'objet !
       // Du coup, autant s'assurer d'une implémentation immuable et se soulager l'esprit !

       // Remarque, si on n'avait pas implémenté hashCode à partir de l'état de l'objet (comme c'est automatiquement le
       // cas dans les dataclasses Kotlin ou les records Java), on n'aurait pas eu ce problème. Mais Java aide à ne pas
       // faire d'erreur en forçant les attributs finaux.
    }
}
