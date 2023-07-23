package com.zybooks.finalproject;

import android.app.Activity;
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

        //ERRORS AND TESTING
        mNoteListViewModel = new ViewModelProvider(this).get(NoteListViewModel.class);
        MemoRepository memoRepository = MemoRepository.getInstance(this.getContext());
        List<Note> noteList = mNoteListViewModel.getNotes().getValue();
        List<Note> noteList2 = memoRepository.getNotes().getValue();
        //END ERRORS AND TESTING

        // Click listener for the RecyclerView
        View.OnClickListener onClickListener = itemView -> {

            // Create fragment arguments containing the selected memo ID
            int selectedNoteId = (int) itemView.getTag();
            Bundle args = new Bundle();
            args.putInt(NoteDetailsFragment.ARG_NOTE_ID, selectedNoteId);

            // Replace list with details
            Navigation.findNavController(itemView).navigate(R.id.show_item_detail, args);
        };


        rootView.findViewById(R.id.add_button).setOnClickListener(view -> {
            // Create fragment arguments containing the selected memo ID
            int selectedNoteId = (int)-2;
            Bundle args = new Bundle();
            args.putInt(NoteDetailsFragment.ARG_NOTE_ID, selectedNoteId);

            // Replace list with details
            Navigation.findNavController(view).navigate(R.id.show_item_detail, args);
        });

        // Create 2 grid layout columns
        mRecyclerView = rootView.findViewById(R.id.subject_recycler_view);
        RecyclerView.LayoutManager gridLayoutManager =
                new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);


        //ERRORS AND TESTING
        List<Note> notes = mNoteListViewModel.getNotes().getValue();
        mNoteListViewModel.addNote(new Note("","",0));
        notes = mNoteListViewModel.getNotes().getValue();

        mRecyclerView.setAdapter(new NoteAdapter(notes, onClickListener));
        //END ERRORS AND TESTING
        //Show the subjects
        //updateUI(mNoteListViewModel.getNotes().getValue());
        return rootView;
    }
    /*
    private void updateUI(List<Note> noteList) {
        mNoteAdapter = new NoteAdapter(noteList);
        mRecyclerView.setAdapter(mNoteAdapter);
    }*/

    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {

        private final List<Note> mNotes;
        private final View.OnClickListener mOnClickListener;
        public NoteAdapter(List<Note> notes, View.OnClickListener onClickListener) {
            mNotes = notes;
            mOnClickListener = onClickListener;
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
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }
    }

    private static class NoteHolder extends RecyclerView.ViewHolder {

        private final TextView mTitleTextView;
        private final TextView mTextTextView;

        public NoteHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recycler_view_items, parent, false));
            mTitleTextView = itemView.findViewById(R.id.note_title_view);
            mTextTextView = itemView.findViewById(R.id.note_text_view);
        }

        public void bind(Note note) {
            mTitleTextView.setText(note.getTitle());
            mTextTextView.setText(note.getText());
            mTitleTextView.setBackgroundColor(images[note.getColor()]);
            mTitleTextView.setBackgroundColor(images[note.getColor()]);
        }
    }
}