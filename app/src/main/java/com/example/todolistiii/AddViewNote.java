package com.example.todolistiii;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

        dataBase.notesDao().add(note)
                .subscribeOn(Schedulers.io())// переключаем поток на фоновый для метода add
                .observeOn(AndroidSchedulers.mainThread()) // переключаем обратно на главный поток. Все что ниже будет выполняться в главном потоке
                .subscribe(new Action() {// с помощью этого метода подписываемся на add и отслеживаем окончание его работы добавляя колбек Action
                    @Override //
                    public void run() throws Throwable {
                        shouldCloseScreen.setValue(true);
                    }
                });

    }


}
