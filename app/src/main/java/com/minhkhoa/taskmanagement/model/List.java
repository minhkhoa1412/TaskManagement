package com.minhkhoa.taskmanagement.model;

import java.util.ArrayList;

public class List {
    private String listID;
    private String boardID;
    private String listName;
    private ArrayList<User> userArrayList;

    public List() {
    }

    public List(String listID, String boardID, String listName, ArrayList<User> userArrayList) {
        this.listID = listID;
        this.boardID = boardID;
        this.listName = listName;
        this.userArrayList = userArrayList;
    }

    public String getListID() {
        return listID;
    }

    public void setListID(String listID) {
        this.listID = listID;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public void setUserArrayList(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }
}
