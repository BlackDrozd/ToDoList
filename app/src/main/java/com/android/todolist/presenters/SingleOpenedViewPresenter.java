package com.android.todolist.presenters;

import android.content.ContentValues;

import androidx.annotation.NonNull;

import com.android.todolist.NewNoteActivity;
import com.android.todolist.R;
import com.android.todolist.common.Note;
import com.android.todolist.data.db.NoteTable;
import com.android.todolist.models.NoteModel;

public class SingleOpenedViewPresenter {

    private static final String TAG = "SingleOpenedViewPresent";

    private NewNoteActivity singleNoteView;

    private final NoteModel mNoteModel;

    public SingleOpenedViewPresenter(NoteModel model){ mNoteModel = model;}

    public void attachView(NewNoteActivity newNoteActivity){ singleNoteView = newNoteActivity; }

    public void detachView() { singleNoteView = null; }

    public void onAddNoteButtonClicked() {

        String title = singleNoteView.getNoteTitle();
        String text = singleNoteView.getNoteText();

        if(title.isEmpty() || text.isEmpty()){
            singleNoteView.showCreationError(R.string.error_note_is_empty);
        }
        else {

            ContentValues cv = packToContentValues(title, text);

            mNoteModel.addNote(cv, new NoteModel.CompleteCallback() {
                @Override
                public void onComplete() {
                    singleNoteView.showToast(R.string.note_added_toast);
                    singleNoteView.startMainActivity();
                }
            });
        }
    }

    private ContentValues packToContentValues(String title, String text) {
        ContentValues cv = new ContentValues(2);
        cv.put(NoteTable.COLUMN.TITLE, title);
        cv.put(NoteTable.COLUMN.NOTE_TEXT, text);
        return cv;
    }

    public void editNote(){
        Note note  = singleNoteView.getEditedNote();
        updateNote(note);
        singleNoteView.showToast(R.string.note_updated_toast);
        singleNoteView.startMainActivity();
    }

    public void deleteNote(@NonNull Long noteId){
        mNoteModel.deleteNote(noteId);
        singleNoteView.showToast(R.string.note_deleted_toast);
        singleNoteView.startMainActivity();
    }

    public void updateNote(@NonNull Note note){
        mNoteModel.updateNote(note);
    }
}
