package com.springwebundf.securityjwtproject.dto;

import jakarta.validation.constraints.Positive;

public record RegisterAtividadeDTO(String nome, Long professorId, Long disciplinaId, Long alunoId, @Positive float nota) {
}
