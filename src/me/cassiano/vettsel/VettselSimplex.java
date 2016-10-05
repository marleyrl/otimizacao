package me.cassiano.vettsel;


public class VettselSimplex {

    public VettselSimplex() {
    }

    public enum Operation {
        MAX, MIN
    }

    public enum RestrictionType {
        GREATER_THAN, LESS_THAN, EQUALS
    }

    public enum SolutionStatus {
        UNLIMITED, IMPOSSIBLE, MULTIPLE_SOLUTIONS, OPTIMAL
    }
    
}
