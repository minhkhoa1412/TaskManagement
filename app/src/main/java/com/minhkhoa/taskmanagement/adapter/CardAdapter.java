package com.minhkhoa.taskmanagement.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.model.Card;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by thaim on 13/09/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtTaskDone, txtTaskTotal, txtDeadline;
        LinearLayout llnTag, llnInfomation, llnDeadline;
        ImageView imgTagOne, imgTagTwo, imgTagTheree, imgTagFour, imgTagFive, imgDesciption;

        ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.textview_title);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    private ArrayList<Card> cardArrayList;
    private Context context;

    public CardAdapter(Context context, ArrayList<Card> cardArrayList) {
        this.context = context;
        this.cardArrayList = cardArrayList;
    }

    @Override
    public int getItemCount() {
        return cardArrayList.size();
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
//        noteViewHolder.txtThoiGian.setText(stf.format(congViecArrayList.get(i).getThoiGianBatDau()) + "-" + stf.format(congViecArrayList.get(i).getThoiGianKetThuc()));
        viewHolder.txtTitle.setText("abc");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}