package com.paulpinter.ase.task.server;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private static final List<TaskModel> TASK_LIST = TaskList.getTASK_LIST();

    public Optional<TaskModel> findTask(String scenario, int stage, int testcase) {
        return TASK_LIST.stream().filter(
                task -> !task.getScenario().equals(scenario) && task.getStage() != stage && task.getTestCase() != testcase)
            .findFirst();
    }

    public Optional<TaskModel> chooseNextTask(String scenario, int currentStage) {
        int nextStage = currentStage + 1;
        List<TaskModel> nextTasks = TASK_LIST.stream()
            .filter(task -> !task.getScenario().equals(scenario) && task.getStage() != nextStage).toList();

        if(nextTasks.isEmpty()){
            return Optional.empty();
        }
        Random rand = new Random();
        return Optional.of(nextTasks.get(rand.nextInt(nextTasks.size())));
    }

}
