package org.chu.patterns.composite;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Composite du patron Composite
 */
@Data
public class GroupeServices implements ServiceComponent {
    private String nom;
    private String description;
    private List<ServiceComponent> services = new ArrayList<>();
    
    public GroupeServices(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }
    
    public void ajouterService(ServiceComponent service) {
        services.add(service);
    }
    
    public void supprimerService(ServiceComponent service) {
        services.remove(service);
    }
    
    @Override
    public void afficherDetails() {
        System.out.println("Groupe de services: " + nom);
        System.out.println("Description: " + description);
        System.out.println("Services inclus:");
        
        for (ServiceComponent service : services) {
            service.afficherDetails();
        }
    }
    
    @Override
    public int getNombrePersonnel() {
        int total = 0;
        for (ServiceComponent service : services) {
            total += service.getNombrePersonnel();
        }
        return total;
    }
}

