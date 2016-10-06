package me.cassiano.vettsel.interfaces;

public interface Function {

    double getCoeficient(int index);

    void setCoeficient(int index, double value);

    int size();

    Operation getOperation();

    void setOperation(Operation type);

    boolean isMax();

    boolean isMin();

    enum Operation {
        MAX, MIN
    }
}
