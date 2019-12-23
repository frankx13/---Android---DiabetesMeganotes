package com.studio.neopanda.diabetesmeganotes.models;

public class User {
    String username;
    int userImg;

    public User(String username, int userImg) {
        this.username = username;
        this.userImg = userImg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserImg() {
        return userImg;
    }

    public void setUserImg(int userImg) {
        this.userImg = userImg;
    }
}
