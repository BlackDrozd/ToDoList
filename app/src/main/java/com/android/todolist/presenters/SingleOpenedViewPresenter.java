package com.android.todolist.presenters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.todolist.MainActivity;
import com.android.todolist.NewNoteActivity;
import com.android.todolist.R;
import com.android.todolist.common.OpenNoteMode;
import com.android.todolist.data.NoteData;
import com.android.todolist.data.db.NoteTable;
import com.android.todolist.models.NoteModel;

public class SingleOpenedViewPresenter {

    private static final String TAG = "SingleOpenedViewPresent";

    private NewNoteActivity singleNoteView;

    private NoteData noteData;

    private final NoteModel mNoteModel;


    public SingleOpenedViewPresenter(NoteModel model){ mNoteModel = model;}

    public void attachView(NewNoteActivity newNoteActivity){ singleNoteView = newNoteActivity; }

    public void detachView() { singleNoteView = null; }

    public void viewIsReady(@NonNull String viewAction) {
        Log.d(TAG, "action="+viewAction);
    }

    public void add(@NonNull final Context context) {
        noteData  = singleNoteView.getNoteData();
        ContentValues cv = new ContentValues(2);
        cv.put(NoteTable.COLUMN.TITLE, noteData.getTitle());
        cv.put(NoteTable.COLUMN.NOTE_TEXT, noteData.getText());
        final Intent intent = new Intent(context, MainActivity.class);
        mNoteModel.addNote(cv, new NoteModel.CompleteCallback() {
            @Override
            public void onComplete() {
                singleNoteView.showToast(R.string.note_added_toast);
                context.startActivity(intent);
            }
        });
    }

    public void openNewNote(@NonNull  Context context) {
        Intent intent = new Intent(context, NewNoteActivity.class);
        intent.setAction(OpenNoteMode.CREATE_NEW_NOTE.toString());
        intent.putExtra("noteId", "");
        context.startActivity(intent);
    }

}
