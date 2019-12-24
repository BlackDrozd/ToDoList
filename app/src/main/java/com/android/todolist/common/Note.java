package com.android.todolist.common;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Note {

    private long mId;
    private String mTitle;
    private String mText;
    private int mColor;

    public Note(){

    }

    public Note(String title, String text){
        mTitle = title;
        mText = text;
        mColor = Integer.parseInt("b0e7cc", 16);
    }

    public Note(String title, String text, int color){
        mTitle = title;
        mText = text;
        mColor = color;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getColor() { return mColor; }

    public void setColor(int color) { mColor = color; }

    @NonNull
    @Override
    public String toString() {
        return String.format("{ Note: id = %s, title = %s , text = %s, color = %d}", mId, mTitle, mText, mColor) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mTitle, mText, mColor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return mId == note.mId &&
                mTitle.equals(note.mTitle) &&
                mColor == note.mColor &&
                Objects.equals(mText, note.mText);
    }
}
