package com.minhkhoa.taskmanagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.model.User;
import com.minhkhoa.taskmanagement.util.SimpleDayFormat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by thaim on 13/09/2017.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEmail;
        CircleImageView imgAvata;

        ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.textview_name_member);
            txtEmail = itemView.findViewById(R.id.textview_email_member);
            imgAvata = itemView.findViewById(R.id.image_avata_member);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_member, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    private ArrayList<User> userArrayList;
    private Context context;

    public MemberAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Picasso.get().load(userArrayList.get(i).getUserAvata()).into(viewHolder.imgAvata);
        viewHolder.txtName.setText(userArrayList.get(i).getUserName());
        viewHolder.txtEmail.setText(userArrayList.get(i).getUserEmail());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}