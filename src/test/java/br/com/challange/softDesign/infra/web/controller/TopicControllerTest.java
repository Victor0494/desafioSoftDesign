package br.com.challange.softDesign.infra.web.controller;

import br.com.challange.softDesign.application.dto.request.TopicRequestDTO;
import br.com.challange.softDesign.application.dto.response.TopicResponseDTO;
import br.com.challange.softDesign.application.service.TopicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TopicService topicService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("create topic with success")
    void createTopicWithSuccess() throws Exception {
        TopicRequestDTO topicRequestDTO = TopicRequestDTO.builder().description("test").build();

        when(topicService.createTopic(topicRequestDTO))
                .thenReturn(TopicResponseDTO.builder()
                        .id(1L)
                        .description("test")
                        .build());

        mockMvc.perform(
                post("/v1/topic")
                        .content(mapper.writeValueAsString(topicRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("test"));
    }

    @Test
    @DisplayName("create topic without body")
    void createTopicWithoutBody() throws Exception {
        mockMvc.perform(
                        post("/v1/topic")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}