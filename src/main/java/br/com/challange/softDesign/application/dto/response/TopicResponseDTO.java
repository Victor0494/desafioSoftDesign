package br.com.challange.softDesign.application.dto.response;

import lombok.Builder;

@Builder
public record TopicResponseDTO(Long id, String description) {
}
