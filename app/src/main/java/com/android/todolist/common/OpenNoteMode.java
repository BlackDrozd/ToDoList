package com.android.todolist.common;

import androidx.annotation.NonNull;

public enum OpenNoteMode {

    CREATE_NEW_NOTE("com.android.todolist.CREATE_NEW_NOTE"),
    EDIT_NOTE("com.android.todolist.EDIT_NOTE");

    private String mode;

    OpenNoteMode(String mode){
        this.mode = mode;
    }

    public String getMode(){
        return mode;
    }

    @NonNull
    @Override
    public String toString() {
        return this.mode;
    }


}
