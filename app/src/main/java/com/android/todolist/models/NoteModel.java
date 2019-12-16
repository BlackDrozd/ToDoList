package com.android.todolist.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;

import com.android.todolist.common.Note;
import com.android.todolist.data.db.DbHelper;
import com.android.todolist.data.db.NoteTable;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class NoteModel {

    private final DbHelper mDbHelper;

    public NoteModel(DbHelper dbHelper){
        mDbHelper = dbHelper;
    }

    public void loadNotes(LoadNoteCallback callback) {
        LoadNotesTask loadNotesTask = new LoadNotesTask(callback);
        loadNotesTask.execute();
    }

    public void addNote(ContentValues contentValues, CompleteCallback callback) {
        AddNoteTask addUserTask = new AddNoteTask(callback);
        addUserTask.execute(contentValues);
    }


    public interface LoadNoteCallback {
        void onLoad(List<Note> notes);
    }

    public interface CompleteCallback {
        void onComplete();
    }

    class LoadNotesTask extends AsyncTask<Void, Void, List<Note>>{

        private final LoadNoteCallback callback;

        LoadNotesTask(LoadNoteCallback callBack){this.callback = callBack;}

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

        @Override
        protected void onPostExecute(List<Note> notes) {
            if (callback != null) {
                callback.onLoad(notes);
            }
        }


    }

    class AddNoteTask extends AsyncTask<ContentValues, Void, Void>{

        private final CompleteCallback callback;

        AddNoteTask(CompleteCallback callback){
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(ContentValues... params) {
            ContentValues cvNote = params[0];
            mDbHelper.getWritableDatabase().insert(NoteTable.TABLE, null, cvNote);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (callback != null) {
                callback.onComplete();
            }
        }
    }


}
