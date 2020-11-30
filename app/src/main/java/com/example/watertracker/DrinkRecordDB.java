package com.example.watertracker;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {DrinkRecord.class},version = 1,exportSchema = false)
public abstract class  DrinkRecordDB extends RoomDatabase {

    private static DrinkRecordDB instance;

    public abstract DrinkRecordDAO drinkRecordDAO();

    public static DrinkRecordDB getInstance(Context context){
        if(instance == null){
            synchronized (DrinkRecordDB.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext()
                            , DrinkRecordDB.class
                            ,"drink_records_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return  instance;
    }

}
