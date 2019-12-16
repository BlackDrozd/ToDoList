package com.android.todolist.presenters;

import com.android.todolist.MainActivity;
import com.android.todolist.common.Note;
import com.android.todolist.models.NoteModel;

import java.util.List;

public class NotesPresenter {

    private MainActivity startView;

    private final NoteModel mNoteModel;

    public NotesPresenter(NoteModel model){ mNoteModel = model;}

    public void attachView(MainActivity mainActivity){ startView = mainActivity; }

    public void detachView() { startView = null; }

    public void viewIsReady() { loadAllNotes();}

    private void loadAllNotes() {
        mNoteModel.loadNotes(new NoteModel.LoadNoteCallback() {
            @Override
            public void onLoad(List<Note> notes) {
                startView.showNotes(notes);
            }
        });
    }


}
