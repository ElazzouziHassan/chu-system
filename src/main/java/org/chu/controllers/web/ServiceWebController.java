package org.chu.controllers.web;

import org.chu.entities.Batiment;
import org.chu.entities.Personnel;
import org.chu.entities.Service;
import org.chu.patterns.adapter.SalleAdapter;
import org.chu.patterns.composite.GroupeServices;
import org.chu.patterns.composite.ServiceIndividuel;
import org.chu.repositories.BatimentRepository;
import org.chu.repositories.PersonnelRepository;
import org.chu.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/services")
public class ServiceWebController {
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private BatimentRepository batimentRepository;
    
    @Autowired
    private PersonnelRepository personnelRepository;
    
    @Autowired
    private SalleAdapter salleAdapter;
    
    @GetMapping
    public String listServices(Model model) {
        model.addAttribute("services", serviceRepository.findAll());
        model.addAttribute("title", "Gestion des Services - CHU El Jadida");
        return "services/list";
    }
    
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("service", new Service());
        model.addAttribute("batiments", batimentRepository.findAll());
        model.addAttribute("personnels", personnelRepository.findAll());
        model.addAttribute("title", "Nouveau Service - CHU El Jadida");
        return "services/form";
    }
    
    @PostMapping("/save")
    public String saveService(@ModelAttribute Service service, 
                             @RequestParam Long batimentId, 
                             @RequestParam Long chefServiceId) {
        Batiment batiment = batimentRepository.findById(batimentId)
                .orElseThrow(() -> new IllegalArgumentException("ID de bâtiment invalide: " + batimentId));
        Personnel chefService = personnelRepository.findById(chefServiceId)
                .orElseThrow(() -> new IllegalArgumentException("ID de chef de service invalide: " + chefServiceId));
        
        service.setBatiment(batiment);
        service.setChefService(chefService);
        
        serviceRepository.save(service);
        return "redirect:/services";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de service invalide: " + id));
        model.addAttribute("service", service);
        model.addAttribute("batiments", batimentRepository.findAll());
        model.addAttribute("personnels", personnelRepository.findAll());
        model.addAttribute("title", "Modifier Service - CHU El Jadida");
        return "services/form";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteService(@PathVariable Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de service invalide: " + id));
        serviceRepository.delete(service);
        return "redirect:/services";
    }
    
    @GetMapping("/view/{id}")
    public String viewService(@PathVariable Long id, Model model) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de service invalide: " + id));
        model.addAttribute("service", service);
        model.addAttribute("title", service.getNom() + " - CHU El Jadida");
        return "services/view";
    }
    
    @GetMapping("/adapter/{id}/{type}")
    public String adapterService(@PathVariable Long id, @PathVariable String type) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de service invalide: " + id));
        
        Service serviceAdapte;
        if ("reanimation-chirurgie".equals(type)) {
            serviceAdapte = salleAdapter.adapterReanimationEnChirurgie(service);
        } else if ("urgence-chirurgie".equals(type)) {
            serviceAdapte = salleAdapter.adapterUrgenceEnChirurgie(service);
        } else {
            throw new IllegalArgumentException("Type d'adaptation invalide: " + type);
        }
        
        serviceRepository.save(serviceAdapte);
        return "redirect:/services";
    }
    
    @GetMapping("/groupe")
    public String viewGroupeServices(Model model) {
        GroupeServices groupeServices = new GroupeServices("Tous les services", "Groupe contenant tous les services du CHU");
        
        for (Service service : serviceRepository.findAll()) {
            ServiceIndividuel serviceIndividuel = new ServiceIndividuel(service, 10); // Exemple avec 10 personnels
            groupeServices.ajouterService(serviceIndividuel);
        }
        
        model.addAttribute("groupeServices", groupeServices);
        model.addAttribute("title", "Groupe de Services - CHU El Jadida");
        return "services/groupe";
    }
}

