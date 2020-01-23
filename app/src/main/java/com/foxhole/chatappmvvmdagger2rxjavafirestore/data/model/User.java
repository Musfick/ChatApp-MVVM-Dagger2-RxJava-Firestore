package com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model;

public class User {

    private String email;
    private String displayName;
    private String image;
    private String status;
    private boolean online;

    public User() {

    }

    public User(String email, String displayName, String image, String status, boolean online) {
        this.email = email;
        this.displayName = displayName;
        this.image = image;
        this.status = status;
        this.online = online;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
