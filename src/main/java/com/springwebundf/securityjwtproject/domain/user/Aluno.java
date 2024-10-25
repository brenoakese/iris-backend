package com.springwebundf.securityjwtproject.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "alunos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Aluno extends User  {



        @ManyToMany
        @JoinTable(
            name = "aluno_disciplinas",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id")
        )
        private List<Disciplina> disciplinas;



}
