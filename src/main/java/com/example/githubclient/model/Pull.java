package com.example.githubclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Pull implements Serializable {

    @JsonProperty("title")
    private String title;
    @JsonProperty("number")
    private int number;
    @JsonProperty("state")
    private String state;


    public String getTitle() {
        return title;
    }

    public int getNumber() {
        return number;
    }

    public String getState() {
        return state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setState(String state) {
        this.state = state;
    }
}
