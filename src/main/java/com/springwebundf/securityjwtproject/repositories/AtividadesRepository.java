package com.springwebundf.securityjwtproject.repositories;

import com.springwebundf.securityjwtproject.domain.user.Atividades;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtividadesRepository extends JpaRepository<Atividades, Long> {

    @Override
    Optional<Atividades> findById(Long idAtividade);
}
