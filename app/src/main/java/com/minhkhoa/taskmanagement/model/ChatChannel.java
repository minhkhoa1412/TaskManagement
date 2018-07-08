package com.minhkhoa.taskmanagement.model;

import java.util.ArrayList;

public class ChatChannel {
    private String channelID;
    private String channelName;
    private String boardcardID;
    private ArrayList<User> userArrayList;
    private ArrayList<Chat> chatArrayList;

    public ChatChannel() {
    }

    public ChatChannel(String channelID, String channelName, String boardcardID, ArrayList<User> userArrayList, ArrayList<Chat> chatArrayList) {
        this.channelID = channelID;
        this.channelName = channelName;
        this.boardcardID = boardcardID;
        this.userArrayList = userArrayList;
        this.chatArrayList = chatArrayList;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getBoardcardID() {
        return boardcardID;
    }

    public void setBoardcardID(String boardcardID) {
        this.boardcardID = boardcardID;
    }

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public void setUserArrayList(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    public ArrayList<Chat> getChatArrayList() {
        return chatArrayList;
    }

    public void setChatArrayList(ArrayList<Chat> chatArrayList) {
        this.chatArrayList = chatArrayList;
    }
}
