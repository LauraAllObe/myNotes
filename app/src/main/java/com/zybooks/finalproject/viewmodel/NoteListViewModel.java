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

    public LiveData<Note> getNote(long noteId) {
        return mMemoRepo.getNote(noteId);
    }

    public void addNote(Note note) {
        /*
        if(note.getId() == 0)
        {
            //LiveData<Note> t = mMemoRepo.getNote(1);
            //Note test = t.getValue();
            if(mMemoRepo.getNotes().getValue() != null)
                note.setId(mMemoRepo.getNotes().getValue().size()+1);
        }
         */
        mMemoRepo.addNote(note);
    }

    public void deleteNote(Note note) {
        mMemoRepo.deleteNote(note);
    }

    public void updateNote(Note note) { mMemoRepo.updateNote(note);}
}