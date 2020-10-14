package com.example.taskapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// определили что Task будет хранится в базе дданных
@Entity
public class Task implements Serializable {

    // автогенерируем id уникального ключа
    // он автомат.заполняет таски по id
    // каждый раз когда создаем данные
    // PrimaryKey - определяем что он у нас ключ уник.ый
    @PrimaryKey(autoGenerate = true)
    private long id; // id для базы данных, должен быть уникальным
    private String title;
    private String time;

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return time;
    }

    public String getTime() {
        return time;
    }

    public Task(String title, String time) {
        this.title = title;
        this.time = time;
    }
}
