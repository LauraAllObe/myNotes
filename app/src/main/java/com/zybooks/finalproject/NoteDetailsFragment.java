package com.zybooks.finalproject;

import yuku.ambilwarna.AmbilWarnaDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.text.InputType;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.zybooks.finalproject.model.Note;
import com.zybooks.finalproject.viewmodel.NoteListViewModel;

import java.util.Objects;

public class NoteDetailsFragment extends Fragment {
    public static final String ARG_NOTE_ID = "note_id";
    public static final String ARG_NOTE_TITLE = "note_title";
    public static final String ARG_NOTE_TEXT = "note_text";
    public static final String ARG_NOTE_COLOR = "note_color";
    public static final String ARG_TEXT_COLOR = "text_color";
    public static final String ARG_TEXT_ALIGN = "text_align";
    public static final String ARG_TEXT_SIZE = "text_size";
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
    private Button mTextColor;
    private Button mTextAlign;
    private Button mSizeButton;
    private ImageButton mSizeUpButton;
    private ImageButton mSizeDownButton;
    private int currentNoteColor;
    private int currentTextColor;
    private int currentTextAlign;
    private int currentTextSize;
    private Typeface currentFont;

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
            assert args != null;
            mNote = new Note(args.getString(ARG_NOTE_TITLE), args.getString(ARG_NOTE_TEXT), args.getInt(ARG_NOTE_COLOR), args.getInt(ARG_TEXT_COLOR), args.getInt(ARG_TEXT_ALIGN), args.getInt(ARG_TEXT_SIZE));
            mNote.setId(args.getInt(ARG_NOTE_ID));
        }

        if(noteId < 0 || mNote == null)
        {
            mNote = new Note("", "",-10931967, -3441332, 1,42);
            noteId = (int)mNote.getId();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_details, container, false);

        if (mNote != null) {
            currentNoteColor = mNote.getNoteColor();
            currentTextColor = mNote.getTextColor();
            currentTextAlign = mNote.getTextAlign();
            currentTextSize = mNote.getTextSize();
            currentFont = ResourcesCompat.getFont(rootView.getContext(), mNote.getFont());

            titleTextView = rootView.findViewById(R.id.note_title_desc);
            titleTextView.setVisibility(View.GONE);
            titleTextEdit = rootView.findViewById(R.id.note_title);
            titleTextEdit.setText(mNote.getTitle());
            titleTextEdit.setBackgroundColor(currentNoteColor);
            titleTextEdit.setTextColor(currentTextColor);
            titleTextEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, currentTextSize+10);
            titleTextEdit.setTypeface(currentFont);

            textTextView = rootView.findViewById(R.id.note_text_desc);
            textTextView.setVisibility(View.GONE);

            textTextEdit = rootView.findViewById(R.id.note_text);
            textTextEdit.setText(mNote.getText());
            textTextEdit.setBackgroundColor(currentNoteColor);
            textTextEdit.setTextColor(currentTextColor);
            textTextEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, currentTextSize);
            textTextEdit.setTypeface(currentFont);

            mNoteColor = rootView.findViewById(R.id.note_color_button);
            mTextColor = rootView.findViewById(R.id.text_color_button);

            mTextAlign = rootView.findViewById(R.id.text_align_button);


            mSizeButton = rootView.findViewById(R.id.text_size_button);
            mSizeButton.setText((CharSequence)String.valueOf(currentTextSize));
            mSizeUpButton = rootView.findViewById(R.id.text_size_up_button);

            mSizeDownButton = rootView.findViewById(R.id.text_size_down_button);

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
                mNote.setNoteColor(mNote.getNoteColor());
                mNote.setTextColor(mNote.getTextColor());
                mNote.setTextAlign(mNote.getTextAlign());
                mNote.setTextSize(mNote.getTextSize());
                mNote.setFont(mNote.getFont());
            });

            mSaveButton = rootView.findViewById(R.id.save_button);
            mSaveButton.setOnClickListener( view -> {
                // Replace list with details


                //replace details with list
                Navigation.findNavController(rootView).navigateUp();
                //Navigation.findNavController(rootView).navigate(R.id.list_fragment);
            });

            mNoteColor.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openNoteColorPickerDialogue();
                        }
                    }
                    );
            mTextColor.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openTextColorPickerDialogue();
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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState )
    {
        switch(currentTextAlign){
            case 0:
            {
                Drawable myDrawable = ContextCompat.getDrawable(view.getContext(), R.drawable.left_align);
                //Drawable myDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.left_align, null);
                mTextAlign.setCompoundDrawablesWithIntrinsicBounds(myDrawable,null, null, null);
                textTextEdit.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                titleTextEdit.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                textTextEdit.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                break;
            }
            case 1:
            {
                Drawable myDrawable = ContextCompat.getDrawable(view.getContext(), R.drawable.center_align);
                //Drawable myDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.center_align, null);
                mTextAlign.setCompoundDrawablesWithIntrinsicBounds(myDrawable,null, null, null);
                textTextEdit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                titleTextEdit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textTextEdit.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                break;
            }
            case 2:
            {
                Drawable myDrawable = ContextCompat.getDrawable(view.getContext(), R.drawable.right_align);
                //Drawable myDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.right_align, null);
                mTextAlign.setCompoundDrawablesWithIntrinsicBounds(myDrawable,null, null, null);
                textTextEdit.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                titleTextEdit.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                textTextEdit.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                break;
            }
        }

        mTextAlign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable myDrawable;
                if(currentTextAlign == 0)
                {
                    currentTextAlign = 1;
                    myDrawable = ContextCompat.getDrawable(v.getContext(), R.drawable.center_align);
                    mTextAlign.setCompoundDrawablesWithIntrinsicBounds(myDrawable,null, null, null);
                    textTextEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                    textTextEdit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    titleTextEdit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textTextEdit.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                }
                else if(currentTextAlign == 1)
                {
                    currentTextAlign = 2;
                    myDrawable = ContextCompat.getDrawable(v.getContext(), R.drawable.right_align);
                    textTextEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                    mTextAlign.setCompoundDrawablesWithIntrinsicBounds(myDrawable,null, null, null);
                    textTextEdit.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    titleTextEdit.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    textTextEdit.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                }
                else if(currentTextAlign == 2)
                {
                    currentTextAlign = 0;
                    myDrawable = ContextCompat.getDrawable(v.getContext(), R.drawable.left_align);
                    textTextEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                    mTextAlign.setCompoundDrawablesWithIntrinsicBounds(myDrawable,null, null, null);
                    textTextEdit.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    titleTextEdit.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    textTextEdit.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                }
            }
        });

        mSizeUpButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(currentTextSize >= 98)
                {
                    currentTextSize = 8;
                }
                else
                {
                    currentTextSize+=1;
                }
                textTextEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, currentTextSize);
                titleTextEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, currentTextSize+10);
                mSizeButton.setText((CharSequence)String.valueOf(currentTextSize));
            }
        });

        mSizeDownButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (currentTextSize <= 8) {
                    currentTextSize = 98;
                } else {
                    currentTextSize -= 1;
                }
                textTextEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, currentTextSize);
                titleTextEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, currentTextSize + 10);
                mSizeButton.setText((CharSequence)String.valueOf(currentTextSize));
            }
        });

        mSizeButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                currentTextSize+=10;
                if (currentTextSize >= 98)
                {
                    currentTextSize = 8;
                }
                textTextEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, currentTextSize);
                titleTextEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, currentTextSize + 10);
                mSizeButton.setText((CharSequence)String.valueOf(currentTextSize));
            }
        });
    }
    @Override
    public void onStop()
    {
        super.onStop();
        if(mNote != null)
        {
            mNote.setTitle(titleTextEdit.getText().toString());
            mNote.setText(textTextEdit.getText().toString());
            mNote.setNoteColor(currentNoteColor);
            mNote.setTextColor(currentTextColor);
            mNote.setTextAlign(currentTextAlign);
            mNote.setTextSize(currentTextSize);
            mNoteListViewModel.addNote(mNote);
        }
    }


    public void openNoteColorPickerDialogue()
    {
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this.getContext(), currentNoteColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        titleTextEdit.setBackgroundColor(mNote.getNoteColor());
                        textTextEdit.setBackgroundColor(mNote.getNoteColor());
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        currentNoteColor = color;
                        titleTextEdit.setBackgroundColor(currentNoteColor);
                        textTextEdit.setBackgroundColor(currentNoteColor);
                    }
                });
        colorPickerDialogue.show();
    }

    public void openTextColorPickerDialogue()
    {
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this.getContext(), currentTextColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        titleTextEdit.setTextColor(mNote.getTextColor());
                        textTextEdit.setTextColor(mNote.getTextColor());
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        currentTextColor = color;
                        titleTextEdit.setTextColor(currentTextColor);
                        textTextEdit.setTextColor(currentTextColor);
                    }
                });
        colorPickerDialogue.show();
    }
}