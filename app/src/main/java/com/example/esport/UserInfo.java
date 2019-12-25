package com.example.esport;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String id;
    private String email;
    private String role;
    private String username;
    private String avatar;

    public UserInfo(String email, String role, String username, String avatar) {
        this.email = email;
        this.role = role;
        this.username = username;
        this.avatar = avatar;
    }

    public UserInfo() {
    }

    public UserInfo(UserInfo u) {
        this.email = u.getEmail();
        this.role = u.getRole();
        this.username = u.getUsername();
        this.avatar = u.getAvatar();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
