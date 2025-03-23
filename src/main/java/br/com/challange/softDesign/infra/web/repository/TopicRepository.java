package br.com.challange.softDesign.infra.web.repository;

import br.com.challange.softDesign.application.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    boolean existsByDescriptionIgnoreCase(String description);
}
