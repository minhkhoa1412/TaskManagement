package com.minhkhoa.taskmanagement.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.minhkhoa.taskmanagement.adapter.ChatAdapter;
import com.minhkhoa.taskmanagement.model.Board;
import com.minhkhoa.taskmanagement.model.Chat;
import com.minhkhoa.taskmanagement.model.ChatChannel;
import com.minhkhoa.taskmanagement.model.User;
import com.minhkhoa.taskmanagement.util.Constant;

import java.util.ArrayList;
import java.util.Calendar;

public class BoardChatFragment extends Fragment {
    FirebaseUser user_firebase = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    View view;
    ListView lvChat;
    EditText edtChat;
    ImageButton btnChat;
    TextView txtChannel;
    Spinner spnChannel;

    Board board;
    User user;
    int index = 0;
    int count = 0;
    ArrayList<ChatChannel> chatChannelArrayList;
    ArrayList<Chat> chatArrayList;
    ArrayList<Chat> chatForAdapterArrayList;
    ChatAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_board_chat, container, false);
        addControls();
        init();
        getUserFormFirebase();
        getChatFormFirebaseTest();
        addEvents();
        return view;
    }

    private void getChatFormFirebaseTest() {
        //get chat data
        databaseReference.child("Chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                count++;//set var count to count children -> if count > children -> get data done
                ChatChannel chatChannel = dataSnapshot.getValue(ChatChannel.class);
                if(chatChannel.getBoardcardID().equals(board.getBoardID())){//collect all chat channel of current board
                    if(chatChannel.getChatArrayList() == null){//check if chat array list null -> no one chat conversation in list -> init arraylist
                        chatChannel.setChatArrayList(new ArrayList<Chat>());//init arraylist
                    }
                    chatChannelArrayList.add(chatChannel);//if arraylist null -> init. 100% list not null -> add to chat channel
                }
                if(count >= dataSnapshot.getChildrenCount()){//check if dataSnapshot get data down -> go in if statment
                    count = 0;//set count back 0 to start again override method
                    /*add list chat to another list -> another list will be adapted to listview
                    * index is position channel in chatChannelArrayList. index default is 0.
                    * in order word, general is default channel. because channel general have position 0 in chatChannelArrayList*/
                    chatForAdapterArrayList.addAll(chatChannelArrayList.get(index).getChatArrayList());
                    adapter.notifyDataSetChanged();//notify data after add data
                    lvChat.smoothScrollToPosition(chatChannelArrayList.get(index).getChatArrayList().size());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                /*method override onChildChanged will run when have data change on this node
                * in this case, when push a 1 object chat to firebase -> data change once
                * so, this method will run only 1, and dataSnapshot have data only data ,this data change*/
                ChatChannel chatChannel = dataSnapshot.getValue(ChatChannel.class);
                if(chatChannel.getBoardcardID().equals(board.getBoardID())){
                    chatChannelArrayList.get(index).getChatArrayList().clear();
                    chatChannelArrayList.get(index).getChatArrayList().addAll(chatChannel.getChatArrayList());
                    chatForAdapterArrayList.clear();
                    chatForAdapterArrayList.addAll(chatChannelArrayList.get(index).getChatArrayList());
                    adapter.notifyDataSetChanged();
                    lvChat.smoothScrollToPosition(chatChannelArrayList.get(index).getChatArrayList().size());
                }
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

    private void getUserFormFirebase() {
        databaseReference.child("User").child(user_firebase.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addEvents() {
        edtChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().matches("")) {
                    btnChat.setVisibility(View.GONE);
                } else {
                    btnChat.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create object chat and set full atribute for obj
                Chat chat = new Chat();
                chat.setChatID(databaseReference.push().getKey());
                chat.setChatTime(Calendar.getInstance().getTime());
                chat.setUserID(user.getUserID());
                chat.setUserName(user.getUserName());
                chat.setUserAvata(user.getUserAvata());
                chat.setChatContent(edtChat.getText().toString().trim());
                //in this case, channel is 0 -> in general channel. add this chat in first item in chatChannelArrayList
                chatChannelArrayList.get(index).getChatArrayList().add(chat);
                databaseReference.child("Chat").child(chatChannelArrayList.get(index).getChannelID()).setValue(chatChannelArrayList.get(index));
                edtChat.setText("");//when done. clear all chat content in edittext
            }
        });

        edtChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lvChat.smoothScrollToPosition(chatChannelArrayList.get(index).getChatArrayList().size());
                    }
                },500);
            }
        });
    }

    private void init() {
        btnChat.setVisibility(View.GONE);
        //ở đây t nhận object board từ bên activity ChatActivity. Fragment BoardChatFragment nằm trong ChatActivity.Tự tạo 1 cái mà giả lập xài
        board = (Board) getArguments().getSerializable(Constant.CHAT_FRAGMENTS);
        chatChannelArrayList = new ArrayList<>();
        chatArrayList = new ArrayList<>();
        chatForAdapterArrayList = new ArrayList<>();

        adapter = new ChatAdapter(getContext(), chatForAdapterArrayList);
        lvChat.setAdapter(adapter);
    }

    private void addControls() {
        txtChannel = view.findViewById(R.id.textview_channel);
        lvChat = view.findViewById(R.id.listview_chat);
        edtChat = view.findViewById(R.id.edittext_chat);
        btnChat = view.findViewById(R.id.button_chat);
        spnChannel = view.findViewById(R.id.spinner_chat);
    }
}
