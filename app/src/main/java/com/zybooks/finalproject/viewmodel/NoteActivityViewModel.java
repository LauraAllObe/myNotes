package com.zybooks.finalproject.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.zybooks.finalproject.model.Note;
import com.zybooks.finalproject.repo.MemoRepository;
import java.util.List;

public class NoteActivityViewModel extends AndroidViewModel{
    private final MemoRepository memoRepository;
    public NoteActivityViewModel(Application application){
        //Initialize MemoRepository
        super(application);
        memoRepository = MemoRepository.getInstance(application.getApplicationContext());
    }

    //retrieve a specific note by its ID from the repository
    public LiveData<Note> getNoteLiveData(long noteId) {
        return memoRepository.getNote(noteId);
    }

    public LiveData<List<Note>> getNotes() {
        return memoRepository.getNotes();
    }

    //Update a note
    public void updateNote(Note note){
        memoRepository.updateNote(note);
    }

    //Delete a note
    public void deleteNote(Note note){
        memoRepository.deleteNote(note);
    }

    //Add a note
    public void addNote(Note note){
        memoRepository.addNote(note);
    }

}
