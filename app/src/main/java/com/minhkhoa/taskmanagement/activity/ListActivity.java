package com.minhkhoa.taskmanagement.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
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
import com.google.firebase.database.ValueEventListener;
import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.adapter.ListAdapter;
import com.minhkhoa.taskmanagement.model.Board;
import com.minhkhoa.taskmanagement.model.ChatChannel;
import com.minhkhoa.taskmanagement.model.List;
import com.minhkhoa.taskmanagement.util.Constant;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    FirebaseUser user_firebase = FirebaseAuth.getInstance().getCurrentUser();


    String boardID;
    String boardName;
    int boardPosition;
    String listKey;
    String chatChannelID;
    ArrayList<List> listArrayList;
    ListAdapter adapter;
    Board board;

    View viewTitle;
    TextView txtTitle;
    ListView lvList;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addControls();
        init();
        getDataFormMain();
        prepareUI();
        getDataListFormFirebase();
        addEvents();
        getDataChatFormFirebase();
    }

    private void getDataChatFormFirebase() {
        databaseReference.child("Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ChatChannel chatChannel = snapshot.getValue(ChatChannel.class);
                    if(chatChannel.getBoardcardID().equals(board.getBoardID()) && chatChannel.getChannelName().equals(getString(R.string.general))){
                        chatChannelID = chatChannel.getChannelID();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addEvents() {
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(ListActivity.this,CardActivity.class);
                bundle.putString(Constant.LIST_ID,listArrayList.get(position).getListID());
                bundle.putString(Constant.LIST_NAME,listArrayList.get(position).getListName());
                bundle.putString(Constant.BOARD_ID_FOR_CHAT_CARD,boardID);
                intent.putExtra(Constant.BUNDLE_LIST_TO_CARD,bundle);
                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        lvList.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mLastFirstVisibleItem < firstVisibleItem && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                }
                if (mLastFirstVisibleItem > firstVisibleItem && fab.getVisibility() == View.GONE) {
                    fab.show();
                }
                mLastFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    private void getDataListFormFirebase() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("List")) {
                    databaseReference.child("List").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            List list = dataSnapshot.getValue(List.class);
                            if(list != null){
                                if (list.getBoardID().equals(boardID)) {
                                    listArrayList.add(list);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            List list1 = dataSnapshot.getValue(List.class);
                            for(int i = 0; i < listArrayList.size(); i++){
                                if(listArrayList.get(i).getListID().equals(list1.getListID())){
                                    listArrayList.set(i,list1);
                                }
                            }
                            adapter.notifyDataSetChanged();
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

        databaseReference.child("Board").child(boardID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                board = dataSnapshot.getValue(Board.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        listArrayList = new ArrayList<>();
        adapter = new ListAdapter(ListActivity.this, listArrayList);
        lvList.setAdapter(adapter);
    }

    private void prepareUI() {
        fab.hide();
        //set title
        getSupportActionBar().setTitle("List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set title name
        if (boardPosition % 3 == 0) {
            viewTitle.setBackgroundResource(R.drawable.rounded_label_1);
        } else if (boardPosition % 3 == 1) {
            viewTitle.setBackgroundResource(R.drawable.rounded_label_2);
        } else {
            viewTitle.setBackgroundResource(R.drawable.rounded_label_3);
        }
        txtTitle.setText(boardName);

        fab.show();
    }

    private void addControls() {
        viewTitle = findViewById(R.id.view_gradient_list);
        txtTitle = findViewById(R.id.board_name_list);
        lvList = findViewById(R.id.listview_list);
        fab = findViewById(R.id.fab);
    }

    private void getDataFormMain() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constant.BUNDLE_MAIN_TO_LIST);
        boardID = bundle.getString(Constant.BOARD_ID);
        boardPosition = bundle.getInt(Constant.POSITION_BOARD);
        boardName = bundle.getString(Constant.BOARD_NAME);
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
        if (item.getItemId() == R.id.action_member) {
            Intent intent = new Intent(ListActivity.this,ChatActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.BOARD_CHAT,board);
            bundle.putString(Constant.CHAT_CHANNEL_ID,chatChannelID);
            intent.putExtra(Constant.BUNDLE_LIST_TO_CHAT,bundle);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.action_delete){

        }
        if(item.getItemId() == R.id.action_edit){
            showDialogEdit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setTitle(R.string.create_list);
        LayoutInflater inflater = ListActivity.this.getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(dialogview);
        final EditText edtCreateList = dialogview.findViewById(R.id.edittextnhap);
        edtCreateList.requestFocus();

        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addList(edtCreateList.getText().toString());
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

    private void showDialogEdit() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setTitle(R.string.edit);
        LayoutInflater inflater = ListActivity.this.getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(dialogview);
        final EditText edtEdit = dialogview.findViewById(R.id.edittextnhap);
        edtEdit.setText(boardName);
        edtEdit.requestFocus();

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.child("Board").child(boardID).child("boardName").setValue(edtEdit.getText().toString());
                txtTitle.setText(edtEdit.getText().toString());
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

    private void addList(String listName) {
        listKey = databaseReference.push().getKey();
        List list = new List(listKey, boardID, listName, null);
        databaseReference.child("List").child(listKey).setValue(list);
    }
}
