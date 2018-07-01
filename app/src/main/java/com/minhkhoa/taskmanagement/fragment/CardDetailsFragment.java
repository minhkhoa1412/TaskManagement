package com.minhkhoa.taskmanagement.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.model.Card;
import com.minhkhoa.taskmanagement.util.Constant;

public class CardDetailsFragment extends Fragment {

    FloatingActionButton fab;
    View view;
    Card card;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_card_details,container,false);
        addControls();
        init();
        return view;
    }

    private void addControls() {
        fab = view.findViewById(R.id.fab_fragments);
    }

    private void init() {
        fab.hide();
        card = (Card) getArguments().getSerializable(Constant.CARD_FRAGMENTS);
        Log.d("aaa",card.getCardName());
    }
}
