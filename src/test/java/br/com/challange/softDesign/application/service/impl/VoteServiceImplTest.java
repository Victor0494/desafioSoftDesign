package br.com.challange.softDesign.application.service.impl;

import br.com.challange.softDesign.application.dto.request.TopicRequestDTO;
import br.com.challange.softDesign.application.dto.response.UserVoteStatusResponseDTO;
import br.com.challange.softDesign.application.dto.response.VoteResponseDTO;
import br.com.challange.softDesign.application.exception.*;
import br.com.challange.softDesign.application.model.Session;
import br.com.challange.softDesign.application.model.Topic;
import br.com.challange.softDesign.application.model.Voters;
import br.com.challange.softDesign.application.model.Vote;
import br.com.challange.softDesign.domain.constant.VoteStatus;
import br.com.challange.softDesign.infra.config.client.LocalClient;
import br.com.challange.softDesign.infra.web.repository.SessionRepository;
import br.com.challange.softDesign.infra.web.repository.TopicRepository;
import br.com.challange.softDesign.infra.web.repository.UserRepository;
import br.com.challange.softDesign.infra.web.repository.VoteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static br.com.challange.softDesign.domain.constant.ErrorMessage.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoteServiceImplTest {

    public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    public static final long TOPIC_ID = 1L;
    public static final String uuid = UUID.randomUUID().toString();

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LocalClient client;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private VoteServiceImpl voteService;

    @Test
    @DisplayName("should return the result vote from an user")
    void shouldReturnTheResultVoteFromAnUser() {
        String description = "test";
        String cpf = "02588786065";
        boolean vote = true;
        Topic topic = getTopic(description, TOPIC_ID);
        TopicRequestDTO topicRequestDTO = getTopicDTO(description);
        Session session = getSession(topic);
        Vote voteResponse = getVoteResponse(cpf, topic, vote);
        VoteResponseDTO voteResponseDTO = getVoteResponseDTO(cpf, topicRequestDTO, vote);

        when(topicRepository.findById(TOPIC_ID))
                .thenReturn(Optional.ofNullable(topic));
        when(sessionRepository.findByTopic(topic))
                .thenReturn(Optional.ofNullable(session));
        when(client.validateCPF(cpf))
                .thenReturn(UserVoteStatusResponseDTO.builder().status(VoteStatus.ABLE_TO_VOTE).build());
        when(voteRepository.existsByTopicAndCpf(topic, cpf))
                .thenReturn(false);
        when(voteRepository.save(any(Vote.class)))
                .thenReturn(voteResponse);
        when(mapper.convertValue(voteResponse, VoteResponseDTO.class))
                .thenReturn(voteResponseDTO);
        when(userRepository.findById(uuid))
                .thenReturn(Optional.ofNullable(Voters.builder().cpf(cpf).build()));

        VoteResponseDTO response = voteService.createVote(TOPIC_ID, uuid, vote, true);

        assertNotNull(response);
        assertEquals(voteResponseDTO.cpf(), response.cpf());
        assertEquals(voteResponseDTO.vote(), response.vote());
        assertEquals(voteResponseDTO.topic().description(), response.topic().description());
    }

    @Test
    @DisplayName("should return TopicNotFoundException")
    void shouldReturnTopicNotFoundException() {
        String cpf = "02588786065";
        boolean vote = true;

        when(topicRepository.findById(TOPIC_ID))
                .thenThrow(new TopicNotFoundException(TOPIC_NOT_FOUND.getMessage()));

        TopicNotFoundException exception = assertThrows(TopicNotFoundException.class,
                () -> voteService.createVote(TOPIC_ID, cpf, vote, true));

        assertEquals(TOPIC_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("should return SessionNotFoundException")
    void shouldReturnSessionNotFoundException() {
        String description = "test";
        String cpf = "02588786065";
        boolean vote = true;
        Topic topic = getTopic(description, TOPIC_ID);

        when(topicRepository.findById(TOPIC_ID))
                .thenReturn(Optional.ofNullable(topic));
        when(sessionRepository.findByTopic(topic))
                .thenThrow(new SessionNotFoundException(SESSION_NOT_FOUND.getMessage()));

        SessionNotFoundException exception = assertThrows(SessionNotFoundException.class,
                () -> voteService.createVote(TOPIC_ID, cpf, vote, true));

        assertEquals(SESSION_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("should return SessionFinishedException")
    void shouldReturnSessionFinishedException() {
        String description = "test";
        String cpf = "02588786065";
        boolean vote = true;
        Topic topic = getTopic(description, TOPIC_ID);
        Session session = getSession(topic);
        session.setFinish(LocalDateTime.now().minusHours(1));

        when(topicRepository.findById(TOPIC_ID))
                .thenReturn(Optional.ofNullable(topic));
        when(sessionRepository.findByTopic(topic))
                .thenReturn(Optional.of(session));

        SessionFinishedException exception = assertThrows(SessionFinishedException.class,
                () -> voteService.createVote(TOPIC_ID, cpf, vote, true));

        assertEquals(SESSION_FINISHED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("should return InvalidDocumentException")
    void shouldReturnInvalidDocumentException() {
        String description = "test";
        String cpf = "02588786065";
        boolean vote = true;
        Topic topic = getTopic(description, TOPIC_ID);
        Session session = getSession(topic);
        Voters voters = getUser(cpf);

        when(topicRepository.findById(TOPIC_ID))
                .thenReturn(Optional.ofNullable(topic));
        when(sessionRepository.findByTopic(topic))
                .thenReturn(Optional.of(session));
        when(userRepository.findById(uuid))
                .thenReturn(Optional.ofNullable(voters));

        when(client.validateCPF(cpf)).thenThrow(FeignException.NotFound.class);

        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class,
                () -> voteService.createVote(TOPIC_ID, uuid, vote, true));

        assertEquals(INVALID_DOCUMENT.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("should return UnableVoteException")
    void shouldReturnUnableVoteException() {
        String description = "test";
        String cpf = "02588786065";
        boolean vote = true;
        Topic topic = getTopic(description, TOPIC_ID);
        Session session = getSession(topic);
        Voters voters = getUser(cpf);

        when(topicRepository.findById(TOPIC_ID))
                .thenReturn(Optional.ofNullable(topic));
        when(sessionRepository.findByTopic(topic))
                .thenReturn(Optional.ofNullable(session));
        when(userRepository.findById(uuid))
                .thenReturn(Optional.ofNullable(voters));
        when(client.validateCPF(cpf))
                .thenReturn(UserVoteStatusResponseDTO.builder().status(VoteStatus.UNABLE_TO_VOTE).build());

        UnableVoteException exception = assertThrows(UnableVoteException.class,
                () -> voteService.createVote(TOPIC_ID, uuid, vote, true));

        assertEquals(UNABLE_TO_VOTE.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("should return AlreadyVotedException")
    void shouldReturnAlreadyVotedException() {
        String description = "test";
        String cpf = "02588786065";
        boolean vote = true;
        Topic topic = getTopic(description, TOPIC_ID);
        Session session = getSession(topic);
        Voters voters = getUser(cpf);

        when(topicRepository.findById(TOPIC_ID))
                .thenReturn(Optional.ofNullable(topic));
        when(sessionRepository.findByTopic(topic))
                .thenReturn(Optional.ofNullable(session));
        when(userRepository.findById(uuid))
                .thenReturn(Optional.ofNullable(voters));
        when(client.validateCPF(cpf))
                .thenReturn(UserVoteStatusResponseDTO.builder().status(VoteStatus.ABLE_TO_VOTE).build());
        when(voteRepository.existsByTopicAndCpf(topic, cpf)).thenReturn(true);

        AlreadyVotedException exception = assertThrows(AlreadyVotedException.class,
                () -> voteService.createVote(TOPIC_ID, uuid, vote, true));

        assertEquals(ALREADY_VOTED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("should return the result of votes with success")
    void shouldReturnTheResultVotesWithSuccess() {
        long yesVotes = 5L;
        long noVotes = 3L;

        when(voteRepository.countVotesByTopicAndVote(TOPIC_ID, true)).thenReturn(yesVotes);
        when(voteRepository.countVotesByTopicAndVote(TOPIC_ID, false)).thenReturn(noVotes);

        Map<String, Long> response = voteService.getCountVotes(TOPIC_ID);

        assertEquals(yesVotes, response.get("Yes"));
        assertEquals(noVotes, response.get("No"));

    }


    private static Vote getVoteResponse(String cpf, Topic topic, boolean vote) {
        return Vote.builder()
                .cpf(cpf)
                .topic(topic)
                .vote(vote)
                .id(TOPIC_ID)
                .build();
    }

    private static VoteResponseDTO getVoteResponseDTO(String cpf, TopicRequestDTO topicRequestDTO, boolean vote) {
        return VoteResponseDTO.builder()
                .cpf(cpf)
                .topic(topicRequestDTO)
                .vote(vote)
                .build();
    }

    private static TopicRequestDTO getTopicDTO(String description) {
        return TopicRequestDTO.builder().description(description).build();
    }

    private static Session getSession(Topic topic) {
        return Session.builder()
                .id(TOPIC_ID)
                .topic(topic)
                .begging(LOCAL_DATE_TIME)
                .finish(LOCAL_DATE_TIME.plusMinutes(5))
                .build();
    }

    private Topic getTopic(String description, Long id) {
        return Topic.builder()
                .description(description)
                .id(id)
                .build();
    }

    private static Voters getUser(String cpf) {
        return Voters.builder()
                .id(uuid)
                .cpf(cpf)
                .build();
    }
}