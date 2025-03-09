package org.chu.patterns.mediator;

import org.chu.entities.Personnel;

/**
 * Interface pour le patron Mediator
 */
public interface ChefServiceMediator {
    void envoyerMessage(String message, Personnel expediteur, Personnel destinataire);
    void diffuserMessage(String message, Personnel expediteur);
    void ajouterChefService(Personnel chefService);
}

