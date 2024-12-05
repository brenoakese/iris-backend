package com.springwebundf.securityjwtproject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springwebundf.securityjwtproject.domain.user.Coordenador;

public interface CoordenadorRepository extends JpaRepository<Coordenador, String> {
    Optional<Coordenador> findByCpf(String cpf);
}
