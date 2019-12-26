package com.example.esport;

import com.google.firebase.database.ServerValue;

public class Comment {
    private String content;
    private String uid;
    private String uname;
    private String cid;

    private String pid;
    private Object timestamp;


    public Comment() {
    }

    public Comment(String content, String uid, String uname, String pid) {
        this.content = content;
        this.uid = uid;
        this.uname = uname;
        this.pid = pid;
        this.timestamp = ServerValue.TIMESTAMP;

    }

    public Comment(String content, String uid, String uname,String pid, Object timestamp) {
        this.content = content;
        this.uid = uid;
        this.uname = uname;
        this.pid = pid;
        this.timestamp = timestamp;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
