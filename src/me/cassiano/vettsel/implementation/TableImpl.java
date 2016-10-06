package me.cassiano.vettsel.implementation;


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

    public void print() {
        MatrixPrinter.printMatrix(table);
    }
}
