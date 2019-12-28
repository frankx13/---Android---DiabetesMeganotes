package com.studio.neopanda.diabetesmeganotes.models;

public class NoteBinder {
    public String userID;
    public String note;

    public NoteBinder(String userID, String note) {
        this.userID = userID;
        this.note = note;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
