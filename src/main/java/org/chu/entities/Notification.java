package org.chu.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String message;
    private Date dateCreation;
    private String type;
    private boolean lue;
    
    @ManyToMany
    @JoinTable(
        name = "notification_destinataire",
        joinColumns = @JoinColumn(name = "notification_id"),
        inverseJoinColumns = @JoinColumn(name = "personnel_id")
    )
    private List<Personnel> destinataires;
}

