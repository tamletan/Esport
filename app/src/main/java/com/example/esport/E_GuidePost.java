package com.example.esport;

public class E_GuidePost {
    private String id;
    private String name;
    private String content;

    public E_GuidePost(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public E_GuidePost() {    }

    public E_GuidePost(E_GuidePost w) {
        this.id = w.getId();
        this.name = w.getName();
        this.content = w.getContent();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
