package com.minhkhoa.taskmanagement.model;

import java.util.Date;

public class Chat {
    private String chatID;
    private String chatContent;
    private String userID;
    private String userName;
    private String userAvata;
    private Date chatTime;

    public Chat() {
    }


    public Chat(String chatID, String chatContent, String userID, String userName, String userAvata, Date chatTime) {
        this.chatID = chatID;
        this.chatContent = chatContent;
        this.userID = userID;
        this.userName = userName;
        this.userAvata = userAvata;
        this.chatTime = chatTime;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
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

    public String getUserAvata() {
        return userAvata;
    }

    public void setUserAvata(String userAvata) {
        this.userAvata = userAvata;
    }

    public Date getChatTime() {
        return chatTime;
    }

    public void setChatTime(Date chatTime) {
        this.chatTime = chatTime;
    }
}
