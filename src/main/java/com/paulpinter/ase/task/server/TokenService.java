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

    public String generateToken(String scenario, String matNumber, int stage, int testCase) {
        return Jwts.builder().setSubject(generateTaskTokenSub(scenario, matNumber, stage, testCase)).signWith(key)
            .compact();
    }


    private boolean verifyToken(String token, String sub) {
        boolean result;
        try {
            result = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject()
                .equals(sub);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return result;
    }

    public boolean verifyTaskToken(String token, String scenario, String matNumber, int stage, int testCase) {
        return verifyToken(token, generateTaskTokenSub(scenario, matNumber, stage, testCase));
    }

    private String generateTaskTokenSub(String scenario, String matNumber, int stage, int testCase) {
        return scenario + '-' + matNumber + '-' + stage + '-' + testCase;
    }

    public String generateInitToken(String scenario, String matNum) {
        return Jwts.builder().setSubject(generateInitTokenSub(scenario,matNum)).signWith(key).compact();
    }

    public String generateInitTokenSub(String scenario, String matNum) {
        return scenario + '-' + matNum;
    }

    public boolean verifyInitToken(String token, String scenario, String matNum) {
        return verifyToken(token, generateInitTokenSub(scenario, matNum));
    }

    public String generateFinalToken(String scenario, String matNum) {
        return Jwts.builder().setSubject(generateFinalTokenSub(scenario,matNum)).signWith(key).compact();
    }

    public String generateFinalTokenSub(String scenario, String matNum) {
        return scenario + '-' + matNum + '-' + "Final";
    }

    public boolean verifyFinalToken(String token, String scenario, String matNum) {
        return verifyToken(token, generateFinalTokenSub(scenario, matNum));
    }
}
