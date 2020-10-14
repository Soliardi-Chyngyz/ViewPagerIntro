package com.example.taskapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.taskapp.Task;

// в массиве - таблицы (модельки) с которым Room будет работать
// version = 1 чтобы таблица не пересоздавалась, в случае изменении таблицы
// version = 1 сравнивает создаваемые таблицы, если схожи, то не сохраняет
// но можно менять эту функцию

// если нужно будет изменить модельку то Room крашнет, для фикса нужно
// лишь удалить приложение и перезапустить
@Database(entities = {Task.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
