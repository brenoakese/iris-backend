package com.springwebundf.securityjwtproject.repositories;

import com.springwebundf.securityjwtproject.domain.user.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, String> {

    Optional<Aluno> findByCpf(String cpf);

    Optional<Aluno> findById(Long id);

}
