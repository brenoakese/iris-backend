package com.springwebundf.securityjwtproject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springwebundf.securityjwtproject.domain.user.Atividades;

public interface AtividadesRepository extends JpaRepository<Atividades, Long> {

    @Override
    Optional<Atividades> findById(Long idAtividade);
}
