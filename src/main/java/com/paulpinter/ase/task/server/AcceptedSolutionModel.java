package com.paulpinter.ase.task.server;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AcceptedSolutionModel {

    private final String message = "Accepted";
    private String linkToNextTask;

}
