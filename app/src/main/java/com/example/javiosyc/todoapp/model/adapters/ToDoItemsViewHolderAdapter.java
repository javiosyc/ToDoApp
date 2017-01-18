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

import java.util.List;

/**
 * Created by javiosyc on 2017/1/18.
 */

public class ToDoItemsViewHolderAdapter extends ArrayAdapter<ToDoItem>{


    private static class ViewHolder {
        TextView name;
        TextView level;
    }

    public ToDoItemsViewHolderAdapter(Context context, List<ToDoItem> resource) {
        super(context, R.layout.list_view_todo_item, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ToDoItem item = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.list_view_todo_item, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.mainNameTextView);
            viewHolder.level = (TextView) convertView.findViewById(R.id.mainLevelTextView);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name.setText(item.getName());

        ToDoItemLevel level = item.getLevel();
        viewHolder.level.setText(level.getText());
        viewHolder.level.setTextColor(Color.parseColor(level.getColor()));

        return  convertView;
    }
}
