package com.example.esport;

import java.io.Serializable;

public class E_News implements Serializable {
    private String title;
    private String content;
    private String picture;

    public E_News(String title, String content, String pic) {
        this.title = title;
        this.content = content;
        this.picture = pic;
    }

    public E_News() {    }

    public E_News(E_News w) {
        this.title = w.getTitle();
        this.content = w.getContent();
        this.picture = w.getPicture();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}