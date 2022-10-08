package com.paulpinter.ase.task.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskModel {
    private String scenario;
    private int stage;
    private int testCase;
    private ProblemModel problemModel;
    private SolutionModel solution;
}
