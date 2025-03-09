package org.chu.patterns.observer;

import org.chu.entities.Patient;
import org.chu.entities.Section;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du sujet observé dans le patron Observer
 */
@Component
public class ServiceSubjectImpl implements ServiceSubject {
    
    private List<ServiceObserver> observateurs = new ArrayList<>();
    private Section section;
    private List<Patient> patients;
    
    @Override
    public void ajouterObservateur(ServiceObserver observateur) {
        observateurs.add(observateur);
    }
    
    @Override
    public void supprimerObservateur(ServiceObserver observateur) {
        observateurs.remove(observateur);
    }
    
    @Override
    public void notifierObservateurs() {
        for (ServiceObserver observateur : observateurs) {
            observateur.mettreAJour(section, patients);
        }
    }
    
    public void setEtatService(Section section, List<Patient> patients) {
        this.section = section;
        this.patients = patients;
        notifierObservateurs();
    }
    
    public void envoyerNotification(String message) {
        for (ServiceObserver observateur : observateurs) {
            observateur.recevoirNotification(message);
        }
    }
}

