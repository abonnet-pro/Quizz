package com.esimed.quizz.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Value;
import org.springframework.stereotype.Service;

import static com.esimed.quizz.security.JWTAuthorizationFilter.TOKEN_PREFIX;

@Service
public class JwtService {

    public static final String SECRET = "jwtsecret";

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .sign(Algorithm.HMAC512(SECRET));
    }

    public static String getUsernameByToken(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
    }
}
