package me.cassiano.vettsel.interfaces;

public interface Restriction {

    double getCoeficient(int index);

    void setCoeficient(int index, double value);

    int size();

    Type getType();

    void setType(Type type);

    enum Type {

        GREATER_THAN("gt"), LESS_THAN("lt"), EQUALS("eq");

        private String text;

        Type(String text) {
            this.text = text;
        }

        public static Type fromString(String text) {

            if (text != null) {
                for (Type b : Type.values()) {
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
