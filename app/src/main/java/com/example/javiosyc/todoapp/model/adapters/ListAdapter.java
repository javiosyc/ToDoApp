package com.example.javiosyc.todoapp.model.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.javiosyc.todoapp.R;
import com.example.javiosyc.todoapp.model.enums.ListProperty;

/**
 * Created by javiosyc on 2017/1/19.
 */

public class ListAdapter<T extends ListProperty> extends ArrayAdapter<T> {
    private final LayoutInflater mInflater;

    public ListAdapter(Context context, T[] resource) {
        super(context, R.layout.support_simple_spinner_dropdown_item, resource);
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        T item = getItem(position);

        TextView view = (TextView) super.getView(position, convertView, parent);

        view.setText(item.getText());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        T item = getItem(position);

        TextView view = (TextView) super.getDropDownView(position,convertView,parent);

        view.setText(item.getText());
        return view;
    }
}
