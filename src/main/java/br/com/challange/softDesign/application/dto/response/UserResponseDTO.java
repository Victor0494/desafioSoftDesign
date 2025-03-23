package br.com.challange.softDesign.application.dto.response;

import lombok.Builder;

@Builder
public record UserResponseDTO(String id, String cpf) {
}
