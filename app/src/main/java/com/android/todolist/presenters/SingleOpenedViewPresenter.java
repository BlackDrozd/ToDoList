package com.android.todolist.presenters;

import androidx.annotation.NonNull;

import com.android.todolist.OpenedNoteView;
import com.android.todolist.R;
import com.android.todolist.common.Note;
import com.android.todolist.models.NoteModel;

public class SingleOpenedViewPresenter extends BasePresenter<OpenedNoteView> {

    private static final String TAG = "SingleOpenedViewPresent";
    
    private OpenedNoteView mOpenedNoteView;

    private final NoteModel mNoteModel;

    public SingleOpenedViewPresenter(NoteModel model){ mNoteModel = model;}

    public void attachView(OpenedNoteView view){ mOpenedNoteView = view; }

    public void detachView() { mOpenedNoteView = null; }

    public void onAddNoteButtonClicked() {

        String title = mOpenedNoteView.getNoteTitle();
        String text = mOpenedNoteView.getNoteText();

        if(title.isEmpty() || text.isEmpty()){
            mOpenedNoteView.showCreationError(R.string.error_note_is_empty);
        }
        else {

            Note note = new Note(title, text);

            mNoteModel.addNote(note, new NoteModel.CompleteCallback() {
                @Override
                public void onComplete() {
                    mOpenedNoteView.showToast(R.string.note_added_toast);
                    mOpenedNoteView.startMainActivity();
                }
            });
        }
    }

    public void editNote(){
        Note note  = mOpenedNoteView.getEditedNote();
        updateNote(note);
        mOpenedNoteView.showToast(R.string.note_updated_toast);
        mOpenedNoteView.startMainActivity();
    }

    public void deleteNote(@NonNull Long noteId){
        mNoteModel.deleteNote(noteId);
        mOpenedNoteView.showToast(R.string.note_deleted_toast);
        mOpenedNoteView.startMainActivity();
    }

    public void updateNote(@NonNull Note note){
        mNoteModel.updateNote(note);
    }
}
