package com.zybooks.finalproject.repo;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import androidx.lifecycle.MutableLiveData;
import com.android.volley.VolleyError;
import androidx.lifecycle.LiveData;
import android.content.Context;
import androidx.room.Room;
import com.zybooks.finalproject.model.Note;
import java.util.List;

public class MemoRepository {
    private static MemoRepository mMemoRepo;
    private final NoteDao mNoteDao;
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService mDatabaseExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MemoRepository getInstance(Context context) {
        if (mMemoRepo == null) {
            mMemoRepo = new MemoRepository(context);
        }
        return mMemoRepo;
    }

    private MemoRepository(Context context) {
        RoomDatabase.Callback databaseCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }
        };

        MemoDatabase database = Room.databaseBuilder(context, MemoDatabase.class, "memo.db")
                .addCallback(databaseCallback)
                //.allowMainThreadQueries()
                .build();

        mNoteDao = database.NoteDao();
    }

    public void addNote(Note note) {
        mDatabaseExecutor.execute(() -> {
            long noteId = mNoteDao.addNote(note);
            note.setId(noteId);
        });
    }

    public LiveData<Note> getNote(long noteId) {
        return mNoteDao.getNote(noteId);
    }

    public LiveData<List<Note>> getNotes() {
        return mNoteDao.getNotes();
    }

    public void deleteNote(Note note) {
        mDatabaseExecutor.execute(() ->
                mNoteDao.deleteNote(note)
        );
    }

    public void updateNote(Note note) {
        mDatabaseExecutor.execute(() ->
                mNoteDao.updateNote(note)
        );
    }

}