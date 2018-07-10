package com.minhkhoa.taskmanagement.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.activity.CardDetailsActivity;
import com.minhkhoa.taskmanagement.adapter.MemberAdapter;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.model.User;
import com.minhkhoa.taskmanagement.util.Constant;
import com.minhkhoa.taskmanagement.util.SimpleDayFormat;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class CardDetailsFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    FirebaseUser user_firebase = FirebaseAuth.getInstance().getCurrentUser();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    FloatingActionButton fab;
    View view;
    Card card;
    RecyclerView rvMember;
    ImageButton btnAddTag, btnaddDeadline, btnAddMember;
    LinearLayout llnTag;
    ImageView imgTagOne, imgTagTwo, imgTagThere, imgTagFour, imgTagFive;
    TextView txtCardName, txtCardDesciption, txtTag, txtDeadline;

    ArrayList<Integer> a;
    String boardID;
    ArrayList<User> userArrayList;
    Calendar calendar = Calendar.getInstance();
    MemberAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_card_details, container, false);
        addControls();
        init();
        showTag();
        addEvents();
        return view;
    }

    private void addEvents() {
        txtCardName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(txtCardName.getText().toString());
            }
        });

        txtCardDesciption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDesciption(txtCardDesciption.getText().toString());
            }
        });

        btnAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card.getCardTag() == null) {
                    a = new ArrayList<>();
                    showDialogTag(a);
                } else {
                    showDialogTag(card.getCardTag());
                }

            }
        });

        btnaddDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });

        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
            }
        });

    }

    private void addControls() {
        fab = view.findViewById(R.id.fab_fragments);
        txtCardName = view.findViewById(R.id.textview_title);
        txtCardDesciption = view.findViewById(R.id.textview_des);
        llnTag = view.findViewById(R.id.linear_tag);
        imgTagOne = view.findViewById(R.id.tag1_item);
        imgTagTwo = view.findViewById(R.id.tag2_item);
        imgTagThere = view.findViewById(R.id.tag3_item);
        imgTagFour = view.findViewById(R.id.tag4_item);
        imgTagFive = view.findViewById(R.id.tag5_item);
        btnAddTag = view.findViewById(R.id.button_edit_tag);
        txtTag = view.findViewById(R.id.textview_tag);
        txtDeadline = view.findViewById(R.id.textview_deadline);
        btnaddDeadline = view.findViewById(R.id.button_edit_deadline);
        btnAddMember = view.findViewById(R.id.button_add_member);
        rvMember = view.findViewById(R.id.rv_member);
    }

    private void init() {
        btnAddMember.setVisibility(View.GONE);
        fab.hide();
        card = (Card) getArguments().getSerializable(Constant.CARD_FRAGMENTS);
        boardID = getArguments().getString(Constant.BOARD_ID_FOR_CHAT_CARD);
        userArrayList = new ArrayList<>();

        for(int i = 0; i < card.getUserArrayList().size(); i ++){
            if(card.getUserArrayList().get(i).getUserID().equals(user_firebase.getUid())){
                if(card.getUserArrayList().get(i).getUserPermission() == 1){
                    btnAddMember.setVisibility(View.VISIBLE);
                }
            }
        }

        databaseReference.child("Board").child(boardID).child("userArrayList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                userArrayList.add(user);
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

        adapter = new MemberAdapter(getContext(),card.getUserArrayList(),card.getCardID(),user_firebase.getUid());
        LinearLayoutManager llm = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvMember.setAdapter(adapter);
        rvMember.setLayoutManager(llm);

        txtCardName.setText(card.getCardName());
        if (card.getCardDescription().matches("")) {
            txtCardDesciption.setText(getString(R.string.card_des_empty));
            txtCardDesciption.setTextColor(getResources().getColor(R.color.gray));
        } else {
            txtCardDesciption.setText(card.getCardDescription());
        }

        if (card.getCardDeadline() != null) {
            txtDeadline.setText(getString(R.string.expires) + " " + SimpleDayFormat.formatDate(card.getCardDeadline()));
        }

    }

    private void showDialog(String a) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.edit);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(dialogview);
        final EditText edtTitleCard = dialogview.findViewById(R.id.edittextnhap);
        edtTitleCard.setText(a);
        edtTitleCard.requestFocus();

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.child("Card").child(card.getCardID()).child("cardName").setValue(edtTitleCard.getText().toString());
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

    private void showDialogDesciption(String a) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.edit);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(dialogview);
        final EditText edtTitleCard = dialogview.findViewById(R.id.edittextnhap);
        edtTitleCard.setText(a);
        edtTitleCard.requestFocus();

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.child("Card").child(card.getCardID()).child("cardDescription").setValue(edtTitleCard.getText().toString());
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

    private void showDialogTag(final ArrayList<Integer> cardTag) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.edit);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.layout_dialog_tag, null);
        builder.setView(dialogview);

        CheckBox ckbTagOne = dialogview.findViewById(R.id.checkbox_tag1);
        CheckBox ckbTagTwo = dialogview.findViewById(R.id.checkbox_tag2);
        CheckBox ckbTagThree = dialogview.findViewById(R.id.checkbox_tag3);
        CheckBox ckbTagFour = dialogview.findViewById(R.id.checkbox_tag4);
        CheckBox ckbTagFive = dialogview.findViewById(R.id.checkbox_tag5);

        if (cardTag != null) {
            for (int i = 0; i < cardTag.size(); i++) {
                if (cardTag.get(i) == 0) {
                    ckbTagOne.setChecked(true);
                }
                if (cardTag.get(i) == 1) {
                    ckbTagTwo.setChecked(true);
                }
                if (cardTag.get(i) == 2) {
                    ckbTagThree.setChecked(true);
                }
                if (cardTag.get(i) == 3) {
                    ckbTagFour.setChecked(true);
                }
                if (cardTag.get(i) == 4) {
                    ckbTagFive.setChecked(true);
                }
            }
        }

        ckbTagOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cardTag.add(0);
                } else {
                    for (int i = 0; i < cardTag.size(); i++) {
                        if (cardTag.get(i) == 0) {
                            cardTag.remove(i);
                        }
                    }
                }
            }
        });
        ckbTagTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cardTag.add(1);
                } else {
                    for (int i = 0; i < cardTag.size(); i++) {
                        if (cardTag.get(i) == 1) {
                            cardTag.remove(i);
                        }
                    }
                }
            }
        });
        ckbTagThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cardTag.add(2);
                } else {
                    for (int i = 0; i < cardTag.size(); i++) {
                        if (cardTag.get(i) == 2) {
                            cardTag.remove(i);
                        }
                    }
                }
            }
        });
        ckbTagFour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cardTag.add(3);
                } else {
                    for (int i = 0; i < cardTag.size(); i++) {
                        if (cardTag.get(i) == 3) {
                            cardTag.remove(i);
                        }
                    }
                }
            }
        });
        ckbTagFive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cardTag.add(4);
                } else {
                    for (int i = 0; i < cardTag.size(); i++) {
                        if (cardTag.get(i) == 4) {
                            cardTag.remove(i);
                        }
                    }
                }
            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.child("Card").child(card.getCardID()).child("cardTag").setValue(cardTag);
                for (int z = 0; z < cardTag.size(); z++) {
                    Log.d("aaa", cardTag.get(z).toString());
                }
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

    private void showTag() {
        if (card.getCardTag() != null) {
            llnTag.setVisibility(View.VISIBLE);
            txtTag.setVisibility(View.GONE);
            for (int i = 0; i < card.getCardTag().size(); i++) {
                if (card.getCardTag().get(i) == 0) {
                    imgTagOne.setVisibility(View.VISIBLE);
                }
                if (card.getCardTag().get(i) == 1) {
                    imgTagTwo.setVisibility(View.VISIBLE);
                }
                if (card.getCardTag().get(i) == 2) {
                    imgTagThere.setVisibility(View.VISIBLE);
                }
                if (card.getCardTag().get(i) == 3) {
                    imgTagFour.setVisibility(View.VISIBLE);
                }
                if (card.getCardTag().get(i) == 4) {
                    imgTagFive.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public ArrayList<User> getUserArrayList(){
        return this.userArrayList;
    }

    private void pickDate() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DATE, dayOfMonth);
                txtDeadline.setText(getString(R.string.expires) + " " + sdf.format(calendar.getTime()));
                databaseReference.child("Card").child(card.getCardID()).child("cardDeadline").setValue(calendar.getTime());
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }

    private void addMember() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.add_member);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogview = inflater.inflate(R.layout.layout_dialog_card_details, null);
        builder.setView(dialogview);

        final ListView lvMember = dialogview.findViewById(R.id.listview_member_dialog);

        class MemberDialogAdapter extends BaseAdapter {
            Context context;
            ArrayList<User> userArrayList;
            LayoutInflater inflater;

            public MemberDialogAdapter(Context context, ArrayList<User> userArrayList) {
                this.context = context;
                this.userArrayList = userArrayList;
                inflater = LayoutInflater.from(context);
            }

            @Override
            public int getCount() {
                return userArrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return userArrayList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = inflater.inflate(R.layout.item_dialog_member,null);
                CircleImageView imgAvata = convertView.findViewById(R.id.image_avata_member);
                TextView txtName = convertView.findViewById(R.id.textview_name_member);
                TextView txtEmail = convertView.findViewById(R.id.textview_email_member);
                Picasso.get().load(userArrayList.get(position).getUserAvata()).into(imgAvata);
                txtName.setText(userArrayList.get(position).getUserName());
                txtEmail.setText(userArrayList.get(position).getUserEmail());
                return convertView;
            }
        }

        MemberDialogAdapter adapterDialog = new MemberDialogAdapter(getContext(),userArrayList);
        lvMember.setAdapter(adapterDialog);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertDialog.show();

        for(int i = 0; i < card.getUserArrayList().size(); i++){
            for(int j =0; j< userArrayList.size(); j++){
                if(card.getUserArrayList().get(i).getUserEmail().equals(userArrayList.get(j).getUserEmail())){
                    userArrayList.remove(j);
                }
            }
        }

        lvMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                databaseReference.child("User").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user.getUserEmail().equals(userArrayList.get(position).getUserEmail())){
                            card.getUserArrayList().add(user);
                            databaseReference.child("Card").child(card.getCardID()).child("userArrayList").setValue(card.getUserArrayList());
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
                alertDialog.dismiss();
            }
        });
    }
}
