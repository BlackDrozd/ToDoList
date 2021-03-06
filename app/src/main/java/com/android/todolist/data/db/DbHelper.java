package com.android.todolist.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";

    public static final String NAME = "note";
    public static final int VERSION = 3;

    public DbHelper(Context context) {
        super(context, NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NoteTable.CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NoteTable.TABLE);
       onCreate(db);
    }
    

}
