package com.example.githubclient.model.github;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Commit implements Serializable {

    @JsonProperty("author")
    private Author author;

    @JsonProperty("message")
    private String message;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
