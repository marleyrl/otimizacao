package me.cassiano.vettsel.interfaces;

public interface Restriction {

    double getCoeficient(int index);

    void setCoeficient(int index, double value);

    int size();

    Type getType();

    void setType(Type type);

    enum Type {
        GREATER_THAN, LESS_THAN, EQUALS
    }
}
