package com.studio.neopanda.diabetesmeganotes.models;

public class CurrentUser {
    String username;
    String userImg;
    String id;

    public CurrentUser(String username, String userImg) {
        this.username = username;
        this.userImg = userImg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
