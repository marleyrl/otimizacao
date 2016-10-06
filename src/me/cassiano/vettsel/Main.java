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

            double coeficients[] = {0, -7, -8.5};

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
                return Operation.MIN;
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

        double restrictions[][] = {{-16, -0.6, -0.8}, {1800, 24, 20}};
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
