package com.studio.neopanda.diabetesmeganotes.models;

public class InsulinBinder {
    public int id;
    public String date;
    public String numberUnit;
    public String dataID;

    public InsulinBinder(String date, String numberUnit, String dataID) {
        this.date = date;
        this.numberUnit = numberUnit;
        this.dataID = dataID;
    }

    public InsulinBinder(String date, String numberUnit) {
        this.date = date;
        this.numberUnit = numberUnit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumberUnit() {
        return numberUnit;
    }

    public void setNumberUnit(String numberUnit) {
        this.numberUnit = numberUnit;
    }

    public int getIdEntry() {
        return id;
    }

    public void setIdEntry(int id) {
        this.id = id;
    }
}
