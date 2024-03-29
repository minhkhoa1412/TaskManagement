package com.minhkhoa.taskmanagement.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.model.Board;
import com.minhkhoa.taskmanagement.model.User;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {

    Context context;
    ArrayList<Board> boardArrayList;
    LayoutInflater inflater;

    public BoardAdapter(Context context, ArrayList<Board> boardArrayList) {
        this.context = context;
        this.boardArrayList = boardArrayList;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder {
        TextView txtName;
        ImageView imgSmall;
        ImageView imgLarge;
        View viewGradient;
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
            convertView = inflater.inflate(R.layout.item_board, null);
            viewHolder = new ViewHolder();
            viewHolder.txtName = convertView.findViewById(R.id.textview_board);
            viewHolder.imgSmall = convertView.findViewById(R.id.image_small_board);
            viewHolder.imgLarge = convertView.findViewById(R.id.image_large_board);
            viewHolder.viewGradient = convertView.findViewById(R.id.view_gradient);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setText(boardArrayList.get(position).getBoardName());
        Picasso.get().load(boardArrayList.get(position).getBoardImage()).fit().centerCrop().into(viewHolder.imgSmall);
        Picasso.get().load(boardArrayList.get(position).getBoardImage()).fit().centerCrop().into(viewHolder.imgLarge);

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
