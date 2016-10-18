package me.cassiano.vettsel.utils;

import java.util.Arrays;

public class MatrixPrinter {

    public static void printMatrix(Object[][] matrix) {

        Arrays.stream(matrix)
                .forEach(
                        (row) -> {
                            System.out.print("[");
                            Arrays.stream(row)
                                    .forEach(
                                            (el) -> System.out.print(String.format("%20s", el.toString()))
                                    );
                            System.out.println("]");
                        }
                );

        System.out.println();
    }

}