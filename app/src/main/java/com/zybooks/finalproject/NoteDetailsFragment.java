package com.zybooks.finalproject;

import yuku.ambilwarna.AmbilWarnaDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
    public static final String ARG_NOTE_TITLE = "note_title";
    public static final String ARG_NOTE_TEXT = "note_text";
    public static final String ARG_NOTE_COLOR = "note_color";
    private Note mNote;
    private NoteListViewModel mNoteListViewModel;
    private TextView titleTextView;
    private TextView textTextView;
    private EditText titleTextEdit;
    private EditText textTextEdit;

    private ImageButton mDeleteButton;
    private ImageButton mUndoButton;
    private ImageButton mSaveButton;
    private Button mNoteColor;
    private int currentColor;
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
        {
            mNote = new Note(args.getString(ARG_NOTE_TITLE), args.getString(ARG_NOTE_TEXT), args.getInt(ARG_NOTE_COLOR));
            mNote.setId(args.getInt(ARG_NOTE_ID));
        }

        if(noteId < 0 || mNote == null)
        {
            mNote = new Note("", "",-10931967);
            noteId = (int)mNote.getId();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_details, container, false);

        if (mNote != null) {
            currentColor = mNote.getColor();
            int[] colorCode = getResources().getIntArray(R.array.memoColors);

            titleTextView = rootView.findViewById(R.id.note_title_desc);
            titleTextView.setVisibility(View.GONE);

            titleTextEdit = rootView.findViewById(R.id.note_title);
            titleTextEdit.setText(mNote.getTitle());
            titleTextEdit.setBackgroundColor(currentColor);

            textTextView = rootView.findViewById(R.id.note_text_desc);
            textTextView.setVisibility(View.GONE);

            textTextEdit = rootView.findViewById(R.id.note_text);
            textTextEdit.setText(mNote.getText());
            textTextEdit.setBackgroundColor(currentColor);

            mNoteColor = rootView.findViewById(R.id.note_color_button);

            mDeleteButton = rootView.findViewById(R.id.delete_button);
            mDeleteButton.setOnClickListener( view -> {
                mNoteListViewModel.deleteNote(mNote);
                mNote = null;

                //replace details with list
                Navigation.findNavController(rootView).navigateUp();
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
                mNote.setColor(currentColor);
                mNoteListViewModel.addNote(mNote);

                //replace details with list
                Navigation.findNavController(rootView).navigateUp();
            });

            mNoteColor.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openColorPickerDialogue();
                        }
                    }
                    );

            /*mNoteColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(this, "You Select Position: "+position+" "+fruits[position], Toast.LENGTH_SHORT).show();
                    mNote.setColor(position);
                    currentColorIndex = position;
                    titleTextEdit.setBackgroundColor(colorCode[position]);
                    textTextEdit.setBackgroundColor(colorCode[position]);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    textTextEdit.setBackgroundColor(colorCode[mNote.getColor()]);
                    titleTextEdit.setBackgroundColor(colorCode[mNote.getColor()]);
                }
            });
            ColorSpinnerAdapter colorSpinnerAdapter=new ColorSpinnerAdapter(getActivity(),images,colors);
            spinner.setAdapter(colorSpinnerAdapter);
            spinner.setSelection(mNote.getColor());*/
        }
        return rootView;
    }

    public void openColorPickerDialogue()
    {
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this.getContext(), currentColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // leave this function body as
                        // blank, as the dialog
                        // automatically closes when
                        // clicked on cancel button
                        titleTextEdit.setBackgroundColor(mNote.getColor());
                        textTextEdit.setBackgroundColor(mNote.getColor());
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // change the mDefaultColor to
                        // change the GFG text color as
                        // it is returned when the OK
                        // button is clicked from the
                        // color picker dialog
                        currentColor = color;

                        // now change the picked color
                        // preview box to mDefaultColor
                        titleTextEdit.setBackgroundColor(currentColor);
                        textTextEdit.setBackgroundColor(currentColor);
                    }
                });
        colorPickerDialogue.show();
    }
}