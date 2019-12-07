package com.studio.neopanda.diabetesmeganotes;

public class Glycemy {

    String date;
    String glycemyLevel;
    int idEntry;

    public Glycemy(String date, String glycemyLevel) {
        this.date = date;
        this.glycemyLevel = glycemyLevel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGlycemyLevel() {
        return glycemyLevel;
    }

    public void setGlycemyLevel(String glycemyLevel) {
        this.glycemyLevel = glycemyLevel;
    }

    public int getIdEntry() {
        return idEntry;
    }

    public void setIdEntry(int idEntry) {
        this.idEntry = idEntry;
    }
}
