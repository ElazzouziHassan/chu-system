package org.chu.controllers;

import org.chu.entities.*;
import org.chu.patterns.abstractfactory.UtilisateurFactory;
import org.chu.repositories.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnels")
public class PersonnelController {
    
    @Autowired
    private PersonnelRepository personnelRepository;
    
    @Autowired
    private UtilisateurFactory utilisateurFactory;
    
    @GetMapping
    public List<Personnel> getAllPersonnels() {
        return personnelRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Personnel> getPersonnelById(@PathVariable Long id) {
        return personnelRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/medecins")
    public Medecin createMedecin(@RequestBody Medecin medecinDetails) {
        Medecin medecin = utilisateurFactory.creerMedecin();
        medecin.setNom(medecinDetails.getNom());
        medecin.setPrenom(medecinDetails.getPrenom());
        medecin.setIdentifiant(medecinDetails.getIdentifiant());
        medecin.setSpecialite(medecinDetails.getSpecialite());
        return personnelRepository.save(medecin);
    }
    
    @PostMapping("/infirmiers")
    public Infirmier createInfirmier(@RequestBody Infirmier infirmierDetails) {
        Infirmier infirmier = utilisateurFactory.creerInfirmier();
        infirmier.setNom(infirmierDetails.getNom());
        infirmier.setPrenom(infirmierDetails.getPrenom());
        infirmier.setIdentifiant(infirmierDetails.getIdentifiant());
        infirmier.setService(infirmierDetails.getService());
        return personnelRepository.save(infirmier);
    }
    
    @PostMapping("/administrateurs")
    public Administrateur createAdministrateur(@RequestBody Administrateur administrateurDetails) {
        Administrateur administrateur = utilisateurFactory.creerAdministrateur();
        administrateur.setNom(administrateurDetails.getNom());
        administrateur.setPrenom(administrateurDetails.getPrenom());
        administrateur.setIdentifiant(administrateurDetails.getIdentifiant());
        administrateur.setDepartement(administrateurDetails.getDepartement());
        return personnelRepository.save(administrateur);
    }
    
    @PostMapping("/agents-aide")
    public AgentAide createAgentAide(@RequestBody AgentAide agentAideDetails) {
        AgentAide agentAide = utilisateurFactory.creerAgentAide();
        agentAide.setNom(agentAideDetails.getNom());
        agentAide.setPrenom(agentAideDetails.getPrenom());
        agentAide.setIdentifiant(agentAideDetails.getIdentifiant());
        agentAide.setZone(agentAideDetails.getZone());
        return personnelRepository.save(agentAide);
    }
    
    @PostMapping("/directeurs")
    public Directeur createDirecteur(@RequestBody Directeur directeurDetails) {
        Directeur directeur = utilisateurFactory.creerDirecteur();
        directeur.setNom(directeurDetails.getNom());
        directeur.setPrenom(directeurDetails.getPrenom());
        directeur.setIdentifiant(directeurDetails.getIdentifiant());
        directeur.setBureau(directeurDetails.getBureau());
        return personnelRepository.save(directeur);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Personnel> updatePersonnel(@PathVariable Long id, @RequestBody Personnel personnelDetails) {
        return personnelRepository.findById(id)
                .map(personnel -> {
                    personnel.setNom(personnelDetails.getNom());
                    personnel.setPrenom(personnelDetails.getPrenom());
                    personnel.setFonction(personnelDetails.getFonction());
                    personnel.setIdentifiant(personnelDetails.getIdentifiant());
                    return ResponseEntity.ok(personnelRepository.save(personnel));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonnel(@PathVariable Long id) {
        return personnelRepository.findById(id)
                .map(personnel -> {
                    personnelRepository.delete(personnel);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

