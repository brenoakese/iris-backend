package com.springwebundf.securityjwtproject.controllers;

import com.springwebundf.securityjwtproject.domain.user.Aluno;
import com.springwebundf.securityjwtproject.repositories.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/aluno")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoRepository alunoRepository;

    @GetMapping("/listarDisciplinas/{id}")
    public ResponseEntity listarDisciplinas(@PathVariable Long id){
        Optional<Aluno> aluno = alunoRepository.findById(id);

        if(aluno.isEmpty()){
            return ResponseEntity.badRequest().body("Aluno não encontrado");
        }
        else {
            return ResponseEntity.ok(aluno.get().getDisciplinas());
        }
    }
}
