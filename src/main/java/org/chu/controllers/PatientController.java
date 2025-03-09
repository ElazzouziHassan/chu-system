package org.chu.controllers;

import org.chu.entities.Patient;
import org.chu.patterns.facade.GestionPatientFacade;
import org.chu.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private GestionPatientFacade gestionPatientFacade;
    
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(gestionPatientFacade.obtenirInformationsPatient(id));
    }
    
    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }
    
    @PostMapping("/enregistrer/{sectionId}")
    public Patient enregistrerPatient(@RequestBody Patient patient, @PathVariable Long sectionId) {
        return gestionPatientFacade.enregistrerPatient(patient, sectionId);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setNom(patientDetails.getNom());
                    patient.setPrenom(patientDetails.getPrenom());
                    patient.setCode(patientDetails.getCode());
                    patient.setSituationMedicale(patientDetails.getSituationMedicale());
                    patient.setAge(patientDetails.getAge());
                    patient.setType(patientDetails.getType());
                    return ResponseEntity.ok(patientRepository.save(patient));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patientRepository.delete(patient);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/transferer/{patientId}/{sectionSourceId}/{sectionDestinationId}")
    public ResponseEntity<Void> transfererPatient(
            @PathVariable Long patientId,
            @PathVariable Long sectionSourceId,
            @PathVariable Long sectionDestinationId) {
        gestionPatientFacade.transfererPatient(patientId, sectionSourceId, sectionDestinationId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/sortie/{patientId}")
    public ResponseEntity<Void> sortiePatient(@PathVariable Long patientId) {
        gestionPatientFacade.sortiePatient(patientId);
        return ResponseEntity.ok().build();
    }
}

