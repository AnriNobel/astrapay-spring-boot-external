package com.astrapay.notes.service.model;

public class NoteModel {
    private String id;
    private String content;

    public NoteModel() {}

    public NoteModel(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
