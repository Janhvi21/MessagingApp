package com.example.midterm_2018;

public class LoggedUser {
    String token;
    String User_email;
    String fname;
    String lname;
    String role;
    int userID;

    @Override
    public String toString() {
        return "LoggedUser{" +
                "token='" + token + '\'' +
                ", User_email='" + User_email + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", role='" + role + '\'' +
                ", userID=" + userID +
                '}';
    }

    public LoggedUser() {
    }



    public LoggedUser(String token, String user_email, String fname, String lname, String role, int userID) {
        this.token = token;
        User_email = user_email;
        this.fname = fname;
        this.lname = lname;
        this.role = role;
        this.userID = userID;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser_email(String user_email) {
        User_email = user_email;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public String getUser_email() {
        return User_email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getRole() {
        return role;
    }

    public int getUserID() {
        return userID;
    }
}
