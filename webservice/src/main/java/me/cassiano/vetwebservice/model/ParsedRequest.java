package me.cassiano.vetwebservice.model;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import me.cassiano.vettsel.interfaces.PartialSolution;

public class ParsedRequest {

    @SerializedName("function")
    private FunctionModel function;

    @SerializedName("restrictions")
    private List<RestrictionModel> restrictions;

    private ParsedRequest() {

        ArrayList<PartialSolution> partialSolutions;
    }

    public static ParsedRequest parse(String string) {
        Gson gson = new Gson();
        return gson.fromJson(string, ParsedRequest.class);
    }

    public List<RestrictionModel> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<RestrictionModel> restrictions) {
        this.restrictions = restrictions;
    }

    public FunctionModel getFunction() {
        return function;
    }

    public void setFunction(FunctionModel function) {
        this.function = function;
    }
}
