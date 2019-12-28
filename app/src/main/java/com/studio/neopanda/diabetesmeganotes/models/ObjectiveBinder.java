package com.studio.neopanda.diabetesmeganotes.models;

public class ObjectiveBinder {
    public String date;
    public String type;
    public String duration;
    public String description;
    public String userID;
    public int idEntry;

    public ObjectiveBinder(String date, String type, String duration, String description, String userID) {
        this.date = date;
        this.type = type;
        this.duration = duration;
        this.description = description;
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getIdEntry() {
        return idEntry;
    }

    public void setIdEntry(int idEntry) {
        this.idEntry = idEntry;
    }
}
