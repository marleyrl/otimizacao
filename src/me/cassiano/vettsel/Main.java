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

            /*
                x1: how many pokemon to catch (100 xp) *2
                x2: how many pokemon to evolve (500 xp) *2
                x3: how many transfers (45 xp) *2
                x4: how many 2km eggs to hatch (200 xp) *2
                x5: how many 5km eggs to hatch (500 xp) *2
                x6: how many 10km eggs to hatch (1000 xp) *2
             */

            double coeficients[] = {0, 200, 1000, 90, 400, 1000, 2000};

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

        double restrictions[][] = {{30, 2, 2, 19, 5, 5, 5}, {10, 0, 0, 0, 2, 5, 10}};
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
