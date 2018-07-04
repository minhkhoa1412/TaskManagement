package com.minhkhoa.taskmanagement.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.adapter.FragmentsAdapter;
import com.minhkhoa.taskmanagement.fragment.BoardChatFragment;
import com.minhkhoa.taskmanagement.fragment.BoardMemberFragment;
import com.minhkhoa.taskmanagement.fragment.CardChatFragment;
import com.minhkhoa.taskmanagement.fragment.CardDetailsFragment;
import com.minhkhoa.taskmanagement.fragment.CardTaskFragment;
import com.minhkhoa.taskmanagement.model.Board;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.util.Constant;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    FragmentsAdapter adapter;
    ArrayList<Fragment> fragmentArrayList;
    ArrayList<String> titleArrayList;

    String boardID;
    String boardName;
    Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        addControls();
        getDataFormListActivity();
        init();
        prepareUI();
    }

    private void getDataFormListActivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constant.BUNDLE_LIST_TO_CHAT);
        board = (Board) bundle.getSerializable(Constant.BOARD_CHAT);
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
