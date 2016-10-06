package me.cassiano.vettsel.implementation;

import java.util.Arrays;
import java.util.stream.IntStream;

import me.cassiano.vettsel.enumerations.SolutionStatus;
import me.cassiano.vettsel.interfaces.PartialSolution;


public class PartialSolutionImpl implements PartialSolution {

    private SolutionStatus status;
    private double values[];

    public PartialSolutionImpl(SolutionStatus status, double[] values) {
        this.status = status;
        this.values = values;
    }

    @Override
    public SolutionStatus getSolutionStatus() {
        return status;
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public Double getVariableValue(int index) {
        return values[index];
    }

    @Override
    public void printSolution() {
        System.out.println(status.name());

        if (status == SolutionStatus.OPTIMAL) {
            System.out.println(String.format("f(x) = %f", values[0]));
            IntStream
                    .range(1, values.length)
                    .forEach(
                            x -> System.out.println(String.format("x%d: %f", x, values[x]))
                    );
        }
    }

    public boolean isOptimal() {
        return status == SolutionStatus.OPTIMAL;
    }

    public static PartialSolution impossible() {
        return new PartialSolutionImpl(SolutionStatus.IMPOSSIBLE, null);
    }

    public static PartialSolution optimal(double variables[]) {
        return new PartialSolutionImpl(SolutionStatus.OPTIMAL, variables);
    }

    public static PartialSolution unlimited() {
        return new PartialSolutionImpl(SolutionStatus.UNLIMITED, null);
    }
}
