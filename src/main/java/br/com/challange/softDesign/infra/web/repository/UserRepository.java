package br.com.challange.softDesign.infra.web.repository;

import br.com.challange.softDesign.application.model.Voters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Voters, String> {

    boolean existsByCpf(String cpf);

}
