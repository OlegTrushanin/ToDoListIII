package com.example.todolistiii;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;



public class AddViewNote extends AndroidViewModel {

    DataBase dataBase;



    private MutableLiveData <Boolean> shouldCloseScreen = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable(); // нужно чтобы удалить подписку discribeOn если сами уходим с активности

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }


    public AddViewNote(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application);// получаем ссылку на базу данных
    }

    public void saveNote(Note note){

       Disposable disposable = dataBase.notesDao().add(note)
                .subscribeOn(Schedulers.io())// переключаем поток на фоновый для метода add
                .observeOn(AndroidSchedulers.mainThread()) // переключаем обратно на главный поток. Все что ниже будет выполняться в главном потоке
                .subscribe(new Action() {// с помощью этого метода подписываемся на add и отслеживаем окончание его работы добавляя колбек Action
                    @Override //
                    public void run() throws Throwable {
                        shouldCloseScreen.setValue(true);
                    }
                });
       compositeDisposable.add(disposable);

    }

    @Override
    protected void onCleared() { // метод жизненного цикла View модели
        super.onCleared();
        compositeDisposable.dispose(); //удаляем все подписки
    }
}
