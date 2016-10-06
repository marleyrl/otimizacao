package me.cassiano.vettsel.interfaces;

import java.util.stream.Stream;

public interface Table {

    Cell getCell(int line, int column);

    void setCell(int line, int column, Cell cell);

    int rows();

    int columns();
}
