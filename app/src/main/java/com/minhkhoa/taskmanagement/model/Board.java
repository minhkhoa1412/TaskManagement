package com.minhkhoa.taskmanagement.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable{
    private String boardID;
    private String userID;
    private String boardName;
    private String boardImage;
    private ArrayList<User> userArrayList;

    public Board() {
    }

    public Board(String boardID, String userID, String boardName, String boardImage, ArrayList<User> userArrayList) {
        this.boardID = boardID;
        this.userID = userID;
        this.boardName = boardName;
        this.boardImage = boardImage;
        this.userArrayList = userArrayList;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardImage() {
        return boardImage;
    }

    public void setBoardImage(String boardImage) {
        this.boardImage = boardImage;
    }

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public void setUserArrayList(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
