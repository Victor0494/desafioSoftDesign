package br.com.challange.softDesign.infra.web.repository;

import br.com.challange.softDesign.application.model.Topic;
import br.com.challange.softDesign.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByCpf(String cpf);

}
