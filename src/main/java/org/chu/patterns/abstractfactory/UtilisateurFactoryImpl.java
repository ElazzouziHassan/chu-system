package org.chu.patterns.abstractfactory;

import org.chu.entities.*;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurFactoryImpl implements UtilisateurFactory {
    
    @Override
    public Medecin creerMedecin() {
        Medecin medecin = new Medecin();
        medecin.setFonction("Médecin");
        return medecin;
    }
    
    @Override
    public Infirmier creerInfirmier() {
        Infirmier infirmier = new Infirmier();
        infirmier.setFonction("Infirmier");
        return infirmier;
    }
    
    @Override
    public Administrateur creerAdministrateur() {
        Administrateur administrateur = new Administrateur();
        administrateur.setFonction("Administrateur");
        return administrateur;
    }
    
    @Override
    public Patient creerPatient() {
        Patient patient = new Patient();
        return patient;
    }
    
    @Override
    public AgentAide creerAgentAide() {
        AgentAide agentAide = new AgentAide();
        agentAide.setFonction("Agent d'aide");
        return agentAide;
    }
    
    @Override
    public Directeur creerDirecteur() {
        Directeur directeur = new Directeur();
        directeur.setFonction("Directeur");
        return directeur;
    }
}

