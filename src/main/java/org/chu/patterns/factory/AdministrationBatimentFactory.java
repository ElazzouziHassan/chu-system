package org.chu.patterns.factory;

import org.chu.entities.Batiment;
import org.springframework.stereotype.Component;

@Component
public class AdministrationBatimentFactory extends BatimentFactory {
    
    @Override
    protected Batiment creerTypeBatiment() {
        Batiment batiment = new Batiment();
        batiment.setCategorie("ADMINISTRATION");
        batiment.setFonctionnalite("Gestion administrative");
        return batiment;
    }
}

