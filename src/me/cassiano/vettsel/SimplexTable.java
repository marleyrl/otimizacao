package me.cassiano.vettsel;


import java.util.List;
import java.util.stream.IntStream;

import me.cassiano.vettsel.implementation.CellImpl;
import me.cassiano.vettsel.implementation.TableImpl;
import me.cassiano.vettsel.interfaces.Cell;
import me.cassiano.vettsel.interfaces.Function;
import me.cassiano.vettsel.interfaces.Restriction;
import me.cassiano.vettsel.interfaces.Table;

public class SimplexTable {

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
                .range(0, function.size())
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
                .orElse(-1);
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
                .orElse(-1);

    }

    public int getPermissibleLineElementPosition(int column) {

        return IntStream
                .range(1, table.rows())
                .filter(i -> table.getCell(i, column).getUpper() != 0)
                .reduce((i, j) -> compareQuotients(i, j, column) ? j : i)
                .orElse(-1);

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

        ((TableImpl) table).print();


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

    private boolean compareQuotients(int line1, int line2, int column) {

        double freeMemberValue = table.getCell(line1, 0).getUpper();
        double value1 = table.getCell(line1, column).getUpper() / freeMemberValue;
        double value2 = table.getCell(line2, column).getUpper() / freeMemberValue;

        return value1 > value2;
    }
}
