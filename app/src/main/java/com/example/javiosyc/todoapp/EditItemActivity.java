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
import com.example.javiosyc.todoapp.model.enums.ToDoItemAction;
import com.example.javiosyc.todoapp.model.enums.ToDoItemLevel;
import com.example.javiosyc.todoapp.model.enums.ToDoItemStatus;
import com.example.javiosyc.todoapp.utils.DBUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;


public class EditItemActivity extends AppCompatActivity {
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

        switch (action) {
            case EDIT:
                int position = intent.getIntExtra("selectedItemPosition", -1);
                _log.info("selectedItemPosition:" + position);

                if (position >= 0) {
                    ToDoItem item = items.get(position);
                    nameEditText.setText(item.getName());
                    //SimpleDateFormat.

                    //calendarView.setDate();
                }
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
        ArrayAdapter statusAdpater = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
                ToDoItemLevel.getAllStatus());

        statusAdpater.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        levelSpinner.setAdapter(statusAdpater);
    }

    private void initStatusSpinner() {
        ArrayAdapter statusAdpater = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
                ToDoItemStatus.getAllStatus());

        statusAdpater.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        statusSpinner.setAdapter(statusAdpater);
    }

    public void onDeleteItem(View v) {
        int position = intent.getIntExtra("selectedItemPosition", -1);
        items.remove(position);

        updateDB();

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
                    item.setName(nameEditText.getText().toString());

                    data.putExtra("updateItemPosition", position);
                    data.putExtra("updateItem", item);
                }
                break;
            case ADD:
                ToDoItem item = new ToDoItem(name);
                items.add(item);

                data.putExtra("updateItemPosition", items.size() - 1);
                data.putExtra("updateItem", item);
                break;
            default:
                break;
        }


        updateDB();

        setResult(RESULT_OK, data);

        finish();
    }

    private void updateDB() {
        String directory = intent.getStringExtra("saveFileDir");
        DBUtils utils = new DBUtils();
        utils.writeItems(new File(directory), items);

    }
}
