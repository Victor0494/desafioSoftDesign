package br.com.desafio.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class GenerateApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GenerateApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
    }


}
