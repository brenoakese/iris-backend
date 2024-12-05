package com.springwebundf.securityjwtproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springwebundf.securityjwtproject.domain.user.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    List<Disciplina> findByProfessorCpf(String cpf);
}
