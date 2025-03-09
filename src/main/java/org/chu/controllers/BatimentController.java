package org.chu.controllers;

import org.chu.entities.Batiment;
import org.chu.patterns.factory.AdministrationBatimentFactory;
import org.chu.patterns.factory.LaboratoireBatimentFactory;
import org.chu.patterns.factory.UrgenceBatimentFactory;
import org.chu.repositories.BatimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batiments")
public class BatimentController {
    
    @Autowired
    private BatimentRepository batimentRepository;
    
    @Autowired
    private AdministrationBatimentFactory administrationBatimentFactory;
    
    @Autowired
    private LaboratoireBatimentFactory laboratoireBatimentFactory;
    
    @Autowired
    private UrgenceBatimentFactory urgenceBatimentFactory;
    
    @GetMapping
    public List<Batiment> getAllBatiments() {
        return batimentRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Batiment> getBatimentById(@PathVariable Long id) {
        return batimentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/administration")
    public Batiment createAdministrationBatiment(@RequestBody Batiment batimentDetails) {
        Batiment batiment = administrationBatimentFactory.creerBatiment();
        batiment.setNom(batimentDetails.getNom());
        batiment.setEmplacement(batimentDetails.getEmplacement());
        batiment.setTaille(batimentDetails.getTaille());
        batiment.setDescription(batimentDetails.getDescription());
        return batimentRepository.save(batiment);
    }
    
    @PostMapping("/laboratoire")
    public Batiment createLaboratoireBatiment(@RequestBody Batiment batimentDetails) {
        Batiment batiment = laboratoireBatimentFactory.creerBatiment();
        batiment.setNom(batimentDetails.getNom());
        batiment.setEmplacement(batimentDetails.getEmplacement());
        batiment.setTaille(batimentDetails.getTaille());
        batiment.setDescription(batimentDetails.getDescription());
        return batimentRepository.save(batiment);
    }
    
    @PostMapping("/urgence")
    public Batiment createUrgenceBatiment(@RequestBody Batiment batimentDetails) {
        Batiment batiment = urgenceBatimentFactory.creerBatiment();
        batiment.setNom(batimentDetails.getNom());
        batiment.setEmplacement(batimentDetails.getEmplacement());
        batiment.setTaille(batimentDetails.getTaille());
        batiment.setDescription(batimentDetails.getDescription());
        return batimentRepository.save(batiment);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Batiment> updateBatiment(@PathVariable Long id, @RequestBody Batiment batimentDetails) {
        return batimentRepository.findById(id)
                .map(batiment -> {
                    batiment.setNom(batimentDetails.getNom());
                    batiment.setEmplacement(batimentDetails.getEmplacement());
                    batiment.setTaille(batimentDetails.getTaille());
                    batiment.setFonctionnalite(batimentDetails.getFonctionnalite());
                    batiment.setDescription(batimentDetails.getDescription());
                    return ResponseEntity.ok(batimentRepository.save(batiment));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatiment(@PathVariable Long id) {
        return batimentRepository.findById(id)
                .map(batiment -> {
                    batimentRepository.delete(batiment);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

