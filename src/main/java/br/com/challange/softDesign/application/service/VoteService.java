package br.com.challange.softDesign.application.service;



import br.com.challange.softDesign.application.dto.response.VoteResponseDTO;

import java.util.Map;

public interface VoteService {

    VoteResponseDTO createVote(Long topicId, String userId, boolean vote, boolean local);

    Map<String, Long> getCountVotes(Long topicId);
}
