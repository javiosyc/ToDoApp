package com.example.javiosyc.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.javiosyc.todoapp.model.ToDoItem;
import com.example.javiosyc.todoapp.model.adapters.ListAdapter;
import com.example.javiosyc.todoapp.model.enums.ToDoItemAction;
import com.example.javiosyc.todoapp.model.enums.ToDoItemLevel;
import com.example.javiosyc.todoapp.model.enums.ToDoItemStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;


public class EditItemActivity extends AppCompatActivity {
    private static final String TODO_NAME_PREFIX = "TODO_";
    private static final String DEFAULT_NOTE_VALUE = "NOTE";
    private static Logger _log = Logger.getLogger(EditItemActivity.class.getName());
    private CalendarView calendarView;
    private EditText nameEditText;
    private EditText noteEditText;
    private Spinner levelSpinner;
    private Spinner statusSpinner;
    private Intent intent;
    private ArrayList<ToDoItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        items = (ArrayList<ToDoItem>) intent.getSerializableExtra("items");

        initView();

        setValueByAction();
    }

    private void setValueByAction() {
        ToDoItemAction action = (ToDoItemAction) intent.getSerializableExtra("action");
        _log.info("action:" + action);
        switch (action) {
            case EDIT:
                int position = intent.getIntExtra("selectedItemPosition", -1);
                _log.info("selectedItemPosition:" + position);

                if (position >= 0) {
                    ToDoItem item = items.get(position);
                    nameEditText.setText(item.getName());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                    try {
                        calendarView.setDate(dateFormat.parse(item.getDueDate()).getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    noteEditText.setText(item.getNote());

                    int index = ((ArrayAdapter)levelSpinner.getAdapter()).getPosition(item.getLevel());

                    if(index  >= 0 ) {
                        levelSpinner.setSelection(index);
                    }

                    index  = ((ArrayAdapter)statusSpinner.getAdapter()).getPosition(item.getStatus());
                    if(index >= 0) {
                        statusSpinner.setSelection(index);
                    }
                }
                break;
            case ADD:
                    nameEditText.setText( TODO_NAME_PREFIX   + (items.size() + 1));
                    noteEditText.setText(DEFAULT_NOTE_VALUE);
                    levelSpinner.setSelection(ToDoItemLevel.getDefaultLevelIndex());
                    statusSpinner.setSelection(ToDoItemStatus.getDefaultLevelIndex());
                break;
            default:
                break;
        }
    }


    public void initView() {
        setContentView(R.layout.activity_edit_item);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        noteEditText = (EditText) findViewById(R.id.noteEditText);
        levelSpinner = (Spinner) findViewById(R.id.levelSpinner);
        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);

        initDropDownView();
    }

    private void initDropDownView() {
        //TODO refactor init Spinner
        initStatusSpinner();
        initLevelSpinner();
    }

    private void initLevelSpinner() {
        ArrayAdapter levelAdpater = new ListAdapter(this, ToDoItemLevel.getAllStatus());

        levelSpinner.setAdapter(levelAdpater);
    }

    private void initStatusSpinner() {

        ArrayAdapter statusListAdapter = new ListAdapter(this,ToDoItemStatus.getAllStatus());

        statusSpinner.setAdapter(statusListAdapter);
    }

    public void onDeleteItem(View v) {
        int position = intent.getIntExtra("selectedItemPosition", -1);
        items.remove(position);

        Intent data = new Intent();
        data.putExtra("action", ToDoItemAction.DELETE);
        data.putExtra("updateItemPosition", position);

        setResult(RESULT_OK, data);

        finish();
    }

    public void onSaveItem(View v) {
        String name = nameEditText.getText().toString();

        ToDoItemAction action = (ToDoItemAction) intent.getSerializableExtra("action");

        Intent data = new Intent();
        data.putExtra("action", action);

        switch (action) {
            case EDIT:
                int position = intent.getIntExtra("selectedItemPosition", -1);

                if (position >= 0) {
                    ToDoItem item = items.get(position);

                    updateByCurrentView(item);

                    data.putExtra("updateItemPosition", position);
                    data.putExtra("updateItem", item);
                }
                break;
            case ADD:
                ToDoItem item = new ToDoItem();
                updateByCurrentView(item);
                items.add(item);

                data.putExtra("updateItemPosition", items.size() - 1);
                data.putExtra("updateItem", item);
                break;
            case DELETE:
                break;
            default:
                break;
        }

        setResult(RESULT_OK, data);

        finish();
    }


    private void updateByCurrentView(ToDoItem item) {

        item.setName(nameEditText.getText().toString());

        Date date  =  new Date (calendarView.getDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        item.setDueDate(dateFormat.format(date));

        item.setNote(noteEditText.getText().toString());
        item.setLevel( (ToDoItemLevel) levelSpinner.getSelectedItem());
        item.setStatus( (ToDoItemStatus) statusSpinner.getSelectedItem());

        _log.info("note:"  +  item.getNote());
    }
}
