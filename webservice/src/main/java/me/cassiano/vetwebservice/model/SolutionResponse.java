package me.cassiano.vetwebservice.model;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import me.cassiano.vettsel.enumerations.SolutionStatus;
import me.cassiano.vettsel.interfaces.PartialSolution;

public class SolutionResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("values")
    private List<Double> values;

    public SolutionResponse(String status, List<Double> values) {
        this.status = status;
        this.values = values;
    }

    public static SolutionResponse fromSolution(PartialSolution solution) {

        List<Double> values = null;
        String status = solution.getSolutionStatus().getText();

        if (solution.getSolutionStatus() == SolutionStatus.OPTIMAL) {
            values = new ArrayList<>();

            for (int i = 0; i < solution.size(); i++)
                values.add(solution.getVariableValue(i));
        }

        return new SolutionResponse(status, values);

    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
