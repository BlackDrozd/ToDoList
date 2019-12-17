package com.android.todolist.presenters;

import android.util.Log;

import com.android.todolist.MainActivity;
import com.android.todolist.common.Note;
import com.android.todolist.data.NoteData;
import com.android.todolist.models.NoteModel;

import java.util.List;

public class NotesPresenter {

    private static final String TAG = "NotesPresenter";

    private MainActivity startView;

    private NoteData noteData;

    private final NoteModel mNoteModel;

    public NotesPresenter(NoteModel model){ mNoteModel = model;}

    public void attachView(MainActivity mainActivity){ startView = mainActivity; }

    public void detachView() { startView = null; }

    public void viewIsReady() { loadAllNotes();}

    public void deleteNote(int position){
    }

    public void openNote(int position, List<Note> notes){
       Note selectedNote = notes.get(position);
        Log.d(TAG,selectedNote.toString());
        // Intent intent = new Intent(this, NewNoteActivity.class);
        // intent.putExtra("selectedNote",mNotes.get(position));
        // startActivity(intent);
    }

    private void loadAllNotes() {
        mNoteModel.loadNotes(new NoteModel.LoadNoteCallback() {
            @Override
            public void onLoad(List<Note> notes) {
                startView.showNotes(notes);
            }
        });
    }

}
