package br.com.challange.softDesign.application.service.impl;

import br.com.challange.softDesign.application.dto.request.UserRequestDTO;
import br.com.challange.softDesign.application.dto.response.UserResponseDTO;
import br.com.challange.softDesign.application.exception.UserAlreadyExistException;
import br.com.challange.softDesign.application.model.Voters;
import br.com.challange.softDesign.infra.web.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.challange.softDesign.domain.constant.ErrorMessage.USER_ALREADY_EXIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    public static final String UUID = java.util.UUID.randomUUID().toString();
    public static final String CPF = "02588786065";
    @Mock
    private UserRepository userRepository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("should return a new user created")
    void createUserWithSuccess() {
        UserRequestDTO userRequestDTO = getUserRequestDTO(CPF);
        UserResponseDTO userResponseDTO = getUserResponseDTO(CPF);
        Voters voters = Voters.builder().cpf(CPF).build();


        Voters votersSaved = Voters.builder().cpf(CPF).id(UUID).build();
        when(userRepository.save(voters)).thenReturn(votersSaved);
        when(mapper.convertValue(userRequestDTO, Voters.class))
                .thenReturn(voters);
        when(userRepository.existsByCpf(CPF)).thenReturn(false);
        when(mapper.convertValue(votersSaved, UserResponseDTO.class))
                .thenReturn(userResponseDTO);

        UserResponseDTO response = userService.createUser(userRequestDTO);

        assertEquals(votersSaved.getId(), response.id());
        assertEquals(votersSaved.getCpf(), response.cpf());
    }

    @Test
    @DisplayName("should return a UserAlreadyExistException")
    void shouldReturnUserAlreadyExistException() {
        when(userRepository.existsByCpf(CPF)).thenReturn(true);

        UserAlreadyExistException exception = assertThrows(UserAlreadyExistException.class,
                () -> userService.createUser(getUserRequestDTO(CPF)));

        assertEquals(USER_ALREADY_EXIST.getMessage(), exception.getMessage());
    }



    private UserResponseDTO getUserResponseDTO(String cpf) {
        return UserResponseDTO.builder().id(UUID).cpf(cpf).build();
    }

    private UserRequestDTO getUserRequestDTO(String cpf) {
        return UserRequestDTO.builder()
                .cpf(cpf)
                .build();
    }
}