package br.com.desafio.notification.dto;

import br.com.desafio.notification.constant.VoteStatus;
import lombok.Builder;

@Builder
public record UserVoteStatusDTO(VoteStatus status) {
}
