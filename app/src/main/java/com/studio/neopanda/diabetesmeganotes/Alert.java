package com.studio.neopanda.diabetesmeganotes;

public class Alert {
    public String name;//
    public String description;//
    public String type;//
    public String startMoment;//
    public String endMoment;//
    public int idEntry;

    public Alert(String name, String description, String type, String startMoment, String endMoment) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.startMoment = startMoment;
        this.endMoment = endMoment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartMoment() {
        return startMoment;
    }

    public void setStartMoment(String startMoment) {
        this.startMoment = startMoment;
    }

    public String getEndMoment() {
        return endMoment;
    }

    public void setEndMoment(String endMoment) {
        this.endMoment = endMoment;
    }

    public int getIdEntry() {
        return idEntry;
    }

    public void setIdEntry(int idEntry) {
        this.idEntry = idEntry;
    }
}
