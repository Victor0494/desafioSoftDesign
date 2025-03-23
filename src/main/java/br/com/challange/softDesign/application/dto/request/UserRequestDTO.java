package br.com.challange.softDesign.application.dto.request;

import lombok.Builder;

@Builder
public record UserRequestDTO(String cpf) {
}
