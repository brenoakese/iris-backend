package com.springwebundf.securityjwtproject.controllers;

import java.util.Collections;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springwebundf.securityjwtproject.domain.user.Aluno;
import com.springwebundf.securityjwtproject.repositories.AlunoRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/aluno")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AlunoController {

    private final AlunoRepository alunoRepository;

    @GetMapping("/listarDisciplinas/{id}")
    public ResponseEntity listarDisciplinas(@PathVariable Long id){
        Optional<Aluno> aluno = alunoRepository.findById(id);

        if(aluno.isEmpty()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensagem", "Aluno não encontrado"));
        }
        else {
            return ResponseEntity.ok(aluno.get().getDisciplinas());
        }
    }
}
