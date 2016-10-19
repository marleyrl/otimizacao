package me.cassiano.vetwebservice.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.cassiano.vettsel.interfaces.Restriction;


public class RestrictionModel implements Restriction {

    @SerializedName("coeficients")
    private List<Double> coeficients;

    @SerializedName("type")
    private String type;

    @SerializedName("value")
    private double value;

    @Override
    public double getCoefficient(int index) {

        if (index == 0)
            return value;

        return coeficients.get(index - 1);
    }

    @Override
    public void setCoefficient(int index, double value) {

        if (value == 0)
            return;

        coeficients.set(index - 1, value);
    }

    @Override
    public int size() {
        return coeficients.size() + 1;
    }

    @Override
    public Type getType() {
        Type type = Type.fromString(this.type);

        if (type == null)
            throw new IllegalStateException("Invalid restriction type.");

        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type.getText();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
