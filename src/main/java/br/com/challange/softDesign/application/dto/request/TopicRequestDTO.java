package br.com.challange.softDesign.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TopicRequestDTO(String description) {
}
