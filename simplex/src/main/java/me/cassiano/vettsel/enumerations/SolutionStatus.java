package me.cassiano.vettsel.enumerations;

public enum SolutionStatus {
    UNLIMITED("UNLIMITED"), IMPOSSIBLE("IMPOSSIBLE"),
    MULTIPLE_SOLUTIONS("MULTIPLE_SOLUTIONS"), OPTIMAL("OPTIMAL");

    private String text;

    SolutionStatus(String text) {
        this.text = text;
    }

    public static SolutionStatus fromString(String text) {

        if (text != null) {
            for (SolutionStatus b : SolutionStatus.values()) {
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
