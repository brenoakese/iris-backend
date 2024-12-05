package com.springwebundf.securityjwtproject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springwebundf.securityjwtproject.domain.user.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
    Optional<Professor> findByCpf(String cpf);

    Optional<Professor> findById(Long id);


}
