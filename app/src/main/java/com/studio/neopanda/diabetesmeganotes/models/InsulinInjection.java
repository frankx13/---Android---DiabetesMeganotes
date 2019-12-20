package com.studio.neopanda.diabetesmeganotes.models;

public class InsulinInjection {

    public String date;
    public String numberUnit;
    public int idEntry;

    public InsulinInjection(String date, String numberUnit) {
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
        return idEntry;
    }

    public void setIdEntry(int idEntry) {
        this.idEntry = idEntry;
    }
}
