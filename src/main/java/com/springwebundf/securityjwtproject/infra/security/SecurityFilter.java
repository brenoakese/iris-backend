package com.springwebundf.securityjwtproject.infra.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.springwebundf.securityjwtproject.domain.user.Aluno;
import com.springwebundf.securityjwtproject.domain.user.Coordenador;
import com.springwebundf.securityjwtproject.domain.user.Professor;
import com.springwebundf.securityjwtproject.domain.user.User;
import com.springwebundf.securityjwtproject.repositories.AlunoRepository;
import com.springwebundf.securityjwtproject.repositories.CoordenadorRepository;
import com.springwebundf.securityjwtproject.repositories.ProfessorRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    ProfessorRepository professorRepository;
    
    @Autowired
    CoordenadorRepository coordenadorRepository;
    
    @Autowired
    AlunoRepository alunoRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);
        User user = typeUser(login);

        if(user != null) {
            var roles = JWT.decode(token).getClaim("role").asArray(String.class);
            var authorities = Arrays.stream(roles)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private User typeUser(String cpf) {
        Optional<Aluno> aluno = alunoRepository.findByCpf(cpf);

        if(aluno.isPresent()) {
        	aluno.get().setRole("ALUNO");
        	return aluno.get();
        }

        Optional<Professor> professor = professorRepository.findByCpf(cpf);

        if(professor.isPresent()) {
        	professor.get().setRole("PROFESSOR");
        	return professor.get();
        }

        Optional<Coordenador> coordenador = coordenadorRepository.findByCpf(cpf);
        if(coordenador.isPresent()) {
        	coordenador.get().setRole("COORDENADOR");
        	return coordenador.get();
        }

        return null;
    }

	private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

}
