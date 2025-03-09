package org.chu.controllers.web;

import org.chu.entities.Patient;
import org.chu.entities.Section;
import org.chu.patterns.abstractfactory.UtilisateurFactory;
import org.chu.patterns.facade.GestionPatientFacade;
import org.chu.repositories.PatientRepository;
import org.chu.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientWebController {
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private SectionRepository sectionRepository;
    
    @Autowired
    private GestionPatientFacade gestionPatientFacade;
    
    @Autowired
    private UtilisateurFactory utilisateurFactory;
    
    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("title", "Gestion des Patients - CHU El Jadida");
        return "patients/list";
    }
    
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("sections", sectionRepository.findAll());
        model.addAttribute("title", "Nouveau Patient - CHU El Jadida");
        return "patients/form";
    }
    
    @PostMapping("/save")
    public String savePatient(@ModelAttribute Patient patient, @RequestParam(required = false) Long sectionId) {
        if (patient.getId() == null) {
            // Nouveau patient
            gestionPatientFacade.enregistrerPatient(patient, sectionId);
        } else {
            // Mise à jour d'un patient existant
            patientRepository.save(patient);
        }
        return "redirect:/patients";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de patient invalide: " + id));
        model.addAttribute("patient", patient);
        model.addAttribute("sections", sectionRepository.findAll());
        model.addAttribute("title", "Modifier Patient - CHU El Jadida");
        return "patients/form";
    }
    
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de patient invalide: " + id));
        patientRepository.delete(patient);
        return "redirect:/patients";
    }
    
    @GetMapping("/view/{id}")
    public String viewPatient(@PathVariable Long id, Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de patient invalide: " + id));
        model.addAttribute("patient", patient);
        model.addAttribute("title", patient.getNom() + " " + patient.getPrenom() + " - CHU El Jadida");
        return "patients/view";
    }
    
    @GetMapping("/transfert/{id}")
    public String showTransfertForm(@PathVariable Long id, Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de patient invalide: " + id));
        model.addAttribute("patient", patient);
        model.addAttribute("sections", sectionRepository.findAll());
        model.addAttribute("title", "Transfert de Patient - CHU El Jadida");
        return "patients/transfert";
    }
    
    @PostMapping("/transfert")
    public String transfertPatient(
            @RequestParam Long patientId,
            @RequestParam Long sectionSourceId,
            @RequestParam Long sectionDestinationId) {
        gestionPatientFacade.transfererPatient(patientId, sectionSourceId, sectionDestinationId);
        return "redirect:/patients/view/" + patientId;
    }
    
    @GetMapping("/sortie/{id}")
    public String sortiePatient(@PathVariable Long id) {
        gestionPatientFacade.sortiePatient(id);
        return "redirect:/patients";
    }
}

