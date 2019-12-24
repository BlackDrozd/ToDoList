package com.android.todolist.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.todolist.common.Note;
import com.android.todolist.data.db.DbHelper;
import com.android.todolist.data.db.NoteTable;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class NoteModel {

    private final DbHelper mDbHelper;
    private SQLiteDatabase db;

    public NoteModel(DbHelper dbHelper){
        mDbHelper = dbHelper;
        db = mDbHelper.getReadableDatabase();
    }

    public void loadNotes(LoadNoteCallback callback) {
        LoadNotesTask loadNotesTask = new LoadNotesTask(callback);
        loadNotesTask.execute();
    }

    public void addNote(Note note, CompleteCallback callback) {

     //  GetGoogleTimeTask getGoogleTimeTask = new GetGoogleTimeTask();
      // getGoogleTimeTask.execute();
       AddNoteTask addNoteTask = new AddNoteTask(callback);
       addNoteTask.execute(note);
    }

    public Note loadNoteById(@NonNull Long noteId){
        Note note = new Note();
        String[] args = {Long.toString(noteId)};
        String query = "SELECT * FROM "+NoteTable.TABLE+" WHERE "+NoteTable.COLUMN.ID+" = ?";
        Cursor cursor = db.rawQuery(query, args);
        while (cursor.moveToNext()) {
            note.setId(cursor.getLong(cursor.getColumnIndex(NoteTable.COLUMN.ID)));
            note.setTitle(cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN.TITLE)));
            note.setText(cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN.NOTE_TEXT)));
        }
        cursor.close();
        return note;
    }

    public void updateNote(@NonNull Note note){

        ContentValues newValues = new ContentValues();
        newValues.put(NoteTable.COLUMN.TITLE, note.getTitle());
        newValues.put(NoteTable.COLUMN.NOTE_TEXT, note.getText());
        String where = NoteTable.COLUMN.ID + "=" + note.getId();
        db.update(NoteTable.TABLE, newValues,where,null );
    }

    public void deleteNote(@NonNull Long noteId){
        String where = NoteTable.COLUMN.ID + "=" + noteId;
        db.delete(NoteTable.TABLE, where,  null);
    }

    public void deleteNotes(){
        db.delete(NoteTable.TABLE,null, null);
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

    class AddNoteTask extends AsyncTask<Note, Void, Void>{

        private final CompleteCallback callback;

        AddNoteTask(CompleteCallback callback){
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Note... params) {
            Note note = params[0];
            mDbHelper.getWritableDatabase()
                    .insert(NoteTable.TABLE, null, convertNoteToContentValues(note));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        private ContentValues convertNoteToContentValues (Note note) {
            Log.d(TAG, note.toString());
            ContentValues cv = new ContentValues(3);
            cv.put(NoteTable.COLUMN.TITLE, note.getTitle());
            cv.put(NoteTable.COLUMN.NOTE_TEXT, note.getText());
            cv.put(NoteTable.COLUMN.COLOR_ID, note.getColor());
            return cv;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (callback != null) {
                callback.onComplete();
            }
        }
    }

    class GetGoogleTimeTask extends AsyncTask<Void, Void, ZonedDateTime>{

        @Override
        protected ZonedDateTime doInBackground(Void... voids) {

           HttpURLConnection urlConnection = null;

            try{
                urlConnection = (HttpURLConnection) new URL("https://google.com").openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    Log.d(TAG,"jsonanswer= "+in.toString());
                   // readStream(in);
            }
           catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }
            return null;
        }
    }

}
