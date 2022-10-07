package com.paulpinter.ase.task.server;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenService {

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String scenario, int stage, int task, int testCase, String matNumber) {
        return Jwts.builder().setSubject(generateTokenSub(scenario, stage, task, testCase, matNumber)).signWith(key)
            .compact();
    }


    public boolean verifyToken(String token, String scenario, int stage, int task, int testCase,
        String matNumber) {
        boolean result;
        try {
            result = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject()
                .equals(generateTokenSub(scenario, stage, task, testCase, matNumber));
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

        return result;
    }

    private String generateTokenSub(String scenario, int stage, int task, int testCase, String matNumber) {
        return scenario + '-' + stage + '-' + task + '-' + testCase + '-' + matNumber;
    }

}
