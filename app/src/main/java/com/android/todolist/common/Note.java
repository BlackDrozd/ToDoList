package com.android.todolist.common;

import androidx.annotation.NonNull;

public class Note {

    private long mId;
    private String mTitle;
    private String mText;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getName() + String.format(": id = %s, title = %s , text = %s", mId, mTitle, mText) ;
    }
}
