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
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;
    
    @ManyToMany
    @JoinTable(
        name = "section_patient",
        joinColumns = @JoinColumn(name = "section_id"),
        inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private List<Patient> patients;
}

