package com.minhkhoa.taskmanagement.fragment;

import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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
import com.minhkhoa.taskmanagement.adapter.MemberBoardAdapter;
import com.minhkhoa.taskmanagement.model.Board;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.model.Chat;
import com.minhkhoa.taskmanagement.model.ChatChannel;
import com.minhkhoa.taskmanagement.model.User;
import com.minhkhoa.taskmanagement.util.Constant;

import java.util.ArrayList;

public class BoardMemberFragment extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    FirebaseUser user_firebase = FirebaseAuth.getInstance().getCurrentUser();


    MemberBoardAdapter adapter;

    View view;
    Board board;
    ListView lvMember;
    String chatChannelID;
    ImageButton btnAddMember;
    EditText edtAddMember;
    ArrayList<User> userArrayList;

    int flag = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_board_member, container, false);
        addControls();
        init();
        addEvents();
        return view;
    }

    private void addEvents() {
        edtAddMember.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().matches("")) {
                    btnAddMember.setVisibility(View.GONE);
                } else {
                    btnAddMember.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edtAddMember.getText().toString();
                databaseReference.child("User").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user.getUserEmail().equals(email)){//user preparing to add board-chat
                            for (int i = 0; i < userArrayList.size(); i++){
                                if(user.getUserEmail().equals(userArrayList.get(i).getUserEmail())){
                                    Toast.makeText(getContext(), getString(R.string.already_have_member), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            board.getUserArrayList().add(user);///not exits -> add local -> push all array to firebase
                            databaseReference.child("Board").child(board.getBoardID()).child("userArrayList").setValue(board.getUserArrayList());
                            databaseReference.child("Chat").child(chatChannelID).child("userArrayList").setValue(board.getUserArrayList());
                            Toast.makeText(getContext(), getString(R.string.add_success), Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                        //tạo 1 boardPush. thêm tv vào boardPush. sau đó push dữ liệu qua bên user. nếu user accept thì thực thi 104,105
                        //class Notify sẽ có
                        //userArrayList() = boardPush.getUserArrayList();
                        //boardID = board.getBoardID();
                        //chatChannelID = chatChannelI;
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
                edtAddMember.setText("");
            }
        });
    }

    private void init() {
        board = (Board) getArguments().getSerializable(Constant.CHAT_FRAGMENTS);
        chatChannelID = getArguments().getString(Constant.CHAT_CHANNEL_ID);
        btnAddMember.setVisibility(View.GONE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        userArrayList = board.getUserArrayList();

        adapter = new MemberBoardAdapter(getContext(),userArrayList,user_firebase.getUid(),board.getBoardID(),chatChannelID);
        lvMember.setAdapter(adapter);
    }

    private void addControls() {
        lvMember = view.findViewById(R.id.listview_member);
        btnAddMember = view.findViewById(R.id.button_addmember);
        edtAddMember = view.findViewById(R.id.edittext_add_member);
    }
}
