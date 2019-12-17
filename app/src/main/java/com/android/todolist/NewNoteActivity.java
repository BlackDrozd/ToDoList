package com.android.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.todolist.common.NoteAdapter;
import com.android.todolist.data.NoteData;
import com.android.todolist.data.db.DbHelper;
import com.android.todolist.models.NoteModel;
import com.android.todolist.presenters.SingleOpenedViewPresenter;

public class NewNoteActivity extends Activity {

    private static final String TAG = "NewNoteActivity";

    private NoteAdapter noteAdapter;

    private EditText editTextTitle;
    private EditText editTextNoteDesc;

    private SingleOpenedViewPresenter presenter;

    DbHelper dbHelper;

    NoteModel noteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        init();
    }

    private void init() {
        editTextTitle = findViewById(R.id.note_title);
        editTextNoteDesc = findViewById(R.id.note_text);

        dbHelper = new DbHelper(this);
        noteModel = new NoteModel(dbHelper);
        presenter = new SingleOpenedViewPresenter(noteModel);
        presenter.attachView(this);
        presenter.viewIsReady();

        findViewById(R.id.add_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.add();
            }
        });

      //  noteAdapter = new NoteAdapter(this);

    }


    public NoteData getNoteData() {
        NoteData noteData = new NoteData();
        noteData.setTitle(editTextTitle.getText().toString());
        noteData.setText(editTextNoteDesc.getText().toString());
        return noteData;
    }

    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        dbHelper.close();
    }



}
