package com.example.todoapp;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseClient {


   private Context mCtx;

   private static DatabaseClient mInstance;


   //app database object
    private AppDatabase appDatabase;

        private DatabaseClient (Context mCtx){

             // Creating appDatabase with room builder


            this.mCtx = mCtx;
        appDatabase = Room.databaseBuilder(mCtx,AppDatabase.class,"MyToDos").build();

        }
    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }




}
