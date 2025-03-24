package br.com.challange.softDesign.application.service.impl;

import br.com.challange.softDesign.application.exception.*;
import br.com.challange.softDesign.application.model.Voters;
import br.com.challange.softDesign.infra.config.client.HerokuClient;
import br.com.challange.softDesign.infra.config.client.LocalClient;
import br.com.challange.softDesign.domain.constant.VoteStatus;
import br.com.challange.softDesign.application.dto.response.UserVoteStatusResponseDTO;
import br.com.challange.softDesign.application.dto.response.VoteResponseDTO;
import br.com.challange.softDesign.application.model.Topic;
import br.com.challange.softDesign.application.model.Vote;
import br.com.challange.softDesign.infra.web.repository.SessionRepository;
import br.com.challange.softDesign.infra.web.repository.TopicRepository;
import br.com.challange.softDesign.application.model.Session;
import br.com.challange.softDesign.infra.web.repository.UserRepository;
import br.com.challange.softDesign.infra.web.repository.VoteRepository;
import br.com.challange.softDesign.application.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import static br.com.challange.softDesign.domain.constant.ErrorMessage.*;
import static br.com.challange.softDesign.domain.utils.CpfUtils.maskCpf;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    private final TopicRepository topicRepository;

    private final SessionRepository sessionRepository;

    private final UserRepository userRepository;

    private final LocalClient localClient;

    private final HerokuClient herokuClient;

    private final ObjectMapper mapper;

    //Quando um novo voto é criado, a contagem de votos armazenada no cache se torna desatualizada.
    // Por isso utilizei o cacheEvict para remover qualquer informação desatualizada
    @Override
    @CacheEvict(value = "voteCountCache", key = "#topicId")
    public VoteResponseDTO createVote(Long topicId, String userId, boolean vote, boolean local) {
        log.info("VoteServiceImpl.createVote - Start - Input: topicId: {}, userId: {}", topicId, userId);

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(TOPIC_NOT_FOUND.getMessage()));

        Session session = sessionRepository.findByTopic(topic)
                .orElseThrow(() -> new SessionNotFoundException(SESSION_NOT_FOUND.getMessage()));

        if(LocalDateTime.now().isAfter(session.getFinish())) {
            throw new SessionFinishedException(SESSION_FINISHED.getMessage());
        }

        Voters voters = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.getMessage()));

        validateUserCpf(voters.getCpf(), topic, local);

        Vote voteResponse = voteRepository.save(Vote.builder().topic(topic).cpf(voters.getCpf()).vote(vote).build());
        voteResponse.setCpf(maskCpf(voteResponse.getCpf()));

        VoteResponseDTO voteResponseDTO = mapper.convertValue(voteResponse, VoteResponseDTO.class);

        log.debug("VoteServiceImpl.createVote - End - Output: voteResponseDTO: {}", voteResponseDTO);
        return voteResponseDTO;
    }

    //Esse método busca a contagem de votos do banco de dados e pode ser chamado diversas vezes,
    // principalmente quando a API é acessada repetidamente para consultar o resultado da votação.
    @Override
    @Cacheable(value = "voteCountCache", key = "#topicId")
    public Map<String, Long> getCountVotes(Long topicId) {
        log.info("VoteServiceImpl.getCountVotes - Start - Input: topicId: {}", topicId);

        long yesVote = voteRepository.countVotesByTopicAndVote(topicId, true);
        long noVote = voteRepository.countVotesByTopicAndVote(topicId, false);

        Map<String, Long> response = Map.of("Yes", yesVote, "No", noVote);

        log.debug("VoteServiceImpl.createVote - End - Output: response: {}", response);
        return response;
    }

    private void validateUserCpf(String cpf, Topic topic, boolean local) {
        UserVoteStatusResponseDTO response;
        try {
            response = local ? localClient.validateCPF(cpf) : herokuClient.validateCPF(cpf);
        } catch (FeignException.NotFound e) {
            throw new InvalidDocumentException(INVALID_DOCUMENT.getMessage());
        }

        if(response.status().equals(VoteStatus.UNABLE_TO_VOTE)) {
            throw new UnableVoteException(UNABLE_TO_VOTE.getMessage());
        }

        if(voteRepository.existsByTopicAndCpf(topic, cpf)) {
            throw new AlreadyVotedException(ALREADY_VOTED.getMessage());
        }
    }
}
