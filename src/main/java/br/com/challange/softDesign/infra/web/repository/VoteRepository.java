package br.com.challange.softDesign.infra.web.repository;

import br.com.challange.softDesign.application.model.Topic;
import br.com.challange.softDesign.application.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsByTopicAndCpf(Topic topic, String cpf);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.topic.id = :topicId AND v.vote = :vote")
    long countVotesByTopicAndVote(Long topicId, boolean vote);
}
