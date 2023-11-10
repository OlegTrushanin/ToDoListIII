package com.example.todolistiii;


import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities ={Note.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    // паттерн проектирования синглтон
    public static DataBase instance = null;

    public static final String NAME_DB = "notes.db";

    public static DataBase getInstance(Application application){
        if(instance == null){
            instance = Room.databaseBuilder(
                            application,
                            DataBase.class, // сам класс в котором создаем базу
                            NAME_DB // имя базы
                    ) // .allowMainThreadQueries() помогает обойти ограничения на запросы к базе данных в Мэйн активити
                    .build();

        }

        return instance;
    }

    public abstract NotesDao notesDao();







}
