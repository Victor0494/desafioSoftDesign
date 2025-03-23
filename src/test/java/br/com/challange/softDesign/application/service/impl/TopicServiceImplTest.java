package br.com.challange.softDesign.application.service.impl;

import br.com.challange.softDesign.application.dto.request.TopicRequestDTO;
import br.com.challange.softDesign.application.dto.response.TopicResponseDTO;
import br.com.challange.softDesign.application.exception.TopicAlreadyCreatedException;
import br.com.challange.softDesign.application.model.Topic;
import br.com.challange.softDesign.infra.web.repository.TopicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.challange.softDesign.domain.constant.ErrorMessage.TOPIC_ALREADY_CREATED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopicServiceImplTest {

    public static final String DESCRIPTION = "test";
    @InjectMocks
    private TopicServiceImpl topicService;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private ObjectMapper mapper;

    @Test
    @DisplayName("should return a new topic")
    void createTopicWithSuccess() {
        Long id = 1L;
        TopicRequestDTO topicRequestDTO = getTopicDTO(id, DESCRIPTION);
        Topic topic = getTopic(DESCRIPTION, id);
        TopicResponseDTO topicResponseDTO = getTopicResponseDTO(id);

        when(topicRepository.save(any(Topic.class)))
                .thenReturn(topic);
        when(mapper.convertValue(topicRequestDTO, Topic.class)).thenReturn(topic);
        when(mapper.convertValue(topic, TopicResponseDTO.class))
                .thenReturn(topicResponseDTO);

        TopicResponseDTO response = topicService.createTopic(topicRequestDTO);

        assertEquals(topicResponseDTO.id(), response.id());
        assertEquals(topicResponseDTO.description(), response.description());
    }

    @Test
    @DisplayName("should return a TopicAlreadyCreatedException")
    void shouldReturnTopicAlreadyCreatedException() {
        Long id = 1L;
        TopicRequestDTO topicRequestDTO = getTopicDTO(id, DESCRIPTION);

        when(topicRepository.existsByDescriptionIgnoreCase(DESCRIPTION))
                .thenReturn(true);

        TopicAlreadyCreatedException exception = assertThrows(TopicAlreadyCreatedException.class,
                () -> topicService.createTopic(topicRequestDTO));

        assertEquals(TOPIC_ALREADY_CREATED.getMessage(), exception.getMessage());
    }

    private TopicResponseDTO getTopicResponseDTO(Long id) {
        return TopicResponseDTO.builder()
                .description(DESCRIPTION)
                .id(id)
                .build();
    }

    private Topic getTopic(String description, Long id) {
        return Topic.builder()
                .description(description)
                .id(id)
                .build();
    }

    private TopicRequestDTO getTopicDTO(Long id, String description) {
        return TopicRequestDTO.builder().description(description).build();
    }
}