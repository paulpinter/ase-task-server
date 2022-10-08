package com.paulpinter.ase.task.server;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class SolutionModel {
    public SolutionModel(){}

    public SolutionModel(List<String> correctedEquations){
        this.correctedEquations = correctedEquations;
    }

    private List<String> correctedEquations;
}
