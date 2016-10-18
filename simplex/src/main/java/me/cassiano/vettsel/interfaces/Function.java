package me.cassiano.vettsel.interfaces;

public interface Function {

    double getCoeficient(int index);

    void setCoeficient(int index, double value);

    int size();

    Operation getOperation();

    void setOperation(Operation type);

    boolean isMax();

    boolean isMin();

    enum Operation {

        MAX("max"), MIN("min");

        private String text;

        Operation(String text) {
            this.text = text;
        }

        public static Operation fromString(String text) {

            if (text != null) {
                for (Operation b : Operation.values()) {
                    if (text.equalsIgnoreCase(b.text)) {
                        return b;
                    }
                }
            }
            return null;
        }

        public String getText() {
            return this.text;
        }
    }
}
