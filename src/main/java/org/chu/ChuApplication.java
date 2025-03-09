package org.chu;

import org.chu.patterns.observer.AdministrationObserver;
import org.chu.patterns.observer.ServiceObserver;
import org.chu.patterns.observer.ServiceSubjectImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChuApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChuApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner setupObservers(ServiceSubjectImpl serviceSubject, AdministrationObserver administrationObserver) {
        return args -> {
            // Enregistrer l'observateur d'administration
            serviceSubject.ajouterObservateur(administrationObserver);
            System.out.println("Observateur d'administration enregistré");
        };
    }
}

