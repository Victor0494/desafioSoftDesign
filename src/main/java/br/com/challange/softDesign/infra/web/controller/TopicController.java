package br.com.challange.softDesign.infra.web.controller;

import br.com.challange.softDesign.application.dto.request.TopicRequestDTO;
import br.com.challange.softDesign.application.service.TopicService;
import br.com.challange.softDesign.application.dto.response.TopicResponseDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/topic")
@Slf4j
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    @ApiResponse(responseCode = "201", description = "create a new topic")
    public ResponseEntity<TopicResponseDTO> createTopic(@RequestBody TopicRequestDTO topicRequestDTO) {
        log.info("TopicController.createTopic - Start - Input: topicDTO: {}", topicRequestDTO);

        TopicResponseDTO response = topicService.createTopic(topicRequestDTO);

        URI location = URI.create("/topic/" + response.id());

        log.debug("TopicController.createTopic - End - Output: topicResponse: {}", response);
        return ResponseEntity.created(location).body(response);
    }
}
