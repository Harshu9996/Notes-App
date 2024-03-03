package com.example.internshalaassignment.Data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import  androidx.room.Database;

import com.example.internshalaassignment.Data.DAO.DAO;
import com.example.internshalaassignment.Data.DataModels.Note;
import com.example.internshalaassignment.R;

@androidx.room.Database(entities = {Note.class}, version = 2)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract DAO notesDao();
    private static final String DB_NAME = "notes_database";
    private static volatile NotesDatabase instance;


    //Apply singleton design pattern
    public static synchronized NotesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public NotesDatabase() {};

    private static NotesDatabase create(final Context context) {

        // Allow Room database queries to be conducted on Main thread as the data is not too heavy
        return Room.databaseBuilder(
                context,
                NotesDatabase.class,
                DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }




}
