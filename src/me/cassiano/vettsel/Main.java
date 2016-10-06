package me.cassiano.vettsel;

import java.util.ArrayList;
import java.util.List;

import me.cassiano.vettsel.interfaces.Function;
import me.cassiano.vettsel.interfaces.Restriction;

public class Main {

    public static void main(String[] args) {

        VettselSimplex.get().run(getTestFunction(), getTestRestrictions()).printSolution();
    }

    private static Function getTestFunction() {
        return new Function() {

            double coeficients[] = {0, 80, 60};

            @Override
            public double getCoeficient(int index) {
                return coeficients[index];
            }

            @Override
            public void setCoeficient(int index, double value) {
                coeficients[index] = value;
            }

            @Override
            public int size() {
                return coeficients.length;
            }

            @Override
            public Operation getOperation() {
                return Operation.MAX;
            }

            @Override
            public void setOperation(Operation type) {

            }

            @Override
            public boolean isMax() {
                return true;
            }

            @Override
            public boolean isMin() {
                return false;
            }
        };
    }

    private static List<Restriction> getTestRestrictions() {

        double restrictions[][] = {{-24, -4, -6}, {16, 4, 2}, {3, 0, 1}};
        ArrayList<Restriction> restrictions1 = new ArrayList<>();

        for (int i = 0; i < restrictions.length; i++) {

            int finalI = i;
            Restriction restriction = new Restriction() {

                double coeficients[] = restrictions[finalI];

                @Override
                public double getCoeficient(int index) {
                    return coeficients[index];
                }

                @Override
                public void setCoeficient(int index, double value) {
                    coeficients[index] = value;
                }

                @Override
                public int size() {
                    return coeficients.length;
                }

                @Override
                public Type getType() {
                    return Type.GREATER_THAN;
                }

                @Override
                public void setType(Type type) {

                }
            };

            restrictions1.add(restriction);

        }


        return restrictions1;
    }
}
