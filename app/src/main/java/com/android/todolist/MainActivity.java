package com.android.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.todolist.common.Note;
import com.android.todolist.common.NoteAdapter;
import com.android.todolist.common.OpenNoteMode;
import com.android.todolist.data.db.DbHelper;
import com.android.todolist.models.NoteModel;
import com.android.todolist.presenters.NotesPresenter;
import com.android.todolist.presenters.SingleOpenedViewPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        StartView,
        NoteAdapter.OnNoteListener{

    private static final String TAG = "MainActivity";

    FloatingActionButton openNewNoteButton;
    Toolbar toolbar;
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
        Note note = noteAdapter.getData().get(position);
        startActivityEditMode(note);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_item:
                Toast.makeText(this, "The page in develop yet =^__^=", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about_item:
                Toast.makeText(this, "The page about in develop yet =^__^=", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.settings_item:
                Toast.makeText(this, "The page settings in develop yet =^__^=", Toast.LENGTH_SHORT).show();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

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

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DbHelper(this);
        noteModel = new NoteModel(dbHelper);
        presenter = new NotesPresenter(noteModel);
        presenter.attachView(this);


        singleOpenedViewPresenter = new SingleOpenedViewPresenter(noteModel);

        openNewNoteButton = findViewById(R.id.open_new_note);

        openNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewNoteActivity.class);
                intent.setAction(OpenNoteMode.CREATE_NEW_NOTE.toString());
                intent.putExtra("noteId", "");
                mContext.startActivity(intent);
            }
        });

        layoutManager = new LinearLayoutManager(this);
        noteAdapter = new NoteAdapter(this);
        RecyclerView noteRecyclerView = findViewById(R.id.note_list);
        noteRecyclerView.setLayoutManager(layoutManager);
        noteRecyclerView.setAdapter(noteAdapter);
    }

    public void startActivityEditMode(Note note){
        Intent intent = new Intent(this, NewNoteActivity.class);
        intent.setAction(OpenNoteMode.EDIT_NOTE.toString());
        intent.putExtra("noteId", note.getId());
        this.startActivity(intent);
    }
}
