package com.minhkhoa.taskmanagement.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kc.unsplash.Unsplash;
import com.kc.unsplash.api.Order;
import com.kc.unsplash.models.Photo;
import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.adapter.BoardAdapter;
import com.minhkhoa.taskmanagement.model.Board;
import com.minhkhoa.taskmanagement.model.User;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user_firebase = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    CircleImageView imgAvata;
    TextView txtName;
    ListView lvBoard;

    ArrayList<Board> boardArrayList;
    BoardAdapter adapter;

    Unsplash unsplash = new Unsplash("df44c5fcfb97a0c6adb47ba2db0c96d125ac3f982b48341162b6fd1da573c9b0");
    Board board = new Board();
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addControls();
        init();
        getData();
        dataDemo();
        getBoard();

        findViewById(R.id.floattingbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        lvBoard.setAdapter(adapter);

        addEvents();

    }

    private void getBoard() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Board")){
                    databaseReference.child("Board").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Board board = dataSnapshot.getValue(Board.class);
                            if(board.getUserID().equals(user_firebase.getUid())){
                                boardArrayList.add(board);
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
        lvBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dataDemo() {
        boardArrayList.add(new Board("1","asd", "Luận văn cuối kì", "https://kenh14cdn.com/2016/chominyeong12-1464720012078.jpg", null));
    }

    private void init() {
        boardArrayList = new ArrayList<>();
        adapter = new BoardAdapter(MainActivity.this, boardArrayList);
        databaseReference.keepSynced(true);
    }

    private void addControls() {
        imgAvata = findViewById(R.id.avata_main);
        txtName = findViewById(R.id.textview_name_main);
        lvBoard = findViewById(R.id.listview_main);
    }

    private void getData() {
        databaseReference.child("User").child(user_firebase.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtName.setText(dataSnapshot.getValue(User.class).getUserName());
                Picasso.get().load(dataSnapshot.getValue(User.class).getUserAvata()).into(imgAvata);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_notifi) {
            Toast.makeText(this, "Noti Menu Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_search) {
            Toast.makeText(this, "Search Menu Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_signout) {
            firebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.create_board);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(dialogview);
        final EditText edtCreateBoard = dialogview.findViewById(R.id.edittextnhap);
        edtCreateBoard.requestFocus();

        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                randomImageFormUnsplash();
                addBoard(edtCreateBoard.getText().toString());
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

    public void addBoard(String name){
        key =  databaseReference.push().getKey();
        board.setBoardID(key);
        board.setBoardName(name);
        board.setUserID(user_firebase.getUid());
        board.setUserArrayList(null);
    }

    public void randomImageFormUnsplash(){
        unsplash.getRandomPhoto("144067", null, null, null, null, null, "landscape", new Unsplash.OnPhotoLoadedListener() {
            @Override
            public void onComplete(Photo photo) {
                board.setBoardImage(photo.getUrls().getRegular());
                databaseReference.child("Board").child(key).setValue(board);
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
