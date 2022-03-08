package com.example.server;

import com.example.server.models.Server;
import com.example.server.repository.ServerRepository;

import java.util.Arrays;

import com.example.server.enums.Status;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepository serverRepository) {
		return args -> {
			serverRepository.save(new Server(null, "192.168.1.70", "IOS", "16 GB", "Iphone de Tom", 
				"http://localhost:8080/server/image/server1.png", Status.SERVER_UP));
			serverRepository.save(new Server(null, "192.168.0.160", "Kali Linux", "32 GB", "Professional PC", 
				"http://localhost:8080/server/image/server2.png", Status.SERVER_UP));
			serverRepository.save(new Server(null, "192.168.0.3", "Ubuntu Linux", "32 GB", "Web Server", 
				"http://localhost:8080/server/image/server3.png", Status.SERVER_DOWN));
			serverRepository.save(new Server(null, "192.168.0.22", "Kali Linux", "64 GB", "Mail Server", 
				"http://localhost:8080/server/image/server4.png", Status.SERVER_UP));
		};
	}

	@Bean
	public CorsFilter CorsFilter() {
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration();
        
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept", "Jwt-Token", "Origin Accept", "X-Requested-With"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept", "Jwt-Token", "Origin Accept", "Filename", "Access-Control-Allow-Credentials"));

        source.registerCorsConfiguration("/**",configuration);
        return new CorsFilter(source);
	}

}
