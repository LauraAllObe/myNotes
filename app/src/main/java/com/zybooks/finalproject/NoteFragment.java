package com.zybooks.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;
import com.zybooks.finalproject.model.Note;
import com.zybooks.finalproject.repo.MemoRepository;
import com.zybooks.finalproject.viewmodel.NoteListViewModel;

import java.util.List;

public class NoteFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note, container, false);

        NoteListViewModel mNoteListViewModel = new ViewModelProvider(this).get(NoteListViewModel.class);

        // Create 2 grid layout columns
        mRecyclerView = rootView.findViewById(R.id.subject_recycler_view);
        RecyclerView.LayoutManager gridLayoutManager =
                new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        rootView.findViewById(R.id.add_button).setOnClickListener(view -> {
            // Create fragment arguments containing the selected memo ID
            int selectedNoteId = (int)-2;
            Bundle args = new Bundle();
            args.putInt(NoteDetailsFragment.ARG_NOTE_ID, selectedNoteId);
            args.putString(NoteDetailsFragment.ARG_NOTE_TITLE, "");
            args.putString(NoteDetailsFragment.ARG_NOTE_TEXT, "");
            args.putInt(NoteDetailsFragment.ARG_NOTE_COLOR, 0);
            args.putInt(NoteDetailsFragment.ARG_TEXT_COLOR, 0);
            args.putInt(NoteDetailsFragment.ARG_TEXT_ALIGN, 1);
            args.putInt(NoteDetailsFragment.ARG_TEXT_SIZE, 42);

            // Replace list with details
            Navigation.findNavController(view).navigate(R.id.show_item_detail, args);
        });

        mNoteListViewModel.getNotes().observe(getViewLifecycleOwner(), this::updateUI);

        return rootView;
    }

    private void updateUI(List<Note> notes) {
        mRecyclerView.setAdapter(new NoteAdapter(notes));
    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {

        private final List<Note> mNotes;
        public NoteAdapter(List<Note> notes) {
            mNotes = notes;
        }

        @NonNull
        @Override
        public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new NoteHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(NoteHolder holder, int position) {
            Note note = mNotes.get(position);
            holder.bind(note);
            holder.itemView.setTag(note.getId());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putInt(NoteDetailsFragment.ARG_NOTE_ID, (int)note.getId());
                    args.putString(NoteDetailsFragment.ARG_NOTE_TITLE, note.getTitle());
                    args.putString(NoteDetailsFragment.ARG_NOTE_TEXT, note.getText());
                    args.putInt(NoteDetailsFragment.ARG_NOTE_COLOR, note.getNoteColor());
                    args.putInt(NoteDetailsFragment.ARG_TEXT_COLOR, note.getTextColor());
                    args.putInt(NoteDetailsFragment.ARG_TEXT_ALIGN, note.getTextAlign());
                    args.putInt(NoteDetailsFragment.ARG_TEXT_SIZE, note.getTextSize());

                    // Replace list with details
                    Navigation.findNavController(holder.itemView).navigate(R.id.show_item_detail, args);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }
    }

    private static class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView mTitleTextView;
        private final TextView mTextTextView;

        public NoteHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recycler_view_items, parent, false));
            mTitleTextView = itemView.findViewById(R.id.note_title_view);
            mTextTextView = itemView.findViewById(R.id.note_text_view);
            itemView.setOnClickListener(this);
        }

        public void bind(Note note) {

            mTitleTextView.setText(note.getTitle());
            mTextTextView.setText(note.getText());
            itemView.setTag(note.getId());
            mTitleTextView.setBackgroundColor(note.getNoteColor());
            mTextTextView.setBackgroundColor(note.getNoteColor());
            mTitleTextView.setTextColor(note.getTextColor());
            mTextTextView.setTextColor(note.getTextColor());
            int size = note.getTextSize();
            if(size > 80)
            {
                mTextTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            }
            else if(size > 60)
            {
                mTextTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
                mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
            }
            else if(size > 40)
            {
                mTextTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
            else if(size > 20)
            {
                mTextTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            }
            else if(size > 0)
            {
                mTextTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
                mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
            }

            if(note.getTextAlign() == 0)
            {
                mTitleTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                mTextTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            }
            else if(note.getTextAlign() == 1)
            {
                mTitleTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                mTextTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            else if(note.getTextAlign() == 2)
            {
                mTitleTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                mTextTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            }
        }

        @Override
        public void onClick(View view) {
            Bundle args = new Bundle();
            args.putInt(NoteDetailsFragment.ARG_NOTE_ID, (int) itemView.getTag());

            // Replace list with details
            Navigation.findNavController(itemView).navigate(R.id.show_item_detail, args);
        }
    }
}