package com.minhkhoa.taskmanagement.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Board {
    private int boardID;
    private String boardName;
    private String boardImage;
    private ArrayList<User> userArrayList;

    public Board() {
    }

    public Board(int boardID, String boardName, String boardImage, ArrayList<User> userArrayList) {
        this.boardID = boardID;
        this.boardName = boardName;
        this.boardImage = boardImage;
        this.userArrayList = userArrayList;
    }

    public int getBoardID() {
        return boardID;
    }

    public void setBoardID(int boardID) {
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
}
