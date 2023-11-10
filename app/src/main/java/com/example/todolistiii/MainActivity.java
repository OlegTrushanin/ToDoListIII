package com.example.todolistiii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewMain;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        floatingActionButton.setOnClickListener(new View.OnClickListener() { // слушатель на кнопку
            @Override
            public void onClick(View view) {
                Intent intent = AddNote.newIntent(MainActivity.this);// создаем интент
                startActivity(intent);// запускаем интент
            }
        });



    }


    private void initView(){ // инициализируем ссылки на объекты
        recyclerViewMain = findViewById(R.id.recyclerViewMain);
        floatingActionButton = findViewById(R.id.floatingActionButton);


    }
}