package com.zybooks.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
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

    private NoteAdapter mNoteAdapter;
    private RecyclerView mRecyclerView;
    private NoteListViewModel mNoteListViewModel;
    private static final int[] images = {R.drawable.berry, R.drawable.candy, R.drawable.tomato, R.drawable.clementine, R.drawable.kale, R.drawable.spirulina, R.drawable.marlin, R.drawable.lobster, R.drawable.berry};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note, container, false);

        mNoteListViewModel = new ViewModelProvider(this).get(NoteListViewModel.class);

        // Create 2 grid layout columns
        mRecyclerView = rootView.findViewById(R.id.subject_recycler_view);
        RecyclerView.LayoutManager gridLayoutManager =
                new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        /*
        // Click listener for the RecyclerView
        View.OnClickListener onClickListener = itemView -> {

            // Create fragment arguments containing the selected memo ID
            int selectedNoteId = (int) itemView.getTag();
            Bundle args = new Bundle();
            args.putInt(NoteDetailsFragment.ARG_NOTE_ID, selectedNoteId);

            // Replace list with details
            Navigation.findNavController(itemView).navigate(R.id.show_item_detail, args);
        };*/


        rootView.findViewById(R.id.add_button).setOnClickListener(view -> {
            // Create fragment arguments containing the selected memo ID
            int selectedNoteId = (int)-2;
            Bundle args = new Bundle();
            args.putInt(NoteDetailsFragment.ARG_NOTE_ID, selectedNoteId);
            args.putString(NoteDetailsFragment.ARG_NOTE_TITLE, "");
            args.putString(NoteDetailsFragment.ARG_NOTE_TEXT, "");
            args.putInt(NoteDetailsFragment.ARG_NOTE_COLOR, 0);

            // Replace list with details
            Navigation.findNavController(view).navigate(R.id.show_item_detail, args);
        });

        mNoteListViewModel.getNotes().observe(getViewLifecycleOwner(), notes -> {
            updateUI(notes);
        });

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
                    //int selectedNoteId = (int) itemView.getTag();
                    Bundle args = new Bundle();
                    args.putInt(NoteDetailsFragment.ARG_NOTE_ID, (int)note.getId());
                    args.putString(NoteDetailsFragment.ARG_NOTE_TITLE, note.getTitle());
                    args.putString(NoteDetailsFragment.ARG_NOTE_TEXT, note.getText());
                    args.putInt(NoteDetailsFragment.ARG_NOTE_COLOR, note.getColor());

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
            int[] colorCode = {-7860657, -4776932, -4246004, -688361, -13407970, -16752540, -16689253, -15064194, -11922292};
            mTitleTextView.setBackgroundColor(colorCode[note.getColor()]);
            mTextTextView.setBackgroundColor(colorCode[note.getColor()]);
        }

        @Override
        public void onClick(View view) {
            //int selectedNoteId = (int) itemView.getTag();
            Bundle args = new Bundle();
            args.putInt(NoteDetailsFragment.ARG_NOTE_ID, (int) itemView.getTag());

            // Replace list with details
            Navigation.findNavController(itemView).navigate(R.id.show_item_detail, args);
        }
    }
}