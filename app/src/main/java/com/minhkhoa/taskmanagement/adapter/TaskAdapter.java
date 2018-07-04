package com.minhkhoa.taskmanagement.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.model.Task;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    ArrayList<Task> taskArrayList;
    LayoutInflater inflater;
    Context context;
    String cardID;

    public TaskAdapter(ArrayList<Task> taskArrayList, Context context, String cardID) {
        this.taskArrayList = taskArrayList;
        this.context = context;
        this.cardID = cardID;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return taskArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_task,null);

        CheckBox ckbTask = convertView.findViewById(R.id.checkbox_task);
        final TextView txtTask = convertView.findViewById(R.id.textview_task);

        if(taskArrayList.get(position).getTaskStatus()){
            ckbTask.setChecked(true);
            txtTask.setPaintFlags(txtTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            ckbTask.setChecked(false);
        }

        ckbTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    txtTask.setPaintFlags(txtTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
                    databaseReference.child("Task").child(cardID).child(taskArrayList.get(position).getTaskID()).child("taskStatus").setValue(true);
                } else {
                    txtTask.setPaintFlags(0);
                    databaseReference.child("Task").child(cardID).child(taskArrayList.get(position).getTaskID()).child("taskStatus").setValue(false);
                }
            }
        });

        txtTask.setText(taskArrayList.get(position).getTaskName());

        return convertView;
    }
}
