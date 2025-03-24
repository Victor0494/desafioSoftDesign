package br.com.challange.softDesign.infra.config.fallback;

import br.com.challange.softDesign.application.dto.response.UserVoteStatusResponseDTO;
import br.com.challange.softDesign.domain.constant.VoteStatus;
import br.com.challange.softDesign.infra.config.client.LocalClient;
import org.springframework.stereotype.Component;

@Component
public class LocalClientFallback implements LocalClient {

    @Override
    public UserVoteStatusResponseDTO validateCPF(String cpf) {
        return new UserVoteStatusResponseDTO(VoteStatus.SERVICE_UNAVAILABLE);
    }
}
