package br.com.challange.softDesign.application.service;


import br.com.challange.softDesign.application.dto.response.SessionResponseDTO;

public interface SessionService {

    SessionResponseDTO createSession(Long topicId, Integer duration);
}
