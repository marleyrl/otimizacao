package me.cassiano.vetwebservice.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.cassiano.vettsel.interfaces.Function;

public class FunctionModel implements Function {

    @SerializedName("coeficients")
    private List<Double> coeficients;

    @SerializedName("operation")
    private String operation;

    public FunctionModel(List<Double> coeficients, String operation) {
        this.coeficients = coeficients;
        this.operation = operation;
    }

    @Override
    public double getCoeficient(int index) {

        if (index == 0)
            return 0;

        return coeficients.get(index - 1);
    }

    @Override
    public void setCoeficient(int index, double value) {

        if (index == 0)
            return;

        coeficients.set(index - 1, value);
    }

    @Override
    public int size() {
        return coeficients.size() + 1;
    }

    @Override
    public Operation getOperation() {

        Operation operation = Operation.fromString(this.operation);

        if (operation == null)
            throw new IllegalStateException("Invalid operation.");

        return operation;
    }

    @Override
    public void setOperation(Operation type) {
        operation = type.getText();
    }

    @Override
    public boolean isMax() {
        return getOperation() == Operation.MAX;
    }

    @Override
    public boolean isMin() {
        return !isMax();
    }
}
