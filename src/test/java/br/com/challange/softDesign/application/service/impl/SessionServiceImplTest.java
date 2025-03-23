package br.com.challange.softDesign.application.service.impl;

import br.com.challange.softDesign.application.dto.response.SessionResponseDTO;
import br.com.challange.softDesign.application.dto.request.TopicRequestDTO;
import br.com.challange.softDesign.application.exception.SessionAlreadyExistException;
import br.com.challange.softDesign.application.exception.TopicNotFoundException;
import br.com.challange.softDesign.application.model.Session;
import br.com.challange.softDesign.application.model.Topic;
import br.com.challange.softDesign.infra.web.repository.SessionRepository;
import br.com.challange.softDesign.infra.web.repository.TopicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.challange.softDesign.domain.constant.ErrorMessage.SESSION_ALREADY_EXIST;
import static br.com.challange.softDesign.domain.constant.ErrorMessage.TOPIC_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {

    public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();

    @InjectMocks
    private SessionServiceImpl sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private ObjectMapper mapper;

    @Test
    @DisplayName("should create a new session with success")
    void createSessionWithSuccess() {
        Long topicId = 1L;
        Integer duration = 5;
        Topic topic = getTopic(topicId);
        Session session = getSession(topic, duration);
        SessionResponseDTO sessionResponseDTO = SessionResponseDTO.builder()
                .id(1L)
                .topic(TopicRequestDTO.builder().description("test").build())
                .begging(LOCAL_DATE_TIME)
                .finish(LOCAL_DATE_TIME.plusMinutes(duration))
                .build();

        when(topicRepository.findById(topicId))
                .thenReturn(Optional.ofNullable(topic));
        when(sessionRepository.save(any(Session.class)))
                .thenReturn(session);
        when(mapper.convertValue(session, SessionResponseDTO.class))
                .thenReturn(sessionResponseDTO);

        SessionResponseDTO responseDTO = sessionService.createSession(topicId, duration);

        assertNotNull(responseDTO);
        assertEquals(sessionResponseDTO.id(), responseDTO.id());
        assertEquals(sessionResponseDTO.topic().description(), responseDTO.topic().description());
        assertEquals(sessionResponseDTO.begging().getMinute(), responseDTO.begging().getMinute());
        assertEquals(sessionResponseDTO.finish().getMinute(), responseDTO.finish().getMinute());
    }

    @Test
    @DisplayName("should return a TopicNotFoundException")
    void shouldReturnTopicNotFoundException() {
        Long topicId = 1L;
        Integer duration = 5;

        when(topicRepository.findById(topicId))
                .thenThrow(new TopicNotFoundException(TOPIC_NOT_FOUND.getMessage()));

        TopicNotFoundException exception = assertThrows(TopicNotFoundException.class, () -> {
            sessionService.createSession(topicId, duration);
        });

        assertEquals(TOPIC_NOT_FOUND.getMessage() ,exception.getMessage());
    }

    @Test
    @DisplayName("should return a SessionAlreadyExistException")
    void shouldReturnSessionAlreadyExistException() {
        Long topicId = 1L;
        Integer duration = 5;
        Topic topic = getTopic(topicId);

        when(topicRepository.findById(topicId))
                .thenReturn(Optional.ofNullable(topic));

        when(sessionRepository.existsByTopic(topic))
                .thenReturn(true);

        SessionAlreadyExistException exception = assertThrows(SessionAlreadyExistException.class, () -> {
            sessionService.createSession(topicId, duration);
        });

        assertEquals(SESSION_ALREADY_EXIST.getMessage() ,exception.getMessage());
    }

    @Test
    @DisplayName("should return a session with just one minute default value")
    void shouldReturnSessionWithJustOneMinuteDefaultValue() {
        Long topicId = 1L;
        int duration = 1;
        Topic topic = getTopic(topicId);
        Session session = getSession(topic, duration);
        SessionResponseDTO sessionResponseDTO = SessionResponseDTO.builder()
                .id(1L)
                .topic(TopicRequestDTO.builder().description("test").build())
                .begging(LOCAL_DATE_TIME)
                .finish(LOCAL_DATE_TIME.plusMinutes(duration))
                .build();

        when(topicRepository.findById(topicId))
                .thenReturn(Optional.ofNullable(topic));
        when(sessionRepository.save(any(Session.class)))
                .thenReturn(session);
        when(mapper.convertValue(session, SessionResponseDTO.class))
                .thenReturn(sessionResponseDTO);

        SessionResponseDTO responseDTO = sessionService.createSession(topicId, null);

        assertEquals(sessionResponseDTO.begging().getMinute(), responseDTO.begging().getMinute());
        assertEquals(sessionResponseDTO.finish().getMinute(), responseDTO.finish().getMinute());
    }

    private Session getSession(Topic topic, Integer duration) {
        return Session.builder()
                .id(1L)
                .topic(topic)
                .begging(LOCAL_DATE_TIME)
                .finish(LOCAL_DATE_TIME.plusMinutes(duration))
                .build();
    }

    private Topic getTopic(long topicId) {
        return Topic.builder()
                .id(topicId)
                .description("test")
                .build();
    }
}