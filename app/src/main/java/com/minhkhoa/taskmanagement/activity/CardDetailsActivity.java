package com.minhkhoa.taskmanagement.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.adapter.FragmentsAdapter;
import com.minhkhoa.taskmanagement.fragment.CardChatFragment;
import com.minhkhoa.taskmanagement.fragment.CardDetailsFragment;
import com.minhkhoa.taskmanagement.fragment.CardTaskFragment;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.model.User;
import com.minhkhoa.taskmanagement.util.Constant;

import java.util.ArrayList;

public class CardDetailsActivity extends AppCompatActivity {
    FirebaseUser user_firebase = FirebaseAuth.getInstance().getCurrentUser();

    TabLayout tabLayout;
    ViewPager viewPager;

    FragmentsAdapter adapter;
    ArrayList<Fragment> fragmentArrayList;
    ArrayList<String> titleArrayList;

    CardDetailsFragment cardDetailsFragment;
    CardTaskFragment cardTaskFragment;
    CardChatFragment cardChatFragment;

    String cardID;
    String cardName;
    String boardID;
    Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addControls();
        getDataFormCardActivity();
        init();
        prepareUI();
    }

    private void prepareUI() {
        getSupportActionBar().setTitle(getString(R.string.card_details));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getDataFormCardActivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constant.BUNDLE_CARD_TO_DETAILS);
        card = (Card) bundle.getSerializable(Constant.CARD_ARRAY_DETAILS);
        boardID = bundle.getString(Constant.BOARD_ID_FOR_CHAT_CARD);
        cardID = card.getCardID();
        cardName = card.getCardName();
    }

    private void init() {
        fragmentArrayList = new ArrayList<>();
        titleArrayList = new ArrayList<>();

        Bundle bundle = new Bundle();
        bundle.putString(Constant.BOARD_ID_FOR_CHAT_CARD,boardID);
        bundle.putSerializable(Constant.CARD_FRAGMENTS,card);
        cardDetailsFragment = new CardDetailsFragment();
        cardTaskFragment = new CardTaskFragment();
        cardChatFragment = new CardChatFragment();
        cardDetailsFragment.setArguments(bundle);
        cardTaskFragment.setArguments(bundle);
        cardChatFragment.setArguments(bundle);

        fragmentArrayList.add(cardDetailsFragment);
        fragmentArrayList.add(cardTaskFragment);
        fragmentArrayList.add(cardChatFragment);

        titleArrayList.add(getString(R.string.details));
        titleArrayList.add(getString(R.string.task));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_card_details,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Toast.makeText(this, "Search menu clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
