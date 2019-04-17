package com.example.todoapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {Task.class},version = 1)

public abstract class AppDatabase extends RoomDatabase{

    public abstract TaskDAO taskDAO();

}

