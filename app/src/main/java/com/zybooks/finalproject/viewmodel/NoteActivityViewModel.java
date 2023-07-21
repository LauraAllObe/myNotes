package com.zybooks.finalproject.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.zybooks.finalproject.model.Note;
import com.zybooks.finalproject.repo.MemoRepository;

public class NoteActivityViewModel extends ViewModel{
    private final MemoRepository memoRepository;
    private LiveData<Note> noteLiveData;

    public NoteActivityViewModel(Application application){
        //Initialize MemoRepository
        memoRepository = MemoRepository.getInstance(application.getApplicationContext());
    }

    //retrieve a specific note by its ID from the repository
    public LiveData<Note> getNoteLiveData(long noteId) {
        if (noteLiveData == null) {
            noteLiveData = memoRepository.getNote(noteId);
        }
        return noteLiveData;
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
