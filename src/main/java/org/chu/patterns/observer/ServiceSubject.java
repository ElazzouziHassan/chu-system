package org.chu.patterns.observer;

/**
 * Interface pour le sujet observé dans le patron Observer
 */
public interface ServiceSubject {
    void ajouterObservateur(ServiceObserver observateur);
    void supprimerObservateur(ServiceObserver observateur);
    void notifierObservateurs();
}

