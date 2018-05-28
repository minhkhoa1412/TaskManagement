package com.minhkhoa.taskmanagement.model;

import android.graphics.Bitmap;

public class Board {
    private int boardID;
    private String boardName;
    private String boardImage;

    public Board() {
    }

    public Board(int boardID, String boardName, String boardImage) {
        this.boardID = boardID;
        this.boardName = boardName;
        this.boardImage = boardImage;
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
}
