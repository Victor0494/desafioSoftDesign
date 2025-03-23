package br.com.challange.softDesign.infra.web.repository;

import br.com.challange.softDesign.application.model.Topic;
import br.com.challange.softDesign.application.model.Vote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class VoteRepositoryTest {

    public static final String CPF = "12345678900";
    public static final String OTHER_CPF = "09876543211";
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private TopicRepository topicRepository;

    private Topic topic;

    @BeforeEach
    void setUp() {
        topic = topicRepository.save(Topic.builder().description("topic").build());

        voteRepository.save(Vote.builder().id(null).topic(topic).cpf(CPF).vote(true).build());
        voteRepository.save(Vote.builder().id(null).topic(topic).cpf(OTHER_CPF).vote(false).build());
    }

    @Test
    void shouldReturnTrueWhenVoteExistsForGivenTopicAndCpf() {
        boolean exists = voteRepository.existsByTopicAndCpf(topic, CPF);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenVoteDoesNotExistForGivenTopicAndCpf() {
        boolean exists = voteRepository.existsByTopicAndCpf(topic, "11122233344");

        assertFalse(exists);
    }

    @Test
    void shouldCountVotesByTopicAndVote() {
        long yesVotes = voteRepository.countVotesByTopicAndVote(topic.getId(), true);
        long noVotes = voteRepository.countVotesByTopicAndVote(topic.getId(), false);

        assertEquals(1, yesVotes);
        assertEquals(1, noVotes);
    }
}