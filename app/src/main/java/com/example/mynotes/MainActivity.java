package com.example.mynotes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mynotes.model.ListNote;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListNote notes = ListNote.getInstance();
        if (notes.size() == 0) {
            notes.addNote("Notes 1", "portray 1");
            notes.addNote("Notes 2", "portray 2");
            notes.addNote("Notes 3", "portray 3");
            notes.addNote("Notes 4", "portray 4");
            notes.addNote("Notes 5", "portray 5");
        }
        setContentView(R.layout.activity_main);
    }
}