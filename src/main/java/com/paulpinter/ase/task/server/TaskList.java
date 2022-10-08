package com.paulpinter.ase.task.server;

import java.util.*;
import lombok.Getter;

public final class TaskList {
    private TaskList() {}

    private static final String EQUATION = "equation";
    private static final TaskModel EQ_S1_T1 = new TaskModel(EQUATION, 1, 1, new ProblemModel("0=9"),
        new SolutionModel(List.of("9=9", "0=0")));
    private static final TaskModel EQ_S1_T2 = new TaskModel(EQUATION, 1, 2, new ProblemModel("3=7"),
        new SolutionModel(List.of()));
    private static final TaskModel EQ_S2_T1 = new TaskModel(EQUATION, 2, 1, new ProblemModel("0+9=9"),
        new SolutionModel(List.of("0+9=9")));
    private static final TaskModel EQ_S2_T2 = new TaskModel(EQUATION, 2, 2, new ProblemModel("2+5=8"),
        new SolutionModel(List.of("3+5=8")));
    private static final TaskModel EQ_S3_T1 = new TaskModel(EQUATION, 3, 1, new ProblemModel("1-7=6"),
        new SolutionModel(List.of("7-1=6")));
    private static final TaskModel EQ_S3_T2 = new TaskModel(EQUATION, 3, 2, new ProblemModel("6+3=1"),
        new SolutionModel(List.of()));
    private static final TaskModel EQ_S4_T1 = new TaskModel(EQUATION, 4, 1, new ProblemModel("1-1=2"),
        new SolutionModel(List.of("1+1=2")));
    private static final TaskModel EQ_S4_T2 = new TaskModel(EQUATION, 4, 2, new ProblemModel("1-1=8"),
        new SolutionModel(List.of("7-1=6")));

    @Getter
    private static final List<TaskModel> TASK_LIST = List.of(EQ_S1_T1, EQ_S1_T2, EQ_S2_T1, EQ_S2_T2, EQ_S3_T1, EQ_S3_T2,
        EQ_S4_T1, EQ_S4_T2);
}
