package com.example.taskapp;

import android.app.Application;

import androidx.room.Room;

import com.example.taskapp.room.AppDataBase;

// нужен для того чтобы к Room можно было образаться с любого класса
// в Манифесте нужно указать
public class App extends Application {


    public static App instance;
    private AppDataBase appDataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // этот App и его методы в дополнительном потоке работают но ->
        appDataBase = Room.databaseBuilder(this, AppDataBase.class, "database")
                // -> здесь разрешили потому что Room работает с небольшими данными
                .allowMainThreadQueries() // разрешение на главном потоке
                .build();
        // создали этот класс единожды (экземпляр)
        // и теперь можем обращаться Prefs.instance...
        new Prefs(this);
    }

    public static App getInstance() {
        return instance;
    }

    public AppDataBase getAppDataBase() {
        return appDataBase;
    }
}
