package com.android.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.todolist.common.Note;
import com.android.todolist.common.NoteAdapter;
import com.android.todolist.data.db.DbHelper;
import com.android.todolist.models.NoteModel;
import com.android.todolist.presenters.NotesPresenter;

import java.util.List;

public class MainActivity extends Activity {

    Button openNewNoteButton;
    LinearLayoutManager layoutManager;
    NoteAdapter noteAdapter;
    private NotesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        init();
    }

    private void init() {
        openNewNoteButton = findViewById(R.id.open_new_note);

        openNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), NewNoteActivity.class);
                startActivity(intent);

            }
        });

        layoutManager = new LinearLayoutManager(this);
        noteAdapter = new NoteAdapter();
        RecyclerView noteRecyclerView = findViewById(R.id.note_list);
        noteRecyclerView.setLayoutManager(layoutManager);
        noteRecyclerView.setAdapter(noteAdapter);

        DbHelper dbHelper = new DbHelper(this);
        NoteModel noteModel = new NoteModel(dbHelper);
        presenter = new NotesPresenter(noteModel);
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    public void showNotes(List<Note> notes){
        noteAdapter.setData(notes);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
