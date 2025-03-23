package br.com.challange.softDesign.application.service.impl;

import br.com.challange.softDesign.application.dto.request.TopicRequestDTO;
import br.com.challange.softDesign.application.exception.TopicAlreadyCreatedException;
import br.com.challange.softDesign.application.model.Topic;
import br.com.challange.softDesign.application.service.TopicService;
import br.com.challange.softDesign.infra.web.repository.TopicRepository;
import br.com.challange.softDesign.application.dto.response.TopicResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.challange.softDesign.domain.constant.ErrorMessage.TOPIC_ALREADY_CREATED;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    private final  ObjectMapper mapper;

    //Criação de um novo tópico
    @Override
    public TopicResponseDTO createTopic(TopicRequestDTO topicRequestDTO) {
        log.info("TopicServiceImpl.createTopic - Start - Input: topicDTO: {}", topicRequestDTO);

        if(topicRepository.existsByDescriptionIgnoreCase(topicRequestDTO.description())) {
            throw new TopicAlreadyCreatedException(TOPIC_ALREADY_CREATED.getMessage());
        }

        Topic topic = topicRepository.save(mapper.convertValue(topicRequestDTO, Topic.class));
        TopicResponseDTO topicResponse = mapper.convertValue(topic, TopicResponseDTO.class);

        log.debug("TopicServiceImpl.createTopic - End - Output: response: {}", topicResponse);
        return topicResponse;
    }
}
