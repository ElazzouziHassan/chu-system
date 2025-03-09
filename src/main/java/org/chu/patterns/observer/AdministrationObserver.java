package org.chu.patterns.observer;

import org.chu.entities.Patient;
import org.chu.entities.Section;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Observateur concret pour l'administration dans le patron Observer
 */
@Component
public class AdministrationObserver implements ServiceObserver {
    
    @Override
    public void mettreAJour(Section section, List<Patient> patients) {
        System.out.println("Administration notifiée - Section: " + section.getNom());
        System.out.println("Nombre de patients: " + patients.size());
        
        // Logique pour mettre à jour les informations administratives
        for (Patient patient : patients) {
            System.out.println("Patient: " + patient.getNom() + " " + patient.getPrenom() + 
                              ", Code: " + patient.getCode() + 
                              ", Situation: " + patient.getSituationMedicale());
        }
    }
    
    @Override
    public void recevoirNotification(String message) {
        System.out.println("Administration a reçu la notification: " + message);
        // Logique pour traiter la notification
    }
}

