package br.com.challange.softDesign.application.service;


import br.com.challange.softDesign.application.dto.request.TopicRequestDTO;
import br.com.challange.softDesign.application.dto.response.TopicResponseDTO;

public interface TopicService {

    TopicResponseDTO createTopic(TopicRequestDTO topicRequestDTO);
}
