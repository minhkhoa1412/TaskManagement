package com.minhkhoa.taskmanagement.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.adapter.ActivityAdapter;
import com.minhkhoa.taskmanagement.model.Activity;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.util.Constant;

import java.util.ArrayList;

public class CardActivityFragment extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    View view;
    ActivityAdapter adapter;
    ArrayList<Activity> activityArrayList;
    ListView lvActivity;
    Card card;
    String boardID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_card_activity,container,false);
        addControls();
        init();
        getDataFormFirebase();
        return view;
    }

    private void getDataFormFirebase() {
        databaseReference.child("Activity").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Activity activity = dataSnapshot.getValue(Activity.class);
                if(activity.getCardID().equals(card.getCardID())){
                    activityArrayList.add(0,activity);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        card = (Card) getArguments().getSerializable(Constant.CARD_FRAGMENTS);
        boardID = getArguments().getString(Constant.BOARD_ID_FOR_CHAT_CARD);

        activityArrayList = new ArrayList<>();
        adapter = new ActivityAdapter(getContext(),activityArrayList);
        lvActivity.setAdapter(adapter);
    }

    private void addControls() {
        lvActivity = view.findViewById(R.id.listview_activity);
    }
}
