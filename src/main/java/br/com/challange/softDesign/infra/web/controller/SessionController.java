package br.com.challange.softDesign.infra.web.controller;

import br.com.challange.softDesign.application.dto.response.SessionResponseDTO;
import br.com.challange.softDesign.application.service.SessionService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/session")
@Slf4j
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "create a new session for a topic"),
            @ApiResponse(responseCode = "404", description = "session not found"),
            @ApiResponse(responseCode = "400", description = "invalid session")
    })
    public ResponseEntity<SessionResponseDTO> createSession(@RequestParam Long topicId, Integer duration) {
        log.info("SessionController.createSession - Start - Input: topicId: {}, duration: {}", topicId, duration);
        SessionResponseDTO response = sessionService.createSession(topicId, duration);

        URI location = URI.create("/session/" + response.id());

        log.debug("SessionController.createSession - End - Output: sessionResponse: {}", response);
        return ResponseEntity.created(location).body(response);
    }
}
