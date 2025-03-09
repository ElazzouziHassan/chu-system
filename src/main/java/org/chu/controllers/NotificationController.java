package org.chu.controllers;

import org.chu.entities.Notification;
import org.chu.entities.Personnel;
import org.chu.patterns.observer.ServiceObserver;
import org.chu.patterns.observer.ServiceSubjectImpl;
import org.chu.repositories.NotificationRepository;
import org.chu.repositories.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private PersonnelRepository personnelRepository;
    
    @Autowired
    private ServiceSubjectImpl serviceSubject;
    
    @Autowired
    private List<ServiceObserver> serviceObservers;
    
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return notificationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        notification.setDateCreation(new Date());
        notification.setLue(false);
        return notificationRepository.save(notification);
    }
    
    @PostMapping("/diffuser")
    public ResponseEntity<Void> diffuserNotification(@RequestBody String message) {
        serviceSubject.envoyerNotification(message);
        
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDateCreation(new Date());
        notification.setType("DIFFUSION");
        notification.setLue(false);
        
        // Ajouter tous les personnels comme destinataires
        List<Personnel> personnels = personnelRepository.findAll();
        notification.setDestinataires(personnels);
        
        notificationRepository.save(notification);
        
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification notificationDetails) {
        return notificationRepository.findById(id)
                .map(notification -> {
                    notification.setMessage(notificationDetails.getMessage());
                    notification.setType(notificationDetails.getType());
                    notification.setLue(notificationDetails.isLue());
                    return ResponseEntity.ok(notificationRepository.save(notification));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        return notificationRepository.findById(id)
                .map(notification -> {
                    notificationRepository.delete(notification);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

