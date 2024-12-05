package com.springwebundf.securityjwtproject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springwebundf.securityjwtproject.domain.user.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, String> {

    Optional<Aluno> findByCpf(String cpf);

    Optional<Aluno> findById(Long id);

}
