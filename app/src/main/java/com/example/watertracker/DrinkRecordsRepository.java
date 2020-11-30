package com.example.watertracker;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DrinkRecordsRepository {
    private DrinkRecordDAO drinkRecordDAO;
    private LiveData<List<String>> uniqueDates;
    private LiveData<List<DrinkRecord>> allDrinkRecords;

    public DrinkRecordsRepository(Application application){
        DrinkRecordDB db = DrinkRecordDB.getInstance(application);
        drinkRecordDAO = db.drinkRecordDAO();
        allDrinkRecords = drinkRecordDAO.getAllRecords();
        uniqueDates = drinkRecordDAO.getUniqueDates();
    }

    public void insert(DrinkRecord drinkRecord){
        new InsertDrinkRecordAsyncTask(drinkRecordDAO).execute(drinkRecord);
    }
    public void update(DrinkRecord drinkRecord){
        new UpdateDrinkRecordAsyncTask(drinkRecordDAO).execute(drinkRecord);
    }
    public void delete(long id){
        new DeleteDrinkRecordAsyncTask(drinkRecordDAO).execute(id);
    }
    public LiveData<List<DrinkRecord>> getAllDrinkRecords(){
        return allDrinkRecords;
    }
    public LiveData<List<String>> getUniqueDates(){return uniqueDates;}


    private static class InsertDrinkRecordAsyncTask extends AsyncTask<DrinkRecord,Void,Void>{
        DrinkRecordDAO dao;

        public InsertDrinkRecordAsyncTask(DrinkRecordDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DrinkRecord... drinkRecords) {
            dao.insert(drinkRecords[0]);
            return null;
        }
    }
    private static class UpdateDrinkRecordAsyncTask extends AsyncTask<DrinkRecord,Void,Void>{
        DrinkRecordDAO dao;

        public UpdateDrinkRecordAsyncTask(DrinkRecordDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DrinkRecord... drinkRecords) {
            dao.update(drinkRecords[0]);
            return null;
        }
    }
    private static class DeleteDrinkRecordAsyncTask extends AsyncTask<Long,Void,Void>{
        DrinkRecordDAO dao;

        public DeleteDrinkRecordAsyncTask(DrinkRecordDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Long... ids) {
            dao.delete(ids[0]);
            return null;
        }
    }
}
