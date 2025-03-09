package org.chu.patterns.factory;

import org.chu.entities.Batiment;
import org.springframework.stereotype.Component;

@Component
public class UrgenceBatimentFactory extends BatimentFactory {
    
    @Override
    protected Batiment creerTypeBatiment() {
        Batiment batiment = new Batiment();
        batiment.setCategorie("URGENCE");
        batiment.setFonctionnalite("Soins d'urgence");
        return batiment;
    }
}

