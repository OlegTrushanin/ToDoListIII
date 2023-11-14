package com.example.todolistiii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {

    EditText addNoteEditText;
    RadioButton low_rg;
    RadioButton medium_rg;
    RadioButton high_rg;

    Button saveNoteButton;

    AddViewNote addViewNote;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initView();
        addViewNote = new ViewModelProvider(this).get(AddViewNote.class);


        saveNoteButton.setOnClickListener(new View.OnClickListener() { // слушатель на кнопку, сохраняем внесенные данные в БД
            @Override
            public void onClick(View view) {
                save();
            }
        });

        addViewNote.getShouldCloseScreen().observe(this, new Observer<Boolean>() { // закрываем текущую активити
            @Override
            public void onChanged(Boolean shouldClose) {
                finish();
            }
        });

        addViewNote.getToastShow().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(
                        AddNote.this,
                        "Отсутствует подключение к интернету",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });




    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,AddNote.class);
        return intent;
    }

    private void initView(){

         addNoteEditText = findViewById(R.id.addNoteEditText);
         low_rg = findViewById(R.id.low_rg);
         medium_rg = findViewById(R.id.medium_rg);
         high_rg = findViewById(R.id.high_rg);
         saveNoteButton = findViewById(R.id.saveNoteButton);

    }

    private void save(){

        String text = addNoteEditText.getText().toString().trim();
        if(text.isEmpty()){// ?Проверка на заполненность

            Toast.makeText(
                    AddNote.this,
                    getString(R.string.error_string_empty),
                    Toast.LENGTH_SHORT
            ).show();

        }else {
            int priority = getPriority();
            Note note = new Note(text, priority);
            addViewNote.saveNote(note);
        }
    }

    private int getPriority(){

        int priority = 0;

        if(medium_rg.isChecked()){
            priority =1;
        } else if(high_rg.isChecked()){
            priority =2;
        }

        return priority;
    }

}

