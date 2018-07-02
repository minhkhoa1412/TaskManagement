package com.minhkhoa.taskmanagement.fragment;


import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.activity.CardActivity;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.util.Constant;

import org.w3c.dom.Text;

public class CardDetailsFragment extends Fragment {

    FloatingActionButton fab;
    View view;
    Card card;

    TextView txtCardName, txtCardDesciption;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_card_details, container, false);
        addControls();
        init();
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

    }

    private void addControls() {
        fab = view.findViewById(R.id.fab_fragments);
        txtCardName = view.findViewById(R.id.textview_title);
        txtCardDesciption = view.findViewById(R.id.textview_des);
    }

    private void init() {
        fab.hide();
        card = (Card) getArguments().getSerializable(Constant.CARD_FRAGMENTS);

        txtCardName.setText(card.getCardName());
        if (card.getCardDescription().matches("")) {
            txtCardDesciption.setText(getString(R.string.card_des_empty));
            txtCardDesciption.setTextColor(getResources().getColor(R.color.gray));
        } else {
            txtCardDesciption.setText(card.getCardDescription());
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
}
