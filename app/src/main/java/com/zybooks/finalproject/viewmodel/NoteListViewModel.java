package com.zybooks.finalproject.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.zybooks.finalproject.model.Note;
import com.zybooks.finalproject.repo.MemoRepository;
import java.util.List;

public class NoteListViewModel extends AndroidViewModel {

    private final MemoRepository mMemoRepo;

    public NoteListViewModel(Application application) {
        super(application);
        mMemoRepo = MemoRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Note>> getNotes() {
        return mMemoRepo.getNotes();
    }

    public void addNote(Note note) {
        mMemoRepo.addNote(note);
    }

    public void deleteNote(Note note) {
        mMemoRepo.deleteNote(note);
    }
}