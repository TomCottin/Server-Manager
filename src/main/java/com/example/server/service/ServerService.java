package com.example.server.service;

import java.io.IOException;
import java.util.Collection;

import com.example.server.models.Server;

public interface ServerService {

    Server create(Server server);
    Collection<Server> list(int limit);
    Server get(Long id);
    Server update(Server server);
    Boolean delete(Long id);
    Server ping(String server) throws IOException;

}
