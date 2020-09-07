package com.example.notesapp;

import com.google.firebase.firestore.Exclude;

public class note {

    String Title;
    String Desc;
    String documentId;
    public note(){
        //kandipa venum
    }
    public note(String title, String desc) {
        Title = title;
        Desc = desc;


    }
    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }


    }

