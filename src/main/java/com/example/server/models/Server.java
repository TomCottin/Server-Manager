package com.example.server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.AUTO;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.example.server.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
// Permet de choper le getter et setter plus easy
@Data
// Constructeur avec 0 argument
@NoArgsConstructor
// Constructeur avec tous les arguments
@AllArgsConstructor
public class Server {
    
    // L'Id se génère automatiquement de 1 en 1
    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    // L'@IP n'aura pas de doublon
    @Column(unique = true)
    // Contrainte qui permet d'empecher la validation de la requête si @IP vide ou null
    @NotEmpty(message = "IP address cannot be empty nor null")
    private String ipAddress;
    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    private Status status;

}
