package com.example.quicknotes;

public class Note {
    private String title;
    private String description;
    private String category;

    public Note(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }
}
