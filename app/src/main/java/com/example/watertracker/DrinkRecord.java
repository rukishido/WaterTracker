package com.example.watertracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "drink_records")
public class DrinkRecord {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private float amount;

    private String date;

    private String time;

    public DrinkRecord(float amount, String date,String time) {
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public float getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
    public String getTime(){
        return time;
    }
}
