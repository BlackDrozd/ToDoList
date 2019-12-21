package com.android.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.todolist.common.Note;
import com.android.todolist.common.NoteAdapter;
import com.android.todolist.common.OpenNoteMode;
import com.android.todolist.data.NoteData;
import com.android.todolist.data.db.DbHelper;
import com.android.todolist.fragments.CreateNewNoteFragment;
import com.android.todolist.fragments.EditNoteFragment;
import com.android.todolist.models.NoteModel;
import com.android.todolist.presenters.SingleOpenedViewPresenter;

public class NewNoteActivity extends AppCompatActivity {

    //region declaration of fields
    private static final String TAG = "NewNoteActivity";

    private NoteAdapter noteAdapter;

    private SingleOpenedViewPresenter presenter;

    private CreateNewNoteFragment mCreateNewNoteFragment;
    private EditNoteFragment mEditNoteFragment;

    private Context mContext;
    DbHelper dbHelper;
    NoteModel noteModel;
    Intent intent;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        mContext = getApplicationContext();
        init();
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

    public Note getEditedNote(){
        EditNoteFragment fragment;
        fragment = (EditNoteFragment) getSupportFragmentManager().findFragmentById(R.id.root_layout);
        String title = ((EditText)fragment.getView().findViewById(R.id.note_title)).getText().toString();
        String text = ((EditText)fragment.getView().findViewById(R.id.note_text)).getText().toString();
        String id = ((TextView)fragment.getView().findViewById(R.id.note_id)).getText().toString();
        Note note = new Note();
        note.setTitle(title);
        note.setText(text);
        note.setId(Long.parseLong(id));
        return note;
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

    private void init() {
        intent = getIntent();
        dbHelper = new DbHelper(mContext);
        noteModel = new NoteModel(dbHelper);
        presenter = new SingleOpenedViewPresenter(noteModel);
        chooseFragment(intent);
    }

    private void chooseFragment(@NonNull  Intent intent) {

        String viewAction = intent.getAction();

        if(isViewActionMatched(viewAction, OpenNoteMode.CREATE_NEW_NOTE.getMode())){
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.root_layout, mCreateNewNoteFragment.newInstance())
                    .commit();
        }
        else if(isViewActionMatched(viewAction, OpenNoteMode.EDIT_NOTE.getMode())){
            Long noteId = intent.getExtras().getLong("noteId");
            Note note = noteModel.loadNoteById(noteId);
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.root_layout, mEditNoteFragment.newInstance(note))
                    .commit();

        }
    }

    private boolean isViewActionMatched(String action1, String action2) {
        if (action1.equals(action2)) return true;
        return false;
    }

}
