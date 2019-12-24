package com.android.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.todolist.common.Note;
import com.android.todolist.common.NoteAdapter;
import com.android.todolist.common.OpenNoteMode;
import com.android.todolist.data.NoteData;
import com.android.todolist.data.db.DbHelper;
import com.android.todolist.fragments.CreateNewNoteFragment;
import com.android.todolist.fragments.EditNoteFragment;
import com.android.todolist.models.NoteModel;
import com.android.todolist.presenters.SingleOpenedViewPresenter;
import com.android.todolist.views.BaseActivity;

public class NewNoteActivity extends BaseActivity implements OpenedNoteView{

    //region declaration of fields
    private static final String TAG = "NewNoteActivity";

    private NoteAdapter noteAdapter;

    private SingleOpenedViewPresenter presenter;

    private CreateNewNoteFragment mCreateNewNoteFragment;
    private EditNoteFragment mEditNoteFragment;

    private Fragment mFragment;

    private Context mContext;
    DbHelper dbHelper;
    NoteModel noteModel;
    Intent intent;
    String mViewAction;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        mContext = getApplicationContext();
        mViewAction = getIntent().getAction();
        init();
        Log.d(TAG,getSupportFragmentManager().getFragments().toString() );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(isViewActionMatched(mViewAction, OpenNoteMode.CREATE_NEW_NOTE.getMode())){
            inflater.inflate(R.menu.menu_create_note, menu);
        }
        else{
            inflater.inflate(R.menu.menu_edit_note, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.edit_note:
                presenter.editNote();
                return true;
            case R.id.delete_note:
                Long noteId = intent.getExtras().getLong("noteId");
                presenter.deleteNote(noteId);
                return true;
            case R.id.add_note:
                presenter.onAddNoteButtonClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public NoteData getNoteData() {
        String title = getNoteTitle();
        String text = getNoteText();
        NoteData noteData = new NoteData();
        noteData.setTitle(title);
        noteData.setText(text);
        return noteData;

    }

    public String getNoteTitle(){
        CreateNewNoteFragment mFragment = (CreateNewNoteFragment)getSupportFragmentManager().findFragmentById(R.id.root_layout);
        return ((EditText)mFragment.getView().findViewById(R.id.note_title)).getText().toString();
    }

    public String getNoteText(){
        CreateNewNoteFragment mFragment = (CreateNewNoteFragment)getSupportFragmentManager().findFragmentById(R.id.root_layout);
        return ((EditText)mFragment.getView().findViewById(R.id.note_text)).getText().toString();
    }

    @Override
    public void showCreationError(int resId) {
        showToast(resId);
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

    public void startMainActivity(){
        final Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
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
        presenter.attachView(this);
        chooseFragment(intent);
        mFragment = this.getSupportFragmentManager().findFragmentById(R.id.root_layout);
    }

    private void chooseFragment(@NonNull  Intent intent) {

        if(isViewActionMatched(mViewAction, OpenNoteMode.CREATE_NEW_NOTE.getMode())){
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.root_layout, mCreateNewNoteFragment.newInstance())
                    .commit();
        }
        else if(isViewActionMatched(mViewAction, OpenNoteMode.EDIT_NOTE.getMode())){
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
