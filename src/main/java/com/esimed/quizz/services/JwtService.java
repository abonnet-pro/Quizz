package com.esimed.quizz.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static com.esimed.quizz.security.JWTAuthorizationFilter.TOKEN_PREFIX;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .sign(Algorithm.HMAC512(secret));
    }

    public String getUsernameByToken(String token) {
        return JWT.require(Algorithm.HMAC512(secret))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
    }
}
