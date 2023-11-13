package com.example.todolistiii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewMain;
    FloatingActionButton floatingActionButton;

    EditText inputSearch;

    NotesAdapter notesAdapter;

    MainViewActivity mainViewActivity;

    List <Note> sourceList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewActivity = new ViewModelProvider(this).get(MainViewActivity.class);
        initView();
        notesAdapter = new NotesAdapter();
        recyclerViewMain.setAdapter(notesAdapter); // определяем какой тип адаптера применять

        mainViewActivity.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                sourceList = notes;
                notesAdapter.setNotes(notes);

            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) { // удаление с помощью свайпа
            @Override // перемещение
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override // удаление
            public void onSwiped(
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    int direction) {
                int position = viewHolder.getAdapterPosition(); // получаем позицию по которой был произведен свайп
                Note note = notesAdapter.getNotes().get(position); //получаем заметку из
                mainViewActivity.remove(note);



            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerViewMain); // прикрепляем наш объект к recyclerViewNotes






        floatingActionButton.setOnClickListener(new View.OnClickListener() { // слушатель на кнопку
            @Override
            public void onClick(View view) {
                Intent intent = AddNote.newIntent(MainActivity.this);// создаем интент
                startActivity(intent);// запускаем интент
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filterSearch(editable.toString());

            }
        });



    }

    public void filterSearch(String text){

        List <Note> filterList = new ArrayList<>();

        for(Note note: sourceList){

            if(note.getText().toLowerCase().contains(text)){
                filterList.add(note);
            }

        }

        notesAdapter.setNotesFilter(filterList);

    }








    private void initView(){ // инициализируем ссылки на объекты
        recyclerViewMain = findViewById(R.id.recyclerViewMain);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        inputSearch = findViewById(R.id.inputSearch);


    }

}