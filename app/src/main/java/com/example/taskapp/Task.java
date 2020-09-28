package com.example.taskapp;

import java.io.Serializable;

public class Task implements Serializable {
    private String title;
    private String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return time;
    }

    public void setCreatedAt(String time) {
        this.time = time;
    }

    public Task(String title, String time) {
        this.title = title;
        this.time = time;
    }
}
