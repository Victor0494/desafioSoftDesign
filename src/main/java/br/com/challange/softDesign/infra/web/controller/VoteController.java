package br.com.challange.softDesign.infra.web.controller;

import br.com.challange.softDesign.application.dto.response.VoteResponseDTO;
import br.com.challange.softDesign.application.service.VoteService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/v1/vote")
@RequiredArgsConstructor
@Slf4j
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/{topicId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "create a new vote for a topic"),
            @ApiResponse(responseCode = "404", description = "session not found"),
            @ApiResponse(responseCode = "404", description = "topic not found"),
            @ApiResponse(responseCode = "404", description = "session already finished"),
    })
    public ResponseEntity<VoteResponseDTO> createVote(@PathVariable Long topicId,
                                                      @RequestParam String userId,
                                                      @RequestParam boolean vote,
                                                      @RequestParam(defaultValue = "true", required = false) boolean local) {
        log.info("VoteController.createVote - Start - Input: topicId: {}, userId: {}", topicId, userId);
        VoteResponseDTO response = voteService.createVote(topicId, userId, vote, local);

        URI location = URI.create("/topic/" + response.id());

        log.debug("VoteController.createVote - End - Output: voteResponse: {}", response);
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{topicId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get the vote result"),
    })
    public ResponseEntity<Map<String, Long>> getVoteResult(@PathVariable Long topicId) {
        log.info("VoteController.createVote - Start - Input: topicId: {}", topicId);

        Map<String, Long> response = voteService.getCountVotes(topicId);

        log.debug("VoteController.getVoteResult - End - Output: voteResponse: {}", response);
        return ResponseEntity.ok(response);
    }
}
