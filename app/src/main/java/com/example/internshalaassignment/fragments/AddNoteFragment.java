package com.example.internshalaassignment.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.internshalaassignment.Data.DAO.DAO;
import com.example.internshalaassignment.Data.DataModels.Note;
import com.example.internshalaassignment.Data.NotesDatabase;
import com.example.internshalaassignment.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNoteFragment extends Fragment {


    FloatingActionButton saveBtn;
    EditText etTitle;
    EditText etDescription;
    SharedPreferences pref;

    public AddNoteFragment() {
        // Required empty public constructor
    }


    public static AddNoteFragment newInstance(String param1, String param2) {
        AddNoteFragment fragment = new AddNoteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getActivity().getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);


        NotesDatabase db = NotesDatabase.getInstance(getActivity());
        DAO notesDao = (DAO) db.notesDao();
        saveBtn = view.findViewById(R.id.saveNoteBtn);
        etTitle = view.findViewById(R.id.editTextTitle);
        etDescription = view.findViewById(R.id.editTextDescription);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long uId = generateUid();
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                if(title.trim().length()==0){
                    //Show a Toast message
                    Toast.makeText(getActivity(), getString(R.string.note_title_required_message), Toast.LENGTH_SHORT).show();
                }else{

                    //Save the note to database
                    String emailId = pref.getString(getString(R.string.current_user_emailId),"Default");
                    Note newNote = new Note(uId,emailId,title,description);
                    notesDao.insert(newNote);
                    Toast.makeText(getActivity(), getString(R.string.note_saved_message), Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }

    private long generateUid(){
        return System.currentTimeMillis();
    }
}