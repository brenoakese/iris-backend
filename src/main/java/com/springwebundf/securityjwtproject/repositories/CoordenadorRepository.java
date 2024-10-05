package com.springwebundf.securityjwtproject.repositories;

import com.springwebundf.securityjwtproject.domain.user.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoordenadorRepository extends UserRepository<Coordenador> {
}
