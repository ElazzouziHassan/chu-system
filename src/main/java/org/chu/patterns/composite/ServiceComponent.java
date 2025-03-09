package org.chu.patterns.composite;

import java.util.List;

/**
 * Interface pour le patron Composite pour les services
 */
public interface ServiceComponent {
    String getNom();
    String getDescription();
    void afficherDetails();
    int getNombrePersonnel();
}

