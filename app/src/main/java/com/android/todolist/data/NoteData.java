package com.android.todolist.data;

public class NoteData {

    private String mTitle;
    private String mText;
    private int mColor;

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

    public int getColor(){ return mColor; }

    public void setColor(int color){ mColor = color;}

}
