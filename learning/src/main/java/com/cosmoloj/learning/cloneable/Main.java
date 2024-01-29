package com.cosmoloj.learning.cloneable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @author Samuel Andrés
 */
public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private Main() {
    }

    public static void main(final String[] args) throws CloneNotSupportedException {
        final Child child = new Child("coco", new String("co'"), new AttributeCloneable("04"), 10,
                new AttributeNotCloneable("06"));
        LOG.info("{}", child);
        LOG.info("{}", child.getName());
        LOG.info("{}", child.getPhone1());
        LOG.info("{}", child.getPhone2());

        // Les propriétés primitives parentes et filles sont automatiquement clonées
        final Child clone = child.clone();
        LOG.info("{}", clone);
        LOG.info("{}", clone.getName());
        LOG.info("{}", clone.getPhone1());
        LOG.info("{}", clone.getPhone2());

        // les références sont partagées
        LOG.info("{}", child.getName() == clone.getName()); // String litéral partagé (normal)
        LOG.info("{}", child.getSurname() == clone.getSurname()); // instance String partagée
        LOG.info("{}", child.getPhone1() == clone.getPhone1());
        LOG.info("{}", child.getPhone2() == clone.getPhone2());


        // clonage d'une implémentation de Cloneable :
        // les super.clone() en cascade ne déclenchent pas de CloneNotSupportedException
        final Clone instance1 = new Clone("clone");
        LOG.info("{}", instance1);

        final Clone clone1 = instance1.clone();
        LOG.info("{}", clone1);


        try {
            // clonage d'une instance qui n'est pas implémentation de Cloneable :
            // les super.clone() en cascade finit par déclencher une CloneNotSupportedException
            final NotClone instance2 = new NotClone("notClone");
            LOG.info("{}", instance2);

            final NotClone clone2 = instance2.clone();
            LOG.info("{}", clone2);
        } catch (final CloneNotSupportedException ex) {
            LOG.info("{}", ex);
        }
    }
}
