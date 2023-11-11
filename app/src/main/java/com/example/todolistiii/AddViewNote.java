package com.example.todolistiii;

import static android.provider.Settings.System.getString;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
;



public class AddViewNote extends AndroidViewModel {

    DataBase dataBase;






    private MutableLiveData <Boolean> shouldCloseScreen = new MutableLiveData<>();



    private MutableLiveData <Boolean> toastShow = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable(); // нужно чтобы удалить подписку discribeOn если сами уходим с активности

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }

    public MutableLiveData<Boolean> getToastShow() {
        return toastShow;
    }



    public AddViewNote(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application);// получаем ссылку на базу данных
    }

    public void saveNote(Note note){

       Disposable disposable = saveNoteRx(note)
                .subscribeOn(Schedulers.io())// переключаем поток на фоновый для метода add
                .observeOn(AndroidSchedulers.mainThread()) // переключаем обратно на главный поток. Все что ниже будет выполняться в главном потоке
                .subscribe(new Action() {// с помощью этого метода подписываемся на add и отслеживаем окончание его работы добавляя колбек Action
                    @Override //
                    public void run() throws Throwable {
                        shouldCloseScreen.setValue(true);
                    }
                }, new Consumer<Throwable>() {//обработчик ошибок
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                        toastShow.setValue(true);

                    }
                });
       compositeDisposable.add(disposable);

    }

    private Completable saveNoteRx(Note note){ // создаем свой объект Single

        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
               // dataBase.notesDao().add(note);

                throw new Exception();
            }
        });




    }

    @Override
    protected void onCleared() { // метод жизненного цикла View модели
        super.onCleared();
        compositeDisposable.dispose(); //удаляем все подписки
    }
}
