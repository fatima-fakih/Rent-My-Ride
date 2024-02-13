package com.example.rentmyride;

public class Card {
    private int cardId;
    private int userId;
    private String name;
    private String number;
    private String expiryDate;
    private String cvv;

    public Card(int userId, String name, String number, String expiryDate, String cvv) {
        this.userId = userId;
        this.name = name;
        this.number = number;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public int getcardid() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}