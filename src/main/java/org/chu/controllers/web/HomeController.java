package org.chu.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "CHU El Jadida - Système de Gestion");
        return "home";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "Tableau de Bord - CHU El Jadida");
        return "dashboard";
    }
}

