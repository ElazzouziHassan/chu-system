package org.chu.patterns.facade;

import org.chu.entities.Patient;
import org.chu.entities.Section;
import org.chu.repositories.PatientRepository;
import org.chu.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Patron Facade pour simplifier la gestion des patients
 */
@Component
public class GestionPatientFacade {
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private SectionRepository sectionRepository;
    
    /**
     * Enregistre un nouveau patient et l'affecte à une section
     */
    public Patient enregistrerPatient(Patient patient, Long sectionId) {
        // Sauvegarde du patient
        Patient patientSauvegarde = patientRepository.save(patient);
        
        // Affectation à la section
        if (sectionId != null) {
            Optional<Section> sectionOpt = sectionRepository.findById(sectionId);
            if (sectionOpt.isPresent()) {
                Section section = sectionOpt.get();
                List<Patient> patients = section.getPatients();
                patients.add(patientSauvegarde);
                section.setPatients(patients);
                sectionRepository.save(section);
            }
        }
        
        return patientSauvegarde;
    }
    
    /**
     * Récupère les informations complètes d'un patient
     */
    public Patient obtenirInformationsPatient(Long patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }
    
    /**
     * Transfère un patient d'une section à une autre
     */
    public void transfererPatient(Long patientId, Long sectionSourceId, Long sectionDestinationId) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        Optional<Section> sectionSourceOpt = sectionRepository.findById(sectionSourceId);
        Optional<Section> sectionDestinationOpt = sectionRepository.findById(sectionDestinationId);
        
        if (patientOpt.isPresent() && sectionSourceOpt.isPresent() && sectionDestinationOpt.isPresent()) {
            Patient patient = patientOpt.get();
            Section sectionSource = sectionSourceOpt.get();
            Section sectionDestination = sectionDestinationOpt.get();
            
            // Retirer le patient de la section source
            List<Patient> patientsSource = sectionSource.getPatients();
            patientsSource.remove(patient);
            sectionSource.setPatients(patientsSource);
            sectionRepository.save(sectionSource);
            
            // Ajouter le patient à la section destination
            List<Patient> patientsDestination = sectionDestination.getPatients();
            patientsDestination.add(patient);
            sectionDestination.setPatients(patientsDestination);
            sectionRepository.save(sectionDestination);
        }
    }
    
    /**
     * Sortie d'un patient
     */
    public void sortiePatient(Long patientId) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            
            // Retirer le patient de toutes les sections
            List<Section> sections = patient.getSections();
            for (Section section : sections) {
                List<Patient> patients = section.getPatients();
                patients.remove(patient);
                section.setPatients(patients);
                sectionRepository.save(section);
            }
            
            // Mise à jour du statut du patient ou suppression selon les besoins
            patient.setSituationMedicale("Sorti");
            patientRepository.save(patient);
        }
    }
}

