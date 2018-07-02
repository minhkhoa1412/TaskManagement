package com.minhkhoa.taskmanagement.model;

public class Task {
    private String taskID;
    private Boolean taskStatus;
    private String taskName;

    public Task() {
    }

    public Task(String taskID, Boolean taskStatus, String taskName) {
        this.taskID = taskID;
        this.taskStatus = taskStatus;
        this.taskName = taskName;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public Boolean getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
