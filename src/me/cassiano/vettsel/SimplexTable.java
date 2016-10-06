package me.cassiano.vettsel;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import me.cassiano.vettsel.implementation.CellImpl;
import me.cassiano.vettsel.implementation.TableImpl;
import me.cassiano.vettsel.interfaces.Cell;
import me.cassiano.vettsel.interfaces.Function;
import me.cassiano.vettsel.interfaces.Restriction;
import me.cassiano.vettsel.interfaces.Table;

public class SimplexTable {

    public static final int BREAK_FLAG = -9182;
    public static final int SOLUTION_UNLIMITED_FLAG = -1232;
    public static final int SOLUTION_IMPOSSIBLE_FLAG = -2334;

    private Table table;
    private Function function;
    private List<Restriction> restrictions;

    private int[] basic;
    private int[] nonBasic;

    public SimplexTable(Function function,
                        List<Restriction> restrictions) {
        this.function = function;
        this.restrictions = restrictions;

        this.nonBasic = IntStream
                .range(0, function.size() - 1)
                .toArray();

        this.basic = IntStream
                .range(0, restrictions.size())
                .map(x -> x + nonBasic.length)
                .toArray();

        initTable();
        setFunctionCells();
        setRestrictionsCells();

    }

    private void initTable() {
        table = new TableImpl(function.size(), restrictions.size());
    }

    private void setFunctionCells() {

        /* (0,0) should be 0 now */
        table.setCell(0, 0, new CellImpl(0.0, null));

        for (int i = 1; i < function.size(); i++) {
            Cell newCell = new CellImpl(function.getCoeficient(i), null);
            table.setCell(0, i, newCell);
        }

    }

    private void setRestrictionsCells() {

        for (int i = 1; i < table.rows(); i++) {

            /* (i - 1): extra line for the f(x) values */
            Restriction restriction = restrictions.get(i - 1);

            /* filling up free members */
            table.setCell(i, 0, new CellImpl(restriction.getCoeficient(0), null));

            /* filling up non basic vars */
            for (int j = 1; j < table.columns(); j++)
                table.setCell(i, j, new CellImpl(restriction.getCoeficient(j), null));
        }

    }

    public Integer getNegativeFreeMemberPosition() {

        /* get all the rows, stream values from column indicating positive values with -1
           filters the values to stream and eliminates -1, then tries to find the
           first negative value, if it can't return -1 */

        return IntStream
                .range(1, table.rows())
                .flatMap(i -> IntStream.of(table.getCell(i, 0).getUpper() < 0 ? i : -1))
                .filter(i -> i != -1)
                .findFirst()
                .orElse(BREAK_FLAG);
    }

    public int getPermissibleColumnPosition(int line) {

        /* get all the columns, stream values from column indicating positive values with -1
           filters the values to stream and eliminates -1, then tries to find the
           smallest negative value, if it can't return -1 */

        return IntStream
                .range(1, table.columns())
                .flatMap(i -> IntStream.of(table.getCell(line, i).getUpper() < 0 ? i : -1))
                .filter(i -> i != -1)
                .findAny()
                .orElse(SOLUTION_IMPOSSIBLE_FLAG);

    }

    public int getPermissibleLineElementPosition(int column) {

        return IntStream
                .range(1, table.rows())
                .filter(i -> table.getCell(i, column).getUpper() != 0)
                .filter(i -> sameSign(i, column))
                .mapToObj(line -> new AbstractMap.SimpleEntry<>(line, calcQuotient(line, column)))
                .min(Comparator.comparingDouble(AbstractMap.SimpleEntry::getValue))
                .map(AbstractMap.SimpleEntry::getKey)
                .orElse(-1);
    }

    public int getPositiveFunctionValuePosition() {

        int position = IntStream
                .range(0, table.columns())
                .filter(x -> table.getCell(0, x).getUpper() >= 0)
                .findAny()
                .orElse(BREAK_FLAG);

        if (position == 0)
            position = SOLUTION_UNLIMITED_FLAG;

        return position;
    }

    public void runSwapAlgorithm(int line, int column) {

        Cell selectedElement = table.getCell(line, column);

        double element = selectedElement.getUpper();
        double inverse = 1 / element;

        selectedElement.setLower(inverse);

        calculateSelectedLineLowers(line, column, inverse);
        calculateSelectedColumnLowers(line, column, -inverse);
        calculateLowers(line, column);

        rearrangeTable(line, column);
        swapVariables(line, column);

    }

    private void swapVariables(int line, int column) {
        int tempNonBasicVar = nonBasic[column - 1];
        nonBasic[column - 1] = basic[line - 1];
        basic[line - 1] = tempNonBasicVar;
    }

    private void rearrangeTable(int line, int column) {

        swapUpperLowerForSelected(line, column);
        sumLowers(line, column);

    }

    private void sumLowers(int line, int column) {

        int lines[] = IntStream
                .range(0, table.rows())
                .filter(x -> x != line)
                .toArray();

        int columns[] = IntStream
                .range(0, table.columns())
                .filter(x -> x != column)
                .toArray();

        for (int i : lines) {
            for (int j : columns) {

                Cell cell = table.getCell(i, j);

                double value = cell.getUpper() + cell.getLower();
                cell.setUpper(value);
                cell.setLower(null);
            }
        }

        printTable();
    }

    private void swapUpperLowerForSelected(int line, int column) {

        IntStream
                .range(0, table.rows())
                .mapToObj(x -> table.getCell(x, column))
                .forEach(cell -> {
                    double lowerValue = cell.getLower();
                    cell.setUpper(lowerValue);
                    cell.setLower(null);
                });

        IntStream
                .range(0, table.columns())
                .mapToObj(x -> table.getCell(line, x))
                .filter(x -> x.getLower() != null)
                .forEach(cell -> {
                    double lowerValue = cell.getLower();
                    cell.setUpper(lowerValue);
                    cell.setLower(null);
                });
    }

    private void calculateLowers(int line, int column) {

        int lines[] = IntStream
                .range(0, table.rows())
                .filter(x -> x != line)
                .toArray();

        int columns[] = IntStream
                .range(0, table.columns())
                .filter(x -> x != column)
                .toArray();

        for (int i : lines) {
            for (int j : columns) {

                double columnElement = table.getCell(line, j).getUpper();
                double lineElement = table.getCell(i, column).getLower();
                double value = columnElement * lineElement;

                table.getCell(i, j).setLower(value);

            }
        }


    }

    private void calculateSelectedColumnLowers(int row, int column, double inverseOposite) {

        IntStream
                .range(0, table.rows())
                .filter(i -> i != row)
                .mapToObj(i -> table.getCell(i, column))
                .forEach(cell -> cell.setLower(cell.getUpper() * inverseOposite));

    }

    private void calculateSelectedLineLowers(int row, int column, double inverse) {

        IntStream
                .range(0, table.columns())
                .filter(i -> i != column)
                .mapToObj(i -> table.getCell(row, i))
                .forEach(cell -> cell.setLower(cell.getUpper() * inverse));


    }

    private Double calcQuotient(int line, int column) {

        double freeMemberValue = table.getCell(line, 0).getUpper();
        return freeMemberValue / table.getCell(line, column).getUpper();
    }

    private boolean sameSign(int line, int column) {

        double freeMemberValue = table.getCell(line, 0).getUpper();
        double value1 = table.getCell(line, column).getUpper();

        return ((value1 > 0 && freeMemberValue > 0) ||
                (value1 < 0 && freeMemberValue < 0));
    }

    public double[] getSolutionVariables() {

        double[] values = new double[basic.length + nonBasic.length + 1];

        values[0] = table.getCell(0, 0).getUpper();

        IntStream
                .range(0, basic.length)
                .forEach(i -> {
                    int index = basic[i] + 1;
                    values[index] = table.getCell(i + 1, 0).getUpper();
                });

        IntStream
                .range(0, nonBasic.length)
                .forEach(i -> {
                    int index = nonBasic[i] + 1;
                    values[index] = table.getCell(0, i + 1).getUpper();
                });

        return values;
    }

    public void printTable() {
        ((TableImpl) table).print();
    }
}
