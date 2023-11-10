package com.example.todolistiii;

import static java.nio.file.attribute.AclEntryPermission.DELETE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDao { // прописываем запросы для работы с БД

    @Insert(onConflict = OnConflictStrategy.REPLACE) //@Insert - означает что метод добавляет данные в БД. (onConflict = OnConflictStrategy.REPLACE) - при одинаковом id заметка перезаписывается
    void add(Note note);

    @Query("DELETE from notes WHERE id = :id" ) // удаляем строчку с указанным id
    void remove(int id);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes(); //возвращает объект LiveData. Нельзя указывать конкретную коллекцию, а только List




}
