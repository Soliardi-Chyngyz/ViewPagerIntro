package com.example.taskapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.taskapp.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
