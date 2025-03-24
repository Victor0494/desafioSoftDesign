package br.com.challange.softDesign.infra.web.controller;

import br.com.challange.softDesign.application.dto.request.UserRequestDTO;
import br.com.challange.softDesign.application.dto.response.UserResponseDTO;
import br.com.challange.softDesign.application.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("should return a new user with success")
    void createUserWithSuccess() throws Exception {
        String cpf = "02588786065";
        UserRequestDTO userRequestDTO = getUserRequestDTO(cpf);

        UserResponseDTO userResponseDTO = getUserResponseDTO(cpf);

        when(userService.createUser(userRequestDTO)).thenReturn(userResponseDTO);

        mockMvc.perform(
                        post("/v1/user")
                                .content(mapper.writeValueAsString(userRequestDTO))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").value(cpf));
    }

    @Test
    @DisplayName("should return a bad request because the lack of body")
    void shouldReturnBadRequest() throws Exception {
        mockMvc.perform(
                        post("/v1/user")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private static UserResponseDTO getUserResponseDTO(String cpf) {
        return UserResponseDTO.builder().cpf(cpf).build();
    }

    private static UserRequestDTO getUserRequestDTO(String cpf) {
        return UserRequestDTO.builder()
                .cpf(cpf)
                .build();
    }
}