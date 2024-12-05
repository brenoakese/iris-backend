package com.springwebundf.securityjwtproject.controllers;

import java.util.Collections;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.springwebundf.securityjwtproject.domain.user.Aluno;
import com.springwebundf.securityjwtproject.domain.user.Coordenador;
import com.springwebundf.securityjwtproject.domain.user.Professor;
import com.springwebundf.securityjwtproject.dto.LoginRequestDTO;
import com.springwebundf.securityjwtproject.dto.RegisterRequestDTO;
import com.springwebundf.securityjwtproject.dto.ResponseDTO;
import com.springwebundf.securityjwtproject.infra.security.TokenData;
import com.springwebundf.securityjwtproject.infra.security.TokenService;
import com.springwebundf.securityjwtproject.repositories.AlunoRepository;
import com.springwebundf.securityjwtproject.repositories.CoordenadorRepository;
import com.springwebundf.securityjwtproject.repositories.ProfessorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CoordenadorRepository coordenadorRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        String user = typeUser(body.cpf());
        if(user == null) return ResponseEntity.badRequest().build();

        if (user.equals("coordenador")) {
            Optional<Coordenador> coordenador = coordenadorRepository.findByCpf(body.cpf());
            if (
                coordenador.isPresent() &&
                passwordEncoder.matches(
                    body.password(),
                    coordenador.get().getPassword()
                )
            ) {
            	coordenador.get().setRole("COORDENADOR");
                TokenData token = tokenService.generateToken(coordenador.get());
                return ResponseEntity.ok(
                    new ResponseDTO(token)
                );
            }
        }

        if (user.equals("professor")) {
            Optional<Professor> professor = professorRepository.findByCpf(
                body.cpf()
            );
            if (
                professor.isPresent() &&
                passwordEncoder.matches(
                    body.password(),
                    professor.get().getPassword()
                )
            ) {
            	professor.get().setRole("PROFESSOR");
                TokenData token = tokenService.generateToken(professor.get());
                return ResponseEntity.ok(
                    new ResponseDTO(
                        token
                    )
                );
            }
        }

        return ResponseEntity.badRequest().body(Collections.singletonMap("mensagem", "Usuário ou senha inválidos"));
    }

    @PostMapping("/register/professor")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        String user = typeUser(body.cpf());

        if(user != null){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensagem", "Usuário já cadastrado"));
        }

        Professor newprofessor = new Professor();

        newprofessor.setName(body.name());
        newprofessor.setCpf(body.cpf());
        newprofessor.setPassword(passwordEncoder.encode(body.password()));

        professorRepository.save(newprofessor);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/coordenador")
    public ResponseEntity registerCoordenador(@RequestBody RegisterRequestDTO body){
        String user = typeUser(body.cpf());

        if(user != null){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensagem", "Usuário já cadastrado"));
        }

        Coordenador newCoordenador = new Coordenador();

        newCoordenador.setName(body.name());
        newCoordenador.setCpf(body.cpf());
        newCoordenador.setPassword(passwordEncoder.encode(body.password()));

        coordenadorRepository.save(newCoordenador);

        return ResponseEntity.ok().build();
    }

    private String typeUser(String cpf) {
        Optional<Aluno> aluno = alunoRepository.findByCpf(cpf);

        if(aluno.isPresent()) return "aluno";

        Optional<Professor> professor = professorRepository.findByCpf(cpf);

        if(professor.isPresent()) return "professor";

        Optional<Coordenador> coordenador = coordenadorRepository.findByCpf(cpf);
        if(coordenador.isPresent()) return "coordenador";

        return null;
    }

    @PostMapping("/validate/token")
    public ResponseEntity<?> validateToken(@RequestBody String body) {
        try {
            JsonNode json = objectMapper.readTree(body);
            String token = json.get("token").asText();
            if(tokenService.validateToken(token) != null) {
                return ResponseEntity.ok().body(Collections.singletonMap("mensagem", "Token válido!"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("mensagem", "Token inválido"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensagem", "JSON inválido"));
        }

    }
}
