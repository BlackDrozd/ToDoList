package com.android.todolist.presenters;

import com.android.todolist.StartView;
import com.android.todolist.common.Note;
import com.android.todolist.models.NoteModel;

import java.util.List;

public class NotesPresenter extends BasePresenter<StartView>{

    private static final String TAG = "NotesPresenter";

    private StartView mStartView;

    private final NoteModel mNoteModel;

    public NotesPresenter(NoteModel model){ mNoteModel = model;}

    public void attachView(StartView view){ mStartView = view; }

    public void detachView() { mStartView = null; }

    public void viewIsReady() { loadAllNotes();}

    private void loadAllNotes() {
        mNoteModel.loadNotes(new NoteModel.LoadNoteCallback() {
            @Override
            public void onLoad(List<Note> notes) {
                mStartView.showNotes(notes);
            }
        });
    }

    public void deleteAllNotes(){
        mNoteModel.deleteNotes();
    }

}
