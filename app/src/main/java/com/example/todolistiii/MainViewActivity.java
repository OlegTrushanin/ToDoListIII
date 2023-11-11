package com.example.todolistiii;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MainViewActivity extends AndroidViewModel {

    private NotesDao dataBase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData <List<Note>> notes = new MutableLiveData<>();

    public MainViewActivity(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application).notesDao();
    }

    public LiveData<List<Note>> getNotes(){
        return notes;
    }

    public void refreshList(){ // обновление листа с заметками

        Disposable disposable = dataBase.getNotes() //получаем заметки из БД
                .subscribeOn(Schedulers.io()) // верхнее действие выполняем в фоновом потоке
                .observeOn(AndroidSchedulers.mainThread())// переключаемся на основной
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notesDromDB) throws Throwable {
                        notes.setValue(notesDromDB); // передаем полученные данные в notes
                    }
                });

        compositeDisposable.add(disposable);

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
                        refreshList(); // обновляем данные

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
