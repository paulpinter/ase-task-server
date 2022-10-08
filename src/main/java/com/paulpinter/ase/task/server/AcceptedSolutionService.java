package com.paulpinter.ase.task.server;

import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AcceptedSolutionService {

    @Value("${server.port}")
    private String port;
    private final static String HOST = "127.0.0.1";
    private final String baseUrl = "http://" + HOST + ":" + port;
    private final TokenService tokenService;
    private final TaskService taskService;

    public AcceptedSolutionService(TokenService tokenService, TaskService taskService) {
        this.tokenService = tokenService;
        this.taskService = taskService;
    }

    public AcceptedSolutionModel linkToNextTask(String scenario, String matNum, int currentStage) {
        Optional<TaskModel> chosenTask = taskService.chooseNextTask(scenario, currentStage);
        if (chosenTask.isEmpty()) {
            String lastToken = tokenService.generateFinalToken(scenario, matNum);
            return new AcceptedSolutionModel(baseUrl + "/" + scenario + "/" + "finish?token=" + lastToken);
        }
        TaskModel nextTask = chosenTask.get();
        int nextStage = nextTask.getStage();
        int nextTestCase = nextTask.getTestCase();
        String nextToken = tokenService.generateToken(scenario, matNum, nextStage, nextTestCase);
        return new AcceptedSolutionModel(
            baseUrl + "/" + scenario + "/" + matNum + "/stage/" + nextStage + "/testcase/" + nextTestCase + "?token="
                + nextToken);
    }

    public boolean isProvidedSolutionCorrect(TaskModel task, SolutionModel providedSolution) {
        return Set.of(task.getSolution().getCorrectedEquations())
            .equals(Set.of(providedSolution.getCorrectedEquations()));
    }


}
