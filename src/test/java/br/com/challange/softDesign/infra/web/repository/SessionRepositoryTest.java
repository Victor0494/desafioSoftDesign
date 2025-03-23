package br.com.challange.softDesign.infra.web.repository;

import br.com.challange.softDesign.application.model.Session;
import br.com.challange.softDesign.application.model.Topic;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TopicRepository topicRepository;

    private Topic topic;

    @BeforeEach
    void setUp() {
        topic = topicRepository.save(Topic.builder().description("test").build());

        sessionRepository.save(Session.builder()
                .topic(topic)
                .begging(LocalDateTime.now())
                .finish(LocalDateTime.now().plusMinutes(10))
                .build());
    }

    @Test
    @DisplayName("should find session by topic")
    void shouldFindSessionByTopic() {
        Optional<Session> session = sessionRepository.findByTopic(topic);

        assertTrue(session.isPresent());
        assertEquals(topic.getId(), session.get().getTopic().getId());
    }

    @Test
    @DisplayName("should return true if session exists by topic")
    void shouldReturnTrueIfSessionExistsByTopic() {
        boolean exists = sessionRepository.existsByTopic(topic);

        assertTrue(exists);
    }

    @Test
    @DisplayName("should return false if no session exists by topic")
    void shouldReturnFalseIfNoSessionExistsForTopic() {
        Topic newTopic = topicRepository.save(Topic.builder().description("Other Topic").build());

        boolean exists = sessionRepository.existsByTopic(newTopic);

        assertFalse(exists);
    }
}