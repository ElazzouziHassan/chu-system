package org.chu.patterns.observer;

import org.chu.entities.Patient;
import org.chu.entities.Section;

import java.util.List;

/**
 * Interface pour le patron Observer
 */
public interface ServiceObserver {
    void mettreAJour(Section section, List<Patient> patients);
    void recevoirNotification(String message);
}

