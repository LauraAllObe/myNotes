package com.zybooks.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.app.Application;

import com.zybooks.finalproject.model.memoRepository;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

   // private final memoRepository mMemoRepository;

   /* public NoteActivity(Application application) {
        super(application);
        mMemoRepository = memoRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<memoRepository>> getSubjects() {
        return mMemoRepository.getMemos();
    }

    public void addMemo(memoRepository memo) {
        mMemoRepository.addMemo(memo);
    }

    public void deleteMemo(memoRepository memo) {
        memoRepository.deleteMemo(memo);
    }*/
}