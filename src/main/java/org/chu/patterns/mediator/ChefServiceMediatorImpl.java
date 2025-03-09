package org.chu.patterns.mediator;

import org.chu.entities.Personnel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du patron Mediator
 */
@Component
public class ChefServiceMediatorImpl implements ChefServiceMediator {
    
    private List<Personnel> chefsService = new ArrayList<>();
    
    @Override
    public void ajouterChefService(Personnel chefService) {
        chefsService.add(chefService);
    }
    
    @Override
    public void envoyerMessage(String message, Personnel expediteur, Personnel destinataire) {
        System.out.println("Message de " + expediteur.getNom() + " à " + destinataire.getNom() + ": " + message);
        // Logique pour envoyer un message à un chef de service spécifique
    }
    
    @Override
    public void diffuserMessage(String message, Personnel expediteur) {
        System.out.println("Message diffusé par " + expediteur.getNom() + ": " + message);
        
        for (Personnel chef : chefsService) {
            if (!chef.equals(expediteur)) {
                System.out.println("Message reçu par " + chef.getNom() + ": " + message);
                // Logique pour envoyer un message à tous les chefs de service sauf l'expéditeur
            }
        }
    }
}

