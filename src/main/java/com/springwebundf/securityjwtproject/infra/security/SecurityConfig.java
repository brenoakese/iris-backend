package com.springwebundf.securityjwtproject.infra.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.POST, "auth/login").permitAll()
                        .requestMatchers("/swagger-ui/*", "/v3/api-docs/**").permitAll()

                        .requestMatchers(POST, "auth/login").permitAll()
                        .requestMatchers(POST, "auth/validar-token").permitAll()
                        .requestMatchers(POST,"auth/register/coordenador").permitAll()
                        
                        .requestMatchers("api/coordenador/**").hasRole("COORDENADOR")
                        .requestMatchers("api/professor/**").hasAnyRole("PROFESSOR", "COORDENADOR")
                        .requestMatchers(POST, "api/aluno/**").hasAnyRole("PROFESSOR", "COORDENADOR")
                        
                        /*
                            .requestMatchers("/api/coordenador/**").hasRole(COORDENADOR.name())
                            .requestMatchers(GET,"/api/coordenador/**").hasAuthority(COORDENADOR_READ.name())
                            .requestMatchers(POST,"/api/coordenador/**").hasAuthority(COORDENADOR_CREATE.name())
                            .requestMatchers(PUT,"/api/coordenador/**").hasAuthority(COORDENADOR_UPDATE.name())
                            .requestMatchers(DELETE,"/api/coordenador/**").hasAuthority(COORDENADOR_DELETE.name())

                            .requestMatchers("/api/professor/**").hasRole(PROFESSOR.name())
                            .requestMatchers(GET,"/api/professor/**").hasAuthority(PROFESSOR_READ.name())
                            .requestMatchers(POST,"/api/professor/**").hasAuthority(PROFESSOR_CREATE.name())
                            .requestMatchers(PUT,"/api/professor/**").hasAuthority(PROFESSOR_UPDATE.name())
                            .requestMatchers(DELETE,"/api/professor/**").hasAuthority(PROFESSOR_DELETE.name())

                        */
                    .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
