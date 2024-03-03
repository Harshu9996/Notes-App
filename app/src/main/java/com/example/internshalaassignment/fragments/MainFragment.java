package com.example.internshalaassignment.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.internshalaassignment.Adapters.MainRecyclerAdapter;
import com.example.internshalaassignment.Data.DAO.DAO;
import com.example.internshalaassignment.Data.NotesDatabase;
import com.example.internshalaassignment.MainActivity;
import com.example.internshalaassignment.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    NavController navController;
    FloatingActionButton addButton;
    RecyclerView recyclerView;



    SharedPreferences pref;

    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize shared preferences
        pref = getActivity().getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        addButton = view.findViewById(R.id.addNoteBtn);
        recyclerView = view.findViewById(R.id.main_recycler_view);



        NotesDatabase db = NotesDatabase.getInstance(getActivity());
        DAO notesDao = (DAO) db.notesDao();


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_mainFragment_to_addNoteFragment);
            }
        });


        //Set adapter to recycler view
        MainRecyclerAdapter adapter = new MainRecyclerAdapter(notesDao,getActivity());
        adapter.setData(notesDao.getAll(pref.getString(getString(R.string.current_user_emailId),"Default")));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(adapter);





        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initialize navController
        navController = Navigation.findNavController(view);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

}