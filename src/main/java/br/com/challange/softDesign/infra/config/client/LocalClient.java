package br.com.challange.softDesign.infra.config.client;

import br.com.challange.softDesign.application.dto.response.UserVoteStatusResponseDTO;
import br.com.challange.softDesign.infra.config.fallback.LocalClientFallback;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "LocalClient", url = "${application.client.local.url}", fallback = LocalClientFallback.class)
public interface LocalClient {

    @GetMapping("/validate")
    @CircuitBreaker(name = "localService", fallbackMethod = "fallbackValidateCPF")
    UserVoteStatusResponseDTO validateCPF(@RequestParam String cpf);

}
