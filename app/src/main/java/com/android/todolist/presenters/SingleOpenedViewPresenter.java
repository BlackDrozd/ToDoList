package com.android.todolist.presenters;

import android.content.ContentValues;

import com.android.todolist.NewNoteActivity;
import com.android.todolist.R;
import com.android.todolist.data.NoteData;
import com.android.todolist.data.db.NoteTable;
import com.android.todolist.models.NoteModel;

public class SingleOpenedViewPresenter {

    private static final String TAG = "SingleOpenedViewPresent";

    private NewNoteActivity singleNoteView;

    private final NoteModel mNoteModel;

    public SingleOpenedViewPresenter(NoteModel model){ mNoteModel = model;}

    public void attachView(NewNoteActivity newNoteActivity){ singleNoteView = newNoteActivity; }

    public void detachView() { singleNoteView = null; }

    public void viewIsReady() {
    }

    public void add() {
        NoteData noteData = singleNoteView.getNoteData();
        ContentValues cv = new ContentValues(2);
        cv.put(NoteTable.COLUMN.TITLE, noteData.getTitle());
        cv.put(NoteTable.COLUMN.NOTE_TEXT, noteData.getText());
        mNoteModel.addNote(cv, new NoteModel.CompleteCallback() {
            @Override
            public void onComplete() {
                singleNoteView.showToast(R.string.note_added_toast);
            }
        });
    }
}
