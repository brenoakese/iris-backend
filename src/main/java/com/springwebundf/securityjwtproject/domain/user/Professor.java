package com.springwebundf.securityjwtproject.domain.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
