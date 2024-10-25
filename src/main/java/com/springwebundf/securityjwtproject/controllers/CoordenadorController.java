package com.springwebundf.securityjwtproject.controllers;

import com.springwebundf.securityjwtproject.domain.user.Professor;
import com.springwebundf.securityjwtproject.domain.user.Roles;
import com.springwebundf.securityjwtproject.dto.RegisterRequestDTO;
import com.springwebundf.securityjwtproject.repositories.AlunoRepository;
import com.springwebundf.securityjwtproject.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/coordenador")
@RequiredArgsConstructor
public class CoordenadorController {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;


    @RequestMapping("/registrarDisciplina")
    public ResponseEntity registrarDisciplina(){
        return ResponseEntity.ok("Disciplina registrada com sucesso");
    }

    @RequestMapping("/listarDisciplinas")
    public ResponseEntity listarDisciplinas(){
        return ResponseEntity.ok("Lista de disciplinas");
    }

    @RequestMapping("/listarAlunos")
    public ResponseEntity listarAlunos(){
        return ResponseEntity.ok("Lista de alunos");
    }

    @RequestMapping("/listarProfessores")
    public ResponseEntity listarProfessores(){
        return ResponseEntity.ok("Lista de professores");
    }

    @RequestMapping("/register/professor")
    public ResponseEntity registerProfessor(@RequestBody RegisterRequestDTO body){

        Optional<Professor> professor = professorRepository.findByCpf(body.cpf());

        if(professor.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        else {
            Professor newprofessor = new Professor();
            newprofessor.setName(body.name());
            newprofessor.setCpf(body.cpf());
            newprofessor.setPassword(passwordEncoder.encode(body.password()));
            newprofessor.setRole(Roles.PROFESSOR);
            professorRepository.save(newprofessor);

            return ResponseEntity.ok().build();
        }
    }

}
