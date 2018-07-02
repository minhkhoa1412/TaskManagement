package com.minhkhoa.taskmanagement.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.adapter.TaskAdapter;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.model.Task;
import com.minhkhoa.taskmanagement.util.Constant;

import java.util.ArrayList;

public class CardTaskFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    TaskAdapter adapter;

    Card card;
    String taskKey;
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
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Task")){
                    databaseReference.child("Task").child(card.getCardID()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Task task = dataSnapshot.getValue(Task.class);
                            taskArrayList.add(task);
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
                edtAddTask.setText("");
            }
        });
    }

    private void init() {
        card = (Card) getArguments().getSerializable(Constant.CARD_FRAGMENTS);
        btnAddTask.setVisibility(View.GONE);
        taskArrayList = new ArrayList<>();

        adapter = new TaskAdapter(taskArrayList,getActivity(),card.getCardID());
        lvTask.setAdapter(adapter);
    }

    private void addControls() {
        edtAddTask = view.findViewById(R.id.edittext_addtask);
        btnAddTask = view.findViewById(R.id.button_addtask);
        lvTask = view.findViewById(R.id.listview_task);
    }
}
