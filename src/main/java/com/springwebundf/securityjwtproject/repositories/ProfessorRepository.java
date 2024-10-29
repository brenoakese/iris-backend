package com.springwebundf.securityjwtproject.repositories;

import com.springwebundf.securityjwtproject.domain.user.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
    Optional<Professor> findByCpf(String cpf);

    Optional<Professor> findById(Long id);


}
