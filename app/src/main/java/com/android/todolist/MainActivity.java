package com.android.todolist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.todolist.common.Note;
import com.android.todolist.common.NoteAdapter;
import com.android.todolist.data.db.DbHelper;
import com.android.todolist.models.NoteModel;
import com.android.todolist.presenters.NotesPresenter;
import com.android.todolist.presenters.SingleOpenedViewPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends Activity implements NoteAdapter.OnNoteListener{

    private static final String TAG = "MainActivity";

    FloatingActionButton openNewNoteButton;
    LinearLayoutManager layoutManager;
    NoteAdapter noteAdapter;
    private NotesPresenter presenter;
    private SingleOpenedViewPresenter singleOpenedViewPresenter;
    private Context mContext;
    DbHelper dbHelper;
    NoteModel noteModel;

    public void showNotes(List<Note> notes){
        noteAdapter.setData(notes);
    }

    @Override
    public void onNoteClick(int position) {
        presenter.openNote(position, noteAdapter.getData(), getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        mContext = getApplicationContext();
        init();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        presenter.viewIsReady();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        presenter.detachView();
        singleOpenedViewPresenter.detachView();
        dbHelper.close();
    }

    private void init() {

        dbHelper = new DbHelper(this);
        noteModel = new NoteModel(dbHelper);
        presenter = new NotesPresenter(noteModel);
        presenter.attachView(this);


        singleOpenedViewPresenter = new SingleOpenedViewPresenter(noteModel);

        openNewNoteButton = findViewById(R.id.open_new_note);

        openNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleOpenedViewPresenter.openNewNote(mContext);
            }
        });

        layoutManager = new LinearLayoutManager(this);
        noteAdapter = new NoteAdapter(this);
        RecyclerView noteRecyclerView = findViewById(R.id.note_list);
        noteRecyclerView.setLayoutManager(layoutManager);
        noteRecyclerView.setAdapter(noteAdapter);
    }
}
