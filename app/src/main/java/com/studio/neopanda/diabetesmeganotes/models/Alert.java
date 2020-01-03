package com.studio.neopanda.diabetesmeganotes.models;

public class Alert {
    public String name;//
    public String description;//
    public String type;//
    public String startMoment;//
    public String endMoment;//
    public String hourAlert;
    public String dataID;
    public String isActive;
    public int idEntry;//

    public Alert(String name, String description, String type, String startMoment, String endMoment, String hourAlert, String dataID, String isActive) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.startMoment = startMoment;
        this.endMoment = endMoment;
        this.hourAlert = hourAlert;
        this.dataID = dataID;
        this.isActive = isActive;
    }

    public String getDataID() {
        return dataID;
    }

    public void setDataID(String dataID) {
        this.dataID = dataID;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getHourAlert() {
        return hourAlert;
    }

    public void setHourAlert(String hourAlert) {
        this.hourAlert = hourAlert;
    }
}
