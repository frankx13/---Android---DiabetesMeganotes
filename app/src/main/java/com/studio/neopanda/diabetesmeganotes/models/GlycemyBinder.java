package com.studio.neopanda.diabetesmeganotes.models;

public class GlycemyBinder {

    public int id;
    public String tableToStock;
    public String glycemy;
    public String date;
    public String extras;
    public String dataID;

    public GlycemyBinder(String glycemy, String date, String dataID) {
        this.glycemy = glycemy;
        this.date = date;
        this.dataID = dataID;
    }

    public GlycemyBinder(String glycemy, String date){
        this.glycemy = glycemy;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableToStock() {
        return tableToStock;
    }

    public void setTableToStock(String tableToStock) {
        this.tableToStock = tableToStock;
    }

    public String getGlycemy() {
        return glycemy;
    }

    public void setGlycemy(String glycemy) {
        this.glycemy = glycemy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getDataID() {
        return dataID;
    }

    public void setDataID(String dataID) {
        this.dataID = dataID;
    }
}