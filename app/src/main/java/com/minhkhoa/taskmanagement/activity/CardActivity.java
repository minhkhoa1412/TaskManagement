package com.minhkhoa.taskmanagement.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.minhkhoa.taskmanagement.R;

public class CardActivity extends AppCompatActivity {

    LinearLayout lln;
    ImageView tagOne, tagTwo, tagThere, tagFour, tagFive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        lln = findViewById(R.id.linear_tag);
        tagOne = findViewById(R.id.tag1_item);
        tagTwo = findViewById(R.id.tag2_item);
        tagThere = findViewById(R.id.tag3_item);
        tagFour = findViewById(R.id.tag4_item);
        tagFive = findViewById(R.id.tag5_item);
        lln.setVisibility(View.VISIBLE);
        tagOne.setVisibility(View.VISIBLE);
//        tagTwo.setVisibility(View.GONE);
//        tagThere.setVisibility(View.GONE);
//        tagFour.setVisibility(View.GONE);
        tagFive.setVisibility(View.VISIBLE);

    }
}
