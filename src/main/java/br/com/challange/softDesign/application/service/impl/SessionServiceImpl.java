package br.com.challange.softDesign.application.service.impl;

import br.com.challange.softDesign.application.dto.response.SessionResponseDTO;
import br.com.challange.softDesign.application.model.Session;
import br.com.challange.softDesign.application.model.Topic;
import br.com.challange.softDesign.application.exception.SessionAlreadyExistException;
import br.com.challange.softDesign.application.exception.TopicNotFoundException;
import br.com.challange.softDesign.application.service.SessionService;
import br.com.challange.softDesign.infra.web.repository.SessionRepository;
import br.com.challange.softDesign.infra.web.repository.TopicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static br.com.challange.softDesign.domain.constant.ErrorMessage.SESSION_ALREADY_EXIST;
import static br.com.challange.softDesign.domain.constant.ErrorMessage.TOPIC_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final TopicRepository topicRepository;
    private final ObjectMapper mapper;

    //Método que registra uma nova seção em um tópico
    @Override
    public SessionResponseDTO createSession(Long topicId, Integer duration) {
        log.info("SessionServiceImpl.createSession - Start - Input: topicId: {}, duration: {}", topicId, duration);

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(TOPIC_NOT_FOUND.getMessage()));

        if(sessionRepository.existsByTopic(topic)) {
            throw new SessionAlreadyExistException(SESSION_ALREADY_EXIST.getMessage());
        }

        LocalDateTime begging = LocalDateTime.now();
        LocalDateTime finish = begging.plusMinutes(duration != null ? duration : 1);

        Session session = sessionRepository.save(Session.builder().topic(topic).begging(begging).finish(finish).build());
        SessionResponseDTO response = mapper.convertValue(session, SessionResponseDTO.class);

        log.debug("SessionServiceImpl.createSession - End - Output: response: {}", response);
        return response;
    }
}
