package com.minhkhoa.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberBoardAdapter extends BaseAdapter {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    Context context;
    ArrayList<User> userArrayList;
    LayoutInflater inflater;
    String userID;
    String boardID;
    String chatChannelID;
    int flag = 0;

    public MemberBoardAdapter(Context context, ArrayList<User> userArrayList,String userID,String boardID,String chatChannelID) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.userID = userID;
        this.boardID = boardID;
        this.chatChannelID = chatChannelID;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_board_member,null);

        CircleImageView imgAvata = convertView.findViewById(R.id.image_avata_member);
        TextView txtName = convertView.findViewById(R.id.textview_name_member);
        TextView txtEmail = convertView.findViewById(R.id.textview_email_member);
        ImageButton btnDelete = convertView.findViewById(R.id.button_delete);

        if(userArrayList.get(position).getUserID().equals(userID) && position == 0){
            flag = 1;
        }
        if(userArrayList.get(position).getUserID().equals(userID) && position !=0 && flag == 0){
            btnDelete.setVisibility(View.VISIBLE);
        }
        if(flag == 1 && position != 0 ){
            btnDelete.setVisibility(View.VISIBLE);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userArrayList.remove(position);
                databaseReference.child("Board").child(boardID).child("userArrayList").setValue(userArrayList);
                databaseReference.child("Chat").child(chatChannelID).child("userArrayList").setValue(userArrayList);
                notifyDataSetChanged();
            }
        });

        Picasso.get().load(userArrayList.get(position).getUserAvata()).into(imgAvata);
        txtName.setText(userArrayList.get(position).getUserName());
        txtEmail.setText(userArrayList.get(position).getUserEmail());

        return convertView;
    }
}
