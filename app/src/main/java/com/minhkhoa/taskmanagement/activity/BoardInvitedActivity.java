package com.minhkhoa.taskmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.adapter.BoardInvitedAdapter;
import com.minhkhoa.taskmanagement.model.Board;
import com.minhkhoa.taskmanagement.model.User;
import com.minhkhoa.taskmanagement.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BoardInvitedActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    CircleImageView imgAvata;
    TextView txtName;
    ListView lvBoard;
    Toolbar toolbar_board;
    User user;
    BoardInvitedAdapter adapter;
    ArrayList<Board> boardArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_invited);
        toolbar_board = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_board);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        getDataFormMain();
        getDataFormFirebase();
        init();
        addEvents();
    }

    private void getDataFormFirebase() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Board")){
                    databaseReference.child("Board").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Board board = dataSnapshot.getValue(Board.class);
                            for( int j = 0; j < board.getUserArrayList().size() ; j++){
                                if(board.getUserArrayList().get(j).getUserPermission() == 0 && board.getUserArrayList().get(j).getUserID().equals(user.getUserID())){
                                    boardArrayList.add(board);
                                }
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

    private void getDataFormMain() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(Constant.USER_TO_BOARD_INVITED);
    }

    private void init() {
        boardArrayList = new ArrayList<>();
        adapter = new BoardInvitedAdapter(BoardInvitedActivity.this,boardArrayList);
        lvBoard.setAdapter(adapter);
        txtName.setText(user.getUserName());
        Picasso.get().load(user.getUserAvata()).fit().centerCrop().into(imgAvata);
    }

    private void addEvents() {
        lvBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BoardInvitedActivity.this, ListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.POSITION_BOARD, position);
                bundle.putString(Constant.BOARD_ID, boardArrayList.get(position).getBoardID());
                bundle.putString(Constant.BOARD_NAME, boardArrayList.get(position).getBoardName());
                intent.putExtra(Constant.BUNDLE_MAIN_TO_LIST, bundle);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        imgAvata = findViewById(R.id.avata_board);
        txtName = findViewById(R.id.textview_name_board);
        lvBoard = findViewById(R.id.listview_board);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
