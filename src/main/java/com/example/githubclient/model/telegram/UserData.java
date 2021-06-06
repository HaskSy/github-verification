package com.example.githubclient.model.telegram;

import java.sql.Timestamp;

public class UserData {

    private int repoId;
    private String repoName;
    private boolean repoIsChecked;
    private Timestamp repoCheckDate;
    private int repoStudentId;
    private int studentStudentId;
    private String studentFirstName;
    private String studentLastName;
    private String studentLogin;

    public int getRepoId() {
        return repoId;
    }

    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public boolean isRepoIsChecked() {
        return repoIsChecked;
    }

    public void setRepoIsChecked(boolean repoIsChecked) {
        this.repoIsChecked = repoIsChecked;
    }

    public Timestamp getRepoCheckDate() {
        return repoCheckDate;
    }

    public void setRepoCheckDate(Timestamp repoCheckDate) {
        this.repoCheckDate = repoCheckDate;
    }

    public int getRepoStudentId() {
        return repoStudentId;
    }

    public void setRepoStudentId(int repoStudentId) {
        this.repoStudentId = repoStudentId;
    }

    public int getStudentStudentId() {
        return studentStudentId;
    }

    public void setStudentStudentId(int studentStudentId) {
        this.studentStudentId = studentStudentId;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getStudentLogin() {
        return studentLogin;
    }

    public void setStudentLogin(String studentLogin) {
        this.studentLogin = studentLogin;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "repoId=" + repoId +
                ", repoName='" + repoName + '\'' +
                ", repoIsChecked=" + repoIsChecked +
                ", repoCheckDate=" + repoCheckDate +
                ", repoStudentId=" + repoStudentId +
                ", studentStudentId=" + studentStudentId +
                ", studentFirstName='" + studentFirstName + '\'' +
                ", studentLastName='" + studentLastName + '\'' +
                ", studentLogin='" + studentLogin + '\'' +
                '}';
    }
}
