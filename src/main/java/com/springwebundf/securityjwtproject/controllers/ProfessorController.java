package com.springwebundf.securityjwtproject.controllers;

import java.util.Collections;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springwebundf.securityjwtproject.domain.user.Aluno;
import com.springwebundf.securityjwtproject.domain.user.Atividades;
import com.springwebundf.securityjwtproject.domain.user.Disciplina;
import com.springwebundf.securityjwtproject.domain.user.Professor;
import com.springwebundf.securityjwtproject.dto.RegisterAtividadeDTO;
import com.springwebundf.securityjwtproject.repositories.AlunoRepository;
import com.springwebundf.securityjwtproject.repositories.AtividadesRepository;
import com.springwebundf.securityjwtproject.repositories.DisciplinaRepository;
import com.springwebundf.securityjwtproject.repositories.ProfessorRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/professor")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProfessorController {

    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final AlunoRepository alunoRepository;
    private final AtividadesRepository atividadesRepository;

    @GetMapping("/listarDisciplinas")
    public ResponseEntity listarDisciplinas(){
        return ResponseEntity.ok(disciplinaRepository.findAll());
    }

    @GetMapping("/listarMinhasDisciplinas")
    public ResponseEntity listarMinhasDisciplinas(@RequestBody String body)
    {
        return ResponseEntity.ok(disciplinaRepository.findByProfessorCpf(body));
    }

    @PostMapping("/cadastrarAtividade")
    public ResponseEntity criarAtividade(@RequestBody RegisterAtividadeDTO body){

        Optional<Professor> professor = professorRepository.findById(body.professorId());
        Optional<Disciplina> disciplina = disciplinaRepository.findById(body.disciplinaId());
        Optional<Aluno> aluno = alunoRepository.findById(body.alunoId());

        if (professor.isEmpty() || disciplina.isEmpty() || aluno.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensagem", "Professor, Disciplina ou Aluno não encontrado"));
        }

        if(!professor.get().getDisciplinas().contains(disciplina.get())){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensagem", "Professor não é responsável por essa disciplina"));
        }

        if (!aluno.get().getDisciplinas().contains(disciplina.get())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensagem", "Aluno não está matriculado nessa disciplina"));
        }

        Atividades atividade = new Atividades();
        atividade.setNome(body.nome());
        atividade.setProfessor(professor.get());
        atividade.setDisciplina(disciplina.get());
        atividade.setAluno(aluno.get());
        atividade.setNota(body.nota());

        atividadesRepository.save(atividade);
        return ResponseEntity.ok(Collections.singletonMap("mensagem", "Atividade cadastrada com sucesso"));
    }
}




