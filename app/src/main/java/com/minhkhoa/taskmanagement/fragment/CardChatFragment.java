package com.minhkhoa.taskmanagement.fragment;

import android.content.Intent;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

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
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.model.Chat;
import com.minhkhoa.taskmanagement.model.ChatChannel;
import com.minhkhoa.taskmanagement.model.User;
import com.minhkhoa.taskmanagement.util.Constant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CardChatFragment extends Fragment {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user_firebase = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    View view;
    ListView lvChat;
    ImageButton btnChat;
    Button btnContact;
    EditText edtChat;
    LinearLayout llnNothing, llnMain;

    ChatAdapter adapter;
    Card card;
    ChatChannel chatChannel;
    String boardID;
    ArrayList<Chat> chatArrayList;
    User user;
    String userAdminEmail;

    int flag = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_card_chat, container, false);
        addControls();
        init();
        getDataFormFirebase();
        prepareUI();
        addEvents();
        return view;
    }

    private void prepareUI() {
        for ( int i = 0; i < chatChannel.getUserArrayList().size(); i++){
            if(user_firebase.getUid().equals(card.getUserArrayList().get(i).getUserID())){
                flag = 1;
            }
        }
        if(flag == 0){
            llnNothing.setVisibility(View.VISIBLE);
            llnMain.setVisibility(View.GONE);
        }
    }

    private void getDataFormFirebase() {
        //get userarray form firebase
        databaseReference.child("Card").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Card card1 = dataSnapshot.getValue(Card.class);
                if(card1.getCardID().equals(card.getCardID())){
//                    chatChannel.setUserArrayList(card1.getUserArrayList());
                    //get user current
                    for( int j = 0; j < card1.getUserArrayList().size(); j++){
                        if(card1.getUserArrayList().get(j).getUserID().equals(user_firebase.getUid())){
                            user = card1.getUserArrayList().get(j);
                        }
                        if(card1.getUserArrayList().get(j).getUserPermission() == 1){
                            userAdminEmail = card1.getUserArrayList().get(j).getUserEmail();
                        }
                    }
                }
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
        //get chatarray form firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Chat")){
                    databaseReference.child("Chat").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            ChatChannel chat = dataSnapshot.getValue(ChatChannel.class);
                            if(chat.getChannelID().equals(chatChannel.getChannelID())){
                                chatChannel.getChatArrayList().clear();
                                for(int j = 0; j < chat.getChatArrayList().size(); j++){
                                    chatChannel.getChatArrayList().add(chat.getChatArrayList().get(j));
                                }
                            }
                            adapter.notifyDataSetChanged();
                            lvChat.smoothScrollToPosition(chatChannel.getChatArrayList().size());
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            ChatChannel chat = dataSnapshot.getValue(ChatChannel.class);
                            if(chat.getChannelID().equals(chatChannel.getChannelID())){
                                chatChannel.getChatArrayList().clear();
                                for(int j = 0; j < chat.getChatArrayList().size(); j++){
                                    chatChannel.getChatArrayList().add(chat.getChatArrayList().get(j));
                                }
                            }
                            adapter.notifyDataSetChanged();
                            lvChat.smoothScrollToPosition(chatChannel.getChatArrayList().size());
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

    private void init() {
        card = (Card) getArguments().getSerializable(Constant.CARD_FRAGMENTS);
        boardID = getArguments().getString(Constant.BOARD_ID_FOR_CHAT_CARD);
        chatArrayList = new ArrayList<>();
        btnChat.setVisibility(View.GONE);

        chatChannel = new ChatChannel();
        chatChannel.setChannelID(card.getCardID());
        chatChannel.setBoardcardID(card.getCardID());
        chatChannel.setChannelName(card.getCardName());
        chatChannel.setChatArrayList(chatArrayList);
        chatChannel.setUserArrayList(card.getUserArrayList());

        adapter = new ChatAdapter(getContext(),chatChannel.getChatArrayList());
        lvChat.setAdapter(adapter);
    }

    private void addEvents() {
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat chat = new Chat();
                chat.setChatID(databaseReference.push().getKey());
                chat.setChatTime(Calendar.getInstance().getTime());
                chat.setUserID(user.getUserID());
                chat.setUserName(user.getUserName());
                chat.setUserAvata(user.getUserAvata());
                chat.setChatContent(edtChat.getText().toString().trim());
                chatChannel.getChatArrayList().add(chat);
                databaseReference.child("Chat").child(chatChannel.getChannelID()).setValue(chatChannel);
                edtChat.setText("");
            }
        });

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

        edtChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lvChat.smoothScrollToPosition(chatChannel.getChatArrayList().size());
                    }
                },500);
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{userAdminEmail});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Require conversation permission");
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
    }

    private void addControls() {
        lvChat = view.findViewById(R.id.listview_chat);
        btnChat = view.findViewById(R.id.button_chat);
        edtChat = view.findViewById(R.id.edittext_chat);
        llnMain = view.findViewById(R.id.lln_main);
        llnNothing = view.findViewById(R.id.lln_empty);
        btnContact = view.findViewById(R.id.button_contact);
    }


}
