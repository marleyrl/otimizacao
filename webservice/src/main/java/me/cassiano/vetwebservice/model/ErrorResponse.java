package me.cassiano.vetwebservice.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;


public class ErrorResponse {

    @SerializedName("reason")
    private String reason;

    @SerializedName("message")
    private String message;

    public ErrorResponse(Throwable throwable) {
        this.reason = throwable.getClass().toString();
        this.message = throwable.getMessage();
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

    public String toJson() {
        return new Gson().toJson(this);
    }
}
