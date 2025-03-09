package org.chu.controllers.web;

import org.chu.entities.Batiment;
import org.chu.patterns.factory.AdministrationBatimentFactory;
import org.chu.patterns.factory.LaboratoireBatimentFactory;
import org.chu.patterns.factory.UrgenceBatimentFactory;
import org.chu.repositories.BatimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/batiments")
public class BatimentWebController {
    
    @Autowired
    private BatimentRepository batimentRepository;
    
    @Autowired
    private AdministrationBatimentFactory administrationBatimentFactory;
    
    @Autowired
    private LaboratoireBatimentFactory laboratoireBatimentFactory;
    
    @Autowired
    private UrgenceBatimentFactory urgenceBatimentFactory;
    
    @GetMapping
    public String listBatiments(Model model) {
        model.addAttribute("batiments", batimentRepository.findAll());
        model.addAttribute("title", "Gestion des Bâtiments - CHU El Jadida");
        return "batiments/list";
    }
    
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("batiment", new Batiment());
        model.addAttribute("title", "Nouveau Bâtiment - CHU El Jadida");
        return "batiments/form";
    }
    
    @PostMapping("/save")
    public String saveBatiment(@ModelAttribute Batiment batiment, @RequestParam String type) {
        Batiment newBatiment;
        
        switch (type) {
            case "ADMINISTRATION":
                newBatiment = administrationBatimentFactory.creerBatiment();
                break;
            case "LABORATOIRE":
                newBatiment = laboratoireBatimentFactory.creerBatiment();
                break;
            case "URGENCE":
                newBatiment = urgenceBatimentFactory.creerBatiment();
                break;
            default:
                newBatiment = new Batiment();
                newBatiment.setCategorie(type);
        }
        
        newBatiment.setNom(batiment.getNom());
        newBatiment.setEmplacement(batiment.getEmplacement());
        newBatiment.setTaille(batiment.getTaille());
        newBatiment.setDescription(batiment.getDescription());
        
        batimentRepository.save(newBatiment);
        return "redirect:/batiments";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Batiment batiment = batimentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de bâtiment invalide: " + id));
        model.addAttribute("batiment", batiment);
        model.addAttribute("title", "Modifier Bâtiment - CHU El Jadida");
        return "batiments/form";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteBatiment(@PathVariable Long id) {
        Batiment batiment = batimentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de bâtiment invalide: " + id));
        batimentRepository.delete(batiment);
        return "redirect:/batiments";
    }
    
    @GetMapping("/view/{id}")
    public String viewBatiment(@PathVariable Long id, Model model) {
        Batiment batiment = batimentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de bâtiment invalide: " + id));
        model.addAttribute("batiment", batiment);
        model.addAttribute("title", batiment.getNom() + " - CHU El Jadida");
        return "batiments/view";
    }
}

