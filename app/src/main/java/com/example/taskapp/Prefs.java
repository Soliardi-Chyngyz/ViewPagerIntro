package com.example.taskapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    // SharedPreferences для работы с настройками
    private SharedPreferences preferences;
    public static Prefs instance;

    public Prefs (Context context) {
        instance = this;
        // получаем настройки приложения с названием settings
        // если он есть, то возвращает
        // если нет, то создает
        // MODE_PRIVATE - доступен только для нашего приложения
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    // операция хранит состояние
    // .edit() - для редактирования
    // .putBoolean("showState", true) - типо мы посмотрели? true - да, значит
    // код вложеный в теле под условием этого оператора не будет вызыватся!!!
    // .apply()/.commit сохраняем
    public void saveShowState() {
        preferences.edit().putBoolean("showState", true).apply();
    }

    public boolean getShowState () {
        // на случай если по ключу showState ничего ненайдет, то он по умолчанию
        // вернет false
        // default значение 46:41
        // а если вернет false то он обработает код под этим условием
        return preferences.getBoolean("showState", false);
    }
}
