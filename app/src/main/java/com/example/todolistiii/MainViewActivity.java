package com.example.todolistiii;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MainViewActivity extends AndroidViewModel {

    private NotesDao dataBase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    List<Note> notes = new ArrayList<>();

    public MainViewActivity(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application).notesDao();
    }

    public LiveData<List<Note>> getNotes(){
        return dataBase.getNotes();
    }



    public void remove(Note note){

        Disposable disposable = dataBase.remove(note.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.d("MainViewActivity", note.getText());


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable { // обработчик ошибок

                    }
                });

        compositeDisposable.add(disposable);


    }




    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }



}
