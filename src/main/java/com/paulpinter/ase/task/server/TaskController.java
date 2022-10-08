package com.paulpinter.ase.task.server;

import java.util.Optional;
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
@RequestMapping("/ase/{scenario}/assignment/{matNum}")
public class TaskController {

    private TokenService tokenService;

    private TaskService taskService;
    private AcceptedSolutionService acceptedSolutionService;

    public TaskController(TokenService tokenService, TaskService taskService,
        AcceptedSolutionService acceptedSolutionService) {
        this.tokenService = tokenService;
        this.taskService = taskService;
        this.acceptedSolutionService = acceptedSolutionService;
    }

    @GetMapping("/token")
    public String getInitToken(@PathVariable String scenario, @PathVariable String matNum) {
        return tokenService.generateInitToken(scenario, matNum);
    }

    @GetMapping("/stage/{stage}/testcase/{testCase}")
    public ResponseEntity<ProblemModel> getProblem(@PathVariable String scenario, @PathVariable String matNum,
        @PathVariable int stage, @PathVariable int testCase, @RequestParam String token) {
        boolean isTokenValid;
        if (stage == 1 && testCase == 1) {
            isTokenValid = tokenService.verifyInitToken(token, scenario, matNum);
        } else {
            isTokenValid = tokenService.verifyTaskToken(token, scenario, matNum, stage, testCase);
        }
        if (!isTokenValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<TaskModel> taskSearchResult = taskService.findTask(scenario, stage, testCase);

        if (taskSearchResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ProblemModel foundProblem = taskSearchResult.get().getProblemModel();

        return ResponseEntity.ok().body(foundProblem);
    }

    @PostMapping("/stage/{stage}/testcase/{testCase}")
    public ResponseEntity<AcceptedSolutionModel> checkSolution(@RequestBody SolutionModel correctedEquations,
        @PathVariable String scenario, @PathVariable String matNum, @PathVariable int stage, @PathVariable int testCase,
        @RequestParam String token) {
        if (!tokenService.verifyTaskToken(token, scenario, matNum, stage, testCase)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<TaskModel> taskSearchResult = taskService.findTask(scenario, stage, testCase);
        if (taskSearchResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        TaskModel task = taskSearchResult.get();
        boolean isSolutionCorrect = acceptedSolutionService.isProvidedSolutionCorrect(task, correctedEquations);

        if (!isSolutionCorrect) {
            return ResponseEntity.status(417).body(null);
        }

        return ResponseEntity.accepted().body(acceptedSolutionService.linkToNextTask(scenario, matNum, stage));
    }

    @GetMapping("/finish")
    public HttpStatus getInitToken(@PathVariable String scenario, @PathVariable String matNum,
        @RequestParam String token) {
        return tokenService.verifyFinalToken(token, scenario, matNum) ? HttpStatus.OK : HttpStatus.valueOf(417);
    }

}
