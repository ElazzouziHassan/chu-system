package org.chu.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String prenom;
    private String code;
    private String situationMedicale;
    private int age;
    private String type;
    
    @ManyToMany(mappedBy = "patients")
    private List<Section> sections;
}

