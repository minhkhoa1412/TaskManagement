package com.minhkhoa.taskmanagement.model;

public class User {
    private String userID;
    private String userName;
    private int userPermission = 0;
    private String userEmail;
    private String userAvata = "https://firebasestorage.googleapis.com/v0/b/taskmanagement-b965a.appspot.com/o/hot-girl-3.jpg?alt=media&token=7beb21ab-9b10-4750-9108-eac61d85e47a";

    public User() {
    }

    public User(String userID, String userName, int userPermission, String userEmail, String userAvata) {
        this.userID = userID;
        this.userName = userName;
        this.userPermission = userPermission;
        this.userEmail = userEmail;
        this.userAvata = userAvata;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
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
