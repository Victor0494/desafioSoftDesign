package br.com.challange.softDesign.application.service.impl;

import br.com.challange.softDesign.application.dto.request.UserRequestDTO;
import br.com.challange.softDesign.application.dto.response.UserResponseDTO;
import br.com.challange.softDesign.application.exception.UserAlreadyExistException;
import br.com.challange.softDesign.application.model.Voters;
import br.com.challange.softDesign.application.service.UserService;
import br.com.challange.softDesign.infra.web.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.com.challange.softDesign.domain.constant.ErrorMessage.USER_ALREADY_EXIST;
import static br.com.challange.softDesign.domain.utils.CpfUtils.removeSpecialCharacters;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ObjectMapper mapper;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        log.info("UserServiceImpl.createUser - Start - Input: userRequestDTO: {}", userRequestDTO);

        validateUserAlreadyCreated(userRequestDTO.cpf());

        Voters voters = mapper.convertValue(userRequestDTO, Voters.class);

        Voters votersSaved = userRepository.save(voters);

        UserResponseDTO response = mapper.convertValue(votersSaved, UserResponseDTO.class);

        log.debug("UserServiceImpl.createUser - End - Output: response: {}", response);

        return response;
    }

    private void validateUserAlreadyCreated(String cpf) {
        if(userRepository.existsByCpf(removeSpecialCharacters(cpf))) {
            throw new UserAlreadyExistException(USER_ALREADY_EXIST.getMessage());
        }
    }
}
