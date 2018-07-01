package com.minhkhoa.taskmanagement.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.adapter.FragmentsAdapter;
import com.minhkhoa.taskmanagement.fragment.CardDetailsFragment;
import com.minhkhoa.taskmanagement.fragment.CardTaskFragment;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.util.Constant;

import java.util.ArrayList;

public class CardDetailsActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    FragmentsAdapter adapter;
    ArrayList<Fragment> fragmentArrayList;
    ArrayList<String> titleArrayList;

    String cardID;
    String cardName;
    Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        addControls();
        getDataFormCardActivity();
        passDataToFragments();
        init();
        prepareUI();
        addEvents();
    }

    private void passDataToFragments() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.CARD_FRAGMENTS,card);
        CardDetailsFragment a = new CardDetailsFragment();
        a.setArguments(bundle);
    }

    private void addEvents() {
    }

    private void prepareUI() {
        getSupportActionBar().setTitle(getString(R.string.card_details));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getDataFormCardActivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constant.BUNDLE_CARD_TO_DETAILS);
        card = (Card) bundle.getSerializable(Constant.CARD_ARRAY_DETAILS);
        cardID = card.getCardID();
        cardName = card.getCardName();
    }

    private void init() {
        fragmentArrayList = new ArrayList<>();
        titleArrayList = new ArrayList<>();

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.CARD_FRAGMENTS,card);
        CardDetailsFragment cardDetailsFragment = new CardDetailsFragment();
        cardDetailsFragment.setArguments(bundle);

        fragmentArrayList.add(cardDetailsFragment);
        fragmentArrayList.add(new CardTaskFragment());

        titleArrayList.add(getString(R.string.details));
        titleArrayList.add(getString(R.string.task));

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
