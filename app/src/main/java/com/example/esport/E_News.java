package com.example.esport;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class E_News implements Serializable {
    private String id;
    private String title;
    private String content;
    private String picture;
    private Date time;
    private String PostKey;

    public E_News(String title, String content, String pic, Date time) {
        this.title = title;
        this.content = content;
        this.picture = pic;
        this.time = time;
    }

    public E_News() {    }

    public E_News(E_News w) {
        this.id = w.getId();
        this.title = w.getTitle();
        this.content = w.getContent();
        this.picture = w.getPicture();
        this.time = w.getTime();
    }

    public String getPostKey() {
        return PostKey;
    }

    public void setPostKey(String postKey) {
        this.PostKey = postKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTimeString() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String strDate = dateFormat.format(time);
        return strDate;
    }

    public Date getTime(){
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}