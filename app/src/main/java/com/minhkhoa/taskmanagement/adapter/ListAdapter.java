package com.minhkhoa.taskmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minhkhoa.taskmanagement.R;
import com.minhkhoa.taskmanagement.model.Board;
import com.minhkhoa.taskmanagement.model.List;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    Context context;
    ArrayList<List> listArrayList;
    LayoutInflater inflater;

    public ListAdapter(Context context, ArrayList<List> listArrayList) {
        this.context = context;
        this.listArrayList = listArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return listArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position == 0){
            convertView = inflater.inflate(R.layout.item_list_first,null);
        } else if ( position == listArrayList.size()-1){
            convertView = inflater.inflate(R.layout.item_list_last,null);
        } else {
            convertView = inflater.inflate(R.layout.item_list,null);
        }

        TextView txtName = convertView.findViewById(R.id.textview_name_item_list);

        txtName.setText(listArrayList.get(position).getListName());

        return convertView;
    }
}
