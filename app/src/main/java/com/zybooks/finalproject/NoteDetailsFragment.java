package com.zybooks.finalproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.zybooks.finalproject.model.Note;
import com.zybooks.finalproject.viewmodel.NoteListViewModel;

public class NoteDetailsFragment extends Fragment {
    public static final String ARG_NOTE_ID = "note_id";
    private Note mNote;
    private NoteListViewModel mNoteListViewModel;
    private TextView titleTextView;
    private TextView textTextView;
    private EditText titleTextEdit;
    private EditText textTextEdit;

    private ImageButton mDeleteButton;
    private ImageButton mUndoButton;
    private ImageButton mSaveButton;
    private Spinner spinner;
    private int currentColorIndex;
    private final String[] colors={"Berry","Candy","Tomato","Clementine","Kale", "Spirulina", "Marlin", "Lobster", "Berry"};
    private final int[] images = {R.drawable.berry, R.drawable.candy, R.drawable.tomato, R.drawable.clementine, R.drawable.kale, R.drawable.spirulina, R.drawable.marlin, R.drawable.lobster, R.drawable.berry};

    public NoteDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int noteId = 1;

        // Get the band ID from the fragment arguments
        Bundle args = getArguments();
        if (args != null) {
            noteId = args.getInt(ARG_NOTE_ID);
        }
        mNoteListViewModel = new ViewModelProvider(this).get(NoteListViewModel.class);
        // Get the selected band
        if(noteId > 0)
            mNote = mNoteListViewModel.getNote(noteId).getValue();

        if(noteId < 0 || mNote == null)
        {
            mNote = new Note("", "",0);
            noteId = (int)mNote.getId();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_details, container, false);

        if (mNote != null) {
            currentColorIndex = mNote.getColor();

            titleTextView = rootView.findViewById(R.id.note_title_desc);
            titleTextView.setVisibility(View.GONE);

            titleTextEdit = rootView.findViewById(R.id.note_title);
            titleTextEdit.setText(mNote.getTitle());
            titleTextEdit.setBackgroundColor(images[mNote.getColor()]);

            textTextView = rootView.findViewById(R.id.note_text_desc);
            textTextView.setVisibility(View.GONE);

            textTextEdit = rootView.findViewById(R.id.note_text);
            textTextEdit.setText(mNote.getText());
            textTextEdit.setBackgroundColor(images[mNote.getColor()]);

            spinner = rootView.findViewById(R.id.colors_spinner);
            spinner.setId(mNote.getColor());

            mDeleteButton = rootView.findViewById(R.id.delete_button);
            mDeleteButton.setOnClickListener( view -> {
                // Replace list with details
                mNoteListViewModel.deleteNote(mNote);
                mNote = null;
                Fragment fragment = new NoteFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });

            mUndoButton = rootView.findViewById(R.id.undo_button);
            mUndoButton.setOnClickListener( view -> {
                textTextEdit.setText(mNote.getText());
                titleTextEdit.setText(mNote.getTitle());
                mNote.setColor(mNote.getColor());
            });

            mSaveButton = rootView.findViewById(R.id.save_button);
            mSaveButton.setOnClickListener( view -> {
                // Replace list with details
                mNote.setTitle(titleTextEdit.getText().toString());
                mNote.setText(textTextEdit.getText().toString());
                mNote.setColor(currentColorIndex);
                mNoteListViewModel.addNote(mNote);
                Fragment fragment = new NoteFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(this, "You Select Position: "+position+" "+fruits[position], Toast.LENGTH_SHORT).show();
                    mNote.setColor(position);
                    currentColorIndex = position;
                    titleTextEdit.setBackgroundColor(images[position]);
                    textTextEdit.setBackgroundColor(images[position]);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    textTextEdit.setBackgroundColor(images[mNote.getColor()]);
                    titleTextEdit.setBackgroundColor(images[mNote.getColor()]);
                }
            });
            ColorSpinnerAdapter colorSpinnerAdapter=new ColorSpinnerAdapter(getActivity(),images,colors);
            spinner.setAdapter(colorSpinnerAdapter);
        }
        return rootView;
    }
}