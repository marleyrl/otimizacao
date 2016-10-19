package me.cassiano.vettsel;


import java.util.List;

import me.cassiano.vettsel.implementation.PartialSolutionImpl;
import me.cassiano.vettsel.interfaces.Function;
import me.cassiano.vettsel.interfaces.PartialSolution;
import me.cassiano.vettsel.interfaces.Restriction;

import static me.cassiano.vettsel.SimplexTable.BREAK_FLAG;
import static me.cassiano.vettsel.SimplexTable.SOLUTION_UNLIMITED_FLAG;

public class VettselSimplex {

    private static VettselSimplex instance;
    private static SimplexTable table;

    private VettselSimplex() {
    }

    public static VettselSimplex get() {

        if (instance == null)
            instance = new VettselSimplex();

        return instance;
    }


    public PartialSolution run(Function function, List<? extends Restriction> restrictions)
            throws SimplexTable.VariableSizesDontMatch {

        table = new SimplexTable(function, restrictions);

        try {
            firstPhase();
            secondPhase();
        } catch (ImpossibleSolutionException e) {
            return PartialSolutionImpl.impossible();
        } catch (UnlimitedSolutionException e) {
            return PartialSolutionImpl.unlimited();
        }

        return PartialSolutionImpl.optimal(table.getSolutionVariables());
    }

    private void firstPhase() throws ImpossibleSolutionException {

        int negativePosition;

        while ((negativePosition = table.getNegativeFreeMemberPosition()) != BREAK_FLAG) {

            int permissibleColumn = table.getPermissibleColumnPosition(negativePosition);

            if (permissibleColumn == -1)
                throw new ImpossibleSolutionException();

            int permissibleLine = table.getPermissibleLineElementPosition(permissibleColumn);
            table.runSwapAlgorithm(permissibleLine, permissibleColumn);
        }
    }

    private void secondPhase() throws UnlimitedSolutionException {

        int permissibleColumn;

        while ((permissibleColumn = table.getPositiveFunctionValuePosition()) != BREAK_FLAG) {

            if (permissibleColumn == SOLUTION_UNLIMITED_FLAG)
                throw new UnlimitedSolutionException();

            int permissibleLine = table.getPermissibleLineElementPosition(permissibleColumn);
            table.runSwapAlgorithm(permissibleLine, permissibleColumn);

        }

    }


    public class ImpossibleSolutionException extends Exception {

    }

    public class UnlimitedSolutionException extends Exception {

    }

}
