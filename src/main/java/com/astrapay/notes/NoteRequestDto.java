package com.astrapay.notes.dto;

public class NoteRequestDto {
    private String content;

    public NoteRequestDto() {}

    public NoteRequestDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
