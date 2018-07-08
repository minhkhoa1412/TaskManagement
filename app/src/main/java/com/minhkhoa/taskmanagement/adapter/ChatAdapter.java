package com.minhkhoa.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.model.Chat;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends BaseAdapter {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat stf = new SimpleDateFormat("HH:mm");

    Context context;
    ArrayList<Chat> chatArrayList;
    LayoutInflater inflater;

    public ChatAdapter(Context context, ArrayList<Chat> chatArrayList) {
        this.context = context;
        this.chatArrayList = chatArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return chatArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_chat,null);

        CircleImageView imgAvata = convertView.findViewById(R.id.image_avata);
        TextView txtName = convertView.findViewById(R.id.textview_name_chat);
        TextView txtTime = convertView.findViewById(R.id.textview_datetime_chat);
        TextView txtContent = convertView.findViewById(R.id.textview_content_chat);

        Picasso.get().load(chatArrayList.get(position).getUserAvata()).fit().centerCrop().into(imgAvata);
        txtName.setText(chatArrayList.get(position).getUserName());
        txtContent.setText(chatArrayList.get(position).getChatContent());
        String time = stf.format(chatArrayList.get(position).getChatTime());
        String date = sdf.format(chatArrayList.get(position).getChatTime());
        txtTime.setText(time + " " + date);

        return convertView;
    }
}
