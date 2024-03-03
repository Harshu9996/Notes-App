package com.example.internshalaassignment.Data.DataModels;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Note implements Serializable {
    @PrimaryKey
    public long uid;


    @ColumnInfo(name="userId")
    public String userId;
    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    public Note(long uid,String userId,String title,String description){
        this.uid = uid;
        this.userId = userId;
        this.title = title;
        this.description = description;

    }
}