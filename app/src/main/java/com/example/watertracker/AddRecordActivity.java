package com.example.watertracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRecordActivity extends AppCompatActivity {
    public static final int ADD_RECORD_REQUEST = 1;
    public static final int CHANGE_RECORD_REQUEST = 2;
    public static final String EXTRA_AMOUNT = "com.example.watertracker.EXTRA_AMOUNT";
    public static final String EXTRA_DATE = "com.example.watertracker.EXTRA_DATE";
    public static final String EXTRA_TIME = "com.example.watertracker.EXTRA_TIME";
    public static final String EXTRA_ID = "com.example.watertracker.EXTRA_ID";
    public static final String EXTRA_CHANGE_REQUEST_CODE = "com.example.watertracker.EXTRA_CHANGE_REQUEST_CODE";
    public static final String LOG = "AddRecordDebug";
    private long recordId;
    EditText amountEditText;
    EditText dateEditText;
    EditText timeEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        setTitle("Добавить запись");
        Button saveButton = findViewById(R.id.save_button);
        amountEditText = findViewById(R.id.amount_edit_text);
        dateEditText = findViewById(R.id.date_edit_text);
        timeEditText = findViewById(R.id.time_edit_text);
        String time = "";
        String date = "";
        String amount = "";
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            date = bundle.getString(EXTRA_DATE);
            time = bundle.getString(EXTRA_TIME);
            amount = bundle.getString(EXTRA_AMOUNT).replace(".",",") + " Л";
            setTitle("Изменить запись");
            recordId = bundle.getLong(EXTRA_ID,0);
            saveButton.setText("Изменить");
        } else {
            time = new SimpleDateFormat("HH:mm").format(new Date());
            date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        }
        dateEditText.setText(date);
        timeEditText.setText(time);
        amountEditText.setText(amount);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecord();
            }
        });
    }

    private void saveRecord() {
        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();
        float amount = Float.parseFloat(amountEditText.getText().toString().replace("Л", "").replace(",", "."));
        if (date.matches("[0-3][0-9]\\D[0-1][0-9]\\D[2][0][0-9][0-9]")) {
            String[] dateDivided = date.split("\\D");
            if ((Integer.parseInt(dateDivided[0]) <= 31) && (Integer.parseInt(dateDivided[1]) <= 12)) {
                String[] timeDivided = time.split("\\D");
                if ((Integer.parseInt(timeDivided[0]) <= 23) && (Integer.parseInt(timeDivided[1]) <= 59)) {
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_AMOUNT, amount);
                    intent.putExtra(EXTRA_DATE, date);
                    intent.putExtra(EXTRA_TIME, time);
                    intent.putExtra(EXTRA_ID,recordId);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        } else {
            Toast.makeText(this, "Проверьте правильность написания даты или времени!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

}