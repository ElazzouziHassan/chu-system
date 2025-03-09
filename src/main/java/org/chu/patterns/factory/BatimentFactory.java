package org.chu.patterns.factory;

import org.chu.entities.Batiment;

/**
 * Patron Factory Method pour créer différents types de bâtiments
 */
public abstract class BatimentFactory {
    
    public Batiment creerBatiment() {
        Batiment batiment = creerTypeBatiment();
        return batiment;
    }
    
    protected abstract Batiment creerTypeBatiment();
}

