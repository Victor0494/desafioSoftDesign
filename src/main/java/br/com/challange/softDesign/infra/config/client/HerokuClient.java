package br.com.challange.softDesign.infra.config.client;

import br.com.challange.softDesign.application.dto.response.UserVoteStatusResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HerokuClient", url = "${application.client.heroku.url}")
public interface HerokuClient {

    @GetMapping("/users/{cpf}")
    UserVoteStatusResponseDTO validateCPF(@PathVariable("cpf") String cpf);
}
