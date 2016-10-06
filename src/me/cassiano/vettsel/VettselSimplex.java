package me.cassiano.vettsel;


import java.util.List;

import me.cassiano.vettsel.interfaces.Function;
import me.cassiano.vettsel.interfaces.Restriction;

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


    public SolutionStatus run(Function function, List<Restriction> restrictions) {

        table = new SimplexTable(function, restrictions);
        firstPhase();

        return SolutionStatus.IMPOSSIBLE;
    }

    private SolutionStatus firstPhase() {

        int negativePosition;

        while ((negativePosition = table.getNegativeFreeMemberPosition()) != -1) {

            int permissibleColumn = table.getPermissibleColumnPosition(negativePosition);

            if (permissibleColumn == -1)
                return SolutionStatus.IMPOSSIBLE;

            int permissibleLine = table.getPermissibleLineElementPosition(permissibleColumn);

            table.runSwapAlgorithm(permissibleLine, permissibleColumn);
        }

        return SolutionStatus.OPTIMAL;
    }


    public enum SolutionStatus {
        UNLIMITED, IMPOSSIBLE, MULTIPLE_SOLUTIONS, OPTIMAL
    }

}
