package com.example.javiosyc.todoapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Created by javiosyc on 2017/1/18.
 */

public class EditDialogFragment extends DialogFragment {

    private EditText mEditText;

    public EditDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditDialogFragment newInstance(String title) {
        EditDialogFragment frag = new EditDialogFragment();
        Bundle args = new Bundle();

        args.putString("title",title);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        mEditText = (EditText) view.findViewById(R.id.fragmentNameEditText);

        String title = getArguments().getString("title","Enter TODO");
        getDialog().setTitle(title);

        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }
}
