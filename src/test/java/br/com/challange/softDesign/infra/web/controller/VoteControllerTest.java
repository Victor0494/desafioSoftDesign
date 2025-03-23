package br.com.challange.softDesign.infra.web.controller;

import br.com.challange.softDesign.application.dto.request.TopicRequestDTO;
import br.com.challange.softDesign.application.dto.response.VoteResponseDTO;
import br.com.challange.softDesign.application.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class VoteControllerTest {

    public static final String URI_TEMPLATE = "/v1/vote/{topicId}";
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VoteService voteService;

    @Autowired
    private ObjectMapper mapper;


    @Test
    @DisplayName("Should create a vote into a topic")
    void createVoteWithSuccess() throws Exception {
        VoteResponseDTO voteResponseDTO = VoteResponseDTO.builder()
                .topic(TopicRequestDTO.builder()
                        .description("test")
                        .build())
                .cpf("02588786065")
                .vote(true)
                .build();
        when(voteService.createVote(1L, "02588786065", true, true))
                .thenReturn(voteResponseDTO);


        mockMvc.perform(
                        post(URI_TEMPLATE, 1L)
                                .param("userId", "02588786065")
                                .param("vote", "true")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())  // HTTP 201 Created
                .andExpect(header().string("Location", "/topic/" + voteResponseDTO.id()))
                .andExpect(jsonPath("$.cpf").value("02588786065"))
                .andExpect(jsonPath("$.vote").value(true));

        verify(voteService).createVote(1L, "02588786065", true, true);
    }

    @Test
    @DisplayName("Should return a not found because the lack of topicId value")
    void createVoteWithoutTopicId() throws Exception {
        mockMvc.perform(
                        post(URI_TEMPLATE, "")
                                .param("userId", "02588786065")
                                .param("vote", "true")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return a bad request because the lack of cpf value")
    void createVoteWithoutCpf() throws Exception {
        mockMvc.perform(
                        post(URI_TEMPLATE, 1L)
                                .param("vote", "true")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return a bad request because the lack of vote value")
    void createVoteWithoutVote() throws Exception {
        mockMvc.perform(
                        post(URI_TEMPLATE, 1L)
                                .param("vote", "true")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get the vote result with success")
    void getVoteResultWithSuccess() throws Exception {
        Map<String, Long> result = new HashMap<>();
        result.put("Yes", 2L);
        result.put("No", 3L);

        when(voteService.getCountVotes(1L)).thenReturn(result);

        MockHttpServletResponse response = mockMvc.perform(
                        get(URI_TEMPLATE, 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertEquals(response.getContentAsString(), mapper.writeValueAsString(result));
    }

    @Test
    @DisplayName("Should return a not found")
    void getVoteResultWithNotFound() throws Exception {
        mockMvc.perform(
                        get(URI_TEMPLATE, "")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}