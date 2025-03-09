package org.chu.controllers;

import org.chu.entities.Service;
import org.chu.patterns.adapter.SalleAdapter;
import org.chu.patterns.composite.GroupeServices;
import org.chu.patterns.composite.ServiceComponent;
import org.chu.patterns.composite.ServiceIndividuel;
import org.chu.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private SalleAdapter salleAdapter;
    
    @GetMapping
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Service createService(@RequestBody Service service) {
        return serviceRepository.save(service);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Long id, @RequestBody Service serviceDetails) {
        return serviceRepository.findById(id)
                .map(service -> {
                    service.setNom(serviceDetails.getNom());
                    service.setType(serviceDetails.getType());
                    service.setDescription(serviceDetails.getDescription());
                    service.setBatiment(serviceDetails.getBatiment());
                    service.setChefService(serviceDetails.getChefService());
                    return ResponseEntity.ok(serviceRepository.save(service));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(service -> {
                    serviceRepository.delete(service);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/adapter-reanimation-chirurgie/{id}")
    public Service adapterReanimationEnChirurgie(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(salleAdapter::adapterReanimationEnChirurgie)
                .map(serviceRepository::save)
                .orElse(null);
    }
    
    @PostMapping("/adapter-urgence-chirurgie/{id}")
    public Service adapterUrgenceEnChirurgie(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(salleAdapter::adapterUrgenceEnChirurgie)
                .map(serviceRepository::save)
                .orElse(null);
    }
    
    @GetMapping("/groupe")
    public ResponseEntity<GroupeServices> getGroupeServices() {
        List<Service> services = serviceRepository.findAll();
        
        if (services.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        GroupeServices groupeServices = new GroupeServices("Tous les services", "Groupe contenant tous les services du CHU");
        
        for (Service service : services) {
            ServiceIndividuel serviceIndividuel = new ServiceIndividuel(service, 10); // Exemple avec 10 personnels
            groupeServices.ajouterService(serviceIndividuel);
        }
        
        return ResponseEntity.ok(groupeServices);
    }
}

