package me.cassiano.vetwebservice.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;


public class ErrorResponse {

    @SerializedName("reason")
    private String reason;

    @SerializedName("message")
    private String message;

    @SerializedName("stack_trace")
    private String stack;

    public ErrorResponse(Throwable throwable) {
        this.reason = throwable.getClass().getSimpleName();
        this.message = throwable.getMessage();
        this.stack = Arrays.toString(throwable.getStackTrace());
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
