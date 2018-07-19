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
import com.minhkhoa.taskmanagement.model.Activity;
import com.minhkhoa.taskmanagement.model.Task;
import com.minhkhoa.taskmanagement.model.User;

import java.util.ArrayList;
import java.util.Calendar;

public class TaskAdapter extends BaseAdapter {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    private ArrayList<Task> taskArrayList;
    private LayoutInflater inflater;
    private Context context;
    private String cardID;
    private User user;

    public TaskAdapter(ArrayList<Task> taskArrayList, Context context, String cardID, User user) {
        this.taskArrayList = taskArrayList;
        this.context = context;
        this.cardID = cardID;
        this.user = user;
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

                    String activityKey = databaseReference.push().getKey();
                    Activity activity = new Activity();
                    activity.setActivityID(activityKey);
                    activity.setUserName(user.getUserName());
                    activity.setUserAvata(user.getUserAvata());
                    activity.setCardID(cardID);
                    activity.setTaskName(context.getString(R.string.has_done) + " \'" + taskArrayList.get(position).getTaskName() + "\' " + context.getString(R.string.in_this_card));
                    activity.setActivityTime(Calendar.getInstance().getTime());
                    databaseReference.child("Activity").child(activityKey).setValue(activity);

                } else {
                    txtTask.setPaintFlags(0);
                    databaseReference.child("Task").child(cardID).child(taskArrayList.get(position).getTaskID()).child("taskStatus").setValue(false);

                    String activityKey = databaseReference.push().getKey();
                    Activity activity = new Activity();
                    activity.setActivityID(activityKey);
                    activity.setUserName(user.getUserName());
                    activity.setUserAvata(user.getUserAvata());
                    activity.setCardID(cardID);
                    activity.setTaskName(context.getString(R.string.not_complete) + " \'" + taskArrayList.get(position).getTaskName() + "\' " + context.getString(R.string.in_this_card));
                    activity.setActivityTime(Calendar.getInstance().getTime());
                    databaseReference.child("Activity").child(activityKey).setValue(activity);
                }
            }
        });

        txtTask.setText(taskArrayList.get(position).getTaskName());

        return convertView;
    }
}
