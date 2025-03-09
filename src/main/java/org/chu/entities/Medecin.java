package org.chu.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@DiscriminatorValue("MEDECIN")
public class Medecin extends Personnel {
    private String specialite;
    
    @OneToMany(mappedBy = "medecin")
    private List<Section> sections;
}

