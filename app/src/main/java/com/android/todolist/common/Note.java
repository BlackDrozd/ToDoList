package com.android.todolist.common;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Note {

    private long mId;
    private String mTitle;
    private String mText;

    public Note(){

    }

    public Note(String title, String text){
        mTitle = title;
        mText = text;
    }

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
        return String.format("{ Note: id = %s, title = %s , text = %s }", mId, mTitle, mText) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mTitle, mText);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return mId == note.mId &&
                mTitle.equals(note.mTitle) &&
                Objects.equals(mText, note.mText);
    }
}
