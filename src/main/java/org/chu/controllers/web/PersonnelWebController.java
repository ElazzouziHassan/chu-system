package org.chu.controllers.web;

import org.chu.entities.*;
import org.chu.patterns.abstractfactory.UtilisateurFactory;
import org.chu.repositories.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/personnel")
public class PersonnelWebController {
    
    @Autowired
    private PersonnelRepository personnelRepository;
    
    @Autowired
    private UtilisateurFactory utilisateurFactory;
    
    @GetMapping
    public String listPersonnel(Model model) {
        model.addAttribute("personnels", personnelRepository.findAll());
        model.addAttribute("title", "Gestion du Personnel - CHU El Jadida");
        return "personnel/list";
    }
    
    @GetMapping("/new/{type}")
    public String showNewForm(@PathVariable String type, Model model) {
        Personnel personnel;
        
        switch (type.toUpperCase()) {
            case "MEDECIN":
                personnel = utilisateurFactory.creerMedecin();
                break;
            case "INFIRMIER":
                personnel = utilisateurFactory.creerInfirmier();
                break;
            case "ADMINISTRATEUR":
                personnel = utilisateurFactory.creerAdministrateur();
                break;
            case "AGENTAIDE":
                personnel = utilisateurFactory.creerAgentAide();
                break;
            case "DIRECTEUR":
                personnel = utilisateurFactory.creerDirecteur();
                break;
            default:
                throw new IllegalArgumentException("Type de personnel invalide: " + type);
        }
        
        model.addAttribute("personnel", personnel);
        model.addAttribute("type", type);
        model.addAttribute("title", "Nouveau " + type + " - CHU El Jadida");
        return "personnel/form-" + type.toLowerCase();
    }
    
    @PostMapping("/save/{type}")
    public String savePersonnel(@PathVariable String type, @ModelAttribute Personnel personnel) {
        personnelRepository.save(personnel);
        return "redirect:/personnel";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Personnel personnel = personnelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de personnel invalide: " + id));
        
        String type;
        if (personnel instanceof Medecin) {
            type = "medecin";
        } else if (personnel instanceof Infirmier) {
            type = "infirmier";
        } else if (personnel instanceof Administrateur) {
            type = "administrateur";
        } else if (personnel instanceof AgentAide) {
            type = "agentaide";
        } else if (personnel instanceof Directeur) {
            type = "directeur";
        } else {
            throw new IllegalArgumentException("Type de personnel inconnu");
        }
        
        model.addAttribute("personnel", personnel);
        model.addAttribute("type", type);
        model.addAttribute("title", "Modifier " + type + " - CHU El Jadida");
        return "personnel/form-" + type;
    }
    
    @GetMapping("/delete/{id}")
    public String deletePersonnel(@PathVariable Long id) {
        Personnel personnel = personnelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de personnel invalide: " + id));
        personnelRepository.delete(personnel);
        return "redirect:/personnel";
    }
    
    @GetMapping("/view/{id}")
    public String viewPersonnel(@PathVariable Long id, Model model) {
        Personnel personnel = personnelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de personnel invalide: " + id));
        model.addAttribute("personnel", personnel);
        model.addAttribute("title", personnel.getNom() + " " + personnel.getPrenom() + " - CHU El Jadida");
        return "personnel/view";
    }
}

