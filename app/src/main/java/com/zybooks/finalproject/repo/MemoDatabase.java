package com.zybooks.finalproject.repo;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.zybooks.finalproject.model.Note;

@Database(entities = {Note.class}, version = 3)
public abstract class MemoDatabase extends RoomDatabase {
    public abstract NoteDao NoteDao();
}
