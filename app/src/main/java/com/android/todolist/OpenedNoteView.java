package com.android.todolist;

public interface OpenedNoteView {

    String getNoteTitle();

    String getNoteText();

    void showCreationError(int resId);
}
