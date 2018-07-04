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
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.model.Task;
import com.minhkhoa.taskmanagement.util.SimpleDayFormat;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by thaim on 13/09/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtTaskDone, txtTaskTotal, txtDeadline, txtCountMember;
        LinearLayout llnTag, llnInfomation, llnDeadline, llnUser, llnTask;
        CircleImageView imgAvataOne, imgAvataTwo, imgAvataThree;
        ImageView imgTagOne, imgTagTwo, imgTagTheree, imgTagFour, imgTagFive, imgDesciption;

        ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.textview_title);
            imgDesciption = itemView.findViewById(R.id.image_des);
            llnInfomation = itemView.findViewById(R.id.linear_infomation);

            llnTask = itemView.findViewById(R.id.linear_task);
            txtTaskDone = itemView.findViewById(R.id.textview_task_complete);
            txtTaskTotal = itemView.findViewById(R.id.textview_task_total);

            llnDeadline = itemView.findViewById(R.id.linear_deadline);
            txtDeadline = itemView.findViewById(R.id.textview_deadline);

            llnUser = itemView.findViewById(R.id.linear_user);
            txtCountMember = itemView.findViewById(R.id.textview_count_card);
            imgAvataOne = itemView.findViewById(R.id.image_avata_card);
            imgAvataTwo = itemView.findViewById(R.id.image_avata_card1);
            imgAvataThree = itemView.findViewById(R.id.image_avata_card2);

            llnTag = itemView.findViewById(R.id.linear_tag);
            imgTagOne = itemView.findViewById(R.id.tag1_item);
            imgTagTwo = itemView.findViewById(R.id.tag2_item);
            imgTagTheree = itemView.findViewById(R.id.tag3_item);
            imgTagFour = itemView.findViewById(R.id.tag4_item);
            imgTagFive = itemView.findViewById(R.id.tag5_item);

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
    private ArrayList<Task> taskArrayList = new ArrayList<>();
    private int count;
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
        viewHolder.txtTitle.setText(cardArrayList.get(i).getCardName());
        if (!cardArrayList.get(i).getCardDescription().matches("")) {
            viewHolder.llnInfomation.setVisibility(View.VISIBLE);
            if (!cardArrayList.get(i).getCardDescription().matches("")) {
                viewHolder.imgDesciption.setVisibility(View.VISIBLE);
            }
        }

        if (cardArrayList.get(i).getCardDeadline() != null) {
            if (viewHolder.llnInfomation.getVisibility() == View.INVISIBLE) {
                viewHolder.llnInfomation.setVisibility(View.VISIBLE);
            }
            viewHolder.llnDeadline.setVisibility(View.VISIBLE);
            viewHolder.txtDeadline.setText(SimpleDayFormat.formatDate(cardArrayList.get(i).getCardDeadline()));
        }

        if (cardArrayList.get(i).getUserArrayList().size() > 1) {
            viewHolder.llnUser.setVisibility(View.VISIBLE);
            if (cardArrayList.get(i).getUserArrayList().size() == 2) {
                viewHolder.imgAvataOne.setVisibility(View.VISIBLE);
                Picasso.get().load(cardArrayList.get(i).getUserArrayList().get(1).getUserAvata()).into(viewHolder.imgAvataOne);
            } else if (cardArrayList.get(i).getUserArrayList().size() == 3) {
                viewHolder.imgAvataOne.setVisibility(View.VISIBLE);
                Picasso.get().load(cardArrayList.get(i).getUserArrayList().get(1).getUserAvata()).into(viewHolder.imgAvataOne);
                viewHolder.imgAvataTwo.setVisibility(View.VISIBLE);
                Picasso.get().load(cardArrayList.get(i).getUserArrayList().get(2).getUserAvata()).into(viewHolder.imgAvataTwo);
            } else if (cardArrayList.get(i).getUserArrayList().size() == 4) {
                viewHolder.imgAvataOne.setVisibility(View.VISIBLE);
                Picasso.get().load(cardArrayList.get(i).getUserArrayList().get(1).getUserAvata()).into(viewHolder.imgAvataOne);
                viewHolder.imgAvataTwo.setVisibility(View.VISIBLE);
                Picasso.get().load(cardArrayList.get(i).getUserArrayList().get(2).getUserAvata()).into(viewHolder.imgAvataTwo);
                viewHolder.imgAvataThree.setVisibility(View.VISIBLE);
                Picasso.get().load(cardArrayList.get(i).getUserArrayList().get(3).getUserAvata()).into(viewHolder.imgAvataThree);
            } else {
                viewHolder.imgAvataOne.setVisibility(View.VISIBLE);
                Picasso.get().load(cardArrayList.get(i).getUserArrayList().get(1).getUserAvata()).into(viewHolder.imgAvataOne);
                viewHolder.imgAvataTwo.setVisibility(View.VISIBLE);
                Picasso.get().load(cardArrayList.get(i).getUserArrayList().get(2).getUserAvata()).into(viewHolder.imgAvataTwo);
                viewHolder.imgAvataThree.setVisibility(View.VISIBLE);
                Picasso.get().load(cardArrayList.get(i).getUserArrayList().get(3).getUserAvata()).into(viewHolder.imgAvataThree);
                viewHolder.txtCountMember.setVisibility(View.VISIBLE);
                viewHolder.txtCountMember.setText("+" + String.valueOf(cardArrayList.get(i).getUserArrayList().size() - 4   ));
            }
        }

        if (cardArrayList.get(i).getCardTag() != null) {
            viewHolder.llnTag.setVisibility(View.VISIBLE);
            for (int j = 0; j < cardArrayList.get(i).getCardTag().size(); j++) {
                if (cardArrayList.get(i).getCardTag().get(j) == 0) {
                    viewHolder.imgTagOne.setVisibility(View.VISIBLE);
                }
                if (cardArrayList.get(i).getCardTag().get(j) == 1) {
                    viewHolder.imgTagTwo.setVisibility(View.VISIBLE);
                }
                if (cardArrayList.get(i).getCardTag().get(j) == 2) {
                    viewHolder.imgTagTheree.setVisibility(View.VISIBLE);
                }
                if (cardArrayList.get(i).getCardTag().get(j) == 3) {
                    viewHolder.imgTagFour.setVisibility(View.VISIBLE);
                }
                if (cardArrayList.get(i).getCardTag().get(j) == 4) {
                    viewHolder.imgTagFive.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}