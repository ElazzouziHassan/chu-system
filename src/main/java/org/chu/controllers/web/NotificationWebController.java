package org.chu.controllers.web;

import org.chu.entities.Notification;
import org.chu.entities.Personnel;
import org.chu.patterns.observer.ServiceSubjectImpl;
import org.chu.repositories.NotificationRepository;
import org.chu.repositories.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationWebController {
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private PersonnelRepository personnelRepository;
    
    @Autowired
    private ServiceSubjectImpl serviceSubject;
    
    @GetMapping
    public String listNotifications(Model model) {
        model.addAttribute("notifications", notificationRepository.findAll());
        model.addAttribute("title", "Notifications - CHU El Jadida");
        return "notifications/list";
    }
    
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("notification", new Notification());
        model.addAttribute("personnels", personnelRepository.findAll());
        model.addAttribute("title", "Nouvelle Notification - CHU El Jadida");
        return "notifications/form";
    }
    
    @PostMapping("/save")
    public String saveNotification(@ModelAttribute Notification notification, 
                                  @RequestParam(value = "destinataireIds", required = false) List<Long> destinataireIds) {
        notification.setDateCreation(new Date());
        notification.setLue(false);
        
        if (destinataireIds != null && !destinataireIds.isEmpty()) {
            List<Personnel> destinataires = personnelRepository.findAllById(destinataireIds);
            notification.setDestinataires(destinataires);
        }
        
        notificationRepository.save(notification);
        return "redirect:/notifications";
    }
    
    @GetMapping("/diffuser")
    public String showDiffuserForm(Model model) {
        model.addAttribute("title", "Diffuser une Notification - CHU El Jadida");
        return "notifications/diffuser";
    }
    
    @PostMapping("/diffuser")
    public String diffuserNotification(@RequestParam String message) {
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
        
        return "redirect:/notifications";
    }
    
    @GetMapping("/marquer-lue/{id}")
    public String marquerLue(@PathVariable Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de notification invalide: " + id));
        notification.setLue(true);
        notificationRepository.save(notification);
        return "redirect:/notifications";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteNotification(@PathVariable Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de notification invalide: " + id));
        notificationRepository.delete(notification);
        return "redirect:/notifications";
    }
}

