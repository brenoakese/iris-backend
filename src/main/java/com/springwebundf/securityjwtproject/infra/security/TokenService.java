package com.springwebundf.securityjwtproject.infra.security;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.springwebundf.securityjwtproject.domain.user.User;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public TokenData generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Instant expirationDate = generateExpirationDate();

            String token = JWT.create()
                    .withIssuer("login-auth-api")
                    .withArrayClaim("role", new String[] {"ROLE_" + user.getRole().toUpperCase()})
                    .withSubject(user.getCpf())
                    .withClaim("nome", user.getName())
                    .withExpiresAt(Date.from(expirationDate))
                    .sign(algorithm);

            return new TokenData(token);

        }catch (JWTCreationException e) {
            throw new RuntimeException("Error generating token.");
        }
    }

    public String validateToken(String token){
        try {
          Algorithm algorithm = Algorithm.HMAC256(secret);

          return JWT.require(algorithm)
                  .withIssuer("login-auth-api")
                  .build()
                  .verify(token)
                  .getSubject();
        }catch (JWTVerificationException exception){
            return null;
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-3"));
    }
}
