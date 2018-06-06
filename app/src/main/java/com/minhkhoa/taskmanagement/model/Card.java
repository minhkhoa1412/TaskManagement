package com.minhkhoa.taskmanagement.model;

import java.util.ArrayList;
import java.util.Date;

public class Card {
    private String cardID;
    private String listID;
    private String cardName;
    private String cardDescription;
    private ArrayList<Integer> cardTag;
    private ArrayList<User> userArrayList;
    private Date cardDeadline;

    public Card() {
    }

    public Card(String cardID, String listID, String cardName, String cardDescription, ArrayList<Integer> cardTag, ArrayList<User> userArrayList, Date cardDeadline) {
        this.cardID = cardID;
        this.listID = listID;
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.cardTag = cardTag;
        this.userArrayList = userArrayList;
        this.cardDeadline = cardDeadline;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getListID() {
        return listID;
    }

    public void setListID(String listID) {
        this.listID = listID;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public ArrayList<Integer> getCardTag() {
        return cardTag;
    }

    public void setCardTag(ArrayList<Integer> cardTag) {
        this.cardTag = cardTag;
    }

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public void setUserArrayList(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    public Date getCardDeadline() {
        return cardDeadline;
    }

    public void setCardDeadline(Date cardDeadline) {
        this.cardDeadline = cardDeadline;
    }
}
