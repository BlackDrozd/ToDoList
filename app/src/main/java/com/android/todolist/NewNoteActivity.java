package com.android.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.todolist.common.NoteAdapter;
import com.android.todolist.data.NoteData;
import com.android.todolist.data.db.DbHelper;
import com.android.todolist.fragments.CreateNewNoteFragment;
import com.android.todolist.models.NoteModel;
import com.android.todolist.presenters.SingleOpenedViewPresenter;

public class NewNoteActivity extends AppCompatActivity {

    //region declaration of fields
    private static final String TAG = "NewNoteActivity";

    private NoteAdapter noteAdapter;

    private SingleOpenedViewPresenter presenter;

    private Context mContext;
    DbHelper dbHelper;
    NoteModel noteModel;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        mContext = getApplicationContext();
        init();
    }

    private void init() {
        Intent intent = getIntent();
        dbHelper = new DbHelper(mContext);
        noteModel = new NoteModel(dbHelper);
        presenter = new SingleOpenedViewPresenter(noteModel);
        presenter.viewIsReady(intent, this);

    }


    public NoteData getNoteData() {

        CreateNewNoteFragment fragment;
        fragment = (CreateNewNoteFragment)  getSupportFragmentManager().findFragmentById(R.id.root_layout);
        String title = ((EditText)fragment.getView().findViewById(R.id.note_title)).getText().toString();
        String text = ((EditText)fragment.getView().findViewById(R.id.note_text)).getText().toString();
        NoteData noteData = new NoteData();
        noteData.setTitle(title);
        noteData.setText(text);
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
