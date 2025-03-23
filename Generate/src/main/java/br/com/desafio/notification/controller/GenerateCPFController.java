package br.com.desafio.notification.controller;

import br.com.desafio.notification.constant.VoteStatus;
import br.com.desafio.notification.dto.UserVoteStatusDTO;
import br.com.desafio.notification.utils.GenerateCpf;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GenerateCPFController {

    @GetMapping("/generate")
    public String generateCPF(@RequestParam(defaultValue = "true") boolean isValid) {
        return GenerateCpf.generateCpf(isValid);
    }

    @GetMapping("/validate")
    public ResponseEntity<UserVoteStatusDTO> validateCPF(@RequestParam String cpf) {
        try {
            return ResponseEntity.ok(UserVoteStatusDTO.builder()
                    .status(GenerateCpf.validateCPF(cpf) ? VoteStatus.ABLE_TO_VOTE : VoteStatus.UNABLE_TO_VOTE).build());
        } catch (ResponseStatusException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
