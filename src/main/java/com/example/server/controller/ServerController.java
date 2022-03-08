package com.example.server.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import static com.example.server.enums.Status.SERVER_UP;
import com.example.server.models.Response;
import com.example.server.models.Server;
import com.example.server.service.implementation.ServerServiceImplementation;

import org.springframework.http.ResponseEntity;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static  org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server")
// Le constructeur est fait par lombock qui s'occupe de l'injection
@RequiredArgsConstructor
public class ServerController {
    
    private final ServerServiceImplementation serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(LocalDate.now())
                .data(Map.of("servers", serverService.list(30)))
                .message("Servers retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(LocalDate.now())
                .data(Map.of("server", serverService.get(id)))
                .message("Server retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(LocalDate.now())
                .data(Map.of("server", server))
                .message(server.getStatus() == SERVER_UP ? "Ping succes" : "Ping failed")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(LocalDate.now())
                .data(Map.of("server", serverService.create(server)))
                .message("Server created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
            Response.builder()
                .timeStamp(LocalDate.now())
                .data(Map.of("deleted", serverService.delete(id)))
                .message("Server deleted")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @GetMapping(path = "image/{filename}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/images/" + filename));
    }

}
