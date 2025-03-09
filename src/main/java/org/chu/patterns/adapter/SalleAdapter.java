package org.chu.patterns.adapter;

import org.chu.entities.Batiment;
import org.chu.entities.Service;
import org.springframework.stereotype.Component;

/**
 * Patron Adapter pour adapter l'utilisation des bâtiments
 */
@Component
public class SalleAdapter {
    
    /**
     * Adapte une salle de réanimation en salle de chirurgie
     */
    public Service adapterReanimationEnChirurgie(Service salleReanimation) {
        Service salleChirurgie = new Service();
        salleChirurgie.setNom(salleReanimation.getNom() + " (adaptée pour chirurgie)");
        salleChirurgie.setType("CHIRURGIE");
        salleChirurgie.setDescription("Salle de réanimation adaptée temporairement pour chirurgie");
        salleChirurgie.setBatiment(salleReanimation.getBatiment());
        salleChirurgie.setChefService(salleReanimation.getChefService());
        return salleChirurgie;
    }
    
    /**
     * Adapte un bloc d'urgence en bloc de chirurgie
     */
    public Service adapterUrgenceEnChirurgie(Service blocUrgence) {
        Service blocChirurgie = new Service();
        blocChirurgie.setNom(blocUrgence.getNom() + " (adapté pour chirurgie)");
        blocChirurgie.setType("CHIRURGIE");
        blocChirurgie.setDescription("Bloc d'urgence adapté temporairement pour chirurgie");
        blocChirurgie.setBatiment(blocUrgence.getBatiment());
        blocChirurgie.setChefService(blocUrgence.getChefService());
        return blocChirurgie;
    }
}

