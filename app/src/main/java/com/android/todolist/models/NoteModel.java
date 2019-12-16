package com.android.todolist.models;

import android.database.Cursor;
import android.os.AsyncTask;

import com.android.todolist.common.Note;
import com.android.todolist.data.NoteData;
import com.android.todolist.data.db.DbHelper;
import com.android.todolist.data.db.NoteTable;

import java.util.LinkedList;
import java.util.List;


public class NoteModel {

    private final DbHelper mDbHelper;

    public NoteModel(DbHelper dbHelper){
        mDbHelper = dbHelper;
    }

    public void loadNotes(LoadNoteCallback callback) {
        LoadNotesTask loadNotesTask = new LoadNotesTask(callback);
        loadNotesTask.execute();
    }


    interface LoadNoteCallback {
        void onLoad(List<NoteData> notes);
    }

    class LoadNotesTask extends AsyncTask<Void, Void, List<Note>>{

        private final LoadNoteCallback callBack;

        LoadNotesTask(LoadNoteCallback callBack){this.callBack = callBack;}

        @Override
        protected List<Note> doInBackground(Void... voids) {

            List<Note> notes = new LinkedList<>();

            Cursor cursor = mDbHelper.getReadableDatabase().query(NoteTable.TABLE, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndex(NoteTable.COLUMN.ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN.TITLE)));
                note.setText(cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN.NOTE_TEXT)));
                notes.add(note);
            }
            cursor.close();

            return notes;
        }
    }

}
