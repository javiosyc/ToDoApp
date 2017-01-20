package com.example.javiosyc.todoapp.model.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.javiosyc.todoapp.R;
import com.example.javiosyc.todoapp.model.ToDoItem;
import com.example.javiosyc.todoapp.model.enums.ToDoItemLevel;

/**
 * Created by javiosyc on 2017/1/18.
 */

public class ToDoItemsNoCachingAdapter extends ArrayAdapter<ToDoItem>{

    public ToDoItemsNoCachingAdapter(Context context, ToDoItem[] resource) {
        super(context, 0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ToDoItem item = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_todo_item,parent,false);
        }

        TextView nameView = (TextView) convertView.findViewById(R.id.mainNameTextView);
        TextView levelView = (TextView) convertView.findViewById(R.id.mainLevelTextView);

        nameView.setText(item.getName());

        ToDoItemLevel level = item.getLevel();
        levelView.setText(level.getText());

        levelView.setTextColor(Color.parseColor(level.getColor() ));

        return  convertView;
    }
}
