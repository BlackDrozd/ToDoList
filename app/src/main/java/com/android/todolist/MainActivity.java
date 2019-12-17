package com.android.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.todolist.common.Note;
import com.android.todolist.common.NoteAdapter;
import com.android.todolist.data.db.DbHelper;
import com.android.todolist.models.NoteModel;
import com.android.todolist.presenters.NotesPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends Activity implements NoteAdapter.OnNoteListener{

    private static final String TAG = "MainActivity";

    ImageButton deleteButton;
    FloatingActionButton openNewNoteButton;
    LinearLayoutManager layoutManager;
    NoteAdapter noteAdapter;
    private NotesPresenter presenter;
    DbHelper dbHelper;
    NoteModel noteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbHelper = new DbHelper(this);
        noteModel = new NoteModel(dbHelper);
        presenter = new NotesPresenter(noteModel);
        presenter.attachView(this);
        presenter.viewIsReady();
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
        noteAdapter = new NoteAdapter(this);
        RecyclerView noteRecyclerView = findViewById(R.id.note_list);
        noteRecyclerView.setLayoutManager(layoutManager);
        noteRecyclerView.setAdapter(noteAdapter);
    }

    public void showNotes(List<Note> notes){
        noteAdapter.setData(notes);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        dbHelper.close();
    }

    @Override
    public void onNoteClick(int position) {

        Log.d(TAG, "pos="+position);
        //mNotes.get(position)
       // Intent intent = new Intent(this, NewNoteActivity.class);
       // intent.putExtra("selectedNote",mNotes.get(position));
       // startActivity(intent);
    }
}
