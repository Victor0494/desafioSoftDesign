package br.com.challange.softDesign.infra.config.client;

import br.com.challange.softDesign.application.dto.response.UserVoteStatusResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "LocalClient", url = "${application.client.local.url}")
public interface LocalClient {

    @GetMapping("/generate")
    String generateCPF(@RequestParam("isValid") boolean isValid);

    @GetMapping("/validate")
    UserVoteStatusResponseDTO validateCPF(@RequestParam String cpf);

    @GetMapping("/users/{cpf}")
    UserVoteStatusResponseDTO checkVoteStatus(@PathVariable("cpf") String cpf);
}
