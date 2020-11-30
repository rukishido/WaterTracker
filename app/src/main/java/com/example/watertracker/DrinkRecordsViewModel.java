package com.example.watertracker;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DrinkRecordsViewModel extends AndroidViewModel {

    private DrinkRecordsRepository repository;
    private LiveData<List<String>> uniqueDates;
    private LiveData<List<DrinkRecord>> allDrinkRecords;

    public DrinkRecordsViewModel(@NonNull Application application) {
        super(application);
        repository = new DrinkRecordsRepository(application);
        allDrinkRecords = repository.getAllDrinkRecords();
        uniqueDates = repository.getUniqueDates();
    }

    public void insert(DrinkRecord drinkRecord){
        repository.insert(drinkRecord);
    }

    public void update(DrinkRecord drinkRecord){
        repository.update(drinkRecord);
    }

    public void delete(long id){
        repository.delete(id);
    }

    public LiveData<List<DrinkRecord>> getAllDrinkRecords(){
        return allDrinkRecords;
    }

    public LiveData<List<String>> getUniqueDates(){
        return uniqueDates;
    }
}
