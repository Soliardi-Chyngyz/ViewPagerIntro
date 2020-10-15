package com.example.taskapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.taskapp.Task;

import java.util.List;

// @Dao - это аннотация (обязательно)
@Dao
public interface TaskDao {

    // * - это называется всё
    // @Query("Вытащи мне всё из табоицы task")
    @Query("SELECT * FROM task") // это синтаксис SQL
    List<Task> getAll();

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllLive();

    // сохранение в таблицу Таск
    @Insert // (onConflict = OnConflictStrategy.IGNORE) это в случае если есть такой же ключ (id)
    void insert (Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM task")
    void nukeTable();
}
