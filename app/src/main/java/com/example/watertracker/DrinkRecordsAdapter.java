package com.example.watertracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DrinkRecordsAdapter extends BaseExpandableListAdapter {
public static final int CHANGE_RECORD_REQUEST = 2;
    private Context context;
    private List<String> categories = new ArrayList<>();
    private DrinkRecordsViewModel viewModel;
    private List<List<DrinkRecord>> drinkRecords = new ArrayList<>();
    public DrinkRecordsAdapter(Context context,DrinkRecordsViewModel viewModel){
        this.context = context;
        this.viewModel = viewModel;
    }

    @Override
    public int getGroupCount() {
        return  categories == null ? 0: categories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return drinkRecords.get(groupPosition) == null ? 0 : drinkRecords.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return drinkRecords.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_category,null);
        }
        TextView categoryDate = convertView.findViewById(R.id.list_category_dateText);
        categoryDate.setText(categories.get(groupPosition));
        TextView categoryTotal = convertView.findViewById(R.id.list_category_total);
        float total = 0.0f;
        for(DrinkRecord i:drinkRecords.get(groupPosition)){
            total += i.getAmount();
        }
        categoryTotal.setText(Float.toString(total));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item,null);
        }
        TextView amount = convertView.findViewById(R.id.list_item_amount);
        TextView time = convertView.findViewById(R.id.list_item_time);
        ImageButton updateBtn = convertView.findViewById(R.id.list_item_updateButton);
        ImageButton deleteBtn = convertView.findViewById(R.id.list_item_deleteButton);

        DrinkRecord record = (DrinkRecord) getChild(groupPosition,childPosition);
        amount.setText(Float.toString(record.getAmount()));
        time.setText(record.getTime());
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddRecordActivity.class);
                intent.putExtra(AddRecordActivity.EXTRA_AMOUNT,String.valueOf(record.getAmount()));
                intent.putExtra(AddRecordActivity.EXTRA_TIME,record.getTime());
                intent.putExtra(AddRecordActivity.EXTRA_DATE,record.getDate());
                intent.putExtra(AddRecordActivity.EXTRA_ID,record.getId());
                intent.putExtra(AddRecordActivity.EXTRA_CHANGE_REQUEST_CODE,AddRecordActivity.CHANGE_RECORD_REQUEST);
                ((Activity)context).startActivityForResult(intent,CHANGE_RECORD_REQUEST);

            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Удаление")
                        .setMessage("Вы действительно хотите удалить запись?");
                builder.setPositiveButton("Удалить",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.delete(record.getId());
                    }
                });
                builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void setData(List<String> dates, List<List<DrinkRecord>> allRecords){
        this.categories.clear();
        this.drinkRecords.clear();
        this.categories.addAll(dates);
        this.drinkRecords.addAll(allRecords);
        notifyDataSetChanged();
    }

}
