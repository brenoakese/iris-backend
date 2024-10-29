package com.springwebundf.securityjwtproject.controllers;

import com.springwebundf.securityjwtproject.domain.user.Aluno;
import com.springwebundf.securityjwtproject.domain.user.Disciplina;
import com.springwebundf.securityjwtproject.domain.user.Professor;
import com.springwebundf.securityjwtproject.domain.user.Roles;
import com.springwebundf.securityjwtproject.dto.RegisterAlunoDTO;
import com.springwebundf.securityjwtproject.dto.RegisterRequestDTO;
import com.springwebundf.securityjwtproject.dto.RegisterRequestDisciplinaDTO;
import com.springwebundf.securityjwtproject.repositories.AlunoRepository;
import com.springwebundf.securityjwtproject.repositories.DisciplinaRepository;
import com.springwebundf.securityjwtproject.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/coordenador")
@RequiredArgsConstructor
public class CoordenadorController {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;
    private final DisciplinaRepository disciplinaRepository;


    @PostMapping("/registrarDisciplina")
    public ResponseEntity registrarDisciplina(@RequestBody RegisterRequestDisciplinaDTO body){
        Optional<Professor> professor = professorRepository.findByCpf(body.professorCpf());

        if(professor.isEmpty()){
            return ResponseEntity.badRequest().body("Professor não encontrado");
        }
        else {
            Disciplina disciplina = new Disciplina();
            disciplina.setNome(body.name());
            disciplina.setProfessor(professor.get());
            disciplinaRepository.save(disciplina);

            return ResponseEntity.ok("Disciplina registrada com sucesso");
        }

    }

    @PostMapping("/register/aluno")
    public ResponseEntity registerAluno(@RequestBody RegisterAlunoDTO body){
        Optional<Aluno> aluno = alunoRepository.findByCpf(body.cpf());

        if(aluno.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        else {
            Aluno newaluno = new Aluno();
            newaluno.setName(body.name());
            newaluno.setCpf(body.cpf());
            newaluno.setPassword(passwordEncoder.encode(body.password()));
            newaluno.setRole(Roles.ALUNO);
            alunoRepository.save(newaluno);

            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/listarDisciplinas")
    public ResponseEntity listarDisciplinas(){ return ResponseEntity.ok(disciplinaRepository.findAll()); }

    @GetMapping("/listarAlunos")
    public ResponseEntity listarAlunos(){
        return ResponseEntity.ok("Lista de alunos");
    }

    @GetMapping("/listarProfessores")
    public ResponseEntity listarProfessores(){ return ResponseEntity.ok(professorRepository.findAll()); }


    @PostMapping("/register/professor")
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
