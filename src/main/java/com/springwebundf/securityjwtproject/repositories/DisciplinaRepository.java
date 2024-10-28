package com.springwebundf.securityjwtproject.repositories;

import com.springwebundf.securityjwtproject.domain.user.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    List<Disciplina> findByProfessorCpf(String cpf);
}
