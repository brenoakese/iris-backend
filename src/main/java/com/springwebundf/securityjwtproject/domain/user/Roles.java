package com.springwebundf.securityjwtproject.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.springwebundf.securityjwtproject.domain.user.Permissions.*;


@RequiredArgsConstructor
public enum Roles {

    PROFESSOR(
            Set.of(
                    PROFESSOR_CREATE,
                    PROFESSOR_READ,
                    PROFESSOR_UPDATE,
                    PROFESSOR_DELETE
            )
    ),


    ALUNO(
            Set.of(
                    ALUNO_READ
            )
    ),

    COORDENADOR(
            Set.of(
                    COORDENADOR_CREATE,
                    COORDENADOR_READ,
                    COORDENADOR_UPDATE,
                    COORDENADOR_DELETE
            )
    );

    @Getter
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {

            var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
            authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

            return authorities;
    }
}
