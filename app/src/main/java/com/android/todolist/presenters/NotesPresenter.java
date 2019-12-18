package com.android.todolist.presenters;

import android.content.Context;
import android.content.Intent;

import com.android.todolist.MainActivity;
import com.android.todolist.NewNoteActivity;
import com.android.todolist.common.Note;
import com.android.todolist.common.OpenNoteMode;
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

    public void openNote(int position, List<Note> notes, Context context){
        Note selectedNote = notes.get(position);
        Intent intent = new Intent(context, NewNoteActivity.class);
        intent.setAction(OpenNoteMode.EDIT_NOTE.toString());
        intent.putExtra("noteId", selectedNote.getId());
        context.startActivity(intent);
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
