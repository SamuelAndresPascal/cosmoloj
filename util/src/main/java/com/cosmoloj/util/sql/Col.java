package com.cosmoloj.util.sql;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Samuel Andrés
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Col {

    String value() default "";

    /**
     * <div class="fr">Si le champ fait partie d'une clef étrangère, indiquer la classe pointée. Si le champ fait partie
     * d'une clef primaire seulement (et ne référence aucun champ), indiquer {@link Void}.</div>
     * @return
     */
    Class<?> reference() default Void.class;

    /**
     * <div class="fr">Indique si le champ fait partie de la clef primaire. Vrai par défaut. Pour des raisons de
     * cohérence la valeur du champ n'est pas examinée si {@link Code#reference() } pointe sur {@link Void.class}
     * car cela indique <em>forcément</em> un champ de clef primaire. Dans les autres cas, mettre à faux si on souhaite
     * que le membre de clef étrangère soit exclu de la clef primaire.</div>
     * @return
     */
    boolean pkMember() default false;

    // simple indication
    ReferenceType type() default ReferenceType.NORMATIVE;

    // Permet de grouper les champs constitutifs d'une même clef étrangère. Par défaut, les membres d'une clef étrangère
    // ne font partie d'aucun groupe et constitue donc une clef étrangère indépendante.
    String fkGroup() default "";
}
