package com.paulpinter.ase.task.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ase/equation/assignment/{matNum}")
public class EquationController {

    private TokenService tokenService;

    public EquationController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/token")
    public String token(@PathVariable String matNum) {
        return tokenService.generateToken("equation", 1, 1, 1, matNum);
    }

    @GetMapping("/stage/1/testcase/1")
    public ResponseEntity<EquationModel> getStage1TestCase1(@PathVariable String matNum, @PathVariable String testCase,
        @RequestParam String token) {
        if (!tokenService.verifyToken(token, "equation", 2, 1, 1, matNum)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(new EquationModel("0=9"));
    }

    @PostMapping("/stage/1/testcase/1")
    public ResponseEntity<EquationModel> getStage2TestCase1(@RequestBody CorrectedEquationModel correctedEquationModel,
        @PathVariable String matNum, @PathVariable String testCase, @RequestParam String token) {
        if (!tokenService.verifyToken(token, "equation", 2, 1, 1, matNum)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok().body(new EquationModel("0=9"));
    }

}
