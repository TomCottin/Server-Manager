package com.example.server.repository;

import com.example.server.models.Server;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
    
    // Permet de selectionner un server en comparant les @IP de la BDD avec celle passée en argument
    // Possible car l'attribut ipAddress est unique
    Server findByIpAddress(String ipAdress);

}
