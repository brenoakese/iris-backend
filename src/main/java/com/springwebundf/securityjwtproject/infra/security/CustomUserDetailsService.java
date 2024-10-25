package com.springwebundf.securityjwtproject.infra.security;

import com.springwebundf.securityjwtproject.domain.user.Professor;
import com.springwebundf.securityjwtproject.repositories.AlunoRepository;
import com.springwebundf.securityjwtproject.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Professor professor = professorRepository.findByCpf(cpf).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return new org.springframework.security.core.userdetails.User(professor.getCpf(), professor.getPassword(), Collections.emptyList());
    }



}
