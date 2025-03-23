package br.com.challange.softDesign.infra.web.repository;

import br.com.challange.softDesign.application.model.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TopicRepositoryTest {

    @Autowired
    private TopicRepository topicRepository;

    @BeforeEach
    void setUp() {
        Topic topic = Topic.builder().description("test").build();
        topicRepository.save(topic);
    }

    @Test
    @DisplayName("should return true when topic with description exists ignoring case")
    void shouldReturnTrueWhenTopicWithDescriptionExistsIgnoringCase() {
        boolean exists = topicRepository.existsByDescriptionIgnoreCase("TEST");

        assertTrue(exists);
    }

    @Test
    @DisplayName("should return false when topic with description does not exist")
    void shouldReturnFalseWhenTopicWithDescriptionDoesNotExist() {
        boolean exists = topicRepository.existsByDescriptionIgnoreCase("another topic");

        assertFalse(exists);
    }
}