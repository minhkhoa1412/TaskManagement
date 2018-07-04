package com.minhkhoa.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberBoardAdapter extends BaseAdapter {
    Context context;
    ArrayList<User> userArrayList;
    LayoutInflater inflater;

    public MemberBoardAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_board_member,null);

        CircleImageView imgAvata = convertView.findViewById(R.id.image_avata_member);
        TextView txtName = convertView.findViewById(R.id.textview_name_member);
        TextView txtEmail = convertView.findViewById(R.id.textview_email_member);

        Picasso.get().load(userArrayList.get(position).getUserAvata()).into(imgAvata);
        txtName.setText(userArrayList.get(position).getUserName());
        txtEmail.setText(userArrayList.get(position).getUserEmail());

        return convertView;
    }
}
