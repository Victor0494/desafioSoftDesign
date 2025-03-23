package br.com.challange.softDesign.application.dto.response;


import br.com.challange.softDesign.application.dto.request.TopicRequestDTO;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SessionResponseDTO(Long id, TopicRequestDTO topic, LocalDateTime begging, LocalDateTime finish) {
}
