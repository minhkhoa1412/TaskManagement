package com.minhkhoa.taskmanagement.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.activity.CardDetailsActivity;
import com.minhkhoa.taskmanagement.adapter.TaskAdapter;
import com.minhkhoa.taskmanagement.model.Activity;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.model.Task;
import com.minhkhoa.taskmanagement.model.User;
import com.minhkhoa.taskmanagement.util.Constant;

import java.util.ArrayList;
import java.util.Calendar;

public class CardTaskFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    FirebaseUser user_firebase = FirebaseAuth.getInstance().getCurrentUser();

    TaskAdapter adapter;
    Card card;
    User user;
    String taskKey;
    String activityKey;
    ListView lvTask;
    View view;
    ImageButton btnAddTask;
    EditText edtAddTask;
    ArrayList<Task> taskArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_card_task, container, false);
        addControls();
        init();
        getDataFormFireBase();
        addEvents();
        return view;
    }

    private void getDataFormFireBase() {
        databaseReference.child("Task").child(card.getCardID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskArrayList.clear();
                for(DataSnapshot dataSS: dataSnapshot.getChildren()){
                    Task task = dataSS.getValue(Task.class);
                    taskArrayList.add(0,task);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child("User").child(user_firebase.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addEvents() {
        edtAddTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().matches("")) {
                    btnAddTask.setVisibility(View.GONE);
                } else {
                    btnAddTask.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskKey = databaseReference.push().getKey();
                Task task = new Task();
                task.setTaskID(taskKey);
                task.setTaskStatus(false);
                task.setTaskName(edtAddTask.getText().toString());
                databaseReference.child("Task").child(card.getCardID()).child(taskKey).setValue(task);

                activityKey = databaseReference.push().getKey();
                Activity activity = new Activity();
                activity.setActivityID(activityKey);
                activity.setUserName(user.getUserName());
                activity.setUserAvata(user.getUserAvata());
                activity.setCardID(card.getCardID());
                activity.setTaskName(getString(R.string.added) + " \'" + edtAddTask.getText().toString() + "\' " + getString(R.string.to_this_card));
                activity.setActivityTime(Calendar.getInstance().getTime());
                databaseReference.child("Activity").child(activityKey).setValue(activity);

                edtAddTask.setText("");
            }
        });
    }

    private void init() {
        card = (Card) getArguments().getSerializable(Constant.CARD_FRAGMENTS);
        btnAddTask.setVisibility(View.GONE);
        taskArrayList = new ArrayList<>();

        adapter = new TaskAdapter(taskArrayList,getActivity(),card.getCardID(),user);
        lvTask.setAdapter(adapter);
    }

    private void reloadFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(CardTaskFragment.this).attach(CardTaskFragment.this).commit();
    }

    private void addControls() {
        edtAddTask = view.findViewById(R.id.edittext_addtask);
        btnAddTask = view.findViewById(R.id.button_addtask);
        lvTask = view.findViewById(R.id.listview_task);
    }
}
