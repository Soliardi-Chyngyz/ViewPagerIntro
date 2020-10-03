package com.example.taskapp;

import android.app.Application;

import androidx.room.Room;

import com.example.taskapp.room.AppDataBase;

public class App extends Application {

    public static App instance;
    private AppDataBase appDataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appDataBase = Room.databaseBuilder(this, AppDataBase.class, "database")
                .allowMainThreadQueries() // разрешение на главном потоке
                .build();
        new Prefs(this);
    }

    public static App getInstance() {
        return instance;
    }

    public AppDataBase getAppDataBase() {
        return appDataBase;
    }
}
