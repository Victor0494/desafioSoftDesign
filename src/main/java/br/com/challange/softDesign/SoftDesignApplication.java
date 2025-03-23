package br.com.challange.softDesign;

import br.com.challange.softDesign.application.model.Session;
import br.com.challange.softDesign.infra.web.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
@RequiredArgsConstructor
public class SoftDesignApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftDesignApplication.class, args);
	}

}
