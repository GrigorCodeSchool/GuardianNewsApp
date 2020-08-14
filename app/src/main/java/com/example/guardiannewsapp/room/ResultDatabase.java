package com.example.guardiannewsapp.room;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.guardiannewsapp.network.entities.Result;

@Database(entities = Result.class,version = 1)
public abstract class ResultDatabase extends RoomDatabase {
    private static final String DB_NAME = "metadatums";
    abstract ResultDao resultDao();

   private static ResultDatabase instance;

   public static synchronized ResultDatabase getInstance(Application application){
       if (instance == null){
           instance = Room.databaseBuilder(application, ResultDatabase.class,DB_NAME)
                   .fallbackToDestructiveMigration()
                   .build();
       }
       return instance;
   }
}
