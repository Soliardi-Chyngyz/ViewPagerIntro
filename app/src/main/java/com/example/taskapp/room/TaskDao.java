package com.example.taskapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.taskapp.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Insert // (onConflict = OnConflictStrategy.IGNORE)
    void insert (Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM task")
    void nukeTable();
}
