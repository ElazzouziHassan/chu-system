package org.chu.patterns.abstractfactory;

import org.chu.entities.*;

/**
 * Patron Abstract Factory pour créer différents types d'utilisateurs
 */
public interface UtilisateurFactory {
    Medecin creerMedecin();
    Infirmier creerInfirmier();
    Administrateur creerAdministrateur();
    Patient creerPatient();
    AgentAide creerAgentAide();
    Directeur creerDirecteur();
}

