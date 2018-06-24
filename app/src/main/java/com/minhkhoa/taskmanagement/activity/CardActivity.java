package com.minhkhoa.taskmanagement.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.adapter.CardAdapter;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.util.Constant;

import java.util.ArrayList;

public class CardActivity extends AppCompatActivity {

    RecyclerView rvCard;
    CardAdapter adapter;
    ArrayList<Card> cardArrayList;

    String listID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        getSupportActionBar().setTitle(null);

        addControls();
        init();
        getDataFormList();
    }

    private void getDataFormList() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constant.BUNDLE_LIST_TO_CARD);
        listID = bundle.getString(Constant.LIST_ID);
    }

    private void init() {
        cardArrayList = new ArrayList<>();
        cardArrayList.add(new Card("asd","asdm","abc","abc",null,null,null));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new CardAdapter(CardActivity.this,cardArrayList);

        rvCard.setLayoutManager(layoutManager);
        rvCard.setAdapter(adapter);
    }

    private void addControls() {
        rvCard = findViewById(R.id.rv);
    }
}
