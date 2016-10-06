package me.cassiano.vettsel.implementation;

import me.cassiano.vettsel.interfaces.Cell;


public class CellImpl implements Cell {

    private Double upper;
    private Double lower;

    public CellImpl(Double upper, Double lower) {
        this.upper = upper;
        this.lower = lower;
    }

    @Override
    public String toString() {

        String upperStr = upper == null ? "(-)" : String.valueOf(upper);
        String lowerStr = lower == null ? "(-)" : String.valueOf(lower);

        return upperStr + "/" + lowerStr;
    }

    @Override
    public Double getUpper() {
        return upper;
    }

    @Override
    public Double getLower() {
        return lower;
    }

    @Override
    public void setUpper(Double value) {
        this.upper = value;
    }

    @Override
    public void setLower(Double value) {
        this.lower = value;
    }
}
