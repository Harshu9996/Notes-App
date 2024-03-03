package com.example.internshalaassignment.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.internshalaassignment.Data.DAO.DAO;
import com.example.internshalaassignment.Data.DataModels.Note;
import com.example.internshalaassignment.Data.NotesDatabase;
import com.example.internshalaassignment.R;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditNoteFragment extends Fragment {

    private static final String TAG = "EditNoteFragment";
    EditText editNoteTitle,editNoteDescription;
    ImageView saveBtn;
    SharedPreferences pref;

    Note noteToEdit;

    public EditNoteFragment() {
        // Required empty public constructor
    }

    public static EditNoteFragment newInstance(String param1, String param2) {
        EditNoteFragment fragment = new EditNoteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getActivity().getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE);
        assert getArguments() != null;
        noteToEdit = (Note) getArguments().getSerializable(getString(R.string.notePassingId));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);


        editNoteTitle = (EditText) view.findViewById(R.id.editNoteTitle);
        editNoteDescription = (EditText) view.findViewById(R.id.editNoteDescription);
        saveBtn = (ImageView) view.findViewById(R.id.editSaveNoteBtn);

        editNoteTitle.setText(noteToEdit.title.toString());
        editNoteDescription.setText(noteToEdit.description.toString());

        NotesDatabase db = NotesDatabase.getInstance(getActivity());
        DAO notesDao = (DAO) db.notesDao();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteToEdit.title = editNoteTitle.getText().toString();
                noteToEdit.description = editNoteDescription.getText().toString();
                notesDao.updateNote(noteToEdit);

                Snackbar.make(view, getString(R.string.note_edited_message), Snackbar.LENGTH_LONG).show();
            }
        });




        return view;
    }
}