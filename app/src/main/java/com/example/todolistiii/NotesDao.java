package com.example.todolistiii;

import static java.nio.file.attribute.AclEntryPermission.DELETE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NotesDao { // прописываем запросы для работы с БД

    @Insert(onConflict = OnConflictStrategy.REPLACE) //@Insert - означает что метод добавляет данные в БД. (onConflict = OnConflictStrategy.REPLACE) - при одинаковом id заметка перезаписывается
    void add(Note note); // возвращаемый тип Completable нужен для возможности подписки на метод, для отслеживания окончания его работы

    @Query("DELETE from notes WHERE id = :id" ) // удаляем строчку с указанным id
    void remove(int id);

    @Query("SELECT * FROM notes")
    List<Note> getNotes(); //возвращает объект Single




}
