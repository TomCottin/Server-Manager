package com.example.server.service.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import javax.transaction.Transactional;

import static com.example.server.enums.Status.SERVER_UP;
import static com.example.server.enums.Status.SERVER_DOWN;

import com.example.server.models.Server;
import com.example.server.repository.ServerRepository;
import com.example.server.service.ServerService;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// Le constructeur est fait par lombock qui s'occupe de l'injection
@RequiredArgsConstructor
@Service
@Transactional
// Print dans la console pour que l'on voit ce qui s'y passe 
@Slf4j

public class ServerServiceImplementation implements ServerService {

    private final ServerRepository serverRepository;
    
    @Override
    public Server create(Server server) {
        log.info("Saving new server : {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by Id : {}", id);
        return serverRepository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server : {}", server.getName());
        // La fonction save est capable de reconnaître s'il s'agit d'un update ou d'un create
        // Methode similaire a create
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by ID : {}", id);
        serverRepository.deleteById(id);
        return true;
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP : {}", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
        serverRepository.save(server);
        return server;
    }

    public String setServerImageUrl() {
        String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
    }
}
