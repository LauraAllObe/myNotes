package com.zybooks.finalproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zybooks.finalproject.model.Note;
import com.zybooks.finalproject.repo.MemoRepository;

public class NoteDetailFragment extends Fragment {

    private Note mNote;

    public NoteDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int noteId = 1;

        mNote = MemoRepository.getInstance(requireContext()).getNote(noteId).getValue();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_detail, container, false);
        rootView.setVisibility(View.VISIBLE);

        ImageButton mCancel = (ImageButton)rootView.findViewById(R.id.cancel_button);
        ImageButton mSave = (ImageButton)rootView.findViewById(R.id.save_button);

        TextView noteTextView = rootView.findViewById(R.id.note_text_view);
        TextView noteTitleView = rootView.findViewById(R.id.note_title_view);
        if (mNote != null) {
            noteTextView.setText(mNote.getText());
            noteTitleView.setText(mNote.getTitle());
        }

        mCancel.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   rootView.setVisibility(View.GONE);
               }
           }

        );

        mSave.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mNote.setText(noteTextView.getText().toString());
                   mNote.setTitle(noteTitleView.getText().toString());
                   rootView.setVisibility(View.GONE);
               }
           }

        );
        return rootView;
    }
}