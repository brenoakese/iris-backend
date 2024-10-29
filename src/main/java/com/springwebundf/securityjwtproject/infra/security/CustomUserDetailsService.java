package com.springwebundf.securityjwtproject.infra.security;

import com.springwebundf.securityjwtproject.domain.user.Aluno;
import com.springwebundf.securityjwtproject.domain.user.Coordenador;
import com.springwebundf.securityjwtproject.domain.user.Professor;
import com.springwebundf.securityjwtproject.domain.user.User;
import com.springwebundf.securityjwtproject.repositories.AlunoRepository;
import com.springwebundf.securityjwtproject.repositories.CoordenadorRepository;
import com.springwebundf.securityjwtproject.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        List<Function<String, Optional<? extends User>>> repositories = Arrays.asList(
                professorRepository::findByCpf,
                alunoRepository::findByCpf,
                coordenadorRepository::findByCpf
        );

        User user = repositories.stream()
                .map(repo -> repo.apply(cpf))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user instanceof Professor professor) {
            return new org.springframework.security.core.userdetails.User(
                    professor.getCpf(),
                    professor.getPassword(),
                    Collections.emptyList()
            );
        } else if (user instanceof Aluno aluno) {
            return new org.springframework.security.core.userdetails.User(
                    aluno.getCpf(),
                    aluno.getPassword(),
                    Collections.emptyList()
            );
        } else if (user instanceof Coordenador coordenador) {
            return new org.springframework.security.core.userdetails.User(
                    coordenador.getCpf(),
                    coordenador.getPassword(),
                    Collections.emptyList()
            );
        } else {
            throw new UsernameNotFoundException("User type not recognized");
        }
    }
}