package com.example.watertracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private DrinkRecordsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        ExpandableListView listView = findViewById(R.id.expandableListView);
        viewModel = new ViewModelProvider(this).get(DrinkRecordsViewModel.class);
        DrinkRecordsAdapter adapter = new DrinkRecordsAdapter(this,viewModel);
        listView.setAdapter(adapter);
        viewModel.getAllDrinkRecords().observe(this, new Observer<List<DrinkRecord>>() {
            @Override
            public void onChanged(List<DrinkRecord> drinkRecords) {
                List<String> uniqueDates = new ArrayList<>();
                for (DrinkRecord record : drinkRecords) {
                    if (!uniqueDates.contains(record.getDate())) {
                        uniqueDates.add(record.getDate());
                    }
                }
                List<List<DrinkRecord>> list = new ArrayList<>();
                if ((drinkRecords != null) && (uniqueDates != null)) {
                    List<DrinkRecord> tempList = new ArrayList<>();
                    for (int i = 0; i < uniqueDates.size(); i++) {
                        tempList.clear();
                        for (DrinkRecord record : drinkRecords) {
                            if (record.getDate().equals(uniqueDates.get(i))) {
                                tempList.add(record);
                            }
                        }
                        list.add(new ArrayList<>());
                        list.get(i).addAll(tempList);

                    }
                }
                adapter.setData(uniqueDates, list);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                startActivityForResult(intent, AddRecordActivity.ADD_RECORD_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddRecordActivity.ADD_RECORD_REQUEST && resultCode == RESULT_OK) {
            String date = data.getStringExtra(AddRecordActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddRecordActivity.EXTRA_TIME);
            float amount = data.getFloatExtra(AddRecordActivity.EXTRA_AMOUNT, 0.0f);
            viewModel.insert(new DrinkRecord(amount, date, time));
            Toast.makeText(this, "Запись успешно добавлена!", Toast.LENGTH_SHORT).show();
        } else if(requestCode == AddRecordActivity.CHANGE_RECORD_REQUEST && resultCode == RESULT_OK){
            String date = data.getStringExtra(AddRecordActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddRecordActivity.EXTRA_TIME);
            long id = data.getLongExtra(AddRecordActivity.EXTRA_ID,0);
            float amount = data.getFloatExtra(AddRecordActivity.EXTRA_AMOUNT, 0.0f);
            DrinkRecord record = new DrinkRecord(amount, date, time);
            record.setId(id);
            viewModel.update(record);
            Toast.makeText(this, "Запись успешно изменена!", Toast.LENGTH_SHORT).show();
        }
    }
}

