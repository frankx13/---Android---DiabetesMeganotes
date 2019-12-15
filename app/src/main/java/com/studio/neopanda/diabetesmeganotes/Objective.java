package com.studio.neopanda.diabetesmeganotes;

public class Objective {
    String date;
    String type;
    String duration;
    String description;
    int idEntry;

    public Objective(String date, String type, String duration, String description) {
        this.date = date;
        this.type = type;
        this.duration = duration;
        this.description = description;
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

    public int getIdEntry() {
        return idEntry;
    }

    public void setIdEntry(int idEntry) {
        this.idEntry = idEntry;
    }
}