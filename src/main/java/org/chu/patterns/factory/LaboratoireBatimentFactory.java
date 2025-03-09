package org.chu.patterns.factory;

import org.chu.entities.Batiment;
import org.springframework.stereotype.Component;

@Component
public class LaboratoireBatimentFactory extends BatimentFactory {
    
    @Override
    protected Batiment creerTypeBatiment() {
        Batiment batiment = new Batiment();
        batiment.setCategorie("LABORATOIRE");
        batiment.setFonctionnalite("Analyses médicales");
        return batiment;
    }
}

