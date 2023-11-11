package com.example.todolistiii;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MainViewActivity extends AndroidViewModel {

    NotesDao dataBase;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewActivity(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application).notesDao();
    }

    LiveData<List<Note>> getNotes(){
        return dataBase.getNotes();
    }

    public void remove(Note note){

        Disposable disposable = dataBase
                .remove(note.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.d("MainViewActivity",note.getText());

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
