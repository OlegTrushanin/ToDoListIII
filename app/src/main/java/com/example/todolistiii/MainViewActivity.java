package com.example.todolistiii;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewActivity extends AndroidViewModel {

    DataBase dataBase;
    public MainViewActivity(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application);
    }

    LiveData<List<Note>> getNotes(){
        return dataBase.notesDao().getNotes();
    }

    public void remove(Note note){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                dataBase.notesDao().remove(note.getId());

            }
        });
        thread.start();


    }
}
