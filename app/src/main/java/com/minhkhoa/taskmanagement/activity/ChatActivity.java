package com.minhkhoa.taskmanagement.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.adapter.FragmentsAdapter;
import com.minhkhoa.taskmanagement.fragment.BoardChatFragment;
import com.minhkhoa.taskmanagement.fragment.BoardMemberFragment;
import com.minhkhoa.taskmanagement.fragment.CardChatFragment;
import com.minhkhoa.taskmanagement.fragment.CardDetailsFragment;
import com.minhkhoa.taskmanagement.fragment.CardTaskFragment;
import com.minhkhoa.taskmanagement.model.Board;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.model.ChatChannel;
import com.minhkhoa.taskmanagement.util.Constant;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    TabLayout tabLayout;
    ViewPager viewPager;

    FragmentsAdapter adapter;
    ArrayList<Fragment> fragmentArrayList;
    ArrayList<String> titleArrayList;

    String boardID;
    String boardName;
    String chatChannelID;
    Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addControls();
        getDataFormListActivity();
        init();
        prepareUI();
    }

    private void getDataFormListActivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constant.BUNDLE_LIST_TO_CHAT);
        board = (Board) bundle.getSerializable(Constant.BOARD_CHAT);
        chatChannelID = bundle.getString(Constant.CHAT_CHANNEL_ID);
        boardID = board.getBoardID();
        boardName = board.getBoardName();
    }

    private void prepareUI() {
        getSupportActionBar().setTitle(boardName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        fragmentArrayList = new ArrayList<>();
        titleArrayList = new ArrayList<>();

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.CHAT_FRAGMENTS,board);
        bundle.putString(Constant.CHAT_CHANNEL_ID,chatChannelID);
        BoardMemberFragment boardMemberFragment = new BoardMemberFragment();
        BoardChatFragment boardChatFragment = new BoardChatFragment();
        boardMemberFragment.setArguments(bundle);
        boardChatFragment.setArguments(bundle);

        fragmentArrayList.add(boardMemberFragment);
        fragmentArrayList.add(boardChatFragment);

        titleArrayList.add(getString(R.string.member));
        titleArrayList.add(getString(R.string.chat));

        adapter = new FragmentsAdapter(getSupportFragmentManager(),fragmentArrayList,titleArrayList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void addControls() {
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
