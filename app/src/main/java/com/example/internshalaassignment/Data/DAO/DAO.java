package com.example.internshalaassignment.Data.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.internshalaassignment.Data.DataModels.Note;

import java.util.List;


@Dao
public interface DAO {
    @Query("SELECT * FROM note WHERE userId LIKE :userId")
    List<Note> getAll(String userId);

    @Query("SELECT * FROM note WHERE uid LIKE :uId")
    Note loadById(long uId);


    @Insert
    void insert(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void delete(Note note);
}