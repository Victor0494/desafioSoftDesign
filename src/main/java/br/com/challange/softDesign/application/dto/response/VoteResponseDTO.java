package br.com.challange.softDesign.application.dto.response;


import br.com.challange.softDesign.application.dto.request.TopicRequestDTO;
import lombok.Builder;

@Builder
public record VoteResponseDTO(Long id, TopicRequestDTO topic, String cpf, boolean vote) {
}
