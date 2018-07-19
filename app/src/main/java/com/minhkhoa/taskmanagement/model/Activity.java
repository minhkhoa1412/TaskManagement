package com.minhkhoa.taskmanagement.model;

import java.util.Date;

public class Activity {
    private String activityID;
    private Date activityTime;
    private String userName;
    private String cardID;
    private String userAvata;
    private String taskName;

    public Activity() {
    }

    public Activity(String activityID, Date activityTime, String userName, String cardID, String userAvata, String taskName) {
        this.activityID = activityID;
        this.activityTime = activityTime;
        this.userName = userName;
        this.cardID = cardID;
        this.userAvata = userAvata;
        this.taskName = taskName;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getUserAvata() {
        return userAvata;
    }

    public void setUserAvata(String userAvata) {
        this.userAvata = userAvata;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(Date activityTime) {
        this.activityTime = activityTime;
    }
}
