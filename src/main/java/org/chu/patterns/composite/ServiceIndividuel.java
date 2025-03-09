package org.chu.patterns.composite;

import org.chu.entities.Service;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Feuille du patron Composite
 */
@Data
@AllArgsConstructor
public class ServiceIndividuel implements ServiceComponent {
    private Service service;
    private int nombrePersonnel;
    
    @Override
    public String getNom() {
        return service.getNom();
    }
    
    @Override
    public String getDescription() {
        return service.getDescription();
    }
    
    @Override
    public void afficherDetails() {
        System.out.println("Service: " + service.getNom() + 
                           ", Type: " + service.getType() + 
                           ", Personnel: " + nombrePersonnel);
    }
    
    @Override
    public int getNombrePersonnel() {
        return nombrePersonnel;
    }
}

