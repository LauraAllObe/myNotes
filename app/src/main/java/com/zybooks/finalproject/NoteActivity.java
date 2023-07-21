package com.zybooks.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.zybooks.finalproject.repo.MemoRepository;
import android.os.Bundle;


public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // Initialize MemoRepository
        MemoRepository memoRepository = MemoRepository.getInstance(this);

        // Display the NoteDetailFragment
        openNoteDetailFragment();

        // Handle the "Add Memo" floating action button click
        findViewById(R.id.add_memo_button).setOnClickListener(v -> {
            openNoteDetailFragment(); // Pass -1 to indicate that it's a new note
        });
    }

    // Method to open the NoteDetailFragment for displaying/editing a specific note
    private void openNoteDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Create the NoteDetailFragment and pass the noteId as an argument
        NoteDetailFragment noteDetailFragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putLong("noteId", -1);
        noteDetailFragment.setArguments(args);

        // Replace the fragment_container with the NoteDetailFragment
        fragmentTransaction.replace(R.id.fragment_container, noteDetailFragment);

        // Add the transaction to the back stack(optional)
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();
    }

}