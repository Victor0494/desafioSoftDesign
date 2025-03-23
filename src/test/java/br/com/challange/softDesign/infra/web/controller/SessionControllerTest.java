package br.com.challange.softDesign.infra.web.controller;

import br.com.challange.softDesign.application.dto.response.SessionResponseDTO;
import br.com.challange.softDesign.application.service.SessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SessionService sessionService;

    @Test
    @DisplayName("create session with success")
    void createSessionWithSuccess() throws Exception {

        when(sessionService.createSession(1L, 5))
                .thenReturn(SessionResponseDTO.builder().id(1L).build());

        MockHttpServletResponse response = mockMvc.perform(
                post("/v1/session")
                        .param("topicId", "1")
                        .param("duration", String.valueOf(5))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(201, response.getStatus());
        verify(sessionService).createSession(1L, 5);
    }

    @Test
    @DisplayName("create session without topicId")
    void createSessionWithoutTopicId() throws Exception {
        mockMvc.perform(
                post("/v1/session")
                        .param("duration", String.valueOf(5))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}