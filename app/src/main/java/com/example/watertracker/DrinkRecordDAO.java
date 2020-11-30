package com.example.watertracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DrinkRecordDAO {
    @Insert
    void insert(DrinkRecord drinkRecord);
    @Update
    void update(DrinkRecord record);
    @Query("DELETE FROM drink_records WHERE id=:id")
    void delete(long id);
    @Query("SELECT DISTINCT date FROM drink_records ORDER BY date DESC")
    LiveData<List<String>> getUniqueDates();
    @Query("SELECT * FROM drink_records ORDER BY date DESC")
    LiveData<List<DrinkRecord>> getAllRecords();


}
