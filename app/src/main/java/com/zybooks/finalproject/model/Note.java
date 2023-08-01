package com.zybooks.finalproject.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;
    @NonNull
    @ColumnInfo(name = "title")
    private String mTitle;
    @NonNull
    @ColumnInfo(name = "text")
    private String mText;

    @NonNull
    @ColumnInfo(name = "note color")
    private Integer mNoteColor;

    @NonNull
    @ColumnInfo(name = "text color")
    private Integer mTextColor;

    @NonNull
    @ColumnInfo(name = "text align")
    private Integer mTextAlign;

    @NonNull
    @ColumnInfo(name = "text size")
    private Integer mTextSize;

    public Note(@NonNull String title, @NonNull String text, @NonNull Integer noteColor, @NonNull Integer textColor, @NonNull Integer textAlign, @NonNull Integer textSize) {
        mTitle = title;
        mText = text;
        mNoteColor = noteColor;
        mTextColor = textColor;
        mTextAlign = textAlign;
        mTextSize = textSize;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
    public Integer getNoteColor() {
        return mNoteColor;
    }
    public void setNoteColor(Integer noteColor) {
        mNoteColor = noteColor;
    }

    public Integer getTextColor() {
        return mTextColor;
    }
    public void setTextColor(Integer textColor) {
        mTextColor = textColor;
    }
    public Integer getTextAlign() {
        return mTextAlign;
    }
    public void setTextAlign(Integer textAlign) {
        mTextAlign = textAlign;
    }
    public Integer getTextSize() {
        return mTextSize;
    }
    public void setTextSize(Integer textSize) {
        mTextSize = textSize;
    }
}