package me.cassiano.vettsel.implementation;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import me.cassiano.vettsel.interfaces.Cell;
import me.cassiano.vettsel.interfaces.Table;
import me.cassiano.vettsel.utils.MatrixPrinter;

public class TableImpl implements Table {

    private Cell table[][];

    public TableImpl(int variables, int restrictions) {
        this.table = new Cell[restrictions + 1][variables];
    }

    @Override
    public Cell getCell(int line, int column) {
        return table[line][column];
    }

    @Override
    public void setCell(int line, int column, Cell cell) {
        table[line][column] = cell;
    }

    @Override
    public int rows() {
        return table.length;
    }

    @Override
    public int columns() {
        return table[0].length;
    }

    public Stream<Cell> streamColumn(int column) {
        return IntStream
                .range(0, table.length)
                .mapToObj(i -> table[i][column]);
    }

    public Stream<Cell> streamRow(int row) {
        return IntStream
                .range(0, table[row].length)
                .mapToObj(i -> table[row][i]);
    }

    public void print() {
        MatrixPrinter.printMatrix(table);
    }
}
