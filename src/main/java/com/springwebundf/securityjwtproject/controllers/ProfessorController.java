package com.springwebundf.securityjwtproject.controllers;

import com.springwebundf.securityjwtproject.domain.user.Disciplina;
import com.springwebundf.securityjwtproject.domain.user.Professor;
import com.springwebundf.securityjwtproject.repositories.AlunoRepository;
import com.springwebundf.securityjwtproject.repositories.DisciplinaRepository;
import com.springwebundf.securityjwtproject.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/professor")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final AlunoRepository alunoRepository;

    @GetMapping("/listarDisciplinas")
    public ResponseEntity listarDisciplinas(){
        return ResponseEntity.ok(disciplinaRepository.findAll());
    }

    @GetMapping("/listarMinhasDisciplinas")
    public ResponseEntity listarMinhasDisciplinas(@RequestBody String body)
    {
        return ResponseEntity.ok(disciplinaRepository.findByProfessorCpf(body));
    }



}
