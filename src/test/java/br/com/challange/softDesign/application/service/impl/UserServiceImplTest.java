package br.com.challange.softDesign.application.service.impl;

import br.com.challange.softDesign.application.dto.request.UserRequestDTO;
import br.com.challange.softDesign.application.dto.response.UserResponseDTO;
import br.com.challange.softDesign.application.exception.TopicNotFoundException;
import br.com.challange.softDesign.application.exception.UserAlreadyExistException;
import br.com.challange.softDesign.application.model.User;
import br.com.challange.softDesign.infra.web.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static br.com.challange.softDesign.domain.constant.ErrorMessage.TOPIC_NOT_FOUND;
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
        User user = User.builder().cpf(CPF).build();


        User userSaved = User.builder().cpf(CPF).id(UUID).build();
        when(userRepository.save(user)).thenReturn(userSaved);
        when(mapper.convertValue(userRequestDTO, User.class))
                .thenReturn(user);
        when(userRepository.existsByCpf(CPF)).thenReturn(false);
        when(mapper.convertValue(userSaved, UserResponseDTO.class))
                .thenReturn(userResponseDTO);

        UserResponseDTO response = userService.createUser(userRequestDTO);

        assertEquals(userSaved.getId(), response.id());
        assertEquals(userSaved.getCpf(), response.cpf());
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