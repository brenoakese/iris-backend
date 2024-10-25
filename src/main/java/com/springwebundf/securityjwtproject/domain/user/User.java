package com.springwebundf.securityjwtproject.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(columnDefinition = "text")
    private String name;
    @Column(columnDefinition = "text")
    private String password;
    @Column(columnDefinition = "text")
    private String cpf;

    @Setter(AccessLevel.NONE)
    private String tipo;

    @Enumerated(EnumType.STRING)
    private Roles role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority (role.name()));
    }

    @Override
    public String getUsername() {
        return "";
    }
}
