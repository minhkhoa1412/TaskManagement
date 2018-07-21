package com.minhkhoa.taskmanagement.util;

import com.minhkhoa.taskmanagement.model.User;

public class Notification {
    private String notificationID;
    private String boardID;
    private String chatChannelID;
    private User user;
    private Boolean notificationStatus;

    public Notification() {
    }

    public Notification(String notificationID, String boardID, String chatChannelID, User user, Boolean notificationStatus) {
        this.notificationID = notificationID;
        this.boardID = boardID;
        this.chatChannelID = chatChannelID;
        this.user = user;
        this.notificationStatus = notificationStatus;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    public String getChatChannelID() {
        return chatChannelID;
    }

    public void setChatChannelID(String chatChannelID) {
        this.chatChannelID = chatChannelID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
