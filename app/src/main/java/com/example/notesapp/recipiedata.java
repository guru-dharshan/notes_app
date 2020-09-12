package com.example.notesapp;

public class recipiedata {
    String title,image,source;

    public recipiedata(String title, String image,String source) {
        this.title = title;
        this.image = image;
        this.source=source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
