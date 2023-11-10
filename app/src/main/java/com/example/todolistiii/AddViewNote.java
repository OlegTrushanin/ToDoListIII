package com.example.todolistiii;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AddViewNote extends AndroidViewModel {

    DataBase dataBase;



    private MutableLiveData <Boolean> shouldCloseScreen = new MutableLiveData<>();

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }


    public AddViewNote(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application);// получаем ссылку на базу данных
    }

    public void saveNote(Note note){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                dataBase.notesDao().add(note); // добавляем запись в БД
                shouldCloseScreen.postValue(true); // закрываем данную активность

            }
        });
        thread.start();
    }


}
