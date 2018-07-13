package com.minhkhoa.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.activity.BoardInvitedActivity;
import com.minhkhoa.taskmanagement.model.Board;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BoardInvitedAdapter extends BaseAdapter {
    Context context;
    ArrayList<Board> boardArrayList;
    LayoutInflater inflater;

    public BoardInvitedAdapter(Context context, ArrayList<Board> boardArrayList) {
        this.context = context;
        this.boardArrayList = boardArrayList;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder {
        TextView txtName,txtNameUser;
        ImageView imgSmall;
        ImageView imgLarge;
        View viewGradient;
        CircleImageView imgAvata;
    }

    @Override
    public int getCount() {
        return boardArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return boardArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_board_invited, null);
            viewHolder = new ViewHolder();
            viewHolder.txtName = convertView.findViewById(R.id.textview_board);
            viewHolder.imgSmall = convertView.findViewById(R.id.image_small_board);
            viewHolder.imgLarge = convertView.findViewById(R.id.image_large_board);
            viewHolder.viewGradient = convertView.findViewById(R.id.view_gradient);
            viewHolder.txtNameUser = convertView.findViewById(R.id.textview_name_user_board);
            viewHolder.imgAvata = convertView.findViewById(R.id.avata_user);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //set
        viewHolder.txtName.setText(boardArrayList.get(position).getBoardName());
        Picasso.get().load(boardArrayList.get(position).getBoardImage()).fit().centerCrop().into(viewHolder.imgSmall);
        Picasso.get().load(boardArrayList.get(position).getBoardImage()).fit().centerCrop().into(viewHolder.imgLarge);
        for (int j = 0 ; j < boardArrayList.get(position).getUserArrayList().size() ; j ++){
            if(boardArrayList.get(position).getUserArrayList().get(j).getUserPermission() == 1){
                viewHolder.txtNameUser.setText(boardArrayList.get(position).getUserArrayList().get(j).getUserName());
                Picasso.get().load(boardArrayList.get(position).getUserArrayList().get(j).getUserAvata()).resize(300,300).centerCrop().into(viewHolder.imgAvata);
            }
        }

        if (position % 3 == 0) {
            viewHolder.viewGradient.setBackgroundResource(R.drawable.gradient_board_1);
        } else if (position % 3 == 1) {
            viewHolder.viewGradient.setBackgroundResource(R.drawable.gradient_board_2);
        } else {
            viewHolder.viewGradient.setBackgroundResource(R.drawable.gradient_board_3);
        }

        return convertView;
    }
}
