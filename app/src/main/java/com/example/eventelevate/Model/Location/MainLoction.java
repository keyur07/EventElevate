package com.example.eventelevate.Model.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainLoction {

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("loactions")
    @Expose
    private List<Loaction> loactions;


    public MainLoction(Integer statusCode, String message, List<Loaction> loactions) {
        this.statusCode = statusCode;
        this.message = message;
        this.loactions = loactions;
    }


    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Loaction> getLoactions() {
        return loactions;
    }

    public void setLoactions(List<Loaction> loactions) {
        this.loactions = loactions;
    }
}
