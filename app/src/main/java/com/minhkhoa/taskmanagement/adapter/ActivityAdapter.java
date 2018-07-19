package com.minhkhoa.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.model.Activity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityAdapter extends BaseAdapter {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat stf = new SimpleDateFormat("HH:mm");

    Context context;
    ArrayList<Activity> activityArrayList;
    LayoutInflater inflater;

    public ActivityAdapter(Context context, ArrayList<Activity> activityArrayList) {
        this.context = context;
        this.activityArrayList = activityArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return activityArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return activityArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position == 0){
            convertView = inflater.inflate(R.layout.item_activity_first,null);
        } else {
            convertView = inflater.inflate(R.layout.item_activity,null);
        }

        CircleImageView imgAvata = convertView.findViewById(R.id.image_avata_member);
        TextView txtName = convertView.findViewById(R.id.textview_name_member);
        TextView txtActivity = convertView.findViewById(R.id.textview_activity);
        TextView txtTime = convertView.findViewById(R.id.textview_time);

        Picasso.get().load(activityArrayList.get(position).getUserAvata()).resize(300,300).centerCrop().into(imgAvata);
        txtName.setText(activityArrayList.get(position).getUserName());
        txtActivity.setText(activityArrayList.get(position).getTaskName());
        String time = stf.format(activityArrayList.get(position).getActivityTime());
        String date = sdf.format(activityArrayList.get(position).getActivityTime());
        txtTime.setText(time + " " + date);

        return convertView;
    }
}
