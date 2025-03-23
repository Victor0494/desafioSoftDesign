package br.com.challange.softDesign.application.dto.response;

import br.com.challange.softDesign.domain.constant.VoteStatus;
import lombok.Builder;

@Builder
public record UserVoteStatusResponseDTO(VoteStatus status) {
}
