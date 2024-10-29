package com.springwebundf.securityjwtproject.domain.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "professors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Professor extends User  {


    @OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Disciplina> disciplinas;




}
