package com.example.todolistiii;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "notes") // делает из класса таблицу
public class Note {


    @PrimaryKey(autoGenerate = true) // @PrimaryKey не допускает создание заметок с одинаковыми id. (autoGenerate = true) - автоматическая генерация id
    private int id;
    private String text;
    private int priority;

    public Note(int id, String text, int priority) {
        this.id = id;
        this.text = text;
        this.priority = priority;
    }

    @Ignore // чтобы не было конфликта между конструкторами
    public Note(String text, int priority) {
        this(0,text,priority);
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPriority() {
        return priority;
    }
}
