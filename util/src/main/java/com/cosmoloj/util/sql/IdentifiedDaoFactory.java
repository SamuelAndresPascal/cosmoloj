package com.cosmoloj.util.sql;

/**
 *
 * @author Samuel Andrés
 */
public interface IdentifiedDaoFactory extends DaoFactory {

    /**
     * <div class="fr">Retourne l'entité caractérisée par le type et l'identifiant en paramètres.</div>
     *
     * @param <R> <span class="fr">le type des l'entités à retourner</span>
     * @param type <span class="fr">le type des l'entités à retourner</span>
     * @param id <span class="fr">l'identifiant de l'entité à retourner</span>
     * @return <span class="fr">une liste d'entités</span>
     * @throws DaoFactoryException
     */
    <R> R code(Class<R> type, Object... id) throws DaoFactoryException;

}
