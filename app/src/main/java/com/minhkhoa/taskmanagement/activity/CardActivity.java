package com.minhkhoa.taskmanagement.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.minhkhoa.taskmanagement.adapter.CardAdapter;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.util.Constant;

import java.util.ArrayList;

public class CardActivity extends AppCompatActivity {

    RecyclerView rvCard;
    CardAdapter adapter;
    ArrayList<Card> cardArrayList;
    FloatingActionButton fab;
    TextView txtListName;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    String listID;
    String listName;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        getSupportActionBar().setTitle("Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        getDataFormList();
        init();
        getDataFormFirebase();
        addEvents();
    }

    private void getDataFormFirebase() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Card")) {
                    databaseReference.child("Card").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Card card = dataSnapshot.getValue(Card.class);
                            if (card.getListID().equals(listID)) {
                                cardArrayList.add(card);
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addEvents() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        rvCard.addOnItemTouchListener(new RecyclerTouchListener(this, rvCard, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(CardActivity.this,CardDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.CARD_ARRAY_DETAILS,cardArrayList.get(position));
                intent.putExtra(Constant.BUNDLE_CARD_TO_DETAILS,bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void getDataFormList() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constant.BUNDLE_LIST_TO_CARD);
        listID = bundle.getString(Constant.LIST_ID);
        listName = bundle.getString(Constant.LIST_NAME);
    }

    private void init() {
        cardArrayList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new CardAdapter(CardActivity.this, cardArrayList);

        rvCard.setLayoutManager(layoutManager);
        rvCard.setAdapter(adapter);

        txtListName.setText(listName);
    }

    private void addControls() {
        rvCard = findViewById(R.id.rv);
        fab = findViewById(R.id.floattingbutton);
        txtListName = findViewById(R.id.list_name_card);
    }

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CardActivity.this);
        builder.setTitle(R.string.create_card);
        LayoutInflater inflater = CardActivity.this.getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.layout_dialog_card, null);
        builder.setView(dialogview);
        final EditText edtTitleCard = dialogview.findViewById(R.id.edittexttitle);
        final EditText edtDescCard = dialogview.findViewById(R.id.edittextdescribe);
        edtTitleCard.requestFocus();

        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addCard(edtTitleCard.getText().toString(), edtDescCard.getText().toString());
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertDialog.show();
    }

    private void addCard(String cardName, String cardDescribe) {
        key = databaseReference.push().getKey();
        Card card = new Card();
        card.setCardID(key);
        card.setListID(listID);
        card.setCardName(cardName);
        card.setCardDescription(cardDescribe);
        databaseReference.child("Card").child(key).setValue(card);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Toast.makeText(this, "Search menu clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    //onClickRecyclerview
    private interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    private class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private CardActivity.ClickListener clickListener;

        private RecyclerTouchListener(Context context, final RecyclerView recyclerView, final CardActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
