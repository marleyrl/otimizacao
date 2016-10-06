package me.cassiano.vettsel.interfaces;


import me.cassiano.vettsel.enumerations.SolutionStatus;

public interface PartialSolution {

    SolutionStatus getSolutionStatus();

    int size();

    Double getVariableValue(int index);

    void printSolution();

}
