package com.minhkhoa.taskmanagement.model;

public class User {
    private int userID;
    private String userName;
    private int userPermission;
    private String userEmail;
    private String userAvata;

    public User() {
    }

    public User(int userID, String userName, int userPermission, String userEmail, String userAvata) {
        this.userID = userID;
        this.userName = userName;
        this.userPermission = userPermission;
        this.userEmail = userEmail;
        this.userAvata = userAvata;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(int userPermission) {
        this.userPermission = userPermission;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAvata() {
        return userAvata;
    }

    public void setUserAvata(String userAvata) {
        this.userAvata = userAvata;
    }
}
