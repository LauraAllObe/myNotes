package com.zybooks.finalproject.repo;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.zybooks.finalproject.model.Note;
import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note WHERE id = :id")
    LiveData<Note> getNote(long id);

    @Query("SELECT * FROM Note ORDER BY title COLLATE NOCASE")
    LiveData<List<Note>> getNotes();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);
}