package org.chu.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String type;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "batiment_id")
    private Batiment batiment;
    
    @ManyToOne
    @JoinColumn(name = "chef_service_id")
    private Personnel chefService;
}

