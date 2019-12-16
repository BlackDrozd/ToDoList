package com.android.todolist.data.db;

public class NoteTable {

    public static final String TABLE = "notes";

    public static class COLUMN {
        public static final String ID = "_id";
        public static final String TITLE = "title";
        public static final String NOTE_TEXT = "note_text";
    }

    public static final String CREATE_SCRIPT =
            String.format("create table %s ("
                            + "%s integer primary key autoincrement,"
                            + "%s text,"
                            + "%s text" + ");",
                    TABLE, COLUMN.ID, COLUMN.TITLE, COLUMN.NOTE_TEXT);

}
